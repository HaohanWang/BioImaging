package objectModel;

public class report {
    private Tutorial tutorial = null;
    private User learner = null;
    private String Algorithm = null, filename = null;
    
    public Tutorial getTutorial() 
    {
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
    
    public String getAlgorithm() 
    {
        return Algorithm;
    }
    
    public void setAlgorithm(String algorithm) 
    {
        Algorithm = algorithm;
    }
    
    public String getFilename() 
    {
       return filename;
    }
    
    public void setFilename(String filename) 
    {
       this.filename = filename;
    }  
  
}
