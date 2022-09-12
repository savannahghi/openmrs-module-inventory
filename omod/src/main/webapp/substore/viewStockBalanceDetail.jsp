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
<spring:message var="pageTitle" code="ehrinventory.viewStockBalance.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="nav.jsp" %>

<span class="boxHeader"><spring:message code="ehrinventory.viewStockBalance.detail"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>S.No</th>
	<th><spring:message code="ehrinventory.viewStockBalance.name"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.category"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.formulation"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.transaction"/></th>
	<th ><spring:message code="ehrinventory.viewStockBalance.openingBalance"/></th>
	<th ><spring:message code="ehrinventory.viewStockBalance.receiptQty"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.STTSS"/></th>
	<th ><spring:message code="ehrinventory.receiptDrug.closingBalance"/></th>
	<th ><spring:message code="ehrinventory.receiptDrug.dateExpiry"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.receiptIssueDate"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listViewStockBalance}">
	<c:forEach items="${listViewStockBalance}" var="balance" varStatus="varStatus">
	<tr  align="center" class='${balance.expiryLessThan3Month == 1 ?" lessThan3Month " : ""}${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${balance.drug.name}</td>
		<td>${balance.drug.category.name} </td>	
		<td>${balance.formulation.name}-${balance.formulation.dozage}</td>
		<td>${balance.transaction.typeTransactionName}</td>
		<td>${balance.openingBalance}</td>
		<td>${balance.quantity }</td>
		<td>${balance.issueQuantity}</td>
		<td>${balance.closingBalance}</td>
		<td><openmrs:formatDate date="${balance.dateExpiry}" type="textbox"/></td>
		<td><openmrs:formatDate date="${balance.createdOn}" type="textbox"/></td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
</div>
<%@ include file="/WEB-INF/template/footer.jsp" %>