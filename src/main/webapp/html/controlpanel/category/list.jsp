<%@ include file="/html/common/init.jsp" %>

<script type="text/javascript">
function submitNewForm() {
	var url = '<c:url value="/controlpanel/category/new"/>';
	location.href = url;

	return false;
}

function submitSearch() {
	document.fm.action = '<c:url value="/controlpanel/category/search"/>';
	document.fm.method="POST";
	document.fm.submit();
}

function submitRemove() {
	var ids = getSelectedCategoryIds();

	if(ids){
		var url = '<c:url value="/controlpanel/category/remove"/>?ids='+ids+'';
		location.href = url;
	}

	return false;
}

function getSelectedCategoryIds(){
	var selectBoxs = document.all("ids");

	if(!selectBoxs) return null;

	if(typeof(selectBoxs.length) == "undefined" && selectBoxs.checked){
		return selectBoxs.value;
	}
	else{//many checkbox ,so is a array 
		var ids = "";
		var split = "";
		for(var i = 0 ; i < selectBoxs.length ; i++){
			if(selectBoxs[i].checked){
				ids += split+selectBoxs[i].value;
				split = ",";
			}
		}

		return ids;
	}
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/category" />">Category Listing</a></li>
</ol>

<div class="row">
	<div class="col-md-3">
		<div class="panel panel-default">
			<div class="panel-heading">
				<spring:message code="search" />
			</div>
			<div class="panel-body">
				<form name="fm">
					<div class="form-group">
						<label for="name"><spring:message code="name" /></label>
						<input id="name" name="name" type="text" class="form-control" />
					</div>
					<input type="submit" value='<spring:message code="search" />' class="btn btn-sm btn-primary" role="button" onclick="return submitSearch();">
				</form>
			</div>
		</div>
	</div>
	<div class="col-md-9">
		<div class="page-header">
			<h4>Category Listing Result</h4>
		</div>
		<div class="text-right">
			<input type="submit" value="<spring:message code="new" />" class="btn btn-sm btn-primary" role="button" onclick="return submitNewForm();">
			<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
		</div>
		<div class="table-responsive">
			<table class="table table-striped">
				<c:if test="${categories != null}">
					<thead>
						<tr>
							<th></th>
							<th><spring:message code="id" /></th>
							<th><spring:message code="name" /></th>
							<th><spring:message code="parent" /></th>
							<th><spring:message code="create-date" /></th>
							<th><spring:message code="action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="category" items="${categories}">
							<tr>
								<td>
									<input type="checkbox" id="ids" value="${category.id}">
								</td>
								<td>
									<c:out value="${category.id}" />
								</td>
								<td>
									<c:out value="${category.name}" />
								</td>
								<td>
									<c:out value="${category.parent}" />
								</td>
								<td>
									<c:out value="${category.createDate}" />
								</td>
								<td>
									<spring:url value="/controlpanel/category/{id}/edit" var="editCategoryUrl">
										<spring:param name="id" value="${category.id}"/>
									</spring:url>
									<a href="${fn:escapeXml(editCategoryUrl)}">
										<spring:message code="edit" />
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