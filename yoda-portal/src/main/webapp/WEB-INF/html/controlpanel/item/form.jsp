<%@ include file="../../common/init.jsp"%>

<jsp:useBean id="item" type="com.taklip.yoda.model.Item" scope="request" />

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
	<form:hidden path="id" />
	<form:hidden path="siteId" />

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
				<form:input path="name" cssClass="form-control input-sm" />
			</div>
			<div class="form-group">
				<label for="brand"><spring:message code="brand" /></label>
				<select id="brandId" name="brandId" class="form-control input-sm">
					<option value="" />
					<c:forEach var="brand" items="${brands}">
						<option value="${brand.brandId}" ${item.brand.brandId == brand.brandId ? "selected='selected'" : ""}>${brand.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="category"><spring:message code="category" /></label>
				<select id="categoryId" name="categoryId" class="form-control input-sm">
					<option value="" />
					<c:forEach var="category" items="${categories}">
						<option value="${category.categoryId}" ${item.categoryId == category.categoryId ? "selected='selected'" : ""}>${category.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="content"><spring:message code="content" /></label>
				<select id="contentId" name="contentId" class="form-control input-sm">
					<option value="" />
					<c:forEach var="content" items="${contents}">
						<option value="${content.contentId}" ${item.contentId == content.contentId ? "selected='selected'" : ""}>${content.title}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="price"><spring:message code="price" /></label>
				<form:input path="price" cssClass="form-control input-sm" />
			</div>
			<div class="form-group">
				<label for="level"><spring:message code="level" /></label>
				<form:select path="level" cssClass="form-control input-sm">
					<form:option value="low" />
					<form:option value="medium" />
					<form:option value="high" />
				</form:select>
			</div>
			<div id="input_fields_wrap">
				<label for="add-extra-field"><spring:message code="add-extra-field" /></label>
				<c:forEach var="extraField" items="${extraFields}" varStatus="index" step="1">
					<div class="form-group">
						<div class="row">
							<div class="col-xs-3">
								<div class="input-group">
									<div class="input-group-addon">Key</div>
									<input id="extraFieldKey" name="extraFieldKey${index.count}" class="form-control input-sm" type="text" value="${extraField.key}">
								</div>
							</div>
							<div class="col-xs-7">
								<div class="input-group">
									<div class="input-group-addon">Value</div>
									<input id="extraFieldValue" name="extraFieldValue${index.count}" class="form-control input-sm" type="text" value="${extraField.value}">
								</div>
							</div>
							<c:if test="${index.count == 1}">
								<div class="col-xs-1">
									<button class="btn btn-default btn-sm" id="addNewField" type="button"><spring:message code="add" /></button>
								</div>
							</c:if>
							<c:if test="${index.count != 1}">
								<div class="col-xs-1">
									<button type="button" class="btn btn-default btn-sm" id="removeField"><span class="glyphicon glyphicon-remove"></span></button>
								</div>
							</c:if>
						</div>
					</div>
				</c:forEach>
				<c:if test="${extraFields.size() == 0}">
					<div class="form-group">
						<div class="row">
							<div class="col-xs-3">
								<div class="input-group">
									<div class="input-group-addon">Key</div>
									<input id="extraFieldKey" name="extraFieldKey1" class="form-control input-sm" type="text">
								</div>
							</div>
							<div class="col-xs-7">
								<div class="input-group">
									<div class="input-group-addon">Value</div>
									<input id="extraFieldValue" name="extraFieldValue1" class="form-control input-sm" type="text">
								</div>
							</div>
							<div class="col-xs-1">
								<button class="btn btn-default btn-sm" id="addNewField" type="button"><spring:message code="add" /></button>
							</div>
						</div>
					</div>
				</c:if>
			</div>
			<div class="form-group">
				<label for="description"><spring:message code="description" /></label>
				<form:textarea path="description" cssClass="form-control" value="" display="none" style="display: none"/>
				<div id="editor" style="width: 100%;height: 300px;">
				</div>
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
						Buy Links
					</div>
					<div class="panel-body">
						<div id="buy_links_wrap">
							<label for="add-buy-link"><spring:message code="add-buy-link" /></label>
							<c:forEach var="buyLink" items="${buyLinks}" varStatus="index" step="1">
								<div class="form-group">
									<div class="row">
										<div class="col-xs-5">
											<div class="input-group">
												<div class="input-group-addon">Key</div>
												<input id="buyLinkKey" name="buyLinkKey${index.count}" class="form-control input-sm" type="text" value="${buyLink.key}">
											</div>
										</div>
										<div class="col-xs-5">
											<div class="input-group">
												<div class="input-group-addon">Value</div>
												<input id="buyLinkValue" name="buyLinkValue${index.count}" class="form-control input-sm" type="text" value="${buyLink.value}">
											</div>
										</div>
										<c:if test="${index.count == 1}">
											<div class="col-xs-1">
												<button class="btn btn-default btn-sm" id="addNewBuyLink" type="button"><spring:message code="add" /></button>
											</div>
										</c:if>
										<c:if test="${index.count != 1}">
											<div class="col-xs-1">
												<button type="button" class="btn btn-default btn-sm" id="removeBuyLink"><span class="glyphicon glyphicon-remove"></span></button>
											</div>
										</c:if>
									</div>
								</div>
							</c:forEach>
							<c:if test="${buyLinks.size() == 0}">
								<div class="form-group">
									<div class="row">
										<div class="col-xs-5">
											<div class="input-group">
												<div class="input-group-addon">Key</div>
												<input id="buyLinkKey" name="buyLinkKey1" class="form-control input-sm" type="text">
											</div>
										</div>
										<div class="col-xs-5">
											<div class="input-group">
												<div class="input-group-addon">Value</div>
												<input id="buyLinkValue" name="buyLinkValue1" class="form-control input-sm" type="text">
											</div>
										</div>
										<div class="col-xs-1">
											<button class="btn btn-default btn-sm" id="addNewBuyLink" type="button"><spring:message code="add" /></button>
										</div>
									</div>
								</div>
							</c:if>
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
							<p class="form-control-static"><c:out value="${item.createBy.username}" /></p>
						</div>
						<div class="form-group">
							<label for="createDate"><spring:message code="create-date" /></label>
							<p class="form-control-static"><c:out value="${item.createDate}" /></p>
						</div>
						<div class="form-group">
							<label for="updateBy"><spring:message code="update-by" /></label>
							<p class="form-control-static"><c:out value="${item.updateBy.username}" /></p>
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
contentId = '${id}';
$(function() {
	yoda.wangEditor.init({
		container : "#editor",
		textareaName : "description",
		/* textareaContent: "${content.description}", */
		uploadUrl : "/api/uploadFile",
		uploadFileName : "file",
		uploadType : "item",
		customCss : {
			"height" : "100%",
			"max-height" : "250px"
		}
	})
});

function submitBackForm(type) {
	location.href='<spring:url value="/controlpanel/item" />';
	return false;
}

$(function() {
	var max_fields = 10;
	var index = $('#input_fields_wrap .form-group').size() + 1;
	var size = $('#input_fields_wrap .form-group').size() + 1;

	$('#addNewField').click(function() {
		if(size < max_fields){
			$('#input_fields_wrap').append(
				'<div class="form-group">'
					+'<div class="row">'
						+'<div class="col-xs-3">'
							+'<div class="input-group">'
								+'<div class="input-group-addon">Key</div>'
								+'<input id="extraFieldKey" name="extraFieldKey' + index + '" class="form-control input-sm" type="text">'
							+'</div>'
						+'</div>'
						+'<div class="col-xs-7">'
							+'<div class="input-group">'
								+'<div class="input-group-addon">Value</div>'
								+'<input id="extraFieldValue" name="extraFieldValue' + index + '" class="form-control input-sm" type="text">'
							+'</div>'
						+'</div>'
						+'<div class="col-xs-1">'
							+ '<button type="button" class="btn btn-default btn-sm" id="removeField"><span class="glyphicon glyphicon-remove"></span></button>'
						+'</div>'
					+'</div>'
				+'</div>'
			);
			index++;
			size++
		}
	});

	$('#input_fields_wrap').on("click","#removeField", function() {
		$(this).parent('div').parent('div').parent('div').remove();

		size--;
	});
});

$(function() {
	var max_fields = 10;
	var index = $('#buy_links_wrap .form-group').size() + 1;
	var size = $('#buy_links_wrap .form-group').size() + 1;

	$('#addNewBuyLink').click(function() {
		if(size < max_fields){
			$('#buy_links_wrap').append(
				'<div class="form-group">'
					+'<div class="row">'
						+'<div class="col-xs-5">'
							+'<div class="input-group">'
								+'<div class="input-group-addon">Key</div>'
								+'<input id="buyLinkKey" name="buyLinkKey' + index + '" class="form-control input-sm" type="text">'
							+'</div>'
						+'</div>'
						+'<div class="col-xs-5">'
							+'<div class="input-group">'
								+'<div class="input-group-addon">Value</div>'
								+'<input id="buyLinkValue" name="buyLinkValue' + index + '" class="form-control input-sm" type="text">'
							+'</div>'
						+'</div>'
						+'<div class="col-xs-1">'
							+ '<button type="button" class="btn btn-default btn-sm" id="removeBuyLink"><span class="glyphicon glyphicon-remove"></span></button>'
						+'</div>'
					+'</div>'
				+'</div>'
			);
			index++;
			size++
		}
	});

	$('#buy_links_wrap').on("click","#removeBuyLink", function() {
		$(this).parent('div').parent('div').parent('div').remove();

		size--;
	});
});
</script>