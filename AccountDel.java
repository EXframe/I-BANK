import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

public class AccountDel extends JFrame implements ActionListener {
  DBManager db = new DBManager();
  ResultSet rs;
  JFrame acd = new JFrame("销户");
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
	upper.setFont(new Font("微软雅黑",Font.ITALIC,16));
	upper.setForeground(Color.WHITE);
	
	cardTextField = new JTextField(20);
	cardTextField.setBounds(585, 255, 200, 25);
	cardTextField.setBorder(null);
	cardTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
	passwordTextField = new JPasswordField(20);
	passwordTextField.setBounds(585, 328, 200, 25);
	passwordTextField.setBorder(null);
	passwordTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    
	delBtn = new JButton("销户");
    delBtn.setBounds(550, 410, 110, 30);
    delBtn.addActionListener(this);
    delBtn.setOpaque(false);
    delBtn.setFont(new Font("微软雅黑",Font.BOLD,18));
    delBtn.setForeground(Color.RED);
    delBtn.setContentAreaFilled(false);
    delBtn.setFocusPainted(false);
    cancelBtn = new JButton("取消");
    cancelBtn.setBounds(680, 410, 110, 30);
    cancelBtn.addActionListener(this);
    cancelBtn.setOpaque(false);
    cancelBtn.setFont(new Font("微软雅黑",Font.BOLD,18));
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
    
    JLabel info1 = new JLabel("尊敬的"+MainFrame.privelege);
    info1.setBounds(55, 230, 300, 25);
    info1.setFont(new Font("微软雅黑",Font.BOLD,15));
    info1.setForeground(Color.WHITE);
    
    JLabel info2 = new JLabel(MainFrame.name+"，您好！");
    info2.setBounds(55, 260, 300, 25);
    info2.setFont(new Font("微软雅黑",Font.BOLD,15));
    info2.setForeground(Color.WHITE);
    
    JLabel info3 = new JLabel("余额："+infob);
    info3.setBounds(55, 290, 300, 25);
    info3.setFont(new Font("微软雅黑",Font.BOLD,15));
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
        if (cardTextField.getText().trim().equals(""))  new Notify(null, "卡号不能为空！");
        else if (stattemp.equals("销户")) new Notify(null, "不能对已注销账户再注销！");
        else if (!privtemp.equals("个人客户") && MainFrame.privelege.equals("银行职员")) new Notify(null, "职员只能由经理销户!");
        else if (passwordTextField.equals("")) {
          new Notify(null, "密码不能为空！");
        }
        else if (db.getResult(strSQL).first()) {
            
        	strSQL= "select balance from deposit where card='" +
                    cardTextField.getText().trim() + "'";
            rs = db.getResult(strSQL); 
            rs.last();
            balance=rs.getString("balance");
            new Notify(null, "即将取出余额："+balance+"并销户");
        	
        	strSQL = "update account set adate=(SELECT CONVERT(VARCHAR(30),GETDATE(),9)),status='销户' where card='" +
              cardTextField.getText().trim() + "'";
          if (db.updateSql(strSQL)>0) {
            new Notify(null, "销户成功!");
            this.dispose();
            
            strSQL = "insert into deposit values ((SELECT CONVERT(VARCHAR(30),GETDATE(),9)),'"+cardTextField.getText().trim()+"','"+balance+"','0','取款')";
            if (db.updateSql(strSQL)>0) {
                new Notify(null, "取款成功！");
                MainFrame mf = new MainFrame();
                acd.dispose();
                }
            else {
                new Notify(null, "取款失败！");
                MainFrame mf = new MainFrame();
                acd.dispose();
                }
          }
          else {
            new Notify(null, "销户失败！");
          }
        }
        else {
          new Notify(null, "卡号或密码错误！");
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
