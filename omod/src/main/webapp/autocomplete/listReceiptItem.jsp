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

<table class="box" width="100%">
	<tr>
		<th>Quantity available</th>
		<th>Issue quantity</th>
	</tr>
	<c:if  test="${sumReceiptItem > 0}">
	<tr >
		<td>${sumReceiptItem}</td>
		<td><em>*</em><input type="hidden" id="currentQuantity" name="currentQuantity" value="${sumReceiptItem}"  /><input type="text" id="issueItemQuantity" onchange="INVENTORY.checkValueExt(this, '${sumReceiptItem}');" name="issueItemQuantity" class="required digits" size="5"/></td>
	</tr>
	
	</c:if>
	<c:if  test="${sumReceiptItem <= 0}">
	<tr >
		<td colspan="2">This item is empty in your store please indent it
		<input type="hidden" id="issueItemQuantityA" name="issueItemQuantityA" class="required number" size="5"/>
		</td>
		
	</tr>	
	</c:if>
</table>