package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import objectModel.User;

public class UserDao extends BaseDao<User> {
  DataBaseConnection instance = DataBaseConnection.getInstance();

  public User getById(String id) {
    User u = new User();
    String sql = "select * from mydb.user where email=\'" + id + "\'";
    ResultSet rs = instance.query(sql);
    try {
      if (rs != null && rs.next()) {
        u.setName(rs.getString("name"));
        u.setAddress(rs.getString("address"));
        u.setAuthority(rs.getInt("authority"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password").toCharArray());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return u;
  }

  public void save(User u) {
    String sql = "insert into mydb.user(name,address,email,password,authority) values (\'"
            + u.getName() + "\',\'" + u.getAddress() + "\',\'" + u.getEmail() + "\',\'"
            + u.getPassword().toString() + "\'," + u.getAuthority() + ")";
    instance.update(sql);
  }

  /**
   * @deprecated
   * @return
   */
  public ArrayList<User> getAll() {
    ArrayList<User> ul = new ArrayList<User>();
    String sql = "select * from mydb.user";
    ResultSet rs = instance.query(sql);
    try {
      while (rs != null && rs.next()) {
        User u = new User();
        u.setAddress(rs.getString("address"));
        u.setAuthority(rs.getInt("authority"));
        u.setEmail(rs.getString("email"));
        u.setName(rs.getString("name"));
        u.setPassword(rs.getString("password").toCharArray());
        ul.add(u);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // TODO
    return ul;
  }

  @SuppressWarnings("finally")
  public int getCount() {
    String sql = "select count(*) from mydb.user";
    ResultSet rs = instance.query(sql);
    try {
      if (rs != null && rs.next()) {
        return rs.getInt("count(*)");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public void delete(User u) {
    String sql = "delete from mydb.user where email=\'" + u.getEmail() + "\'";
    instance.query(sql);
  }
}
