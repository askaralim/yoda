<%@ include file="../common/init.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<title>Install</title>

		<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

		<link rel="stylesheet" href="<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />" type="text/css">
		<link rel="stylesheet" href="<c:url value="/resources/css/installation.css" />">

	</head>
	<body>
		<form:form method="post" modelAttribute="installationInfo">
			<div class="container">
				<div class="page-header">
					<h1>Yoda Site Installation</h1>
				</div>
				<c:choose>
					<c:when test="${installationInfo.databaseExist}">
						<p>
							Database already exist and will not be overriden. This is a normal behaviour when performing upgrade to yoda Site.
							<br> You can simply mark the installation process complete.
						</p>
						<input type="submit" name="complete" value="Complete" class="btn btn-primary">
					</c:when>
					<c:when test="${installationInfo.error}">
						<div class="alert alert-danger" role="alert">
							Database loaded with errors. Due to the errors, setup may not have completed successfully.
						</div>
						<span style="font-size: 0.8em">
							<c:out value='${installationInfo.detailLog}' escapeXml="false" />
						</span>
					</c:when>
					<c:otherwise>
						<div class="alert alert-success" role="alert">
							Congratulation! Database initialized successfully.
						</div>
						<p>
							Before you start, please make sure that Yoda Site is restarted on the application server.
						</p>
					</c:otherwise>
				</c:choose>
			</div>
		</form:form>
	</body>
</html>