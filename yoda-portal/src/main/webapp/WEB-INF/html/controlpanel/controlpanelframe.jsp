<%@ include file="../common/init.jsp" %>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%-- <%@ page errorPage="/html/global/jerror.jsp" %> --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
	<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->

		<title>Yoda Site content management ecommerce system - <tiles:insertAttribute name="title" /></title>

		<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

		<link rel="stylesheet" href='<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />'>
		<link rel="stylesheet" href='<c:url value="/resources/css/mainAdmin.css" />' />
		<%-- <link rel="stylesheet" href='<c:url value="/resources/css/bootstrap-fileupload.min.css" />' /> --%>
		<link rel="stylesheet" href='<c:url value="/resources/css/fileinput.css" />' />
		<link rel="stylesheet" href='<c:url value="/resources/css/datepicker.css" />' />
		<link rel="stylesheet" href='<c:url value="/resources/css/chosen.min.css" />' />
		<!-- <link rel="stylesheet" href="/resources/css/wangEditor.min.css" type="text/css" /> -->

		<!-- <link rel="stylesheet" href="http://fonts.useso.com/css?family=Montserrat:400,700" /> -->
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Montserrat:400,700" />

		<link rel="stylesheet" href='<c:url value="/resources/font-awesome-4.1.0/css/font-awesome.min.css" />' />
	</head>

	<body>
		<nav class="navbar navbar-default navbar-static-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#"></a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li>
							<a href='<c:url value="/controlpanel/home" />'>Home</a>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Contents<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href='<c:url value="/controlpanel/content" />'>Content</a></li>
								<li><a href='<c:url value="/controlpanel/brand" />'>Brand</a></li>
								<li><a href='<c:url value="/controlpanel/item" />'>Item</a></li>
								<li><a href='<c:url value="/controlpanel/comment" />'>Comment</a></li>
								<li><a href='<c:url value="/controlpanel/category" />'>Category</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Public Site<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href='<c:url value="/controlpanel/homepage" />'>Home Page</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Module<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href='<c:url value="/controlpanel/contactus/list" />'>Contact Us</a></li>
								<li><a href='<c:url value="/controlpanel/feedback" />'>Feedback</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Setup<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href='<c:url value="/controlpanel/menu/edit" />'>Menus</a></li>
							</ul>
						</li>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Administration<span class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<li><a href='<c:url value="/controlpanel/system/edit" />'>System</a></li>
									<li><a href='<c:url value="/controlpanel/pageview" />'>Page View</a></li>
									<li><a href='<c:url value="/controlpanel/elasticsearch" />'>Elastic Search</a></li>
									<li><a href='<c:url value="/controlpanel/site/list" />'>Sites</a></li>
									<li><a href='<c:url value="/controlpanel/user/list" />'>Users</a></li>
								</ul>
							</li>
						</sec:authorize>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li>
							<a href='<c:url value="/" />'>${homePageCommand.siteName}</a>
						</li>
						<li>
							<a href='<c:url value="/logout" />'><spring:message code="logout" /></a>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</nav>

		<div class="container-fluid">
			<div class="section">
				<tiles:insertAttribute name="content" />
			</div>
		</div>

		<footer class="footer text-center">
			<div class="container">
				<p class="text-muted">&copy; Yoda Site, 2019</p>
			</div>
		</footer>

<div class="modal fade chooseImgModal" id="chooseImgModal" tabindex="-1" role="dialog" aria-labelledby="chooseImgLabelledby" aria-hidden="true" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="chooseImgLabelledby"><i class="fa fa-image fa-fw"></i>素材库</h4>
            </div>
            <div class="modal-body material-body">
                <div class="btn-group" style="width: 100%;margin: 0 5px 5px 5px;padding: 0 0 10px 0;border-bottom: 1px solid #e7e7eb;">
                    <form action="" id="materialForm">
                    	<input id="modalContentType" name="modalContentType" type="hidden" value="content">
                    	<input id="modalContentId" name="modalContentId" type="hidden" value="${content.contentId}">
                        <input id="input-material-upload" type="file" name="file" multiple="multiple" accept="image/bmp,image/png,image/jpeg,image/jpg,image/gif" style="display: none;">
                        <button id="btn-material-upload" type="button" class="btn btn-success btn-md" title="本地上传">
                            <i class="fa fa-cloud-upload fa-fw"></i> 本地上传
                        </button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
                <div class="fade active in material-box">
                    <ul class="list-unstyled list-file">
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <span class="material-status pull-left">已选<span id="selected">0</span>个，可选<span id="selectable">1</span>个</span>
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-close"> 关闭</i></button>
                <button type="button" class="btn btn-success btn-confirm" data-dismiss="modal"><i class="fa fa-hand-o-up"> 确定</i></button>
            </div>
        </div>
    </div>
</div>

		<!-- Placed at the end of the document so the pages load faster -->
		<script src='<c:url value="/resources/js/jquery-1.11.1.min.js" />'></script>
		<script src='<c:url value="/resources/bootstrap-3.2.0/js/bootstrap.min.js" />'></script>
		<%-- <script src='<c:url value="/resources/js/modernizr-2.6.2-respond-1.1.0.min.js" />'></script> --%>
		<%-- <script src='<c:url value="/resources/js/fileupload/bootstrap-fileupload.js" />'></script> --%>
		<script type="text/javascript" src='<c:url value="/resources/js/fileupload/fileinput.js" />'></script>
		<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
		<script src='<c:url value="/resources/js/ie10-viewport-bug-workaround.js" />'></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.lazyload/1.9.1/jquery.lazyload.min.js" type="text/javascript"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js" type="text/javascript"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="/resources/js/jquery-form.js"></script>
		<script type="text/javascript" src="/resources/js/wangEditor.min.js"></script>
		<script type="text/javascript" src="/resources/js/validator.js"></script>
		<script type="text/javascript" src="/resources/js/yoda.core.js"></script>
		<script src="/resources/js/yoda.tool.js"></script>
	</body>
</html>