<%-- 
    Document   : filter
    Created on : 11-giu-2016, 9.39.50
    Author     : Marco
--%>

<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




    <json:array>
    <c:forEach var="obj" items="${oggetti}">
        <json:object>
            <json:property name="id" value="${obj.getId()}"/>
            <json:property name="nome" value="${obj.getNome()}"/>
            <json:property name="img" value="${obj.getUrl()}"/>
            <json:property name="desc" value="${obj.getDescrizione()}"/>
            <json:property name="quantita" value="${obj.getQuantita()}"/>
            <json:property name="prezzo" value="${obj.getPrezzo()}"/>
        </json:object>
    </c:forEach>
</json:array>



 