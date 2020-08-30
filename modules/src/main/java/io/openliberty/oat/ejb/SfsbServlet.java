
package io.openliberty.oat.ejb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/OpenLiberty/ejb/src/main/java"})
@WebServlet(name = "SfsbServlet", urlPatterns = {"/sfsbCache"})
public class SfsbServlet extends HttpServlet {

    @EJB
    ShoppingCart sb;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet sfsbCache</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sfsbCache at " + request.getContextPath() + "</h1>");
            try {
                String product = request.getParameter("product");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                int quantityBefore = 0;
                if (sb.getCartContents().get(product)!=null)
                    quantityBefore = sb.getCartContents().get(product);
                out.println("Before purchase : product - " + product + " quantity - " + quantityBefore);
                sb.buy(product, quantity);
                int quantityAfter = sb.getCartContents().get(product);
                out.println("After purchase : product - " + product + " quantity - " + quantityAfter);
            } catch (Exception ex) {
                Logger.getLogger(SfsbServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("</body>");
            out.println("</html>");
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

