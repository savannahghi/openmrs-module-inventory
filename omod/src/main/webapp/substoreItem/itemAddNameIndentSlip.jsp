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
<form method="post" id="formAddNameForPurchase">
<table width="100%">
	<tr>
		<td>Name<em>*</em></td>
		<td><input type="text" name="indentName" id="indentName" size="35"/>
		<input type="hidden" name="send" id="send" value="${send}"/>
		</td>
	</tr>
	<tr>
		<td>
		<spring:message code="ehrinventory.substore.selectMainStore"/> <spring:message code="ehrinventory.substore.indentItem"/>
		</td>
		<td>
			<select name="mainstore">
			<c:forEach items="${store.parentStores}" var="vparent">
			  <option value="${vparent.id}">${vparent.name}</option>
			</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="2"><input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.save"/>"></td>
	</tr>
</table>
</form>