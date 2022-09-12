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

<openmrs:require privilege="Add/Edit substore" otherwise="/login.htm" redirect="/module/ehrinventory/main.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>

<h2><spring:message code="ehrinventory.indentItem.process"/></h2>
<form method="post" class="box" id="formSubStoreItemProcessIndent">
<input type="hidden" name="indentId" id="indentId"  value="${indent.id}">
<c:forEach items="${errors}" var="error">
	<span class="error"><spring:message code="${error}" /></span><br/>
</c:forEach>
<table>
<tr>
	<td><spring:message code="ehrinventory.indentItem.name"/></td>
	<td><input type="text" disabled="disabled"  value="${indent.name}" size="50"></td>

</tr>
<tr>
	<td><spring:message code="ehrinventory.indentItem.createdOn"/></td>
	<td><input type="text" disabled="disabled"  value="<openmrs:formatDate date="${indent.createdOn}" type="textbox"/>"> </td>

</tr>
</table>
<table class="box" width="100%" id="tableIndent">
	<tr align="center">
		<th >#</th>
		<th ><spring:message code="ehrinventory.indentItem.item"/></th>
		<th  ><spring:message code="ehrinventory.indentItem.specification"/></th>
		<th  ><spring:message code="ehrinventory.indentItem.quantityIndent"/></th>
		<th  ><spring:message code="ehrinventory.indentItem.transferQuantity"/></th>
	</tr>
	
	<c:forEach items="${listItemNeedProcess}" var="itemIndent" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow " : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td >${itemIndent.item.name} </td>
		<td >${itemIndent.specification.name}</td>

		<td >
		${itemIndent.quantity}
		</td>
		<td >
			${itemIndent.mainStoreTransfer} 
		</td>
	</tr>
	</c:forEach>
</table>
		
		
<br />		
<br />
<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.indentItem.receipt"/>">
<input type="hidden" id="refuse" name="refuse" value="">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.indentItem.refuse"/>" onclick="INDENT.refuseIndentFromSubStoreItem(this);">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.returnList"/>" onclick="ACT.go('subStoreIndentItemList.form');">
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>
