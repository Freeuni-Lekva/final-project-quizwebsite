package servlets;

import DAO.*;
import database.DatabaseConnection;
import question.*;
import quiz.Quiz;
import response.MultipleUnorderedAnswerResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@WebServlet("/QuizTakeOnSinglePageServlet")
public class QuizTakeOnSinglePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        QuizDao quizDao = (QuizDao)httpServletRequest.getServletContext().getAttribute("QuizDao");
        Quiz quiz = null;
        try {
            quiz = quizDao.getQuiz(quizId);
            httpServletRequest.setAttribute("questions", quiz.getQuestions());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        httpServletRequest.getRequestDispatcher("quizTakeOnSinglePage.jsp").forward(httpServletRequest, httpServletResponse);
    }
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        List<Question> questions= (List<Question> )httpServletRequest.getSession().getAttribute("questions");
        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        String[] getAnswers=httpServletRequest.getParameterValues("answer");
        int type = (int)Long.parseLong(httpServletRequest.getParameter("type"));
        double score = 0.0;
        try {
            MultipleAnswerUnorderedResponseQuestionDao qDao1 = new MultipleAnswerUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
            MultipleChoiceUnorderedResponseQuestionDao qDao2 = new MultipleChoiceUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
            PictureUnorderedResponseQuestionDao qDao3 = new PictureUnorderedResponseQuestionDao(DatabaseConnection.getConnection());
            StandardUnorderedResponseQuestionDao qDao4 = new StandardUnorderedResponseQuestionDao(DatabaseConnection.getConnection());

            List<MultipleAnswerUnorderedResponseQuestion> q1 = qDao1.getQuestionsMultipleAnsUnordered(quizId);
            List<MultipleChoiceUnorderedResponseQuestion> q2 = qDao2.getQuestionsWithChoices(quizId);
            List<PictureUnorderedResponseQuestion> q3 = qDao3.getQuestionsPictureUnordered(quizId);
            List<StandardUnorderedResponseQuestion> q4 = qDao4.getQuestionsStandardUnordered(quizId);

            int index = 0;
            while (true) {
                if (index >= questions.size()) {
                    break;
                }
                Question q = questions.get(index);
                if (type == 1) {
                    for (int i = 0; i < q1.size(); i++) {
                        if (q1.get(i).getQuestionText().equals(q.getQuestionText())) {
                            HashSet<String> ans = new HashSet<>();
                            for(int k=0; k<q1.get(i).getNumOfRequestedAnswers(); k++){
                                ans.add(getAnswers[index + k]);
                            }
                            MultipleUnorderedAnswerResponse r = new MultipleUnorderedAnswerResponse(ans);
                            score += q1.get(i).getScore(r);
                            index+=q1.get(i).getNumOfRequestedAnswers();
                        }
                    }
                } else if (type == 2) {
                    for (int i = 0; i < q2.size(); i++) {
                        String s = q2.get(i).getQuestionText();
                        if (s.equals(q.getQuestionText())) {
                            HashSet<String> ans = new HashSet<>();
                            ans.add(getAnswers[index]);
                            MultipleUnorderedAnswerResponse r = new MultipleUnorderedAnswerResponse(ans);
                            score += q2.get(i).getScore(r);
                            index++;
                        }
                    }
                } else if (type == 3) {
                    for (int i = 0; i < q3.size(); i++) {
                        if (q3.get(i).getQuestionText().equals(q.getQuestionText())) {
                            HashSet<String> ans = new HashSet<>();
                            ans.add(getAnswers[index]);
                            MultipleUnorderedAnswerResponse r = new MultipleUnorderedAnswerResponse(ans);
                            score += q3.get(i).getScore(r);
                            index++;
                        }
                    }
                } else if (type == 4) {
                    for (int i = 0; i < q4.size(); i++) {
                        if (q4.get(i).getQuestionText().equals(q.getQuestionText())) {
                            HashSet<String> ans = new HashSet<>();
                            ans.add(getAnswers[index]);
                            MultipleUnorderedAnswerResponse r = new MultipleUnorderedAnswerResponse(ans);
                            score += q4.get(i).getScore(r);
                            index++;
                        }
                    }
                }
                index++;
            }
            PrintWriter writer = httpServletResponse.getWriter();
            String st= "<title>"+score +"</title>";
            writer.println(st);

        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
