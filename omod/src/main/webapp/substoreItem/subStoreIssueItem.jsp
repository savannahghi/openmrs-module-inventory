<%@ include file="/WEB-INF/template/include.jsp" %>


<spring:message var="pageTitle" code="ehrinventory.issueItem.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="nav.jsp" %>
<h2><spring:message code="ehrinventory.issueItem.manage"/></h2>
<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='ehrinventory.issueItemPatient.add'/>" onclick="ACT.go('subStoreIssueItemPatientForm.form');"/>
<br /><br />