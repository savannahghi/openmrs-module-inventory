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



<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<h2><spring:message code="ehrinventory.store.manage"/></h2>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span>
</c:forEach>
<spring:bind path="store">
<c:if test="${not empty  status.errorMessages}">
<div class="error">
<ul>
<c:forEach items="${status.errorMessages}" var="error">
    <li>${error}</li>   
</c:forEach>
</ul>
</div>
</c:if>
</spring:bind>

<form method="post" class="box" id="inventoryStore">
<spring:bind path="store.id">
	<input type="hidden" name="${status.expression}" id="${status.expression}" value="${status.value}" />
</spring:bind>
<table>
	<tr>
		<td><spring:message code="general.name"/><em>*</em></td>
		<td>
			<spring:bind path="store.name">
				
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td valign="top"><spring:message code="ehrinventory.store.code"/><em>*</em></td>
		<td>
			<spring:bind path="store.code">
				
				
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td valign="top"><spring:message code="ehrinventory.store.parent"/></td>
		<td>
			<spring:bind path="store.parentStores">
			<select name="parent"  tabindex="20" multiple>
				<option value=""></option>
                <c:forEach items="${parents}" var="vparent">
                   <option value="${vparent.id}" 
                   <c:forEach items="${store.parentStores}" var="vselectedParent">
                        <c:if test="${vselectedParent.id == vparent.id}">selected</c:if>
                   </c:forEach>>${vparent.name}</option>
				  
                </c:forEach>
   			</select>
			<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td valign="top"><spring:message code="ehrinventory.store.role"/><em>*</em></td>
		<td>
			
			<select id="rol" name="roles" tabindex="20" multiple="multiple"  required="required">
				
                <c:forEach items="${roles}" var="rl">
                    <option value="${rl.role}"  <c:forEach items="${selectedModule}" var="vselected"><c:if test="${rl.role ==vselected.roleName }"> selected</c:if></c:forEach>>${rl.role}</option>
                </c:forEach>
   			</select>
			
		</td>
	</tr>
	
	<tr>
		<td><spring:message code="general.retired" /></td>
		<td>
			<spring:bind path="store.retired">
				<openmrs:fieldGen type="java.lang.Boolean" formFieldName="${status.expression}" val="${status.value}" parameters="isNullable=false" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
</table>
<br />
<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.save"/>">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.cancel"/>" onclick="ACT.go('storeList.form');">
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>