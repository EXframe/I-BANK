import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

public class AccountDel extends JFrame implements ActionListener {
  DBManager db = new DBManager();
  ResultSet rs;
  JFrame acd = new JFrame("����");
  JPanel panel1;
  Container c;
  JLabel backlabel, upper;
  JTextField cardTextField;
  JPasswordField passwordTextField;
  JButton delBtn, cancelBtn;
  String balance;
  public AccountDel() {
	ImageIcon background;
	background = new ImageIcon(getClass().getResource("del.jpg"));
	backlabel = new JLabel(background);
	backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());
		      
	acd.setLocation(MainFrame.Locatewidth, MainFrame.Locateheight);
	acd.setUndecorated(true);
	acd.setSize(1120, 630);
	acd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    
	upper= new JLabel(MainFrame.privelege+" "+MainFrame.card, JLabel.LEFT);
	upper.setBounds(5, 0, 300, 25);
	upper.setFont(new Font("΢���ź�",Font.ITALIC,16));
	upper.setForeground(Color.WHITE);
	
	cardTextField = new JTextField(20);
	cardTextField.setBounds(585, 255, 200, 25);
	cardTextField.setBorder(null);
	cardTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
	passwordTextField = new JPasswordField(20);
	passwordTextField.setBounds(585, 328, 200, 25);
	passwordTextField.setBorder(null);
	passwordTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
    
	delBtn = new JButton("����");
    delBtn.setBounds(550, 410, 110, 30);
    delBtn.addActionListener(this);
    delBtn.setOpaque(false);
    delBtn.setFont(new Font("΢���ź�",Font.BOLD,18));
    delBtn.setForeground(Color.RED);
    delBtn.setContentAreaFilled(false);
    delBtn.setFocusPainted(false);
    cancelBtn = new JButton("ȡ��");
    cancelBtn.setBounds(680, 410, 110, 30);
    cancelBtn.addActionListener(this);
    cancelBtn.setOpaque(false);
    cancelBtn.setFont(new Font("΢���ź�",Font.BOLD,18));
    cancelBtn.setForeground(new Color(100,81,40));
    cancelBtn.setContentAreaFilled(false);
    cancelBtn.setFocusPainted(false);
    
    c = acd.getContentPane();
    c.setLayout(null);
    panel1 = new JPanel();
    panel1.setLayout(null);
    panel1.setSize(1120, 630);
    panel1.setLocation(0, 0);
    panel1.setOpaque(false);
    panel1.add(upper);
    panel1.add(cardTextField);
    panel1.add(passwordTextField);
    panel1.add(delBtn);
    panel1.add(cancelBtn);
    
    String infob = "";
    try {
    	String strSQL = "select balance from deposit where card='" + MainFrame.card + "'";
    	rs = db.getResult(strSQL);
    	rs.last();
        infob = rs.getString("balance");
	} catch (SQLException e) {
		e.printStackTrace();
	}
    
    JLabel info1 = new JLabel("�𾴵�"+MainFrame.privelege);
    info1.setBounds(55, 230, 300, 25);
    info1.setFont(new Font("΢���ź�",Font.BOLD,15));
    info1.setForeground(Color.WHITE);
    
    JLabel info2 = new JLabel(MainFrame.name+"�����ã�");
    info2.setBounds(55, 260, 300, 25);
    info2.setFont(new Font("΢���ź�",Font.BOLD,15));
    info2.setForeground(Color.WHITE);
    
    JLabel info3 = new JLabel("��"+infob);
    info3.setBounds(55, 290, 300, 25);
    info3.setFont(new Font("΢���ź�",Font.BOLD,15));
    info3.setForeground(Color.RED);
    panel1.add(info1);
    panel1.add(info2);
    panel1.add(info3);
    
    c.add(panel1);
    c.add(backlabel);
    acd.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    try {
      if (e.getSource() == cancelBtn) {
    	  Select select = new Select();
    	  acd.dispose();
      }
      else if (e.getSource() == delBtn) {
    	  String privtemp = "", stattemp = "";
          try {
        	  String strSQL = "select privelege,status from account where card='" + cardTextField.getText().trim() + "'";
              rs = db.getResult(strSQL);
              rs.first();
        	  privtemp = rs.getString("privelege");
        	  stattemp = rs.getString("status");
          } catch (SQLException e1) {
    		e1.printStackTrace();
          }
        
    	char[] password = passwordTextField.getPassword();
        String passwordSTR = new String(password);
        String strSQL = "select * from account where card='" +
            cardTextField.getText().trim() + "' and password='" +
            passwordSTR + "'";
        if (cardTextField.getText().trim().equals(""))  new Notify(null, "���Ų���Ϊ�գ�");
        else if (stattemp.equals("����")) new Notify(null, "���ܶ���ע���˻���ע����");
        else if (!privtemp.equals("���˿ͻ�") && MainFrame.privelege.equals("����ְԱ")) new Notify(null, "ְԱֻ���ɾ�������!");
        else if (passwordTextField.equals("")) {
          new Notify(null, "���벻��Ϊ�գ�");
        }
        else if (db.getResult(strSQL).first()) {
            
        	strSQL= "select balance from deposit where card='" +
                    cardTextField.getText().trim() + "'";
            rs = db.getResult(strSQL); 
            rs.last();
            balance=rs.getString("balance");
            new Notify(null, "����ȡ����"+balance+"������");
        	
        	strSQL = "update account set adate=(SELECT CONVERT(VARCHAR(30),GETDATE(),9)),status='����' where card='" +
              cardTextField.getText().trim() + "'";
          if (db.updateSql(strSQL)>0) {
            new Notify(null, "�����ɹ�!");
            this.dispose();
            
            strSQL = "insert into deposit values ((SELECT CONVERT(VARCHAR(30),GETDATE(),9)),'"+cardTextField.getText().trim()+"','"+balance+"','0','ȡ��')";
            if (db.updateSql(strSQL)>0) {
                new Notify(null, "ȡ��ɹ���");
                MainFrame mf = new MainFrame();
                acd.dispose();
                }
            else {
                new Notify(null, "ȡ��ʧ�ܣ�");
                MainFrame mf = new MainFrame();
                acd.dispose();
                }
          }
          else {
            new Notify(null, "����ʧ�ܣ�");
          }
        }
        else {
          new Notify(null, "���Ż��������");
        }
      }
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
    }
    catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }
}
