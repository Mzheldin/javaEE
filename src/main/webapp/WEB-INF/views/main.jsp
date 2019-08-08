<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<jsp:include page="general/header.jsp">
    <jsp:param name="title" value="${requestScope.title}"/>
</jsp:include>
<body>
    <jsp:include page="general/menu.jsp"/>

    <jsp:include page="general/footer.jsp"/>
</body>
</html>
