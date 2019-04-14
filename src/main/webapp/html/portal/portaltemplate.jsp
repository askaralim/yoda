<%@ include file="/html/common/init.jsp" %>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">

		<meta property="og:type" content="article" />
		<meta property="og:url" content="${url}" />
		<meta property="og:title" content="${pageTitle}" />
		<meta property="og:image" content="${image}" />
		<meta property="og:description" content="${description}" />

		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta name="Keywords" content="${keywords}">
		<meta name="Description" content="${description}">

		<meta property="wb:webmaster" content="a680e5302e30bee6" />

		<title>${pageTitle}</title>

		<link rel="icon" type="image/x-icon" href='<c:url value="/resources/images/favicon.ico" />' />

		<!--<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css">-->
		<link rel="stylesheet" href='<c:url value="/template/basic/bootstrap-3.2.0/css/bootstrap.min.css" />' type="text/css" />
		<link rel="stylesheet" href='<c:url value="/template/basic/main.css" />' type="text/css" />
		<!--<link rel="stylesheet" href='#springUrl("/resources/css/login.css")' type="text/css" />-->

		<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
		<!-- <link href='https://fonts.cat.net/css?family=Montserrat:400,700' rel='stylesheet'> -->

		<link rel="stylesheet" href="http://cdn.bootcss.com/font-awesome/4.1.0/css/font-awesome.min.css">
		<%-- <link href='<c:url value="/template/basic/font-awesome-4.1.0/css/font-awesome.min.css" />' rel="stylesheet" type="text/css" /> --%>
		<style>
			img:not([src]) {
				visibility: hidden;
			}
			/* Fixes Firefox anomaly during image load */
			@-moz-document url-prefix() {
				img:-moz-loading {
					visibility: hidden;
				}
			}
	</style>
	</head>
	<body id="page-top" class="index">
		<%@ include file="/html/portal/home/horizontalMenu.jsp"%>

		<tiles:insertAttribute name="content" />

		<footer class="footer-below">
			<p class="small text-muted text-center">${site.footer}</p>
		</footer>

		<!--
		<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		<script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
		-->
		<script type="text/javascript" src='<c:url value="/template/basic/jquery-1.11.1.min.js" />'></script>
		<script type="text/javascript" src='<c:url value="/template/basic/bootstrap-3.2.0/js/bootstrap.min.js" />'></script>

		<c:if test="${!empty site.googleAnalyticsId}">
			<script>
				(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
				(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
				m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
				})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

				ga('create', '${site.googleAnalyticsId}', 'auto');
				ga('send', 'pageview');
			</script>
		</c:if>
	</body>
</html>