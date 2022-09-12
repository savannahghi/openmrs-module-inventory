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

<span class="boxHeader">Issue Drugs Detail</span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>S.No</th>
	<th><spring:message code="ehrinventory.viewStockBalance.category"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.drug"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.formulation"/></th>
	<th ><spring:message code="ehrinventory.receiptDrug.dateExpiry"/></th>
	<th><spring:message code="ehrinventory.issueDrug.quantity"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listDrugIssue}">
	<c:forEach items="${listDrugIssue}" var="detail" varStatus="varStatus">
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${detail.transactionDetail.drug.category.name} </td>	
		<td>${detail.transactionDetail.drug.name} </td>	
		<td>${detail.transactionDetail.formulation.name}-${detail.transactionDetail.formulation.dozage}</td>
		<td><openmrs:formatDate date="${detail.transactionDetail.dateExpiry}" type="textbox"/></td>
		<td>${detail.quantity }</td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
</div>

<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptItem.print"/>" onClick="INDENT.printDiv();" />

<!-- PRINT DIV -->
<div  id="printDiv" style="display: none;">        		
<div style="margin: 10px auto; width: 981px; font-size: 1.0em;font-family:'Dot Matrix Normal',Arial,Helvetica,sans-serif;">
<c:if  test="${not empty issueDrugAccount}">
<br />
<br />      		
<center style="float:center;font-size: 2.2em">Issue Drugs To Account ${issueDrugAccount.name }</center>
<br/>
<br/>
<span style="float:right;font-size: 1.7em">Date: <openmrs:formatDate date="${date}" type="textbox"/></span>
<br />
<br />
</c:if>
<table border="1">
	<tr>
	<th>S.No</th>
	<th><spring:message code="ehrinventory.drug.category"/></th>
	<th><spring:message code="ehrinventory.drug.name"/></th>
	<th><spring:message code="ehrinventory.drug.formulation"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.quantity"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listDrugIssue}">
	<c:forEach items="${listDrugIssue}" var="issue" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${issue.transactionDetail.drug.category.name} </td>	
		<td>${issue.transactionDetail.drug.name}</td>
		<td>${issue.transactionDetail.formulation.name}-${issue.transactionDetail.formulation.dozage}</td>
		<td>${issue.quantity}</td>
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