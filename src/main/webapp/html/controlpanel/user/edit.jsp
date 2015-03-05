<%@ include file="/html/common/init.jsp" %>

<script type="text/javascript">
function submitCancel(type) {
	location.href='<spring:url value="/controlpanel/user/list" htmlEscape="true" />';
	return false;
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/user/list" htmlEscape="true" />">User Listing</a></li>
	<li>User Maintenance</li>
</ol>

<form:form method="post" modelAttribute="userEditCommand">
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
		<div class="col-md-6">
			<c:if test="${!userEditCommand['new']}">
				<div class="form-group">
					<form:hidden path="userId"/>
					<label><spring:message code="id" /></label>
					<p class="form-control-static"><c:out value="${userEditCommand.userId}" /></p>
				</div>
			</c:if>
			<div class="form-group">
				<label for="username"><spring:message code="username" /></label>
				<form:input path="username" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="password"><spring:message code="password" /></label>
				<form:password path="password" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="verifyPassword"><spring:message code="verify-password" /></label>
				<form:password path="verifyPassword" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label>User Type</label>
				<c:if test="${userEditCommand.hasSuperUser}">
					<div class="radio">
						<label>
							<form:radiobutton path="userType" value="S" />
							<spring:message code="user.type.S" />
						</label>
					</div>
				</c:if>
				<c:if test="${userEditCommand.hasAdministrator}">
					<div class="radio">
						<label>
							<form:radiobutton path="userType" value="A" />
							<spring:message code="user.type.A" />
						</label>
					</div>
				</c:if>
				<div class="radio">
					<label>
						<form:radiobutton path="userType" value="R" />
						<spring:message code="user.type.R" />
					</label>
				</div>
			</div>
			<div class="checkbox">
				<label>
					<form:checkbox path="active" value="Y" />
					Active
				</label>
			</div>
			<div class="form-group">
				<label for="email"><spring:message code="email" /></label>
				<form:input path="email" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="phone"><spring:message code="phone" /></label>
				<form:input path="phone" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label><spring:message code="address" /></label>
				<form:input path="addressLine1" cssClass="form-control" />
				<form:input path="addressLine2" cssClass="form-control" />
			</div>
		</div>

		<div class="col-md-6">
			<div class="form-group">
				<label>User has access to the following sites</label>
				<form:checkboxes items="${userEditCommand.sites}" path="selectedSiteIds" itemLabel="siteName" itemValue="siteId" cssClass="checkbox" />
			</div>
			<c:if test="${userEditCommand['new']}">
				<input type="submit" value='<spring:message code="save" />' class="btn btn-sm btn-primary" role="button">
			</c:if>
			<c:if test="${!userEditCommand['new']}">
				<input type="submit" value='<spring:message code="update" />' class="btn btn-sm btn-primary" role="button">
			</c:if>
			<input type="button" value='<spring:message code="cancel" />' class="btn btn-sm btn-default" role="button" onclick="return submitCancel();">
		</div>
	</div>
								<%-- <tr>
									<td class="jc_input_label">City</td>
								</tr>
								<tr>
									<td class="jc_input_control">
										<form:input path="userCityName" size="25" cssClass="tableContent" />
										<span class="jc_input_error">
											<form:errors path="userCityName" cssClass="error" />
										</span>
									</td>
								</tr>
								<tr>
									<td class="jc_input_label">State/Province</td>
								</tr>
								<tr>
									<td class="jc_input_control">
										<form:select path="userStateCode" styleClass="tableContent" style="width: 200px">
											<form:options item="states" itemValue="value" itemLabel="label" />
										</form:select>
										<!-- <html:select property="userStateCode" styleClass="tableContent" style="width: 200px">
											<html:optionsCollection property="states" label="label" value="value" />
										</html:select> -->
									</td>
								</tr>
								<tr>
									<td class="jc_input_label">Country</td>
								</tr>
								<tr>
									<td class="jc_input_control">
										<form:select path="userCountryCode" styleClass="tableContent" style="width: 200px">
											<form:options item="countries" itemValue="value" itemLabel="label" />
										</form:select>
										<!-- <html:select property="userCountryCode" styleClass="tableContent" style="width: 200px">
											<html:optionsCollection property="countries" label="label" value="value" />
										</html:select> -->
									</td>
								</tr>
								<tr class="jc_input_label">
									<td>Zip/Postal code</td>
								</tr>
								<tr>
									<td class="jc_input_control">
										<form:input path="userZipCode" size="10" cssClass="tableContent" />
										<span class="jc_input_error">
											<form:errors path="userZipCode" cssClass="error" />
										</span>
									</td>
								</tr> --%>
</form:form>