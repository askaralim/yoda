<%@ include file="/html/common/init.jsp" %>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page errorPage="/html/global/jerror.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<title>Yoda Site content management ecommerce system - <tiles:insertAttribute name="title" /></title>

		<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

		<link rel="stylesheet" href="<c:url value="/resources/web/yui_combine.css" />" type="text/css" />
		<link rel="stylesheet" href="<c:url value="/resources/web/styles.css" />" type="text/css" />

		<script type="text/javascript" src='<c:url value="/resources/web/yui_combine.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/resources/web/utility/javacontent.js" />' ></script>

		<script type="text/javascript">
			//YAHOO.widget.MenuItem.prototype.IMG_ROOT = "/<c:url value='${adminBean.contextPath}'/>/resources/web/yui/images/";
			YAHOO.widget.MenuItem.prototype.IMG_ROOT = "/<c:url value='/resources/web/yui/images' />";

			YAHOO.example.onMenuBarReady = function(p_oEvent) {
				// Instantiate and render the menu bar
				var oMenuBar = new YAHOO.widget.MenuBar("jc_menu", {
					autosubmenudisplay : true,
					hidedelay : 750,
					lazyload : false
				});
				oMenuBar.render();
				// A hack to make sure it render in IE. Possibly will be fixed in the later version of YUI library.
				oMenuBar.hide();
				oMenuBar.show();
			}

			// Initialize and render the menu bar when it is available in the DOM
			YAHOO.util.Event.onContentReady("done", YAHOO.example.onMenuBarReady, "");
			// YAHOO.util.Event.on(window, "load", YAHOO.example.onMenuBarReady);
		</script>
	</head>

	<body bgcolor="#FFFFFF" text="#000000">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-bottom: 5px">
						<tr>
							<td nowrap>
								<div align="right" class="jc_admin_header">
									Current site - ${SITE.siteName}
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div id="jc_menu" class="yuimenubar yuimenubarnav">
						<div class="bd">
							<ul class="first-of-type">
								<li class="yuimenubaritem first-of-type">
									<a class="yuimenubaritemlabel" href='<c:url value="/controlpanel/home" />'>Home</a>
								</li>
								<li class="yuimenubaritem first-of-type">
									<a class="yuimenubaritemlabel" href='<c:url value="/controlpanel/content/list" />'>Content</a>
								</li>
								<li class="yuimenubaritem first-of-type">
									<a class="yuimenubaritemlabel" href="javascript:void(null);">Public Site</a>
									<div id="public" class="yuimenu">
										<div class="bd">
											<ul>
												<li class="yuimenuitem">
													<a href='<c:url value="/controlpanel/homepage" />' class="yuimenuitemlabel">Home Page</a>
												</li>
											</ul>
										</div>
									</div>
								</li>
								<li class="yuimenubaritem first-of-type">
									<a class="yuimenubaritemlabel" href="javascript:void(null);">Module</a>
									<div id="module" class="yuimenu">
										<div class="bd">
											<ul>
												<%-- <li class="yuimenuitem">
													<a href='<c:url value="/admin/poll/pollListing.do?process=start" />' class="yuimenuitemlabel">Poll</a>
												</li> --%>
												<li class="yuimenuitem">
													<a href='<c:url value="/controlpanel/contactus/list" />' class="yuimenuitemlabel">Contact Us</a>
												</li>
												<li class="yuimenuitem">
													<a href='<c:url value="/controlpanel/feedback" />' class="yuimenuitemlabel">Feedback</a>
												</li>
											</ul>
										</div>
									</div>
								</li>
								<li class="yuimenubaritem first-of-type">
									<a class="yuimenubaritemlabel" href="javascript:void(null);">Setup</a>
									<div id="setup" class="yuimenu">
										<div class="bd">
											<ul>
												<li class="yuimenuitem">
													<a href='<c:url value="/controlpanel/menu/edit" />' class="yuimenuitemlabel">Menus</a>
												</li>
												<li class="yuimenuitem">
													<a href='<c:url value="/controlpanel/section/edit" />' class="yuimenuitemlabel">Sections</a>
												</li>
											</ul>
										</div>
									</div>
								</li>
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<li class="yuimenubaritem first-of-type">
										<a class="yuimenubaritemlabel" href="javascript:void(null);">Administration</a>
										<div id="administration" class="yuimenu">
											<div class="bd">
												<ul>
													<li class="yuimenuitem">
														<a href='<c:url value="/controlpanel/system/edit" />' class="yuimenuitemlabel">System</a>
													</li>
													<li class="yuimenuitem">
														<a href='<c:url value="/controlpanel/site/list" />' class="yuimenuitemlabel">Sites</a>
													</li>
													<li class="yuimenuitem">
														<a href='<c:url value="/controlpanel/user/list" />' class="yuimenuitemlabel">Users</a>
													</li>
												</ul>
											</div>
										</div>
									</li>
								</sec:authorize>
								<li class="yuimenubaritem first-of-type">
									<a class="yuimenubaritemlabel" href='<c:url value="/logout" />'>Logout</a>
								</li>
							</ul>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br>
		<tiles:insertAttribute name="content" />
		<br>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div align="center">
						<font size="1" face="Helvetica, sans-serif" color="#666666"><br>Copyright by Yoda Site, 2014</font>
					</div>
				</td>
			</tr>
		</table>
		<div id="done"></div>
	</body>
</html>