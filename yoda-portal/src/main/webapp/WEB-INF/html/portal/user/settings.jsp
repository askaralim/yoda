<%@ include file="../../common/init.jsp"%>

<div class="container pt">
	<!-- <h3>Edit Profile</h3>
	<hr> -->
	<div class="row">
		<!-- left column -->
		<!-- <div class="col-lg-2">
			<div class="text-center">
				<img src="//placehold.it/100" class="avatar img-circle" alt="avatar">
				<h6>Upload a different photo...</h6>

				<input type="file" class="form-control">
			</div>
		</div> -->

		<!-- edit form column -->
		<div class="col-lg-8 col-lg-offset-2">
			<form:form method="post" modelAttribute="user" cssClass="form-horizontal" role="form" enctype="multipart/form-data">
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

				<form:hidden path="userId" />

				<div class="nav-tabs-custom">
					<ul class="nav nav-tabs">
						<li class='${tab == "basic" ? "active" : ""}'>
							<a href="#tab-basic" data-toggle="tab"><spring:message code="basic" /></a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab-basic">
							<div class="form-group">
								<label class="col-md-2 control-label"></label>
								<div class="col-md-8">
									<div class="fileinput fileinput-new" data-provides="fileinput">
										<div class="fileinput-new thumbnail" style="width: 100px; height: 100px;">
											<%-- <c:if test="${success != null}">
												<img src='<c:url value="/resources/images/defaultAvatar.png" />' class="img-responsive" alt="avatar" />
											</c:if> --%>
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
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label"><spring:message code="username" /></label>
								<div class="col-md-8">
									<form:input path="username" cssClass="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label"><spring:message code="email" /></label>
								<div class="col-md-8">
									<form:input path="email" cssClass="form-control" readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label"><spring:message code="password" /></label>
								<div class="col-md-8">
									<form:password path="password" cssClass="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label"><spring:message code="verify-password" /></label>
								<div class="col-md-8">
									<form:password path="verifyPassword" cssClass="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label"></label>
								<div class="col-md-8">
									<input type="submit" class="btn btn-primary" value='<spring:message code="save" />'>
									<span></span>
									<%--
									<input type="reset" class="btn btn-primary" value="<spring:message code="cancel" />">
									--%>
								</div>
							</div>
								<%-- <a class="btn btn-sm btn-primary" href="javascript:submitForm('password');" role="button"><spring:message code="update" /></a> --%>

							<%-- <div class="tab-pane" id="tab-profile">
								<div class="form-group">
									<label for="userName"><spring:message code="username" /></label>
									<form:input path="userName" cssClass="form-control" />
								</div>
								<a class="btn btn-sm btn-primary" href="javascript:submitForm('update');" role="button"><spring:message code="update" /></a>
							</div> --%>
						</div>
					</div>
				</div>
			</form:form>
		</div>
		<!-- Custom Tabs -->
	</div>
</div>