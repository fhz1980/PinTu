package defeated;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import JieMian.JieMian1;
import JieMian.PaiHangBang;
import aftersuccess.Aftersuccess;
import imagedivision.SeeRightImage2;
import playgamemysql.PlayGameMysql;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class PlayDefeated extends JFrame {

	private JPanel contentPane;
	private  int screenWidth = 0;
	private int screenHeight =0;
	private int backgroundWidth = 0;
	private int backgroundHeight = 0;
	private int getGrade ;
	private String playerNameString;
	private PaiHangBang pai;//查看冠军榜
	private JFrame seeAJFrame;//查看答案

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayDefeated frame = new PlayDefeated("configurationImage/A1reference.jpg",8,"知足的高手");
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
	public PlayDefeated(String imageErrorUrlString,int getGrade,String playerNameString) {
		getContentPane().setEnabled(false);
		getContentPane().setForeground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) dimension.getWidth();//获得屏幕得宽1920
		screenHeight = (int) dimension.getHeight();//获得屏幕得高1080
		String backgroundImageString = "configurationImage/playdefeated.jpg";
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new FileInputStream(backgroundImageString));
			backgroundWidth = bufferedImage.getWidth();
			backgroundHeight = bufferedImage.getHeight();
			System.out.println("background:    "+backgroundWidth+backgroundHeight);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getGrade = getGrade;
		this.playerNameString = playerNameString;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int)(screenWidth/2)-(int)(backgroundWidth/2), (int)(screenHeight/2)-(int)(backgroundHeight/2), backgroundWidth, backgroundHeight+40);
		
		
		ImageIcon icon = new ImageIcon("configurationImage/playdefeated.jpg");
		JLabel backJLabel = new JLabel(icon);
		backJLabel.setBounds(0, 0, backgroundWidth, backgroundHeight);
		getLayeredPane().setLayout(null);
		getLayeredPane().add(backJLabel,new Integer(Integer.MIN_VALUE));		
		contentPane = (JPanel)this.getContentPane();
		contentPane.setLayout(null);
		
		ImageIcon plateImageIcon = new ImageIcon("configurationImage/defeated7.png");
		JLabel plateJlable = new JLabel(plateImageIcon);
		plateJlable.setBounds(55, 13, 400, 635);
		getContentPane().add(plateJlable);
		
		
		
		JLabel Label = new JLabel("New label");
		Label.setBackground(new Color(0, 255, 255));
		Label.setHorizontalAlignment(SwingConstants.LEFT);
		Label.setFont(new Font("微软雅黑", Font.BOLD | Font.ITALIC, 29));
		Label.setForeground(Color.RED);
		Label.setBounds(469, 187, 441, 77);
		Label.setText("1.\u627E\u5230\u5168\u90E8\u8FDD\u7AE0\u70B9 ");
		getContentPane().add(Label);
		
		Button nextCustomsPassButton_2 = new Button("\u67E5\u770B\u6392\u540D");
		nextCustomsPassButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pai==null||!pai.isVisible()){
					pai =new PaiHangBang();
				}
			}
		});
		nextCustomsPassButton_2.setForeground(Color.BLACK);
		nextCustomsPassButton_2.setFont(new Font("微软雅黑", Font.BOLD, 20));
		nextCustomsPassButton_2.setBackground(Color.CYAN);
		nextCustomsPassButton_2.setBounds(902, 506, 134, 77);
        nextCustomsPassButton_2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		getContentPane().add(nextCustomsPassButton_2);
		
		Button selectCustomsPassButton_2_1 = new Button("\u9000\u51FA");
		selectCustomsPassButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteFile();
				new JieMian1();
				PlayDefeated.this.dispose();
			}
		});
		selectCustomsPassButton_2_1.setForeground(Color.BLACK);
		selectCustomsPassButton_2_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
		selectCustomsPassButton_2_1.setBackground(Color.ORANGE);
		selectCustomsPassButton_2_1.setBounds(1008, 605, 134, 43);
        selectCustomsPassButton_2_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		getContentPane().add(selectCustomsPassButton_2_1);
		
		
		JLabel starJlable_1 = new JLabel("失");
		starJlable_1.setFont(new Font("微软雅黑", Font.BOLD, 120));
		starJlable_1.setBounds(627, 13, 144, 151);
		getContentPane().add(starJlable_1);
		
		JLabel starJlable_2 = new JLabel("败");
		starJlable_2.setFont(new Font("微软雅黑", Font.BOLD, 120));
		starJlable_2.setBounds(800, 13, 144, 151);
		getContentPane().add(starJlable_2);
		
		Button answerButton_1 = new Button("\u67E5\u770B\u7B54\u6848");
		answerButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//进行数据库查询找到正确图片路径
						PlayGameMysql playGameMysql = new PlayGameMysql();
						String rightAndErrorImagePahtString[] = new String[2];
						try {
							rightAndErrorImagePahtString= playGameMysql.getaddressinformation(imageErrorUrlString);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						SeeRightImage2 seeRightImage2 = new SeeRightImage2(rightAndErrorImagePahtString[0], rightAndErrorImagePahtString[1]);
						try {
							seeRightImage2.checkcoordina();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							if (seeAJFrame==null||!seeAJFrame.isVisible()){
								seeAJFrame = seeRightImage2.showRightImage();
							}

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				thread.start();
							
			}
		});
		answerButton_1.setForeground(Color.BLACK);
		answerButton_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
		answerButton_1.setBackground(Color.CYAN);
		answerButton_1.setBounds(559, 506, 134, 77);
        answerButton_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		getContentPane().add(answerButton_1);
		
		JLabel Label_1 = new JLabel("2.\u5728\u5B89\u5168\u503C\u8303\u56F4\u5185\u627E\u51FA\u5168\u90E8\u8FDD\u7AE0\u70B9");
		Label_1.setHorizontalAlignment(SwingConstants.LEFT);
		Label_1.setForeground(Color.RED);
		Label_1.setFont(new Font("微软雅黑", Font.BOLD | Font.ITALIC, 28));
		Label_1.setBounds(469, 299, 441, 77);
		getContentPane().add(Label_1);
		
		ImageIcon errorJlaIcon = new ImageIcon("configurationImage/error2.png");
		JLabel error1Lable = new JLabel(errorJlaIcon);
		error1Lable.setHorizontalAlignment(SwingConstants.CENTER);
		error1Lable.setForeground(Color.BLACK);
		error1Lable.setFont(new Font("微软雅黑", Font.BOLD, 25));
		error1Lable.setBounds(924, 187, 112, 77);
		getContentPane().add(error1Lable);
		
		JLabel error2Lable = new JLabel(errorJlaIcon);
		error2Lable.setHorizontalAlignment(SwingConstants.CENTER);
		error2Lable.setForeground(Color.BLACK);
		error2Lable.setFont(new Font("微软雅黑", Font.BOLD, 25));
		error2Lable.setBounds(924, 299, 112, 77);
		getContentPane().add(error2Lable);
		
		JLabel showGradeLable = new JLabel("本轮得分为:  "+this.getGrade+"  颗星星");
		showGradeLable.setHorizontalAlignment(SwingConstants.CENTER);
		showGradeLable.setForeground(Color.RED);
		showGradeLable.setFont(new Font("微软雅黑", Font.BOLD | Font.ITALIC, 30));
		showGradeLable.setBounds(469, 409, 542, 77);
		getContentPane().add(showGradeLable);
		contentPane.setOpaque(false);
		//执行保存分数函数
		try {
			saveNowGrade();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setVisible(true);
	}
	//保存分数函数
	private void saveNowGrade() throws SQLException {
		PlayGameMysql playGameMysql = new PlayGameMysql();
		playGameMysql.saveGrade(this.playerNameString, this.getGrade);
		playGameMysql.MysqlClose();
	}
	//退出时删除文件
	private void deleteFile() {
        File sr = new File("divisionImag");
        File []fIles = sr.listFiles();
        for(File file:fIles){
            System.out.println(file);
	        boolean resulte = file.delete();	
	        System.out.println(file+"======>删除图片:  "+resulte);
	    }
	}
}

