package com.yoda.install.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yoda.install.InstallationCommand;
import com.yoda.kernal.model.Release;
import com.yoda.kernal.service.ReleaseService;
import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.kernal.util.ReleaseInfo;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.Validator;

public class Installer {
	static void testDatabaseConnectivity(InstallationCommand command)
		throws Exception {
		String url = null;

		if (Format.isNullOrEmpty(command.getUrl())) {
			url = "jdbc:mysql://" + command.getDbHost() + ":"
				+ command.getDbPort() + "/"
				+ command.getDbName() + "?autoReconnect=true";

			command.setUrl(url);
		}
		else {
			url = command.getUrl();
		}

		Class.forName(command.getDriver());

		Connection connection = DriverManager.getConnection(
			url, command.getUsername(), command.getPassword());

		connection.close();
	}

	static void writeConfig(InstallationCommand installationInfo, String dir)
		throws IOException {
		Properties properties = new Properties();

		properties.setProperty("driverClass", installationInfo.getDriver());
		properties.setProperty("jdbcUrl", installationInfo.getUrl());
		properties.setProperty("user", installationInfo.getUsername());
		properties.setProperty("password", installationInfo.getPassword());
		properties.setProperty(Constants.CONFIG_PROPERTY_WORKINGDIRECTORY, installationInfo.getWorkingDirectory());
		properties.setProperty(Constants.CONFIG_PROPERTY_LOGDIRECTORY, installationInfo.getLog4jDirectory());

		FileOutputStream out = 
			new FileOutputStream(new File(dir + "\\" + Constants.CONFIG_PROPERTIES_FILENAME));

		properties.store(out, null);
	}

	static boolean isDatabaseCreated(InstallationCommand installationInfo)
		throws SQLException {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
				installationInfo.getUrl(), installationInfo.getUsername(),
				installationInfo.getPassword());

			String sql = "select count(*) from site";

			ResultSet result = connection.createStatement().executeQuery(sql);

			result.next();

			int count = result.getInt(1);

			if (count > 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException e) {
			// Object does not exist error
			if (e.getSQLState().equals("42S02")) {
				return false;
			}
			throw e;
		}
		finally {
			connection.close();
		}
	}

	static void setInstallCompleted() {
		getService().addRelease(ReleaseInfo.getBuildNumber());
	}

	static public boolean isInstallCompleted() {
		Release release;

		try {
			release = getService().getRelease(ReleaseInfo.getBuildNumber());
		}
		catch (InvalidDataAccessResourceUsageException e) {
			return false;
		}

		if (Validator.isNotNull(release)) {
			return true;
		}

		return false;
	}

	static public boolean isValidDirectory(String value) {
		String filename = "_00000";

		File file = new File(value + "/" + filename);

		try {
			FileWriter writer = new FileWriter(file);
			writer.append(' ');
			writer.close();
			file.delete(); // why? is this really need to?
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	static private ReleaseService getService() {
		ReleaseService releaseService = (ReleaseService)WebApplicationContextUtils.getRequiredWebApplicationContext(
			ServletContextUtil.getServletContext()).getBean("releaseServiceImpl");

		return releaseService;
	}
}