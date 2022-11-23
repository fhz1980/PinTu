package JieMian;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import addinformation.AddData2;
import gongJu.JDBCUtils;
import layout.TableLayout;

public class GuanJunBang extends JFrame {

	private JPanel contentPane;
	private String firstName ;
	private String allstar ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuanJunBang frame = new GuanJunBang();
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
	public GuanJunBang() {
		double fill = TableLayout.FILL ;
		double [][] size = {{fill,fill,fill},{30,fill,fill,fill,100,100}} ;
		setTitle("冠军榜");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setSize(900,550);
		contentPane = new JPanel();
		contentPane.setLayout(new TableLayout(size));
		
		contentPane.setBackground(Color.white);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		Connection connection;
		Statement statement;
		ResultSet rs;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.createStatement() ;
			String sql = "select * from user order by Ustar DESC" ;
			rs = statement.executeQuery(sql) ;
			rs.next() ;
			firstName = rs.getString("Uname");
			allstar = "总星数:"+String.valueOf(rs.getInt("Ustar")) ;
			
			JDBCUtils.close(statement, connection,rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		JLabel lblNewLabel = new JLabel("\u7B2C\u4E00\u540D",SwingConstants.CENTER);
		lblNewLabel.setBounds(211, 33, 341, 129);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 90));
		contentPane.add(lblNewLabel,"1,1,1,1");
		
		JLabel lblNewLabel_1 = new JLabel("\u9648\u6653\u4F1F",SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(211, 156, 288, 300);
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 75));
		lblNewLabel_1.setText(firstName);
		contentPane.add(lblNewLabel_1,"1,2,1,2");
		
		JLabel lblNewLabel_2 = new JLabel("\u603B\u661F\u6570:66",JLabel.CENTER);
		lblNewLabel_2.setText(allstar);
		lblNewLabel_2.setBounds(193, 371, 348, 47);
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 50));
		Icon icon2 = new ImageIcon("configurationImage/2.jpg");
		lblNewLabel_2.setIcon(icon2);
		contentPane.add(lblNewLabel_2,"1,3,1,3");
		
		JLabel lblNewLabel_3 = new JLabel("",SwingConstants.CENTER);
		lblNewLabel_3.setIcon(new ImageIcon("configurationImage/1.jpg"));
		lblNewLabel_3.setBounds(575, 21, 125, 129);
		contentPane.add(lblNewLabel_3,"0,1,0,1");
		
		JLabel lblNewLabel_3_1 = new JLabel("",SwingConstants.CENTER);
		lblNewLabel_3_1.setIcon(new ImageIcon("configurationImage/1.jpg"));
		lblNewLabel_3_1.setBounds(58, 33, 125, 129);
		contentPane.add(lblNewLabel_3_1,"2,1,2,1");
		//退出
		JButton quitJButto = new JButton("退出");
		quitJButto.setBackground(Color.CYAN);
		quitJButto.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		quitJButto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(quitJButto,"1,4,1,4");
		quitJButto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GuanJunBang.this.dispose();
			}
		});


		setVisible(true);

	}

}
