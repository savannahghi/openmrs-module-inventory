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
<center><b>Drug&nbsp;| <a href="#" onclick="ACT.go('itemViewStockBalance.form');">Item</a></b></center>
<br/><br/>
<b><a href="#" onclick="ACT.go('viewStockBalance.form');"><spring:message code="ehrinventory.viewStockBalance"/></a></b>&nbsp;|
<b><a href="#" onclick="ACT.go('viewStockBalanceExpiry.form');"><spring:message code="ehrinventory.viewStockBalanceExpiry"/></a></b>&nbsp;|
<!-- 
<b><a href="#" onclick="ACT.go('purchaseOrderForGeneralStoreList.form');"><spring:message code="ehrinventory.mainStore.purchaseOrderForGeneralStore"/></a></b>&nbsp;|
 --> 
<b><a href="#" onclick="ACT.go('receiptsToGeneralStoreList.form');"><spring:message code="ehrinventory.mainStore.receiptsToGeneralStore"/></a></b>&nbsp;|
<b><a href="#" onclick="ACT.go('transferDrugFromGeneralStore.form');"><spring:message code="ehrinventory.mainStore.transferFromGeneralStore"/></a></b>
<br/><br/>

