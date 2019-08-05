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
        String error;
        switch ((Integer) req.getAttribute("javax.servlet.error.status_code")){
            case 404:
                error = "Страница не найдена!";
                break;
            case 403:
                error = "Доступ запрещен!";
                break;
            default:
                error = "Неизвестная ошибка!";
        }
        req.setAttribute("title", "Error");
        req.setAttribute("error", error);
        req.getRequestDispatcher("WEB-INF/views/error.jsp").forward(req, resp);
    }
}
