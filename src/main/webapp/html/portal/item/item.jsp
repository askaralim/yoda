<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>${siteInfo.siteName} - ${item.name}</title>

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
					${item.name}
				</h4>
				<p class="text-right">
					<bd style="font-size: 12px; color: #d2d2d2; font-weight: 700;">
						<i class="glyphicon glyphicon-eye-open"></i>
						${item.hitCounter}
					</bd>
				</p>
				<div class="row">
					<div class="brand-detail">
						<div class="col-sm-3">
							<img src="${item.imagePath}" class="img-responsive" alt="${item.name}">
						</div>
						<div class="col-sm-9">
							<dl class="dl-horizontal">
								<dt><spring:message code="brand" /></dt>
								<spring:url value="/brand/{brandId}" var="brandUrl">
									<spring:param name="brandId" value="${item.brand.brandId}"/>
								</spring:url>
								<dd>
									<a href="${fn:escapeXml(brandUrl)}">
										${item.brand.name}
									</a>
								</dd>
								<dt><spring:message code="price" /></dt>
								<dd>${item.price}</dd>
								<c:forEach var="extraField" items="${item.extraFieldList}">
									<dt><c:out value="${extraField.key}" /></dt>
									<dd><c:out value="${extraField.value}" /></dd>
								</c:forEach>
								<c:if test="${item.buyLinkList.size() > 0}">
									<dt><spring:message code="buy-link" /></dt>
									<dd>
										<c:forEach var="buyLink" items="${item.buyLinkList}">
											<i class="fa fa-external-link" aria-hidden="true"></i>
											<a href="${fn:escapeXml(buyLink.value)}" target="_blank">
												${buyLink.key}
											</a>
											&nbsp&nbsp
										</c:forEach>
									</dd>
								</c:if>
							</dl>
							<div class="item-description">${item.description}</div>
						</div>
					</div>
				</div>
				<p class="text-right">
					<a href="${fn:escapeXml(backURL)}" class="btn btn-default">
						<spring:message code="back" />
					</a>
				</p>
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
</body>
</html>