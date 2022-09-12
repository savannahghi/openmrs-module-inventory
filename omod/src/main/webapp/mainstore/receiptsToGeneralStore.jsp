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
<%@ include file="../includes/js_css.jsp" %>

<script type="text/javascript">
jQuery(document).ready(function() {
	
	jQuery("#receiptDate").change(function() {
		VALIDATION.checkRecieptDate();
	});
	});

VALIDATION={
	checkRecieptDate : function() {
		var recieptDate = new Date(STRING.convertDateFormat(jQuery('#receiptDate').val()));
		var expiryDate = new Date(STRING.convertDateFormat(jQuery('#dateExpiry').val()));
		var dateManufacture = new Date(STRING.convertDateFormat(jQuery('#dateManufacture').val()));
		var currentDate = new Date();
		if (recieptDate > expiryDate){
			jQuery('#receiptDate').val("");
			alert("You can not receipt an expired drug");
		}
		if (recieptDate > currentDate){
			jQuery('#receiptDate').val("");
			alert("You can not receipt on future date");
		}
		if (recieptDate < dateManufacture){
			jQuery('#receiptDate').val("");
			alert("Receiept Date can not be before than manufacture Date");
		}
		if (recieptDate.getTime() == expiryDate.getTime()){
			jQuery('#receiptDate').val("");
			alert("Receiept Date of drug should be less than Expiry Date");
		}
	}
}


</script>
<script type="text/javascript">
function checkCompany()
{

if($("#companyName").val().length > 100)
{
	$('#companyName').val("");
	alert("The company's name exceeds the maximum limit of 100 characters");
}
}
</script>
<div style="width: 20%; float: left; margin-left: 4px; ">
<b class="boxHeader">Drug</b>
<div class="box">
<form method="post" id="receiptDrug">
<c:if  test="${not empty errors}">
<c:forEach items="${errors}" var="error">
	<span class="error"><spring:message code="${error}" /></span>
</c:forEach>
</c:if>
<br/>
<table width="100%">
<tr><td><b>Drug Info</b></td></tr>
 <tr>
 

<%-- 	// Sagar Bele - 07-08-2012 New Requirement #302 [INVENTORY] Non Mandatory Drug Category filter for drug search  --%>
		<td><spring:message code="ehrinventory.drug.category"/></td>
		<td>
			<select name="category" id="category" onchange="RECEIPT.onChangeCategory(this);"  style="width: 80%;">
				<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
                <c:forEach items="${listCategory}" var="vCat">
                    <option value="${vCat.id}" title="${vCat.name}" <c:if test="${vCat.id == categoryId }">selected</c:if> >${vCat.name}</option>
                </c:forEach>
   			</select>
		</td>
	
	</tr>

	<tr>
		<td>Drug Name<em>*</em></td>
		<td>
			
				<input id="drugName" name="drugName" onblur="RECEIPT.onBlur(this);" style="width: 80%;">
				<div id="divDrug"  ></div>
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.drug.formulation"/><em>*</em></td>
		<td>
			<div id="divFormulation"  >
				<select id="formulation"  name="formulation" style="width: 80%;">
					<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
				</select>
			</div>
		</td>
	</tr>
