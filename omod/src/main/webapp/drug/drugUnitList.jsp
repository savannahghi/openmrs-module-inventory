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

<openmrs:require privilege="View drugUnit" otherwise="/login.htm" redirect="/module/ehrinventory/drugUnitList.form" />

<spring:message var="pageTitle" code="ehrinventory.drugUnit.manage" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="nav.jsp" %>
<h2><spring:message code="ehrinventory.drugUnit.manage"/></h2>

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='ehrinventory.drugUnit.add'/>" onclick="ACT.go('drugUnit.form');"/>

<br /><br />

<form method="post" onsubmit="return false" id="form">
<table cellpadding="5" cellspacing="0">
	<tr>
		<td><spring:message code="general.name"/></td>
		<td><input type="text" id="searchName" name="searchName" value="${searchName}" /></td>
		<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search" onclick="INVENTORY.search('drugUnitList.form','searchName');"/></td>
	</tr>
</table>

<span class="boxHeader"><spring:message code="ehrinventory.drugUnit.list"/></span>
<div class="box">
<c:choose>


<c:when test="${not empty drugUnits}">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="INVENTORY.checkValue();" value="<spring:message code='ehrinventory.deleteSelected'/>"/>
<table cellpadding="5" cellspacing="0" width="100%">
<tr>
	<th>S.No</th>
	<th><spring:message code="ehrinventory.drugUnit.name"/></th>
	<th><spring:message code="ehrinventory.drugUnit.description"/></th>
	<th><spring:message code="ehrinventory.drugUnit.createdDate"/></th>
	<th><spring:message code="ehrinventory.drugUnit.createdBy"/></th>
	<th></th>
</tr>
<c:forEach items="${drugUnits}" var="drugUnit" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>	
		<td><a href="#" onclick="ACT.go('drugUnit.form?drugUnitId=${ drugUnit.id}');">${drugUnit.name}</a> </td>
		<td>${drugUnit.description}</td>
		<td><openmrs:formatDate date="${drugUnit.createdOn}" type="textbox"/></td>
		<td>${drugUnit.createdBy}</td>
		<td><input type="checkbox" name="ids" value="${drugUnit.id}"/></td>
	</tr>
</c:forEach>

<tr class="paging-container">
	<td colspan="6"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</c:when>
<c:otherwise>
	No drugUnit found.
</c:otherwise>
</c:choose>
</div>
</form>






<%@ include file="/WEB-INF/template/footer.jsp" %>