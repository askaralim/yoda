<%@ include file="../../common/init.jsp"%>

<div class="section">
<div id="content-body">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-1">
				<div class="content-post">
					<div class="page-header">
						<h3>${contentInfo.title}</h3>
						<c:if test="${!empty contentInfo.category}">
							<span class="label label-default">${contentInfo.category.name}</span>
						</c:if>
						<!--<p>$contentInfo.shortDescription</p>-->
						<p><!-- <img src="assets/img/user.png" width="50px" height="50px"> --> <bd></bd></p>
						<p class="text-left">
							<bd>
								<i class="glyphicon glyphicon-time"></i>
								<fmt:formatDate value="${contentInfo.updateDate}" pattern="yyyy-MM-dd" />
								&nbsp
								<i class="glyphicon glyphicon-eye-open"></i>
								${contentInfo.hitCounter}
							</bd>
						</p>
					</div>
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#tab-should-know"><spring:message code="should-know" /></a></li>
							<c:if test="${!empty contentInfo.contentBrands}">
								<li><a data-toggle="tab" href="#tab-brand"><spring:message code="related-brands" /></a></li>
							</c:if>
							<c:if test="${!empty contentInfo.items}">
								<li><a data-toggle="tab" href="#tab-recommend"><spring:message code="recommend-product" /></a></li>
							</c:if>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab-should-know">
								<p>${contentInfo.description}</p>
								<!-- <p><bt>TAGS: <a href="#">Wordpress</a> - <a href="#">Web Design</a></bt></p> -->
								<div class="content-actions">
									<c:choose>
										<c:when test='${userLogin && isUserLike}'>
											<button id="thumbUp" class="fa fa-thumbs-up"></button>
										</c:when>
										<c:otherwise>
											<button id="thumbUp" class="fa fa-thumbs-o-up"></button>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test='${userLogin && isUserDislike}'>
											<button id="thumbDown" class="fa fa-thumbs-down"></button>
										</c:when>
										<c:otherwise>
											<button id="thumbDown" class="fa fa-thumbs-o-down"></button>
										</c:otherwise>
									</c:choose>
									<span id="score" class="label label-success">
										<c:choose>
											<c:when test='${contentInfo.score > 0}'>
												+${contentInfo.score}
											</c:when>
											<c:otherwise>
												${contentInfo.score}
											</c:otherwise>
										</c:choose>
									</span>
								</div>
							</div>
							<div class="tab-pane" id="tab-brand">
								<c:forEach var="contentBrand" items="${contentInfo.contentBrands}">
									<div class="row content-preview-wrapper-row">
										<div class="col-sm-4">
											<a href='<spring:url value="/brand/${contentBrand.brandId}" />'>
												<img data-src='<spring:url value="${contentBrand.brandLogo}"/>' alt="${contentBrand.brandName}" width="150" height="150">
											</a>
										</div>
										<div class="col-sm-8">
											<div id="brand-name">
												<p><a href='<spring:url value="/brand/${contentBrand.brandId}" />'>${contentBrand.brandName}</a></p>
											</div>
											<div class="brand-description">
												<p>${contentBrand.description}</p>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
							<div class="tab-pane" id="tab-recommend">
								<c:forEach var="item" items="${contentInfo.items}">
									<div class="content-preview-wrapper-row">
										<div class="row">
											<div class="col-sm-4">
												<a href='<spring:url value="/item/${item.id}?backURL=${backURL}" />'>
													<img data-src="<spring:url value="${item.imagePath}"/>" alt="${item.name}" width="150" height="150">
												</a>
											</div>
											<div class="col-sm-8">
												<dl class="dl-horizontal">
													<dt><spring:message code="name" /></dt>
													<dd><a href='<spring:url value="/item/${item.id}?backURL=${backURL}" />'>${item.name}</a></dd>
													<dt><spring:message code="brand" /></dt>
													<dd><a href='<spring:url value="/brand/${item.brand.brandId}" />'>${item.brand.name}</a></dd>
													<dt><spring:message code="price" /></dt>
													<dd>${item.price}</dd>
													<c:forEach var="extraField" items="${item.extraFieldList}">
														<dt>${extraField.key}</dt>
														<dd>${extraField.value}</dd>
													</c:forEach>
												</dl>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12">
												<div class="item-description">
													<p>
														${item.description}
													</p>
													<p>
														<a href='<spring:url value="/item/${item.id}?backURL=${backURL}" />'><spring:message code="more" />>>></a>
													</p>
												</div>
												<div class="content-actions">
													<button id="itemThumbUp${item.id}" class="glyphicon glyphicon-thumbs-up" onclick="return itemThumbUp(${item.id})"></button>
													<button id="itemThumbDown${item.id}" class="glyphicon glyphicon-thumbs-down" onclick="return itemThumbDown(${item.id})"></button>
													<span id="rating${item.id}" class="label label-success">
														<c:choose>
															<c:when test="${item.rating > 0}">
																+${item.rating}
															</c:when>
															<c:otherwise>
																${item.rating}
															</c:otherwise>
														</c:choose>
													</span>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="comments">
					<h5><spring:message code="comments" />(${comments.size()})</h5>
					<ul class="list-unstyled">
						<c:forEach var="comment" items="${comments}">
							<li class="left clearfix">
								<span class="comments-img pull-left">
									<c:choose>
										<c:when test="${comment.user.profilePhotoSmall}">
											<img src="<spring:url value="${comment.user.profilePhotoSmall}"/>" alt="${comment.user.username}" class="img-rounded profile_photo_img" />
										</c:when>
										<c:otherwise>
											<img src='<spring:url value="/resources/images/defaultAvatar.png" />' alt="${comment.user.username}" class="img-rounded profile_photo_img" width="32" height="32" />
										</c:otherwise>
									</c:choose>
								</span>
								<div class="comments-body clearfix">
									<div>
										<strong class="primary-font">
											<a href='<spring:url value="/user/${comment.user.userId}" />'>${comment.user.username}</a>
										</strong>
									</div>
									<p class="comment-description">
										${comment.description}
									</p>
									<p class="comment-time">
										<fmt:formatDate value="${comment.createDate}" pattern="yyyy-MM-dd hh:mm" />
										<!--
										<span class="pull-right primary-font">
										#if ($userLogin)
											<a href='#springUrl("/user/$comment.user.userId")'>#springMessage('reply')</a>
										#end
										</span>
										-->
									</p>
								</div>
							</li>
						</c:forEach>
					</ul>
					<c:choose>
						<c:when test="${userLogin}">
							<div>
								<form role="form" method="POST" action="<spring:url value='/comment/new' />">
									<div class="form-group">
										<textarea class="form-control" rows="5" id="description" name="description" placeholder='<spring:message code="your-comment" />' required></textarea>
									</div>
									<div class="form-group">
										<button type="submit" class="btn btn-primary pull-right"><spring:message code="submit" /></button>
									</div>
									<div class="clearfix"></div>
	
									<input type="hidden" name="contentId" value="${contentInfo.contentId}"/>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</div>
						</c:when>
						<c:otherwise>
							<p class="text-muted">
								<spring:message code="please-login-to-make-comments" />
								<button type="button" class="btn btn-primary btn-xs" onclick="return register()"><spring:message code="register" /></button>
							</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-lg-3 home-sidebar">
				<div class="home-sidebar-module">
					<h5><spring:message code="content-publisher" /></h5>
					<c:choose>
						<c:when test="${empty contentInfo.createBy.profilePhotoSmall}">
							<img src='<spring:url value="/resources/images/defaultAvatar.png" />' alt="${contentInfo.createBy.username}" class="img-rounded profile_photo_img" width="32" height="32" />
						</c:when>
						<c:otherwise>
							<img src="${contentInfo.createBy.profilePhotoSmall}" alt="${contentInfo.createBy.username}" class="img-rounded profile_photo_img" width="25" height="25" />
						</c:otherwise>
					</c:choose>
					<a href='<spring:url value="/user/${contentInfo.createBy.userId}" />'>${contentInfo.createBy.username}</a>
				</div>
				<c:if test="${contentInfo.contentContributors.size() > 0}">
					<div class="home-sidebar-module">
						<h5><spring:message code="content-contributor" /></h5>
						<ul class="list-unstyled">
							<c:forEach var="contributor" items="${contentInfo.contentContributors}">
								<li>
									<c:choose>
										<c:when test="${contributor.profilePhotoSmall}">
											<img src="${contributor.profilePhotoSmall}" alt="${contributor.username}" class="img-rounded profile_photo_img" width="25" height="25" />
										</c:when>
										<c:otherwise>
											<img src='<spring:url value="/resources/images/defaultAvatar.png" />' alt="${contributor.username}" class="img-rounded profile_photo_img" width="32" height="32" />
										</c:otherwise>
									</c:choose>
									${contributor.username}
								</li>
								<br>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
