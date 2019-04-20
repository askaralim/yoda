<link rel="stylesheet" href="<c:url value="/resources/web/yui_combine.css" />" type="text/css" />
<link rel="stylesheet" href="<c:url value="/resources/web/styles.css" />" type="text/css" />
<script type="text/javascript" src='<c:url value="/resources/web/yui_combine.js" />' ></script>
<script type="text/javascript" src='<c:url value="/resources/web/utility/javacontent.js" />' ></script>
<script>
var jc_item_search_panel = null;

function showItemPickWindow() {
	jc_item_search_panel.show();
}

function jc_item_search_client_callback(value) {
	var response = eval('(' + value + ')');
	document.getElementById('itemNum_container').innerHTML = response.items[0].itemNum;
	document.getElementById('itemShortDesc_container').innerHTML = response.items[0].itemShortDesc;
	document.menuEditCommand.itemId.value = response.items[0].itemId;
	document.menuEditCommand.itemNum.value = response.items[0].itemNum;
	document.menuEditCommand.itemShortDesc.value = response.items[0].itemShortDesc;

	jc_item_search_panel.hide();
	return false;
}

var jc_content_search_panel = null;

function showContentPickWindow() {
	jc_content_search_panel.show();
}

function jc_content_search_client_callback(value) {
	var response = eval('(' + value + ')');
	document.getElementById('contentTitle_container').innerHTML = response.contents[0].contentTitle;
	//document.menuEditCommand.contentId.value = response.contents[0].contentId;
	//document.menuEditCommand.contentTitle.value = response.contents[0].contentTitle;
	document.getElementById("contentId").value = response.contents[0].contentId;
	document.getElementById("contentTitle").value = response.contents[0].contentTitle;

	jc_content_search_panel.hide();
	return false;
}

var jc_section_search_panel = null;

function showSectionPickWindow() {
	jc_section_search_panel.show();
}

function jc_section_search_client_callback(value) {
	var response = eval('(' + value + ')');
	if (response.sections[0].sectionShortTitle == 'Home') {
		return false;
	}
	document.getElementById('sectionShortTitle_container').innerHTML = response.sections[0].sectionShortTitle;
	//document.menuEditCommand.sectionId.value = response.sections[0].sectionId;
	//document.menuEditCommand.sectionShortTitle.value = response.sections[0].sectionShortTitle;

	document.getElementById("sectionId").value = response.sections[0].sectionId;
	document.getElementById("sectionShortTitle").value = response.sections[0].sectionShortTitle;

	jc_section_search_panel.hide();
	return false;
}

function init_expand() {
	var menuType = document.menuEditCommand.menuType;
	for (i = 0; i < menuType.length; i++) {
		if (menuType[i].checked) {
			expand(i + 1);
		}
	}
}
YAHOO.util.Event.onDOMReady(init_expand);

function jc_item_search_init() {
	jc_item_search_panel = new YAHOO.widget.Panel("jc_item_search_panel", {
		width : "500px",
		visible : false,
		constraintoviewport : true,
		fixedcenter : true,
		modal : true
	});
	jc_item_search_panel.render();
}

YAHOO.util.Event.onDOMReady(jc_item_search_init);

var handleFailure = function(o) {
	var message = document.getElementById("jc_item_search_message");
	message.innerHTML = "Error retrieving item list";
}

var handleSuccess = function(o) {
	if (o.responseText == undefined) {
		return;
	}
	var table = document.getElementById("jc_item_search_table");
	while (table.rows.length > 0) {
		table.deleteRow(0);
	}

	var object = eval('(' + o.responseText + ')');
	var message = document.getElementById("jc_item_search_message");
	if (object.message) {
		message.innerHTML = object.message;
	} else {
		message.innerHTML = "";
	}
	var index = 0;
	for (i = 0; i < object.items.length; i++) {
		table.insertRow(table.rows.length);
		var row = table.rows[table.rows.length - 1];
		var cell = null;
		var itemId = object.items[index].itemId;
		var itemNum = object.items[index].itemNum;
		var itemShortDesc = object.items[index].itemShortDesc;
		cell = row.insertCell(0);
		cell.width = "100px";
		cell.innerHTML = "<a href='javascript:void(0)' onclick='jc_item_search_callback_single(\""
				+ itemId
				+ "\", \""
				+ itemNum
				+ "\", \""
				+ itemShortDesc
				+ "\")'>" + object.items[index].itemNum + "</a>";
		cell = row.insertCell(1);
		cell.width = "100px";
		cell.innerHTML = object.items[index].itemUpcCd;
		cell = row.insertCell(2);
		cell.innerHTML = object.items[index].itemShortDesc;
		index++;
	}

	var div = document.getElementById("jc_item_search_div");
	div.style.display = "block";
}

