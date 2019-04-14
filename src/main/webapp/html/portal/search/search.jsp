<%@ include file="/html/common/init.jsp"%>

<div class="container pt">
	<div class="row">
		<div class="col-lg-8">
			<div class="content-post">
				<div class="page-header">
					<h4><spring:message code="search" />&nbsp${q}</h4>
					<p><spring:message code="search-results" />&nbsp:&nbsp${contentsTotal}</p>
				</div>
				<c:forEach var="content" items="${contents}">
					<div class="row content-preview-wrapper-row">
						<div class="col-sm-12 content-preview-item">
							<h4>
								<spring:url value="/content/{contentId}" var="contentUrl">
									<spring:param name="contentId" value="${content.contentId}"/>
								</spring:url>
								<a href="${fn:escapeXml(contentUrl)}">
									${content.title}
								</a>
							</h4>
							<p>${content.shortDescription}</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<c:if test="${brands != null}">
			<div class="col-lg-4">
				<div class="content-post">
					<c:forEach var="brand" items="${brands}">
						<div class="row content-preview-wrapper-row">
							<div class="col-sm-6 content-preview-item">
								<h4>
									<spring:url value="/brand/{brandId}" var="brandUrl">
										<spring:param name="brandId" value="${brand.brandId}"/>
									</spring:url>
									<a href="${fn:escapeXml(brandUrl)}">
										${brand.name}
									</a>
								</h4>
								<div class="content-preview-image">
									<img src="${brand.imagePath}" alt="${brand.name}" width="100%">
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</c:if>
	</div>
</div>