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

<script type="text/javascript">

	function sizeSelecting(size){
		document.getElementsByName("sizeOfPage")[0].value=size;	
	}

	function getPaeSize(){		
		var querystring = window.location.href.split('=');
		var myValue = querystring[1];
		return myValue;
	}
	
	function sizeSeting(){
		var size=getPaeSize();
		sizeSelecting(size);	
	}
</script>

<body onload="sizeSeting();">

	<br>
	
	<c:set var="moduleId" value="ehrinventory"/>
	<c:set var="baseLink" value="${pagingUtil.baseLink}" />
	<c:set var="pageSize" value="${pagingUtil.pageSize}" />
	<c:set var="currentPage" value="${pagingUtil.currentPage}" />
	<c:set var="startPage" value="${pagingUtil.startPage}" />
	<c:set var="numberOfPages" value="${pagingUtil.numberOfPages}" />	
	
	<input type="hidden" id="baseLink" value="${baseLink}"/>
	<input type="hidden" id="currentPage" value="${currentPage}"/>


	<c:if test="${numberOfPages > 0 }">
		
		
		<div class="paging">
		<c:choose>
			<c:when test="${currentPage > 1}">
				<c:set var="prev" value="${currentPage - 1 }"/>
				<a href="${baseLink}currentPage=1&pageSize=${pageSize}" class="first" title="First">&laquo;&laquo;</a>
				<a href="${baseLink}currentPage=${prev}&pageSize=${pageSize}" class="prev" title="Previous">&laquo;</a>
			</c:when>
			<c:otherwise>
				<span class="first" title="First">&laquo;&laquo;</span>
				<span class="prev" title="Previous">&laquo;</span>
			</c:otherwise>
		</c:choose>
		<c:forEach begin="0" end="4" step="1" var="i">
			<c:set var="p" value="${startPage + i }"/>
			<c:if test="${p <= numberOfPages }">
				<c:if test="${i > 0}">
					<span class="seperator">|</span>
				</c:if>
				<c:choose>
					<c:when test="${p != currentPage }">
						<a href="${baseLink}currentPage=${p}&pageSize=${pageSize}" class="page" title="Page $p">${p}</a>
					</c:when>
					<c:otherwise>
						<span class="page" title="Page $p">${p}</span>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:forEach>
		<c:choose>
			<c:when test="${currentPage < numberOfPages  }">
				<c:set var="next" value="${currentPage + 1  }"/>
				<a href="${baseLink}currentPage=${next}&pageSize=${pageSize}" class="next" title="Next">&raquo;</a>
				<a href="${baseLink}currentPage=${numberOfPages}&pageSize=${pageSize}" class="last" title="Last">&raquo;&raquo;</a>
			</c:when>
			<c:otherwise>
				<span class="next" title="Next">&raquo; </span>
				<span class="last" title="Last">&raquo;&raquo;</span>
			</c:otherwise>
		</c:choose>
		</div><br>
		<div id="selection">
Show 
 <select name="sizeOfPage" id="sizeOfPage" onChange="changePageSize('${baseLink}');">
    	<option value="50" id="1">50</option>
      	<option value="100" id="2">100</option>
      	<option value="150" id="3">150</option>
      	<option value="200" id="4">200</option>
	</select>
    entries 
</div>
	</c:if>
