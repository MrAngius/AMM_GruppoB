/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.milestone3.servlet;

import amm.milestone3.classi.ObjectSaleFactory;
import amm.milestone3.classi.OggettoVendita;
import amm.milestone3.classi.UserFactory;
import amm.milestone3.classi.UserFactory.SQLnoResultException;
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
@WebServlet(name = "Filter", urlPatterns = {"/filter.json"})
public class FilterServ extends HttpServlet {

    // c'è stato un problema con i nomi delle classi nel package e mi vede Filter.java come duplicato e ho dovuto rinominarlo FilterServ
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
            throws ServletException, IOException, SQLException, SQLnoResultException {
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession();

        if ("cliente".equals((String) session.getAttribute("tipoUtente"))) {

            if (request.getParameter("q") != null) {
                if ("".equals(request.getParameter("q"))) {

                    recuperaTuttiValori(request, response);

                } else {

                    recuperaDaRicerca(request, response);
                }

                response.setContentType("application/json");
                response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
                response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

                // mando la richeista alla jsp per generare json
                request.getRequestDispatcher("filter.jsp").forward(request, response);

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
            Logger.getLogger(FilterServ.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLnoResultException ex) {
            Logger.getLogger(FilterServ.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FilterServ.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLnoResultException ex) {
            Logger.getLogger(FilterServ.class.getName()).log(Level.SEVERE, null, ex);
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

    private void recuperaDaRicerca(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, UserFactory.SQLnoResultException {

        ArrayList<OggettoVendita> lista = ObjectSaleFactory.getInstance().getListaOggettiBySearch(request.getParameter("q"));

        if (lista != null) {
            if (lista.isEmpty()) {
                request.setAttribute("noresult", true);
            } else {
                request.setAttribute("oggetti", lista);
            }
        }

    }

    private void recuperaTuttiValori(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, SQLnoResultException {

        request.setAttribute("oggetti", ObjectSaleFactory.getInstance().getListaOggetti());

    }

}