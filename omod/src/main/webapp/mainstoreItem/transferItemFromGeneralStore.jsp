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

<openmrs:require privilege="View mainstore" otherwise="/login.htm" redirect="/module/ehrinventory/main.form" />

<spring:message var="pageTitle" code="ehrinventory.indentItem.manage" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="nav.jsp" %>
<script>
	jQuery(document).ready(function(){
		jQuery('.date-pick').datepicker({minDate: '-100y', dateFormat: 'dd/mm/yy'});
	});
</script>
<h2><spring:message code="ehrinventory.indentItem.manage"/></h2>

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>


<br /><br />
<form method="get"  id="form">


<table >
	<tr>
		<td valign="top"><spring:message code="ehrinventory.store.store"/></td>
		<td>
			<select name="storeId" style="width:200px;">
	    	    <option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
				<c:forEach items="${listStore}" var="store">
					<option value="${store.id}"
					<c:if test="${store.id == storeId}"> selected</c:if>
					>${store.name}</option>
				</c:forEach>
	   		</select>
		</td>
		<td><spring:message code="ehrinventory.indentItem.status"/></td>
		<td>
			<select name="statusId" >
      		<option value=""><spring:message code="ehrinventory.pleaseSelect"/></option>
			<c:forEach items="${listMainStoreStatus}" var="status">
				<option value="${status.id}" 
				<c:if test="${status.id == statusId }">selected</c:if>
				>${status.name}</option>
			</c:forEach>
	   </select>
		<td><spring:message code="ehrinventory.indentItem.name"/></td>
		<td><input type="text" id="indentName" name="indentName" value="${indentName}" /></td>
		<td><spring:message code="ehrinventory.fromDate"/></td>
		<td><input type="text" id="fromDate" class="date-pick left" readonly="readonly" name="fromDate" value="${fromDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><spring:message code="ehrinventory.toDate"/></td>
		<td><input type="text" id="toDate" class="date-pick left" readonly="readonly" name="toDate" value="${toDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search" /></td>
	</tr>
</table>

<span class="boxHeader"><spring:message code="ehrinventory.indentItem.list"/></span>
<div class="box">
<c:choose>

<c:when test="${not empty listIndent}">
<table width="100%" cellpadding="0" cellspacing="0">
<tr align="center">
	<th >S.No</th>
	<th align="center" ><spring:message code="ehrinventory.indentItem.fromStore"/></th>
	<th align="center" >Indent Name</th>
	<th align="center" ><spring:message code="ehrinventory.indentItem.createdOn"/></th>
	<th align="center" ><spring:message code="ehrinventory.indentItem.status"/></th>
	<th></th>
	
</tr>
<c:forEach items="${listIndent}" var="indent" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td>${indent.store.name}</td>
		<td align="center"><a href="#" title="Detail indent" onclick="INDENT.detailItemIndent('${ indent.id}');">${indent.name}</a> </td>
		<td align="center"><openmrs:formatDate date="${indent.createdOn}" type="textbox"/> </td>
		<td align="center">${indent.mainStoreStatusName} </td>
		<td>
		<c:if test="${indent.mainStoreStatus == 1 }">
			<a href="#" onclick="ACT.go('mainStoreItemProcessIndent.form?indentId=${ indent.id}');"><spring:message code="ehrinventory.indentItem.process"/></a>
		</c:if>
		</td>
	</tr>
</c:forEach>






<tr class="paging-container">
	<td colspan="8"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
<br>
<div id="divDetailIndent"></div>

</c:when>
<c:otherwise>
	No indent found.
</c:otherwise>

</c:choose>
</div>
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>