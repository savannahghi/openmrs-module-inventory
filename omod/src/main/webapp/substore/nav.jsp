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
<%@ include file="../includes/js_css.jsp" %>
<br/>
<center><openmrs:hasPrivilege privilege="Add/Edit substore"><b>Drug&nbsp;| <a href="#" onclick="ACT.go('subStoreIssueItem.form');">Item </a></b></openmrs:hasPrivilege></center>
<center><openmrs:hasPrivilege privilege="Drug/Item Dispense"><b>Drug&nbsp;| <a href="#" onclick="ACT.go('subStoreIssueItemPatientList.form');">Item </a></b></openmrs:hasPrivilege></center>

<br/><br/>




<openmrs:hasPrivilege privilege="Drug order queue"><b><a href="#" onclick="ACT.go('patientQueueDrugOrder.form');"><spring:message code="ehrinventory.substore.patientQueueForDrugOrders"/></a></b></openmrs:hasPrivilege>
<openmrs:hasPrivilege privilege="Drug/Item Dispense"><b><a href="#" onclick="ACT.go('subStoreIssueDrugList.form');"><spring:message code="ehrinventory.substore.listDrugPatient"/></a></b></openmrs:hasPrivilege>&nbsp;
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('subStoreListPatient.form');"><spring:message code="ehrinventory.substore.listPatient"/></a></b></openmrs:hasPrivilege>&nbsp;
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('subStoreIssueDrugForm.form');"><spring:message code="ehrinventory.substore.issueDrugPatient"/></a></b></openmrs:hasPrivilege>&nbsp;
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('subStoreIssueDrugAccountList.form');"><spring:message code="ehrinventory.substore.issueDrugAccount"/></a></b></openmrs:hasPrivilege>&nbsp;
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('subStoreIndentDrugList.form');"><spring:message code="ehrinventory.substore.indentDrug"/></a></b></openmrs:hasPrivilege>&nbsp;
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('viewStockBalanceSubStore.form');"><spring:message code="ehrinventory.viewStockBalance"/></a></b></openmrs:hasPrivilege>&nbsp;
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('viewStockBalanceExpiry.form');"><spring:message code="ehrinventory.viewStockBalanceExpiry"/></a></b></openmrs:hasPrivilege>&nbsp;
<br/><br/>

<!--  15-june-2013 New Requirement #1636 User is able to see and dispense drugs in patient queue for issuing drugs, as ordered from dashboard -->
<!--  17-Nov-2014 In order to change the order of the sub menues-->



