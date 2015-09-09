import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

public class AccountAdd extends JFrame implements ActionListener {
  DBManager db = new DBManager();
  ResultSet rs;
  Container c;
  String priv, privsend, amount;
  JFrame ada;
  JPanel panel1;
  JLabel backlabel, upper;
  JTextField nameTextField, IDTextField, telTextField, cardTextField, amountTextField;
  JPasswordField passwordTextField, passwordConfirmTextField;
  JButton addBtn, cancelBtn;
  int pot, len;
  public AccountAdd(String privilege) {
	priv = privilege;
	if (priv.equals("客户")) privsend = "个人客户";
	else privsend = "银行职员";
	ImageIcon background;
	if (privilege.equals("客户")) background = new ImageIcon(getClass().getResource("custom_create.jpg"));
	else background = new ImageIcon(getClass().getResource("clerk_create.jpg"));
	backlabel = new JLabel(background);
	backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());
	
	ada = new JFrame(priv+"开户");	      
	ada.setLocation(MainFrame.Locatewidth, MainFrame.Locateheight);
	ada.setUndecorated(true);
	ada.setSize(1120, 630);
	ada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    
	upper= new JLabel(MainFrame.privelege+" "+MainFrame.card, JLabel.LEFT);
	upper.setBounds(5, 0, 300, 25);
	upper.setFont(new Font("微软雅黑",Font.ITALIC,16));
	upper.setForeground(Color.WHITE);
     
    cardTextField = new JTextField(20);
    cardTextField.setBounds(615, 157, 200, 25);
    cardTextField.setBorder(null);
    cardTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    nameTextField = new JTextField(20);
    nameTextField.setBounds(615, 215, 200, 25);
    nameTextField.setBorder(null);
    nameTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    telTextField = new JTextField(20);
    telTextField.setBounds(615, 275, 200, 25);
    telTextField.setBorder(null);
    telTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    IDTextField = new JTextField(20);
    IDTextField.setBounds(615, 333, 200, 25);
    IDTextField.setBorder(null);
    IDTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    passwordTextField =  new JPasswordField(20);
    passwordTextField.setBounds(615, 395, 200, 25);
    passwordTextField.setBorder(null);
    passwordTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    passwordConfirmTextField =  new JPasswordField(20);
    passwordConfirmTextField.setBounds(615, 450, 200, 25);
    passwordConfirmTextField.setBorder(null);
    passwordConfirmTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    
    if (privilege.equals("客户")){
    	amountTextField = new JTextField(20);
        amountTextField.setBounds(615, 512, 200, 25);
        amountTextField.setBorder(null);
        amountTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
        }
    else {;}
    
    addBtn = new JButton("开户");
    addBtn.setBounds(950, 480, 110, 30);
    addBtn.addActionListener(this);
    addBtn.setOpaque(false);
    addBtn.setFont(new Font("微软雅黑",Font.BOLD,18));
    addBtn.setForeground(Color.RED);
    addBtn.setContentAreaFilled(false);
    addBtn.setFocusPainted(false);
    cancelBtn = new JButton("取消");
    cancelBtn.setBounds(950, 520, 110, 30);
    cancelBtn.addActionListener(this);
    cancelBtn.setOpaque(false);
    cancelBtn.setFont(new Font("微软雅黑",Font.BOLD,18));
    cancelBtn.setForeground(new Color(100,81,40));
    cancelBtn.setContentAreaFilled(false);
    cancelBtn.setFocusPainted(false);
    
    panel1 = new JPanel();
    panel1.setLayout(null);
    panel1.setSize(1120, 630);
    panel1.setLocation(0, 0);
    panel1.setOpaque(false);
    panel1.add(upper);
    panel1.add(cardTextField);
    panel1.add(nameTextField);
    panel1.add(IDTextField);
    panel1.add(telTextField);
    panel1.add(passwordTextField);
    panel1.add(passwordConfirmTextField);
    if (privilege.equals("客户")) panel1.add(amountTextField);
    else {;}
    panel1.add(addBtn);
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
    
    c = ada.getContentPane();
    c.setLayout(new BorderLayout());
    c.add(panel1);
    c.add(backlabel);
    ada.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == cancelBtn) {
    	Select select = new Select();
    	ada.dispose();
    }
    else if (e.getSource() == addBtn) {
      try {
    	if (priv.equals("客户")) {
          	pot = amountTextField.getText().indexOf(".");
            len = amountTextField.getText().length();
    	}
    	if (priv.equals("客户")) amount = amountTextField.getText().trim();
        else amount = "0.00";   	  
    	String strSQL = "select * from account where card='" + cardTextField.getText().trim() + "'";
    	
        if (cardTextField.getText().trim().equals("")) new Notify(null, "卡号不能为空！"); 
        else if (cardTextField.getText().length()!=8) new Notify(null, "卡号只能为8位！");
        else if (IDTextField.getText().length()!=18) new Notify(null, "身份证只能为18位！");
        else if (telTextField.getText().length()!=11) new Notify(null, "电话只能为11位！");
        else if (new String(passwordTextField.getPassword()).trim().equals("")) new Notify(null, "密码不能为空！");
        else if (!new String(passwordTextField.getPassword()).trim().equals(new String(passwordConfirmTextField.getPassword()).trim())) new Notify(null, "两次输入的密码不一致！"); 
        else if (passwordTextField.getPassword().length!=6) new Notify(null, "密码只能为6位！");
        else if (priv.equals("客户") && amountTextField.getText().trim().equals("")) new Notify(null, "金额不能为空！");
        else if (priv.equals("客户") && (pot>0&&(len-pot>3))) new Notify(null, "金额须在两位小数以内！");     
        else {
          if (db.getResult(strSQL).first()) new Notify(null, "卡号已经存在，请使用另一卡号！");
          else {
            strSQL = "insert into account values(GETDATE(),'" +
                nameTextField.getText().trim() + "','" + 
                IDTextField.getText().trim() + "','" +
                telTextField.getText().trim() + "','" +
                cardTextField.getText().trim() + "','" +
                new String(passwordTextField.getPassword()).trim() + "','开户','" + 
                privsend + "')";
            if (db.updateSql(strSQL)>0) {
              new Notify(null, "开户成功！");
              strSQL = "insert into deposit values(GETDATE(),'" +
            		  cardTextField.getText().trim() + "','" + 
                      amount + "','" +
                      amount + "','存款')";
              if (db.updateSql(strSQL)>0) {
              	  Select select = new Select();
            	  ada.dispose();
            	  if (priv.equals("客户")) new Notify(null, "存款成功！");
            	  else {;}
                  }
              else {
            	  new Notify(null, "存款失败！");}
            }
            else {
              new Notify(null, "开户失败！");
            }
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
}
