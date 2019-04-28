<%@ include file="../../common/init.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="section">
	<div class="container">
		<div class="home-main">
			<!-- <div class="col-sm-12"> -->
				<div class="home-content">
					<div class="row">
					<!--<h1 style="text-align: center">选购参考</h1>-->
						<c:forEach var="contentInfo" items="${homeInfo.homePageDatas}">
							<div class="col-sm-3">
								<div class="content-preview">
									<div class="row content-preview-wrapper-row">
										<div class="col-sm-12 content-preview-item">
											<div class="content-preview-image">
												<a href="${contentInfo.contentUrl}"><img src="${contentInfo.featuredImage}" width="100%" alt="${contentInfo.title}"></a>
											</div>
										</div>
										<div class="col-sm-12 content-preview-item">
											<h4><a href="${contentInfo.contentUrl}" class="">${contentInfo.title}</a></h4>
											<p class="text-left">
												<bd>
													<i class="fa fa-eye"></i>
													${contentInfo.hitCounter}
													&nbsp
													<i class="fa fa-heart"></i>
													<span id="score">
														<c:choose>
															<c:when test="${contentInfo.score > 0}">
																+${contentInfo.score}
															</c:when>
															<c:otherwise>
																${contentInfo.score}
															</c:otherwise>
														</c:choose>
													</span>
												</bd>
											</p>
											<p>${contentInfo.shortDescription}</p>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			<!-- </div> -->
			<c:if test="${homeInfo.page.totalCount > 4}">
				<div class="row">
					<div class="col-lg-4 col-lg-offset-4" style="padding: 15px;">
						<button id="more-content" type="button" class="btn btn-default btn-block"><spring:message code="more" /></button>
					</div>
				</div>
			</c:if>
			<div class="row">
				<div class="col-sm-12">
					<div class="home-brand-all">
						<h2 style="text-align: center; padding: 10px"><spring:message code="top-viewed-brands" /></h2>
						<div class="row">
							<c:forEach var="brand" items="${topViewedBrands}">
								<div class="col-sm-3" style="text-align: -webkit-center;">
									<div class="home-brand">
										<a href='<spring:url value="/brand/${brand.brandId}" />'>
											<div class="thumbnail" style="width: 150px; height: 150px; display: flex;align-items: center;">
												<img src="${brand.imagePath}" class="img-responsive" alt="${brand.name}">
											</div>
										</a>
										<a href='<spring:url value="/brand/${brand.brandId}" />'>
											<p>${brand.name}</p>
										</a>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="home-item-all">
						<h2 style="text-align: center; padding: 10px;"><spring:message code="top-viewed-items" /></h2>
						<div class="row">
							<c:forEach var="item" items="${topViewedItems}">
								<div class="col-sm-3" style="text-align: -webkit-center;">
									<div class="home-item">
										<a href='<spring:url value="/item/${item.id}" />'>
											<div class="thumbnail" style="width: 150px; height: 150px; display: flex;align-items: center;">
												<img src='<spring:url value="${item.imagePath}"/>' class="img-responsive" alt="${item.name}">
											</div>
										</a>
										<a href='<spring:url value="/item/${item.id}" />'>
											<p>${item.name}</p>
										</a>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="home-about">
							<div class="row">
								<div class="col-sm-12">
									<div class="home-about-inset">
										<h1 style="text-align: center">${homeInfo.homePageFeatureData.title}</h1>
										<p>${homeInfo.homePageFeatureData.description}</p>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<h4><spring:message code="social" /></h4>
									<div class="col-sm-2">
										<a href="http://weibo.com/taklip" aria-label="weibo">
											<i class="fa fa-weibo fa-2x fa-fw margin-bottom" aria-hidden="true"></i> taklip太离谱
										</a>
									</div>
									<div class="col-sm-2">
										<i class="fa fa-weixin fa-2x fa-fw margin-bottom"></i> taklip太离谱
										<img src="/yoda/uploads/1/content/qrcode_for_gh_1306e58d35fe_860_L.jpg" width="100%">
									</div>
								</div>
							</div>
						</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src='<c:url value="/resources/js/jquery-1.11.1.min.js" />'></script>

<script type="text/javascript">
	$(function(){
		var offset = 4;

		$('#more-content').click(function(){
			$.ajax({
				type: "GET",
				url: '<spring:url value="/content/page"/>',
				data:{
					offset:offset
				},
				contentType: 'text/json,charset=utf-8',
				dataType: "json",
				success: function(data){
					//$('.home-content').append('<div class="row">');

					var text = '<div class="row">';
					for(var i = 0; i < data.length; i++) {
						var info = data[i];

						var infoScore = info.score;

						if (info.score > 0) {
							infoScore = '+' + info.score;
						}
						text += '<div class="col-sm-3">'
							+'<div class="content-preview">'
							+'<div class="row content-preview-wrapper-row">'
								+ '<div class="col-sm-12 content-preview-item">'
									+ '<div class="content-preview-image">'
										+ '<a href="' + info.contentUrl + '"><img src="'+ info.defaultImageUrl + '" width="100%" alt="' + info.title + '"></a>'
									+ '</div>'
								+ '</div>'
								+ '<div class="col-sm-12 content-preview-item">'
									+ '<h4><a href="' + info.contentUrl + '" class="">' + info.title + '</a></h4>'
									+ '<p class="text-left">'
										+ '<bd>'
											+ '<i class="fa fa-eye"></i>'
											+ info.hitCounter
											+ '&nbsp'
											+ '<i class="fa fa-heart"></i>'
											+ '<span id="score">'
												+ infoScore
											+ '</span>'
										+ '</bd>'
									+ '</p>'
									+ '<p>' + info.shortDescription + '</p>'
								+ '</div>'
							+ '</div>'
						+ '</div>'
					+ '</div>';
					}

					text += '</div>';

					$('.home-content').append(text);
				}
			});
			offset = offset + 4;
		});
	});
</script>