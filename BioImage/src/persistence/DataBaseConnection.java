package persistence;

import java.sql.*;

public class DataBaseConnection {
  private static DataBaseConnection instance;

  private final String user = "root";

  private final String passwd = "111111";

  private final String strDBname = "mydb";

  private Statement stmt = null;

  private ResultSet rs = null;

  private Connection conn = null;

  // String sql;

  // String strurl;

  private DataBaseConnection() {
    boolean suc = connect();
  }

  public static DataBaseConnection getInstance() {
    if (instance == null) {
      instance = new DataBaseConnection();
    }
    return instance;
  }

  public ResultSet query(String q) {
    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery(q);
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("query:" + e.getMessage());
    }
    return rs;
  }

  @SuppressWarnings("finally")
  public boolean update(String q) {
    try {
      stmt = conn.createStatement();
      stmt.executeUpdate(q);
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("update" + e.getMessage());
    } finally {
      return true;
    }
  }

  @SuppressWarnings("finally")
  private boolean connect() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://localhost/:3306" + strDBname + "?user=" + user
              + "&password=" + passwd + "");
      // stmt = conn.createStatement();
    } catch (Exception e) {
      System.out.println("OpenConnection:" + e.getMessage());
      return false;
    } finally {
      return true;
    }
  }

}
