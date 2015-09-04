import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class MainFrame extends JFrame implements ActionListener{	
	DBManager db = new DBManager();
	JFrame jf1 = new JFrame("���¼");
	JPanel panel1;
	JTextField cardTextField;
	JPasswordField passwordTextField;
	JButton yesBtn, cancelBtn;
	ResultSet rs;
	Connection con;
	Container c1;   
    JLabel backlabel;
    static int width = 1120;
    static int height = 630;
    static String card, privelege, name;
    static int Locatewidth = (Toolkit.getDefaultToolkit().getScreenSize().width - 1120) / 2;
    static int Locateheight = (Toolkit.getDefaultToolkit().getScreenSize().height - 630) / 2; 
    
    public MainFrame(){
    	// ���ñ���ͼƬ
    	ImageIcon background;
        background = new ImageIcon(getClass().getResource("login.jpg"));
        backlabel = new JLabel(background);
        backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());
        // ���ô����С������
        jf1.setLocation(Locatewidth, Locateheight);
        jf1.setUndecorated(true);
        jf1.setSize(width, height);
        jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        yesBtn = new JButton("��¼");
        yesBtn.addActionListener(this);
        yesBtn.setBounds(485, 400, 70, 30);
        yesBtn.setOpaque(false);
        yesBtn.setFont(new Font("΢���ź�",Font.BOLD,18));
        yesBtn.setForeground(new Color(100,81,40));
        yesBtn.setContentAreaFilled(false);
        yesBtn.setFocusPainted(false);
        cancelBtn = new JButton("�˳�");
        cancelBtn.addActionListener(this);
        cancelBtn.setBounds(600, 400, 70, 30);
        cancelBtn.setOpaque(false);
        cancelBtn.setFont(new Font("΢���ź�",Font.BOLD,18));
        cancelBtn.setForeground(new Color(100,81,40));
        cancelBtn.setContentAreaFilled(false);  
        cancelBtn.setFocusPainted(false);
        
        cardTextField = new JTextField(20);
        cardTextField.setBounds(490, 272, 200, 25);
        cardTextField.setBorder(null);
        cardTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
        passwordTextField =  new JPasswordField(20);
        passwordTextField.setBounds(490, 330, 200, 25);
        passwordTextField.setBorder(null);
        passwordTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
        
        c1 = jf1.getContentPane();
        c1.setLayout(null);
        panel1 = new JPanel();
        panel1.setSize(1120, 630);
        panel1.setLocation(0, 0);
        panel1.setLayout(null);
        panel1.setOpaque(false);
        panel1.add(cardTextField);
        panel1.add(passwordTextField);
        panel1.add(yesBtn);
        panel1.add(cancelBtn);
        
        c1.add(panel1);
        c1.add(backlabel);
        jf1.setVisible(true); 
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBtn) {
        	jf1.dispose();
        }
        else {
          char[] password = passwordTextField.getPassword();
          String passwordSTR = new String(password);
          if (cardTextField.getText().trim().equals("")) {
        	new Notify(null, "�û�������Ϊ��!");
        	return;
          }
          else if (passwordSTR.equals("")) {
            new Notify(null, "���벻��Ϊ��!");
            return;
          }
          String strSQL;
          strSQL = "select * from account where card='" +
              cardTextField.getText().trim() + "'and password='" +
              passwordSTR +"'and status='����'";

          rs = db.getResult(strSQL);
          boolean isExist = false;
          try {
        	  isExist = rs.first();	 
          }
          catch (SQLException sqle) {	
            System.out.println(sqle.toString());
          }
          if (!isExist) {
            new Notify(null, "����/���벻��ȷ��ÿ�������!");
          }
          else {
            try {
              rs.first();
              card = cardTextField.getText().trim();
              name = rs.getString("name").trim();
              privelege = rs.getString("privelege").trim();
              Select F2 = new Select();
              jf1.dispose();
            }
            catch (SQLException sqle2) {
            	System.out.println(sqle2.toString());
            }
          }  
        }
      }
    
    public static void main(String args[]) {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        MainFrame mainFrame = new MainFrame();
      }

}
