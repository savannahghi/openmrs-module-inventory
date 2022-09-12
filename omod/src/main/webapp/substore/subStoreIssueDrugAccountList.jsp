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
<spring:message var="pageTitle" code="ehrinventory.issueDrug.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="nav.jsp" %>
<h2><spring:message code="ehrinventory.issueDrug.manage"/></h2>
<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='ehrinventory.issueDrugAccount.add'/>" onclick="ACT.go('subStoreIssueDrugAccountForm.form');"/>
<br /><br />

<form method="get"  id="form">
<table >
	<tr>
		<td><spring:message code="ehrinventory.issueDrug.account"/></td>
		<td>
			<input type="text" name="issueName" id="issueName" value="${issueName }"/>
		</td><td>&nbsp;&nbsp;</td>
		<td><spring:message code="ehrinventory.fromDate"/></td>
		<td><input type="text" id="fromDate" class="date-pick left" readonly="readonly" name="fromDate" value="${fromDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td><td>&nbsp;&nbsp;</td>
		<td><spring:message code="ehrinventory.toDate"/></td>
		<td><input type="text" id="toDate" class="date-pick left" readonly="readonly" name="toDate" value="${toDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td><td>&nbsp;&nbsp;</td>
		<td><input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search"/></td>
	</tr>
</table>
<br />
<span class="boxHeader"><spring:message code="ehrinventory.issueDrug.list"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr>
	<th>S.No</th>
	<th><spring:message code="ehrinventory.issueDrug.account"/></th>
	<th><spring:message code="ehrinventory.issueDrug.createdOn"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listIssue}">
	<c:forEach items="${listIssue}" var="issue" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td> <a href="#" title="Detail issue drug to account" onclick="ISSUE.detailIssueDrugAccount('${issue.id}');">${issue.name}</a> </td>
		<td><openmrs:formatDate date="${issue.createdOn}" type="textbox"/></td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
	
	<tr class="paging-container">
	<td colspan="3"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</div>

</form>




<%@ include file="/WEB-INF/template/footer.jsp" %>