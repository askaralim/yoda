<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" import="com.yoda.fckeditor.FckEditorCreator"%>

<jsp:useBean id="siteEditCommand" type="com.yoda.site.SiteEditCommand" scope="request" />

<script type="text/javascript">
/* function submitForm(type) {
	<c:if test="${siteEditCommand['new']}">
		document.siteEditCommand.tabIndex.value = tabView.get('activeIndex');
		document.siteEditCommand.templateFooter.value = document.getElementById('templateFooter').value;
		document.siteEditCommand.checkoutShoppingCartMessage.value = document.getElementById('checkoutShoppingCartMessage').value;
	</c:if>

	document.siteEditCommand.process.value = type;
	document.siteEditCommand.submit();

	return false;
} */

function submitBackForm() {
	location.href='<spring:url value="/controlpanel/site/list" htmlEscape="true" />';

	return false;
}

/* function preview(selection) {
    var templateName = selection.options[selection.selectedIndex].value;
    if (templateName == "basic") {
      document.images.templateImage.src = "/<c:out value='${adminBean.contextPath}'/>/services/SecureImageProvider.do?type=U&maxsize=240&imageId=" + "/html/content/template/basic/preview.jpg";
    }
    else {
      document.images.templateImage.src = "/<c:out value='${adminBean.contextPath}'/>/services/SecureImageProvider.do?type=T&siteId=<c:out value='${siteEditCommand.siteId}'/>&maxsize=240&imageId=" + templateName;
    }
} */
function paymentGatewaySelect() {
    var selection = document.getElementById('paymentGatewaySelection');
    var gateway = selection.options[selection.selectedIndex].value;
    var gatewayList = new Array('PSIGate', 'Authorize.Net');
    for (var i = 0; i < gatewayList.length; i++) {
      var obj = document.getElementById(gatewayList[i]);
      if (gateway == gatewayList[i]) {
        obj.style.display = 'block';
      }
      else {
        obj.style.display = 'none';
      }
    }
}
</script>


