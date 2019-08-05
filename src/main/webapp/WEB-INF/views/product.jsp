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
        <c:url value="/product" var="productPostUrl"/>
        <form action="${productPostUrl}">
            <input type="hidden" name="id" value="${requestScope.product.id}">
            <div class="form-group">
                <label for="exampleInputProductname">Product name</label>
                <input type="text" class="form-control" id="exampleInputProductname" placeholder="Enter productname"
                       name="name" value="${product.name}">
            </div>
            <div class="form-group">
                <label for="exampleInputDescription">Password</label>
                <input type="text" class="form-control" id="exampleInputDescription" placeholder="Description"
                       name="description" value="${product.description}">
            </div>
            <button type="submit" formmethod="post" class="btn btn-primary">Submit</button>
        </form>
    </div>
    <jsp:include page="general/footer.jsp"/>
</body>
</html>
