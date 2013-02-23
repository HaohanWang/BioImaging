package objectModel;

public class SignalNode {
  private long timestamp;

  private int userId;

  private double attention;

  private double meditation;

  private double raw;

  private double theta;

  private double delta;

  private double alpha1;

  private double alpha2;

  private double beta1;

  private double beta2;

  private double gamma1;

  private double gamma2;

  private int confusion;

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public double getAttention() {
    return attention;
  }

  public void setAttention(double attention) {
    this.attention = attention;
  }

  public double getMeditation() {
    return meditation;
  }

  public void setMeditation(double meditation) {
    this.meditation = meditation;
  }

  public double getRaw() {
    return raw;
  }

  public void setRaw(double raw) {
    this.raw = raw;
  }

  public double getTheta() {
    return theta;
  }

  public void setTheta(double theta) {
    this.theta = theta;
  }

  public double getDelta() {
    return delta;
  }

  public void setDelta(double delta) {
    this.delta = delta;
  }

  public double getAlpha1() {
    return alpha1;
  }

  public void setAlpha1(double alpha1) {
    this.alpha1 = alpha1;
  }

  public double getAlpha2() {
    return alpha2;
  }

  public void setAlpha2(double alpha2) {
    this.alpha2 = alpha2;
  }

  public double getBeta1() {
    return beta1;
  }

  public void setBeta1(double beta1) {
    this.beta1 = beta1;
  }

  public double getBeta2() {
    return beta2;
  }

  public void setBeta2(double beta2) {
    this.beta2 = beta2;
  }

  public double getGamma1() {
    return gamma1;
  }

  public void setGamma1(double gamma1) {
    this.gamma1 = gamma1;
  }

  public double getGamma2() {
    return gamma2;
  }

  public void setGamma2(double gamma2) {
    this.gamma2 = gamma2;
  }

  public int getConfusion() {
    return confusion;
  }

  public void setConfusion(int confusion) {
    this.confusion = confusion;
  }

}
