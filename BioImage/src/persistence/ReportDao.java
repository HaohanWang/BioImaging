package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import objectModel.Report;

public class ReportDao extends BaseDao<Report> {
  DataBaseConnection instance = DataBaseConnection.getInstance();

  public void saveReportInDirectory(String path) {
    // TODO what to do with this method?
  }

  public Report getNewInstance() {
    // TODO what to do with this method?
    return null;
  }

  public Report getById(int id) {
    Report r = new Report();
    String sql = "select * from mydb.report where id=" + id+"";
    ResultSet rs = instance.query(sql);
    try {
      if (rs != null && rs.next()) {
        r.setAlgorithm(rs.getString("algorithm"));
        r.setFilename(rs.getString("fileName"));
        String email = rs.getString("user");
        r.setLearner(new UserDao().getById(email));
        int tutorial = rs.getInt("tutorial");
        r.setTutorial(new TutorialDao().getById(tutorial));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return r;
  }

  public ArrayList<Report> getAll() {
    ArrayList<Report> rl = new ArrayList<Report>();
    String sql = "select * from mydb.report";
    ResultSet rs = instance.query(sql);
    try {
      while (rs != null && rs.next()) {
        Report r = new Report();
        r.setAlgorithm(rs.getString("algorithm"));
        r.setFilename(rs.getString("fileName"));
        String email = rs.getString("user");
        r.setLearner(new UserDao().getById(email));
        int tutorial = rs.getInt("tutorial");
        r.setTutorial(new TutorialDao().getById(tutorial));
        rl.add(r);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rl;
  }

  /**
   * @deprecated how to get the id of tutorial
   */
  public void save(Report r) {
    String sql = "insert mydb.report(user, tutorial, fileName, alogirthm) values (\'"
            + r.getLearner().getEmail() + "\',\'" + r.getTutorial().getName() + "\',\'"
            + r.getFilename() + "\',\'" + r.getAlgorithm() + "\'";
    instance.update(sql);
  }

  public void delete(Report r) {
    String sql = "delete from mydb.report where fileName=\'" + r.getFilename() + "\' and user=\'"
            + r.getLearner().getEmail() + "\' and tutorial=\'" + r.getTutorial().getFileName() + "\'";
    instance.update(sql);
  }

  public int getCount() {
    String sql = "select count(*) from mydb.report";
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
