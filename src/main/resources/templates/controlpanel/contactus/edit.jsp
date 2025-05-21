<%@ include file="../../common/init.jsp"%>

<jsp:useBean id="contactUsEditCommand" type="com.taklip.yoda.vo.ContactUsEditCommand" scope="request" />

<script type="text/javascript">
/* function submitForm(type) {
	document.forms[0].process.value = type;
	document.forms[0].submit();
	return false;
} */

function submitCancel() {
	location.href='<spring:url value="/controlpanel/contactus/list" htmlEscape="true" />';
	return false;
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/contactus/list" htmlEscape="true" />">Contact Us Listing</a></li>
	<li>Contact Us Maintenance</li>
</ol>

<div class="col-md-6">
	<form:form method="post" modelAttribute="contactUsEditCommand">
		<form:hidden path="contactUsId"/>

		<!-- <html:hidden property="mode" /> -->
		<!-- <html:hidden property="process" value="" /> -->
		<div class="row">
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

			<div class="form-group">
				<label for="contactUsName">Contact Us Name</label>
				<form:input path="contactUsName" cssClass="form-control" />
			</div>
			<div class="checkbox">
				<label>
					<form:checkbox path="active" value="Y" />
					Published
				</label>
			</div>
			<div class="form-group">
				<label for="contactUsEmail"><spring:message code="email" /></label>
				<form:input path="contactUsEmail" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="contactUsPhone"><spring:message code="phone" /></label>
				<form:input path="contactUsPhone" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="contactUsAddressLine1"><spring:message code="address" /></label>
				<form:input path="contactUsAddressLine1" cssClass="form-control" />
				<form:input path="contactUsAddressLine2" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="contactUsCityName"><spring:message code="city" /></label>
				<form:input path="contactUsCityName" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="contactUsZipCode">Zip/Postal code</label>
				<form:input path="contactUsZipCode" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="contactUsDesc"><spring:message code="description" /></label>
				<form:input path="contactUsDesc" cssClass="form-control" />
			</div>

			<c:if test="${contactUsEditCommand['new']}">
				<button class="btn btn-sm btn-primary" type="submit"><spring:message code="save" /></button>
			</c:if>
			<c:if test="${!contactUsEditCommand['new']}">
				<button class="btn btn-sm btn-primary" type="submit"><spring:message code="update" /></button>
			</c:if>
			<input type="button" value='<spring:message code="cancel" />' class="btn btn-sm btn-default" role="button" onclick="return submitCancel();">
		</div>

	<%-- <tr>
		<td class="jc_input_label">State/Province</td>
	</tr>
	<tr>
		<td class="jc_input_control">
			<form:select path="contactUsStateCode" cssClass="tableContent" cssStyle="width: 200px">
				<form:options items="${contactUsEditCommand.states}" itemLabel="stateName" itemValue="stateCode" />
			</form:select>
		</td>
	</tr>
	<tr>
		<td class="jc_input_label">Country</td>
	</tr>
	<tr>
		<td class="jc_input_control">
			<form:select path="contactUsCountryCode" cssClass="tableContent" cssStyle="width: 200px">
				<form:options items="${contactUsEditCommand.countries}" itemLabel="countryName" itemValue="countryCode" />
			</form:select>
		</td>
	</tr> --%>
	</form:form>
</div>