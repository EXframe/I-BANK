import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

import java.util.Vector;

public class List extends JFrame implements ActionListener{
  DBManager db = new DBManager();
  ResultSet rs;
  Container c;
  String card, priv;
  boolean none = false;
  JTextField cardTextField;
  JFrame AcList = new JFrame("清单");
  JButton request = new JButton("查询");
  JButton home = new JButton("返回");
  JTable table = null;
  JPanel panel1;
  JLabel backlabel, upper;
  DefaultTableModel defaultModel = null;
  public List() {
	ImageIcon background;
    background = new ImageIcon(getClass().getResource("List.jpg"));
    backlabel = new JLabel(background);
    backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());	
        
    AcList.setLocation(MainFrame.Locatewidth, MainFrame.Locateheight);
    AcList.setUndecorated(true);
    AcList.setSize(1120, 630);
    AcList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
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
    cardTextField.setBounds(545, 140, 200, 25);
    cardTextField.setBorder(null);
    cardTextField.setFont(new Font("微软雅黑",Font.BOLD,15));
    
    c = AcList.getContentPane();
    c.setLayout(null);
    panel1 = new JPanel();
    panel1.setSize(1120, 630);
    panel1.setLocation(0, 0);
    panel1.setLayout(null);
    panel1.setOpaque(false);
    
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
    
    String[] name = {"时间", "发生金额", "余额","存取"};
    String[][] data = new String[0][0];
    defaultModel = new DefaultTableModel(data, name);
    DefaultTableCellRenderer render = new DefaultTableCellRenderer();
    render.setOpaque(false);
    table = new JTable (defaultModel) {   
    	public boolean isCellEditable(int row, int column) {     
    		return false;   
    		}  
    	};
    table.getColumnModel().getColumn(0).setMinWidth(180);
    table.getColumnModel().getColumn(3).setMaxWidth(50);
    table.setRowHeight(30);
    table.setDefaultRenderer(Object.class, render);
    table.setShowGrid(false);
    table.setOpaque(false);
    table.getTableHeader().setFont(new Font("微软雅黑",Font.BOLD,16)); 
    table.getTableHeader().setOpaque(false);
    table.getTableHeader().setForeground(new Color(225,53,53));
    table.getTableHeader().setBackground(new Color(64,131,255));
    table.setFont(new Font("微软雅黑",Font.ITALIC,16)); 
    table.setForeground(new Color(100,81,40));
    JScrollPane s = new JScrollPane();
    s.getViewport().setOpaque(false);
    s.setOpaque(false);
    s.setViewportView(table);
    s.setColumnHeaderView(table.getTableHeader());
    s.getColumnHeader().setOpaque(false);
    s.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new EmptyBorder(8, 8, 8, 8)));
    s.setBounds(400, 180, 550, 315);
    
    home.setBounds(965, 545, 110, 30);
    home.addActionListener(this);
    home.setOpaque(false);
    home.setFont(new Font("微软雅黑",Font.PLAIN,18));
    home.setForeground(new Color(100,81,40));
    home.setContentAreaFilled(false);
    home.setFocusPainted(false);
    
    request.setBounds(745, 140, 200, 25);
    request.addActionListener(this);
    request.setOpaque(false);
    request.setFont(new Font("微软雅黑",Font.PLAIN,18));
    request.setForeground(new Color(100,81,40));
    request.setContentAreaFilled(false);
    request.setFocusPainted(false);
    
    panel1.add(home);
    panel1.add(request);
    panel1.add(cardTextField);
    panel1.add(upper);
    panel1.add(s);
    c.add(panel1);
    c.add(backlabel);
    
    AcList.setVisible(true);
  }
  @Override
  public void actionPerformed(ActionEvent e) {
	  if (e.getSource() == home){
		  AcList.dispose();
		  Select select = new Select();
	  }	
	  else if (e.getSource() == request){
		  if (MainFrame.privelege.equals("个人客户")) card = MainFrame.card;
		  else card = cardTextField.getText().trim();
		  
		  try { 
			String strSQL = "select privelege from account where card='"+ card + "'";
			rs = db.getResult(strSQL);
			if(!rs.first()) {
				new Notify(null, "卡号不存在!");
				none = true;
			}
			else {
				priv = rs.getString("privelege").trim();
				none = false;
			}
		  }
		  catch (SQLException e1) {
			e1.printStackTrace(); 
		  }
		  if (none==true) {;}
		  else if (!priv.equals("个人客户") && MainFrame.privelege.equals("银行职员") && !card.equals(MainFrame.card)) new Notify(null, "只能查询客户和自己的清单!");
		  else {
		  DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		  int rows = tableModel.getRowCount();
		  if (rows>0) for (int i=0;i<rows;i++) tableModel.removeRow(0);
		  
		  try {  
			  String strSQL= "select balance from deposit where card='" + card + "'";
		        if(db.getResult(strSQL).first()) {
		        	String strSql = "select ddate,amount,balance,type from deposit where card='"+ card +"' order by ddate";
		  	        rs = db.getResult(strSql);
		  	        while (rs.next()) {
		  	          Vector insertRow = new Vector();
		  	          insertRow.addElement(rs.getString(1));
		  	          insertRow.addElement(rs.getString(2));
		  	          insertRow.addElement(rs.getString(3));
		  	          insertRow.addElement(rs.getString(4));
		  	          defaultModel.addRow(insertRow);
		  	        }
		  	        table.revalidate();
		        }
		        else new Notify(null, "此卡号不存在");
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
}