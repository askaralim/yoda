package com.yoda.install.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Vector;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.install.InstallationCommand;
import com.yoda.util.Utility;

@Controller
@RequestMapping("/install")
public class InstallController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			@ModelAttribute InstallationCommand command,
			BindingResult result, SessionStatus status)
		throws Throwable {
		if (Installer.isInstallCompleted()) {
			command.setInstallCompleted(true);

			result.reject("error.install.completed");
		}

//		command.setContextPath(ApplicationGlobal.getContextPath());
		command.setDriver("com.mysql.jdbc.Driver");
		command.setDbHost("localhost");
		command.setDbPort("3306");

		return new ModelAndView("/install/form", "installationCommand", command);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute InstallationCommand command,
			BindingResult result, SessionStatus status) {

//		new InstallFormValidator().validate(command, result);

		if(result.hasErrors()) {
			return "/install/form";
		}

//		ApplicationGlobal.setWorkingDirectory(command.getWorkingDirectory());

		try {
			Installer.testDatabaseConnectivity(command);
		}
		catch (Exception e) {
			command.setError(true);

			String errorInfo = Utility.getStackTrace(e);

			errorInfo = errorInfo.replaceAll("\n", "<br>");

			command.setDetailLog(errorInfo);

			return "/install/form";
		}
		finally {
//			try {
//				Installer.writeConfig(command, ServletContextUtil.getRealPath());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}

//		command.setContextPath(ServletContextUtil.getContextPath());
		command.setError(false);
		command.setDetailLog("");

//		String filename = installationInfo.getWorkingDirectory() + "\\" + Constants.CONFIG_PROPERTIES_FILENAME;

		try {
			if (Installer.isDatabaseCreated(command)) {
				command.setDatabaseExist(true);

				Installer.setInstallCompleted();

				result.reject("dbcreated");

				return "/install/complete";
			}
		}
		catch (Exception e) {
			command.setError(true);

			String errorInfo = Utility.getStackTrace(e);

			errorInfo = errorInfo.replaceAll("\n", "<br>");

			command.setDetailLog(
				command.getDetailLog()+ "<br>" + errorInfo);

			return "/install/form";
		}

		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
				command.getUrl(), command.getUsername(),
				command.getPassword());

			Vector<String> ddl = prepareSchema(connection);

			loadCreate(connection, ddl, command);
			loadData(connection, command);
			loadIndex(connection, ddl, command);

			Installer.setInstallCompleted();
		}
		catch (Exception e) {
			command.setError(true);

			String errorInfo = Utility.getStackTrace(e);

			errorInfo = errorInfo.replaceAll("\n", "<br>");

			command.setDetailLog(command.getDetailLog() + "\n" + errorInfo);

			return "/install/complete";
		}
		finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "/install/complete";
	}

	public void loadCreate(
			Connection connection, Vector<String> ddl, InstallationCommand comand) 
		throws Exception {
		Enumeration<String> enumeration = ddl.elements();

		while (enumeration.hasMoreElements()) {
			String sql = (String)enumeration.nextElement();

			try {

				if (!ltrim(sql).toLowerCase().startsWith("create")) {
					continue;
				}

				connection.createStatement().executeUpdate(sql);
			} catch (Exception e) {
				String errorInfo = Utility.getStackTrace(e);

				errorInfo = errorInfo.replaceAll("\n", "<br>");

				comand.setDetailLog(comand.getDetailLog() + "<br>" + sql
					+ "<br><br>");

				throw e;
			}
		}

	}

	public void loadData(
			Connection connection, InstallationCommand info)
		throws Exception {

		InputStream stream = 
			getClass().getResourceAsStream("/sql/load.sql");

		BufferedReader reader =
			new BufferedReader(new InputStreamReader(stream));

		String sql = "";
		String line = null;

		try {
			while (true) {
				line = reader.readLine();

				if (line == null) {
					break;
				}

				if (line.startsWith("--")) {
					continue;
				}

				if (line.startsWith("/*")) {
					continue;
				}

				if (line.startsWith("#")) {
					continue;
				}

				if (line.trim().length() == 0) {
					continue;
				}

				sql += line;

				if (sql.startsWith("INSERT") || sql.startsWith("LOCK")) {
					sql = sql.replaceAll("\"", "`");
				}

				if (sql.trim().endsWith(";")) {
					sql = sql.replaceAll("\t", " ");

//					System.out.println("[" + sql + "]");

					connection.createStatement().executeUpdate(sql);

					sql = "";
				}
			}

		}
		catch (Exception e) {
			String errorInfo = Utility.getStackTrace(e);

			errorInfo = errorInfo.replaceAll("\n", "<br>");

			info.setDetailLog(info.getDetailLog() + "<br>" + line + "<br><br>");

			throw e;
		}

	}

	public void loadIndex(
			Connection connection, Vector<String> ddl, InstallationCommand command)
		throws Exception {
		Enumeration<String> enumeration = ddl.elements();

		while (enumeration.hasMoreElements()) {
			String sql = (String) enumeration.nextElement();

			try {

				if (ltrim(sql).toLowerCase().startsWith("create")) {
					continue;
				}

//				System.out.println(sql);

				connection.createStatement().executeUpdate(sql);
			} catch (Exception e) {
				String errorInfo = Utility.getStackTrace(e);

				errorInfo = errorInfo.replaceAll("\n", "<br>");

				command.setDetailLog(command.getDetailLog() + "<br>" + sql
					+ "<br><br>");

				throw e;
			}
		}
	}

	String ltrim(String input) {
		if (input == null) {
			return null;
		}
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != ' ') {
				return input.substring(i);
			}
		}
		return "";
	}

	public Vector<String> prepareSchema(Connection connection)
		throws Exception {
		Vector<String> ddl = new Vector<String>();

		InputStream stream =
			getClass().getResourceAsStream("/sql/schema.ddl");
		
		BufferedReader reader =
			new BufferedReader(new InputStreamReader(stream));

		StringBuffer buffer = new StringBuffer();

		while (true) {
			String line = reader.readLine();

			if (line == null) {
				break;
			}

			line = line.trim();

			if (line.length() == 0) {
				continue;
			}

			buffer.append(line + " ");

			if (line.endsWith(";")) {

				if (buffer.toString().indexOf("drop") == -1) {
					ddl.add(buffer.toString());
				}

				buffer = new StringBuffer();
			}
		}

		return ddl;
	}

}