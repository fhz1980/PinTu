package JieMian;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import addinformation.AddData2;

public class JieMian1 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JieMian1 frame = new JieMian1();
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
	public JieMian1() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		int screenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
		int screenHeight = (int) screensize.getHeight();//获得屏幕得高1080
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		
		contentPane = (JPanel) this.getContentPane() ;
		
		contentPane.setLayout(new BorderLayout());
		
		JLabel jLabel = new JLabel("",SwingUtilities.CENTER) ;
		jLabel.setText("<html><body>违章纠错互动体感设备<br><br>&nbsp&nbsp</body></html>");
		jLabel.setFont(new Font("宋体",Font.PLAIN,99)) ;
		JLabel jLabel2 = new JLabel("单人模式");
		jLabel2.setBounds((int)(screenWidth/2-200),(int)(screenHeight/3*2)-100,420, 200);
		jLabel2.setFont(new Font("宋体",Font.PLAIN,99));
		jLabel2.setBorder(BorderFactory.createMatteBorder(10,10, 10, 10, Color.YELLOW));
		jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

		contentPane.add(jLabel2);
		Button addInformationButton = new Button("添加数据");
		addInformationButton.setBounds(screenWidth-200,30, 136, 68);
		addInformationButton.setForeground(Color.BLACK);
		addInformationButton.setBackground(Color.CYAN);
		addInformationButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		addInformationButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(addInformationButton);
		addInformationButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new AddData2();
				JieMian1.this.dispose();
				
			}
		});
		
		contentPane.add(jLabel,BorderLayout.CENTER) ;
		contentPane.setBackground(Color.white);
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new JieMian2_2() ;
				JieMian1.this.dispose();
			}

		});
		setVisible(true);
	}

}
