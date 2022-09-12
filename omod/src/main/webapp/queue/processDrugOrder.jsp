
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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<openmrs:require privilege="Drug order queue" otherwise="/login.htm" redirect="/module/ehrinventory/main.form" />

<form method="post" id="processDrugOrderForm" class="box">
	<table class="box">
		<tr>
			<th>S.No</th>
			<th>Date of expiry</th>
			<th title="Date of manufacturing">DM</th>
			<th>Company name</th>
			<th>Batch no</th>
			<th title="Quantity available">Qty available</th>
			<th title="Issue quantity">Issue qty</th>
		</tr>
		<c:choose>
			<c:when test="${not empty listReceiptDrug}">
				<c:forEach items="${listReceiptDrug}" var="avaiable"
					varStatus="varStatus">
					<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
						<td><c:out value="${varStatus.count }" />
						</td>
						<td><openmrs:formatDate date="${avaiable.dateExpiry}"
								type="textbox" /></td>
						<td><openmrs:formatDate date="${avaiable.dateManufacture}"
								type="textbox" /></td>
						<td title="${avaiable.companyName }">${avaiable.companyNameShort}</td>
						<td>${avaiable.batchNo }</td>
						<td>${avaiable.currentQuantity}</td>
						<!-- ghanshyam,4-july-2013, issue no # 1984, User can issue drugs only from the first indent -->
						<td><em>*</em><input type="text" id="${avaiable.id }_quantity"
							${!varStatus.first ? 'value=0' : ''} onchange="INVENTORY.checkValueExt(this, '${avaiable.currentQuantity}');"
							name="${avaiable.id }_quantity" class="required digits" size="5" />
						</td>
						<td><input id="${avaiable.id }_drugName"
							name="${avaiable.id }_drugName" type='hidden'
							value="${avaiable.drug.name}" />
						</td>
						<td><input id="${avaiable.id }_formulation"
							name="${avaiable.id }_formulation" type='hidden'
							value="${avaiable.formulation.name}-${avaiable.formulation.dozage}" />
						</td>
						<td><input id="${avaiable.id }_formulationId"
							name="${avaiable.id }_formulationId" type='hidden'
							value="${avaiable.formulation.id }" />
						</td>
						<td><input id="${avaiable.id }_frequencyName"
							name="${avaiable.id }_frequencyName" type='hidden'
							value="${frequencyName}" />
						</td>
						<td><input id="${avaiable.id }_noOfDays"
							name="${avaiable.id }_noOfDays" type='hidden'
							value="${noOfDays}" />
						</td>
						<td><input id="${avaiable.id }_comments"
							name="${avaiable.id }_comments" type='hidden'
							value="${comments}" />
						</td>
						<c:set var="price" value="${avaiable.costToPatient}" />
						<td><input id="${avaiable.id }_price"
							name="${avaiable.id }_price" type='hidden'
							value=<fmt:formatNumber value="${price}" type="number" maxFractionDigits="2"/>
							/>
						</td>
						
					</tr>
				</c:forEach>

			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="6">This drug is empty in your store please indent
						it <input type="hidden" id="${avaiable.id }"
						name="${avaiable.id }" class="required digits" size="5" />
					</td>

				</tr>
			</c:otherwise>
		</c:choose>
	</table>

	<br /> <input type="button"
		class="ui-button ui-widget ui-state-default ui-corner-all"
		value="Issue" onClick="issueDrugOrder('${listOfDrugQuantity}');"> <input
		type="button"
		class="ui-button ui-widget ui-state-default ui-corner-all"
		value="Cancel" onclick="cancel();">
</form>