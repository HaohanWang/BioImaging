package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import objectModel.SignalNode;

public class SignalNodeDao extends BaseDao<SignalNode> {
  DataBaseConnection instance = DataBaseConnection.getInstance();

  public SignalNode getById(int id) {
    SignalNode s = new SignalNode();
    String sql = "select * from signal where id=" + id;
    ResultSet rs = instance.query(sql);
    try {
      if (rs != null && rs.next()) {
        s.setAlpha1(rs.getDouble("alpha1"));
        s.setAlpha2(rs.getDouble("alpha2"));
        s.setAttention(rs.getDouble("attention"));
        s.setBeta1(rs.getDouble("beta1"));
        s.setBeta2(rs.getDouble("beta2"));
        s.setConfusion(rs.getInt("confusion"));
        s.setDelta(rs.getDouble("delta"));
        s.setGamma1(rs.getDouble("gamma1"));
        s.setGamma2(rs.getDouble("gamma2"));
        s.setMeditation(rs.getDouble("meditation"));
        s.setRaw(rs.getDouble("raw"));
        s.setTheta(rs.getDouble("theat"));
        s.setTimestamp(rs.getLong("timestamp"));
        s.setUserId(rs.getInt("user"));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return s;
  }

  public void save(SignalNode s) {
    String sql = "insert signal values(timestamp, attention, meditation, raw, delta, theta,alpha1, alpha2, beta1, beta2, gamma1, gamma2,confusion) values ("
            + s.getTimestamp()
            + ","
            + s.getAttention()
            + ","
            + s.getMeditation()
            + ","
            + s.getRaw()
            + ","
            + s.getDelta()
            + ","
            + s.getTheta()
            + ","
            + s.getAlpha1()
            + ","
            + s.getAlpha2()
            + ","
            + s.getBeta1()
            + ","
            + s.getBeta2()
            + ","
            + s.getGamma1()
            + ","
            + s.getGamma2() + s.getConfusion();
    instance.update(sql);
  }

  public ArrayList<SignalNode> getAll() {
    ArrayList<SignalNode> sl = new ArrayList<SignalNode>();
    String sql = "select * from signal";
    ResultSet rs = instance.query(sql);
    try {
      while (rs != null && rs.next()) {
        SignalNode s = new SignalNode();
        s.setAlpha1(rs.getDouble("alpha1"));
        s.setAlpha2(rs.getDouble("alpha2"));
        s.setAttention(rs.getDouble("attention"));
        s.setBeta1(rs.getDouble("beta1"));
        s.setBeta2(rs.getDouble("beta2"));
        s.setConfusion(rs.getInt("confusion"));
        s.setDelta(rs.getDouble("delta"));
        s.setGamma1(rs.getDouble("gamma1"));
        s.setGamma2(rs.getDouble("gamma2"));
        s.setMeditation(rs.getDouble("meditation"));
        s.setRaw(rs.getDouble("raw"));
        s.setTheta(rs.getDouble("theat"));
        s.setTimestamp(rs.getLong("timestamp"));
        s.setUserId(rs.getInt("user"));
        sl.add(s);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return sl;
  }
  @SuppressWarnings("finally")
  public int getCount(){
    String sql = "select count(*) from signal";
    ResultSet rs = instance.query(sql);
    try {
      if (rs != null && rs.next()) {
        return rs.getInt("count");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return 0;
    }
  }
  
  public void delete(SignalNode s){
    String sql = "delete from signal where timestamp="+s.getTimestamp();
    instance.update(sql);
  }
}
