<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page language="java" import="com.yoda.fckeditor.FckEditorCreator"%>

<jsp:useBean id="content" type="com.yoda.content.model.Content" scope="request" />

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<c:choose>
		<c:when test='${content.contentId > 0}'>
			<title>${content.title} - ${siteInfo.siteName}</title>
		</c:when>
		<c:otherwise>
			<title><spring:message code="new-content" /> - ${siteInfo.siteName}</title>
		</c:otherwise>
	</c:choose>

	<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

	<link rel="stylesheet" href='<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />' type="text/css">
	<link rel="stylesheet" href='<c:url value="/template/basic/main.css" />' type="text/css" />

	<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
	<link href='<c:url value="/template/basic/font-awesome-4.1.0/css/font-awesome.min.css" />' rel="stylesheet" type="text/css">

	<script type="text/javascript" src='<c:url value="/FCKeditor/fckeditor.js" />'></script>
</head>

<body>
	${horizontalMenu}

	<div class="container pt">
			<%-- <div class="page-header">
				<spring:url value="/user/{username}" var="userProfileUrl">
					<spring:param name="username" value="${user.username}"/>
				</spring:url>
				<c:if test="${!content['new']}">
					<h3>${content.title}</h3>
					<p>
						<b><spring:message code="create-date" /></b> : <fmt:formatDate value="${content.createDate}" pattern="yyyy-MM-dd" /> | 
						<b><spring:message code="update-date" /></b> : <fmt:formatDate value="${content.updateDate}" pattern="yyyy-MM-dd" />

						<a class="pull-right" href="${userProfileUrl}"><spring:message code="back-to-profile" /></a>
					</p>
				</c:if>
				<c:if test="${content['new']}">
					<h3><spring:message code="new" /></h3>
					<p>
						<b></b>
						<a class="pull-right" href="${userProfileUrl}"><spring:message code="back-to-profile" /></a>
					</p>
				</c:if>
			</div> --%>
			<form:form method="post" modelAttribute="content" role="form">
				<c:if test="${success != null}">
					<div class="alert alert-success" role="alert">
						<a class="panel-close close" data-dismiss="alert">×</a>
						<i class="glyphicon glyphicon-ok"></i> <spring:message code="saved-success" />
					</div>
				</c:if>

				<c:if test="${errors != null}">
					<div class="alert alert-danger" role="alert">
						<a class="panel-close close" data-dismiss="alert">×</a>
						<form:errors path="*" />
					</div>
				</c:if>

				<form:hidden path="contentId" />

				<div class="col-lg-8 col-lg-offset-2">
					<div class="form-group">
						<label for="title"><spring:message code="title" /></label>
						<input id="title" name="title" type="text" class="form-control" value="${content.title}" required autofocus>
					</div>
					<div class="form-group">
						<label for="shortDescription"><spring:message code="short-description" /></label>
						<form:input path="shortDescription" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label for=description><spring:message code="text" /></label>
						<%
						out.println(FckEditorCreator.getFckEditor(request, "description", "100%", "600", "Basic", content.getDescription()));
						%>
					</div>
				</div>
				<div class="col-md-3">
				</div>

				<div class="col-lg-8 col-lg-offset-2">
					<div class="form-group">
						<button type="submit" class="btn btn-primary"><spring:message code="submit" /></button>
					</div>
				</div>
			</form:form>
	</div>

	<footer class="text-center">
		<div class="footer-below">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">${site.footer}</div>
				</div>
			</div>
		</div>
	</footer>

	<script type="text/javascript" src='<c:url value="/template/basic/jquery-1.11.1.min.js" />'></script>
	<script type="text/javascript" src='<c:url value="/template/basic/bootstrap-3.2.0/js/bootstrap.min.js" />'></script>
</body>
</html>