<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<jsp:include page="general/header.jsp">
    <jsp:param name="title" value="${requestScope.title}"/>
</jsp:include>
<body>
    <jsp:include page="general/menu.jsp"/>
    <div class="container">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Description</th>
            </tr>
            </thead>
            <c:forEach var="product" items="${requestScope.products}">
                <c:url value="/product" var="productUrl">
                    <c:param name="id" value="${product.id}"/>
                </c:url>
                <tr>
                    <td>
                        <a href="${productUrl}"><c:out value="${product.id}"/></a>
                    </td>
                    <td>
                        <a href="${productUrl}"><c:out value="${product.name}"/></a>
                    </td>
                    <td>
                        <a href="${productUrl}"><c:out value="${product.description}"/></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <jsp:include page="general/footer.jsp"/>
</body>
</html>
