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

<form:form method="post" modelAttribute="user" role="form" enctype="multipart/form-data">
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
			<c:if test="${!user['new']}">
				<div class="form-group">
					<form:hidden path="userId"/>
					<label><spring:message code="id" /></label>
					<p class="form-control-static"><c:out value="${user.userId}" /></p>
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
				<div class="fileinput fileinput-new" data-provides="fileinput">
					<div class="fileinput-new thumbnail" style="width: 100px; height: 100px;">
						<c:choose>
							<c:when test="${user.profilePhoto != null}">
								<img src="${user.profilePhoto}" class="img-responsive" alt="${user.username}" />
							</c:when>
							<c:otherwise>
								<img src='<c:url value="/resources/images/defaultAvatar.png" />' class="img-responsive" alt="avatar" />
							</c:otherwise>
						</c:choose>
					</div>
					<div class="fileinput-preview fileinput-exists thumbnail img-rounded" style="width: 100px; height: 100px;"></div>
					<div>
						<span class="btn btn-sm btn-default btn-file">
							<span class="fileinput-new"><spring:message code="select-image" /></span>
							<span class="fileinput-exists"><spring:message code="change" /></span>
							<input type="file" name="photo" value="">
						</span>
						<a href="#" class="btn btn-sm btn-default fileinput-exists" data-dismiss="fileinput"><spring:message code="delete" /></a>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label"><spring:message code="site" /></label>
				<c:forEach var="site" items="${user.sites}">
					<p class="form-control-static">
						${site.siteName}
					</p>
				</c:forEach>
				<label class="control-label"><spring:message code="select-site" /></label>
				<c:forEach var="site" items="${sites}">
					<div class="checkbox">
						<label>
							<input type="checkbox" name="selectedSiteIds" value="${site.siteId}" />${site.siteName}
						</label>
					</div>
				</c:forEach>
			</div>
			<div class="form-group">
				<label class="control-label"><spring:message code="role" /></label>
				<c:forEach var="authority" items="${user.authorities}">
					<p class="form-control-static">
						${authority.authorityName}
					</p>
				</c:forEach>
				<label class="control-label"><spring:message code="select-role" /></label>
				<div class="radio">
					<label>
						<input type="radio" name="userRole" id="administrator" value="administrator">
						<spring:message code="administrator" />
					</label>
				</div>
				<div class="radio">
					<label>
						<input type="radio" name="userRole" id="superUser" value="superUser">
						<spring:message code="super-user" />
					</label>
				</div>
				<div class="radio">
					<label>
						<input type="radio" name="userRole" id="user" value="user">
						<spring:message code="user" />
					</label>
				</div>
			</div>
			<div class="form-group">
				<label><spring:message code="enabled" /></label>
				<div class="checkbox">
					<label>
						<form:checkbox path="enabled" value="enabled" /><spring:message code="enabled" />
					</label>
				</div>
			</div>
			<c:if test="${user['new']}">
				<input type="submit" value='<spring:message code="save" />' class="btn btn-sm btn-primary" role="button">
			</c:if>
			<c:if test="${!user['new']}">
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