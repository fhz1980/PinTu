package aftersuccess;

import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import JieMian.GuanQia;
import JieMian.JieMian1;
import JieMian.JieMian2_1;
import JieMian.PaiHangBang;
import imagedivision.SeeRightImage2;
import playgamemysql.PlayGameMysql;


public class Aftersuccess extends JFrame {

	private JPanel contentPane;
	private  int screenWidth = 0;
	private int screenHeight =0;
	private int backgroundWidth = 0;
	private int backgroundHeight = 0;
	private String imageErrorUrl;
	private int getGrade;
	private String playerNameString;
	private ArrayList isdoArrayList;
	private JFrame seeAJframe;//查看正确答案
	private PaiHangBang pai;//查看排行榜

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArrayList arrayList = new ArrayList();
					arrayList.add(1);
					arrayList.add(1);
					Aftersuccess frame = new Aftersuccess("configurationImage/A1reference.jpg",6,"知足的高手",arrayList);
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
	public Aftersuccess(String imageErrorUrlString,int getGrade,String playerNameString,ArrayList isdoArrayList) {
		getContentPane().setEnabled(false);
		getContentPane().setForeground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getGrade = getGrade;
		this.playerNameString = playerNameString;
		this.isdoArrayList = isdoArrayList;
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) dimension.getWidth();//获得屏幕得宽1920
		screenHeight = (int) dimension.getHeight();//获得屏幕得高1080
		String backgroundImageString = "configurationImage/background2.jpg";
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
		this.imageErrorUrl = imageErrorUrlString;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int)(screenWidth/2)-(int)(backgroundWidth/2), (int)(screenHeight/2)-(int)(backgroundHeight/2), backgroundWidth, backgroundHeight+40);
		ImageIcon icon = new ImageIcon("configurationImage/background3.jpg");
		JLabel backJLabel = new JLabel(icon);
		backJLabel.setBounds(0, 0, backgroundWidth, backgroundHeight);
		getLayeredPane().setLayout(null);
		getLayeredPane().add(backJLabel,new Integer(Integer.MIN_VALUE));		
		contentPane = (JPanel)this.getContentPane();
		contentPane.setLayout(null);
		
		ImageIcon plateImageIcon = new ImageIcon("configurationImage/success13.png");
		JLabel plateJlable = new JLabel(plateImageIcon);
		plateJlable.setBounds(14, 13, 473, 570);
		getContentPane().add(plateJlable);
		
		ImageIcon starJlaIcon = new ImageIcon("configurationImage/star2.png");
		JLabel starJlable = new JLabel(starJlaIcon);
		starJlable.setBounds(526, 13, 144, 151);
		getContentPane().add(starJlable);
		
		JLabel Label = new JLabel("New label");
		Label.setHorizontalAlignment(SwingConstants.LEFT);
		Label.setFont(new Font("微软雅黑", Font.BOLD, 25));
		Label.setForeground(new Color(0, 0, 0));
		Label.setBounds(567, 194, 596, 102);
		Label.setText("\u6311\u6218\u6210\u529F\uFF1A\u5956\u52B1\u65F6\u95F4\u52A010\u79D2\u3002\u5B89\u5168\u503C\u52A01");
		getContentPane().add(Label);
		
		//查看排行
		Button selectCustomsPassButton = new Button("\u67E5\u770B\u6392\u540D");
		selectCustomsPassButton.setForeground(Color.BLACK);
		selectCustomsPassButton.setBackground(Color.CYAN);
		selectCustomsPassButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        selectCustomsPassButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		selectCustomsPassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pai==null||!pai.isVisible()){
					 pai =new PaiHangBang();
				}
			}
		});
		selectCustomsPassButton.setBounds(497, 442, 134, 77);
		getContentPane().add(selectCustomsPassButton);
		
		//下一关
		Button nextCustomsPassButton_2 = new Button("\u4E0B\u4E00\u5173");
		nextCustomsPassButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int passNumber = 0;
				PlayGameMysql playGameMysql = new PlayGameMysql();
				try {
					passNumber = playGameMysql.allPass();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(passNumber==Aftersuccess.this.isdoArrayList.size()){
					JOptionPane.showMessageDialog(Aftersuccess.this,"恭喜全部通过！");
					try {
						playGameMysql.MysqlClose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					Aftersuccess.this.isdoArrayList.add(1);
					new GuanQia(Aftersuccess.this.isdoArrayList,true);
					Aftersuccess.this.dispose();
				}				
			}
		});
		nextCustomsPassButton_2.setForeground(Color.BLACK);
		nextCustomsPassButton_2.setFont(new Font("微软雅黑", Font.BOLD, 20));
		nextCustomsPassButton_2.setBackground(Color.CYAN);
		nextCustomsPassButton_2.setBounds(965, 442, 134, 77);
        nextCustomsPassButton_2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		getContentPane().add(nextCustomsPassButton_2);
		
		//退出按钮
		Button quitsButton_2_1 = new Button("\u9000\u51FA");
		quitsButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteFile();
				new JieMian1();
				Aftersuccess.this.dispose();
				
			}
		});
		quitsButton_2_1.setForeground(Color.BLACK);
		quitsButton_2_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
		quitsButton_2_1.setBackground(Color.ORANGE);
		quitsButton_2_1.setBounds(1010, 540, 134, 43);
        quitsButton_2_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		getContentPane().add(quitsButton_2_1);
		
		
		JLabel starJlable_1 = new JLabel(starJlaIcon);
		starJlable_1.setBounds(684, 13, 144, 151);
		getContentPane().add(starJlable_1);
		
		JLabel starJlable_2 = new JLabel(starJlaIcon);
		starJlable_2.setBounds(842, 13, 144, 151);
		getContentPane().add(starJlable_2);
		
		JLabel starJlable_3 = new JLabel(starJlaIcon);
		starJlable_3.setBounds(1000, 13, 144, 151);
		getContentPane().add(starJlable_3);
		
		
		//查看答案
		Button answerButton_1 = new Button("\u67E5\u770B\u7B54\u6848");
		answerButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					if(seeAJframe==null||!seeAJframe.isVisible()){
						seeAJframe = seeRightImage2.showRightImage();
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		answerButton_1.setForeground(Color.BLACK);
		answerButton_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
		answerButton_1.setBackground(Color.CYAN);
		answerButton_1.setBounds(726, 442, 134, 77);
        answerButton_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		getContentPane().add(answerButton_1);
		
		JLabel showGradeLable = new JLabel("本轮得分为:  "+this.getGrade+"  颗星星");
		showGradeLable.setVerticalAlignment(SwingConstants.TOP);
		showGradeLable.setHorizontalAlignment(SwingConstants.LEFT);
		showGradeLable.setForeground(Color.BLACK);
		showGradeLable.setFont(new Font("微软雅黑", Font.BOLD, 25));
		showGradeLable.setBounds(567, 309, 596, 102);
		getContentPane().add(showGradeLable);
		contentPane.setOpaque(false);
		
		//执行分数保存
		try {
			saveSuccessGrade(this.playerNameString, this.getGrade);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setVisible(true);
	}
	private void saveSuccessGrade(String playerName,int obtainGrade) throws SQLException {
		//执行数据库将分数保存
		PlayGameMysql playGameMysql = new PlayGameMysql();
		playGameMysql.saveGrade(playerName, obtainGrade);
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
