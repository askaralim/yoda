<%@ include file="../../common/init.jsp"%>

<nav class="navbar navbar-default navbar-static-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href='<spring:url value="/" />'><b>taklip</b></a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<form class="navbar-form navbar-left" role="search" action='<spring:url value="/search" />' method="GET">
					<div class="form-group input-group">
						<input type="text" class="form-control" id="q" name="q" placeholder='<spring:message code="search" />'>
						<span class="input-group-btn">
							<button class="button-search btn btn-default" type="submit">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</form>
				<li class="page-scroll"><a href='<spring:url value="/" />'><spring:message code="home" /></a></li>
				<li class="page-scroll"><a href='<spring:url value="/brand" />'><spring:message code="brand" /></a></li>
				<li class="page-scroll"><a href='<spring:url value="/contactus" />'><spring:message code="contact-us" /></a></li>

				<%-- ${horizontalMenuCode} --%>

				<c:choose>
					<c:when test="${userLogin}">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="glyphicon glyphicon-user"></i> ${username} <span class="caret"></span>
							</a>
							<ul class="dropdown-menu" role="menu">
								<c:if test="${roleAdmin}">
									<li><a href='<spring:url value="/controlpanel/home" />'><spring:message code="control-panel" /></a></li>
									<li class="divider"></li>
								</c:if>
								<li><a href='<spring:url value="/user/${userId}" />'><spring:message code="profile" /></a></li>
								<li><a href='<spring:url value="/user/settings" />'><spring:message code="settings" /></a></li>
								<li class="divider"></li>
								<li><a href='<spring:url value="/logout" />'><spring:message code="logout" /></a></li>
							</ul>
						</li>
					</c:when>
					<c:otherwise>
						<li class="page-scroll"><a href='<spring:url value="/login" />'><spring:message code="login" /></a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</nav>