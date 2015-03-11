<%@ include file="/html/common/init.jsp" %>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- <%@ page errorPage="/html/global/jerror.jsp" %> --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
	<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->

		<title>Yoda Site content management ecommerce system - <tiles:insertAttribute name="title" /></title>

		<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

		<link rel="stylesheet" href='<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />'>
		<link rel="stylesheet" href='<c:url value="/resources/css/main.css" />' />
		<link rel="stylesheet" href='<c:url value="/resources/css/bootstrap-fileupload.min.css" />' />
		<link rel="stylesheet" href='<c:url value="/resources/css/datepicker.css" />' />

		<link rel="stylesheet" href="http://fonts.useso.com/css?family=Montserrat:400,700" />

		<link rel="stylesheet" href='<c:url value="/resources/font-awesome-4.1.0/css/font-awesome.min.css" />' />
	</head>

	<body>
		<nav class="navbar navbar-default navbar-static-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#"></a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li>
							<a href='<c:url value="/controlpanel/home" />'>Home</a>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Contents<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href='<c:url value="/controlpanel/content/list" />'>Content</a></li>
								<li><a href='<c:url value="/controlpanel/items" />'>Items</a></li>
								<li><a href='<c:url value="/controlpanel/comments" />'>Comments</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Public Site<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href='<c:url value="/controlpanel/homepage" />'>Home Page</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Module<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href='<c:url value="/controlpanel/contactus/list" />'>Contact Us</a></li>
								<li><a href='<c:url value="/controlpanel/feedback" />'>Feedback</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Setup<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href='<c:url value="/controlpanel/menu/edit" />'>Menus</a></li>
								<li><a href='<c:url value="/controlpanel/section/edit" />'>Sections</a></li>
							</ul>
						</li>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Administration<span class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<li><a href='<c:url value="/controlpanel/system/edit" />'>System</a></li>
									<li><a href='<c:url value="/controlpanel/site/list" />'>Sites</a></li>
									<li><a href='<c:url value="/controlpanel/user/list" />'>Users</a></li>
								</ul>
							</li>
						</sec:authorize>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li>
							<a href='<c:url value="/" />'>${homePageCommand.siteName}</a>
						</li>
						<li>
							<a href='<c:url value="/logout" />'><spring:message code="logout" /></a>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</nav>

		<div class="container-fluid">
			<div class="section">
				<tiles:insertAttribute name="content" />
			</div>
		</div>

		<footer class="footer text-center">
			<div class="container">
				<p class="text-muted">&copy; Yoda Site, 2015</p>
			</div>
		</footer>

		<!-- Placed at the end of the document so the pages load faster -->
		<script src='<c:url value="/resources/js/jquery-1.11.1.min.js" />'></script>
		<script src='<c:url value="/resources/bootstrap-3.2.0/js/bootstrap.min.js" />'></script>
		<%-- <script src='<c:url value="/resources/js/modernizr-2.6.2-respond-1.1.0.min.js" />'></script> --%>
		<script src='<c:url value="/resources/js/fileupload/bootstrap-fileupload.js" />'></script>
		<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
		<script src='<c:url value="/resources/js/ie10-viewport-bug-workaround.js" />'></script>
	</body>
</html>