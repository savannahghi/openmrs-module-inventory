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

<div style="max-height: 50px; max-width: 1800px;">
	<b class="boxHeader">Detail Issue</b>
</div>

<div id="patientDetails">	
	<table>
		<tr>
			<td>Patient ID :</td>
            <td>&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;${identifier}</td>
		</tr>
		<tr>
			<td>Name :</td><td>&nbsp;</td>
			<td>&nbsp;${givenName}&nbsp;
				${familyName}&nbsp;&nbsp;${fn:replace(middleName,","," ")}</td>
		</tr>
        <tr>
        	<td>Age:</td><td>&nbsp;</td>
        	<td>&nbsp;
        	<c:choose>
				<c:when test ="${age < 1}"> < 1 </c:when>
				<c:otherwise> ${age}</c:otherwise>	
			</c:choose>
			</td>
      </tr>
        <tr>
        	<td>Gender:</td><td>&nbsp;</td>
        	<td>&nbsp;${gender}</td>
        </tr>
        <tr>
        	<td>Payment Category:</td><td>&nbsp;</td>
        	<td>&nbsp;${paymentSubCategory}</td>
        </tr>
		<tr>
			<td>Date :</td><td>&nbsp;</td>
			<td>${date}</td>
		</tr>
	</table>
</div>

<span class="boxHeader">Issue Drugs Detail</span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th style="width:10px">S.No</th>
	
	<th style="width:10px"><spring:message code="ehrinventory.viewStockBalance.drug"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.viewStockBalance.formulation"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.viewStockBalance.frequency"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.issueDrug.noOfDays"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.issueDrug.comments"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.receiptDrug.dateExpiry"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.issueDrug.quantity"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.receiptDrug.price" text="Price" /></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listDrugIssue}">
	<c:set var="total" value="${0}"/>  
	<c:forEach items="${listDrugIssue}" var="detail" varStatus="varStatus">
	<%-- <c:set var="price" value="${ detail.quantity* (detail.transactionDetail.unitPrice + 0.01*detail.transactionDetail.VAT*detail.transactionDetail.unitPrice) }" /> --%>
	<c:set var="price" value="${ detail.quantity * detail.transactionDetail.costToPatient}" />
	<c:set var="generalVar" value="GENERAL"/>
	<c:set var="expectantVar" value="EXPECTANT MOTHER"/>
	<c:set var="tbVar" value="TB PATIENT"/>
	<c:set var="cccVar" value="CCC PATIENT"/>
	<c:set var="total" value="${total + price}"/>
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td style="width:10px"><c:out value="${varStatus.count }"/></td>
		
		<td style="width:10px">${detail.transactionDetail.drug.name} </td>	
		<td style="width:10px">${detail.transactionDetail.formulation.name}-${detail.transactionDetail.formulation.dozage}</td>
		<td style="width:10px">${detail.transactionDetail.frequency.name} </td>	
		<td style="width:10px">${detail.transactionDetail.noOfDays} </td>	
		<td style="width:10px">${detail.transactionDetail.comments} </td>
		<td style="width:10px"><openmrs:formatDate date="${detail.transactionDetail.dateExpiry}" type="textbox"/></td>
		<td style="width:10px">${detail.quantity }</td>
		<td style="width:10px"><fmt:formatNumber value="${price}" type="number" maxFractionDigits="2"/></td>
		</tr>
	</c:forEach>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<c:choose>
            <c:when test="${ not empty listOfNotDispensedOrder }">
			<td width="100%"><span class="boxHeader">Drugs not issued</span></td>
		   </c:when>
		</c:choose>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<c:forEach items="${listOfNotDispensedOrder}" var="nonDispensed" varStatus="varStatus">
		<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td style="width:10px"><c:out value="${varStatus.count }"/></td>
		<td style="width:10px">${nonDispensed.inventoryDrug.name} </td>	
		<td style="width:10px">${nonDispensed.inventoryDrugFormulation.name}-${nonDispensed.inventoryDrugFormulation.dozage} </td>
		<td style="width:10px">${nonDispensed.frequency.name}</td>
		<td style="width:10px">${nonDispensed.noOfDays}</td>
		<td style="width:10px">${nonDispensed.comments}</td>
		<td style="width:10px">N.A.</td>
		<td style="width:10px">N.A.</td>
		<td style="width:10px">N.A.</td>
		</tr>
	</c:forEach>
	<tr><td>&nbsp;</td></tr>
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td><b><spring:message code="ehrinventory.receiptDrug.total" text="Total Price" /></b></td>
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
	<%-- <tr>
			<td>Treating Doctor </td>
			<td><b>:${paymentMode}</b></td>
		</tr> --%>
		<tr>
			<td>Attending Cashier</td>
			<td><b>:${cashier}</b></td>
		</tr>
	</c:when>
	</c:choose>
</table>
</div>



<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptDrug.print" />"   onClick="ISSUE.printDiv('${receiptid}','${flag }');" />


<!-- PRINT DIV -->
<div style="max-height: 50px; max-width: 1800px;">
	<b class="boxHeader"></b>
</div>

<div  id="printDiv" style="display: none;  width: 1280px; font-size: 0.8em">
<br/>
<br/>     

<center><img width="100" height="100" align="center" title="OpenMRS" alt="OpenMRS" src="${pageContext.request.contextPath}/moduleResources/ehrinventory/kenya_logo.bmp"><center>
  <table  class="spacer" style="margin-left: 60px;"> 		
