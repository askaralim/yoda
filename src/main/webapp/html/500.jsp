<%@ include file="/html/common/init.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

	<title>500</title>

	<link rel="stylesheet" href="<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />">

	<link href="<c:url value="/resources/css/403.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/font-awesome-4.1.0/css/font-awesome.min.css" />" rel="stylesheet">
</head>

<body>
	<!-- Main content -->
	<div class="container">
		<div class="error-page">
			<h2 class="headline text-info">500</h2>
			<div class="error-content">
				<h3>
					<i class="fa fa-warning text-yellow"></i> <spring:message code="server-error" />
				</h3>
				<%-- <p>
				${requestURL}
				</p> --%>
				<p>
					<a href="<c:url value="/" />"><spring:message code="go-to-home-page" /></a> <spring:message code="or-try-using-the-search-form" />
				</p>
				<form class='search-form' action="<spring:url value="/search" />" >
					<div class='input-group'>
						<input type="text" class="form-control" id="q" name="q" placeholder="<spring:message code="search" />">
						<div class="input-group-btn">
							<button type="submit" name="submit" class="btn btn-primary">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</div>
					<!-- /.input-group -->
				</form>
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