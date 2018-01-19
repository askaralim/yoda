<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>${brand.name} - ${siteInfo.siteName}</title>

	<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href='<c:url value="/template/basic/main.css" />' type="text/css" />

	<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
	<%-- <link href='<c:url value="/template/basic/font-awesome-4.1.0/css/font-awesome.min.css" />' rel="stylesheet" type="text/css"> --%>
	<link rel="stylesheet" href="http://cdn.bootcss.com/font-awesome/4.1.0/css/font-awesome.min.css">
</head>

<body>
	${horizontalMenu}
	<div class="section">
		<div class="container">
			<div class="col-lg-10 col-lg-offset-1">
				<h4 class="page-header">
					${brand.name}
				</h4>
				<p class="text-right">
					<bd style="font-size: 12px; color: #d2d2d2; font-weight: 700;">
						<i class="glyphicon glyphicon-eye-open"></i>
						${brand.hitCounter}
					</bd>
				</p>
				<div class="row">
					<div class="brand-detail">
						<div class="col-sm-3">
							<img src="${brand.imagePath}" class="img-responsive" alt="">
						</div>
						<div class="col-sm-9">
							<dl class="dl-horizontal">
								<dt><spring:message code="brand-founded" /></dt>
								<dd>${brand.country}</dd>
								<c:if test="${brand.foundDate != null}">
								<dt><spring:message code="foundeded-date" /></dt>
								<dd>${brand.foundDate}</dd>
								</c:if>
								<dt><spring:message code="brand-products" /></dt>
								<dd>${brand.kind}</dd>
							</dl>
							<div class="brand-description">${brand.description}</div>
						</div>
					</div>
				</div>
				<h4 class="page-header">
					<spring:message code="recommend-product" />
				</h4>
				<div class="row">
					<c:forEach var="item" items="${items}">
						<spring:url value="/item/{itemId}" var="itemUrl">
							<spring:param name="itemId" value="${item.id}"/>
						</spring:url>
						<div class="col-xs-6 col-sm-4 col-md-3">
							<a href="${fn:escapeXml(itemUrl)}">
								${item.name}
							</a>
							<div class="thumbnail">
								<a href="${fn:escapeXml(itemUrl)}">
									<img class="img-responsive" src="${item.imagePath}" alt="${item.name}">
								</a>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

	<footer class="text-center">
		<div class="footer-below">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">${siteInfo.siteFooter}</div>
				</div>
			</div>
		</div>
	</footer>

	<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<%-- <script type="text/javascript" src='<c:url value="/template/basic/jquery-1.11.1.min.js" />'></script>
	<script type="text/javascript" src='<c:url value="/template/basic/bootstrap-3.2.0/js/bootstrap.min.js" />'></script> --%>
</body>
</html>