package servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/ErrorServlet")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch ((Integer) req.getAttribute("javax.servlet.error.status_code")){
            case 404:
                resp.getWriter().println("<h1>Страница не найдена!</h1>");
                break;
            case 403:
                resp.getWriter().println("<h1>Доступ запрещен!</h1>");
                break;
            default:
                resp.getWriter().println("<h1>Неизвестная ошибка!</h1>");
        }
        getServletContext().getRequestDispatcher("/menu").include(req, resp);
    }
}