<form:form method="post" modelAttribute="siteEditCommand" name="fm">
	<form:hidden path="tabIndex" />

	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="jc_top_navigation">
		<tr>
			<td>
				Administration - <a href="<spring:url value="/controlpanel/site/list" htmlEscape="true" />">Site Listing</a> - Site Maintenance
			</td>
		</tr>
	</table>
	<br>
	<table width="98%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td width="100%" valign="top">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="right" class="jc_panel_table_header">
							<table width="0%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<c:if test="${siteEditCommand['new']}">
										<td>
											<input type="submit" value="Save" class="jc_submit_button">
											&nbsp;
										</td>
									</c:if>
									<c:if test="${!siteEditCommand['new']}">
										<td>
											<input type="submit" value="Update" class="jc_submit_button">
											&nbsp;
										</td>
										<!-- <c:if test="${siteEditCommand.siteId != null}">
											<td>
												<input type="submit" value="Remove" class="jc_submit_button" onclick="return submitForm('remove')">
												&nbsp;
											</td>
										</c:if> -->
									</c:if>
									<td>
										<input type="button" value="Cancel" class="jc_submit_button" onclick="return submitBackForm();">
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<br>
				<span class="jc_input_error">
					<form:errors path="*" />
				</span>
				<br>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="400px" valign="top">
							<table width="100%" class="borderTable" border="0" cellspacing="0" cellpadding="3">
								<c:if test="${!siteEditCommand['new']}">
									<tr>
										<td class="jc_input_label">Site Id</td>
										<td>
									</tr>
									<tr>
										<td>
											<form:hidden path="siteId"/>
											<c:out value="${siteEditCommand.siteId}" />
										</td>
									</tr>
									<tr>
										<td class="jc_input_error">
											<form:errors path="siteId" cssClass="error" />
										</td>
									</tr>
								</c:if>
								<tr>
									<td class="jc_input_label">Site Name</td>
								</tr>
								<tr>
									<td>The name of the site and is feed to the template.</td>
								</tr>
								<tr>
									<td>
										<form:input path="siteName" cssClass="jc_input_object" size="50" maxlength="50"/>
									</td>
								</tr>
								<tr>
									<td class="jc_input_error">
										<form:errors path="siteName" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td class="jc_input_label">Site Public Domain Name</td>
								</tr>
								<tr>
									<td>
										Domain name for the public site and is important for
										determining if the incoming request is for this site. It
										should be in the format of www.yodasite.com.
									</td>
								</tr>
								<tr>
									<td>
										<form:input path="domainName" cssClass="jc_input_object" size="50" maxlength="50"/>
									</td>
								</tr>
								<tr>
									<td class="jc_input_error">
										<form:errors path="domainName" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td class="jc_input_label">
										Site Public Port Number (default to 80)
									</td>
								</tr>
								<tr>
									<td>
										If not specified, port number will be defaulted to standard port 80.
									</td>
								</tr>
								<tr>
									<td>
										<form:input path="publicPort" cssClass="jc_input_object" size="4" maxlength="4"/>
									</td>
								</tr>
								<tr>
									<td class="jc_input_error">
										<form:errors path="publicPort" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td class="jc_input_label">Enable SSL secure connection</td>
								</tr>
								<tr>
									<td>
										Select this option when SSL is required when
										performing secure transactions in the public site. Once
										selected, SSL will be used when user perform checkout
										transaction and viewing their account information.
									</td>
								</tr>
								<tr>
									<td>
										<form:checkbox path="secureConnectionEnabled" value="TRUE" cssClass="jc_input_object" />
									</td>
								</tr>
								<tr>
									<td class="jc_input_error">
										<form:errors path="secureConnectionEnabled" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td class="jc_input_label">
										Site Secure Port Number (default to 443)
									</td>
								</tr>
								<tr>
									<td>
										If not specified, port number will be defaulted to standard port 443.
									</td>
								</tr>
								<tr>
									<td>
										<form:input path="securePort" cssClass="jc_input_object" size="4" maxlength="4"/>
									</td>
								</tr>
								<tr>
									<td class="jc_input_error">
										<form:errors path="securePort" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td class="jc_input_label">
										Google Analytics Id
									</td>
								</tr>
								<tr>
									<td>
										<form:input path="googleAnalyticsId" cssClass="jc_input_object" size="50" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td class="jc_input_error">
										<form:errors path="googleAnalyticsId" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td class="jc_input_label">Active</td>
								</tr>
								<tr>
									<td>
										<form:checkbox path="active" value="Y" cssClass="jc_input_object" />
									<td class="jc_input_error"></td>
								</tr>
							</table>
						</td>
						<c:if test="${!siteEditCommand['new']}">
							<td width="50px">&nbsp;</td>
							<td width="100%" valign="top">
								<div class=" yui-skin-sam">
									<div id="tabPanel" class="yui-navset">
										<ul class="yui-nav">
											<li class="selected"><a href="#general"><em>General</em></a></li>
											<li><a href="#logo"><em>Site logo</em></a></li>
											<li><a href="#mail"><em>Mail</em></a></li>
											<li><a href="#template"><em>Template</em></a></li>
											<li><a href="#business"><em>Business</em></a></li>
											<li><a href="#shipping"><em>Shipping</em></a></li>
											<li><a href="#checkout"><em>Checkout</em></a></li>
											<li><a href="#paypal"><em>Paypal</em></a></li>
											<li><a href="#payment"><em>Payment Gateway</em></a></li>
										</ul>
										<div class="yui-content">
											<div id="general" style="padding: 5px; min-height: 300px">
												<table border="0" cellspacing="0" cellpadding="3" width="100%">
													<tr>
														<td class="jc_input_label">Listing page size</td>
													</tr>
													<tr>
														<td>Number of entries to be shown per page during search.</td>
													</tr>
													<tr>
														<td>
															<form:input path="listingPageSize" cssClass="jc_input_object" />
															<span class="jc_input_error">
																<form:errors path="listingPageSize" cssClass="error" />
															</span>
														</td>
													</tr>
													<tr>
														<td class="jc_input_label"><hr></td>
													</tr>
													<%-- <tr>
														<td class="jc_input_label">Default Currency Code</td>
													</tr>
													<tr>
														<td>
															Currency code to be used when performing transaction.
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">
															<form:select path="currencyId" cssClass="tableContent" cssStyle="width: 200px">
																<form:options items="${siteEditCommand.countries}" itemLabel="countryName" itemValue="countryCode" />
															</form:select>
														</td>
													</tr> --%>
													<tr>
														<td class="jc_input_label">Section page size</td>
													</tr>
													<tr>
														<td>Number of entries to be shown per section page.</td>
													</tr>
													<tr>
														<td>
															<form:input path="sectionPageSize" cssClass="jc_input_object" />
															<span class="jc_input_error">
																<form:errors path="sectionPageSize" cssClass="error" />
															</span>
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Public site footer</td>
													</tr>
													<tr>
														<td>Footer to be used on public site.</td>
													</tr>
													<tr>
														<td>
															<form:input path="footer" cssClass="jc_input_object" />
														</td>
													</tr>
												</table>
											</div>
											<div id="logo" style="padding: 5px; min-height: 300px">
												<table border="0" cellspacing="0" cellpadding="3" width="100%">
													<tr>
														<td class="jc_input_label">
															Site Logo&nbsp;&nbsp;

															<!-- <a href="javascript:showLogoWindow();" class="jc_navigation_link">Upload</a> -->
															<a href="#" class="jc_navigation_link">Upload</a>
															&nbsp;&nbsp;

															<c:if test="${siteEditCommand.logoContentType != null}">
																<!-- <a href="javascript:submitForm('removeImage')" class="jc_navigation_link">Remove</a> -->
																<a href="#" class="jc_navigation_link">Remove</a>
															</c:if>
														</td>
													</tr>
													<tr>
														<td>
															Logo to be shown on the public site. The acual
															placement is determined by the template selected.
														</td>
													</tr>
													<tr>
														<td width="100%">&nbsp;</td>
													</tr>
													<tr>
														<td width="100%">
															<c:if test="${siteEditCommand.logoContentType != null}">
																<img src='<spring:url value="/images?imageId=${siteEditCommand.siteId}&type=s&maxsize=100" htmlEscape="true" />'>
																<%-- <img src="/<c:out value='${adminBean.contextPath}'/>/services/SecureImageProvider.do?type=S&maxsize=100&imageId=<c:out value='${siteEditCommand.siteId}'/>"> --%>
																<br>
															</c:if>
														</td>
													</tr>
												</table>
												<br>
											</div>
											<%-- <div id="mail" style="padding: 5px; min-height: 300px">
												<table border="0" cellspacing="0" cellpadding="3" width="100%">
													<tr>
														<td class="jc_input_label">Mail outgoing (SMTP) host</td>
													</tr>
													<tr>
														<td>SMTP server address</td>
													</tr>
													<tr>
														<td class="jc_input_label">
															<form:textarea path="mailSmtpHost" cols="40" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Mail outgoing (SMTP) port</td>
													</tr>
													<tr>
														<td>
															SMTP server port number. If not specified, port
															number will be defaulted to 25.
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">
															<form:textarea path="mailSmtpPort" cols="10" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">
															Mail outgoing (SMTP) account
														</td>
													</tr>
													<tr>
														<td>
															Account name to be used when connecting to SMTP
															server. Leave it empty is authentication is not required.
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">
															<form:textarea path="mailSmtpAccount" cols="40" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">
															Mail outgoing (SMTP) password
														</td>
													</tr>
													<tr>
														<td>
															Password to be used when connecting to SMTP
															server if authentication is required.<br> This field
															contains sensitive information and is not displayed.
															Enter a value if change is required. Enter a space if
															this field is to be cleared. Otherwise, leave this field
															as blank and existing value will not be altered.
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">
															<form:password path="mailSmtpPassword" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td><hr></td>
													</tr>
													<tr>
														<td class="jc_input_label">Password Reset email from</td>
													</tr>
													<tr>
														<td>
															Specify the 'mail from' address when delivery
															password to user during password reset.
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">
															<form:textarea path="mailFromPwdReset" cols="40" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															Password Reset email subject line
														</td>
													</tr>
													<tr>
														<td>
															Specify the subject line of the email to be sent
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															<form:textarea path="subjectPwdReset" cols="80" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td><hr></td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															Customer sales confirmation email from
														</td>
													</tr>
													<tr>
														<td>
															Specify the 'mail from' address when sending the
															sales confirmation email
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															<form:textarea path="mailFromCustSales" cols="40" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															Customer sales confirmation email subject line
														</td>
													</tr>
													<tr>
														<td>
															Specify the subject line of the email to be sent.
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															<form:textarea path="subjectCustSales" cols="80" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td><hr></td>
													</tr>
													<tr>
														<td class="jc_input_label">Sales notification email</td>
													</tr>
													<tr>
														<td>
															Automatic generate email to this address once a
															sale is completed.<br> Leave it blank if no
															automatic email is required.
														</td>
													</tr>
													<tr>
														<td width="100%">
															<form:input path="checkoutNotificationEmail" cssClass="jc_input_object" maxlength="40" size="40" />
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															Sales notification internal email from
														</td>
													</tr>
													<tr>
														<td>
															Specify the 'mail from' address when sending the
															sales notification internal email
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															<form:textarea path="mailFromNotification" cols="40" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															Customer sales notification internal email subject line
														</td>
													</tr>
													<tr>
														<td>
															Specify the subject line of the email to be sent.
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															<form:textarea path="subjectNotification" cols="80" cssClass="jc_input_object" />
														</td>
													</tr>
												</table>
												<br>
											</div> --%>
											<div id="template" style="padding: 5px; min-height: 300px">
												<table border="0" cellspacing="0" cellpadding="3">
													<tr>
														<td class="jc_input_label" valign="top">Content template</td>
													</tr>
													<tr>
														<td>Template to be used for this site.</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label" valign="top">
															<%-- <form:select path="templateName" onchange="javascript:preview(this);">
																<form:options items="${siteEditCommand.templates}" itemLabel="templateName" itemValue="templateName" />
															</form:select> --%>
														</td>
													</tr>
													<tr>
														<td width="100%">
															<c:choose>
																<c:when test="${siteEditCommand.themeId == '1'}">
																	<%-- <img name="templateImage"
																		src="/<c:out value='${adminBean.contextPath}'/>/services/SecureImageProvider.do?type=U&siteId=<c:out value='${siteEditCommand.siteId}'/>&imageId=/html/content/template/basic/preview.jpg&maxsize=240"
																		border="0"> --%>
																</c:when>
																<c:otherwise>
																	<%-- <img name="templateImage"
																		src="/<c:out value='${adminBean.contextPath}'/>/services/SecureImageProvider.do?type=T&siteId=<c:out value='${siteEditCommand.siteId}'/>&imageId=<c:out value='${siteEditCommand.templateName}'/>&maxsize=240"
																		border="0"> --%>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</table>
												<br>
											</div>
											<%-- <div id="business" style="padding: 5px; min-height: 300px">
												<table border="0" cellspacing="0" cellpadding="3">
													<tr>
														<td>
															Business contact information when performing
															monetary transaction. Please make sure the following
															information matches your business's bank account to avoid
															any confusion in the future.
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">Contact Name</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="businessContactName" maxlength="60" cols="60"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Company</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="businessCompany" maxlength="60" cols="60"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Address Line 1</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="businessAddress1" maxlength="60" cols="60"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Address Line 2</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="businessAddress2" maxlength="60" cols="60"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">City</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="businessCity" maxlength="30" cols="30"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">State/Province</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label"><html:select
																property="businessStateCode">
																<html:optionsCollection property="states" value="value"
																	label="label" />
															</html:select></td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">Country</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label"><html:select
																property="businessCountryCode">
																<html:optionsCollection property="countries"
																	value="value" label="label" />
															</html:select></td>
													</tr>
													<tr>
														<td class="jc_input_label">Postal / Zip Code</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="businessPostalCode" maxlength="30" cols="30"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Phone</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="businessPhone" maxlength="30" cols="30"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Fax</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="businessFax" maxlength="30" cols="30"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Email</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="businessEmail" maxlength="40" cols="40"  cssClass="jc_input_object" />
														</td>
													</tr>
												</table>
												<br>
											</div> --%>
											<%-- <div id="shipping" style="padding: 5px; min-height: 300px">
												<table border="0" cellspacing="0" cellpadding="3">
													<tr>
														<td width="200" class="jc_input_label">
															Default Shipping Type
														</td>
													</tr>
													<tr>
														<td>
															Default shipping type for items. If no dropdown
															option for shipping types can be seen, you should create
															them via Shipping-&gt;Shipping Type.
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															<form:select path="shippingTypeId">
																<form:options items="${siteEditCommand.shippingTypes}" itemLabel="templateName" itemValue="templateName" />
															</form:select>
														</td>
													</tr>
												</table>
											</div>
											<div id="checkout" style="padding: 5px; min-height: 300px">
												<table border="0" cellspacing="0" cellpadding="3" width="100%">
													<tr>
														<td class="jc_input_label">Activate</td>
													</tr>
													<tr>
														<td>Activate checkout process.</td>
													</tr>
													<tr>
														<td>
															<form:checkbox path="checkoutActivate" value="Y" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td width="200" class="jc_input_label">
															Shopping Cart Message
														</td>
													</tr>
													<tr>
														<td>
															The following message will be shown at the bottom
															of every page during check-out process.
														</td>
													</tr>
													<tr>
														<td width="100%">
															<%
															out.println(Utility.getFckEditor(request, "checkoutShoppingCartMessage", "100%", "200", "Simple", siteEditCommand.getCheckoutShoppingCartMessage()));
															%>
													</tr>
												</table>
											</div>
											<div id="paypal" style="padding: 5px; min-height: 300px">
												<table border="0" cellspacing="0" cellpadding="3">
													<tr>
														<td class="jc_input_label">Activate</td>
													</tr>
													<tr>
														<td>Activate credit card processing via PayPal.</td>
													</tr>
													<tr>
														<td>
															<form:checkbox path="paymentPaypalActivate" value="Y" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">&nbsp;</td>
													</tr>
													<tr>
														<td>Api username, api password and signature can be
															obtained from PayPal once account is setup with them.</td>
													</tr>
													<tr>
														<td class="jc_input_label">Api Username</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="paymentPaypalApiUsername" maxlength="60" cols="60"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Api Password</td>
													</tr>
													<tr>
														<td>This field contains sensitive information and is
															not displayed. Enter a value if change is required. Enter
															a space if this field is to be cleared. Otherwise, leave
															this field as blank and existing value will not be
															altered.</td>
													</tr>
													<tr>
														<td>
															<form:textarea path="paymentPaypalApiPassword" maxlength="60" cols="60"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Signature</td>
													</tr>
													<tr>
														<td>This field contains sensitive information and is
															not displayed. Enter a value if change is required. Enter
															a space if this field is to be cleared. Otherwise, leave
															this field as blank and existing value will not be
															altered.</td>
													<tr>
														<td>
															<form:textarea path="paymentPaypalSignature" maxlength="60" cols="80"  cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Environment</td>
													</tr>
													<tr>
														<td>Specify live mode or sandbox mode for testing.</td>
													</tr>
													<tr>
														<td>
															<form:select path="paymentPaypalEnvironment" cssClass="tableContent">
																<form:option value="sandbox" label="Sandbox" />
																<form:option value="live" label="Live" />
															</form:select>
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Authorize Extra Amount</td>
													</tr>
													<tr>
														<td>Specify the extra dollar amount to pre-authorize
															before the transaction is committed.</td>
													</tr>
													<tr>
														<td>
															<form:input path="paymentPaypalExtraAmount" cssClass="jc_input_object" />
															<span class="jc_input_error">
																<form:errors path="paymentPaypalExtraAmount" cssClass="error" />
															</span>
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Authorize Extra Percentage</td>
													</tr>
													<tr>
														<td>Specify the extra percentage amount to
															pre-authorize before the transaction is committed.</td>
													</tr>
													<tr>
														<td>
															<form:input path="paymentPaypalExtraPercentage" cssClass="jc_input_object" />
															<span class="jc_input_error">
																<form:errors path="paymentPaypalExtraPercentage" cssClass="error" />
															</span>
														</td>
													</tr>
												</table>
												<br>
											</div>
											<div id="payment" style="padding: 5px; min-height: 300px">
												<table border="0" cellspacing="0" cellpadding="3">
													<tr>
														<td class="jc_input_label">Activate</td>
													</tr>
													<tr>
														<td>Activate credit card processing.</td>
													</tr>
													<tr>
														<td>
															<form:checkbox path="paymentGatewayActivate" value="Y" cssClass="jc_input_object" />
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">Payment gateway provider</td>
													</tr>
													<tr>
														<td>Select one of the following supported payment
															gateway provider.</td>
													</tr>
													<tr>
														<td>
															<form:select path="paymentGateway" styleClass="tableContent" onclick="paymentGatewaySelect();">
																<form:options items="${siteEditCommand.paymentGateways}" itemLabel="templateName" itemValue="templateName" />
															</form:select>
															<!-- <html:select styleId="paymentGatewaySelection" property="paymentGateway" styleClass="tableContent" onclick="paymentGatewaySelect();">
																<html:optionsCollection property="paymentGateways" value="value" label="label" />
															</html:select> -->
														</td>
													</tr>
													<tr>
														<td class="jc_input_label">&nbsp;</td>
													</tr>
													<tr>
														<td>
															<div id="PSIGate" style="display: none">
																<table border="0" cellspacing="0" cellpadding="3">
																	<tr>
																		<td>
																			<hr>
																		</td>
																	</tr>
																	<tr>
																		<td class="jc_input_label">PSIGate configuration
																		</td>
																	</tr>
																	<tr>
																		<td>Store Id and PassPhrase should be obtained
																			from PSIGate once an account is created with them.</td>
																	</tr>
																	<tr>
																		<td class="jc_input_label">Store Id</td>
																	</tr>
																	<tr>
																		<td>
																			<form:textarea path="paymentPsiGateStoreId" maxlength="60" cols="60"  cssClass="jc_input_object" />
																		</td>
																	</tr>
																	<tr>
																		<td class="jc_input_label">PassPhrase</td>
																	</tr>
																	<td>This field contains sensitive information and
																		is not displayed. Enter a value if change is required.
																		Enter a space if this field is to be cleared.
																		Otherwise, leave this field as blank and existing
																		value will not be altered.</td>
																	<tr>
																		<td>
																			<form:textarea path="paymentPsiGatePassPhrase" maxlength="60" cols="60"  cssClass="jc_input_object" />
																		</td>
																	</tr>
																	<tr>
																		<td class="jc_input_label">Environment</td>
																	</tr>
																	<tr>
																		<td>Specify live mode or sandbox mode for
																			testing.</td>
																	</tr>
																	<tr>
																		<td>
																			<form:select path="paymentPsiGateEnvironment" styleClass="tableContent">
																				<form:option value="sandbox" label="Sandbox" />
																				<form:option value="live" label="Live" />
																			</form:select>
																		</td>
																	</tr>
																</table>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															<div id="Authorize.Net" style="display: none">
																<table border="0" cellspacing="0" cellpadding="3">
																	<tr>
																		<td>
																			<hr>
																		</td>
																	</tr>
																	<tr>
																		<td class="jc_input_label">Authorize.Net
																			Configuration</td>
																	</tr>
																	<tr>
																		<td>Login Id and Tran key should be obtained from
																			Authorize.Net once an account is created with them.</td>
																	</tr>
																	<tr>
																		<td class="jc_input_label">Login Id</td>
																	</tr>
																	<tr>
																		<td>
																			<form:textarea path="paymentAuthorizeNetLoginId" maxlength="60" cols="60" cssClass="jc_input_object" />
																		</td>
																	</tr>
																	<tr>
																		<td class="jc_input_label">Tran key</td>
																	</tr>
																	<tr>
																		<td>This field contains sensitive information and
																			is not displayed. Enter a value if change is
																			required. Enter a space if this field is to be
																			cleared. Otherwise, leave this field as blank and
																			existing value will not be altered.</td>
																	<tr>
																		<td>
																			<form:password path="paymentAuthorizeNetTranKey" cssClass="jc_input_object" maxlength="60" />
																		</td>
																	</tr>
																	<tr>
																		<td class="jc_input_label">Environment</td>
																	</tr>
																	<tr>
																		<td>Specify live mode or sandbox mode for
																			testing.</td>
																	</tr>
																	<tr>
																		<td>
																			<form:select path="paymentAuthorizeNetEnvironment" styleClass="tableContent">
																				<form:option value="sandbox" label="Sandbox" />
																				<form:option value="live" label="Live" />
																			</form:select>
																		</td>
																	</tr>
																</table>
															</div>
														</td>
													</tr>
												</table>
												<br>
											</div> --%>
										</div>
									</div>
								</div>
							</td>
						</c:if>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form:form>

