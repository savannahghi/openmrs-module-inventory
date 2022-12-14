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
<openmrs:require privilege="Drug order queue" otherwise="/login.htm" redirect="/module/ehrinventory/main.form" />

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
	
	PATIENTSEARCHRESULT = {
		oldBackgroundColor: "",
		
		visit: function(patientId,date){
		    var dat = date.toString(); 			
			window.location.href = openmrsContextPath + "/module/ehrinventory/listoforder.form?patientId=" + patientId + "&date=" + dat;
		}
	};

	jQuery(document).ready(function(){
	
		// hover rows
		jQuery(".patientSearchRow").hover(
			function(event){					
				obj = event.target;
				while(obj.tagName!="TR"){
					obj = obj.parentNode;
				}
				PATIENTSEARCHRESULT.oldBackgroundColor = jQuery(obj).css("background-color");
				jQuery(obj).css("background-color", "#00FF99");									
			}, 
			function(event){
				obj = event.target;
				while(obj.tagName!="TR"){
					obj = obj.parentNode;
				}
				jQuery(obj).css("background-color", PATIENTSEARCHRESULT.oldBackgroundColor);				
			}
		);
		
	});
</script>

<c:choose>
	<c:when test="${not empty patientList}">
		<table style="width: 100%">
			<tr>
				<td align="center"><b>S.No</b>
				</td>
				<td align="center"><b>Patient ID</b>
				</td>
				<td align="center"><b>Name</b>
				</td>
				<td align="center"><b>Age</b>
				</td>
				<td align="center"><b>Gender</b>
				</td>
				<!--  
				<td><b>Relative Name</b></td>
				<td><b>Phone number</b></td>
				-->
			</tr>
			<c:forEach items="${patientList}" var="patient" varStatus="varStatus">
				<tr
					class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } patientSearchRow'
					onclick="PATIENTSEARCHRESULT.visit(${patient.patientId},'${date}');">
					<td align="center">
					<c:choose>
					<c:when test="${pagingUtil.currentPage != 1}">
						${varStatus.count +
							(pagingUtil.currentPage-1)*pagingUtil.pageSize}
					</c:when>
					<c:otherwise>
						${varStatus.count}
					</c:otherwise>
					</c:choose>
					</td align="center">
					<td align="center">${patient.identifier}</td>
					<td align="center">${patient.givenName} ${patient.familyName} ${fn:replace(patient.middleName,","," ")}
						</td>
					<td align="center"><c:choose>
							<c:when test="${patient.age == 0}">&lt 1</c:when>
							<c:otherwise>${patient.age}</c:otherwise>
						</c:choose>
					</td>
					<td align="center">${patient.gender}</td>

				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
	No Result Found.
	</c:otherwise>
</c:choose>

<div id='paging'>
	<a style="text-decoration: none" href='javascript:getQueue(1);'>&laquo;&laquo;</a>
	<a style="text-decoration: none"
		href="javascript:getQueue(${pagingUtil.prev});">&laquo;</a>
	${pagingUtil.currentPage} / <b>${pagingUtil.numberOfPages}</b> <a
		style="text-decoration: none"
		href="javascript:getQueue(${pagingUtil.next});">&raquo;</a> <a
		style="text-decoration: none"
		href='javascript:getQueue(${pagingUtil.numberOfPages});'>&raquo;&raquo;</a>
</div>