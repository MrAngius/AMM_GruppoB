<%-- 
    Document   : cliente
    Created on : 24-apr-2016, 12.42.33
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
        <title>Cliente</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="keywords" content="hardware,ram, dissipatore, corsair, liquido, ddr4, case, titanium">
        <meta name="author" content="Marco Angius">
        <meta name="description" content="pagina acquisto prodotti in vendita">
        <link href="css/style.css" type="text/css" rel="stylesheet" media="screen"/>
        <script type="text/javascript" src="js/jquery-2.2.4.js"></script>
        <script type="text/javascript" src="js/filter.js"></script>
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


            <c:if test="${isClient != true || isVendor == true}">
                <p>Accesso non autorizzato! Verificare il login e le autorizzazioni necessarie.</p>
            </c:if>


            <c:if test="${isClient == true && riepilogo != true && acquistato != true}">
                <!-- Sidebar 1-->
                <div id="sidebar1">
                    <h2>Cliente</h2>
                    <p>Prodotti disponibili</p>

                    <div id="welcome">
                        <p>Benvenuto ${utente.getName()}</p>                      
                        <p>Credito: ${utente.getDisponibilita().getConto()}€</p>
                    </div>

                    <div>
                        <a href="cliente.html?ricarica=true">Ricarica</a>
                    </div>
                    <div>
                        <a href="cliente.html?visualizza_acquisti=true">Storico Acquisti</a>
                    </div>
                </div>

                <!-- Content -->
                <div id="content">

                    <!-- RICARICA -->

                    <c:if test="${ricarica == true}">

                        <div>
                            <form class="form"  method='GET' acttion='cliente.html'>
                                <p>Scegliere l'importo da ricaricare</p>
                                <label for='ricarica'>Importo:</label>
                                <input type='text' name='imp_ric' alt="0.0" id='ricarica'>
                                <input type="submit" name="procedi" value="Continua"/> 
                            </form>
                        </div>

                    </c:if>   


                    <!-- LISTA OGGETTI VENDITA -->

                    <c:if test="${ricarica != true && riepilogo_acquisto != true}">
                        
                        <!-- Sezione ricerca-->
                        <div id = "ricerca">
                            <label for="filter">Ricerca</label>
                            <input  type="text" id="filter"/> 
                        </div>
                        
                        
                        
                        
                        
                        <table id="client_table">

                            <tr>
                                <th></th>
                                <th>Prodotto</th>
                                <th>Quantità</th>
                                <th>Prezzzo</th>
                                <th></th>
                            </tr>

                            <c:forEach var="oggetto" items="${listaOggetti}">

                                <tr>

                                    <td class="imag_row"><img src="${oggetto.getUrl()}" alt="${oggetto.getDescrizione()}" width="20px" height="20px"/></td>
                                    <td>${oggetto.getNome()}</td>
                                    <td class="column_text_centered">${oggetto.getQuantita()}</td>
                                    <td class="column_text_centered">${oggetto.getPrezzo()}</td>
                                    <td><a href="cliente.html?oggetto_scelto=${oggetto.getId()}">Aggiungi Carrello</a></td> 

                                </tr>
                            </c:forEach>

                        </table>

                    </c:if>


                    <!-- LISTA OGGETTI ACQUISTATI -->

                    <c:if test="${ricarica != true && riepilogo_acquisto == true}">



                        <div>
                            <table id="client_table">
                                <c:if test="${!oggetti_acquistati.isEmpty()}">
                                    <tr>
                                        <th></th>
                                        <th>Prodotto</th>
                                        <th>Quantità</th>
                                        <th>Prezzzo</th>

                                    </tr>
                                </c:if>

                                <c:if test="${oggetti_acquistati.isEmpty()}">
                                    <div class="messaggio">Non ci sono oggetti acquistati!
                                    </div>
                                </c:if>    


                                <c:forEach var="oggetto" items="${oggetti_acquistati}">

                                    <tr>

                                        <td class="imag_row"><img src="${oggetto.getUrl()}" alt="${oggetto.getDescrizione()}" width="20px" height="20px"/></td>
                                        <td>${oggetto.getNome()}</td>
                                        <td class="column_text_centered">${oggetto.getQuantita()}</td>
                                        <td class="column_text_centered">${oggetto.getPrezzo()}</td>

                                    </tr>
                                </c:forEach>


                            </table>  
                            <div id="link_continua">
                                <a href="cliente.html">Continua</a> 
                            </div>

                        </div>

                    </c:if>
                </div>

            </c:if>   


            <!-- RIEPILOGO -->

            <c:if test="${riepilogo == true && acquistato != true}">
                <div>

                    <div class="clear"></div>

                    <form  id="riepilogo" method="POST" action="cliente.html?conferma_oggetto=${oggetto_confermato}">
                        <div class="image">
                            <img src="${oggetto.getUrl()}" alt="${oggetto.getDescrizione()}" width="60px" height="60px"/> 
                        </div>

                        <p class="bold">Nome oggetto:</p><p>${oggetto.getNome()}</p>
                        <p class="bold">Descrizione:</p><p> ${oggetto.getDescrizione()}</p>
                        <p class="bold">Prezzo: </p> <p>${oggetto.getPrezzo()}</p>
                        <p class="bold">Quantità: </p> <p> 1 </p>


                        <input type="submit" name="submit_conferma" value="Conferma"/> 
                        <div class="rifiuta">
                            <a  href="cliente.html">Rifiuta</a>
                        </div>

                    </form>
                </div>
            </c:if>


            <!-- ACQUISTO -->

            <c:if test="${acquistato == true}">
                <div class="clear"></div>

                <form id="riepilogo" action="cliente.html" method="GET">
                    <p>${messaggio_acquisto}</p>
                    <p class="bold">Nome oggetto:</p><p>${oggetto.getNome()}</p>
                    <p class="bold">Prezzo: </p> <p>${oggetto.getPrezzo()}</p>
                    <p class="bold">Id oggetto: </p> <p>${oggetto.getId()}</p>
                    <div>
                        <input type="submit" name="continua" value="Contnua"/> 
                    </div>  
                </form>

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
