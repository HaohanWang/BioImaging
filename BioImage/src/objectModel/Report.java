package objectModel;

public class Report {
  private Tutorial tutorial = null;

  private User learner = null;

  private String algorithm = null, filename = null;

  public Tutorial getTutorial() {
    return tutorial;
  }

  public void setTutorial(Tutorial tutorial) {
    this.tutorial = tutorial;
  }

  public void setLearner(User learner) {
    this.learner = learner;
  }

  public User getLearner() {

    return learner;
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

}
