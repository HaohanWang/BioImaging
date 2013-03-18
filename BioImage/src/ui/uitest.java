package ui;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;


public class uitest {

  protected Shell shell;
  private Text txtUserId;
  private Text text_1;
  private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

  /**
   * Launch the application.
   * @param args
   */
  public static void main(String[] args) {
    try {
      uitest window = new uitest();
      window.open();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Open the window.
   */
  public void open() {
    Display display = Display.getDefault();
    createContents();
    shell.open();
    shell.layout();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * Create contents of the window.
   */
  protected void createContents() {
    shell = new Shell();
    shell.setSize(450, 300);
    shell.setText("SWT Application");
    
    txtUserId = new Text(shell, SWT.BORDER);
    txtUserId.setBounds(119, 52, 158, 19);
    
    text_1 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
    text_1.setBounds(119, 109, 158, 19);
    
    String text = txtUserId.getText()+text_1.getText();
    
    if(text=="bioimaging123456" )
    MessageDialog.openInformation(shell,"welcome"+ txtUserId.getText()+"login", text);
    else
      MessageDialog.openError(shell,"wrong","please type again");
      
    Button btnLogin = new Button(shell, SWT.NONE);
    btnLogin.setBounds(99, 157, 94, 28);
    btnLogin.setText("login");
    
    Button btnNewUser = new Button(shell, SWT.NONE);
    btnNewUser.setBounds(216, 157, 94, 28);
    btnNewUser.setText("New user");
    
    Label lblUser = formToolkit.createLabel(shell, "user id", SWT.NONE);
    lblUser.setBounds(57, 55, 59, 14);
    
    Label lblPassword = formToolkit.createLabel(shell, "password", SWT.NONE);
    lblPassword.setBounds(57, 109, 59, 14);

  }
}
