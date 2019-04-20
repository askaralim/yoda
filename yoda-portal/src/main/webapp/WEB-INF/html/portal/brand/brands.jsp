<%@ include file="../../common/init.jsp"%>

<div class="container pt">
	<div class="row">
		<div id="brands" class="col-lg-8 col-lg-offset-2">
			<c:forEach var="brand" items="${page.data}">
				<spring:url value="/brand/{brandId}" var="brandUrl">
					<spring:param name="brandId" value="${brand.brandId}"/>
				</spring:url>
				<div class="col-sm-3" style="text-align: -webkit-center;">
					<div class="thumbnail" style="width: 150px; height: 150px; display: flex;align-items: center;">
						<a href="${fn:escapeXml(brandUrl)}">
							<img src="${brand.imagePath}" alt="..">
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<c:if test="${page.totalCount > 20}">
		<div class="row">
			<div class="col-lg-4 col-lg-offset-4">
				<button id="more-brand" type="button" class="btn btn-default btn-block"><spring:message code="more" /></button>
			</div>
		</div>
	</c:if>
</div>

<script type="text/javascript" src='<c:url value="/resources/js/jquery-1.11.1.min.js" />'></script>

<script type="text/javascript">
	$(function(){
		var offset = 20;

		$('#more-brand').click(function(){
			$.ajax({
				type: "GET",
				url: '<spring:url value="/brand/page"/>',
				data:{
					offset:offset,
				},
				dataType: "json",
				success: function(data){
					for(var i = 0; i < data.length; i++) {
						var url = '<spring:url value="/brand/" />' + data[i].brandId

						$('#brands').append(
							'<div class="col-sm-3" style="text-align: -webkit-center;">'
								+'<div class="thumbnail" style="width: 150px; height: 150px; display: flex;align-items: center;">'
									+'<a href="' + url + '"><img src="' + data[i].imagePath + '" alt=".."></a>'
								+'</div>'
							+'</div>'
						);
					}
				}
			});
			offset = offset + 20;
		});
	});
</script>