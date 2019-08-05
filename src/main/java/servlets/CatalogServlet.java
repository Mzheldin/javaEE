package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persist.Product;
import persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CatalogServlet", urlPatterns = "/catalog")
public class CatalogServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(CatalogServlet.class);
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productRepository = (ProductRepository) servletContext.getAttribute("productRepository");
        if (productRepository == null)
            throw new ServletException("No repository in Servlet Context");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Product> products = productRepository.getAllProducts();
            req.setAttribute("products", products);
            req.setAttribute("title", "Catalog");
            req.getRequestDispatcher("WEB-INF/views/catalog.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
