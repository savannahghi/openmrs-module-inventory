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
<openmrs:require privilege="Drug/Item Dispense" otherwise="/login.htm" redirect="/module/ehrinventory/main.form" />

<spring:message var="pageTitle" code="ehrinventory.issueItem.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="nav.jsp" %>
<h2><spring:message code="ehrinventory.issueItem.manage"/></h2>
<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>

<br /><br />

<form method="get"  id="form">
<table >
	<tr>
		<td>Identifier/Name</td>
		<td>
			<input type="text" name="issueName" id="issueName" value="${issueName }"/><td>&nbsp;&nbsp;</td>
		</td>
		<td><spring:message code="ehrinventory.fromDate"/></td>
		<td><input type="text" id="fromDate" class="date-pick left" readonly="readonly" name="fromDate" value="${fromDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td><td>&nbsp;&nbsp;</td>
		<td><spring:message code="ehrinventory.toDate"/></td>
		<td><input type="text" id="toDate" class="date-pick left" readonly="readonly" name="toDate" value="${toDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td><td>&nbsp;&nbsp;</td>
		<td>Receipt No.</td>
		<td><input type="text" name="receiptId" id="receiptId" value="${receiptId }"/>
		<td><input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search"/></td>
	</tr>
</table>
<br />
<span class="boxHeader"><spring:message code="ehrinventory.issueItem.list"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr>
	<th>S.No</th>
	<th>Receipt No.</th>
	<th><spring:message code="ehrinventory.issueItem.identifier"/></th>
	<th>Name</th>
	<th>Age</th>
	<th><spring:message code="ehrinventory.issueItem.createdOn"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listIssue}">
	<c:forEach items="${listIssue}" var="issue" varStatus="varStatus">
	<c:choose>
	<c:when test="${(issue.values==0)&&(fromDate==null)&&(toDate==null)}" >
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td> ${issue.id}</td>
		<td> <a href="#" title="Detail issue Item to this patient" onclick="ISSUE.detailIssueItemPatient('${issue.id}');">${issue.identifier}</a> </td>
		<td>${issue.patient.givenName}&nbsp;${issue.patient.familyName}&nbsp;${fn:replace(issue.patient.middleName,","," ")}</td>
		<td>
              	<c:choose>
              		<c:when test="${issue.patient.age == 0  }">&lt 1</c:when>
              		<c:otherwise >${issue.patient.age }</c:otherwise>
              	</c:choose>
        </td>	
		<td><openmrs:formatDate date="${issue.createdOn}" type="textbox"/></td>
		</tr>
		</c:when>
		<c:when test="${(issue.values==0)&&(not empty fromDate )&&(not empty toDate)}" >
		<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td> ${issue.id}</td>
		<td> <a href="#" title="Detail issue Item to this patient" onclick="ISSUE.detailIssueItemPatient('${issue.id}');">${issue.identifier}</a> </td>
		<td>${issue.patient.givenName}&nbsp;${issue.patient.familyName}&nbsp;${fn:replace(issue.patient.middleName,","," ")}</td>
		<td>
              	<c:choose>
              		<c:when test="${issue.patient.age == 0  }">&lt 1</c:when>
              		<c:otherwise >${issue.patient.age }</c:otherwise>
              	</c:choose>
        </td>	
		<td><openmrs:formatDate date="${issue.createdOn}" type="textbox"/></td>
		</tr>
		</c:when>
		<c:when test="${(issue.values!=0)&&(not empty fromDate )&&(not empty toDate)}" >
		<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td> ${issue.id}</td>
		<td> <a href="#" title="Detail issue Item to this patient" onclick="ISSUE.detailIssueItemPatient('${issue.id}');">${issue.identifier}</a> </td>
		<td>${issue.patient.givenName}&nbsp;${issue.patient.familyName}&nbsp;${fn:replace(issue.patient.middleName,","," ")}</td>
		<td>
              	<c:choose>
              		<c:when test="${issue.patient.age == 0  }">&lt 1</c:when>
              		<c:otherwise >${issue.patient.age }</c:otherwise>
              	</c:choose>
        </td>	
		<td><openmrs:formatDate date="${issue.createdOn}" type="textbox"/></td>
		</tr>
		</c:when>
		</c:choose>
	</c:forEach>
	</c:when>
	</c:choose>
	
	<tr class="paging-container">
	<td colspan="5"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</div>

</form>




<%@ include file="/WEB-INF/template/footer.jsp" %>