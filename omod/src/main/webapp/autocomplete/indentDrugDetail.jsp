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
<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<%@ include file="../includes/js_css.jsp" %>

<c:choose>
	<c:when test="${empty listTransactionDetail}">
		<span class="boxHeader"><spring:message code="ehrinventory.indent.detail"/></span>
		<div class="box">
		
		<table width="100%" cellpadding="5" cellspacing="0">
			<tr align="center">
			<th>S.No</th>
			<th><spring:message code="ehrinventory.indent.category"/></th>
			<th><spring:message code="ehrinventory.indent.name"/></th>
			<th><spring:message code="ehrinventory.indent.formulation"/></th>
			<th><spring:message code="ehrinventory.indent.quantity"/></th>
			<th><spring:message code="ehrinventory.indent.transferQuantity"/></th>
			</tr>
			<c:choose>
			<c:when test="${not empty listIndentDetail}">
			<c:forEach items="${listIndentDetail}" var="indent" varStatus="varStatus">
			<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
				<td><c:out value="${ varStatus.count}"/></td>
				<td>${indent.drug.category.name} </td>	
				<td>${indent.drug.name}</td>
				<td>${indent.formulation.name}-${indent.formulation.dozage}</td>
				<td>${indent.quantity}</td>
				<td>${indent.mainStoreTransfer}</td>
				</tr>
			</c:forEach>
			</c:when>
			</c:choose>	
		</table>
		</div>
		<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.indent.print"/>" onClick="INDENT.printDiv();" />
		
		<!-- PRINT DIV -->
		<div  id="printDiv" style="display: none;">  
		<div style="margin: 10px auto; width: 981px; font-size: 1.0em;font-family:'Dot Matrix Normal',Arial,Helvetica,sans-serif;">      		
		<br />
		<br />      		
		<center style="float:center;font-size: 2.2em">Indent From ${store.name}</center>
		<br/>
		<br/>
		<span style="float:right;font-size: 1.7em">Date: <openmrs:formatDate date="${date}" type="textbox"/></span>
		<br />
		<br />
		<table border="1">
			<tr>
			<th>S.No</th>
			<th><spring:message code="ehrinventory.drug.category"/></th>
			<th><spring:message code="ehrinventory.drug.name"/></th>
			<th><spring:message code="ehrinventory.drug.formulation"/></th>
			<th><spring:message code="ehrinventory.indent.quantity"/></th>
			<th><spring:message code="ehrinventory.indent.transferQuantity"/></th>
			</tr>
			<c:choose>
			<c:when test="${not empty listIndentDetail}">
			<c:forEach items="${listIndentDetail}" var="indent" varStatus="varStatus">
			<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
				<td><c:out value="${varStatus.count }"/></td>
				<td>${indent.drug.category.name} </td>	
				<td>${indent.drug.name}</td>
				<td>${indent.formulation.name}-${indent.formulation.dozage}</td>
				<td>${indent.quantity}</td>
				<td>${indent.mainStoreTransfer}</td>
				</tr>
			</c:forEach>
			</c:when>
			</c:choose>
		</table>
		
		<br/><br/><br/><br/><br/><br/>
		<span style="float:left;font-size: 1.5em">Signature of sub-store/ Stamp</span><span style="float:right;font-size: 1.5em">Signature of inventory clerk/ Stamp</span>
		<br/><br/><br/><br/><br/><br/>
		<span style="margin-left: 13em;font-size: 1.5em">Signature of Medical Superintendent/ Stamp</span>
		</div>
		</div>
		<!-- END PRINT DIV -->   
	</c:when>
	<c:otherwise>
		<span class="boxHeader"><spring:message code="ehrinventory.indent.detail"/></span>
		<div class="box">
		
		<table width="100%" cellpadding="5" cellspacing="0">
			<tr align="center">
			<th>S.No</th>
			<th><spring:message code="ehrinventory.indent.category"/></th>
			<th><spring:message code="ehrinventory.indent.name"/></th>
			<th><spring:message code="ehrinventory.indent.formulation"/></th>
			<th><spring:message code="ehrinventory.indent.quantityIndent"/></th>
			<th><spring:message code="ehrinventory.receiptDrug.batchNo"/></th>
			<th><spring:message code="ehrinventory.receiptDrug.dateExpiry"/></th>
			<th><spring:message code="ehrinventory.receiptDrug.companyName"/></th>
			<th><spring:message code="ehrinventory.indent.transferQuantity"/></th>
			</tr>
			<c:choose>
			<c:when test="${not empty listIndentDetail}">
			<c:forEach items="${listIndentDetail}" var="indent" varStatus="varStatus">
			<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
				<td><c:out value="${ varStatus.count}"/></td>
				<td>${indent.drug.category.name} </td>	
				<td>${indent.drug.name}</td>
				<td>${indent.formulation.name}-${indent.formulation.dozage}</td>
				<td>${indent.quantity}</td>

				<c:set var="count" value="0" />	
				<c:set var="check" value="0" />	
				<c:forEach items="${listTransactionDetail}" var="trDetail" >
					<c:if test="${trDetail.drug.id == indent.drug.id &&  trDetail.formulation.id == indent.formulation.id }">
							<c:set var="check" value="1" />	
							<c:choose>
							<c:when test="${count >0}">
							</tr>
							<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td>${trDetail.batchNo }</td>
							<td><openmrs:formatDate date="${trDetail.dateExpiry}" type="textbox"/></td>
							<td>${trDetail.companyName }</td>
							<td>${trDetail.issueQuantity }</td>
							</tr>
							</c:when>
							<c:otherwise>
								<td>${trDetail.batchNo }</td>
								<td><openmrs:formatDate date="${trDetail.dateExpiry}" type="textbox"/></td>
								<td>${trDetail.companyName }</td>
								<td>${trDetail.issueQuantity }</td>
								</tr>
							</c:otherwise>
							
							</c:choose>
							<c:set var="count" value="${count + 1 }"/>	
					</c:if>
				</c:forEach>
				<c:if test="${check == '0'}">
					<td>N/A</td>
					<td>N/A</td>
					<td>N/A</td>
					<td>0</td>
					</tr>	
				</c:if>
			</c:forEach>
			</c:when>
			</c:choose>	
		</table>
		</div>
		<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="ehrinventory.indent.print"/>" onClick="INDENT.printDiv();" />
		
		<!-- PRINT DIV -->
		<div  id="printDiv" style="display: none;">  
		<div style="margin: 10px auto; width: 981px; font-size: 1.0em;font-family:'Dot Matrix Normal',Arial,Helvetica,sans-serif;">      		
		<br />
		<br />      		
		<center style="float:center;font-size: 2.2em">Indent From ${store.name}</center>
		<br/>
		<br/>
		<span style="float:right;font-size: 1.7em">Date: <openmrs:formatDate date="${date}" type="textbox"/></span>
		<br />
		<br />
		<table border="1">
			<tr>
			<th>S.No</th>
			<th><spring:message code="ehrinventory.indent.category"/></th>
			<th><spring:message code="ehrinventory.indent.name"/></th>
			<th><spring:message code="ehrinventory.indent.formulation"/></th>
			<th><spring:message code="ehrinventory.indent.quantityIndent"/></th>
			<th><spring:message code="ehrinventory.receiptDrug.batchNo"/></th>
			<th><spring:message code="ehrinventory.receiptDrug.dateExpiry"/></th>
			<th><spring:message code="ehrinventory.receiptDrug.companyName"/></th>
			<th><spring:message code="ehrinventory.indent.transferQuantity"/></th>
			</tr>
			<c:choose>
			<c:when test="${not empty listIndentDetail}">
			<c:forEach items="${listIndentDetail}" var="indent" varStatus="varStatus">
			<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
				<td><c:out value="${ varStatus.count}"/></td>
				<td>${indent.drug.category.name} </td>	
				<td>${indent.drug.name}</td>
				<td>${indent.formulation.name}-${indent.formulation.dozage}</td>
				<td>${indent.quantity}</td>

				<c:set var="count" value="0" />	
				<c:set var="check" value="0" />	
				<c:forEach items="${listTransactionDetail}" var="trDetail" >
					<c:if test="${trDetail.drug.id == indent.drug.id &&  trDetail.formulation.id == indent.formulation.id }">
							<c:set var="check" value="1" />	
							<c:choose>
							<c:when test="${count >0}">
							</tr>
							<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td>${trDetail.batchNo }</td>
							<td><openmrs:formatDate date="${trDetail.dateExpiry}" type="textbox"/></td>
							<td>${trDetail.companyName }</td>
							<td>${trDetail.issueQuantity }</td>
							</tr>
							</c:when>
							<c:otherwise>
								<td>${trDetail.batchNo }</td>
								<td><openmrs:formatDate date="${trDetail.dateExpiry}" type="textbox"/></td>
								<td>${trDetail.companyName }</td>
								<td>${trDetail.issueQuantity }</td>
								</tr>
							</c:otherwise>
							
							</c:choose>
							<c:set var="count" value="${count + 1 }"/>	
					</c:if>
				</c:forEach>
				<c:if test="${check == '0'}">
					<td>N/A</td>
					<td>N/A</td>
					<td>N/A</td>
					<td>0</td>
					</tr>	
				</c:if>
			</c:forEach>
			</c:when>
			</c:choose>	
		</table>
		
		<br/><br/><br/><br/><br/><br/>
		<span style="float:left;font-size: 1.5em">Signature of sub-store/ Stamp</span><span style="float:right;font-size: 1.5em">Signature of inventory clerk/ Stamp</span>
		<br/><br/><br/><br/><br/><br/>
		<span style="margin-left: 13em;font-size: 1.5em">Signature of Medical Superintendent/ Stamp</span>
		</div>
		</div>
		<!-- END PRINT DIV -->   
	</c:otherwise>
</c:choose>