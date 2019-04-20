<%@ include file="../../common/init.jsp"%>

<div class="section">
	<div class="container">
		<div class="col-lg-8 col-lg-offset-2">
			<div class="alert alert-danger" role="alert">
				<spring:message code="page-not-available-content-may-moved-or-expired" />
			</div>

			<a class="btn btn-primary" href='<c:url value="/" />' role="button"><spring:message code="go-to-home-page"/></a>
		</div>
	</div>
</div>