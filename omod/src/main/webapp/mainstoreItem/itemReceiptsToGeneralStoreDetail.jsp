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
<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<span class="boxHeader"><spring:message code="ehrinventory.receiptItem.receiptItemList"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>S.No</th>
	<th><spring:message code="ehrinventory.item.subCategory"/></th>
	<th><spring:message code="ehrinventory.item.name"/></th>
	<th><spring:message code="ehrinventory.item.specification"/></th>
	<th><spring:message code="ehrinventory.receiptItem.quantity"/></th>
	<th><spring:message code="ehrinventory.receiptItem.unitPrice"/></th>
	<th><spring:message code="ehrinventory.receiptItem.VAT"/></th>
	<th><spring:message code="ehrinventory.receiptItem.costToPatient"/></th>
<!-- 	<th><spring:message code="ehrinventory.receiptItem.totalPrice"/></th> -->
	<th title="<spring:message code="ehrinventory.receiptItem.companyName"/>">CN</th>
	<th title="<spring:message code="ehrinventory.receiptItem.dateManufacture"/>">DM</th>
	<th title="<spring:message code="ehrinventory.receiptItem.receiptDate"/>">RD</th>
	</tr>
	<c:choose>
	<c:when test="${not empty transactionDetails}">
	<c:forEach items="${transactionDetails}" var="receipt" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${ varStatus.count }"/></td>
		<td>${receipt.item.subCategory.name} </td>	
		<td>${receipt.item.name}</td>
		<td>${receipt.specification.name}</td>
		<td>${receipt.quantity}</td>
		<td>${receipt.unitPrice}</td>
		<td>${receipt.VAT}</td>
		<td>${receipt.costToPatient}</td>
		<%-- <td>${receipt.totalPrice}</td> --%>
		<td>${receipt.companyName}</td>
		<td><openmrs:formatDate date="${receipt.dateManufacture}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.receiptDate}" type="textbox"/></td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
</div>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptItem.print"/>" onClick="INDENT.printDiv();" />
<!-- PRINT DIV -->
<div  id="printDiv" style="display: none; ">        		
<div style="margin: 10px auto; width: 981px; font-size: 1.0em;font-family:'Dot Matrix Normal',Arial,Helvetica,sans-serif;">
<br />
<br />      		
<center style="float:center;font-size: 2.2em">${store.name} - Receipt - Items</center>
<br/>
<br/>
<span style="float:right;font-size: 1.7em">Date: <openmrs:formatDate date="${date}" type="textbox"/></span>
<br />
<br />
<table border="1">
	<tr align="center">
	<th>S.No</th>
	<th><spring:message code="ehrinventory.item.subCategory"/></th>
	<th><spring:message code="ehrinventory.item.name"/></th>
	<th><spring:message code="ehrinventory.item.specification"/></th>
	<th><spring:message code="ehrinventory.receiptItem.quantity"/></th>
	<th><spring:message code="ehrinventory.receiptItem.unitPrice"/></th>
	<th><spring:message code="ehrinventory.receiptItem.VAT"/></th>
	<th><spring:message code="ehrinventory.receiptItem.costToPatient"/></th>
	<!-- <th><spring:message code="ehrinventory.receiptItem.totalPrice"/></th> -->
	<th>CN</th>
	<th>DM</th>
	<th>DR</th>
	</tr>
	<c:choose>
	<c:when test="${not empty transactionDetails}">
	<c:forEach items="${transactionDetails}" var="receipt" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${ varStatus.count }"/></td>
		<td>${receipt.item.subCategory.name} </td>	
		<td>${receipt.item.name}</td>
		<td>${receipt.specification.name}</td>
		<td>${receipt.quantity}</td>
		<td>${receipt.unitPrice}</td>
		<td>${receipt.VAT}</td>
		<td>${receipt.costToPatient}</td>
		<%-- <td>${receipt.totalPrice}</td> --%>
		<td>${receipt.companyName}</td>
		<td><openmrs:formatDate date="${receipt.dateManufacture}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.receiptDate}" type="textbox"/></td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
<br/><br/><br/><br/><br/><br/>
<span style="float:right;font-size: 1.5em">Signature of Inventory Clerk/ Stamp</span>
</div>
</div>
<!-- END PRINT DIV -->   
