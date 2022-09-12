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
<spring:message var="pageTitle" code="ehrinventory.receiptDrug.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="nav.jsp" %>
<h2><spring:message code="ehrinventory.receiptDrug.manage"/></h2>
<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='ehrinventory.receiptDrug.add'/>" onclick="ACT.go('receiptsToGeneralStore.form');"/>
<br /><br />

<form method="get"  id="form">
<table >
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.description"/></td>
		<td>
			<input type="text" name="receiptName" id="receiptName" value="${receiptName }"/>
		</td>
		<td><spring:message code="ehrinventory.fromDate"/></td>
		<td><input type="text" id="fromDate" class="date-pick left" readonly="readonly" name="fromDate" value="${fromDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><spring:message code="ehrinventory.toDate"/></td>
		<td><input type="text" id="toDate" class="date-pick left" readonly="readonly" name="toDate" value="${toDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search"/></td>
	</tr>
</table>
<br />
<span class="boxHeader"><spring:message code="inventory.receiptDrug.receiptDrugList"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>S.No</th>
	<th><spring:message code="inventory.receiptDrug.description"/></th>
	<th><spring:message code="inventory.receiptDrug.createdOn"/></th>
	<!--<th><spring:message code="inventory.receiptDrug.number"/></th>
	--></tr>
	<c:choose>
	<c:when test="${not empty transactions}">
	<c:forEach items="${transactions}" var="receipt" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td><a href="#" title="Detail indent" onclick="RECEIPT.detailReceiptDrug('${ receipt.id}');">${receipt.description}</a> </td>	
		<td><openmrs:formatDate date="${receipt.createdOn}" type="textbox"/></td>
		<!--<td>${receipt.id} </td>	
		--></tr>
	</c:forEach>
	</c:when>
	</c:choose>
<tr class="paging-container">
	<td colspan="4"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</div>

</form>




<%@ include file="/WEB-INF/template/footer.jsp" %>