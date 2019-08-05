<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul>
<li><c:url value="/main" var="mainUrl"/><a href="${mainUrl}">Главная</a></li>
<li><c:url value="/catalog" var="catalogUrl"/><a href="${catalogUrl}">Каталог</a></li>
<li><c:url value="/cart" var="cartUrl"/><a href="${cartUrl}">Корзина</a></li>
<%--<li><c:url value="/product" var="productUrl"/><a href="${productUrl}">Товары</a></li>--%>
<li><c:url value="/order" var="orderUrl"/><a href="${orderUrl}">Заказ</a></li>
<li><c:url value="/about" var="aboutUrl"/><a href="${aboutUrl}">О компании</a></li>
</ul>
