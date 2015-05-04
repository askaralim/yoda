<%@ include file="/html/common/init.jsp" %>

<script type="text/javascript" language="JavaScript">
function submitRemove() {
	var ids = getSelectedIds();

	if(ids){
		var url = '<c:url value="/controlpanel/feedback/remove"/>?feedbackIds='+ids+'';
		location.href = url;
	}

	return false;
}

function submitSearch() {
	alert('not yet');
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
	<li><a href='<spring:url value="/controlpanel/home" />'>Administration</a></li>
	<li class="active"><a href="<spring:url value="/controlpanel/feedback" />">Feedback Listing</a></li>
</ol>

<form:form modelAttribute="feedback" name="fm">
	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="search" />
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label for="expireDateStart"><spring:message code="create-date" /></label>
						<div class="input-daterange input-group" id="datepicker">
							<input class="form-control" type="text" id="from" />
							<span class="input-group-addon">to</span>
							<input class="form-control" type="text" id="to" />
						</div>
					</div>

					<input type="submit" value='<spring:message code="search" />' class="btn btn-sm btn-primary" role="button" onclick="return submitSearch();">
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<div class="page-header">
				<h4>Content Listing Result</h4>
			</div>
			<div class="text-right">
				<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
			</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th></th>
							<th><spring:message code="id" /></th>
							<th><spring:message code="username" /></th>
							<th><spring:message code="email" /></th>
							<th><spring:message code="phone" /></th>
							<th><spring:message code="create-date" /></th>
							<th><spring:message code="action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="feedback" items="${feedbacks}">
							<tr>
								<td>
									<input type="checkbox" id="ids" value="${feedback.id}">
								</td>
								<td>
									<c:out value="${feedback.id}" />
								</td>
								<td>
									<c:out value="${feedback.username}" />
								</td>
								<td>
									<c:out value="${feedback.email}" />
								</td>
								<td>
									<c:out value="${feedback.phone}" />
								</td>
								<td>
									<c:out value="${feedback.createDate}" />
								</td>
								<td>
									<spring:url value="/controlpanel/feedback/{id}" var="viewFeedbackUrl">
										<spring:param name="id" value="${feedback.id}"/>
									</spring:url>
									<a href="${fn:escapeXml(viewFeedbackUrl)}">
										<spring:message code="view" />
									</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form:form>