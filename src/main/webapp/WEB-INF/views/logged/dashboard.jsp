<%@include file="/WEB-INF/views/logged/header.jsp"%>
<div class="span-7">
	<%-- Date --%>
	<div class="top-grey-box date-container">
		<%-- TODO: Capitalize first letter --%>
		<spring:message code="header.dateFormat" var="dateFormat" />
		<c:set var="today" value="<%=new java.util.Date()%>" />
		<fmt:formatDate value="${today}" pattern="${dateFormat}" />
	</div>

	<div class="categories-main-container">
		<%-- Categories --%>
		<%-- TODO: Change design, view/hide is not clear / ugly --%>
		<b>Categories</b>:
		<input id="view-example" type="checkbox" checked="checked" disabled="disabled" /> View
		<input id="hide-example" type="checkbox" disabled="disabled" /> Hide
		<div id="categories-container">
			<br />
			<ul id="categories" class="pointer">
				<c:forEach items="${categoriesList}" var="cur">
					<li id="cat-<c:out value="${cur.id}" />"> <input type="checkbox" <c:if test="${cur.displayed == true}">checked="checked"</c:if> /> <c:out value="${cur.name}" /></li>
				</c:forEach>
			</ul>
			<div id="cat-unclassified"> <input type="checkbox" /> Unclassified</div>
			<div class='trash pointer'>Trash</div>
		</div>
		<div class="right"><a id="cat-edit" class="pointer">Edit / Add</a></div>

		<%-- Categories editing --%>
		<div id="cat-edit-container" class="hide">
		    <b>Insert:</b><br />
			<input id="cat-name" type="text" />
			<i>(press 'Enter' key to insert category)</i>
		</div>
	</div>
</div>

<div class="span-17 last">
	<%-- Add a quick task --%>
	<div id="quick-add-task-container" class="top-grey-box">
		<div id="hide-quick-add-task" class="right pointer">X&nbsp;&nbsp;</div>
		<input id="quick-add-task" class="clearField" type="text" value="What do you need to do? Press 'Enter' to validate." />
	</div>
	<br />
	Right side... Should have a lot of crazy stuff later...
</div>
<%@include file="/WEB-INF/views/logged/footer.jsp"%>