import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

public class AccountModify extends JFrame implements ActionListener {
  DBManager db = new DBManager();
  ResultSet rs;
  JFrame acm = new JFrame("修改密码");
  JPanel panel1, panel2;
  Container c;
  JLabel backlabel, upper;
  JPasswordField PasswordTextField, NewPasswordTextField, PasswordConfirmTextField;
  JTextField cardTextField;
  JButton UpdateBtn, CancelBtn;
  String card;
  public AccountModify() {
	ImageIcon background;
	background = new ImageIcon(getClass().getResource("modify.jpg"));
	backlabel = new JLabel(background);
	backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());
	      
	acm.setLocation(MainFrame.Locatewidth, MainFrame.Locateheight);
	acm.setUndecorated(true);
	acm.setSize(1120, 630);
	acm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	upper= new JLabel(MainFrame.privelege+" "+MainFrame.card, JLabel.LEFT);
	upper.setBounds(5, 0, 300, 25);
	upper.setFont(new Font("微软雅黑",Font.ITALIC,16));
	upper.setForeground(Color.WHITE);

    if (MainFrame.privelege.equals("个人客户")) {
    	cardTextField = new JTextField(MainFrame.card);
    	cardTextField.setEnabled(false);
    	cardTextField.setBackground(Color.WHITE);
    } 
    else cardTextField = new JTextField(16); 
    cardTextField.setBounds(595, 223, 200, 25);
    cardTextField.setBorder(null);
    cardTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    PasswordTextField =  new JPasswordField(20);
    PasswordTextField.setBounds(595, 283, 200, 25);
    PasswordTextField.setBorder(null);
    PasswordTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    NewPasswordTextField =  new JPasswordField(20);
    NewPasswordTextField.setBounds(595, 340, 200, 25);
    NewPasswordTextField.setBorder(null);
    NewPasswordTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    PasswordConfirmTextField =  new JPasswordField(20);
    PasswordConfirmTextField.setBounds(595, 397, 200, 25);
    PasswordConfirmTextField.setBorder(null);
    PasswordConfirmTextField.setFont(new Font("微软雅黑",Font.BOLD,15));


    
    UpdateBtn = new JButton("更改");
    UpdateBtn.setBounds(550, 450, 110, 30);
    UpdateBtn.addActionListener(this);
    UpdateBtn.setOpaque(false);
    UpdateBtn.setFont(new Font("微软雅黑",Font.BOLD,18));
    UpdateBtn.setForeground(Color.RED);
    UpdateBtn.setContentAreaFilled(false);
    UpdateBtn.setFocusPainted(false);
    CancelBtn = new JButton("取消");
    CancelBtn.setBounds(680, 450, 110, 30);
    CancelBtn.addActionListener(this);
    CancelBtn.setOpaque(false);
    CancelBtn.setFont(new Font("微软雅黑",Font.BOLD,18));
    CancelBtn.setForeground(new Color(100,81,40));
    CancelBtn.setContentAreaFilled(false);
    CancelBtn.setFocusPainted(false);
   
    c = acm.getContentPane();
    c.setLayout(null);
    panel1 = new JPanel();
    panel1.setLayout(null);
    panel1.setSize(1120, 630);
    panel1.setLocation(0, 0);
    panel1.setOpaque(false);
    panel1.add(upper);
    panel1.add(cardTextField);
    panel1.add(PasswordTextField);
    panel1.add(NewPasswordTextField);
    panel1.add(PasswordConfirmTextField);  
    panel1.add(UpdateBtn);
    panel1.add(CancelBtn);
    
    String infob = "";
    try {
    	String strSQL = "select balance from deposit where card='" + MainFrame.card + "' order by ddate";
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

    acm.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == CancelBtn) {
      Select select = new Select();
      acm.dispose();
    }
    else if (e.getSource() == UpdateBtn) {
      try {
    	card = cardTextField.getText().trim();
        char[] password = PasswordTextField.getPassword();
        String passwordSTR = new String(password);
        char[] newPassword = NewPasswordTextField.getPassword();
        String newPasswordSTR = new String(newPassword);
        char[] confirmPassword = PasswordConfirmTextField.getPassword();
        String confirmPasswordSTR = new String(confirmPassword);
        String strSQL = "select password, privelege, status from account where card='" +
        		card + "'";
        rs = db.getResult(strSQL);
        if (cardTextField.getText().trim().equals("")) new Notify(null, "卡号不能为空！");
        else if (!rs.first()) new Notify(null, "卡号不存在！");
        else if (rs.getString("status").trim().equals("销户")) new Notify(null, "不能为已注销的账户修改密码！");
        else if (!rs.getString("privelege").trim().equals("个人客户") && !cardTextField.getText().trim().equals(MainFrame.card) && MainFrame.privelege.equals("银行职员")) {
        	new Notify(null, "职员只能更改客户或自己的密码！");
        }
        else if (!rs.getString("password").trim().equals(passwordSTR)) new Notify(null, "原密码不正确！");
        else if (newPasswordSTR.equals("")) {
          new Notify(null, "原密码不能为空！");
        }
        else if (passwordSTR.equals("")) {
            new Notify(null, "原密码不能为空！");
        }
        else if (!newPasswordSTR.equals(confirmPasswordSTR)) {
          new Notify(null, "两次输入的新密码不一致！");
        }
        else {
            strSQL = "update account set password='" +
                newPasswordSTR + "'where card='" + card + "'";
            if (db.updateSql(strSQL)>0) {
              new Notify(null, "更新密码成功！");
              Select select = new Select();
              acm.dispose();
            }
            else {
              new Notify(null, "更新密码失败！");
            }
          }
        }
      catch (Exception ex) {
        System.out.println(ex.toString());
      }
    }
  }
}
