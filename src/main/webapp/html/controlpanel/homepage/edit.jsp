<%@ include file="/html/common/init.jsp"%>

<script type="text/javascript">
function submitFormResequence() {
	document.fm.action = '<c:url value="/controlpanel/homepage/resequence"/>';
	document.fm.submit();
	return true;
}

function submitFormMakeFeature() {
	document.fm.action = '<c:url value="/controlpanel/homepage/makefeature"/>';
	document.fm.submit();
	return true;
}

function submitForm() {
	document.fm.action = '<c:url value="/controlpanel/homepage/save"/>';
	document.fm.submit();
	return false;
}
</script>

<jsp:useBean id="homePageEditCommand" type="com.yoda.homepage.HomePageEditCommand" scope="request" />

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/homepage" />">Home Page Maintenance</a></li>
</ol>

<form:form modelAttribute="homePageEditCommand" method="post" name="fm">
	<div class="text-right">
		<input type="submit" value="Save" class="btn btn-sm btn-primary"role="button" onclick="return submitForm();">
	</div>

	<div class="form-group">
		<label for="pageTitle">HTML title text for home page</label>
		<form:input path="pageTitle" cssClass="form-control" />
	</div>

	<div class="col-md-12">
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Type</th>
						<th><a href="javascript:submitFormResequence()" class="jc_submit_link">Resequence</a></th>
						<th>Description</th>
						<th>Section</th>
						<th><a href="javascript:submitFormMakeFeature()" class="jc_submit_link">Set Feature</a></th>
						<th>Publish</th>
						<th>Publish On</th>
						<th>Expire On</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="homePage" items="${homePageEditCommand.homePages}">
						<input type="hidden" name="homePageIds" value="<c:out value="${homePage.homePageId}"/>">
						<tr>
							<td>
								<c:out value="${homePage.dataType}" />
							</td>
							<td>
								<input type="text" name="seqNums" value="<c:out value="${homePage.seqNum}"/>" size="2">
							</td>
							<td>
								<c:out value="${homePage.description}" />
							</td>
							<td>
								<c:out value="${homePage.sectionName}" />
							</td>
							<td>
								<%-- <layout:radio layout="false" name="homePage" property="featureData" mode="E,E,E" value="${homePage.homePageId}" /> --%>
								<form:radiobutton path="featureData" value="${homePage.homePageId}"/>
							</td>
							<td>
								<c:out value="${homePage.published}" />
							</td>
							<td>
								<c:out value="${homePage.dataPublishOn}" />
							</td>
							<td>
								<c:out value="${homePage.dataExpireOn}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</form:form>

<script src='<c:url value="/resources/js/jquery-1.11.1.min.js" />'></script>