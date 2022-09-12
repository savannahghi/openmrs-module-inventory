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
<%@ include file="/WEB-INF/template/header.jsp" %>
<openmrs:require privilege="Drug order queue" otherwise="/login.htm" redirect="/module/ehrinventory/main.form" />
<body onLoad="reset()">
<spring:message var="pageTitle" code="ehrinventory.substore.patientQueueForDrugOrders.manage" scope="page"/>
<%@ include file="../substore/nav.jsp" %>

<h2><spring:message code="ehrinventory.substore.patientQueueForDrugOrders.manage"/></h2>
<br />

<script type="text/javascript">
// get context path in order to build controller url
	function getContextPath(){		
		pn = location.pathname;
		len = pn.indexOf("/", 1);				
		cp = pn.substring(0, len);
		return cp;
	}
</script>

<script type="text/javascript">

	currentPage = 1;
    jQuery(document).ready(function() {
		jQuery('#date').datepicker({yearRange:'c-30:c+30', dateFormat: 'dd/mm/yy', changeMonth: true, changeYear: true,showOn: "button",
                buttonImage: "${pageContext.request.contextPath}/moduleResources/ehrinventory/calendar.gif",
                buttonImageOnly: true});
    });
	
	// get queue
	function getQueue(currentPage){
		jQuery("#selection").show(0);
		this.currentPage = currentPage;
		var date = jQuery("#date").val();
		var searchKey = jQuery("#searchKey").val();
		var pgSize = jQuery("#sizeSelector").val();
		jQuery.ajax({
			type : "GET",
			url : getContextPath() + "/module/ehrinventory/patientsearchdruggqueue.form",
			data : ({
				date			: date,
				searchKey		: searchKey,
				currentPage		: currentPage,
				pgSize			: pgSize
			}),
			success : function(data) {
				jQuery("#queue").html(data);
				jQuery("#queue").show(0);	
			},
			
		});
	}

	
	/**
	 * RESET SEARCH FORM
	 *    Set date text box to current date
	 *    Empty the patient name/identifier textbox
	 */
	function reset(){
		jQuery("#date").val("${currentDate}");
		jQuery("#searchKey").val("");
		jQuery("#sizeSelector").val(100);
		jQuery("#selection").hide(0);
		jQuery("#queue").hide(0);
	}
</script> 

<div class="boxHeader">
	<strong>Patient Queue for Drug Orders</strong>
</div>
<div class="box">
	Date:
	<input id="date" value="${currentDate}" style="text-align:right;"/>
	Patient ID/Name:
	<input id="searchKey"/>
	<br/>
	<input type="button" value="Get patients" onClick="getQueue(1);"/>
<!-- 	<input type="button" value="Reset" onClick="reset();"/> -->
</div>

<div id="queue">
</div>
<div id="selection">
Show 
 <select name="sizeSelector" id="sizeSelector" onChange="getQueue(1)">
    	<option value="50" id="1">50</option>
      	<option value="100" id="2" selected>100</option>
      	<option value="150" id="3">150</option>
      	<option value="200" id="4">200</option>
	</select>
    entries 
</div>
<%@ include file="/WEB-INF/template/footer.jsp" %>