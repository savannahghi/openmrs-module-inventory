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

<openmrs:require privilege="Add/Edit drug" otherwise="/login.htm" redirect="/module/ehrinventory/drug.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>


<h2><spring:message code="ehrinventory.drug.manage"/></h2>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span>
</c:forEach>
<spring:bind path="drug">
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

<form method="post" class="box" id="drugForm">
<table>
	<tr>
		<spring:bind path="drug.id">
			<input type="hidden" name="${status.expression}" id="${status.expression}" value="${status.value}" />
		</spring:bind>
		<td><spring:message code="ehrinventory.drug.name"/><em>*</em></td>
		<td>
			<spring:bind path="drug.name">
				<input type="text" id="${status.expression}" name="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		
		<td><spring:message code="ehrinventory.drug.drug"/><em>*</em></td>
		<td>
			<spring:bind path="drug.drugCore">
				<input   type="text" id="${status.expression}" name="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td><spring:message code="ehrinventory.drug.formulation"/><em>*</em></td>
		<td><spring:bind path="drug.formulations">
		<select    name="${status.expression}" id="${status.expression}"  multiple="multiple"  size="10" style="width:150px"> 
				<c:forEach items="${formulations}" var="sFormulation">
									
				 
					<option value="${sFormulation.id}"
						<c:forEach items="${status.value}" var="formulation">
						
						    	<c:if test="${sFormulation.id == formulation.id}"> selected</c:if>
						</c:forEach> 
					>${sFormulation.name}-${sFormulation.dozage}
					</option>
				</c:forEach>
			</select>
			${not empty add ? 'Please do not remove the selected option':'Please do not remove the selected option' }
			<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>

	</tr>

	<tr>
		<td><spring:message code="ehrinventory.drug.unit"/><em>*</em></td>
		<td>
			<spring:bind path="drug.unit">
			<select name="${status.expression}" id="${status.expression}"  tabindex="20" >
				<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
                <c:forEach items="${units}" var="vUnit">
                    <option value="${vUnit.id}" <c:if test="${vUnit.id == drug.unit.id }">selected</c:if> >${vUnit.name}</option>
                </c:forEach>
   			</select>
			<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>

	</tr>
	<tr>
		<td><spring:message code="ehrinventory.drug.category"/><em>*</em></td>
		<td>
			<spring:bind path="drug.category">
			<select name="${status.expression}" id="${status.expression}"  tabindex="20" >
				<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
                <c:forEach items="${categories}" var="vCat">
                    <option value="${vCat.id}" <c:if test="${vCat.id == drug.category.id }">selected</c:if> >${vCat.name}</option>
                </c:forEach>
   			</select>
			<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>

	</tr>
    <tr>
        <td><spring:message code="ehrinventory.drug.route"/><em>*</em></td>
        <td class = "select">
            <select>
            <option value="">Please Select</option>
            <option value="1">Intravenous</option>
            <option value="3">Oral</option>
            </select>
        </td>

    </tr>

    <tr>
        <td><spring:message code="ehrinventory.drug.line"/><em>*</em></td>
        <td class = "select">
            <select>
            <option value="">Please Select</option>
            <option value="1">First Line</option>
            <option value="3">Second Line</option>
            <option value="4"> Third Line</option>
            </select>
        </td>

    </tr>


	<tr>
		<td><spring:message code="ehrinventory.drug.attribute"/><em>*</em></td>
		<td>
			<spring:bind path="drug.attribute">
			<select name="${status.expression}" id="${status.expression}"  tabindex="20" onchange="DRUG.onChangeAttribute(this);">
				<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
                <c:forEach items="${attributes}" var="vAttr">
                    <option value="${vAttr.id}" <c:if test="${vAttr.id == drug.attribute }">selected</c:if> >${vAttr.name}</option>
                </c:forEach>
   			</select>
			<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>

	</tr>
	
	<tr class="depentOnAttribute" <c:if test="${drug.attribute != 1 }"> style='display: none;'</c:if> >
	
		<td><spring:message code="ehrinventory.drug.reorderQty"/><em>*</em></td>
		<td>
			<spring:bind path="drug.reorderQty">
			<input type="text" id="${status.expression}" name="${status.expression}" value="${status.value}" size="15" />
			<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
			
		</td>
	</tr>
</table>
<br />

<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.save"/>" >
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.cancel"/>" onclick="ACT.go('drugList.form');">
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>