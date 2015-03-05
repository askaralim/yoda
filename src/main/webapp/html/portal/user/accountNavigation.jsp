<%@ include file="/html/common/init.jsp"%>

<c:set var="customer" scope="session" value="${customer}" />

<table width="200" border="0" cellspacing="0" cellpadding="3">
	<tr>
		<td class="jc_tran_border" style="border-width: 0px 0px 0px 0px">
			<span class="jc_tran_heading_text">My Account</span>
		</td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 0px 0px 0px 0px">
			<span class="jc_tran_title_text">
				<c:out value="${customer.custFirstName}" />
				&nbsp;
				<c:out value="${customer.custLastName}" />
			</span>
		</td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 1px 1px 0px 1px" onmouseover="this.className='jc_tran_menu_row_over'" onmouseout="this.className='jc_tran_menu_row'">
			<html:link page="/content/checkout/shoppingCart.do?process=start" styleClass="jc_tran_link">
				My Cart - view my current shopping cart order
			</html:link>
		</td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 1px 1px 1px 1px" onmouseover="this.className='jc_tran_menu_row_over'" onmouseout="this.className='jc_tran_menu_row'">
			<html:link page="/myaccount/login/myAccountLogout.do?process=logout" styleClass="jc_tran_link">
				Sign out
			</html:link>
		</td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 0px 0px 0px 0px">&nbsp;</td>
	</tr>
	<tr>
		<td><div class="jc_tran_title_text">My Account Settings</div></td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 1px 1px 0px 1px" onmouseover="this.className='jc_tran_menu_row_over'" onmouseout="this.className='jc_tran_menu_row'">
			<a href="/account/identity/accountIdentity" class="jc_tran_link">
				Email and Password
			</a>
			<!-- <html:link page="/myaccount/identity/myAccountIdentity.do?process=start" styleClass="jc_tran_link">
				Email and Password
			</html:link> -->
		</td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 1px 1px 0px 1px" onmouseover="this.className='jc_tran_menu_row_over'" onmouseout="this.className='jc_tran_menu_row'">
			<a href="/account/shipinfo/accountShipInfo" class="jc_tran_link">
				Shipping Information
			</a>
			<!-- <html:link page="/myaccount/shipinfo/myAccountShipInfo.do?process=start" styleClass="jc_tran_link">
				Shipping Information
			</html:link> -->
		</td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 1px 1px 1px 1px" onmouseover="this.className='jc_tran_menu_row_over'" onmouseout="this.className='jc_tran_menu_row'">
			<a href="/account/payment/accountPayment" class="jc_tran_link">
				Payment Information
			</a>
			<!-- <html:link page="/myaccount/payment/myAccountPayment.do?process=start" styleClass="jc_tran_link">
				Payment Information
			</html:link> -->
		</td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 0px 0px 0px 0px">&nbsp;</td>
	</tr>
	<tr>
		<td style="border-width: 1px 1px 1px 1px">
			<div class="jc_tran_title_text">My Orders</div>
		</td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 1px 1px 0px 1px" onmouseover="this.className='jc_tran_menu_row_over'" onmouseout="this.className='jc_tran_menu_row'">
			<a href="/account/order/accountOrderStatusList?process=list&srPageNo=1" class="jc_tran_link">
				Order Status - track and view status of current orders
			</a>
			<!-- <html:link page="/myaccount/order/myAccountOrderStatusListing.do?process=list&srPageNo=1" styleClass="jc_tran_link">
				Order Status - track and view status of current orders
			</html:link> -->
		</td>
	</tr>
	<tr>
		<td class="jc_tran_menu_row" style="border-width: 1px 1px 1px 1px" onmouseover="this.className='jc_tran_menu_row_over'" onmouseout="this.className='jc_tran_menu_row'">
			<a href="/account/order/myAccountOrderHistoryList?process=list&srPageNo=1" class="jc_tran_link">
				Order History - view history of past orders
			</a>
			<!-- <html:link page="/myaccount/order/myAccountOrderHistoryListing.do?process=list&srPageNo=1" styleClass="jc_tran_link">
				Order History - view history of past orders
			</html:link> -->
		</td>
	</tr>
</table>