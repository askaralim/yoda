<%@ include file="../../common/init.jsp"%>

<jsp:useBean id="brand" type="com.taklip.yoda.model.Brand" scope="request" />

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/brand" />">Brand Listing</a></li>
	<li>Brand Maintenance</li>
</ol>

<h2>
	<c:if test="${brand['new']}"><spring:message code="new" /></c:if>
	<c:out value="${brand.name}" />
</h2>

<form:form method="post" modelAttribute="brand" name="fm">
	<form:hidden path="brandId" />
	<%-- <form:hidden path="content" /> --%>

	<c:if test="${success != null}">
		<div class="alert alert-success" role="alert">
			<a class="panel-close close" data-dismiss="alert">�</a>
			<i class="glyphicon glyphicon-ok"></i><spring:message code="saved-success" />
		</div>
	</c:if>

	<c:if test="${errors != null}">
		<div class="alert alert-danger" role="alert">
			<a class="panel-close close" data-dismiss="alert">�</a>
			<form:errors path="*" />
		</div>
	</c:if>

	<div class="row">
		<div class="col-md-8">
			<div class="form-group">
				<label for="name"><spring:message code="name" /></label>
				<form:input path="name" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="company"><spring:message code="company" /></label>
				<form:input path="company" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="kind"><spring:message code="kind" /></label>
				<form:input path="kind" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="country"><spring:message code="country" /></label>
				<form:input path="country" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="foundDate"><spring:message code="founded-date" /></label>
				<div class="input-daterange input-group" id="datepicker">
					<form:input path="foundDate" cssClass="form-control" />
				</div>
			</div>
			<div class="form-group">
				<label for="description"><spring:message code="description" /></label>
				<form:textarea path="description" cssClass="form-control" value="" display="none" style="display: none"/>
				<div id="editor" style="width: 100%;height: 300px;">
				</div>
			</div>
			<div class="form-actions">
				<c:choose>
					<c:when test="${brand['new']}">
						<button class="btn btn-sm btn-primary" type="submit"><spring:message code="save" /></button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-sm btn-primary" type="submit"><spring:message code="update" /></button>
					</c:otherwise>
				</c:choose>
				<input type="button" value='<spring:message code="cancel" />' class="btn btn-sm btn-default" role="button" onclick="return submitBackForm();">
			</div>
		</div>
		<div class="col-md-4">
			<c:if test="${!brand['new']}">
				<div class="panel panel-default">
					<div class="panel-heading">
						Images
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="control-label col-lg-4">Image Upload</label>
							<div class="col-lg-8">
								<c:choose>
									<c:when test="${empty brand.imagePath}">
									</c:when>
									<c:otherwise>
										<div class="thumbnail" style="max-width: 200px; max-height: 200px; line-height: 20px;">
											<img src="${brand.imagePath}" alt="..">
										</div>
									</c:otherwise>
								</c:choose>
								<a href="#" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#uploadImageModal">Select image</a>
							</div>
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
				<form class="form-horizontal" role="form" action='<spring:url value="/controlpanel/brand/${brand.id}/uploadImage?${_csrf.parameterName}=${_csrf.token}"/>' method="post" enctype="multipart/form-data">
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
contentId = '${brandId}';
$(function() {
	yoda.wangEditor.init({
		container : "#editor",
		textareaName : "description",
		/* textareaContent: "${content.description}", */
		uploadUrl : "/api/uploadFile",
		uploadFileName : "file",
		uploadType : "brand",
		customCss : {
			"height" : "100%",
			"max-height" : "250px"
		}
	})
});

function submitBackForm(type) {
	location.href = '<spring:url value="/controlpanel/content/list" />';
	return false;
}

$('.input-daterange').datepicker({
	format : "yyyy-mm-dd",
	weekStart : 1,
	language : "zh-CN",
	todayHighlight : true
});
</script>