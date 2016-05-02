/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.milestone3.servlet;

import amm.milestone3.classi.ObjectSaleFactory;
import amm.milestone3.classi.OggettoVendita;
import amm.milestone3.classi.SaldoConto;
import amm.milestone3.classi.UserFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marco
 */
@WebServlet(name = "Client", urlPatterns = {"/cliente.html"})
public class Client extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        
        // qui serve sempre 
        ArrayList<OggettoVendita> listaOggetti = ObjectSaleFactory.getInstance().getListaOggetti();
        request.setAttribute("listaOggetti", listaOggetti);
        
        
        if (session.getAttribute("loggedIn") == null) {
            // qui è un caso un po particolare, ad esempio accedo alla pagina con una url senza passare per login, non creo la sessione
            request.setAttribute("isClient", false);
            request.getRequestDispatcher("show_cliente.jsp").forward(request, response);
        } else {
            session = request.getSession();

            // se proviene da login senza effetuare il login session è vuota e ritorna null exception 
            if ((String) session.getAttribute("tipoUtente") == null) {
                request.setAttribute("isClient", false);
                request.getRequestDispatcher("show_cliente.jsp").forward(request, response);
            } else {
                // controlliamo chi sta provando ad accedere
                switch ((String) session.getAttribute("tipoUtente")) {
                    case ("cliente"): {

                        request.setAttribute("isClient", true);

                        if (request.getParameter("oggetto_scelto") != null) {

                            // recupero l'oggetto richiesto con il link, non viene salvato in sessione!
                            OggettoVendita oggettoScelto = ObjectSaleFactory.getInstance().getOggetto(Integer.parseInt(request.getParameter("oggetto_scelto")));

                            request.setAttribute("oggetto", oggettoScelto);
                            request.setAttribute("riepilogo", true);

                            // mi serve ripassarli il valore dell id dell'oggetto scelto per recuperarlo dopo
                            request.setAttribute("oggetto_confermato", request.getParameter("oggetto_scelto"));
                            request.getRequestDispatcher("show_cliente.jsp").forward(request, response);

                        } else if (request.getParameter("submit_conferma") != null) {

                            // recupero il conto del cliente
                            Double contoCliente = UserFactory.getInstance().getCliente((int) session.getAttribute("id")).getDisponibilita().getConto();

                            int id_oggetto = Integer.parseInt(request.getParameter("conferma_oggetto"));
                            OggettoVendita oggettoScelto = ObjectSaleFactory.getInstance().getOggetto(id_oggetto);
                            

                            // recupero l'oggetto scelto e verifico se i soldi sono sufficienti con un metodo della classe oggeto
                            settaMessaggio(request, contoCliente, id_oggetto);
                            request.setAttribute("oggetto", oggettoScelto);
                            request.setAttribute("acquistato", true);
                            request.getRequestDispatcher("show_cliente.jsp").forward(request, response);

                        } else {

                            request.getRequestDispatcher("show_cliente.jsp").forward(request, response);
                        }

                        break;
                    }
                    case ("venditore"): {

                        request.setAttribute("isClient", false);
                        request.getRequestDispatcher("show_cliente.jsp").forward(request, response);

                        break;
                    }
                }
            }
        }
    }
        
    public void settaMessaggio(HttpServletRequest request, Double contoCliente, int id_oggetto) {
        
        
        
        
        if (ObjectSaleFactory.getInstance().getOggetto(id_oggetto).soldiSufficienti(contoCliente)) {
            request.setAttribute("messaggio_acquisto", "L'oggetto è stato acquistato correttamente!");

        } else {
            request.setAttribute("messaggio_acquisto", "I fondi non sono sufficienti per effetuare l'acquisto!");
        }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
