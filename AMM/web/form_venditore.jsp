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



            <c:if test="${isVendor == true && riepilogo != true && elimina_obj != true && eliminato != true && modifica != true}">
                <!-- Sidebar 1-->
                <div id="sidebar1">

                    <h2>Nuovo Oggetto</h2>
                    <p>Inserisci un nuovo oggetto da mettere in vendita</p>

                    <div id="welcome">
                        <p>Benvenuto ${utente.getName()}</p>                      
                        <p>Credito: ${utente.getDisponibilita().getConto()}€</p>
                    </div>


                    <div >
                        <a href="venditore.html?elimina_obj=true">Elimina/Modifica Oggetto</a>
                    </div>
                </div>

                <!-- Content -->
                <div id="content">

                    <!--MAIN-->

                    <form class="form" method="POST" action="venditore.html">
                        <div>
                            <label for="nome_oggetto">Nome Oggetto</label>
                            <input type="text" name="nomeOggetto" value = "${oggetto_form.getNome()}" id="nome_oggetto"/>
                        </div>
                        <div>
                            <label for="url_immagine">URL Immagine</label>
                            <input type="text" name="urlImmagine" value = "${oggetto_form.getUrl()}" id="url_immagine"/>
                        </div>
                        <div>
                            <label for="descrizione_oggetto">Descrizione</label>
                            <textarea name="descrizioneOggetto" id="descrizione_oggetto" rows="4" cols="20">${oggetto_form.getDescrizione()}</textarea>
                        </div>
                        <div>


                            <label for="prezzo_oggetto">Prezzo Oggetto</label>
                            <input class="<c:if test="${error_prez == true}">evidenzia</c:if>" type="text" name="prezzoOggetto" id="prezzo_oggetto"/>

                            <c:if test="${isVendor == true && error_prez == true}">
                                <p class="error">${errore.get(0)}</p>
                            </c:if>
                        </div>
                        <div>


                            <label for="quantita_oggetto">Pezzi Disponibili</label>
                            <input class="<c:if test="${error_quant == true}">evidenzia</c:if>" type="text" name="numeroPezzi"  id="quantita_oggetto"/>

                            <c:if test="${isVendor == true && error_quant == true}">
                                <p class="error">${errore.get(1)}</p>
                            </c:if>
                        </div>

                        <input type="submit" name="submit_oggetto" value="Registra Prodotto"/>
                    </form>

                </div>
            </c:if>

            <!--RIEPILOGO-->

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

            <!-- ELIMINAZIONE-->

            <c:if test="${elimina_obj == true}">

                <div class="clear"></div>

                <table id="client_table">

                    <tr>
                        <th></th>
                        <th>Prodotto</th>
                        <th>Quantità</th>
                        <th>Prezzzo</th>
                        <th></th>
                    </tr>

                    <c:forEach var="oggetto" items="${oggetti_venditore}">

                        <tr>

                            <td class="imag_row"><img src="${oggetto.getUrl()}" alt="${oggetto.getDescrizione()}" width="20px" height="20px"/></td>
                            <td>${oggetto.getNome()}</td>
                            <td class="column_text_centered">${oggetto.getQuantita()}</td>
                            <td class="column_text_centered">${oggetto.getPrezzo()}</td>
                            <td><a href="venditore.html?oggetto_eliminato=${oggetto.getId()}">Elimina</a>
                                <a href="venditore.html?oggetto_modificato=${oggetto.getId()}">Modifica</a>
                            </td>


                        </tr>
                    </c:forEach>


                </table>


            </c:if>


            <!--MODIFICA-->
            <c:if test="${modifica == true}">

                <form class="form" method="POST" action="venditore.html">
                    <div>
                        <label for="nome_oggetto">Nome Oggetto</label>
                        <input type="text" name="nomeOggetto" value="${oggetto_mod.getNome()}" id="nome_oggetto"/>
                    </div>
                    <div>
                        <label for="url_immagine">URL Immagine</label>
                        <input type="text" name="urlImmagine" value="${oggetto_mod.getUrl()}" id="url_immagine"/>
                    </div>
                    <div>
                        <label for="descrizione_oggetto">Descrizione</label>
                        <textarea name="descrizioneOggetto" id="descrizione_oggetto" rows="4" cols="20">${oggetto_mod.getDescrizione()}</textarea>
                    </div>
                    <div>


                        <label for="prezzo_oggetto">Prezzo Oggetto</label>
                        <input class="<c:if test="${error_prez == true}">evidenzia</c:if>" type="text" name="prezzoOggetto" id="prezzo_oggetto" value="${oggetto_mod.getPrezzo()}"/>
                        <c:if test="${isVendor == true && error_prez == true}">
                            <p class="error">${errore.get(0)}</p>
                        </c:if>
                    </div>
                    <div>


                        <label for="quantita_oggetto">Pezzi Disponibili</label>
                        <input class="<c:if test="${error_quant == true}">evidenzia</c:if>" type="text" name="numeroPezzi" id="quantita_oggetto"value="${oggetto_mod.getQuantita()}" />

                        <c:if test="${isVendor == true && error_quant == true}">
                            <p class="error">${errore.get(1)}</p>
                        </c:if>
                    </div>

                    <input type="submit" name="mod_oggetto" value="Modifica"/>
                    <input type="hidden" name="id_obj" value="${oggetto_mod.getId()}"/>
                </form>

            </c:if>



            <!-- CONFERMA MOD O ELIM -->
            <c:if test="${elim_mod == true}">
                <div class="clear"></div>

                <form id="riepilogo" action="venditore.html" method="GET">
                    <p>L'oggetto: ${oggetto.getNome()} id:${oggetto.getId()} è stato ${azione}! </p>

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
