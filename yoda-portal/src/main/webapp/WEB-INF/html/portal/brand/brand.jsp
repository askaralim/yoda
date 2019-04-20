<%@ include file="../../common/init.jsp"%>
<div class="section">
	<div class="container">
		<div class="col-lg-10 col-lg-offset-1">
			<h4 class="page-header">
				${brand.name}
			</h4>
			<p class="text-right">
				<bd style="font-size: 12px; color: #d2d2d2; font-weight: 700;">
					<i class="glyphicon glyphicon-eye-open"></i>
					${brand.hitCounter}
				</bd>
			</p>
			<div class="row">
				<div class="brand-detail">
					<div class="col-sm-3">
						<img src="${brand.imagePath}" class="img-responsive" alt="">
					</div>
					<div class="col-sm-9">
						<dl class="dl-horizontal">
							<dt><spring:message code="brand-founded" /></dt>
							<dd>${brand.country}</dd>
							<c:if test="${brand.foundDate != null}">
							<dt><spring:message code="foundeded-date" /></dt>
							<dd>${brand.foundDate}</dd>
							</c:if>
							<dt><spring:message code="brand-products" /></dt>
							<dd>${brand.kind}</dd>
						</dl>
						<div class="brand-description">${brand.description}</div>
					</div>
				</div>
			</div>
			<h4 class="page-header">
				<spring:message code="recommend-product" />
			</h4>
			<div class="row">
				<c:forEach var="item" items="${items}">
					<spring:url value="/item/{itemId}" var="itemUrl">
						<spring:param name="itemId" value="${item.id}"/>
					</spring:url>
					<div class="col-sm-3" style="text-align: -webkit-center;">
						<a href="${fn:escapeXml(itemUrl)}">
							<div class="thumbnail" style="width: 150px; height: 150px; display: flex;align-items: center;">
								<img class="img-responsive" src='<spring:url value="${item.imagePath}"/>' alt="${item.name}">
							</div>
						</a>
						<a href="${fn:escapeXml(itemUrl)}">
							<p style="line-height: 1.6;font-size: 16px;font-weight: 200;">${item.name}</p>
						</a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>