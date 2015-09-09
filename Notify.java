import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

public class Notify extends JFrame implements ActionListener {
  Container c;
  JFrame notify = new JFrame("Ã· æ");
  JPanel panel1;
  JLabel backlabel, infoshow;
  JButton ok;
  static int notifywidth = (Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2;
  static int notifyheight = (Toolkit.getDefaultToolkit().getScreenSize().height - 300) / 2; 
  public Notify(Object control,String info) {
	ImageIcon background;
	background = new ImageIcon(this.getClass().getResource("notify.png"));
	backlabel = new JLabel(background);
	backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());
		      
	notify.setLocation(notifywidth, notifyheight);
	notify.setUndecorated(true);
	notify.setSize(400, 300);
	notify.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	notify.setAlwaysOnTop(true);
		    
    ok = new JButton("»∑∂®");
    ok.setBounds(150, 250, 110, 30);
    ok.addActionListener(this);
    ok.setOpaque(false);
    ok.setFont(new Font("Œ¢»Ì—≈∫⁄",Font.BOLD,18));
    ok.setForeground(new Color(100,81,40));
    ok.setContentAreaFilled(false);
    ok.setFocusPainted(false);
    
    infoshow = new JLabel(info, JLabel.CENTER);
    infoshow.setBounds(50, 130, 300, 25);
    infoshow.setFont(new Font("Œ¢»Ì—≈∫⁄",Font.BOLD,16));
    infoshow.setForeground(Color.RED);
    
    panel1 = new JPanel();
    panel1.setLayout(null);
    panel1.setSize(400, 300);
    panel1.setLocation(0, 0);
    panel1.setOpaque(false);
    panel1.add(ok);
    panel1.add(infoshow);

    c = notify.getContentPane();
    c.setLayout(null);
    c.add(panel1);
    c.add(backlabel);
    
    c.setBackground(null);
    notify.setBackground(new Color(0,0,0,0));
    notify.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == ok) {
    	notify.dispose();
    }
  }
}