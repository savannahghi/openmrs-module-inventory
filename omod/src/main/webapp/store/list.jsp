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

<openmrs:require privilege="View Store" otherwise="/login.htm" redirect="/module/ehrinventory/storeList.form" />

<spring:message var="pageTitle" code="ehrinventory.store.manage" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="../includes/nav.jsp" %>

<h2><spring:message code="ehrinventory.store.manage"/></h2>

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='ehrinventory.store.add'/>" onclick="ACT.go('store.form');"/>

<br /><br />
<form method="post" onsubmit="return false" id="form">
<span class="boxHeader"><spring:message code="ehrinventory.store.list"/></span>
<div class="box">
<c:choose>
<c:when test="${not empty stores}">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="INVENTORY.checkValue();" value="<spring:message code='ehrinventory.deleteSelected'/>"/>
<table cellpadding="5" cellspacing="0" width="100%">
<tr>
	<th>S.No</th>
	<th><spring:message code="general.name"/></th>
	<th><spring:message code="ehrinventory.store.code"/></th>
	<th><spring:message code="ehrinventory.store.parent"/></th>
	<th><spring:message code="ehrinventory.store.role"/></th>
	<th><spring:message code="ehrinventory.store.isDrug"/></th>
	<th><spring:message code="ehrinventory.store.retired"/></th>
	<th><spring:message code="ehrinventory.store.createdDate"/></th>
	<th><spring:message code="ehrinventory.store.createdBy"/></th>
	<th></th>
</tr>
<c:forEach items="${stores}" var="store" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>	
		<td><a href="#" onclick="STORE.edit('${ store.id}');">${store.name}</a> </td>
		<td>${store.code}</td>
	<!-- 	<td></td>     store.parent. It should be updated later-->
		<td>
			<c:forEach items="${store.parentStores}" var="vparent">
			  ${vparent.name} <br>
			</c:forEach>
		</td>
		<td><c:forEach items="${roless}" var="rol">
		<c:if test="${store.id==rol.storeid}">${rol.roleName}<br></c:if></c:forEach></td>
		<td>${store.isDrugName}</td>
		<td>${store.retired}</td>
		<td><openmrs:formatDate date="${store.createdOn}" type="textbox"/></td>
		<td>${store.createdBy}</td>
		<td><input type="checkbox" name="ids" value="${store.id}"/></td>
	</tr>
</c:forEach>

<tr class="paging-container">
	<td colspan="9"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</c:when>
<c:otherwise>
	No Store found.
</c:otherwise>
</c:choose>
</div>
</form>



<%@ include file="/WEB-INF/template/footer.jsp" %>
