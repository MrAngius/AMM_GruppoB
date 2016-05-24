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
     * @throws java.sql.SQLException
     * @throws amm.milestone3.classi.UserFactory.SQLnoResultException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        // qui serve sempre 
        ArrayList<OggettoVendita> listaOggetti = ObjectSaleFactory.getInstance().getListaOggetti();
        request.setAttribute("listaOggetti", listaOggetti);

        if (session.getAttribute("loggedIn") == null) {
            // qui è un caso un po particolare, ad esempio accedo alla pagina con una url senza passare per login, non creo la sessione
            request.setAttribute("isClient", false);

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
                        request.setAttribute("utente", UserFactory.getInstance().getCliente((int) session.getAttribute("id")));

                        // GESTIONE RICARICA
                        ricaricaCredito(request, response, session);

                        // GESTIONE ACQUISTO OGGETTO
                        acquistaOggetto(request, response, session);

                        // GESTIONE VISUALIZZA OGGETTI
                        visualizzaOggettiAcquisto(request, response, session);

                        request.getRequestDispatcher("show_cliente.jsp").forward(request, response);
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

    private void visualizzaOggettiAcquisto(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, UserFactory.SQLnoResultException {

        if (request.getParameter("visualizza_acquisti") != null) {

            request.setAttribute("riepilogo_acquisto", true);
            request.setAttribute("utente", UserFactory.getInstance().getCliente((int) session.getAttribute("id")));

            ArrayList<OggettoVendita> oggettiAcquistati = ObjectSaleFactory.getInstance().getOggettiAcquistati((int) session.getAttribute("id"));
            request.setAttribute("oggetti_acquistati", oggettiAcquistati);

        }

    }

    public void ricaricaCredito(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {

        if (request.getParameter("ricarica") != null) {
            request.setAttribute("ricarica", true);

        } else if (request.getParameter("procedi") != null) {

            // integriamo un minimo di controllo
            if (!"".equals(request.getParameter("imp_ric"))) {
                Double importo = Double.parseDouble(request.getParameter("imp_ric"));
                if (importo >= 0) {
                    UserFactory.getInstance().modificaContoCliente((int) session.getAttribute("id"), importo, 1);
                }
            }
            // aggiorniamo il credito
            request.setAttribute("utente", UserFactory.getInstance().getCliente((int) session.getAttribute("id")));
        }

    }

    public void acquistaOggetto(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {

        if (request.getParameter("oggetto_scelto") != null) {

            // recupero l'oggetto richiesto con il link, non viene salvato in sessione!
            OggettoVendita oggettoScelto = ObjectSaleFactory.getInstance().getOggetto(Integer.parseInt(request.getParameter("oggetto_scelto")));

            request.setAttribute("oggetto", oggettoScelto);
            request.setAttribute("riepilogo", true);

            // mi serve ripassarli il valore dell id dell'oggetto scelto per recuperarlo dopo
            request.setAttribute("oggetto_confermato", request.getParameter("oggetto_scelto"));

        } else if (request.getParameter("submit_conferma") != null) {

            // recupero il conto del cliente
            Cliente cliente = UserFactory.getInstance().getCliente((int) session.getAttribute("id"));

            // recupero l'oggetto
            int id_oggetto = Integer.parseInt(request.getParameter("conferma_oggetto"));
            OggettoVendita oggettoScelto = ObjectSaleFactory.getInstance().getOggetto(id_oggetto);

            // recupero l'oggetto scelto e verifico se i soldi sono sufficienti con un metodo della classe oggeto
            verificaDisponibilita(request, response, cliente, oggettoScelto);

            request.setAttribute("oggetto", oggettoScelto);
            request.setAttribute("acquistato", true);
        }
    }

    public void verificaDisponibilita(HttpServletRequest request, HttpServletResponse response, Cliente cliente, OggettoVendita oggetto)
            throws SQLException, UserFactory.SQLnoResultException {

        if (oggetto.soldiSufficienti(cliente.getDisponibilita().getConto())) {

            ObjectSaleFactory.getInstance().transazioneOggetto(cliente, oggetto);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserFactory.SQLnoResultException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserFactory.SQLnoResultException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
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

}
