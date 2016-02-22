<%@ include file="/html/common/init.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<!-- <link rel="icon" href="../../favicon.ico"> -->

	<title>403</title>

	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />">

	<!-- Custom styles for this template -->
	<link href="<c:url value="/resources/css/403.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/font-awesome-4.1.0/css/font-awesome.min.css" />" rel="stylesheet">
</head>

<body>
	<!-- Main content -->
	<div class="container">
		<div class="error-page">
			<h2 class="headline text-info">403</h2>
			<div class="error-content">
				<h3>
					<i class="fa fa-warning text-yellow"></i><spring:message code="http-Status-403-access-is-denied" />
				</h3>
				<c:choose>
					<c:when test="${empty username}">
						<p class="lead">
							<spring:message code="no-permission-to-access-this-page" />
						</p>
					</c:when>
					<c:otherwise>
						<p class="lead">
							<spring:message code="username" /> : ${username} <br /> <spring:message code="no-permission-to-access-this-page" />
						</p>
					</c:otherwise>
				</c:choose>
				<p>
					<a href="<c:url value="/" />"><spring:message code="go-to-home-page" /></a>.
				</p>
			</div>
			<!-- /.error-content -->
		</div>
		<!-- /.error-page -->

	</div>
	<!-- /.content -->

	<script type="text/javascript" src='<c:url value="/resources/js/jquery-1.11.1.min.js" />'></script>
	<script type="text/javascript" src='<c:url value="/resources/bootstrap-3.2.0/js/bootstrap.min.js" />'></script>
</body>
</html>