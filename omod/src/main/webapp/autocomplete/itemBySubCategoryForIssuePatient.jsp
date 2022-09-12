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
<select id="itemId" name="itemId" onchange="ISSUE.onBlurItemPatient(this);"  style="width: 200px;">
	<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
	   <c:if test ="${not empty items }">
	       <c:forEach items="${items}" var="item">
	           <option value="${item.id}" title="${item.name}">${item.name}</option>
	       </c:forEach>
       </c:if>
</select>