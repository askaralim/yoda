package com.taklip.yoda.vo;

public class InstallationCommand {
    boolean databaseExist;
    boolean error;
    boolean installCompleted;

    String contextPath;
    String dbHost;
    String dbName;
    String dbPort;
    String detailLog;
    String driver;
    String log4jDirectory;
    String password;
    String url;
    String username;
    String workingDirectory;

    public boolean isDatabaseExist() {
        return databaseExist;
    }

    public boolean isError() {
        return error;
    }

    public boolean isInstallCompleted() {
        return installCompleted;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getDbHost() {
        return dbHost;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbPort() {
        return dbPort;
    }

    public String getDetailLog() {
        return detailLog;
    }

    public String getDriver() {
        return driver;
    }

    public String getLog4jDirectory() {
        return log4jDirectory;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setDatabaseExist(boolean databaseExist) {
        this.databaseExist = databaseExist;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setInstallCompleted(boolean installCompleted) {
        this.installCompleted = installCompleted;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public void setDetailLog(String detailLog) {
        this.detailLog = detailLog;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setLog4jDirectory(String log4jDirectory) {
        this.log4jDirectory = log4jDirectory;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
}