var jc_item_search_callback = {
	success : handleSuccess,
	failure : handleFailure
};

function jc_item_search_get() {
	var url = '<spring:url value="/controlpanel/lookup/itemLookup" htmlEscape="true" />';
	var postData = ""
	postData += "itemNum=" + document.jc_item_search_form.itemNum.value + "&";
	postData += "itemUpcCd=" + document.jc_item_search_form.itemUpcCd.value
			+ "&";
	postData += "itemShortDesc="
			+ document.jc_item_search_form.itemShortDesc.value;
	var request = YAHOO.util.Connect.asyncRequest('POST', url,
			jc_item_search_callback, postData);
}

function jc_item_search_callback_single(itemId, itemNum, itemShortDesc) {
	var result = '{"items": [{"itemId": "' + itemId + '", "itemNum": "'
			+ itemNum + '", "itemShortDesc": "' + itemShortDesc + '"}]}';

	jc_item_search_client_callback(result);
}

function jc_item_search_callback_multiple() {
}
</script>
<div class="yui-skin-sam">
	<div>
		<div id="jc_item_search_panel">
			<div class="hd">Item search</div>
			<div class="bd">
				<form name="jc_item_search_form" method="post" action="javascript:void(0)">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><span class="jc_input_label">Item number</span></td>
							<td nowrap>
								<a href="javascript:void(0);" onclick="jc_item_search_get()" class="jc_navigation_link">
									Pick item
								</a>
							</td>
						</tr>
						<tr>
							<td>
								<input type="text" name="itemNum" class="jc_input_control" size="20">
							</td>
							<td></td>
						</tr>
						<tr>
							<td><span class="jc_input_label">Item upc code</span></td>
							<td></td>
						</tr>
						<tr>
							<td>
								<input type="text" name="itemUpcCd" class="jc_input_control" size="20">
							</td>
							<td></td>
						</tr>
						<tr>
							<td><span class="jc_input_label">Item description</span></td>
							<td></td>
						</tr>
						<tr>
							<td>
								<input type="text" name="itemShortDesc" class="jc_input_control" size="40">
							</td>
							<td></td>
						</tr>
					</table>
					<div id="jc_item_search_div">
						<hr>
						<div id="jc_item_search_message" class="jc_input_error"></div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100px" nowrap>
									<span class="jc_input_label">
										Item number
									</span>
								</td>
								<td width="100px" nowrap>
									<span class="jc_input_label">
										Upc Code
									</span>
								</td>
								<td><span class="jc_input_label">Description</span></td>
							</tr>
						</table>
						<div style="height: 200px; overflow: auto; vertical-align: top">
							<table id="jc_item_search_table" width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="100px"></td>
									<td width="100px"></td>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
function jc_content_search_init() {
	jc_content_search_panel = new YAHOO.widget.Panel("jc_content_search_panel",
			{
				width : "500px",
				visible : false,
				constraintoviewport : true,
				fixedcenter : true,
				modal : true
			});
	jc_content_search_panel.render();
}
YAHOO.util.Event.onDOMReady(jc_content_search_init);

var handleFailure = function(o) {
	var message = document.getElementById("jc_content_search_message");
	message.innerHTML = "Error retrieving content list";
}

var handleSuccess = function(o) {
	if (o.responseText == undefined) {
		return;
	}
	var table = document.getElementById("jc_content_search_table");
	while (table.rows.length > 0) {
		table.deleteRow(0);
	}

	var object = eval('(' + o.responseText + ')');
	var message = document.getElementById("jc_content_search_message");
	if (object.message) {
		message.innerHTML = object.message;
	} else {
		message.innerHTML = "";
	}
	var index = 0;
	for (i = 0; i < object.contents.length; i++) {
		table.insertRow(table.rows.length);
		var row = table.rows[table.rows.length - 1];
		var cell = null;
		var contentId = object.contents[index].contentId;
		var contentTitle = object.contents[index].contentTitle;
		cell = row.insertCell(0);
		cell.width = "100%";
		cell.innerHTML = "<a href='javascript:void(0)' onclick='jc_content_search_callback_single(\""
				+ contentId
				+ "\", \""
				+ contentTitle
				+ "\")'>"
				+ object.contents[index].contentTitle + "</a>";
		index++;
	}

	var div = document.getElementById("jc_content_search_div");
	div.style.display = "block";
}

