package JieMian;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import course.Course2;
import layout.TableLayout;

public class JieMian3 extends JFrame {

	private JPanel contentPane;
	private ArrayList isdoArrayList = new ArrayList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JieMian3 frame = new JieMian3();
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
	public JieMian3() {
		isdoArrayList.add(1);
		double fill = TableLayout.FILL ;
		double [][] size = {{20,fill,fill,fill,fill,fill,fill,fill,fill,fill,20},
				{20,fill,fill,fill,fill,fill,fill,fill,fill,fill,20}} ;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setBounds(0,0,1248,900);
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
//		int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-getWidth())/2 ;
//		int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-getHeight())/2 ;
//		setLocation(x,y);
		System.out.println(this.getBounds());
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		setContentPane(contentPane);
		getContentPane().setLayout(new TableLayout(size));

		contentPane.setBackground(Color.white);
		JButton btnNewButton = new JButton("\u5355\u4EBA");//单人
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GuanQia(JieMian3.this.isdoArrayList,false) ;
				JieMian3.this.dispose();

			}
		});

		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 50));
		btnNewButton.setBackground(Color.yellow);
		btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		contentPane.add(btnNewButton,"4,6,6,6");

//		JButton btnNewButton_1 = new JButton("\u591A\u4EBA");//多人
//		btnNewButton_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(JieMian3.this, "双人游戏尚未开发！");
//			}
//		});
//		btnNewButton_1.setBackground(Color.yellow);
//		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 50));
//		btnNewButton_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		contentPane.add(btnNewButton_1,"6,6,7,6");

		JButton btnNewButton_2 = new JButton("\u6559\u7A0B");//新手教程按钮
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showConfirmDialog(contentPane, "是否进入新手教程?","新手教程",JOptionPane.YES_NO_OPTION);
				//这里有int返回值的
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Course2 frame = new Course2();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				JieMian3.this.dispose();
			}
		});
		btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 40));
		btnNewButton_2.setBackground(Color.CYAN);

		btnNewButton_2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnNewButton_2,"9,1,9,1");

		JLabel lblNewLabel = new JLabel("\u8FDD\u7AE0\u7EA0\u9519\u4E92\u52A8\u4F53\u611F\u8BBE\u5907",SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 99));
		contentPane.add(lblNewLabel,"1,0,9,6");



		JButton btnNewButton_3 = new JButton("\u51A0\u519B\u699C");//冠军榜
//		btnNewButton_3.setBackground(Color.orange);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GuanJunBang() ;
			}
		});
		btnNewButton_3.setFont(new Font("宋体", Font.PLAIN, 30));
		btnNewButton_3.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnNewButton_3.setBackground(Color.CYAN);

		contentPane.add(btnNewButton_3,"7,9,7,9");

		JButton btnNewButton_4 = new JButton("\u6392\u884C\u699C");//排行榜
//		btnNewButton_4.setBackground(Color.orange);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PaiHangBang() ;
			}
		});
		btnNewButton_4.setFont(new Font("宋体", Font.PLAIN, 30));
		btnNewButton_4.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnNewButton_4.setBackground(Color.CYAN);

		contentPane.add(btnNewButton_4,"9,9,9,9");
		setVisible(true);

	}
}
