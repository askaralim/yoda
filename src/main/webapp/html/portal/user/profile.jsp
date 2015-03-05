<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>${user.username} - ${siteInfo.siteName}</title>

	<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

	<link rel="stylesheet" href='<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />' type="text/css">
	<link rel="stylesheet" href='<c:url value="/template/basic/main.css" />' type="text/css" />

	<link href="http://fonts.useso.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
	<link href='<c:url value="/template/basic/font-awesome-4.1.0/css/font-awesome.min.css" />' rel="stylesheet" type="text/css">
</head>

<body>
	${horizontalMenu}

	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="page-header">
					<h3><i class="fa fa-user"></i> ${user.username}</h3>
					<i class="fa fa-envelope"></i> ${user.email}&nbsp&nbsp&nbsp
					<c:if test="${user.phone}">
						<i class="fa fa-phone"></i> ${user.phone}
					</c:if>
				</div>
			</div>
		</div>

		<div class="row mt">
			<div class="col-md-2">
				<ul class="nav nav-pills nav-stacked" role="tablist">
					<!-- <li class="nav-header"></li> -->
					<li class="active" role="presentation"><a href="#post"><i class="glyphicon glyphicon-list"></i> <b><spring:message code="posts" /></b></a></li>
				</ul>
			</div>
			<div class="col-md-8">
				<div class="tab-content">
					<div class="tab-pane active" id="posts">
						<div class="row">
							<ul class="list-group">
								<c:forEach var="content" items="${contents}">
									<li class="list-group-item">
										<c:choose>
											<c:when test='${content.published}'>
												<spring:url value="/content/{contentId}" var="contentUrl">
													<spring:param name="contentId" value="${content.contentId}"/>
												</spring:url>
												<a href="${fn:escapeXml(contentUrl)}">
													<small><fmt:formatDate value="${content.createDate}" pattern="yyyy-MM-dd" /></small>&nbsp&nbsp&nbsp<b>${content.title}</b>&nbsp[<spring:message code="published" />]
												</a>
											</c:when>
											<c:otherwise>
												<small><fmt:formatDate value="${content.createDate}" pattern="yyyy-MM-dd" /></small>&nbsp&nbsp&nbsp<b>${content.title}</b>&nbsp[<spring:message code="unpublished" />]
											</c:otherwise>
										</c:choose>
										<c:if test="${currentUser != null && currentUser.userId == content.createBy}">
											<spring:url value="/content/{contentId}/edit" var="editContentUrl">
												<spring:param name="contentId" value="${content.contentId}"/>
											</spring:url>
											<span class="pull-right">
												<a href="${fn:escapeXml(editContentUrl)}" class="btn btn-sm"><i class="fa fa-edit"></i></a>
												<%-- <a href="${fn:escapeXml(deleteContentUrl)}" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i></a> --%>
											</span>
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2">
				<c:if test="${currentUser != null && currentUser.userId == user.userId}">
					<a class="btn btn-primary btn-sm" href='<c:url value="/content/add" />' role="button"><spring:message code="new-content" /></a>
				</c:if>
			</div>
		</div>
		<hr>
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

	<script type="text/javascript" src='<c:url value="/template/basic/jquery-1.11.1.min.js" />'></script>
	<script type="text/javascript" src='<c:url value="/template/basic/bootstrap-3.2.0/js/bootstrap.min.js" />'></script>
</body>
</html>