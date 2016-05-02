<%-- 
    Document   : login
    Created on : 24-apr-2016, 12.43.05
    Author     : Marco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="keywords" content="hardware, e-commerce, login, username, password">
        <meta name="author" content="Marco Angius">
        <meta name="description" content="pagina di login utente Thrift Hardware Shop">
        <!-- link al css-->
        <link href="css/style.css" type="text/css" rel="stylesheet" media="screen"/>
    </head>

    <body>

        <!-- Page -->
        <div id="page">

            <!-- Header -->
            <header id="header">
                <jsp:include page="_headerTitle.jsp"/>

                <nav>
                    <ul>
                        <li><a href="descrizione.html">Home</a></li>
                        <li><a href="cliente.html">Cliente</a></li>
                        <li><a href="venditore.html">Venditore</a></li>
                    </ul>
                </nav>


            </header>


            <!-- Sidebar 1-->
            <div id="sidebar1">

                <H2>Login</H2>
                <p>Effetua il login per accedere alla pagina personale</p>

            </div>

            <!-- Content -->
            <div id="content">

                <c:if test="${error == true}">
                    <p>L'username o la password risulatano errati! Riprovare.</p>
                </c:if>



                <form class="form" method="POST" action="login.html">
                    <div>
                        <label for="username">Username</label>
                        <input type="text" name="username" id="username"/>
                    </div>
                    <div>
                        <label for="password">Password</label>
                        <input type="password" name="password" id="password"/>
                    </div>
                    <div>
                        <input type="submit" name="Submit" value="Invia">
                    </div>
                </form>
            </div>

            <!-- Clear -->
            <div id="clear"></div>

            <!-- Footer -->
            <footer id="footer">
                <jsp:include page="_footerContent.jsp"/>
            </footer>

        </div>
    </body>
</html>
