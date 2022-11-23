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
import playgamemysql.PlayGameMysql;
import javax.swing.SwingConstants;

public class JieMian2_2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
   public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JieMian2_2 frame = new JieMian2_2();
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
	public JieMian2_2() {
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
		
		JLabel jLabel =new JLabel("������һ����ϷID",SwingUtilities.CENTER) ;
		contentPane.add(jLabel,"1,1,9,4");
		jLabel.setFont(new Font("����",Font.PLAIN,99));
		
		String name = NickName.generateName() ;
		JTextField jTextField =new JTextField(name) ;
		jTextField.setFont(new Font("����",Font.PLAIN,60));
		contentPane.add(jTextField,"2,4,8,4") ;
		
		JButton jButton = new JButton("��½") ;
		jButton.setBackground(Color.yellow);
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameString  = jTextField.getText();
				PlayGameMysql playGameMysql = new PlayGameMysql();
				boolean exist = false;
				try {
					exist = playGameMysql.checkNameExist(nameString);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(exist) {
					try {
						playGameMysql.updateStar(nameString);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
				}else {
					try {
						playGameMysql.savePlayerNmae(nameString);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(JieMian2_2.this,"��½�ɹ���");
				new JieMian3() ;
				JieMian2_2.this.dispose();

			}
		});
		jButton.setFont(new Font("����",Font.PLAIN,60));
		contentPane.add(jButton,"4,7,6,7");		
		JLabel propmtJLabel = new JLabel();
		propmtJLabel.setForeground(Color.ORANGE);
		propmtJLabel.setFont(new Font("΢���ź�", Font.BOLD, 30));
		propmtJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		String aString = "��ʾ����ǰIDΪ���ID������Ѿ�ע��ID������ԭID��";
		propmtJLabel.setText(aString);
		
		contentPane.add(propmtJLabel,"1,5,9,5");
		
	}

}

