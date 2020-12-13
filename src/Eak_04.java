
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

public class Eak_04 {

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Подключение к базе данных
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/notebook", "root", "Asdasd99");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("SELECT * FROM notebook");

            // Вывод начальных данных
            System.out.println("Список блокнота: ");
            while (rs.next()) {
                System.out.println(rs.getString("name") + ": " + rs.getString("num") );
            }

            System.out.println("\nИзменение номеров -1000 до +1000 ...\n");
            rs.beforeFirst();
            while (rs.next()) {
                String oldNum = rs.getString("num");
                String newNum = changeNum(oldNum);
                rs.updateString("num", newNum);
                rs.updateRow();
            }

            System.out.println("изменение номеров в блокноте: ");
            rs.beforeFirst();
            while (rs.next()) {
                System.out.println(rs.getString("name") + ": " + rs.getString("num"));
            }
        } catch (SQLException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static String changeNum(String num) {
        return Long.toString(Long.parseLong(num) + ThreadLocalRandom.current().nextInt(-1000, 1000));
    }
}
