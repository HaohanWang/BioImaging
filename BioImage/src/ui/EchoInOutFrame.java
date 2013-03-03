package ui;
/*���ڳ���*/

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EchoInOutFrame  extends JFrame{
  protected Container contentPane;
  protected JPanel inPan=new JPanel(); //��Ϣ¼�����

  JLabel input;
  JButton sendBt;  
  JTextArea out;
  JTextField in;
  Font font=new Font("",Font.BOLD,20);
  
  public EchoInOutFrame(String title) throws IOException {
    super(title);
   
    out=new JTextArea(10,20);
    out.setFont(font);
    out.setEditable(false);

    input=new JLabel("Input:");
    sendBt=new JButton("Send");
    in=new JTextField(20);  
    in.setFont(font);
    
   inPan.add(input);
   inPan.add(in);
   inPan.add(sendBt);   

   contentPane=getContentPane();//��ʾ��������,¼�봰������.
   contentPane.setLayout(new BorderLayout());
   contentPane.add(out,BorderLayout.NORTH);
   contentPane.add(inPan,BorderLayout.SOUTH);

    setSize(500,400);   
    setVisible(true);

    addWindowListener(new WindowAdapter(){
       public void windowClosing(WindowEvent evt){
           System.exit(0);  //�رմ���.
      }
    });   
  }

  public void append(String msg) {
      out.append("\r\n"+msg);
   }

  public String get(){
     String msg="";
     while(msg.endsWith(".")!=true)
         {msg=in.getText();}
     in.setText("");
     return msg;
}
   
  public static void main(String args[])throws IOException {
    new EchoInOutFrame("Server").append("show here");
          
  }
}