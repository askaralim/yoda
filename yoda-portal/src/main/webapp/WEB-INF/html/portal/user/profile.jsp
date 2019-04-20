<%@ include file="../../common/init.jsp"%>

<div class="section">
	<div class="container">
		<div class="page-header">
			<div class="row">
				<div class="col-sm-2">
					<div class="thumbnail">
						<c:choose>
							<c:when test="${user.profilePhoto != null}">
								<img src="${user.profilePhoto}" class="img-responsive" alt="${user.username}" />
							</c:when>
							<c:otherwise>
								<img src='<c:url value="/resources/images/defaultAvatar.png" />' class="img-responsive" alt="avatar" />
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="col-sm-8">
					<h5>${user.username}</h5>
					<small><i class="fa fa-envelope"></i> ${user.email}</small>
					<c:if test="${user.phone}">
						<i class="fa fa-phone"></i> ${user.phone}
					</c:if>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-8">
				<div class="nav-tabs-custom">
					<ul class="nav nav-tabs">
						<%-- <li class='${tab == "basic" ? "active" : ""}'> --%>
						<li class="active">
							<a href="#post"><i class="glyphicon glyphicon-list"></i>&nbsp<spring:message code="posts" /></a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="posts">
								<div class="box-body table-responsive no-padding">
									<table class="table">
										<thead>
											<tr>
												<th>#</th>
												<th><spring:message code="title" /></th>
												<th><spring:message code="create-date" /></th>
												<th><spring:message code="hit-counter" /></th>
												<c:if test="${currentUser != null && currentUser.userId == content.createBy}">
													<th><spring:message code="edit" /></th>
												</c:if>
											</tr>
										</thead>
										<c:forEach var="content" items="${contents}" step="1" varStatus="s">
											<tr>
												<td>${s.count}</td>
												<td>
													<c:choose>
														<c:when test='${content.published}'>
															<spring:url value="/content/{contentId}" var="contentUrl">
																<spring:param name="contentId" value="${content.contentId}"/>
															</spring:url>
															<a href="${fn:escapeXml(contentUrl)}">
																${content.title}&nbsp[<spring:message code="published" />]
															</a>
														</c:when>
														<c:otherwise>
															${content.title}&nbsp[<spring:message code="unpublished" />]
														</c:otherwise>
													</c:choose>
												</td>
												<td><fmt:formatDate value="${content.createDate}" pattern="yyyy-MM-dd" /></td>
												<td>
													<c:out value="${content.hitCounter}" />
												</td>
												<td>
													<c:if test="${currentUser != null && currentUser.userId == content.createBy.userId}">
														<spring:url value="/content/{contentId}/edit" var="editContentUrl">
															<spring:param name="contentId" value="${content.contentId}"/>
														</spring:url>
														<a href="${fn:escapeXml(editContentUrl)}" class="btn btn-sm"><i class="fa fa-edit"></i></a>
															<%-- <a href="${fn:escapeXml(deleteContentUrl)}" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i></a> --%>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</table>
								</div>
								<!-- /.box-body -->
						</div>
					</div>
				</div>
			</div>
			<%-- <div class="col-lg-2">
				<c:if test="${currentUser != null && currentUser.userId == user.userId}">
					<a class="btn btn-primary btn-sm" href='<c:url value="/content/add" />' role="button"><spring:message code="new-content" /></a>
				</c:if>
			</div> --%>
		</div>
	</div>
</div>