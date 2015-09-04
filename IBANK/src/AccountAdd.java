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
	if (priv.equals("�ͻ�")) privsend = "���˿ͻ�";
	else privsend = "����ְԱ";
	ImageIcon background;
	if (privilege.equals("�ͻ�")) background = new ImageIcon(getClass().getResource("custom_create.jpg"));
	else background = new ImageIcon(getClass().getResource("clerk_create.jpg"));
	backlabel = new JLabel(background);
	backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());
	
	ada = new JFrame(priv+"����");	      
	ada.setLocation(MainFrame.Locatewidth, MainFrame.Locateheight);
	ada.setUndecorated(true);
	ada.setSize(1120, 630);
	ada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    
	upper= new JLabel(MainFrame.privelege+" "+MainFrame.card, JLabel.LEFT);
	upper.setBounds(5, 0, 300, 25);
	upper.setFont(new Font("΢���ź�",Font.ITALIC,16));
	upper.setForeground(Color.WHITE);
     
    cardTextField = new JTextField(20);
    cardTextField.setBounds(615, 157, 200, 25);
    cardTextField.setBorder(null);
    cardTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
    nameTextField = new JTextField(20);
    nameTextField.setBounds(615, 215, 200, 25);
    nameTextField.setBorder(null);
    nameTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
    telTextField = new JTextField(20);
    telTextField.setBounds(615, 275, 200, 25);
    telTextField.setBorder(null);
    telTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
    IDTextField = new JTextField(20);
    IDTextField.setBounds(615, 333, 200, 25);
    IDTextField.setBorder(null);
    IDTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
    passwordTextField =  new JPasswordField(20);
    passwordTextField.setBounds(615, 395, 200, 25);
    passwordTextField.setBorder(null);
    passwordTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
    passwordConfirmTextField =  new JPasswordField(20);
    passwordConfirmTextField.setBounds(615, 450, 200, 25);
    passwordConfirmTextField.setBorder(null);
    passwordConfirmTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
    
    if (privilege.equals("�ͻ�")){
    	amountTextField = new JTextField(20);
        amountTextField.setBounds(615, 512, 200, 25);
        amountTextField.setBorder(null);
        amountTextField.setFont(new Font("΢���ź�",Font.BOLD,15));
        }
    else {;}
    
    addBtn = new JButton("����");
    addBtn.setBounds(950, 480, 110, 30);
    addBtn.addActionListener(this);
    addBtn.setOpaque(false);
    addBtn.setFont(new Font("΢���ź�",Font.BOLD,18));
    addBtn.setForeground(Color.RED);
    addBtn.setContentAreaFilled(false);
    addBtn.setFocusPainted(false);
    cancelBtn = new JButton("ȡ��");
    cancelBtn.setBounds(950, 520, 110, 30);
    cancelBtn.addActionListener(this);
    cancelBtn.setOpaque(false);
    cancelBtn.setFont(new Font("΢���ź�",Font.BOLD,18));
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
    if (privilege.equals("�ͻ�")) panel1.add(amountTextField);
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
    	if (priv.equals("�ͻ�")) {
          	pot = amountTextField.getText().indexOf(".");
            len = amountTextField.getText().length();
    	}
    	if (priv.equals("�ͻ�")) amount = amountTextField.getText().trim();
        else amount = "0.00";   	  
    	String strSQL = "select * from account where card='" + cardTextField.getText().trim() + "'";
    	
        if (cardTextField.getText().trim().equals("")) new Notify(null, "���Ų���Ϊ�գ�"); 
        else if (cardTextField.getText().length()!=8) new Notify(null, "����ֻ��Ϊ8λ��");
        else if (IDTextField.getText().length()!=18) new Notify(null, "���ֻ֤��Ϊ18λ��");
        else if (telTextField.getText().length()!=11) new Notify(null, "�绰ֻ��Ϊ11λ��");
        else if (new String(passwordTextField.getPassword()).trim().equals("")) new Notify(null, "���벻��Ϊ�գ�");
        else if (!new String(passwordTextField.getPassword()).trim().equals(new String(passwordConfirmTextField.getPassword()).trim())) new Notify(null, "������������벻һ�£�"); 
        else if (passwordTextField.getPassword().length!=6) new Notify(null, "����ֻ��Ϊ6λ��");
        else if (priv.equals("�ͻ�") && amountTextField.getText().trim().equals("")) new Notify(null, "����Ϊ�գ�");
        else if (priv.equals("�ͻ�") && (pot>0&&(len-pot>3))) new Notify(null, "���������λС�����ڣ�");     
        else {
          if (db.getResult(strSQL).first()) new Notify(null, "�����Ѿ����ڣ���ʹ����һ���ţ�");
          else {
            strSQL = "insert into account values(GETDATE(),'" +
                nameTextField.getText().trim() + "','" + 
                IDTextField.getText().trim() + "','" +
                telTextField.getText().trim() + "','" +
                cardTextField.getText().trim() + "','" +
                new String(passwordTextField.getPassword()).trim() + "','����','" + 
                privsend + "')";
            if (db.updateSql(strSQL)>0) {
              new Notify(null, "�����ɹ���");
              strSQL = "insert into deposit values(GETDATE(),'" +
            		  cardTextField.getText().trim() + "','" + 
                      amount + "','" +
                      amount + "','���')";
              if (db.updateSql(strSQL)>0) {
              	  Select select = new Select();
            	  ada.dispose();
            	  if (priv.equals("�ͻ�")) new Notify(null, "���ɹ���");
            	  else {;}
                  }
              else {
            	  new Notify(null, "���ʧ�ܣ�");}
            }
            else {
              new Notify(null, "����ʧ�ܣ�");
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
