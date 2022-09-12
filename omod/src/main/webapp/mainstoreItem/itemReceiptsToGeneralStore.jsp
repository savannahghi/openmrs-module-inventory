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
<spring:message var="pageTitle" code="ehrinventory.receiptItem.manage" scope="page"/>
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
		var dateManufacture = new Date(STRING.convertDateFormat(jQuery('#dateManufacture').val()));
		var currentDate = new Date();
		
		if (recieptDate > currentDate){
			jQuery('#receiptDate').val("");
			alert("You can not receipt on future date");
		}
		if (recieptDate < dateManufacture){
			jQuery('#receiptDate').val("");
			alert("Receipt Date can not be before than manufacture Date");
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
<div style="width: 25%; float: left; margin-left: 4px; ">
<b class="boxHeader">Item</b>
<div class="box">

<form method="post" id="receiptItem">
<br/>

<table class="box">
<tr><td><b>Item Info</b></td></tr>
<tr>
		<td><spring:message code="ehrinventory.item.subCategory"/><em>*</em></td>
		<td>
			<select name="category" id="category" onchange="RECEIPT.onChangeCategoryItem(this);"   style="width: 200px;">
				<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
                <c:forEach items="${listCategory}" var="vCat">
                    <option value="${vCat.id}" <c:if test="${vCat.id == categoryId }">selected</c:if> >${vCat.name}</option>
                </c:forEach>
   			</select>
		</td>
		

	</tr>
	<tr>
		<td>Item Name<em>*</em></td>
		<td>
			<div id="divItem">
				<select id="itemId" name="itemId" onchange="RECEIPT.onBlurItem(this);"  style="width: 200px;">
					<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
					   <c:if test ="${not empty items }">
					       <c:forEach items="${items}" var="item">
					           <option value="${item.id}" title="${item.name}">${item.name}</option>
					       </c:forEach>
				       </c:if>
				</select>
			</div>
		</td>
	</tr>
	<tr id="divSpecification" >
		<td></td>
		<td></td>
	</tr>
</table>
<br/>
<table class="box">
	<tr>
		<td><spring:message code="ehrinventory.receiptItem.quantity"/><em>*</em></td>
		<td>
			<input type="text" id="quantity" name="quantity" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptItem.unitPrice"/></td>
		<td>
			<input type="text" id="unitPrice" name="unitPrice" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptItem.VAT"/></td>
		<td>
			<input type="text" id="VAT" name="VAT" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptItem.costToPatient"/><em>*</em></td>
		<td>
			<input type="text" id="costToPatient" name="costToPatient" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptItem.companyName"/></td>
		<td>
			<input type="text" id="companyName" name="companyName" onblur="checkCompany();" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptItem.dateManufacture"/></td>
		<td>
			<input type="text" id="dateManufacture" name="dateManufacture" class="date-pick left" readonly="readonly"  ondblclick="this.value='';"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.receiptItem.receiptDate"/><em>*</em></td>
		<td>
			<input type="text" id="receiptDate" name="receiptDate" class="date-pick left" readonly="readonly"  ondblclick="this.value='';"/>
		</td>
	</tr>
</table>
<br/>
<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptItem.addToSlip"/>">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.back"/>" onclick="ACT.go('itemReceiptsToGeneralStoreList.form');">
</form>
</div>
</div>
<!-- Receipt list -->  <!-- Sept 22,2012 -- Sagar Bele -- Issue 387 --Change case of word Slip-->
<div style="width: 73%; float: right; margin-right: 16px; ">
<b class="boxHeader">Receipt Slip</b>
<div class="box">
<table class="box" width="100%" cellpadding="5" cellspacing="0">
	<tr>
	<th>S.No</th>
	<th>Item Category</th>
	<th><spring:message code="ehrinventory.item.name"/></th>
	<th><spring:message code="ehrinventory.item.specification"/></th>
	<th><spring:message code="ehrinventory.receiptItem.quantity"/></th>
	<th><spring:message code="ehrinventory.receiptItem.unitPrice"/></th>
	<th><spring:message code="ehrinventory.receiptItem.VAT"/></th>
	<th><spring:message code="ehrinventory.receiptItem.costToPatient"/></th>
	<!-- <th><spring:message code="ehrinventory.receiptItem.totalPrice"/></th> -->
	<th title="<spring:message code="ehrinventory.receiptItem.companyName"/>">CN</th>
	<th title="<spring:message code="ehrinventory.receiptItem.dateManufacture"/>">DM</th>
	<th title="<spring:message code="ehrinventory.receiptItem.receiptDate"/>">RD</th>
	</tr>
	<c:choose>
	<c:when test="${not empty listReceipt}">
	<c:forEach items="${listReceipt}" var="receipt" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${receipt.item.category.name} </td>	
		<td><a href="#" title="Remove this" onclick="INVENTORY.removeObject('${varStatus.index}','6');">${receipt.item.name}</a></td>
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
<br/>
	<c:if  test="${not empty listReceipt}">
		<table class="box" width="100%" cellpadding="5" cellspacing="0">
		<tr>
			<td>
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptItem.finish"/>" onclick="RECEIPT.receiptSlipItem('0');" />
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptItem.clear"/>"  onclick="RECEIPT.receiptSlipItem('1');"/>
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.receiptItem.print"/>" onClick="RECEIPT.printDiv();" />
			</td>
		</tr>
		</table>
	</c:if>
</div>
</div>
<!-- PRINT DIV -->
<div  id="printDiv" style="display: none;">
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
	<tr>
	<th>S.No</th>
	<th><spring:message code="ehrinventory.item.category"/></th>
	<th><spring:message code="ehrinventory.item.name"/></th>
	<th><spring:message code="ehrinventory.item.specification"/></th>
	<th><spring:message code="ehrinventory.receiptItem.quantity"/></th>
	<th><spring:message code="ehrinventory.receiptItem.unitPrice"/></th>
	<th><spring:message code="ehrinventory.receiptItem.VAT"/></th>
	<th><spring:message code="ehrinventory.receiptItem.costToPatient"/></th>
	<!-- <th><spring:message code="ehrinventory.receiptItem.totalPrice"/></th> -->
	<th><spring:message code="ehrinventory.receiptItem.companyName"/></th>
	<th><spring:message code="ehrinventory.receiptItem.dateManufacture"/></th>
	<th><spring:message code="ehrinventory.receiptItem.receiptDate"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listReceipt}">
	<c:forEach items="${listReceipt}" var="receipt" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${ varStatus.count }"/></td>
		<td>${receipt.item.category.name} </td>	
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
<span style="float:right;font-size: 1.5em">Signature of inventory clerk/ Stamp</span>
</div>
</div>
<!-- END PRINT DIV -->   

 
<%@ include file="/WEB-INF/template/footer.jsp" %>