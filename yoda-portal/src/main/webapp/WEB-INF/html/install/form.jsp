<%@ include file="../common/init.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<title>Yoda Site Installation</title>

		<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

		<link rel="stylesheet" href="<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />" type="text/css">
		<link rel="stylesheet" href="<c:url value="/resources/css/installation.css" />">
	</head>
	<body>
		<div class="container">
			<div class="page-header">
				<h1>Yoda Site Installation</h1>
			</div>
			<c:choose>
				<c:when test="${installationCommand.installCompleted == false}">
					<form:form method="post" modelAttribute="installationCommand">
						<div class="form-group">
							<form:label for="driver" path="driver" cssClass="control-label">Driver</form:label>
							<span class="help-block">
								Database driver class name to be used. If you are using MySql as the database, please enter com.mysql.jdbc.Driver.
							</span>
							<form:input path="driver" cssClass="form-control"/><form:errors path="driver" cssClass="alert-danger" />
						</div>

						<div class="form-group">
							<form:label for="dbHost" path="dbHost" cssClass="control-label">Database host name</form:label>
							<span class="help-block">
								The host name of the machine where the database is located. If the application is installed together on the same machine of
								the database, the host name of the database is usually 'localhost'.
							</span>
							<form:input path="dbHost" cssClass="form-control"/><form:errors path="dbHost" cssClass="alert-danger" />
						</div>

						<div class="form-group">
							<form:label for="dbPort" path="dbPort" cssClass="control-label">Database port number</form:label>
							<span class="help-block">
								Listener port of the database. If you are using MySql default installation, the listener port is 3306.
							</span>
							<form:input path="dbPort" cssClass="form-control"/><form:errors path="dbPort" cssClass="alert-danger" />
						</div>

						<div class="form-group">
							<form:label for="dbName" path="dbName" cssClass="control-label">Name of the database</form:label>
							<span class="help-block">
								Name of the empty database. Please make sure the database is already created and is empty.
							</span>
							<form:input path="dbName" cssClass="form-control"/><form:errors path="dbName" cssClass="alert-danger" />
						</div>

						<div class="form-group">
							<form:label for="username" path="username" cssClass="control-label">User id</form:label>
							<span class="help-block">
								User id to be used to connect to the database.
							</span>
							<form:input path="username" cssClass="form-control"/><form:errors path="username" cssClass="alert-danger" />
						</div>

						<div class="form-group">
							<form:label for="password" path="password" cssClass="control-label">User password</form:label>
							<span class="help-block">
								Password to be used to connect to the database.
							</span>
							<form:password path="password" cssClass="form-control"/><form:errors path="password" cssClass="alert-danger" />
						</div>

						<div class="form-group">
							<form:label for="workingDirectory" path="workingDirectory" cssClass="control-label">Working Directory</form:label>
							<span class="help-block">
								Directory on the local machine. This directory will contains all the uploaded templates, site search indexes, user uploaded
								images, etc. Directory entered here has to be writable by Yoda Site.
							</span>
							<form:input path="workingDirectory" cssClass="form-control"/><form:errors path="workingDirectory" cssClass="alert-danger" />
						</div>

						<div class="form-group">
							<form:label for="log4jDirectory" path="log4jDirectory" cssClass="control-label">Log Directory</form:label>
							<span class="help-block">
								Directory on the local machine where log files should be located. Directory entered here has to be writable by Yoda Site.
							</span>
							<form:input path="log4jDirectory" cssClass="form-control"/><form:errors path="log4jDirectory" cssClass="alert-danger" />
						</div>

						<c:out value='${installationCommand.detailLog}' escapeXml="false" />

						<input type="submit" name="Next" value="Next" class="btn btn-primary">
					</form:form>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info" role="alert">
						Install Completed.
					</div>

					<a class="btn btn-primary" href='<c:url value="/" />' role="button">Go To Home Page</a>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>