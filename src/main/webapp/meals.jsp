<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<pre class="prettyprint">
<html>
<head>
    <title>Meals</title>
    <style>
table, th, td {
    border: 1px solid #39cdfb;
}

.normal {
    color: green;
}

.exceeded {
    color: red;
}
</style>
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <th>Description</th>
        <th>Calories</th>
        <th>Date & Time</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories()}</td>
                <td>
                        <%=TimeUtil.toString(meal.getDateTime())%>
                </td>
            </tr>
    </c:forEach>
</table>
</body>
</html>