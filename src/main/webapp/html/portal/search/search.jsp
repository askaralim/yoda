<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title><spring:message code="search" /> - ${siteInfo.siteName}</title>

	<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

	<link rel="stylesheet" href='<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />' type="text/css">
	<link rel="stylesheet" href='<c:url value="/template/basic/main.css" />' type="text/css" />

	<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
	<link href='<c:url value="/template/basic/font-awesome-4.1.0/css/font-awesome.min.css" />' rel="stylesheet" type="text/css">
</head>

<body>
	${horizontalMenu}
	<div class="container pt">
		<div class="row">
			<div class="col-lg-8">
				<div class="content-post">
					<div class="page-header">
						<h4><spring:message code="search" />&nbsp${q}</h4>
						<p><spring:message code="search-results" />&nbsp:&nbsp${contentsTotal}</p>
					</div>
					<c:forEach var="content" items="${contents}">
						<div class="row content-preview-wrapper-row">
							<div class="col-sm-12 content-preview-item">
								<h4>
									<spring:url value="/content/{contentId}" var="contentUrl">
										<spring:param name="contentId" value="${content.contentId}"/>
									</spring:url>
									<a href="${fn:escapeXml(contentUrl)}">
										${content.title}
									</a>
								</h4>
								<p>${content.shortDescription}</p>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<c:if test="${brands != null}">
				<div class="col-lg-4">
					<div class="content-post">
						<c:forEach var="brand" items="${brands}">
							<div class="row content-preview-wrapper-row">
								<div class="col-sm-6 content-preview-item">
									<h4>
										<spring:url value="/brand/{brandId}" var="brandUrl">
											<spring:param name="brandId" value="${brand.brandId}"/>
										</spring:url>
										<a href="${fn:escapeXml(brandUrl)}">
											${brand.name}
										</a>
									</h4>
									<div class="content-preview-image">
										<img src="${brand.imagePath}" alt="${brand.name}">
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
		</div>
	</div>

	<p class="small text-muted text-center">${siteInfo.siteFooter}</p>

	<script type="text/javascript" src='<c:url value="/template/basic/jquery-1.11.1.min.js" />'></script>
	<script type="text/javascript" src='<c:url value="/template/basic/bootstrap-3.2.0/js/bootstrap.min.js" />'></script>
</body>
</html>