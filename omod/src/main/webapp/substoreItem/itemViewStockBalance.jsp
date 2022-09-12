<%--
/**
* The contents of this file are subject to the OpenMRS Public License
* Version 1.0 (the "License"); you may not use this file except in
* compliance with the License. You may obtain a copy of the License at
* http://license.openmrs.org
*
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
* License for the specific language governing rights and limitations
* under the License.
*
* Copyright (C) OpenMRS, LLC.  All Rights Reserved.
*/
--%>
<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Add/Edit substore" otherwise="/login.htm" redirect="/module/ehrinventory/main.form" />
<spring:message var="pageTitle" code="ehrinventory.substoreItem.viewitemStockBalance.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="nav.jsp" %>
<h2><spring:message code="ehrinventory.substoreItem.viewitemStockBalance.manage"/></h2>
<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<br /><br />
<form method="get"  id="form">
<table >
	<tr>
		<td><spring:message code="ehrinventory.viewStockBalance.subCategory"/></td>
		<td>
			<select name="categoryId" id="categoryId"  style="width: 250px;">
      			<option value=""></option>
				<c:forEach items="${listCategory}" var="category">
					<option value="${category.id}" 
					<c:if test="${category.id == categoryId }">selected</c:if>
					>${category.name}</option>
				</c:forEach>
	   		</select>
		 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</td>
		<td><spring:message code="ehrinventory.receiptItem.itemName"/></td>
		<td>
			<input type="text" name="itemName" id="itemName" value="${itemName }"/><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td>Attribute</td>
		<td>
			<input type="text" name="attribute" id="attribute" value="${attribute }"/><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</td><!--
		<td><spring:message code="ehrinventory.fromDate"/></td>
		<td><input type="text" id="fromDate" class="date-pick left" readonly="readonly" name="fromDate" value="${fromDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><spring:message code="ehrinventory.toDate"/></td>
		<td><input type="text" id="toDate" class="date-pick left" readonly="readonly" name="toDate" value="${toDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		--><td><input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search"/></td>
	</tr>
</table>
<br />
<span class="boxHeader"><spring:message code="ehrinventory.viewStockBalance.list"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>S.No</th>
	<th>Item Name</th>
	<th><spring:message code="ehrinventory.viewStockBalance.subCategory"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.specification"/></th>
	<!--
	<th ><spring:message code="ehrinventory.viewStockBalance.receiptQty"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.STTSS"/></th>
	-->
	<th ><spring:message code="ehrinventory.receiptItem.currentQuantity"/></th>
	<th >Attribute</th>
	</tr>
	<c:choose>
	<c:when test="${not empty stockBalances}">
	<c:forEach items="${stockBalances}" var="balance" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" }'>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td><a href="#" onclick="STOCKBALLANCE.detailSubStoreItem('${balance.item.id}', '${balance.specification.id}');" title="Detail all transactions of this item">${balance.item.name}</a></td>
		<td>${balance.item.subCategory.name} </td>	
		<td>${balance.specification.name}</td>
		<!--
		<td>${balance.quantity}</td>
		<td>${balance.issueQuantity}</td>
		-->
		<td>${balance.currentQuantity}</td>
        <td>${balance.attribute}</td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
	
	<tr class="paging-container">
	<td colspan="5"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</div>

</form>




<%@ include file="/WEB-INF/template/footer.jsp" %>