</div>

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="registerLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title text-center" id="registerLabel"><spring:message code="join" /> taklip</h4>
			</div>
			<form>
				<div class="modal-body">
					<p id="modalError" class="alert alert-danger" role="alert" hidden="true"></p>
					<div class="form-group">
						<input id="username" name="username" type="text" class="form-control" placeholder='<spring:message code="username" />' required autofocus>
					</div>
					<div class="form-group">
						<input id="email" name="email" type="email" class="form-control" placeholder='<spring:message code="email" />' required>
					</div>
					<div class="form-group">
						<input id="password" name="password" type="password" class="form-control" placeholder='<spring:message code="password" />' required>
					</div>
				</div>
				<div class="modal-footer">
					<a class="btn btn-primary btn-block" id="modalRegister"><spring:message code="register"/></a>

					<spring:message code="have-a-account" />
					<a class="text-center" href='<spring:url value="/login" />'><spring:message code="login"/></a>
				</div>
			</form>
		</div>
	</div>
</div>

<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vanilla-lazyload/10.3.0/lazyload.min.js"></script>

<script>
(function () {
	var myLazyLoad1 = new LazyLoad({
		container: document.getElementById('tab-should-know')
	});
	var myLazyLoad2 = new LazyLoad({
		container: document.getElementById('tab-brand')
	});
	var myLazyLoad3 = new LazyLoad({
		container: document.getElementById('tab-recommend')
	});
}());
</script>


