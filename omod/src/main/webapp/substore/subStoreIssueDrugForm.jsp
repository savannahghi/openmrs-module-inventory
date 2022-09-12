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
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<openmrs:require privilege="Add/Edit substore" otherwise="/login.htm"
	redirect="/module/ehrinventory/main.form" />

<spring:message var="pageTitle" code="inventory.issueDrug.manage"
	scope="page" />
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/js_css.jsp"%>
<openmrs:globalProperty var="userLocation" key="hospital.location_user" defaultValue="false"/>
<script type="text/javascript">
var cat="General";
function getValue()
  {
	
	var patientType = $("#patientType").val();
	ISSUE.processSlip('0',patientType);
  };


</script>
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
<input type="hidden" id="patientType" value="${patientType}">
<div style="width: 40%; float: left; margin-left: 4px;">
	<b class="boxHeader">Drug</b>
	<div class="box">

		<form method="post" id="formIssueDrug">
			<c:if test="${not empty errors}">
				<c:forEach items="${errors}" var="error">
					<span class="error"><spring:message code="${error}" /></span>
				</c:forEach>
			</c:if>
			<br />
			<table class="box">
				<tr>
					<td><b>Drug info</b></td>
				</tr>
				<!-- @support feature#174
					 @author Thai Chuong
	 				 @date <dd/mm/yyyy> 08/05/2012
	 				
	 				18/7/2012 : harsh issue :302
	 				 -->
				<tr>

