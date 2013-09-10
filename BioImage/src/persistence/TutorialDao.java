package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import persistence.BaseDao;
import persistence.DataBaseConnection;
import persistence.UserDao;

import objectModel.Tutorial;
import objectModel.User;

public class TutorialDao extends BaseDao<Tutorial> {
	DataBaseConnection instance = DataBaseConnection.getInstance();

	public void saveTutorialInDirectory(String path) {
		// TODO
	}

	public Tutorial getNewInstance() {
		// TODO
		return null;
	}

	public Tutorial getById(long id) {
		Tutorial t = new Tutorial();
		String sql = "select * from mydb.tutorial where id=" + id;
		ResultSet rs = instance.query(sql);
		try {
			if (rs != null && rs.next()) {
				t.setName(rs.getString("name"));
				t.setFileName(rs.getString("fileName"));
				t.setLength(rs.getInt("length"));
				String user = rs.getString("user");
				User u = new UserDao().getById(user);
				t.setUnploader(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public ArrayList<Tutorial> getAll() {
		ArrayList<Tutorial> tl = new ArrayList<Tutorial>();
		String sql = "select * from mydb.tutorial";
		ResultSet rs = instance.query(sql);
		try {
			while (rs != null && rs.next()) {
				Tutorial t = new Tutorial();
				t.setName(rs.getString("name"));
				t.setFileName(rs.getString("fileName"));
				t.setLength(rs.getInt("length"));
				String user = rs.getString("user");
				User u = new UserDao().getById(user);
				t.setUnploader(u);
				tl.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tl;
	}

	public void save(Tutorial t) {
		System.out.println(t.getId());
		String sql = "insert mydb.tutorial(id, name, fileName,length,user) values(\'"
				+ t.getId()
				+ "\',\'"
				+ t.getName()
				+ "\',\'"
				+ t.getFileName()
				+ "\',"
				+ t.getLength() + ",\'" + t.getUnploader().getEmail() + "\')";
		System.out.println(sql);
		instance.update(sql);
	}

	public void delete(Tutorial t) {
		String sql = "delete from mydb.tutorial where name=\'" + t.getName()
				+ "\'and user=\'" + t.getUnploader().getEmail() + "\'";
		instance.update(sql);
	}

	@SuppressWarnings("finally")
	public int getCount() {
		String sql = "select count(*) from mydb.tutorial";
		ResultSet rs = instance.query(sql);
		try {
			if (rs != null && rs.next()) {
				return rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return 0;
		}
	}
}