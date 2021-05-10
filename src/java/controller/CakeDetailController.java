/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CakeDAO;
import dao.ShareDAO;
import entity.Cake;
import entity.Share;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author kynhanht
 */
public class CakeDetailController extends HttpServlet {

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
        try {
            String sId = request.getParameter("id");
            String regex = "\\d{1,}";
            List<Share> share = new ShareDAO().selectShare();
            request.setAttribute("share", share);
            if (sId == null) {
                request.setAttribute("notFound", "Not found cake");
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            } else {
                if (!sId.matches(regex)) {
                    request.setAttribute("notFound", "Not found cake");
                    request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                } else {
                    int id = Integer.parseInt(sId);
                    Cake cake = new CakeDAO().selectById(id);
                    if (cake == null) {
                        request.setAttribute("notFound", "Not found cake");
                        request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                    }
                    request.setAttribute("cake", cake);
                    request.getRequestDispatcher("/CakeDetail.jsp").forward(request, response);

                }
            }
        } catch (Exception ex) {
            request.setAttribute("error", "Error page!");
            RequestDispatcher rd = request.getRequestDispatcher("/ErrorPage.jsp");
            rd.forward(request, response);
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
