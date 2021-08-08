package DAO;

import question.MultipleAnswerUnorderedResponseQuestion;
import question.MultipleChoiceUnorderedResponseQuestion;
import question.Question;
import quiz.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MultipleChoiceUnorderedResponseQuestionDao implements QuestionDao{
    Connection conn;

    public MultipleChoiceUnorderedResponseQuestionDao(Connection conn){
        this.conn=conn;
    }

    @Override
    public void addQuestion(Question question) {
        MultipleChoiceUnorderedResponseQuestion q = (MultipleChoiceUnorderedResponseQuestion)question;
        try {
            PreparedStatement statement = conn.prepareStatement
                    ("INSERT  INTO multiple_choice_unordered_questions (question_text, quiz_id)" +
                            "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, q.getQuestionText());
            statement.setInt(2, q.getQuizId());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int question_id =rs.getInt(1);

            HashSet<String> legalAnswers = q.getLegalAnswers();
            HashSet<String> choices = q.getChoices();
            System.out.println(choices);
            for(String s : choices){
                PreparedStatement statement1 = conn.prepareStatement
                        ("Insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values (?, ?, ?);");

                statement1.setString(1, s);
                statement1.setInt(2, question_id);

                if(legalAnswers.contains(s)){
                    statement1.setBoolean(3, true);
                } else {
                    statement1.setBoolean(3, false);
                }
                statement1.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Question> getQuestions(int quizId) {
        List<Question> result = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("select * from multiple_choice_unordered_questions WHERE  quiz_id = ?;" );
            st.setInt(1, quizId);
            ResultSet res = st.executeQuery();

            while(res.next()){
                String text = res.getString("question_text");
                int question_id = res.getInt("id");
                int quiz_id = res.getInt("quiz_id");
                HashSet<String> legalAnswers = getLegalAnswers(question_id);
                HashSet<String> choices = getChoices(question_id);
                Question q = new MultipleChoiceUnorderedResponseQuestion(text, legalAnswers, choices);
                result.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private HashSet<String> getChoices(int question_id) {
        HashSet<String> result = new HashSet<>();
        try {
            PreparedStatement st = conn.prepareStatement("select * from  multiple_choice_unordered_answers  WHERE question_id = ?;" );
            st.setInt(1, question_id);
            ResultSet res = st.executeQuery();
            while(res.next()){
                result.add(res.getString("answer_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private HashSet<String> getLegalAnswers(int question_id) {
        HashSet<String> result = new HashSet<>();
        try {
            PreparedStatement st = conn.prepareStatement("select * from multiple_choice_unordered_answers  WHERE question_id = ? AND is_correct;" );
            st.setInt(1, question_id);
            ResultSet res = st.executeQuery();
            while(res.next()){
                result.add(res.getString("answer_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
