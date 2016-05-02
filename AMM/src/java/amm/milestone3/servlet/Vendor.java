
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.milestone3.servlet;

import amm.milestone3.classi.ObjectSaleFactory;
import amm.milestone3.classi.OggettoVendita;
import amm.milestone3.classi.OggettoVendita.ValueException;
import amm.milestone3.classi.UserFactory;
import amm.milestone3.classi.Utente;
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
@WebServlet(name = "Vendor", urlPatterns = {"/venditore.html"})
public class Vendor extends HttpServlet {

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

        request.setAttribute("isVendor", true);
        
        
        if (session.getAttribute("loggedIn") == null) {
            // qui è un caso un po particolare, ad esempio accedo alla pagina con una url senza passare per login, non creo la sessione
            request.setAttribute("isVendor", false);
            request.getRequestDispatcher("form_venditore.jsp").forward(request, response);
        } else {
            session = request.getSession();

            // se proviene da login senza effetuare il login session è vuota e ritorna null exception 
            if ((String) session.getAttribute("tipoUtente") == null) {
                request.setAttribute("isVendor", false);
                request.getRequestDispatcher("form_venditore.jsp").forward(request, response);
            } else {
                // controlliamo chi sta provando ad accedere
                switch ((String) session.getAttribute("tipoUtente")) {
                    case ("cliente"): {

                        request.setAttribute("isVendor", false);
                        request.getRequestDispatcher("form_venditore.jsp").forward(request, response);
                        break;
                    }
                    case ("venditore"): {

                        // gestione del form
                        if (request.getParameter("submit_oggetto") != null) {

                            // servirebbe controllo per campi vuoti almeno
                            OggettoVendita oggettoInserito = new OggettoVendita();
                            oggettoInserito.setId(ObjectSaleFactory.getInstance().nuovoId());
                            oggettoInserito.setNome(request.getParameter("nomeOggetto"));
                            oggettoInserito.setUrl(request.getParameter("urlImmagine"));
                            oggettoInserito.setDescrizione(request.getParameter("descrizioneOggetto"));
                            

                            
                            ArrayList<String> error = new ArrayList<>();
                            
                                    
                                    
                            
                            try {
                                oggettoInserito.setPrezzo(Double.parseDouble(request.getParameter("prezzoOggetto")));
                                error.add("");

                            } catch (Exception e) {

                                if (e instanceof IllegalArgumentException) {
                                    //ci aspettiamo che nn ci siano errori, la stringa viene converita a double 
                                } else if (e instanceof OggettoVendita.ValueException) {
                                    // gestiamo l'errore per i valori negativi
                                    error.add(((ValueException) e).getInfo());
                                }
                            }

                            try {
                                oggettoInserito.setQuantita(Integer.parseInt(request.getParameter("numeroPezzi")));
                                error.add("");
                                
                            } catch (Exception e) {
                                if (e instanceof IllegalArgumentException) {
                                    // ci aspettiamo che nn ci siano errori, la stringa viene convertita a int
                                } else if (e instanceof OggettoVendita.ValueException) {
                                    // gestiamo l'erroe per valori negativi

                                   error.add(((ValueException) e).getInfo());
                                }
                            }
                            
                            if (!error.isEmpty()) {
                                request.setAttribute("error", true);
                                request.setAttribute("errore", error);
                                request.getRequestDispatcher("form_venditore.jsp").forward(request, response);
                            } else {
                                // passiamo alla jsp l'oggetto 
                                request.setAttribute("oggettoInserito", oggettoInserito);
                                request.setAttribute("riepilogo", true);
                                request.getRequestDispatcher("form_venditore.jsp").forward(request, response);
                            }
                                


                        
                        } else {

                            request.setAttribute("isVendor", true);
                            request.getRequestDispatcher("form_venditore.jsp").forward(request, response);

                        }

                        break;
                    }
                }
            }
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
