package ui;
//��Listing 5���ػ�Java����̨�����
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
public class ConsoleTextArea extends JTextArea {
    public ConsoleTextArea(InputStream[] inStreams) {
        for(int i = 0; i < inStreams.length; ++i)
            startConsoleReaderThread(inStreams[i]);
    } // ConsoleTextArea()
    public ConsoleTextArea() throws IOException {
        final LoopedStreams ls = new LoopedStreams();
        // �ض���System.out��System.err
        PrintStream ps = new PrintStream(ls.getOutputStream());
        System.setOut(ps);
        System.setErr(ps);
        startConsoleReaderThread(ls.getInputStream());
    } // ConsoleTextArea()
    private void startConsoleReaderThread(
        InputStream inStream) {
        final BufferedReader br =
            new BufferedReader(new InputStreamReader(inStream));
        new Thread(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer();
                try {
                    String s;
                    Document doc = getDocument();
                    while((s = br.readLine()) != null) {
                        boolean caretAtEnd = false;
                        caretAtEnd = getCaretPosition() == doc.getLength() ?
                            true : false;
                        sb.setLength(0);
                        append(sb.append(s).append('\n').toString());
                        if(caretAtEnd)
                            setCaretPosition(doc.getLength());
                    }
                }
                catch(IOException e) {
                    JOptionPane.showMessageDialog(null,
                        "��BufferedReader��ȡ����" + e);
                    System.exit(1);
                }
            }
        }).start();
    } // startConsoleReaderThread()
    // ����ʣ�ಿ�ֵĹ����ǽ��в���
    public static void main(String[] args) {
        JFrame f = new JFrame("ConsoleTextArea����");
        ConsoleTextArea consoleTextArea = null;
        try {
            consoleTextArea = new ConsoleTextArea();
        }
        catch(IOException e) {
            System.err.println(
                "���ܴ���LoopedStreams��" + e);
            System.exit(1);
        }
        consoleTextArea.setFont(java.awt.Font.decode("monospaced"));
        f.getContentPane().add(new JScrollPane(consoleTextArea),
            java.awt.BorderLayout.CENTER);
        f.setBounds(50, 50, 300, 300);
        f.setVisible(true);
        f.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(
                java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        });
        // ��������д�����߳���
        // System.out��System.err���
        startWriterTestThread("confusion", System.out, 200, 50);
//        startWriterTestThread(
//            "д�����߳� #1", System.err, 920, 50);
//        startWriterTestThread(
//            "д�����߳� #2", System.out, 500, 50);
//        startWriterTestThread(
//            "д�����߳� #3", System.out, 200, 50);
//        startWriterTestThread(
//            "д�����߳� #4", System.out, 1000, 50);
//        startWriterTestThread(
//            "д�����߳� #5", System.err, 850,    50);
    } // main()
    private static void startWriterTestThread(
        final String name, final PrintStream ps, 
        final int delay, final int count) {
        new Thread(new Runnable() {
            public void run() {
                for(int i = 1; i <= count; ++i) {
                    ps.println(name + ": i=" + i);      //confusion level �޸Ĵ˴����뼴��
                    try {
                        Thread.sleep(delay);
                    }
                    catch(InterruptedException e) {}
                }
            }
        }).start();
    } // startWriterTestThread()
} // ConsoleTextArea
