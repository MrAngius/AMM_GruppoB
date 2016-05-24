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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "Login", urlPatterns = {"/login.html"}, loadOnStartup = 0)
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
    
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";   
    private static final String DB_CLEAN_PATH = "../../web/WEB-INF/db/ammdb";
    private static final String DB_BUILD_PATH = "WEB-INF/db/ammdb";
    
       protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {
        response.setContentType("text/html;charset=UTF-8");

        // oggetto sessione
        HttpSession session = request.getSession(false);

        // request.setAttribute("error", false); //tolgo il messaggio se ricarica la pagina o preme submit con campi vuoti
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // controllo se è stato inviato e se non c'è una sessione in corso 
        if (request.getParameter("Submit") != null && session.getAttribute("loggedIn") == null) {

            // controllo sui campi
            if ((!"".equals(username) && !"".equals(password))) {

                Utente u = UserFactory.getInstance().getUtente(username, password);

                if (u != null) {
                    session = request.getSession();
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("id", u.getId());

                    if (u instanceof Cliente) {
                        session.setAttribute("tipoUtente", "cliente");
                        caricaCliente(request, response, session, u);
                    } else {
                        session.setAttribute("tipoUtente", "venditore");
                        caricaVenditore(request, response, session, u);
                    }

                } else {
                    // se non trova valori corrispondenti
                    erroreLogin(request, response);
                }

            } else {
                // se non trova valori corrispondenti
                erroreLogin(request, response);
            }

            // gestiamo se è presente un utente in sessione
        } else if (session != null) {

            if (session.getAttribute("loggedIn") != null) {
                if ((Boolean) session.getAttribute("loggedIn")) {
                    switch ((String) session.getAttribute("tipoUtente")) {
                        case ("cliente"): {
                            Utente u = UserFactory.getInstance().getCliente((int) session.getAttribute("id"));
                            caricaCliente(request, response, session, u);
                            break;
                        }
                        case ("venditore"): {
                            Utente u = UserFactory.getInstance().getVenditore((int) session.getAttribute("id"));
                            caricaVenditore(request, response, session, u);
                            break;
                        }
                    }
                }
            } else {

                request.getRequestDispatcher("form_login.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("form_login.jsp").forward(request, response);
        }
    }

    // metodi di suporto
    public void caricaCliente(HttpServletRequest request, HttpServletResponse response, HttpSession session, Utente u)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {
        // impostiamo la request e passiamo gli attributi

        request.setAttribute("listaOggetti", ObjectSaleFactory.getInstance().getListaOggetti());
        request.setAttribute("utente", u);
        request.setAttribute("isClient", true); // ci serve per compilare adeguatamente la jsp

        // facciamo il dispatcher e inviamo alla jsp
        request.getRequestDispatcher("show_cliente.jsp").forward(request, response);
    }

    public void caricaVenditore(HttpServletRequest request, HttpServletResponse response, HttpSession session, Utente u)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {
        // impostiamo la request e passiamo gli attributi
        
        request.setAttribute("utente", u);
        request.setAttribute("isVendor", true); // ci serve per compilare adeguatamente la jsp
        
        // facciamo il dispatcher e inviamo alla jsp
        request.getRequestDispatcher("form_venditore.jsp").forward(request, response);
    }
    
    private void erroreLogin(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.setAttribute("error", true); //tolgo il messaggio se ricarica la pagina o preme submit con campi vuoti
        request.setAttribute("submit", request.getParameter("submit"));
        request.getRequestDispatcher("form_login.jsp").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserFactory.SQLnoResultException ex) {
            System.out.println(ex.getInfo());
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (UserFactory.SQLnoResultException ex) {
            System.out.println(ex.getInfo());
        }
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
    
    // METODO GESTIONE DB CARICAMENTO DRIVER DERBY
    @Override
    public void init() {
        String dbConnection = "jdbc:derby:" + this.getServletContext().getRealPath("/") + DB_BUILD_PATH;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {

            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        // agiungere tutte le factory 
        
        ObjectSaleFactory.getInstance().setConnectionString(dbConnection);
        UserFactory.getInstance().setConnectionString(dbConnection);
    }
}
