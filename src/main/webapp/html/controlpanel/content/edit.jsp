<%@ include file="/html/common/init.jsp" %>

<%@ page language="java" import="com.yoda.fckeditor.FckEditorCreator"%>

<jsp:useBean id="contentEditCommand" type="com.yoda.content.ContentEditCommand" scope="request" />

<script src='<c:url value="/FCKeditor/fckeditor.js" />'></script>

<!--  tab panel -->
<script type="text/javascript">
function submitDefaultImage(input) {
	document.forms[0].process.value = "defaultImage";
	document.forms[0].createDefaultImageId.value = input;
}

function submitBackForm(type) {
	location.href='<spring:url value="/controlpanel/content/list" />';
	return false;
}

function removeContent() {
	document.fm.action= '<spring:url value="/controlpanel/content/remove" />';
	document.fm.submit();
	return false;
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/content/list" />">Content Listing</a></li>
	<li>Content Maintenance</li>
</ol>

<form:form method="post" modelAttribute="contentEditCommand" name="fm">
	<form:hidden path="contentId" />

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

	<div class="row">
		<div class="col-md-8">
			<div class="form-group">
				<label for="title"><spring:message code="title" /></label>
				<form:input path="title" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="shortDescription"><spring:message code="short-description" /></label>
				<form:input path="shortDescription" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for=description><spring:message code="text" /></label>
				<%
				out.println(FckEditorCreator.getFckEditor(request, "description", "100%", "300", "Basic", contentEditCommand.getDescription()));
				%>
			</div>
			<div class="form-group">
				<label for="pageTitle"><spring:message code="keywords" /></label>
				<form:textarea path="pageTitle" cssClass="form-control" />
			</div>
			<c:if test="${contentEditCommand['new']}">
				<input type="submit" value='<spring:message code="save" />' class="btn btn-sm btn-primary" role="button">
			</c:if>
			<c:if test="${!contentEditCommand['new']}">
				<input type="submit" value='<spring:message code="update" />' class="btn btn-sm btn-primary" role="button">
			</c:if>
			<input type="button" value='<spring:message code="cancel" />' class="btn btn-sm btn-default" role="button" onclick="return submitBackForm();">
		</div>
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					General
				</div>
				<div class="panel-body">
					<c:if test="${!contentEditCommand['new']}">
						<div class="row">
							<div class="col-md-12">
								<label for="password">Hit Counter</label>
								<div class="input-group">
									<p id="hitCounter" class="form-control"><c:out value="${contentEditCommand.hitCounter}" /></p>
									<span class="input-group-btn">
										<input type="button" value='Reset Counter' class="btn btn-default" role="button" onclick="return resetCounter();">
									</span>
								</div>
							</div>
						</div>
					</c:if>
					<div class="form-group">
						<label for="publishDate"><spring:message code="publish-date" /></label>
						<form:input path="publishDate" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label for="expireDate"><spring:message code="expire-date" /></label>
						<form:input path="expireDate" cssClass="form-control" />
					</div>
					<label class="checkbox-inline">
						<form:checkbox path="homePage" />
						Show in Home Page
					</label>
					<label class="checkbox-inline">
						<form:checkbox path="published" />
						Published
					</label>
				</div>
			</div>
			<c:if test="${!contentEditCommand['new']}">
				<div class="panel panel-default">
					<div class="panel-heading">
						Images
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="control-label col-lg-4">Image Upload</label>
							<div class="col-lg-8">
								<c:choose>
									<c:when test="${empty contentEditCommand.imagePath}">
									</c:when>
									<c:otherwise>
										<div class="thumbnail" style="max-width: 200px; max-height: 150px; line-height: 20px;">
											<img src="${contentEditCommand.imagePath}" alt="..">
										</div>
									</c:otherwise>
								</c:choose>
								<a href="#" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#uploadImageModal">Select image</a>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">
						<spring:message code="items" />
					</div>
					<div class="panel-body">
						<spring:url value="/controlpanel/{contentId}/items/new" var="addUrl">
							<spring:param name="contentId" value="${contentEditCommand.contentId}" />
						</spring:url>
						<a href="${fn:escapeXml(addUrl)}" class="btn btn-sm btn-primary">Add New Item</a>
					</div>
					<table class="table">
						<c:if test="${contentEditCommand.items != null}">
							<thead>
								<tr>
									<th></th>
									<th><spring:message code="id" /></th>
									<th><spring:message code="name" /></th>
									<th><spring:message code="brand" /></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${contentEditCommand.items}">
									<spring:url value="/controlpanel/content/{contentId}/items/{itemId}edit" var="editItemUrl">
										<spring:param name="contentId" value="${contentEditCommand.contentId}"/>
										<spring:param name="itemId" value="${item.id}"/>
									</spring:url>
									<tr>
										<td>
											<input type="checkbox" id="itemIds" value="${item.id}">
										</td>
										<td>
											<c:out value="${item.id}" />
										</td>
										<td>
											<c:out value="${item.name}" />
										</td>
										<td>
											<c:out value="${item.brand}" />
										</td>
										<td>
											<a href="${fn:escapeXml(editItemUrl)}">
												<spring:message code="edit" />
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</c:if>
					</table>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">
						<spring:message code="details" />
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label for="createBy"><spring:message code="create-by" /></label>
							<p class="form-control-static"><c:out value="${contentEditCommand.createBy}" /></p>
						</div>
						<div class="form-group">
							<label for="createDate"><spring:message code="create-date" /></label>
							<p class="form-control-static"><c:out value="${contentEditCommand.createDate}" /></p>
						</div>
						<div class="form-group">
							<label for="updateBy"><spring:message code="update-by" /></label>
							<p class="form-control-static"><c:out value="${contentEditCommand.updateBy}" /></p>
						</div>
						<div class="form-group">
							<label for="updateDate"><spring:message code="update-date" /></label>
							<p class="form-control-static"><c:out value="${contentEditCommand.updateDate}" /></p>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</form:form>

<div class="col-lg-12">
	<div class="modal fade" id="uploadImageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="H2">Upload Image</h4>
				</div>
				<form class="form-horizontal" role="form" action='<spring:url value="/controlpanel/content/${contentEditCommand.contentId}/edit/uploadImage?${_csrf.parameterName}=${_csrf.token}"/>' method="post" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label class="control-label col-lg-4">Image</label>
							<div class="col-lg-8">
								<input type="file" name="file" />
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="uploadImage" class="btn btn-primary">Save changes</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script src='<c:url value="/resources/js/jquery-1.11.1.min.js" />'></script>
<script src='<c:url value="/resources/js/datepicker/bootstrap-datepicker.js" />'></script>

<script type="text/javascript">
$('#publishDate').datepicker({
	format : "yyyy-mm-dd",
	weekStart : 1,
	todayHighlight : true
});

$('#expireDate').datepicker({
	format : "yyyy-mm-dd",
	weekStart : 1,
	todayHighlight : true
});


function resetCounter() {
	$.ajax({
		type: "POST",
		url: '<spring:url value="/controlpanel/content/${contentEditCommand.contentId}/edit/resetCounter"/>',
		data:'${_csrf.parameterName}=${_csrf.token}',
		success: function(data){
			$('#hitCounter').empty();
			$('#hitCounter').html(0);
		}
	});
}
</script>