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

<openmrs:require privilege="View itemSubCategory" otherwise="/login.htm" redirect="/module/ehrinventory/itemSubCategoryList.form" />

<spring:message var="pageTitle" code="ehrinventory.itemSubCategory.manage" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="nav.jsp" %>
<h2><spring:message code="ehrinventory.itemSubCategory.manage"/></h2>

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='ehrinventory.itemSubCategory.add'/>" onclick="ACT.go('itemSubCategory.form');"/>

<br /><br />
<form method="post" onsubmit="return false" id="form">
<table cellpadding="5" cellspacing="0">
	<tr>
		<td><spring:message code="general.name"/></td>
		<td><input type="text" id="searchName" name="searchName" value="${searchName}" /></td>
		<td><input type="button" value="Search" onclick="INVENTORY.search('itemSubCategoryList.form','searchName');"/></td>
	</tr>
</table>

<span class="boxHeader"><spring:message code="ehrinventory.itemSubCategory.list"/></span>
<div class="box">
<c:choose>
<c:when test="${not empty itemSubCategorys}">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="INVENTORY.checkValue();" value="<spring:message code='ehrinventory.deleteSelected'/>"/>
<table cellpadding="5" cellspacing="0" width="100%">
<tr>
	<th>S.No</th>
	<th><spring:message code="ehrinventory.itemSubCategory.name"/></th>
	<th><spring:message code="ehrinventory.itemSubCategory.code"/></th>
	<th><spring:message code="ehrinventory.itemSubCategory.description"/></th>
	<th><spring:message code="ehrinventory.itemSubCategory.category"/></th>
	<th><spring:message code="ehrinventory.itemSubCategory.createdDate"/></th>
	<th><spring:message code="ehrinventory.itemSubCategory.createdBy"/></th>
	<th></th>
</tr>
<c:forEach items="${itemSubCategorys}" var="itemSubCategory" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>	
		<td><a href="#" onclick="ACT.go('itemSubCategory.form?itemSubCategoryId=${ itemSubCategory.id}');">${itemSubCategory.name}</a> </td>
		<td>${itemSubCategory.code}</td>
		<td>${itemSubCategory.description}</td>
		<td>${itemSubCategory.category.name}</td>
		<td><openmrs:formatDate date="${itemSubCategory.createdOn}" type="textbox"/></td>
		<td>${itemSubCategory.createdBy}</td>
		<td><input type="checkbox" name="ids" value="${itemSubCategory.id}"/></td>
	</tr>
</c:forEach>

<tr class="paging-container">
	<td colspan="8"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</c:when>
<c:otherwise>
	No itemSubCategory found.
</c:otherwise>
</c:choose>
</div>
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>