</table>
<br/>
<table class="box">
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.quantiry"/><em>*</em></td>
		<td>
			<input type="text" id="quantity" name="quantity" />
		</td>
	</tr>
	<tr>
		<td>Unit Price</td>
		<td>
			<input type="text" id="unitPrice" name="unitPrice" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.VAT"/></td>
		<td>
			<input type="text" id="VAT" name="VAT" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.costToPatient"/><em>*</em></td>
		<td>
			<input type="text" id="costToPatient" name="costToPatient" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.batchNo"/><em>*</em></td>
		<td>
			<input type="text" id="batchNo" name="batchNo" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.companyName"/><em>*</em></td>
		<td>
			<input type="text" id="companyName" name="companyName" onblur="checkCompany();" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.dateManufacture"/><em>*</em></td>
		<td>
			<input type="text" id="dateManufacture" name="dateManufacture" class="date-pick left" readonly="readonly"  ondblclick="this.value='';"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.dateExpiry"/><em>*</em></td>
		<td>
			<input type="text" id="dateExpiry" name="dateExpiry" class="date-pick left" readonly="readonly"  ondblclick="this.value='';"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.receiptDate"/><em>*</em></td>
		<td>
			<input type="text" id="receiptDate" name="receiptDate" class="date-pick left" readonly="readonly"  ondblclick="this.value='';"/>
		</td>
	</tr>
	<!-- Sagar Bele : Date - 22-01-2013 Issue Number 660 : [Inventory] Add receipt from field in Table and front end -->
	<tr>
		<td><spring:message code="ehrinventory.receiptDrug.receiptFrom"/></td>
		<td>
			<input type="text" id="receiptFrom" name="receiptFrom" />
		</td>
	</tr>

</table>
<br/>
<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptDrug.addToSlip"/>">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.back"/>" onclick="ACT.go('receiptsToGeneralStoreList.form');">
</form>
</div>
</div>
<!-- Receipt list -->
<div style="width: 79%; float: right; margin-right: 4px; ">
<b class="boxHeader">Receipt Slip</b>  <!-- Sept 22,2012 -- Sagar Bele -- Issue 387 --Change case of word Slip-->
<div class="box">
<table class="box" width="100%" border="1" cellpadding="5" cellspacing="0">
	<tr>
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
	<th title="<spring:message code="ehrinventory.receiptDrug.companyName"/>">CN</th>
	<th title="<spring:message code="ehrinventory.receiptDrug.dateManufacture"/>">DM</th>
	<th title="<spring:message code="ehrinventory.receiptDrug.dateExpiry"/>">DE</th>
	<th title="<spring:message code="ehrinventory.receiptDrug.receiptDate"/>">RD</th>
	<th title="<spring:message code="ehrinventory.receiptDrug.receiptFrom"/>">RF</th>
	</tr>
	<c:choose>
	<c:when test="${not empty listReceipt}">
	<c:forEach items="${listReceipt}" var="receipt" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td>${receipt.drug.category.name} </td>	
		<td><a href="#" title="Remove this" onclick="INVENTORY.removeObject('${varStatus.index}','7');">${receipt.drug.name}</a></td>
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
<br/>
	<c:if  test="${not empty listReceipt}">
		<table class="box" width="100%" cellpadding="5" cellspacing="0">
		<tr>
			<td>
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptDrug.finish"/>" onclick="RECEIPT.receiptSlip('0');" />
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptDrug.clear"/>"  onclick="RECEIPT.receiptSlip('1');"/>
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptDrug.print"/>" onClick="RECEIPT.printDiv();" />
			</td>
		</tr>
		</table>
	</c:if>
</div>
</div>
<!-- PRINT DIV -->
<div id="printDiv" style="display: none; ">
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
	<tr>
	<th>S.No</th>
	<th><spring:message code="ehrinventory.drug.category"/></th>
	<th><spring:message code="ehrinventory.drug.name"/></th>
	<th><spring:message code="ehrinventory.drug.formulation"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.quantity"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.unitPrice"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.VAT"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.costToPatient"/></th>
	<!-- <th><spring:message code="ehrinventory.receiptDrug.totalPrice"/></th> -->
	<th><spring:message code="ehrinventory.receiptDrug.batchNo"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.companyName"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.dateManufacture"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.dateExpiry"/></th>
	<th><spring:message code="ehrinventory.receiptDrug.receiptDate"/></th>
	<!-- Sagar Bele : Date - 22-01-2013 Issue Number 660 : [Inventory] Add receipt from field in Table and front end -->	
	<th><spring:message code="ehrinventory.receiptDrug.receiptFrom"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listReceipt}">
	<c:forEach items="${listReceipt}" var="receipt" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
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
		<td>${receipt.receiptFrom}</td>
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

 
<%@ include file="/WEB-INF/template/footer.jsp" %>