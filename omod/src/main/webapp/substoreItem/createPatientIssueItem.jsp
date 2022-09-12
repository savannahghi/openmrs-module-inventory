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
<div id="searchbox"></div>
<div id="patientSearchResult"></div>

<script type="text/javascript">

	jQuery(document).ready(function(){
		jQuery("#searchbox").showPatientSearchBox({					
			resultView: "/module/ehrinventory/patientsearch/issuePatientItem",
			rowPerPage: 15
		});		
	});
</script>