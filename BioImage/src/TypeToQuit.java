
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import java.io.*;

public class TypeToQuit{
	public static void main(String[] args){	
		Timer t = new Timer(1000,new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println(new Date());
				}
			});
		t.start();
		Quit q = new Quit();
		q.start();
	}

}

class Quit extends Thread{
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public void run(){
		while(true){
			try{
				int a = br.read();
				if(a=='q'){
					System.exit(0);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}


