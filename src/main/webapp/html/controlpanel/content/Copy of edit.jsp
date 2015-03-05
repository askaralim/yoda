<%@ include file="/html/common/init.jsp" %>

<c:url var="actionUrl" value="/controlpanel/content/${contentEditCommand.contentId}/edit/uploadImage"/>

<form action="${actionUrl}" method="POST" enctype="multipart/form-data">
	<input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>

	<label for="file">File</label>

	<input id="file" type="file" name="file" />

	<p>
		<button type="submit">Upload</button>
	</p>
</form>