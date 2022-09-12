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

<openmrs:require privilege="Add/Edit mainstore" otherwise="/login.htm" redirect="/module/ehrinventory/main.form" />
<spring:message var="pageTitle" code="ehrinventory.viewStockBalance.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="nav.jsp" %>
<h2><spring:message code="ehrinventory.viewStockBalance.manage"/></h2>
<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<br /><br />
<form method="get"  id="form">
<table >
	<tr >
		<td><spring:message code="ehrinventory.receiptDrug.category"/></td>
		<td>
			<select name="categoryId" id="categoryId"  style="width: 250px;">
      			<option value=""></option>
				<c:forEach items="${listCategory}" var="category">
					<option value="${category.id}" title="${category.name}"
					<c:if test="${category.id == categoryId }">selected</c:if>
					>${category.name}</option>
				</c:forEach>
	   		</select>
		</td>
		<td><spring:message code="ehrinventory.receiptDrug.drugName"/></td>
		<td>
			<input type="text" name="drugName" id="drugName" value="${drugName }"/>
		</td>
		<!--<td><spring:message code="ehrinventory.fromDate"/></td>
		<td><input type="text" id="fromDate" class="date-pick left" readonly="readonly" name="fromDate" value="${fromDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><spring:message code="ehrinventory.toDate"/></td>
		<td><input type="text" id="toDate" class="date-pick left" readonly="readonly" name="toDate" value="${toDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		-->
		<td><input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search"/></td>
	</tr>
</table>
<span class="boxHeader"><spring:message code="ehrinventory.viewStockBalance.list"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr  align="center" >
	<th>S.No</th>
	<th><spring:message code="ehrinventory.viewStockBalance.name"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.category"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.formulation"/></th>
	<!--
		<th ><spring:message code="ehrinventory.viewStockBalance.receiptQty"/></th>
		<th><spring:message code="ehrinventory.viewStockBalance.STTSS"/></th>
	-->
	<th ><spring:message code="ehrinventory.receiptDrug.currentQuantity"/></th>
	<th ><spring:message code="ehrinventory.viewStockBalance.reorderPoint"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty stockBalances}">
	<c:forEach items="${stockBalances}" var="balance" varStatus="varStatus">
	<tr  align="center"  class='${balance.currentQuantity < balance.drug.reorderQty ?" reorder " : ""}${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" }' >
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td><a href="#" onclick="STOCKBALLANCE.detail('${balance.drug.id}', '${balance.formulation.id}');" title="Detail all transactions of this drug">${balance.drug.name}</a></td>
		<td>${balance.drug.category.name} </td>	
		<td>${balance.formulation.name}-${balance.formulation.dozage}</td>
		<!--
		<td>${balance.quantity}</td>
		<td>${balance.issueQuantity}</td>
		-->
		<td>${balance.currentQuantity}</td>
		<td>${balance.drug.reorderQty}</td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
	
	<tr class="paging-container">
	<td colspan="6"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</div>

</form>




<%@ include file="/WEB-INF/template/footer.jsp" %>