import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class Select extends JFrame implements ActionListener{
	DBManager db = new DBManager();
	ResultSet rs;
	Container c2;
	String infob;
	JFrame select;
	JPanel panel1;
	JButton jb2, d, d2, AcList, m, del, cuscreate, clerkcreate, list, flow, help, about;
	JLabel backlabel, upper;
	public Select(){	
		ImageIcon background = new ImageIcon(getClass().getResource("select.jpg"));
        backlabel = new JLabel(background);
        backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());		
	        
		select = new JFrame(MainFrame.privelege);
		select.setLocation(MainFrame.Locatewidth, MainFrame.Locateheight);
        select.setUndecorated(true);
        select.setSize(1120, 630);
        select.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        upper= new JLabel(MainFrame.privelege+" "+MainFrame.card, JLabel.LEFT);
        upper.setBounds(5, 0, 300, 25);
        upper.setFont(new Font("΢���ź�",Font.ITALIC,16));
        upper.setForeground(Color.WHITE);
        
        c2 = select.getContentPane();
        c2.setLayout(null);
        panel1 = new JPanel();
        panel1.setSize(1120, 630);
        panel1.setLocation(0, 0);
        panel1.setLayout(null);
        panel1.setOpaque(false);
       
    	infob = null;
        try {
        	String strSQL = "select balance from deposit where card='" + MainFrame.card + "' order by ddate";
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
        
        jb2 = new JButton("ע��");
        jb2.setBounds(60, 380, 110, 30);
        jb2.addActionListener(this); 
        jb2.setOpaque(false);
        jb2.setFont(new Font("΢���ź�",Font.PLAIN,18));
        jb2.setForeground(new Color(255,255,255));
        jb2.setContentAreaFilled(false);
        jb2.setFocusPainted(false);     
        
        // ��һ��
        if (MainFrame.privelege.equals("���˿ͻ�")) d = new JButton("���˴��");
        else d = new JButton("�ͻ����");
        d.setBounds(315, 130, 110, 30);
        d.addActionListener(this);
        d.setOpaque(false);
        d.setFont(new Font("΢���ź�",Font.PLAIN,18));
        d.setForeground(new Color(255,255,255));
        d.setContentAreaFilled(false);
        d.setFocusPainted(false);
        d.setHorizontalAlignment(JButton.LEFT); 
        
        if (MainFrame.privelege.equals("���˿ͻ�")) d2 = new JButton("����ȡ��");
        else d2 = new JButton("�ͻ�ȡ��");
        d2.setBounds(415, 130, 110, 30);
        d2.addActionListener(this);
        d2.setOpaque(false);
        d2.setFont(new Font("΢���ź�",Font.PLAIN,18));
        d2.setForeground(new Color(255,255,255));
        d2.setContentAreaFilled(false);
        d2.setFocusPainted(false);
        
        // �ڶ���
        list = new JButton("�˻��嵥");
        list.setBounds(315, 230, 110, 30);
        list.addActionListener(this);
        list.setOpaque(false);
        list.setFont(new Font("΢���ź�",Font.PLAIN,18));
        list.setForeground(new Color(255,255,255));
        list.setContentAreaFilled(false);
        list.setFocusPainted(false);
        
        flow = new JButton("�ʽ�����");
        flow.setBounds(415, 230, 110, 30);
        flow.addActionListener(this);
        flow.setOpaque(false);
        flow.setFont(new Font("΢���ź�",Font.PLAIN,18));
        flow.setForeground(new Color(255,255,255));
        flow.setContentAreaFilled(false);
        flow.setFocusPainted(false);
        
        if (!MainFrame.privelege.equals("���˿ͻ�")){
        AcList = new JButton("�û��б�");
        AcList.setBounds(515, 230, 110, 30);
        AcList.addActionListener(this);
        AcList.setOpaque(false);
        AcList.setFont(new Font("΢���ź�",Font.PLAIN,18));
        AcList.setForeground(new Color(255,255,255));
        AcList.setContentAreaFilled(false);
        AcList.setFocusPainted(false);
        }
        
        // ������
        m = new JButton("�޸�����");
        m.setBounds(315, 340, 110, 30);
        m.addActionListener(this);
        m.setOpaque(false);
        m.setFont(new Font("΢���ź�",Font.PLAIN,18));
        m.setForeground(new Color(255,255,255));
        m.setContentAreaFilled(false);
        m.setFocusPainted(false);
        
        if (!MainFrame.privelege.equals("���˿ͻ�")){
        del = new JButton("����");
        del.setBounds(415, 340, 110, 30);
        del.addActionListener(this);
        del.setOpaque(false);
        del.setFont(new Font("΢���ź�",Font.PLAIN,18));
        del.setForeground(new Color(255,255,255));
        del.setContentAreaFilled(false);
        del.setFocusPainted(false);
        }
        
        if (!MainFrame.privelege.equals("���˿ͻ�")){
        cuscreate = new JButton("�ͻ�����");
        cuscreate.setBounds(515, 340, 110, 30);
        cuscreate.addActionListener(this);
        cuscreate.setOpaque(false);
        cuscreate.setFont(new Font("΢���ź�",Font.PLAIN,18));
        cuscreate.setForeground(new Color(255,255,255));
        cuscreate.setContentAreaFilled(false);
        cuscreate.setFocusPainted(false);
        }
        
        if (MainFrame.privelege.equals("���о���")){
        clerkcreate = new JButton("ְԱ����");
        clerkcreate.setBounds(615, 340, 110, 30);
        clerkcreate.addActionListener(this);
        clerkcreate.setOpaque(false);
        clerkcreate.setFont(new Font("΢���ź�",Font.PLAIN,18));
        clerkcreate.setForeground(new Color(255,255,255));
        clerkcreate.setContentAreaFilled(false);
        clerkcreate.setFocusPainted(false);
        }
        
        // ������
        help = new JButton("ʹ�ð���");
        help.setBounds(315, 450, 110, 30);
        help.addActionListener(this);
        help.setOpaque(false);
        help.setFont(new Font("΢���ź�",Font.PLAIN,18));
        help.setForeground(new Color(255,255,255));
        help.setContentAreaFilled(false);
        help.setFocusPainted(false);
        
        about = new JButton("����");
        about.setBounds(415, 450, 110, 30);
        about.addActionListener(this);
        about.setOpaque(false);
        about.setFont(new Font("΢���ź�",Font.PLAIN,18));
        about.setForeground(new Color(255,255,255));
        about.setContentAreaFilled(false);
        about.setFocusPainted(false);
        
        panel1.add(upper);
        panel1.add(info1);
        panel1.add(info2);
        panel1.add(info3);
        panel1.add(d);
        panel1.add(d2);
        panel1.add(jb2);      
        panel1.add(m);
        
        if (!MainFrame.privelege.equals("���˿ͻ�")){
        panel1.add(del);
        panel1.add(cuscreate);
        panel1.add(AcList);
        }
        if (MainFrame.privelege.equals("���о���")){
        panel1.add(clerkcreate);
        }
        panel1.add(list);
        panel1.add(flow);
        panel1.add(help);
        panel1.add(about);
        c2.add(panel1);
        c2.add(backlabel);
                    
        select.setVisible(true);
	}       
	 
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb2) {        
			MainFrame mf  = new MainFrame();
			MainFrame.card = null;
			MainFrame.privelege = null;
			select.dispose();
        }
        else if (e.getSource() == d){     	
        	Deposit dep  = new Deposit();  
        	select.dispose(); 
        }	
        else if (e.getSource() == d2){	
        	Deposit2 dep2  = new Deposit2();  
        	select.dispose();
        }	
        else if (e.getSource() == AcList){	
        	AccountList acl  = new AccountList(); 
        	select.dispose();
        }
        else if (e.getSource() == m){
        	AccountModify acm  = new AccountModify(); 
        	select.dispose();
        }
        else if (e.getSource() == del){
        	AccountDel acd  = new AccountDel(); 
        	select.dispose();
        }
        else if (e.getSource() == cuscreate){
        	AccountAdd acd  = new AccountAdd("�ͻ�"); 
        	select.dispose();
        }
        else if (e.getSource() == clerkcreate){
        	AccountAdd acd  = new AccountAdd("ְԱ"); 
        	select.dispose();
        }
        else if (e.getSource() == list){
        	List acd  = new List(); 
        	select.dispose();
        }
        else if (e.getSource() == flow){
			Flow flow  = new Flow(); 
        	select.dispose();
        }
        else if (e.getSource() == help){
			Help help  = new Help(); 
        	select.dispose();
        }
        else if (e.getSource() == about){
			About about  = new About(); 
        	select.dispose();
        }
      }

}