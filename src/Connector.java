import java.sql.*;
import java.util.ArrayList;

public class Connector {
    private static final String id = "database";
    private static final String pw = "q1w2e3r4";

    private Connection dbTest;

    Connector() {
        connectDB("localhost", id, pw);
    }

    Connector(String addr) {
        connectDB(addr, id, pw);
    }

    Connector(String addr, String id, String pw) {
        connectDB(addr, id, pw);
    }
    private void connectDB(String addr, String id, String pw) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            dbTest = DriverManager.getConnection("jdbc:oracle:thin:" + "@" + addr + ":1521:XE", id, pw);
            System.out.println("DB에 연결되었습니다.");
        } catch (SQLException e) {
            System.out.println("연결에 실패하였습니다.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean executeQuery(String query) throws SQLException {
        PreparedStatement stmt = dbTest.prepareStatement(query);
        System.out.println("Executing query " + query);
        ResultSet rs = stmt.executeQuery();
        rs.close();
        stmt.close();
        return true;
    }
    String[][] getQueryResult(String query) throws SQLException {
        if(dbTest == null)
            return null;
        System.out.println("Executing query: " + query);
        PreparedStatement stmt = dbTest.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData meta = rs.getMetaData();
        ArrayList<String[]> rows = new ArrayList<>();
        String[] rowName = new String[meta.getColumnCount()];
        for(int i = 1; i <= meta.getColumnCount(); i++)
            rowName[i-1] = meta.getColumnName(i);
        rows.add(rowName);
        while(rs.next()) {
            ArrayList<String> cols = new ArrayList<>();
            for (int i = 1; i <= meta.getColumnCount(); i++)
                cols.add(rs.getString(meta.getColumnName(i)));
            rows.add(cols.toArray(new String[0]));
        }
        rs.close();
        stmt.close();
        return (rows.toArray(new String[0][]));
    }
}
