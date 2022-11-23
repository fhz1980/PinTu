package JieMian;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import gongJu.JDBCUtils;
import gongJu.NickName;
import layout.TableLayout;

public class JieMian2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
   public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JieMian2 frame = new JieMian2();
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
	public JieMian2() {
		double fill = TableLayout.FILL ;
		double [][] size = {{fill,fill,fill,fill,fill,fill,fill,fill,fill,fill,fill},
							{fill,fill,fill,fill,fill,fill,fill,fill,fill}} ;
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);		
//		setBounds(0,0,1248,900);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		contentPane = new JPanel();
		contentPane.setLayout(new TableLayout(size));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel jLabel =new JLabel("请给自己取一个名字吧",SwingUtilities.CENTER) ;
		contentPane.add(jLabel,"1,1,9,4");
		jLabel.setFont(new Font("宋体",Font.PLAIN,99));
		
		String name = NickName.generateName() ;
		JTextField jTextField =new JTextField(name) ;
		jTextField.setFont(new Font("宋体",Font.PLAIN,60));
		contentPane.add(jTextField,"2,4,8,4") ;
		
		JButton jButton = new JButton("注册") ;
		jButton.setBackground(Color.yellow);
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameString  = jTextField.getText()  ;
				try {
					Connection connection =JDBCUtils.getConnection() ;
					Statement statement =connection.createStatement() ;
					String sql = "select Uname from user " ;
					ResultSet rs = statement.executeQuery(sql) ;
					int n = 0 ; //哨兵
					while(rs.next()) {
						if(rs.getString("Uname").equals(nameString)) {
							JOptionPane.showMessageDialog(JieMian2.this,"名字已经被注册，请换一个名字吧");
							n=1 ;
							break ;
						}
					}
					
					if(n ==0) {
						String sql1= "insert into user(Uno,Uname) values(null,'"+nameString+"')" ;
						statement.executeUpdate(sql1) ;
						JOptionPane.showMessageDialog(JieMian2.this,"注册成功！");

					}
					
					JDBCUtils.close(statement, connection,rs);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				new JieMian3() ;
				JieMian2.this.dispose();

			}
		});
		jButton.setFont(new Font("宋体",Font.PLAIN,60));
		contentPane.add(jButton,"4,6,6,6");
		
		
		
		
	}

}
