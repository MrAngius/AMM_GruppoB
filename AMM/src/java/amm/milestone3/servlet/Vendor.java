
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
     * @throws java.sql.SQLException
     * @throws amm.milestone3.classi.UserFactory.SQLnoResultException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {
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

                        request.setAttribute("isVendor", true);
                        request.setAttribute("utente", UserFactory.getInstance().getVenditore((int) session.getAttribute("id")));

                        // GESTIONE ELIMINA OGGETTO
                        eliminaOggetto(request, response, session);

                        // GESTIONE MODIFICA OGGETTO
                        modificaOggetto(request, response, session);

                        // GESTIONE INSERIMENTO OGGETTO
                        inserisciOggetto(request, response, session);

                        request.getRequestDispatcher("form_venditore.jsp").forward(request, response);
                        break;
                    }
                }
            }
        }
    }

    
    // metodi supporto
      public void eliminaOggetto(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {

        if (request.getParameter("elimina_obj") != null) {
            if ("true".equals(request.getParameter("elimina_obj"))) {
                request.setAttribute("elimina_obj", true);
                request.setAttribute("oggetti_venditore", ObjectSaleFactory.getInstance().oggettiVenditore((int) session.getAttribute("id")));

            }
        } else if (request.getParameter("oggetto_eliminato") != null) {

            // settiamo un paremetro per usare lo stesso codice di riepilogo nella jsp
            request.setAttribute("azione", "eliminato");

            request.setAttribute("elim_mod", true);
            request.setAttribute("oggetto", ObjectSaleFactory.getInstance().getOggetto(Integer.parseInt(request.getParameter("oggetto_eliminato"))));
            
            // eliminazione lato DB
            ObjectSaleFactory.getInstance().eliminaOggettoVendita(Integer.parseInt(request.getParameter("oggetto_eliminato")));

        }
    }
      
    public void modificaOggetto(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {

        if (request.getParameter("oggetto_modificato") != null) {

            request.setAttribute("modifica", true);
            request.setAttribute("oggetto_mod", ObjectSaleFactory.getInstance().getOggetto(Integer.parseInt(request.getParameter("oggetto_modificato")))); 
            
        } else if (request.getParameter("mod_oggetto") != null) {

            // creiamo l'oggetto e poi lo passiamo ad una funzione che MODIFICA IL DB
            OggettoVendita oggettoInserito = new OggettoVendita();
            oggettoInserito.setId(Integer.parseInt(request.getParameter("id_obj")));
            oggettoInserito.setNome(request.getParameter("nomeOggetto"));
            oggettoInserito.setUrl(request.getParameter("urlImmagine"));
            oggettoInserito.setDescrizione(request.getParameter("descrizioneOggetto"));

            // in questo modo effettuo il controllo 
            ArrayList<String> error = new ArrayList<>();

            try {
                oggettoInserito.setPrezzo(Double.parseDouble(request.getParameter("prezzoOggetto")));
                error.add("");

            } catch (Exception e) {

                if (e instanceof IllegalArgumentException) {
                    //ci aspettiamo che nn ci siano errori, la stringa viene converita a double 
                    error.add("Valore non consentito");
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
                    error.add("Valore non consentito");
                } else if (e instanceof OggettoVendita.ValueException) {
                    // gestiamo l'erroe per valori negativi

                    error.add(((ValueException) e).getInfo());
                }
            }

            if (!"".equals(error.get(0)) || !"".equals(error.get(1))) {

                if ("".equals(error.get(0))) {
                    request.setAttribute("error_quant", true);
                }
                if ("".equals(error.get(1))) {
                    request.setAttribute("error_prez", true);
                }
                if (!"".equals(error.get(0)) && !"".equals(error.get(1))) {
                    request.setAttribute("error_prez", true);
                    request.setAttribute("error_quant", true);
                }

                request.setAttribute("modifica", true);
                request.setAttribute("oggetto_mod", oggettoInserito);
                request.setAttribute("errore", error);
            
            } else {
                // se non ci sono errori possimo rendere definitiva la modifica
                request.setAttribute("oggettoInserito", oggettoInserito);
                request.setAttribute("riepilogo", true);
                
                // rendiamo persistente la modifica lato DB
                ObjectSaleFactory.getInstance().modificaOggettoVendita(oggettoInserito);

            }
        }
    }

    public void inserisciOggetto(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException, SQLException, UserFactory.SQLnoResultException {

        if (request.getParameter("submit_oggetto") != null) {

            // servirebbe controllo per campi vuoti almeno
            OggettoVendita oggettoInserito = new OggettoVendita();
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
                    error.add("Valore non consentito");
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
                    error.add("Valore non consentito");
                } else if (e instanceof OggettoVendita.ValueException) {
                    // gestiamo l'erroe per valori negativi
                    error.add(((ValueException) e).getInfo());
                }
            }

            if (!"".equals(error.get(0)) || !"".equals(error.get(1))) {

                //controllo il campo
                if ("".equals(error.get(0))) {
                    request.setAttribute("error_quant", true);
                }
                if ("".equals(error.get(1))) {
                    request.setAttribute("error_prez", true);
                }
                if (!"".equals(error.get(0)) && !"".equals(error.get(1))) {
                    request.setAttribute("error_prez", true);
                    request.setAttribute("error_quant", true);
                }

                // ripasso l'oggetto per nn far rinserire i campi all'utente
                request.setAttribute("oggetto_form", oggettoInserito);
                request.setAttribute("errore", error);
               
            } else {
                // se non ci sono errori possimo rendere definitiva la modifica
                request.setAttribute("oggettoInserito", oggettoInserito);
                request.setAttribute("riepilogo", true);

                // rendiamo persistente la modifica lato DB
                ObjectSaleFactory.getInstance().inserisciOggettoVendita(oggettoInserito, (int) session.getAttribute("id"));
   
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Vendor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserFactory.SQLnoResultException ex) {
            Logger.getLogger(Vendor.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Vendor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserFactory.SQLnoResultException ex) {
            Logger.getLogger(Vendor.class.getName()).log(Level.SEVERE, null, ex);
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
