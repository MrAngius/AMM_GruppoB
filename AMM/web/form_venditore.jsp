<%-- 
    Document   : venditore
    Created on : 24-apr-2016, 12.43.21
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
        <title>Nuovo Oggetto</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="keywords" content="nuovo oggetto, hardware, venditore">
        <meta name="author" content="Marco Angius">
        <meta name="description" content="Pagina inserzione prodotto">
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
                        <li><a href="login.html">Login</a></li>
                    </ul>
                </nav>
            </header>


            <c:if test="${isVendor != true || isClient == true}">
                <p>Accesso non autorizzato! Verificare il login e le autorizzazioni necessarie.</p>
            </c:if>
                
                

            <c:if test="${isVendor == true && riepilogo != true}">
                <!-- Sidebar 1-->
                <div id="sidebar1">

                    <h2>Nuovo Oggetto</h2>
                    <p>Inserisci un nuovo oggetto da mettere in vendita</p>

                </div>

                <!-- Content -->
                <div id="content">

                    <form class="form" method="POST" action="venditore.html">
                        <div>
                            <label for="nome_oggetto">Nome Oggetto</label>
                            <input type="text" name="nomeOggetto" id="nome_oggetto"/>
                        </div>
                        <div>
                            <label for="url_immagine">URL Immagine</label>
                            <input type="url" name="urlImmagine" id="url_immagine"/>
                        </div>
                        <div>
                            <label for="descrizione_oggetto">Descrizione</label>
                            <textarea name="descrizioneOggetto" id="descrizione_oggetto" rows="4" cols="20"></textarea>
                        </div>
                        <div>
                           

                            <label for="prezzo_oggetto">Prezzo Oggetto</label>
                            <input class="<c:if test="${error == true}">evidenzia</c:if>" type="text" name="prezzoOggetto" id="prezzo_oggetto"/>
                             <c:if test="${isVendor == true && error == true}">
                                <p class="error">${errore.get(0)}</p>
                            </c:if>
                        </div>
                        <div>
                            

                            <label for="quantita_oggetto">Pezzi Disponibili</label>
                            <input class="<c:if test="${error == true}">evidenzia</c:if>" type="text" name="numeroPezzi" id="quantita_oggetto"/>
                            
                            <c:if test="${isVendor == true && error == true}">
                                <p class="error">${errore.get(1)}</p>
                            </c:if>
                        </div>

                        <input type="submit" name="submit_oggetto" value="Registra Prodotto"/>
                    </form>

                </div>
            </c:if>

            <c:if test="${riepilogo == true}">
                <div>

                    <div class="clear"></div>

                    <form  id="riepilogo" method="GET" action="venditore.html">
                        <div class="image">
                            <img src="${oggettoInserito.getUrl()}" alt="${oggettoInserito.getDescrizione()}" width="60px" height="60px" /> 
                        </div>

                        <p class="bold">Nome oggetto:</p><p>${oggettoInserito.getNome()}</p>
                        <p class="bold">Descrizione:</p><p> ${oggettoInserito.getDescrizione()}</p>
                        <p class="bold">Prezzo: </p> <p>${oggettoInserito.getPrezzo()}</p>
                        <p class="bold">Quantità: </p> <p> ${oggettoInserito.getQuantita()}</p>

                        <div>
                            <input type="submit" name="submit_conferma" value="Conferma"/> 
                        </div>


                    </form>
                </div>
            </c:if>


            <!-- Clear -->
            <div id="clear"></div>

            <!-- Footer -->
            <footer id="footer">
                <jsp:include page="_footerContent.jsp"/>
            </footer>

        </div>
    </body>
</html>