<script type="text/javascript">
$(function(){
	$('#modalRegister').click(function(){
		$.ajax({
			type: "POST",
			url: '<spring:url value="/user/register/ajax" />',
			data:{
				'${_csrf.parameterName}':'${_csrf.token}',
				username:$("#username").val(),
				email:$("#email").val(),
				password:$("#password").val()
			},
			dataType: "json",
			success: function(data){
				var modalError = $('#modalError');
				modalError.hide();

				if (data.error) {
					modalError.show();
					modalError.html(data.error);
				}
				else {
					location.reload();
				}
			}
		});
	});
});
function register(){
	var userLogin = ${userLogin};
	if (!userLogin) {
		$('#loginModal').modal('show')
		return;
	}
}
$(function(){
	$('#thumbUp').click(function(){
		var userLogin = ${userLogin};
		if (!userLogin) {
			$('#loginModal').modal('show')
			return;
		}

		var url = '<spring:url value="/content/${contentInfo.contentId}/rate?thumb=" />';
		var currentUpClass = $('#thumbUp').attr('class');
		var currentDownClass = $('#thumbDown').attr('class');
		var currentUpClassNotActive = (currentUpClass == 'fa fa-thumbs-o-up');
		var currentDownClassNotActive = (currentDownClass == 'fa fa-thumbs-o-down');

		if (currentUpClassNotActive) {
			url = url + 'up';
		}
		else {
			url = url + 'neutral';
		}

		$.ajax({
			type: "POST",
			url: url,
			data:'${_csrf.parameterName}=${_csrf.token}',
			dataType: "json",
			success: function(data){
				var total = data.score;

				$('#score').empty();

				if (total > 0) {
					total = "+" + total;
				}

				$('#score').html(total);
				$('#thumbDown').attr('class', 'fa fa-thumbs-o-down');

				if (currentUpClassNotActive && currentDownClassNotActive) {
					$('#thumbUp').attr('class', 'fa fa-thumbs-up');
				}
				else {
					$('#thumbUp').attr('class', 'fa fa-thumbs-o-up');
				}
			}
		});
	});
});
$(function(){
	$('#thumbDown').click(function(){
		var userLogin = ${userLogin};
		if (!userLogin) {
			$('#loginModal').modal('show')
			return;
		}

		var url = '<spring:url value="/content/${contentInfo.contentId}/rate?thumb=" />';
		var currentUpClass = $('#thumbUp').attr('class');
		var currentDownClass = $('#thumbDown').attr('class');
		var currentDownClassNotActive = (currentDownClass == 'fa fa-thumbs-o-down');
		var currentUpClassNotActive = (currentUpClass == 'fa fa-thumbs-o-up');

		if (currentDownClassNotActive) {
			url = url + 'down';
		}
		else {
			url = url + 'neutral';
		}

		$.ajax({
			type: "POST",
			url: url,
			data:'${_csrf.parameterName}=${_csrf.token}',
			dataType: "json",
			success: function(data){
				var total = data.score;

				$('#score').empty();

				if (total > 0) {
					total = "+" + total;
				}

				$('#score').html(total);
				$('#thumbUp').attr('class', 'fa fa-thumbs-o-up');

				if (currentDownClassNotActive && currentUpClassNotActive) {
					$('#thumbDown').attr('class', 'fa fa-thumbs-down');
				}
				else {
					$('#thumbDown').attr('class', 'fa fa-thumbs-o-down');
				}
			}
		});
	});
});
function contentRate(score){
	var userLogin = ${userLogin};
	if (!userLogin) {
		$('#loginModal').modal('show')
		return;
	}
	$.ajax({
		type: "POST",
		url: '<spring:url value="/content/${contentInfo.contentId}/rate?score=" />'+score,
		data:'${_csrf.parameterName}=${_csrf.token}',
		dataType: "json",
		success: function(data){
			var total = data.score;

			$('#score').empty();

			if (total > 0) {
				total = "+" + total;
			}

			$('#score').html(total);

			if (score == 1) {
				$('#thumbUp').attr('class', 'fa fa-thumbs-up active');
				$('#thumbUp').attr('onclick', 'cancelContentRate(1)');
			}
			else {
				$('#thumbDown').attr('class', 'fa fa-thumbs-down');
			}
		}
	});
}
function cancelContentRate(score){
	var userLogin = ${userLogin};
	if (!userLogin) {
		$('#loginModal').modal('show')
		return;
	}
	$.ajax({
		type: "POST",
		url: '<spring:url value="/content/${contentInfo.contentId}/rate?score=" />'+score,
		data:'${_csrf.parameterName}=${_csrf.token}',
		dataType: "json",
		success: function(data){
			var total = data.score;

			$('#score').empty();

			if (total > 0) {
				total = "+" + total;
			}

			$('#score').html(total);

			if (score == 1) {
				$('#thumbUp').class('fa fa-thumbs-up');
			}
			else {
				$('#thumbDown').class('fa fa-thumbs-down');
			}
		}
	});
}
function contentThumbDown(score){
	var userLogin = ${userLogin};
	if (!userLogin) {
		$('#loginModal').modal('show')
		return;
	}
	$.ajax({
		type: "POST",
		url: '<spring:url value="/content/${contentInfo.contentId}/score?thumb=down" />',
		data:'${_csrf.parameterName}=${_csrf.token}', 
		dataType: "json",
		success: function(data){
			var score = data.score;

			$('#score').empty();

			if (score > 0) {
				score = "+" + score;
			}

			$('#score').html(score);
		}
	});
}
function itemThumbUp(id){
	var userLogin = ${userLogin};
	if (!userLogin) {
		$('#loginModal').modal('show')
		return;
	}
	$.ajax({
		type: "POST",
		url: '<spring:url value="/item/" />' + id + '/rating?thumb=up',
		data:'${_csrf.parameterName}=${_csrf.token}', 
		dataType: "json",
		success: function(data){
			var rating = data.rating;

			$('#rating' + id).empty();

			if (rating > 0) {
				rating = "+" + rating;
			}

			$('#rating' + id).html(rating);
		}
	});
}

function itemThumbDown(id){
	var userLogin = ${userLogin};
	if (!userLogin) {
		$('#loginModal').modal('show')
		return;
	}
	$.ajax({
		type: "POST",
		url: '<spring:url value="/item/" />' + id + '/rating?thumb=down',
		data:'${_csrf.parameterName}=${_csrf.token}', 
		dataType: "json",
		success: function(data){
			var rating = data.rating;

			$('#rating' + id).empty();

			if (rating > 0) {
				rating = "+" + rating;
			}

			$('#rating' + id).html(rating);
		}
	});
}
</script>