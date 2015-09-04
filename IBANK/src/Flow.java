import javax.swing.*; 

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
 






















import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Flow extends JFrame implements ActionListener{
	DBManager db = new DBManager();
	ResultSet rs;
	JFrame flow = new JFrame("�ʽ�����");
    Container c;
	JTextField cardTextField = new JTextField(MainFrame.card,8);
	JTextField monthselect;
    JLabel backlabel, upper, info1, info2, info3;
    JPanel p1;
	JButton home;
	JButton request;
	String card,month,d,d2,priv;
	Double dd, dd2;
	public Flow(){
		ImageIcon background;
	    background = new ImageIcon(getClass().getResource("Flow.jpg"));
	    backlabel = new JLabel(background);
	    backlabel.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());	
	        
	    flow.setLocation(MainFrame.Locatewidth, MainFrame.Locateheight);
	    flow.setUndecorated(true);
	    flow.setSize(1120, 630);
	    flow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
        upper= new JLabel(MainFrame.privelege+" "+MainFrame.card, JLabel.LEFT);
        upper.setBounds(5, 0, 300, 25);
        upper.setFont(new Font("΢���ź�",Font.ITALIC,16));
        upper.setForeground(Color.WHITE);

        if (MainFrame.privelege.equals("���˿ͻ�")) {
        	cardTextField.setEnabled(false);
        	cardTextField.setBackground(Color.WHITE);
        } 
        cardTextField.setBounds(375, 505, 120, 25);
        cardTextField.setBorder(null);
        cardTextField.setFont(new Font("΢���ź�",Font.BOLD,15));   
        
        monthselect = new JTextField("3");
        monthselect.setBounds(440, 544, 20, 23);
        monthselect.setBorder(null);
        monthselect.setFont(new Font("΢���ź�",Font.BOLD,15));

	    home = new JButton("����");
        home.setBounds(965, 545, 110, 30);
        home.addActionListener(this);
        home.setOpaque(false);
        home.setFont(new Font("΢���ź�",Font.PLAIN,18));
        home.setForeground(new Color(100,81,40));
        home.setContentAreaFilled(false);
        home.setFocusPainted(false);
        
        request = new JButton("��ѯ");
        request.setBounds(870, 545, 90, 30);
        request.addActionListener(this);
        request.setOpaque(false);
        request.setFont(new Font("΢���ź�",Font.PLAIN,18));
        request.setForeground(new Color(100,81,40));
        request.setContentAreaFilled(false);
        request.setFocusPainted(false);
	    
	    c = flow.getContentPane();
	    c.setLayout(null);
	    p1 = new JPanel();
	    p1.setSize(1120, 630);
	    p1.setLocation(0, 0);
	    p1.setLayout(null);
	    p1.setOpaque(false);
	    
	    String infob = "";
        try {
        	String strSQL = "select balance from deposit where card='" + MainFrame.card + "' order by ddate";
        	rs = db.getResult(strSQL);
        	rs.last();
            infob = rs.getString("balance");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        info1 = new JLabel("�𾴵�"+MainFrame.privelege);
        info1.setBounds(55, 230, 300, 25);
        info1.setFont(new Font("΢���ź�",Font.BOLD,15));
        info1.setForeground(Color.WHITE);
        
        info2 = new JLabel(MainFrame.name+"�����ã�");
        info2.setBounds(55, 260, 300, 25);
        info2.setFont(new Font("΢���ź�",Font.BOLD,15));
        info2.setForeground(Color.WHITE);
        
        info3 = new JLabel("��"+infob);
        info3.setBounds(55, 290, 300, 25);
        info3.setFont(new Font("΢���ź�",Font.BOLD,15));
        info3.setForeground(Color.RED);
        p1.add(info1);
        p1.add(info2);
        p1.add(info3);
        p1.add(home);
        p1.add(request);
        p1.add(upper);
        p1.add(monthselect);
        p1.add(cardTextField);
        c.add(p1);
        c.add(backlabel);
        flow.setVisible(true);
    }

    private static JFreeChart createChart(PieDataset dataset, String monthx) {
         
        JFreeChart chart = ChartFactory.createPieChart(
            " ",            // chart title
            dataset,            // data
            true,               // include legend
            true,
            false
            );
         
        TextTitle title = new TextTitle("���"+monthx+"���µĴ�ȡ�����", new Font("΢���ź�", Font.PLAIN, 30));
        title.setPaint(new Color(100,81,40));
        chart.setTitle(title);
        chart.setBorderVisible(false);  
        chart.setBackgroundPaint(null);  
        chart.setBackgroundImageAlpha(0.0f);  
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setLabelFont(new Font("΢���ź�", Font.PLAIN, 12));
        plot.setNoDataMessage("���ݲ�����");
        plot.setForegroundAlpha(0.9f);   //��ͼ͸����
        plot.setBackgroundAlpha(0.0f);   //����͸����
        plot.setBackgroundPaint(null);
        plot.setCircular(false);  // Բ�λ�����Բ
        plot.setOutlinePaint(null); // �ޱ߿�
        plot.setLabelGap(0.02);
        return chart;    
    }

    public JFreeChart getchart(String cardx, String monthx) {
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	try {	    	
    		String strSQL = "select sum(amount) ,card from deposit where  type = 'ȡ��' and card = "+cardx+" and (ddate between DateAdd(Month,-"+monthx+",getdate()) and getdate()) group by card"
	    			+ " union "
	    			+ "select sum(amount) ,card  from deposit where  type = '���' and card = "+cardx+" and (ddate between DateAdd(Month,-"+monthx+",getdate()) and getdate()) group by card";
		    rs = db.getResult(strSQL);
		    rs.first();
	    	d2 = rs.getString(1);
	    	rs.last();
		    d= rs.getString(1);
		    dd = Double.parseDouble(d);
		    dd2 = Double.parseDouble(d2);
		    dataset.setValue("��� ��"+dd, dd);
	        dataset.setValue("ȡ�� ��"+dd2, dd2);
	        
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	JFreeChart chart = createChart(dataset, monthx);
    	return chart;
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == home) {        
			Select s  = new Select();
			flow.dispose();
        }
		else if (e.getSource() == request) {        
			priv="";
			if (MainFrame.privelege.equals("���˿ͻ�")) card = MainFrame.card;
			else card = cardTextField.getText().trim();
			  
			try { 
				String strSQL = "select privelege from account where card='"+ card + "'";
				rs = db.getResult(strSQL);
				rs.first();
				priv = rs.getString("privelege").trim();
			  }
			  catch (SQLException e1) {
				e1.printStackTrace(); 
			  }
			if (priv.equals("")) new Notify(null, "�˻�������!");
			else if (!priv.equals("���˿ͻ�") && MainFrame.privelege.equals("����ְԱ") && !card.equals(MainFrame.card)) new Notify(null, "ֻ�ܲ�ѯ�ͻ����Լ����嵥!");
			else {
			int val=Integer.parseInt(monthselect.getText().trim());
			if (val<=0) new Notify(null, "�·ݱ������0!");
			else {
			JFreeChart chart = getchart(cardTextField.getText().trim(), monthselect.getText().trim());
	        ChartPanel localChartPanel = new ChartPanel(chart, false); 
	        localChartPanel.setOpaque(false);
	        localChartPanel.setBounds(380, 100, 600, 430);
	        p1.removeAll();
	        p1.repaint();
	        c.removeAll();
	        c.repaint();
	        flow.setVisible(false);
	        flow.setVisible(true);
	        p1.add(localChartPanel);
	        p1.add(info1);
	        p1.add(info2);
	        p1.add(info3);
	        p1.add(home);
	        p1.add(request);
	        p1.add(upper);
	        p1.add(monthselect);
	        p1.add(cardTextField);
	        c.add(p1);
	        c.add(backlabel);
	        flow.setVisible(false);
	        flow.setVisible(true);
			}
		}
      }		  
	}     
}
