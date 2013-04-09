package persistence;

import java.sql.*;

import objectModel.*;

public class DataBaseConnection {
	private static DataBaseConnection instance;

	private final String user = "bioimagingteam";

	private final String passwd = "111111";

	private final String strDBname = "mydb";

	private Statement stmt = null;

	private ResultSet rs = null;

	private Connection conn = null;

	// String sql;

	// String strurl;

	private DataBaseConnection() {
		boolean suc = connect();
		System.out.println(suc);
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

	public boolean update(String q) {
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(q);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("update" + e.getMessage());
		}
		return true;
	}

	// @SuppressWarnings("finally")
	private boolean connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection("jdbc:mysql://instance41489.db.xeround.com:4654/"
							+ strDBname
							+ "?user="
							+ user
							+ "&password="
							+ passwd + "");
			// stmt = conn.createStatement();
		} catch (Exception e) {
			System.out.println("OpenConnection:" + e.getMessage());
			return false;
		}
		System.out.println("hmmm");
		return true;
	}

	public static void main(String args[]) throws SQLException {
		DataBaseConnection instance = DataBaseConnection.getInstance();
		// String sql = "select * from user";
		// ResultSet rs = instance.query(sql);
		// System.out.println(rs);
		UserDao ud = new UserDao();
		TutorialDao td = new TutorialDao();
		SignalNodeDao snd = new SignalNodeDao();
		ReportDao rd = new ReportDao();
		SignalNode s = new SignalNode();
		Tutorial t = new Tutorial();
		t.setFileName("abc");
		t.setLength(100);
		t.setName("what");
		User u = new User();
		u.setEmail("admin@bic.com");
		t.setUnploader(u);
		td.save(t);
	}
}
