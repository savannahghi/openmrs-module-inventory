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
<script type="text/javascript">

	function runout(urlS,value){
		setTimeout(function(){
			self.parent.tb_remove();
			self.parent.ACT.go(urlS);
			},value);
		//setTimeout("self.parent.location.href=self.parent.location.href;self.parent.tb_remove()",3000);
	}
</script>
<body onload="runout('${urlS}',3000);">
<center>
		<div style="height:40px; float: center; vertical-align:middle"><img src="${pageContext.request.contextPath}/moduleResources/ehrinventory/ajax-loader.gif"/></div>
		<span class="text center" style="color:#000000">
         ${message}
		<a href="#"  onclick="runout('${urlS}',0);">click here</a>
		</span>
</center>
</body>
</html>
