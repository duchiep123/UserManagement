/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mrhie
 */
public class DispatchServlet extends HttpServlet {

    private final String LOGIN = "LoginServlet";
    private final String LOGIN_PAGE = "LoginPage.jsp";
    private final String SEARCH = "SearchServlet";
    private final String LOAD = "LoadUserServlet";
    private final String REMOVE = "RemoveUserServlet";
    private final String EDIT = "EditUserServlet";
    private final String UPDATE = "UpdateUserServlet";
    private final String GO_TO_INSERT = "AddUserPage.jsp";
    private final String ADD_TO_PROMOTION_LIST = "AddUserToPromotionList";
    private final String VIEW_PROMOTION_LIST = "PromotionListPage.jsp";
    private final String UPDATE_RANK = "UpdateRankFromPromotionList";
    private final String REMOVE_USER_PROMOTION = "RemoveUserFromPromotionList";
    private final String CONFIRM = "ConfirmPromotionListServlet";
    private final String CREATE_USER = "InsertUserServlet";
    private final String VIEW_HISTORY = "ShowHistoryPromotionList";
    private final String LOGOUT = "LogoutServlet";
    private final String BACK_TO_PROMOTION_LIST_PAGE = "PromotionListPage.jsp";

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
        request.setCharacterEncoding("utf-8");
        String url = LOGIN;
        try {

            String button = request.getParameter("button");
            String buttonConfirm = request.getParameter("buttonConfirm");
       
            if (button == null) {
                button="";
            }
            if (button.equalsIgnoreCase("Login")) {
                url = LOGIN;
            } else {
                HttpSession session = request.getSession(false);
                if (session.getAttribute("ADMIN") != null) {
                    if (buttonConfirm != null) {
                        
                        url = CONFIRM;
                    } else if (button.equalsIgnoreCase("Search")) {
                        url = SEARCH;
                    } else if (button.equalsIgnoreCase("Load")) {
                        url = LOAD;
                    } else if (button.equalsIgnoreCase("Remove")) {
                        url = REMOVE;
                    } else if (button.equalsIgnoreCase("Edit")) {
                        url = EDIT;
                    } else if (button.equalsIgnoreCase("Update") || button.equalsIgnoreCase("Change")) {
                        url = UPDATE;
                    } else if (button.equalsIgnoreCase("GoToInsertPage")) {
                        url = GO_TO_INSERT;
                    } else if (button.equalsIgnoreCase("Add To Promotion List")) {
                        url = ADD_TO_PROMOTION_LIST;
                    } else if (button.equalsIgnoreCase("ShowPromotionList")) {
                        url = VIEW_PROMOTION_LIST;
                    } else if (button.equalsIgnoreCase("Update Rank")) {
                        url = UPDATE_RANK;
                    } else if (button.equalsIgnoreCase("Remove Promotion")) {
                        url = REMOVE_USER_PROMOTION;
                    } else if (button.equalsIgnoreCase("Create")) {
                        url = CREATE_USER;
                    } else if (button.equalsIgnoreCase("ViewHistoryPromotionList")) {
                        url = VIEW_HISTORY;
                    } else if (button.equalsIgnoreCase("Logout")) {
                        url = LOGOUT;
                    } else if (button.equalsIgnoreCase("BackToPromotionListPage")) {
                        url = BACK_TO_PROMOTION_LIST_PAGE;
                    }
                } else {
                    url = LOGIN_PAGE;
                }
            }

        } catch (Exception e) {
            log("Error at DispatchServlet: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
