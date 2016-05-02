/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.milestone3.servlet;

import amm.milestone3.classi.Cliente;
import amm.milestone3.classi.ObjectSaleFactory;
import amm.milestone3.classi.OggettoVendita;
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
@WebServlet(name = "Login", urlPatterns = {"/login.html"})
public class Login extends HttpServlet {

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

        // oggetto sessione
        HttpSession session = request.getSession(false);

        request.setAttribute("error", false); //tolgo il messaggio se ricarica la pagina o preme submit con campi vuoti
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Boolean trovato = false;

        // c'è un problema se si richiama la servlet da un altra pagina i valori sono null
        if (request.getParameter("Submit") != null &&  session.getAttribute("loggedIn") == null) {
            // e comuqnue mi serve un doppio controllo se ricarico 
            if ((!"".equals(username) && !"".equals(password))) {
                

                ArrayList<Utente> listaUtenti = UserFactory.getInstance().getListaUtenti();
                ArrayList<OggettoVendita> listaOggetti = ObjectSaleFactory.getInstance().getListaOggetti();

                for (Utente u : listaUtenti) {

                    // siamo sicuri che sia giusto ? 
                    if (u.getUsername().equals(username) && u.getPassword().equals(password)) // registraimolo nella sessione
                    {
                        session = request.getSession();
                        session.setAttribute("loggedIn", true);
                        session.setAttribute("id", u.getId());

                        if (u instanceof Cliente) {
                            trovato = true;
                            session.setAttribute("tipoUtente", "cliente");
                            caricaCliente(request, response, listaOggetti, u);
                        } else {
                            trovato = true;
                            session.setAttribute("tipoUtente", "venditore");
                            caricaVenditore(request, response, u);
                        }
                    }
                }

                // controllo se non viene trovato un utente 
                if (trovato == false) {
                    // se non trova valori corrispondenti
                    request.setAttribute("error", true); //tolgo il messaggio se ricarica la pagina o preme submit con campi vuoti
                    request.setAttribute("submit", request.getParameter("submit"));
                    request.getRequestDispatcher("form_login.jsp").forward(request, response);
                }

            }

        } else if (session != null) {

            if (session.getAttribute("loggedIn") != null) {
                if ((Boolean) session.getAttribute("loggedIn")) {
                    switch ((String) session.getAttribute("tipoUtente")) {
                        case ("cliente"): {
                            Utente u = UserFactory.getInstance().getCliente((int) session.getAttribute("id"));
                            ArrayList<OggettoVendita> listaOggetti = ObjectSaleFactory.getInstance().getListaOggetti();
                            caricaCliente(request, response, listaOggetti, u);
                            break;
                        }
                        case ("venditore"): {
                            Utente u = UserFactory.getInstance().getVenditore((int) session.getAttribute("id"));
                            caricaVenditore(request, response, u);
                            break;
                        }
                    }
                }
            }
        }

        request.getRequestDispatcher("form_login.jsp").forward(request, response);

    }

    // metodi di suporto 
    public void caricaCliente(HttpServletRequest request, HttpServletResponse response, ArrayList<OggettoVendita> listaOggetti, Utente u)
            throws ServletException, IOException {
        // impostiamo la request e passiamo gli attributi
        request.setAttribute("listaOggetti", listaOggetti);
        
        request.setAttribute("isClient", true); // ci serve per compilare adeguatamente la jsp

        // facciamo il dispatcher e inviamo alla jsp
        request.getRequestDispatcher("show_cliente.jsp").forward(request, response);
    }

    public void caricaVenditore(HttpServletRequest request, HttpServletResponse response, Utente u)
            throws ServletException, IOException {
        // impostiamo la request e passiamo gli attributi
        request.setAttribute("venditore", u); // ci serve per il conto
        request.setAttribute("isVendor", true); // ci serve per compilare adeguatamente la jsp
        
        // facciamo il dispatcher e inviamo alla jsp
        request.getRequestDispatcher("form_venditore.jsp").forward(request, response);
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
