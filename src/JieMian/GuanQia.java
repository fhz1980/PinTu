package JieMian;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import gongJu.JDBCUtils;
import layout.TableLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuanQia extends JFrame {

	private JPanel contentPane;
	private int allcount=0 ;//总共通过了多少关
	private String nameString =null;
	private ArrayList isdoArrayList ;
	private boolean reward;
	/**
	 * Launch the application.
	 */
 public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArrayList arrayList = new ArrayList();
					arrayList.add(1);
					GuanQia frame = new GuanQia(arrayList,false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuanQia(ArrayList isdoArrayList,boolean reward) {
		this.isdoArrayList = isdoArrayList;
		this.reward = reward;
		double fill = TableLayout.FILL ;
		double [][] size = {{60,fill,fill,fill,fill,fill,fill,fill,fill,fill,60},
							{60,fill,100}} ;
		setTitle("关卡");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900,550);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
//		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setLayout(new TableLayout(size));
		contentPane.setBackground(Color.white);
		JLabel jLabel = new JLabel("默认") ;//右上角名字

		try {
			Connection connection  = JDBCUtils.getConnection() ;
			Statement statement = connection.createStatement() ;
			String sql = "select *from user order by Uno DESC limit 1" ;
			ResultSet resultSet = statement.executeQuery(sql) ;
			resultSet.next() ;
			nameString =resultSet.getString("Uname") ;
			jLabel.setText(nameString);
			
			JDBCUtils.close(statement, connection,resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jLabel.setFont(new Font("宋体",Font.BOLD,30));
		contentPane.add(jLabel,"9,0,10,0") ;
		
		JPanel jPanel = new JPanel() ;
		jPanel.setLayout(new FlowLayout(FlowLayout.LEFT,30,30));
		jPanel.setBackground(Color.white);			
		int i = 1;//总共的关数
		int H =0 ;//高度
		
		try {
			Connection connection  = JDBCUtils.getConnection() ;
			Statement statement = connection.createStatement() ;
			String sql = "select DISTINCT difficult from relevance order by difficult" ;//==============================================
			ResultSet resultSet = statement.executeQuery(sql) ;
			
			while(resultSet.next()) {
				String s1="关卡"+i ;
				String s2="难度"+resultSet.getInt("difficult")+"☆" ;
				JLabel jLabel1 = new JLabel("",SwingUtilities.CENTER) ;
				
				jLabel1.setText("<html><body>"+s1+"<br>"+s2+"<body></html>");
				jLabel1.setPreferredSize(new Dimension(180,100));
				jLabel1.setFont(new Font("宋体",Font.PLAIN,40));
				jLabel1.setOpaque(true);
				if(isdoArrayList.size()>=i) {
					jLabel1.setBackground(Color.green);
					allcount++ ;
				}
				
				Border border =BorderFactory.createLineBorder(Color.black) ;
				jLabel1.setBorder(border);
				jPanel.add(jLabel1) ;
				H+=60;
				i++ ;			
			}
			
			JDBCUtils.close(statement, connection,resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		JScrollPane jScrollPane = new JScrollPane(jPanel) ;

		jPanel.setPreferredSize(new Dimension(jScrollPane.getWidth(),H));
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED) ;
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(jScrollPane,"1,1,9,1") ;
		
		JButton jButton  = new JButton("开始") ;
		jButton.setBackground(Color.orange);
		
		jButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					new GuanQiaJieShao(allcount,nameString,GuanQia.this.isdoArrayList,GuanQia.this.reward) ;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GuanQia.this.dispose();

			}
		});
		
		jButton.setFont(new Font("宋体",Font.BOLD,40));
		jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(jButton,"4,2,6,2");
	}

}
