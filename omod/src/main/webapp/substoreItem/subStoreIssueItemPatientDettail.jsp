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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<openmrs:globalProperty var="userLocation" key="hospital.location_user" defaultValue="false"/>

		<style>
@media print {
	.donotprint {
		display: none;
	}
	.spacer {
		margin-top: 100px;
		font-family: "Dot Matrix Normal", Arial, Helvetica, sans-serif;
		font-style: normal;
		font-size: 14px;
	}
	.printfont {
		font-family: "Dot Matrix Normal", Arial, Helvetica, sans-serif;
		font-style: normal;
		font-size: 14px;
	}
}
</style>
<span class="boxHeader">Issue items detail</span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0" >
	<tr align="center">
	<th>S.No</th>
	<th><spring:message code="ehrinventory.viewStockBalance.category"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.item"/></th>
	<th><spring:message code="ehrinventory.viewStockBalance.specification"/></th>
	<th><spring:message code="ehrinventory.issueDrug.quantity"/></th>
	<th><spring:message  text="Amount" /></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listItemPatientIssue}">
	<c:set var="total" value="${0}"/>
	<c:forEach items="${listItemPatientIssue}" var="detail" varStatus="varStatus">
		<%-- <c:set var="price" value="${ detail.quantity* (detail.transactionDetail.unitPrice + 0.01*detail.transactionDetail.VAT*detail.transactionDetail.unitPrice) }" /> --%>
		<c:set var="price" value="${ detail.quantity * detail.transactionDetail.costToPatient}" />
		<c:set var="generalVar" value="GENERAL"/>
		<c:set var="expectantVar" value="EXPACTANT MOTHER"/>
		<c:set var="tbVar" value="TB PATIENT"/>
		<c:set var="cccVar" value="CCC PATIENT"/>
		<c:set var="total" value="${total + price}"/>	
	
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${detail.transactionDetail.item.category.name} </td>	
		<td>${detail.transactionDetail.item.name} </td>	
		<td>${detail.transactionDetail.specification.name}</td>
		<td>${detail.quantity }</td>
		<td><fmt:formatNumber value="${price}" type="number" maxFractionDigits="2"/></td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
</div>

<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptItem.print"/>" onClick="ISSUE.printDivItem('${receiptid}','${flag }');" />

<!-- PRINT DIV -->
<div  id="printDiv" style="display: none; ">        		
<div style="width: 1280px; font-size: 0.8em">
<center><img width="100" height="100" align="center" title="OpenMRS" alt="OpenMRS" src="${pageContext.request.contextPath}/moduleResources/inventory/kenya_logo.bmp"><center>
  <table  class="spacer" style="margin-left: 60px;"> 		
<tr><h3><center><b><u>${userLocation}</u> </b></center></h3></tr>
<tr><h5><b><center>CASH RECEIPT</center></b></h5></tr>
</table>
<c:if  test="${not empty issueItemPatient}">
<table class="spacer" style="margin-left: 60px; margin-top: 40px;">
	<tr><td>Date/Time: </td><td>:${date}</td></tr>
	<tr><td>Name</td><td>:${issueItemPatient.patient.givenName}&nbsp;${issueItemPatient.patient.familyName}&nbsp;${fn:replace(issueItemPatient.patient.middleName,","," ")}</td></tr>
	<tr><td>Patient ID</td><td>:${issueItemPatient.identifier }</td></tr>
	<tr><td>Age</td><td>:${age}</td></tr>
	<tr><td>Gender</td><td>:${gender}</td></tr>
	<tr><td>Payment Category</td><td>:${paymentSubCategory}</td></tr>
	<tr><td>Waiver/Exempt. No.</td><td>:${exemption}</td></tr>
	
</table>
</c:if>
<table class="spacer" style="margin-left: 60px; margin-top: 40px;">
	<tr>
	<th>S.No</th>
	<th><spring:message code="ehrinventory.item.name"/></th>
	<th><spring:message code="ehrinventory.item.specification"/></th>
	<th><spring:message code="ehrinventory.receiptItem.quantity"/></th>
	<th><spring:message text="Amount" /></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listItemPatientIssue}">
	<c:set var="total" value="${0}"/>
	<c:forEach items="${listItemPatientIssue}" var="issue" varStatus="varStatus">
	
		<%-- <c:set var="price" value="${ issue.quantity* (issue.transactionDetail.unitPrice + 0.01*issue.transactionDetail.VAT*issue.transactionDetail.unitPrice) }" /> --%>
		<c:set var="price" value="${ issue.quantity * issue.transactionDetail.costToPatient}" />
		<c:set var="generalVar" value="GENERAL"/>
		<c:set var="expectantVar" value="EXPACTANT MOTHER"/>
		<c:set var="tbVar" value="TB PATIENT"/>
		<c:set var="cccVar" value="CCC PATIENT"/>
		<c:set var="total" value="${total + price}"/>	
			
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><center><c:out value="${varStatus.count }"/></center></td>
		<td><center>${issue.transactionDetail.item.name}</center></td>
		<td><center>${issue.transactionDetail.specification.name}</center></td>
		<td><center>${issue.quantity}</center></td>
		<td><center><fmt:formatNumber value="${price}" type="number" maxFractionDigits="2"/></center></td>
		</tr>
	</c:forEach>
	<tr><td>&nbsp;</td></tr>
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td></td>
		<td></td>
		<td></td>
		<td><b><spring:message text="Total" /></b></td>
		<td>	
			<c:choose>
				<c:when test ="${paymentSubCategory == generalVar}">
					<fmt:formatNumber value="${total}" type="number" maxFractionDigits="2"/>
				</c:when>
				<c:when test ="${paymentSubCategory == expectantVar}">
					<fmt:formatNumber value="${total}" type="number" maxFractionDigits="2"/>
				</c:when>
				<c:when test ="${paymentSubCategory == tbVar}">
					<fmt:formatNumber value="${total}" type="number" maxFractionDigits="2"/>
				</c:when>
				<c:when test ="${paymentSubCategory == cccVar}">
					<fmt:formatNumber value="${total}" type="number" maxFractionDigits="2"/>
				</c:when>
				<c:otherwise>
					<strike><fmt:formatNumber value="${total}" type="number" maxFractionDigits="2"/>
					</strike>  0.00
				</c:otherwise>
			</c:choose>
		</td>	

	</tr>	
	
	
	</c:when>
	</c:choose>
</table>
	<table  class="spacer" style="margin-left: 60px; margin-top: 40px;">
		<%-- <tr>
			<td>PAYMENT MODE </td>
			<td><b>:${paymentMode}</b></td>
		</tr> --%>
		<tr>
			<td>CASHIER </td>
			<td><b>:${cashier}</b></td>
		</tr>
	</table>

<br/><br/><br/><br/><br/><br/>
<span style="float:right;font-size: 1.5em">Signature of Inventory Clerk/ Stamp</span>
</div>
</div>
<!-- END PRINT DIV -->   
