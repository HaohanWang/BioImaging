package persistence;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataBaseConnection {
  private static DataBaseConnection instance;
  // temporarily use
  BufferedWriter out = null;
  private DataBaseConnection(){
    //temporarily, only use a file to store.
    try {
      this.out =  new BufferedWriter(new FileWriter("pesdoDataBase.txt"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("FailtoCreateDataBase");
      e.printStackTrace();
    }
  }
  public static DataBaseConnection getInstance(){
    if (instance==null){
      instance=new DataBaseConnection();
    }
    return instance;
  }
//  public ResultSet query (String q){
//    
//  }
//  public boolean update(String q){
//    
//  }
//  public boolean connect(){
//    
//  }
}
