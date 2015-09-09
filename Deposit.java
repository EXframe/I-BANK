import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.lang.Float;
import java.math.BigDecimal;
import java.sql.*;

public class Deposit extends JFrame implements ActionListener {
  DBManager db = new DBManager();
  ResultSet rs;
  JFrame deposit = new JFrame("���");  
  JPanel panel1;
  JLabel backlabel, upper;
  JTextField cardTextField, amountTextField;
  Container c;
  JButton addBtn, exitBtn;
  String balance, strSQL;
  BigDecimal amounttemp, balancetemp;

  public Deposit() {
	ImageIcon background;
    background = new ImageIcon(getClass().getResource("deposit.jpg"));
    backlabel = new JLabel(background);
    backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());	
      
    deposit.setLocation(MainFrame.Locatewidth, MainFrame.Locateheight);
    deposit.setUndecorated(true);
    deposit.setSize(1120, 630);
    deposit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    upper= new JLabel(MainFrame.privelege+" "+MainFrame.card, JLabel.LEFT);
    upper.setBounds(5, 0, 300, 25);
    upper.setFont(new Font("΢���ź�",Font.ITALIC,16));
    upper.setForeground(Color.WHITE);
    
    c = deposit.getContentPane();
    c.setLayout(null);
    panel1 = new JPanel();
    panel1.setLayout(null);
    panel1.setSize(1120, 630);
    panel1.setLocation(0, 0);
    panel1.setOpaque(false);
    
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

    if (MainFrame.privelege.equals("���˿ͻ�")) this.cardTextField = new JTextField(MainFrame.card);
    else this.cardTextField = new JTextField(16); 
    if (MainFrame.privelege.equals("���˿ͻ�")) {
    	cardTextField.setEnabled(false);
    	cardTextField.setBackground(Color.WHITE);
    }

    addBtn = new JButton("����ִ��");
    addBtn.setBounds(550, 410, 110, 30);
    addBtn.addActionListener(this);
    addBtn.setOpaque(false);
    addBtn.setFont(new Font("΢���ź�",Font.BOLD,18));
    addBtn.setForeground(Color.RED);
    addBtn.setContentAreaFilled(false);
    addBtn.setFocusPainted(false);
    exitBtn = new JButton("ȡ��");
    exitBtn.setBounds(680, 410, 110, 30);
    exitBtn.addActionListener(this);
    exitBtn.setOpaque(false);
    exitBtn.setFont(new Font("΢���ź�",Font.BOLD,18));
    exitBtn.setForeground(new Color(100,81,40));
    exitBtn.setContentAreaFilled(false);
    exitBtn.setFocusPainted(false);

    cardTextField.setBounds(585, 255, 200, 25);
    cardTextField.setBorder(null);
    cardTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
    amountTextField =  new JTextField(20);
    amountTextField.setBounds(585, 328, 200, 25);
    amountTextField.setBorder(null);
    amountTextField.setFont(new Font("΢���ź�",Font.BOLD,15));

    panel1.add(upper);
    panel1.add(cardTextField);
    panel1.add(amountTextField);
    panel1.add(addBtn);
    panel1.add(exitBtn);
    c.add(panel1);
    c.add(backlabel);
    
    deposit.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
	if (e.getSource() == exitBtn) {
      Select s = new Select();
      deposit.dispose();
    }
    else if (e.getSource() == addBtn) {    
      int pot = amountTextField.getText().indexOf(".");
      int len = amountTextField.getText().length();
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
      
      if (cardTextField.getText().trim().equals("")) new Notify(null, "���Ų���Ϊ�գ�");
      else if (stattemp.equals("����")) new Notify(null, "����Ϊ��ע�����˻���");
      else if (privtemp.equals("����ְԱ")||privtemp.equals("���о���")) new Notify(null, "����ΪְԱ���!");
      else if (amountTextField.getText().trim().equals("")) new Notify(null, "����Ϊ�գ�");
      else if (pot>0&&(len-pot>3)) new Notify(null, "���������λС�����ڣ�");    
      else {
        try {
        	strSQL= "select balance from deposit where card='" + cardTextField.getText().trim() + "' order by ddate";  
        	if (!db.getResult(strSQL).first()) {
        		new Notify(null, "���Ų����ڣ�");
        	}
        	else{
        	    rs = db.getResult(strSQL); 
                rs.last();
                balance=rs.getString("balance");
            amounttemp = new BigDecimal(amountTextField.getText().trim());
            balancetemp = new BigDecimal(balance); 
            balance = balancetemp.add(amounttemp).toString();
            strSQL = "insert into deposit values (GETDATE(),'"+cardTextField.getText().trim()+"','"
                    + amountTextField.getText().trim() + "','" + balance + "','���')";	
        	
          if (db.updateSql(strSQL)!=-1) {
            new Notify(null, "�����ɹ���");
            Select select = new Select();
            deposit.dispose();
          }
          else {
            new Notify(null, "����ʧ�ܣ�");
          }
          db.closeConnection();
        }
        }
        catch (Exception ex) {
          System.out.println(ex.toString());
        }
      }

    }
  }
}
