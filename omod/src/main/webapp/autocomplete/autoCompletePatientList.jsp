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


<table class="box">
		<tr>
			<th>Identifier</th>
			<th>Name</th>
			<th>Age</th>
		</tr>
		<c:if test ="${not empty patients }">
			<c:forEach items="${patients }" var="patient" varStatus="varStatus">
				<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '  >
					<td ><a href="#" onclick="ISSUE.addPatient('createPatientIssueDrug.form?patientId=${patient.patientId}');">${patient.patientIdentifier.identifier}</a></td>
					<td>${patient.givenName}&nbsp;${patient.familyName}&nbsp;${fn:replace(patient.middleName,","," ")}</td>
	                <td>
	                	<c:choose>
	                		<c:when test="${patient.age == 0  }">&lt 1</c:when>
	                		<c:otherwise >${patient.age }</c:otherwise>
	                	</c:choose>
	                </td>
				</tr>
			</c:forEach>
		</c:if>
	</table>