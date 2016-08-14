<%@ include file="/html/common/init.jsp" %>

<script type="text/javascript">
function submitCancel() {
	location.href='<spring:url value="/controlpanel/category/list" htmlEscape="true" />';

	return false;
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/category" />">Category Listing</a></li>
	<li>Category Maintenance</li>
</ol>

<h2>
	<c:if test="${category['new']}"><spring:message code="new" /></c:if>
	<c:out value="${category.name}" />
</h2>

<form:form method="post" modelAttribute="category" name="fm">
	<form:hidden path="categoryId" />

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
				<label for="parent"><spring:message code="parent" /></label>
				<form:select path="parent" cssClass="form-control">
					<form:option value="" />
					<c:forEach var="cate" items="${categories}">
						<form:option value="${cate.categoryId}" label="${cate.name}" />
					</c:forEach>
				</form:select>
			</div>
			<div class="form-group">
				<label for="description"><spring:message code="description" /></label>
				<form:textarea path="description" cssClass="form-control" />
			</div>
			<div class="form-actions">
				<c:choose>
					<c:when test="${category['new']}">
						<button class="btn btn-sm btn-primary" type="submit"><spring:message code="save" /></button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-sm btn-primary" type="submit"><spring:message code="update" /></button>
					</c:otherwise>
				</c:choose>
				<input type="button" value='<spring:message code="cancel" />' class="btn btn-sm btn-default" role="button" onclick="return submitCancel();">
			</div>
		</div>
		<div class="col-md-4">
			<c:if test="${!category['new']}">
				<div class="panel panel-default">
					<div class="panel-heading">
						<spring:message code="details" />
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label for="createBy"><spring:message code="create-by" /></label>
							<p class="form-control-static"><c:out value="${category.createBy.userId}" /></p>
						</div>
						<div class="form-group">
							<label for="createDate"><spring:message code="create-date" /></label>
							<p class="form-control-static"><fmt:formatDate value="${category.createDate}" pattern="yyyy-MM-dd" /></p>
						</div>
						<div class="form-group">
							<label for="updateBy"><spring:message code="update-by" /></label>
							<p class="form-control-static"><c:out value="${category.updateBy.userId}" /></p>
						</div>
						<div class="form-group">
							<label for="updateDate"><spring:message code="update-date" /></label>
							<p class="form-control-static"><fmt:formatDate value="${category.updateDate}" pattern="yyyy-MM-dd" /></p>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</form:form>