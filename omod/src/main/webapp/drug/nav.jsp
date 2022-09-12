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
<b><spring:message code="ehrinventory.drug.manage"/></b>
<br/>
<br/>
<b><a href="#" onclick="ACT.go('drugCategoryList.form');"><spring:message code="ehrinventory.drugCategory.manage"/></a></b>&nbsp; |
<b><a href="#" onclick="ACT.go('drugUnitList.form');"><spring:message code="ehrinventory.drugUnit.manage"/></a></b>&nbsp;|
<b><a href="#" onclick="ACT.go('drugFormulationList.form');"><spring:message code="ehrinventory.drugFormulation.manage"/></a></b>&nbsp;|
<b><a href="#" onclick="ACT.go('drugList.form');"><spring:message code="ehrinventory.drug.manage"/></a></b>
<br/><br/>


