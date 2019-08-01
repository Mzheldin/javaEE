package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MenuServlet", urlPatterns = "/menu")
public class MenuServlet extends HttpServlet {
    private final String SERVERPATH = "http://localhost:8080/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<ul>");
        resp.getWriter().println("<li><a href=\"" + SERVERPATH + req.getContextPath() + Servlets.MAIN.getPath() + "\">Главная</a></li>");
        resp.getWriter().println("<li><a href=\"" + SERVERPATH + req.getContextPath() + Servlets.CATALOG.getPath() + "\">Каталог</a></li>");
        resp.getWriter().println("<li><a href=\"" + SERVERPATH + req.getContextPath() + Servlets.CART.getPath() + "\">Корзина</a></li>");
        resp.getWriter().println("<li><a href=\"" + SERVERPATH + req.getContextPath() + Servlets.PRODUCT.getPath() + "\">Товары</a></li>");
        resp.getWriter().println("<li><a href=\"" + SERVERPATH + req.getContextPath() + Servlets.ORDER.getPath() + "\">Заказ</a></li>");
        resp.getWriter().println("</ul>");
    }

    private enum Servlets {

        MAIN("/main"),
        CATALOG("/catalog"),
        CART("/cart"),
        PRODUCT("/product"),
        ORDER("/order");

        private String path;

        Servlets(String path){
            this.path = path;
        }

        public String getPath(){
            return path;
        }
    }
}
