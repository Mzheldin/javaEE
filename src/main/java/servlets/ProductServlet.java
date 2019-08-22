package servlets;

import persist.Product;
import persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {

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
        String id = req.getParameter("id");
        if (id != null) {
            Product product = productRepository.findById(Integer.parseInt(id));
            req.setAttribute("product", product);
            req.setAttribute("title", "Product " + product.getName());
            req.getRequestDispatcher("WEB-INF/views/product.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = productRepository.findById(Integer.parseInt(req.getParameter("id")));
        product.setName(req.getParameter("name"));
        product.setDescription(req.getParameter("description"));
        productRepository.merge(product);
        resp.sendRedirect(getServletContext().getContextPath() + "/catalog");
    }
}
