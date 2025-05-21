<%@ include file="../../common/init.jsp"%>

<script type="text/javascript">
function prepareIndex() {
	location.href = '<c:url value="/controlpanel/elasticsearch/prepareindex"/>';

	return false;
}

function deleteIndex() {
	location.href = '<c:url value="/controlpanel/elasticsearch/deleteindex"/>';

	return false;
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/elasticsearch" />">Elastic Search Configuration</a></li>
</ol>

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
	<div class="col-md-3">
	</div>
	<div class="col-md-9">
		<div class="page-header">
			<h4>Indexes</h4>
		</div>
		<div class="text-right">
			<input type="submit" value="<spring:message code="prepare-index" />" class="btn btn-sm btn-primary" role="button" onclick="return prepareIndex();">
			<input type="submit" value="<spring:message code="delete-index" />" class="btn btn-sm btn-default" role="button" onclick="return deleteIndex();">
		</div>
		<div class="table-responsive">
			<table class="table table-striped">
				<c:if test="${types != null}">
					<thead>
						<tr>
							<th><input type="checkbox" id="all"></th>
							<th><spring:message code="type" /></th>
							<th><spring:message code="action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="type" items="${types}">
							<tr>
								<td>
									<input type="checkbox" id="ids" value="${type}">
								</td>
								<td>
									<spring:message code="${type}" />
								</td>
								<td>
									<spring:url value="/controlpanel/elasticsearch/reindex/{type}" var="reindexUrl">
										<spring:param name="type" value="${type}"/>
									</spring:url>
									<a href="${fn:escapeXml(reindexUrl)}">
										<spring:message code="reindex" />
									</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:if>
			</table>
		</div>
	</div>
</div>