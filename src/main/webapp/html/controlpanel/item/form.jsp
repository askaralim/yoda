<%@ include file="/html/common/init.jsp" %>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/item" />">Item Listing</a></li>
	<li>Item Maintenance</li>
</ol>

<%-- <c:choose>
	<c:when test="${owner['new']}">
		<c:set var="method" value="post" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
	</c:otherwise>
</c:choose> --%>

<h2>
	<c:if test="${item['new']}"><spring:message code="new" /></c:if>
	<c:out value="${item.name}" />
</h2>

<form:form method="post" modelAttribute="item" name="fm">
<%-- 	<div class="form-group">
		<label><spring:message code="content" /></label>

		<spring:url value="/controlpanel/content/{contentId}/edit" var="editContentUrl">
			<spring:param name="contentId" value="${item.content.contentId}"/>
		</spring:url>

		<p class="form-control-static">
			<a href="${fn:escapeXml(editContentUrl)}">
				<c:out value="${item.content.title}" />
			</a>
		</p>
	</div> --%>

	<form:hidden path="id" />
	<%-- <form:hidden path="content" /> --%>

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
				<label for="name"><spring:message code="name" /></label>
				<form:input path="name" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="brand"><spring:message code="brand" /></label>
				<select id="brandId" name="brandId" class="form-control">
					<option value="" />
					<c:forEach var="brand" items="${brands}">
						<option value="${brand.brandId}" ${item.brand.brandId == brand.brandId ? "selected='selected'" : ""}>${brand.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="category"><spring:message code="category" /></label>
				<select id="categoryId" name="categoryId" class="form-control">
					<option value="" />
					<c:forEach var="category" items="${categories}">
						<option value="${category.categoryId}" ${item.categoryId == category.categoryId ? "selected='selected'" : ""}>${category.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="content"><spring:message code="content" /></label>
				<select id="contentId" name="contentId" class="form-control">
					<option value="" />
					<c:forEach var="content" items="${contents}">
						<option value="${content.contentId}" ${item.contentId == content.contentId ? "selected='selected'" : ""}>${content.title}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="price"><spring:message code="price" /></label>
				<form:input path="price" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="level"><spring:message code="level" /></label>
				<form:select path="level" cssClass="form-control">
					<form:option value="low" />
					<form:option value="medium" />
					<form:option value="high" />
				</form:select>
			</div>
			<div class="form-group">
				<label for="description"><spring:message code="description" /></label>
				<form:textarea path="description" cssClass="form-control" />
			</div>
			<div class="form-actions">
				<c:choose>
					<c:when test="${item['new']}">
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
			<c:if test="${!item['new']}">
				<div class="panel panel-default">
					<div class="panel-heading">
						Images
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="control-label col-lg-4">Image Upload</label>
							<div class="col-lg-8">
								<c:choose>
									<c:when test="${empty item.imagePath}">
									</c:when>
									<c:otherwise>
										<div class="thumbnail" style="max-width: 200px; max-height: 200px; line-height: 20px;">
											<img src="${item.imagePath}" alt="..">
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
						<spring:message code="details" />
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label for="createBy"><spring:message code="create-by" /></label>
							<p class="form-control-static"><c:out value="${item.createBy}" /></p>
						</div>
						<div class="form-group">
							<label for="createDate"><spring:message code="create-date" /></label>
							<p class="form-control-static"><c:out value="${item.createDate}" /></p>
						</div>
						<div class="form-group">
							<label for="updateBy"><spring:message code="update-by" /></label>
							<p class="form-control-static"><c:out value="${item.updateBy}" /></p>
						</div>
						<div class="form-group">
							<label for="updateDate"><spring:message code="update-date" /></label>
							<p class="form-control-static"><c:out value="${item.updateDate}" /></p>
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
				<form class="form-horizontal" role="form" action='<spring:url value="/controlpanel/item/${item.id}/uploadImage?${_csrf.parameterName}=${_csrf.token}"/>' method="post" enctype="multipart/form-data">
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
function submitBackForm(type) {
	location.href='<spring:url value="/controlpanel/item/list" />';
	return false;
}
</script>