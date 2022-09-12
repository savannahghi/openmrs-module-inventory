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

<openmrs:require privilege="Add/Edit itemSubCategory" otherwise="/login.htm" redirect="/module/ehrinventory/itemSubCategory.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<h2><spring:message code="ehrinventory.itemSubCategory.manage"/></h2>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span>
</c:forEach>
<spring:bind path="itemSubCategory">
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
<form method="post" class="box" id="itemSubCategory">
<table>
	<tr>
		<spring:bind path="itemSubCategory.id">
			<input type="hidden" name="${status.expression}" id="${status.expression}" value="${status.value}" />
		</spring:bind>
		<tr>
		<td><spring:message code="ehrinventory.itemSubCategory.category"/><em>*</em></td>
		<td>
			<spring:bind path="itemSubCategory.category">
			<select name="${status.expression}" id="${status.expression}"  tabindex="20" >
				<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
                <c:forEach items="${categories}" var="vCat">
                    <option value="${vCat.id}" <c:if test="${vCat.id == itemSubCategory.category.id }">selected</c:if> >${vCat.name}</option>
                </c:forEach>
   			</select>
			<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>

	</tr>
	<tr>
		<td><spring:message code="ehrinventory.itemSubCategory.name"/><em>*</em></td>
		<td>
			<spring:bind path="itemSubCategory.name">
				<input type="text" id="${status.expression}" name="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td valign="top"><spring:message code="ehrinventory.itemSubCategory.code"/><em>*</em></td>
		<td>
			<spring:bind path="itemSubCategory.code">
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td valign="top"><spring:message code="ehrinventory.itemSubCategory.description"/></td>
		<td>
			<spring:bind path="itemSubCategory.description">
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	
</table>
<br />
<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.save"/>">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.cancel"/>" onclick="ACT.go('itemSubCategoryList.form');">
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>