<script>
/* var jc_logo_panel = null;

function showLogoWindow() {
	jc_logo_panel.show();
}

function jc_logo_init() {
	jc_logo_panel = new YAHOO.widget.Panel("jc_logo_panel", 
		{
			width: "300px", 
			visible: false, 
			constraintoviewport: true ,
			fixedcenter : true,
			modal: true
		}
	);
	jc_logo_panel.render();
}

YAHOO.util.Event.onDOMReady(jc_logo_init); */

<%-- var jc_upload_callback = {
  upload: function(o) {
    var value = o.responseText.replace(/<\/?pre>/ig, '');
    var object = eval('(' + value + ')');
    if (object.status == 'failed') {
      var fn = document.getElementById('filename');
      fn.innerHTML = object.filename;
    }
    else {
      window.location = "/<c:out value='${adminBean.contextPath}'/>/admin/site/siteMaint.do?" + 
                        "process=edit&" +
                        "siteId=<c:out value='${siteEditCommand.siteId}'/>&" + 
                        "tabIndex=1";
    }
  }
}
function jc_uploadImage() {
  YAHOO.util.Connect.setForm('siteEditCommand1', true, true);

  var url = "/<c:out value='${adminBean.contextPath}'/>/admin/site/siteMaint.do";
  var response = YAHOO.util.Connect.asyncRequest('GET', url, jc_upload_callback);
  
}
</script>
<form name="siteEditCommand1" method="post" action="/<c:out value='${adminBean.contextPath}'/>/admin/site/siteMaint.do" enctype="multipart/form-data"> 
<input type="hidden" name="siteId" value="<c:out value='${siteEditCommand.siteId}'/>"> 
<input type="hidden" name="process" value="uploadImage"> 
<div class=" yui-skin-sam">
<div>
<div id="jc_logo_panel">
  <div class="hd">Site Logo</div>
  <div class="bd"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td>
          Upload new logo for site
        </td>
      </tr>
      <tr>
         <td>
         <input type="file" name="file" class="jc_input_object">
         </td>
      </tr>
      <tr>
         <td class="jc_input_error"><div id="filename"></div></td>
      </tr>
      <tr>
         <td>&nbsp;</td>
      </tr>
      <tr>
        <td>
          <a href="javascript:void(0);" onclick="jc_uploadImage();" class="jc_navigation_link">Confirm</a>
          <a href="javascript:void(0);" onclick="jc_logo_panel.hide()" class="jc_navigation_link">Cancel</a>&nbsp;
        </td>
      </tr>
    </table>
  </div>
</div>
</div>
</div>
</form> --%>
<!------------------------------------------------------------------------>
<c:if test="${siteEditCommand['new']}">
	<script type="text/javascript">
		var tabView = new YAHOO.widget.TabView("tabPanel");

		tabView.set("activeIndex", <c:out value="${siteEditCommand.tabIndex}"/>);

		paymentGatewaySelect();
	</script>
</c:if>