<%-- 	// Sagar Bele - 07-08-2012 New Requirement #302 [INVENTORY] Non Mandatory Drug Category filter for drug search  --%>
					<td><spring:message code="ehrinventory.drug.category" /></td>
					<td><select name="category" id="category"
						onchange="ISSUE.onChangeCategory(this);" style="width: 250px;">
							<option value="">
								<spring:message code="ehrinventory.pleaseSelect" />
							</option>
							<c:forEach items="${listCategory}" var="vCat">
								<option value="${vCat.id}"
									<c:if test="${vCat.id == categoryId }">selected</c:if>>${vCat.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				  
				<tr>
					<td>Drug<em>*</em></td>
					<td>
					
				 <input id="drugName" name="drugName" onblur="ISSUE.onBlur(this);" style="width: 200px;">
						<div id="divDrug"  ></div>
					
					</td>
				</tr>
				
					<tr>
					<td><spring:message code="ehrinventory.drug.formulation" /><em>*</em></td>
					<td>
						<div id="divFormulation">
							<select id="formulation" name="formulation" >
								<option value="">
									<spring:message code="ehrinventory.pleaseSelect" />
								</option>
							</select>
						</div>
					</td>
				</tr>
			<tr>
					<td>Frequency<em>*</em></td>
					<td>
						<div id="divFrequency">
							<select id="frequency" name="frequency"  >
										<option value="">Please select</option>
										<c:forEach items="${drugFrequencyList}" var="dfl">
										
											<option value="${dfl.conceptId}">${dfl.name}</option>
											
										</c:forEach>
									</select>
									<!--<select hidden id="drugId" >
<option value="${drugId}"></option>
</select>
<select hidden id="formulation" >

           <option value="${formulation}" ></option>
     

</select>-->
						</div>
					</td>
				</tr>
				<tr>
					<td>No Of Days<em>*</em></td>
					<td>
						<div class="no-of-days">
									<input type="text" id="noOfDays" name="noOfDays"
										placeholder="No Of Days" size="7">
								</div>
					</td>
				</tr>
				<tr>              <td>Comments<em>*</em></td>
								<td>
								<div class="comments">
									<input id="comments"  type="text" name="comments" placeholder="Comments">
								</div>
								</td>
								</tr>
			</table>
			<br />
			<div id="divDrugAvailable">
			</div>
			<br /> <input type="submit"
				class="ui-button ui-widget ui-state-default ui-corner-all"
				value="<spring:message code="ehrinventory.issueDrug.addToSlip"/>">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${empty issueDrugPatient}">
				<input type="button"
					class="ui-button ui-widget ui-state-default ui-corner-all"
					value="<spring:message code="ehrinventory.issueDrug.createPatient"/>"
					onclick="ISSUE.createPatient();">
			</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button"
				class="ui-button ui-widget ui-state-default ui-corner-all"
				value="<spring:message code="ehrinventory.back"/>"
				onclick="ACT.go('patientQueueDrugOrder.form');">
		</form>
	</div>
</div>
<!-- Purchase list -->
<div style="width: 58%; float: right; margin-right: 16px;">
	<b class="boxHeader">Issue drug to patient slip</b>
	<div class="box">
		
		<c:if test="${not empty issueDrugPatient}">
			<table class="box" width="100%">
				<tr>
					<th>Identifier</th>
				  	<th>Category</th> 
					<th>Name</th>
					<th>Age</th>
					<th>Gender</th>
				</tr>
				<tr>
					<td>${issueDrugPatient.patient.patientIdentifier.identifier}</td>
				    <td>${paymentSubCategory}</td>
					<td>${issueDrugPatient.patient.givenName}&nbsp;${issueDrugPatient.patient.familyName}&nbsp;${fn:replace(issueDrugPatient.patient.middleName,","," ")} </td>
					<td><c:choose>
							<c:when test="${issueDrugPatient.patient.age == 0  }">&lt 1</c:when>
							<c:otherwise>${issueDrugPatient.patient.age }</c:otherwise>
						</c:choose></td>
					<td>${issueDrugPatient.patient.gender}</td>	
				</tr>

			</table>
		</c:if>
		
		

	</div>
	<div class="box">
		<table class="box" width="100%" cellpadding="5" cellspacing="0">
			<tr>
				<th>S.No</th>
				<th><spring:message code="ehrinventory.drug.category" /></th>
				<th><spring:message code="ehrinventory.drug.name" /></th>
				<th><spring:message code="ehrinventory.drug.formulation" /></th>
				<th><spring:message code="ehrinventory.viewStockBalance.frequency"/></th>
				<th><spring:message code="ehrinventory.issueDrug.noOfDays"/></th>
	            <th><spring:message code="ehrinventory.issueDrug.comments"/></th>
				<th><spring:message code="ehrinventory.receiptDrug.quantity" /></th>
				<th><spring:message code="ehrinventory.receiptDrug.price" text="Price" /></th>
			</tr>
			<c:choose>
				<c:when test="${not empty listPatientDetail}">
					<c:set var="total" value="${0}"/>   
					<c:forEach items="${listPatientDetail}" var="issue"
						varStatus="varStatus">
						<%-- <c:set var="price" value="${ issue.quantity* (issue.transactionDetail.unitPrice + 0.01*issue.transactionDetail.VAT*issue.transactionDetail.unitPrice) }" /> --%>
						<c:set var="price" value="${ issue.quantity * issue.transactionDetail.costToPatient}" />
						<c:set var="generalVar" value="GENERAL"/>
						<c:set var="expectantVar" value="EXPECTANT MOTHER"/>
						<c:set var="tbVar" value="TB PATIENT"/>
						<c:set var="cccVar" value="CCC PATIENT"/>
						<c:set var="total" value="${total + price}"/>	
						<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
							<td><c:out value="${varStatus.count }" /></td>
							<td>${issue.transactionDetail.drug.category.name}</td>
							<td><a href="#" title="Remove this"
								onclick="INVENTORY.removeObject('${varStatus.index}','5');">${issue.transactionDetail.drug.name}</a></td>
							<td>${issue.transactionDetail.formulation.name}-${issue.transactionDetail.formulation.dozage}</td>
							<td>${issue.transactionDetail.frequency.name} </td>
							<td>${issue.transactionDetail.noOfDays} </td>	
		                    <td>${issue.transactionDetail.comments} </td>
							<td>${issue.quantity}</td>
							<td><fmt:formatNumber value="${price}" type="number" maxFractionDigits="2"/></td>
						</tr>
					</c:forEach>
						<tr><td>&nbsp;</td></tr>
						<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td><b>Total Price</b></td>
                          <td align="left">	
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
						<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
												
						</tr>
				</c:when>
			</c:choose>
		</table>
		<br />

		<table class="box" width="100%" cellpadding="5" cellspacing="0">
			<tr>
				<td><c:if
						test="${not empty listPatientDetail && not empty issueDrugPatient}">
						<input type="button"
							class="ui-button ui-widget ui-state-default ui-corner-all"
							id="bttprocess" value="<spring:message code="inventory.finish"/>"
							onclick="getValue();" />
					</c:if> <c:if
						test="${not empty listPatientDetail || not empty issueDrugPatient}">
						<input type="button"
							class="ui-button ui-widget ui-state-default ui-corner-all"
							id="bttclear" value="<spring:message code="inventory.clear"/>"
							onclick="ISSUE.processSlip('1');" />
					</c:if></td>
			</tr>
		</table>

	</div>
</div>
<!-- PRINT DIV -->
<div id="printDiv" style="display: none;">
	<div style="width: 1280px; font-size: 0.8em">
		
		<br/>
<br/>     

<center><img width="100" height="100" align="center" title="OpenMRS" alt="OpenMRS" src="${pageContext.request.contextPath}/moduleResources/ehrinventory/kenya_logo.bmp"><center>
  <table  class="spacer" style="margin-left: 60px;"> 		
<tr><h3><center><b><u>${userLocation}</u> </b></center></h3></tr>
<tr><h5><b><center>CASH RECEIPT</center></b></h5></tr>
</table>
<br/>
<br/>

		<c:if test="${not empty issueDrugPatient}">

			<table class="spacer" style="margin-left: 60px;">
				<tr>
					<td>Date/Time</td>
					<td>:${date}</td>
				</tr>
				<tr>
					<td>Name</td>
					<td>:${issueDrugPatient.patient.givenName}&nbsp;${issueDrugPatient.patient.familyName}&nbsp;${fn:replace(issueDrugPatient.patient.middleName,","," ")}</td>
				</tr>
				<tr>
					<td>Identifier</td>
					<td>:${issueDrugPatient.identifier }</td>
				</tr>
				<tr>
					<td>Payment category</td>
					<td>:${paymentSubCategory }</td>
				</tr>  
<!-- 				<tr>
					<td>Waiver/Exempt. No.</td>
					<td>:${exemption }</td>
				</tr>  
 -->
			</table>
			<br />
		</c:if>
		<table class="printfont"
			style="margin-left: 60px; margin-top: 10px; font-family: 'Dot Matrix Normal', Arial, Helvetica, sans-serif; font-style: normal;"
			width="80%">
			<tr>
				<th>S.No</th>
				<th><spring:message code="ehrinventory.drug.name" /></th>
				<th><spring:message code="ehrinventory.drug.formulation" /></th>
				<th><spring:message code="ehrinventory.receiptDrug.quantity" /></th>
				<th><spring:message code="ehrinventory.viewStockBalance.frequency"/></th>
				<th><spring:message code="ehrinventory.issueDrug.noOfDays"/></th>
	            <th><spring:message code="ehrinventory.issueDrug.comments"/></th>
				<th><spring:message code="ehrinventory.receiptDrug.price" text="Price" /></th>
			</tr>
			<c:choose>
				<c:when test="${not empty listPatientDetail}">
				<c:set var="total" value="${0}"/>
					<c:forEach items="${listPatientDetail}" var="issue"
						varStatus="varStatus">
						<%-- <c:set var="price" value="${ issue.quantity* (issue.transactionDetail.unitPrice + 0.01*issue.transactionDetail.VAT*issue.transactionDetail.unitPrice) }" /> --%>
						<c:set var="price" value="${ issue.quantity * issue.transactionDetail.costToPatient}" />
						<c:set var="generalVar" value="GENERAL"/>
						<c:set var="expectantVar" value="EXPECTANT MOTHER"/>
						<c:set var="tbVar" value="TB PATIENT"/>
						<c:set var="cccVar" value="CCC PATIENT"/>
						<c:set var="total" value="${total + price}"/>
						<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
							<td><center><c:out value="${varStatus.count }" /></center></td>
							<td><center>${issue.transactionDetail.drug.name}</center></td>
							<td><center>${issue.transactionDetail.formulation.name}-${issue.transactionDetail.formulation.dozage}</center></td>
							<td><center>${issue.transactionDetail.frequency.name}</center></td>
							<td>${issue.transactionDetail.noOfDays} </td>	
	                        <td>${issue.transactionDetail.comments} </td>
							<td><center>${issue.quantity}</center></td>
							<td><center><fmt:formatNumber value="${price}" type="number" maxFractionDigits="2"/></center></td>
						</tr>
					</c:forEach>
						<tr><td>&nbsp;</td></tr>
						<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td><spring:message code="ehrinventory.receiptDrug.total" text="Total" /></td>
								
								<c:choose>
								<c:when test ="${paymentSubCategory == generalVar}">
									<td><fmt:formatNumber value="${total}" type="number" maxFractionDigits="2"/></td>
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
									<td><fmt:formatNumber value="0.00" type="number" maxFractionDigits="2"/></td>
								</c:otherwise>
							</c:choose>
							</td>						
						</tr>
				</c:when>
			</c:choose>
		</table   class="spacer" style="margin-left: 60px; margin-top: 60px;">
			<table  class="spacer" style="margin-left: 60px; margin-top: 60px;">
	<!-- 	<tr>
			<td>PAYMENT MODE </td>
			<td><b>:</b></td>
		</tr> -->
	</table>
		<br /> <br /> <br /> <br /> <br /> <br /> <span
			style="float: right; font-size: 1.5em">Signature of Inventory
			Clerk/ Stamp</span>
	</div>
</div>
<!-- END PRINT DIV -->


<%@ include file="/WEB-INF/template/footer.jsp"%>