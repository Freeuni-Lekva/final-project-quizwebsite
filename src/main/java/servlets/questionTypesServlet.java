package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/questionTypesServlet")
public class questionTypesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        int curr =  (int)httpServletRequest.getSession().getAttribute("currQuestions");
        curr ++;
        httpServletRequest.getSession().setAttribute("currQuestions", curr);
        String type = httpServletRequest.getParameter("type");
        httpServletRequest.getSession().setAttribute("type", type);
        httpServletRequest.getRequestDispatcher("Question.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
