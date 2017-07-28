<%@ include file="/html/common/init.jsp" %>

<%@ page language="java" import="com.yoda.fckeditor.FckEditorCreator"%>

<jsp:useBean id="contentBrand" type="com.yoda.content.model.ContentBrand" scope="request" />

<script src='<c:url value="/FCKeditor/fckeditor.js" />'></script>


<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<spring:url value="/controlpanel/content/{contentId}/edit" var="editContentUrl">
		<spring:param name="contentId" value="${contentBrand.contentId}"/>
	</spring:url>
	<li><a href="${fn:escapeXml(editContentUrl)}"><spring:message code="content" /></a></li>
	<li>Content Brand Maintenance</li>
</ol>

<h2>
	<c:choose>
		<c:when test="${contentBrand['new']}">
			<spring:message code="new" />
		</c:when>
		<c:otherwise>
			<spring:message code="edit" />
		</c:otherwise>
	</c:choose>
</h2>

<form:form method="post" modelAttribute="contentBrand" name="fm">
	<form:hidden path="contentBrandId" />
	<form:hidden path="contentId" />

	<c:if test="${success != null}">
		<div class="alert alert-success" role="alert">
			<a class="panel-close close" data-dismiss="alert">×</a>
			<i class="glyphicon glyphicon-ok"></i><spring:message code="saved-success" />
		</div>
	</c:if>

	<c:if test="${errors != null}">
		<div class="alert alert-danger" role="alert">
			<a class="panel-close close" data-dismiss="alert">×</a>
			<form:errors path="*" />
		</div>
	</c:if>

	<div class="row">
		<div class="col-md-8">
			<div class="form-group">
				<label for="brand"><spring:message code="brand" /></label>
				<select id="brandId" name="brandId" class="form-control">
					<option value="" />
					<c:forEach var="brand" items="${brands}">
						<option value="${brand.brandId}" ${contentBrand.brandId == brand.brandId ? "selected='selected'" : ""}>${brand.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="description"><spring:message code="description" /></label>
				<%
					out.println(FckEditorCreator.getFckEditor(request, "description", "100%", "300", "Basic", contentBrand.getDescription()));
				%>
			</div>
			<div class="form-actions">
				<c:choose>
					<c:when test="${contentBrand['new']}">
						<button class="btn btn-sm btn-primary" type="submit"><spring:message code="save" /></button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-sm btn-primary" type="submit"><spring:message code="update" /></button>
					</c:otherwise>
				</c:choose>
				<input type="button" value='<spring:message code="cancel" />' class="btn btn-sm btn-default" role="button" onclick="return submitBackForm();">
			</div>
		</div>
	</div>
</form:form>

<script type="text/javascript">
function submitBackForm(type) {
	location.href='<spring:url value="/controlpanel/content/list" />';
	return false;
}
</script>