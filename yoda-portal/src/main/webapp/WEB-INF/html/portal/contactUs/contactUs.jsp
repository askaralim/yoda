<%@ include file="../../common/init.jsp"%>

<div class="container pt">
	<div class="row mt">
		<div class="col-lg-6 col-lg-offset-3 contactus">
			<h3><spring:message code="contact-us" /></h3>
			<hr>
			<p><spring:message code="please-do-not-resubmit" /></p>
			<form role="form" method="POST" action='<spring:url value="/feedback/add" />'>
				<c:forEach var="error" items="${status.errorMessages}">
					<div class="alert alert-danger" role="alert">
						<b>${error}</b>
						<br>
					</div>
				</c:forEach>
				<c:if test="${successMessage}">
					<div class="alert alert-success" role="alert">
						<b>${successMessage}</b>
					</div>
				</c:if>
				<div class="form-group">
					<input type="text" name="username" class="form-control" id="username" placeholder='<spring:message code="username" />' required>
					<br>
				</div>
				<div class="form-group">
					<input type="email" name="email" class="form-control" id="email" placeholder='<spring:message code="email" />' required>
					<br>
				</div>
				<div class="form-group">
					<input type="phone" name="phone" class="form-control" id="phone" placeholder='<spring:message code="phone" />'>
					<br>
				</div>
				<textarea name="description" class="form-control" rows="6" id="description" placeholder='<spring:message code="content" />'></textarea>
				<br>

				<button type="submit" class="btn btn-primary"><spring:message code="submit" /></button>

				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
		</div>
		<c:forEach var="contactUs" items="${contactUsList}">
			<div class="col-lg-4">
				<h3>${contactUs.name}</h3>
				<h4>${contactUs.description}</h4>
				<c:if test='${contactUs.addressLine1 != ""}'>
					<p>
						${contactUs.addressLine1}<br>
						<c:if test='${contactUs.addressLine2 != ""}'>
							${contactUs.addressLine2}<br>
						</c:if>
						${contactUs.cityName}<br>
						${contactUs.zipCode}<br>
					</p>
				</c:if>
				<c:if test='${contactUs.phone != ""}'>
					<p>
						<span class="glyphicon glyphicon-earphone"></span> <abbr title="Phone">Phone</abbr>: ${contactUs.phone}
					</p>
				</c:if>
				<c:if test='${contactUs.email != ""}'>
					<p>
						<span class="glyphicon glyphicon-envelope"></span> <abbr title="Email">E-mail</abbr>: <a href="mailto:${contactUs.email}">${contactUs.email}</a>
					</p>
				</c:if>
				<c:if test='${contactUs.description != ""}'>
					<p>
						${contactUs.description}
					</p>
				</c:if>
				<ul class="list-unstyled list-inline list-social-icons">
					<li class="tooltip-social facebook-link">
						<a href="#facebook-page" data-toggle="tooltip" data-placement="top" title="Facebook">
							<i class="fa fa-facebook-square fa-2x"></i>
						</a>
					</li>
					<li class="tooltip-social linkedin-link">
						<a href="#linkedin-company-page" data-toggle="tooltip" data-placement="top" title="LinkedIn">
							<i class="fa fa-linkedin-square fa-2x"></i>
						</a>
					</li>
					<li class="tooltip-social twitter-link">
						<a href="#twitter-profile" data-toggle="tooltip" data-placement="top" title="Twitter">
							<i class="fa fa-twitter-square fa-2x"></i>
						</a>
					</li>
					<li class="tooltip-social google-plus-link">
						<a href="#google-plus-page" data-toggle="tooltip" data-placement="top" title="Google+">
							<i class="fa fa-google-plus-square fa-2x"></i>
						</a>
					</li>
				</ul>
			</div>
		</c:forEach>
	</div>
</div>

<!--
<div class="container">
	<div class="social text-center">
		<a href="#" target="_blank"><i class="fa fa-twitter"></i></a>
		<a href="#" target="_blank"><i class="fa fa-facebook"></i></a>
		<a href="#" target="_blank"><i class="fa fa-linkedin"></i></a>
		<a href="#"><i class="fa fa-github" target="_blank"></i></a>
	</div>
</div>
-->