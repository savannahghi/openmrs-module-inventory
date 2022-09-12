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


<center><openmrs:hasPrivilege privilege="Drug order queue"><b><a href="#" onclick="ACT.go('patientQueueDrugOrder.form');">Drug</a>&nbsp;| Item </b></openmrs:hasPrivilege></center>
<center><openmrs:hasPrivilege privilege="Drug/Item Dispense"><b><a href="#" onclick="ACT.go('subStoreIssueDrugList.form');">Drug</a>&nbsp;| Item </b></openmrs:hasPrivilege></center>
<br/><br/>
<openmrs:hasPrivilege privilege="Add/Edit substore"><b><a href="#" onclick="ACT.go('subStoreIssueItem.form');"><spring:message code="ehrinventory.substore.issueItemPatient"/></a></b></openmrs:hasPrivilege>
<openmrs:hasPrivilege privilege="Drug/Item Dispense"><b><a href="#" onclick="ACT.go('subStoreIssueItemPatientList.form');"><spring:message code="ehrinventory.substore.listItemPatient"/></a></b></openmrs:hasPrivilege>
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('subStoreListItemPatient.form');"><spring:message code="ehrinventory.substore.listPatientItem"/></a></b></openmrs:hasPrivilege>
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('subStoreIndentItemList.form');"><spring:message code="ehrinventory.substore.indentItem"/></a></b></openmrs:hasPrivilege>
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('subStoreIssueItemList.form');"><spring:message code="ehrinventory.substore.issueItem"/></a></b></openmrs:hasPrivilege>
<openmrs:hasPrivilege privilege="Add/Edit substore">|&nbsp;<b><a href="#" onclick="ACT.go('itemViewStockBalanceSubStore.form');"><spring:message code="ehrinventory.substoreItem.viewitemStockBalance"/></a></b></openmrs:hasPrivilege>

<br/><br/>


