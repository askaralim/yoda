<%@ include file="/html/common/init.jsp"%>

<c:set var="emptyTemplateInfo" scope="request" value="${emptyTemplateInfo}"/>

<c:out escapeXml="false" value="${emptyTemplateInfo.templatePrefix}"/>

<script type="text/javascript" language="JavaScript">
<!--
function submitForm(type) {
	document.myAccountLoginActionForm.process.value = type;
	document.myAccountLoginActionForm.submit();
}
//-->
</script>

<c:set var="myAccountLoginActionForm" scope="request" value="${myAccountLoginActionForm}"/>

<span class="jc_tran_heading_text">Sign in</span>
<br>
<br>
<form:form method="post" action="/myaccount/login/myAccountLogin.do?process=login">
	<logic:messagesPresent property="msg" message="true">
		<html:messages property="msg" id="message" message="true">
			<span class="jc_tran_message_text">
			<bean:write name="message" /><br>
			<br>
			</span>
		</html:messages>
	</logic:messagesPresent>
	<span class="jc_tran_text">
		To sign in, please enter your email address and password below.
	</span>
	<br>
	<br>
	<html:hidden property="process" value="" />
	<table border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td nowrap>
				<div class="jc_tran_title_text">
					Email address
				</div>
			</td>
			<td nowrap>
				<html:text property="custEmail" size="30" styleClass="jc_tran_input" />
				<logic:messagesPresent property="custEmail" message="true">
					<html:messages property="custEmail" id="errorText" message="true">
						<span class="jc_tran_error"> 
							<bean:write name="errorText" />
						</span>
					</html:messages>
				</logic:messagesPresent>
			</td>
		</tr>
		<tr>
			<td nowrap>
				<div class="jc_tran_title_text">
					Password
				</div>
			</td>
			<td nowrap>
				<html:password property="custPassword" size="30" styleClass="jc_tran_input" />
				<logic:messagesPresent property="custPassword" message="true">
					<html:messages property="custPassword" id="errorText" message="true">
						<span class="jc_tran_error">
							<bean:write name="errorText" />
						</span>
					</html:messages>
				</logic:messagesPresent>
			</td>
		</tr>
		<tr>
			<td nowrap></td>
			<td nowrap>
				<logic:messagesPresent property="login" message="true">
					<html:messages property="login" id="errorText" message="true">
						<span class="jc_tran_error">
							<bean:write name="errorText" />
						</span>
					</html:messages>
				</logic:messagesPresent>
			</td>
		</tr>
		<tr>
			<td nowrap></td>
			<td nowrap>
				<html:link href="javascript:submitForm('login');" styleClass="jc_tran_button">
					Sign in
				</html:link>
				<html:link page="/myaccount/forgot/myAccountForgot.do?process=start" styleClass="jc_tran_button">
					Forgot your password?
				</html:link>
			</td>
		</tr>
	</table>
</form:form>

<c:out escapeXml="false" value="${emptyTemplateInfo.templateSuffix}"/>