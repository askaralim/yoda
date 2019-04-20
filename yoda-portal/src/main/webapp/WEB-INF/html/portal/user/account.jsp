<%-- <%@ include file="/html/common/init.jsp"%>

<c:set var="emptyTemplateInfo" scope="request" value="${emptyTemplateInfo}"/>
<c:out escapeXml="false" value="${emptyTemplateInfo.templatePrefix}"/>

<script type="text/javascript" language="JavaScript">
<!--
	function submitForm(type) {
		document.forms[0].process.value = type;
		document.forms[0].submit();
	}
//-->
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="5">
	<tr>
		<td valign="top" width="0"><jsp:include page="/html/portal/account/accountNavigation.jsp" /></td>
	</tr>
</table>

<c:out escapeXml="false" value="${emptyTemplateInfo.templateSuffix}"/> --%>