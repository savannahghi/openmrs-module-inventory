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


<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="nav.jsp" %>
<h2><spring:message code="ehrinventory.issueDrug.manage"/></h2>
<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>

<br /><br />


<style>
@media print {
	.donotprint {
		display: none;
	}
	.spacer {
		margin-top: 100px;
		font-family: "Dot Matrix Normal", Arial, Helvetica, sans-serif;
		font-style: normal;
		font-size: 14px;
	}
	.printfont {
		font-family: "Dot Matrix Normal", Arial, Helvetica, sans-serif;
		font-style: normal;
		font-size: 14px;
	}
}
</style>
<form method="get"  id="form">
<table >
	<tr>
		<td>Patient Name/ID</td>
		<td>
			<input type="text" name="issueName" id="issueName" value="${issueName }"/>
		</td><td>&nbsp;&nbsp;</td>
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
<span class="boxHeader"><spring:message code="ehrinventory.issueDrug.list"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr>
	<th>S.No </th>
	<th>Receipt No.</th>
	<th><spring:message code="ehrinventory.issueDrug.identifier"/></th>
    <th>Drug Regimen</th>
	<th>Name</th>
	<th>Age</th>
	<th>Gender</th>
	<th><spring:message code="ehrinventory.issueDrug.createdOn"/></th>
	</tr>
<c:choose>
	<c:when test="${(not empty listIssue)  }">
	<c:forEach items="${listIssue}" var="issue" varStatus="varStatus">
	<c:choose>
	<c:when test="${(issue.values==0)&&(issue.statuss==1)&&(fromDate==null)&&(toDate==null) }">
	
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
	     
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td> ${issue.id}</td>
		<td> ${issue.identifier}</td>
       	<td> <a href="#" title="Detail issue drug to this patient" onclick="ISSUE.detailIssueDrug('${issue.id}');">View/Print</a> </td>
		<td>${issue.patient.givenName}&nbsp;${issue.patient.familyName}&nbsp;${fn:replace(issue.patient.middleName,","," ")}</td>
		<td><center>
              	<c:choose>
              		<c:when test="${issue.patient.age == 0  }">&lt 1</c:when>
              		<c:otherwise >${issue.patient.age }</c:otherwise>
              	</c:choose>
              	</center>
        </td>	
        <td><center>${issue.patient.gender }</center></td>
		<td><openmrs:formatDate date="${issue.createdOn}" type="textbox"/></td>
		</tr>
		</c:when>
		<c:when test="${(issue.values==0)&&(issue.statuss==1)&&(not empty fromDate)&&(not empty toDate) }">
	
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
	     
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td> ${issue.id}</td>
		<td> ${issue.identifier}</td>
       	<td> <a href="#" title="Detail issue drug to this patient" onclick="ISSUE.detailIssueDrug('${issue.id}');">View/Print</a> </td>
		<td>${issue.patient.givenName}&nbsp;${issue.patient.familyName}&nbsp;${fn:replace(issue.patient.middleName,","," ")}</td>
		<td><center>
              	<c:choose>
              		<c:when test="${issue.patient.age == 0  }">&lt 1</c:when>
              		<c:otherwise >${issue.patient.age }</c:otherwise>
              	</c:choose>
              	</center>
        </td>	
        <td><center>${issue.patient.gender }</center></td>
		<td><openmrs:formatDate date="${issue.createdOn}" type="textbox"/></td>
		</tr>
		</c:when>
	<c:when test="${(issue.values!=0)&&(issue.statuss==1)&&(not empty fromDate)&&(not empty toDate) }">
	
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
	     
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td> ${issue.id}</td>
		<td> ${issue.identifier}</td>
       	<td> <a href="#" title="Detail issue drug to this patient" onclick="ISSUE.detailIssueDrug('${issue.id}');">View/Print</a> </td>
		<td>${issue.patient.givenName}&nbsp;${issue.patient.familyName}&nbsp;${fn:replace(issue.patient.middleName,","," ")}</td>
		<td><center>
              	<c:choose>
              		<c:when test="${issue.patient.age == 0  }">&lt 1</c:when>
              		<c:otherwise >${issue.patient.age }</c:otherwise>
              	</c:choose>
              	</center>
        </td>	
        <td><center>${issue.patient.gender }</center></td>
		<td><openmrs:formatDate date="${issue.createdOn}" type="textbox"/></td>
		</tr>
		</c:when>
		
		</c:choose>
		
		</c:forEach>
		</c:when>
		</c:choose>
		</table>
		</div>
		</form>