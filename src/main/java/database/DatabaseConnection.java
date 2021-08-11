package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String user = "root";
    private static final String password = "root";
    private static final String db_name = "quizwebsite_db";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, password);
    }

    public static void resetTables() throws IOException, SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader("reset.sql"));
        String s;

        while ((s = reader.readLine()) != null) {
            sql.append(s);
        }

        Statement statement = getConnection().createStatement();

        String[] inst = sql.toString().split(";");

        for (String value : inst) {
            if (!value.trim().equals("")) {
                statement.executeUpdate(value);
            }
        }
    }
}