var jc_content_search_callback = {
	success : handleSuccess,
	failure : handleFailure
};

function jc_content_search_get() {
	var url = '<spring:url value="/controlpanel/lookup/contentLookup" htmlEscape="true" />';
	var postData = ""
	postData += "contentTitle="
			+ document.jc_content_search_form.contentTitle.value;
	var request = YAHOO.util.Connect.asyncRequest('POST', url,
			jc_content_search_callback, postData);
}

function jc_content_search_callback_single(contentId, contentTitle) {
	var result = '{"contents": [';
	result += '{"contentId": "' + contentId + '", "contentTitle": "'
			+ contentTitle + '"}';
	result += ']}';
	jc_content_search_client_callback(result);
}

function jc_content_search_callback_multiple() {
}
</script>
<div class=" yui-skin-sam">
	<div>
		<div id="jc_content_search_panel">
			<div class="hd">Content search</div>
			<div class="bd">
				<form name="jc_content_search_form" method="post" action="javascript:void(0)">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><span class="jc_input_label">Content Title</span></td>
							<td nowrap>
								<a href="javascript:void(0);" onclick="jc_content_search_get()" class="jc_navigation_link">
									Pick content
								</a>
							</td>
						</tr>
						<tr>
							<td>
								<input type="text" name="contentTitle" class="jc_input_control" size="40">
							</td>
							<td></td>
						</tr>
					</table>
					<div id="jc_content_search_div">
						<hr>
						<div id="jc_content_search_message" class="jc_input_error"></div>
						<div style="height: 200px; overflow: auto; vertical-align: top">
							<table id="jc_content_search_table" width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="100%"></td>
								</tr>
							</table>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
function jc_section_search_init() {
	jc_section_search_panel = new YAHOO.widget.Panel("jc_section_search_panel",
			{
				width : "500px",
				visible : false,
				constraintoviewport : true,
				fixedcenter : true,
				modal : true
			});
	jc_section_search_panel.render();
	jc_section_search_get();
}
YAHOO.util.Event.onDOMReady(jc_section_search_init);

var handleFailure = function(o) {
	var message = document.getElementById("jc_section_search_message");
	message.innerHTML = "Error retrieving section list";
}

var handleSuccess = function(o) {
	if (o.responseText == undefined) {
		return;
	}
	var jsonObject = eval('(' + o.responseText + ')');
	var tree = new YAHOO.widget.TreeView("jc_section_search_tree");
	var root = tree.getRoot();

	jc_section_search_addnode(root, jsonObject);
	tree.draw();
}

function jc_section_search_addnode(tree, jsonObject) {
	var url = "javascript:jc_section_search_callback_single(\""
			+ jsonObject.sectionId + "\", \"" + jsonObject.sectionShortTitle
			+ "\")";
	var nodeText = {
		label : jsonObject.sectionShortTitle,
		href : url
	};
	var node = new YAHOO.widget.TextNode(nodeText, tree, false);
	var i = 0;
	for (i = 0; i < jsonObject.sections.length; i++) {
		jc_section_search_addnode(node, jsonObject.sections[i]);
	}
}

var jc_section_search_callback = {
	success : handleSuccess,
	failure : handleFailure
};

function jc_section_search_get() {
	var url = '<spring:url value="/controlpanel/lookup/sectionLookup" htmlEscape="true" />';
	var request = YAHOO.util.Connect.asyncRequest('GET', url,
			jc_section_search_callback);
}

function jc_section_search_callback_single(sectionId, sectionShortTitle) {
	var result = '{"sections": [';
	result += '{"sectionId": "' + sectionId + '", "sectionShortTitle": "'
			+ sectionShortTitle + '"}';
	result += ']}';
	jc_section_search_client_callback(result);
}

function jc_section_search_callback_multiple() {}
</script>
<div class=" yui-skin-sam">
	<div>
		<div id="jc_section_search_panel">
			<div class="hd">Section search</div>
			<div class="bd">
				<div id="jc_section_search_message" class="jc_input_error"></div>
				<div id="jc_section_search_tree" style="height: 200px; overflow: auto; vertical-align: top"></div>
			</div>
		</div>
	</div>
</div>