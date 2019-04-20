<%@ include file="../../common/init.jsp"%>

<ol class="breadcrumb">
	<li><a href='<spring:url value="/controlpanel/home" />'>Administration</a></li>
	<li class="active"><a href="<spring:url value="/controlpanel/brand" />">Brand</a></li>
</ol>

<div class="container-fluid">
	<dl>
		<dt><spring:message code="logo" /></dt>
		<dd>
			<c:choose>
				<c:when test="${empty brand.imagePath}">
				</c:when>
				<c:otherwise>
					<div class="thumbnail" style="max-width: 200px; line-height: 20px;">
						<img src="${brand.imagePath}" alt="..">
					</div>
				</c:otherwise>
			</c:choose>
		</dd>
	</dl>
	<dl>
		<dt><spring:message code="id" /></dt>
		<dd>${brand.brandId}</dd>
	</dl>
	<dl>
		<dt><spring:message code="name" /></dt>
		<dd>${brand.name}</dd>
	</dl>
	<dl>
		<dt><spring:message code="kind" /></dt>
		<dd>${brand.kind}</dd>
	</dl>
	<dl>
		<dt><spring:message code="country" /></dt>
		<dd>${brand.country}</dd>
	</dl>
	<dl>
		<dt><spring:message code="score" /></dt>
		<dd>${brand.score}</dd>
	</dl>
	<dl>
		<dt><spring:message code="description" /></dt>
		<dd>${brand.description}</dd>
	</dl>
</div>