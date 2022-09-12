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
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Drug order queue" otherwise="/login.htm" redirect="/module/ehrinventory/main.form" />
<%@ include file="../includes/js_css.jsp"%>

<div style="max-height: 50px; max-width: 1800px;">
	<b class="boxHeader">List of Order</b>
</div>
<input type="hidden" id="patientType" value="${patientType}">
<div>	
	<table>
		<tr>
			<td>Patient ID :</td>
            <td>&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;${patientSearch.identifier}</td>
		</tr>
		<tr>
			<td>Name :</td><td>&nbsp;</td>
			<td>&nbsp;${patientSearch.givenName}&nbsp;
				${patientSearch.familyName}&nbsp;&nbsp;${fn:replace(patientSearch.middleName,","," ")}</td>
		</tr>
        <tr>
        	<td>Age:</td><td>&nbsp;</td>
        	<td><c:choose>
							<c:when test="${patientSearch.age == 0}">&lt 1</c:when>
							<c:otherwise>${patientSearch.age}</c:otherwise>
						</c:choose>
					</td>
      </tr>
        <tr>
        	<td>Gender:</td><td>&nbsp;</td>
        	<td>&nbsp;${patientSearch.gender}</td>
        </tr>
		<tr>
			<td>Date :</td><td>&nbsp;</td>
			<td>${date}</td>
		</tr>
	</table>
</div>

<br />

	<table id="myTable" class="tablesorter" class="thickbox">
	<thead>
		<tr align="center">
			<th>S.No</th>
			<th>Order Id</th>
			<th>Date</th>  
			<th>Sent From</th>         
            
		</tr>
	</thead>
	<tbody>
		<c:forEach var="listOfOrder" items="${listOfOrders}" varStatus="index">
			<c:choose>
				<c:when test="${index.count mod 2 == 0}">
					<c:set var="klass" value="odd" />
				</c:when>
				<c:otherwise>
					<c:set var="klass" value="even" />
				</c:otherwise>
			</c:choose>
			<tr class="${klass}">
				<td>${index.count}</td>
				<td><a href="drugorder.form?patientId=${patientId}&encounterId=${listOfOrder.encounter.encounterId}
				&date=${date}&patientType=${patientType}">${listOfOrder.encounter.encounterId}</a>
				</td>
				<td><openmrs:formatDate date="${listOfOrder.createdOn}" /></td>
				<td>${listOfOrder.referralWardName}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@ include file="/WEB-INF/template/footer.jsp"%>