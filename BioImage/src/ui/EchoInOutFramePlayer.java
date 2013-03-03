package ui;
//�����ڵ�JAVA�������

import java.io.*;

public class EchoInOutFramePlayer 
{
  public String echo(String msg) 
  {
    return "echo:"+msg;
  }

  public void talk()throws IOException 
  {
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    String msg=null;
    
    EchoInOutFrame frame=new EchoInOutFrame("My InOutFrame");

   while((msg=frame.get())!=null) 
   {
      frame.append(echo(msg));
 
     if(msg.equals("bye"))  //���û����롰bye������������
       System.exit(0);
    }  
   
  }
  public static void main(String arg[])throws IOException
  {
    new EchoInOutFramePlayer().talk();
  }
}
