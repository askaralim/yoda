<%@ include file="/html/common/init.jsp" %>

<script type="text/javascript">
function submitSearch() {
	alert('not yet');
	return false;
	/* document.fm.action = '<c:url value="/controlpanel/comments/search"/>';
	document.fm.method="POST";
	document.fm.submit(); */
}

function submitRemove() {
	var ids = getSelectedIds();

	if(ids){
		var url = '<c:url value="/controlpanel/comments/remove"/>?ids='+ids+'';
		location.href = url;
	}

	return false;
}

function getSelectedIds(){
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
	<li><a href="<spring:url value="/controlpanel/comments" />">Comment Listing</a></li>
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
						<label for="name"><spring:message code="content" /></label>
						<input id="name" name="name" type="text" class="form-control" />
					</div>
					<input type="submit" value='<spring:message code="search" />' class="btn btn-sm btn-primary" role="button" onclick="return submitSearch();">
				</form>
			</div>
		</div>
	</div>
	<div class="col-md-9">
		<div class="page-header">
			<h4>Comment Listing Result</h4>
		</div>
		<div class="text-right">
			<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
		</div>
		<div class="table-responsive">
			<table class="table table-striped">
				<c:if test="${comments != null}">
					<thead>
						<tr>
							<th></th>
							<th><spring:message code="id" /></th>
							<th><spring:message code="content" /></th>
							<th><spring:message code="create-date" /></th>
							<th><spring:message code="action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="comment" items="${comments}">
							<tr>
								<td>
									<input type="checkbox" id="ids" value="${comment.id}">
								</td>
								<td>
									<c:out value="${comment.id}" />
								</td>
								<td>
									<c:out value="${comment.content.title}" />
								</td>
								<td>
									<c:out value="${comment.createDate}" />
								</td>
								<td>
									<spring:url value="/controlpanel/comments/{id}" var="viewCommentUrl">
										<spring:param name="id" value="${comment.id}"/>
									</spring:url>
									<a href="${fn:escapeXml(viewCommentUrl)}">
										<spring:message code="view" />
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