package com.yoda.kernal.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.yoda.install.controller.Installer;
import com.yoda.kernal.util.PortalInstances;

public class MainServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		ServletContext servletContext = config.getServletContext();

		super.init(config);

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize servlet context pool");
		}

		try {
			initServletContextUtil(servletContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		boolean installCompleted = false;

//		String filename =
//			Utility.getServletLocation(config.getServletContext())
//			+ Constants.CONFIG_PROPERTIES_FILENAME;

//		String filename = ServletContextUtil.getRealPath() + Constants.CONFIG_PROPERTIES_FILENAME;

		try {
//			installCompleted = Installer.isInstallCompleted(
//				Utility.getServletLocation(config.getServletContext()));
//			installCompleted = Installer.isInstallCompleted(ServletContextUtil.getRealPath());

			installCompleted = Installer.isInstallCompleted();
//			installCompleted = true;
		}
		catch (Exception e) {
			config.getServletContext().log(
				"Unable to test if installation has completed", e);
		}

		if (!installCompleted) {
			config.getServletContext().log("Installation has not been finished. Skipping custom initialization.");
			config.getServletContext().log("Please proceed to installation and remmember to restart before continue.");

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize companies");
		}

		try {
			initSites();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

//		try {
//			URL rootURL = context.getResource("/");
//
//			File rootFile = new File(rootURL.toString());
//
//			contextPath = rootFile.getName();

//			if (contextPath.equals("localhost")) {
//				contextPath = StringPool.BLANK;
//			}

//			ApplicationGlobal.setContextPath(contextPath);
//
//			ApplicationGlobal.setServletLocation(
//				Utility.getServletLocation(servletContext));
//		}
//		catch (Exception e) {
//			config.getServletContext().log("Problem initialing Yoda Site.", e);
//		}

//		ApplicationGlobal.setWorkingDirectory(
//			config.getInitParameter("workingDirectory"));

//		boolean requireInstall = true;
//
//		File file = new File("/tmp/__install__.txt");
//		if (file.exists()) {
//			requireInstall = false;
//		}

//		Properties properties = new Properties();
//
//		try {
//			if (!installCompleted) {
//				config.getServletContext()
//					.log("Yoda has not been configured. Default configuration will be used.");
//			}
//			else {
//				properties.load(new FileInputStream(filename));
//
//				ApplicationGlobal.setWorkingDirectory(
//					properties.getProperty(
//						Constants.CONFIG_PROPERTY_WORKINGDIRECTORY));
//			}
//		}
//		catch (Exception e) {
//			config.getServletContext().log("Problem loading Yoda Site configuration file.", e);
//		}

		/*
		 * Configure log4j configuration information
		 */
//		try {
//			InputStream input = MainServlet.class.getResourceAsStream("/log4j.properties");
//
//			Properties log4jProperties = new Properties();
//
//			log4jProperties.load(input);
//
//			if (installCompleted) {
//				String logDirectory = properties.getProperty(Constants.CONFIG_PROPERTY_LOGDIRECTORY);
//
//				if (logDirectory != null) {
//					log4jProperties.put(Constants.CONFIG_LOG4J_PROPERTY, properties.getProperty(Constants.CONFIG_PROPERTY_LOGDIRECTORY) + "/" + Constants.CONFIG_LOG4J_LOGFILENAME);
//
//					config.getServletContext().log("Picking up log directory from installation setup - " + logDirectory);
//				}
//			}
//
//			PropertyConfigurator.configure(log4jProperties);
//		}
//		catch (Exception e) {
//			config.getServletContext().log("Problem loading log4j.properties file.", e);
//		}
	}

	private void initSites() {
		ServletContext servletContext = getServletContext();

		PortalInstances.initSites(servletContext);
	}

	protected void initServletContextUtil(ServletContext servletContext) throws Exception {
		ServletContextUtil.setServletContext(servletContext);

		ServletContextUtil.setContextPath(servletContext.getContextPath());
	}

	Logger _log = Logger.getLogger(MainServlet.class);
}