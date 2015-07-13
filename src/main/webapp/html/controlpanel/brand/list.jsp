<%@ include file="/html/common/init.jsp" %>

<script type="text/javascript">
function submitNewForm() {
	var url = '<c:url value="/controlpanel/brand/new"/>';
	location.href = url;

	return false;
}

function submitSearch() {
	document.fm.action = '<c:url value="/controlpanel/brand/search"/>';
	document.fm.method="POST";
	document.fm.submit();
}

function submitRemove() {
	var ids = getSelectedBrandIds();

	if(ids){
		var url = '<c:url value="/controlpanel/brand/remove"/>?brandIds='+brandIds+'';
		location.href = url;
	}

	return false;
}

function getSelectedBrandIds(){
	var selectBoxs = document.all("brandIds");

	if(!selectBoxs) return null;

	if(typeof(selectBoxs.length) == "undefined" && selectBoxs.checked){
		return selectBoxs.value;
	}
	else{//many checkbox ,so is a array 
		var brandIds = "";
		var split = "";
		for(var i = 0 ; i < selectBoxs.length ; i++){
			if(selectBoxs[i].checked){
				brandIds += split+selectBoxs[i].value;
				split = ",";
			}
		}

		return brandIds;
	}
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/brand" />">Brand Listing</a></li>
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
			<h4>Brand Listing Result</h4>
		</div>
		<div class="text-right">
			<input type="submit" value="<spring:message code="new" />" class="btn btn-sm btn-primary" role="button" onclick="return submitNewForm();">
			<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
		</div>
		<div class="table-responsive">
			<table class="table table-striped">
				<c:if test="${brands != null}">
					<thead>
						<tr>
							<th></th>
							<th><spring:message code="id" /></th>
							<th><spring:message code="name" /></th>
							<th><spring:message code="kind" /></th>
							<th><spring:message code="country" /></th>
							<th><spring:message code="score" /></th>
							<th><spring:message code="action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="brand" items="${brands}">
							<tr>
								<td>
									<input type="checkbox" id="brandIds" value="${brand.brandId}">
								</td>
								<td>
									<c:out value="${brand.brandId}" />
								</td>
								<td>
									<c:out value="${brand.name}" />
								</td>
								<td>
									<c:out value="${brand.kind}" />
								</td>
								<td>
									<c:out value="${brand.country}" />
								</td>
								<td>
									<c:out value="${brand.score}" />
								</td>
								<td>
									<spring:url value="/controlpanel/brand/{id}" var="viewBrandUrl">
										<spring:param name="id" value="${brand.brandId}"/>
									</spring:url>
									<a href="${fn:escapeXml(viewBrandUrl)}">
										<spring:message code="view" />
									</a>
									<spring:url value="/controlpanel/brand/{id}/edit" var="editBrandUrl">
										<spring:param name="id" value="${brand.brandId}"/>
									</spring:url>
									<a href="${fn:escapeXml(editBrandUrl)}">
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