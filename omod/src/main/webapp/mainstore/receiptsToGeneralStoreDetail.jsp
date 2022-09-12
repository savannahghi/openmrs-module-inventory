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
<span class="boxHeader"><spring:message code="ehrinventory.receiptDrug.receiptDrugList"/></span>
<div class="box">
<table width="100%" border="1" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>S.No</th>
	<th><spring:message code="ehrinventory.drug.category"/></th>
	<th><spring:message code="ehrinventory.drug.name"/></th>
	<th><spring:message code="ehrinventory.drug.formulation"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.receiptQuantity"/></th>
	<th>Unit Price</th>
	<th><spring:message code="ehrinventory.receiptDrug.VAT"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.costToPatient"/></th>
	<!-- <th><spring:message code="ehrinventory.receiptDrug.totalPrice"/></th> -->
	<th><spring:message code="ehrinventory.receiptDrug.batchNo"/></th>
	<th title="<spring:message code="ehrinventory.receiptDrug.companyName"/>">CN</th>
	<th title="<spring:message code="ehrinventory.receiptDrug.dateManufacture"/>">DM</th>
	<th title="<spring:message code="ehrinventory.receiptDrug.dateExpiry"/>">DE</th>
	<th title="<spring:message code="ehrinventory.receiptDrug.receiptDate"/>">RD</th>
	<!-- Sagar Bele : Date - 22-01-2013 Issue Number 660 : [Inventory] Add receipt from field in Table and front end -->	
	<th title="<spring:message code="ehrinventory.receiptDrug.receiptFrom"/>">RF</th>
	</tr>
	<c:choose>
	<c:when test="${not empty transactionDetails}">
	<c:forEach items="${transactionDetails}" var="receipt" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${receipt.drug.category.name} </td>	
		<td>${receipt.drug.name}</td>
		<td>${receipt.formulation.name}-${receipt.formulation.dozage}</td>
		<td>${receipt.quantity}</td>
		<td>${receipt.unitPrice}</td>
		<td>${receipt.VAT}</td>
		<td>${receipt.costToPatient}</td>
		<%-- <td>${receipt.totalPrice}</td> --%>
		<td>${receipt.batchNo}</td>
		<td>${receipt.companyName}</td>
		<td><openmrs:formatDate date="${receipt.dateManufacture}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.dateExpiry}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.receiptDate}" type="textbox"/></td>
		<td>${receipt.receiptFrom}</td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
</div>

<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptDrug.print"/>" onClick="INDENT.printDiv();" />

<!-- PRINT DIV -->
<div  id="printDiv" style="display: none;">
<div style="width: 100%; float: right; margin-right: 4px; font-size: 1.0em;font-family:'Dot Matrix Normal',Arial,Helvetica,sans-serif;">        		
<br />
<br />      		
<center style="float:center;font-size: 2.2em">${store.name} - Receipt - Drugs</center>
<br/>
<br/>
<span style="float:right;font-size: 1.7em">Date: <openmrs:formatDate date="${date}" type="textbox"/></span>
<br />
<br />
<table border="1">
	<tr align="center">
	<th>S.No</th>
	<th><spring:message code="ehrinventory.drug.category"/></th>
	<th><spring:message code="ehrinventory.drug.name"/></th>
	<th><spring:message code="ehrinventory.drug.formulation"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.quantity"/></th>
	<th>Unit Price</th>
	<th><spring:message code="ehrinventory.receiptDrug.VAT"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.costToPatient"/></th>
	<!-- <th><spring:message code="ehrinventory.receiptDrug.totalPrice"/></th> -->
	<th><spring:message code="ehrinventory.receiptDrug.batchNo"/></th>
	<th>CN</th>
	<th>DM</th>
	<th>DE</th>
	<th>DR</th>
	<th>RF</th>
	</tr>
	<c:choose>
	<c:when test="${not empty transactionDetails}">
	<c:forEach items="${transactionDetails}" var="receipt" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td>${receipt.drug.category.name} </td>	
		<td>${receipt.drug.name}</td>
		<td>${receipt.formulation.name}-${receipt.formulation.dozage}</td>
		<td>${receipt.quantity}</td>
		<td>${receipt.unitPrice}</td>
		<td>${receipt.VAT}</td>
		<td>${receipt.costToPatient}</td>
		<%-- <td>${receipt.totalPrice}</td> --%>
		<td>${receipt.batchNo}</td>
		<td>${receipt.companyName}</td>
		<td><openmrs:formatDate date="${receipt.dateManufacture}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.dateExpiry}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.receiptDate}" type="textbox"/></td>
		<!-- Sagar Bele : Date - 22-01-2013 Issue Number 660 : [Inventory] Add receipt from field in Table and front end -->	
		<td>${receipt.receiptFrom}</td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
<br/><br/><br/><br/><br/><br/>
<span style="float:right;font-size: 1.5em">Signature of inventory clerk/ Stamp</span>
</div>
</div>
<!-- END PRINT DIV -->   
