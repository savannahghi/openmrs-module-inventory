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
<c:if test="${not empty  specifications}">
<td><spring:message code="ehrinventory.item.specification"/><em>*</em></td>
<td>		
<select name="specification" id="specification"   style="width: 200px;">
	<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
       <c:forEach items="${specifications}" var="specification">
           <option value="${specification.id}" <c:if test="${specification.id == specificationId }">selected</c:if> >${specification.name}</option>
       </c:forEach>
</select>
</td>
</c:if>