<tr><h3><center><b><u>${userLocation}</u> </b></center></h3></tr>
<tr><h5><b><center>CASH RECEIPT</center></b></h5></tr>
</table>
<br/>
<br/>
<c:if  test="${not empty listDrugIssue}">
<table class="spacer" style="margin-left: 60px;">
	<tr><td>Date/Time: </td><td>:${date}</td></tr>
	<tr><td>Name</td><td>:${issueDrugPatient.patient.givenName}&nbsp;${issueDrugPatient.patient.familyName}&nbsp;${fn:replace(issueDrugPatient.patient.middleName,","," ")} </td></tr>
	<tr><td>Patient ID</td><td>:${issueDrugPatient.identifier }</td></tr>
	<tr><tr>
        	<td>Age</td>
        	<td>:
        	<c:choose>
				<c:when test ="${age < 1}"> < 1 </c:when>
				<c:otherwise> ${age}</c:otherwise>	
			</c:choose>
			</td>
      </tr></tr>
	<tr><td>Gender</td><td>:${gender}</td></tr>
	<tr><td>Payment Category</td><td>:${paymentSubCategory}</td></tr>
	<!-- <tr><td>Waiver/Exempt. No.</td><td>:${exemption}</td></tr> -->
	
</table>
</c:if>
<table class="printfont"
			style="margin-left: 60px; margin-top: 10px; font-family: 'Dot Matrix Normal', Arial, Helvetica, sans-serif; font-style: normal;"
			width="80%">
	<tr align="center">
	<th style="width:10px">S.No</th>
	<th style="width:10px"><spring:message code="ehrinventory.viewStockBalance.drug"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.viewStockBalance.formulation"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.viewStockBalance.frequency"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.issueDrug.noOfDays"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.issueDrug.comments"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.receiptDrug.dateExpiry"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.issueDrug.quantity"/></th>
	<th style="width:10px"><spring:message code="ehrinventory.receiptDrug.price" text="Price" /></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listDrugIssue}">
	<c:set var="total" value="${0}"/>  
	<c:forEach items="${listDrugIssue}" var="detail" varStatus="varStatus">
	<%-- <c:set var="price" value="${ detail.quantity* (detail.transactionDetail.unitPrice + 0.01*detail.transactionDetail.VAT*detail.transactionDetail.unitPrice) }" /> --%>
	<c:set var="price" value="${ detail.quantity * detail.transactionDetail.costToPatient}" />
	<c:set var="generalVar" value="GENERAL"/>
	<c:set var="expectantVar" value="EXPECTANT MOTHER"/>
	<c:set var="tbVar" value="TB PATIENT"/>
	<c:set var="cccVar" value="CCC PATIENT"/>
	<c:set var="total" value="${total + price}"/>
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td style="width:10px"><c:out value="${varStatus.count }"/></td>
		<td style="width:10px">${detail.transactionDetail.drug.name} </td>	
		<td style="width:10px">${detail.transactionDetail.formulation.name}-${detail.transactionDetail.formulation.dozage}</td>
		<td style="width:10px">${detail.transactionDetail.frequency.name} </td>	
		<td style="width:10px">${detail.transactionDetail.noOfDays} </td>	
		<td style="width:10px">${detail.transactionDetail.comments} </td>
		<td style="width:10px"><openmrs:formatDate date="${detail.transactionDetail.dateExpiry}" type="textbox"/></td>
		<td style="width:10px">${detail.quantity }</td>
		<td style="width:10px"><fmt:formatNumber value="${price}" type="number" maxFractionDigits="2"/></td>
		</tr>
	</c:forEach>
	<tr><td>&nbsp;</td></tr>
	
	<tr>
	<td></td>
	<td></td>
	<td></td>
	<c:choose>
            <c:when test="${ not empty listOfNotDispensedOrder }">
			<td width="100%"><b>Drugs not issued</b></td>
		   </c:when>
		</c:choose></tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<c:forEach items="${listOfNotDispensedOrder}" var="nonDispensed" varStatus="varStatus">
		<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td style="width:10px"><c:out value="${varStatus.count }"/></td>
		<td style="width:10px">${nonDispensed.inventoryDrug.name} </td>	
		<td style="width:10px">${nonDispensed.inventoryDrugFormulation.name}-${nonDispensed.inventoryDrugFormulation.dozage} </td>
		<td style="width:10px">${nonDispensed.frequency.name}</td>
		<td style="width:10px">${nonDispensed.noOfDays}</td>
		<td style="width:10px">${nonDispensed.comments}</td>
		<td style="width:10px">N.A.</td>
		<td style="width:10px">N.A.</td>
		<td style="width:10px">N.A.</td>
		</tr>
	</c:forEach>
	
	<tr>
		<td>&nbsp;</td>
	</tr>
	
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td><b><spring:message code="ehrinventory.receiptDrug.total" text="Total Price" /></b></td>
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
	<br />
	</c:when>
	</c:choose>
	</table>
	<table  class="spacer" style="margin-left: 60px; margin-top: 60px;">
		<%-- <tr>
			<td>Treating Doctor </td>
			<td><b>:${paymentMode}</b></td>
		</tr> --%>
		<tr>
			<td>Attending Cashier</td>
			<td><b>:${cashier}</b></td>
		</tr>
	</table>
<br/><br/><br/><br/><br/><br/>
<span style="float:right;font-size: 1.5em">Signature of Inventory Clerk/ Stamp</span>
</div>
<!-- END PRINT DIV -->   