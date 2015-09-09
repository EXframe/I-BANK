import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class Help extends JFrame implements ActionListener{
	DBManager db = new DBManager();
	ResultSet rs;
	Container c2;
	JFrame help;
	JPanel panel1;
	JButton jb2;
	JLabel backlabel, upper;
	public Help(){	
		ImageIcon background = new ImageIcon(getClass().getResource("help.jpg"));
        backlabel = new JLabel(background);
        backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());		
	        
		help = new JFrame("°ïÖú");
		help.setLocation(MainFrame.Locatewidth, MainFrame.Locateheight);
        help.setUndecorated(true);
        help.setSize(1120, 630);
        help.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        upper= new JLabel(MainFrame.privelege+" "+MainFrame.card, JLabel.LEFT);
        upper.setBounds(5, 0, 300, 25);
        upper.setFont(new Font("Î¢ÈíÑÅºÚ",Font.ITALIC,16));
        upper.setForeground(Color.WHITE);
        
        c2 = help.getContentPane();
        c2.setLayout(null);
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
        
        JLabel info1 = new JLabel("×ð¾´µÄ"+MainFrame.privelege);
        info1.setBounds(55, 230, 300, 25);
        info1.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,15));
        info1.setForeground(Color.WHITE);
        
        JLabel info2 = new JLabel(MainFrame.name+"£¬ÄúºÃ£¡");
        info2.setBounds(55, 260, 300, 25);
        info2.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,15));
        info2.setForeground(Color.WHITE);
        
        JLabel info3 = new JLabel("Óà¶î£º"+infob);
        info3.setBounds(55, 290, 300, 25);
        info3.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,15));
        info3.setForeground(Color.RED);
        panel1.add(info1);
        panel1.add(info2);
        panel1.add(info3);
        
        jb2 = new JButton("·µ»Ø");
        jb2.setBounds(970, 550, 110, 30);
        jb2.addActionListener(this); 
        jb2.setOpaque(false);
        jb2.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,18));
        jb2.setForeground(new Color(100,81,40));
        jb2.setContentAreaFilled(false);
        jb2.setFocusPainted(false);     
        
        panel1.add(upper);
        panel1.add(info1);
        panel1.add(info2);
        panel1.add(info3);
        panel1.add(jb2);      
        c2.add(panel1);
        c2.add(backlabel);
                    
        help.setVisible(true);
	}       
	 
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb2) {        
			Select select  = new Select();
			help.dispose();
        }
	}
}