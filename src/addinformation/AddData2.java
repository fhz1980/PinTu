package addinformation;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import JieMian.JieMian1;
import javafx.util.Builder;
import layout.TableLayout;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Label;
import java.awt.Button;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

import inputmysql.*;
import java.awt.TextField;
import javax.swing.JTextPane;


//问题错误点迭代是nummarker增加问题
public class AddData2 extends JFrame {
	private JTextPane promptJTextPane;

	private JPanel contentPane;
	private int errorImageheight ;
	private int  errorImagewidth;
	private int rigthImageHeight;
	private int rigthImageWidth;
	private int nummarker = 0;
	private String[] allPathString = new  String[2];
	private String textPaneString = new String();
	JLabel savaiformation ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddData2 frame = new AddData2();
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
	public AddData2() {

		JLabel boundsJLabel = new JLabel();
		//正确图片选择
		Button errorButton = new Button("\u9519\u8BEF\u56FE\u7247");
		//错误图片选择
		Button rightButton = new Button("\u6B63\u786E\u56FE\u7247");
		errorButton.setActionCommand("\u9009\u62E9\u9519\u8BEF\u56FE\u7247");

		//正确图片路径
		JLabel rightPathJLable = new JLabel("null");
		//错误图片路径
		JLabel errorPathJLabel = new JLabel("null");
		//主屏幕尺寸
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		int screenWidth =(int) screensize.getWidth();//获得屏幕得宽1920
		int screenHeight = (int) screensize.getHeight();//获得屏幕得高1080
		int row = screenWidth/3;
		int row3 = screenWidth/4;
		int rank = screenHeight/3;

		//JFrame参数	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, screenWidth, screenHeight);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //作用是是窗口一开始就是最大化

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//错误图片选择
		errorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MouseListener[] mouseListeners1 = boundsJLabel.getMouseListeners();
				for(MouseListener m : mouseListeners1){
					boundsJLabel.removeMouseListener(m);
				}

				//文件打开
				FileDialog fileDialog = new FileDialog(AddData2.this,"添加",FileDialog.LOAD);
				fileDialog.setVisible(true);
				String errorAbsPath = fileDialog.getDirectory() + fileDialog.getFile();
				allPathString[0] = errorAbsPath;
				System.out.println(errorAbsPath);
				if(!errorAbsPath.equals("null")&&!errorAbsPath.equals("nullnull")){

					JLabel LableImage = new JLabel();
					Icon image1Icon = new ImageIcon(errorAbsPath);
					//更新图片的宽高
					BufferedImage bufferedImage;
					try {
						bufferedImage = ImageIO.read(new FileInputStream(errorAbsPath));
						errorImageheight = bufferedImage.getHeight();
						errorImagewidth = bufferedImage.getWidth();
		        			 System.out.println("当前图片大小图片大小：height:"+errorImageheight+"  "+"width"+errorImagewidth);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();

					}
					//做个判断，图片是否超过屏幕宽，和图片是否超过指定宽度：（屏幕高-240-屏幕高*0.12）
					if(screenWidth< errorImagewidth||(screenHeight-240-screenHeight*0.12)< errorImageheight){
						if(screenWidth< errorImagewidth&&(screenHeight-240-screenHeight*0.12)< errorImageheight){
							StringBuilder stringBuilder = new StringBuilder("<html>");
							stringBuilder.append("<h2><font face='微软雅黑' color='red'>").append("图片宽超出").append((int)screenWidth).append("像素、").append("图片高超出").append((int) (screenHeight-240-screenHeight*0.12)).append("像素").append("</font></h2></html>");
							JOptionPane.showMessageDialog(null,new JLabel(stringBuilder.toString()), "错误",JOptionPane.ERROR_MESSAGE);
							System.out.println("<html><h2><font face='微软雅黑' color='red'>图片超出屏幕宽度、指定高度（屏幕高-240-屏幕高*0.12）</font></h2></html>");
						}
						else if(screenWidth< errorImagewidth){
							StringBuilder stringBuilder1 = new StringBuilder("<html>");
							stringBuilder1.append("<h2><font face='微软雅黑' color='red'>").append("图片超出").append((int) screenWidth).append("像素").append("</font></h2></html>");
							JOptionPane.showMessageDialog(null,new JLabel(stringBuilder1.toString()), "错误",JOptionPane.ERROR_MESSAGE);
							System.out.println("<html><h2><font face='微软雅黑' color='red'>图片超出屏幕宽度</font></");
						}
						else if((screenHeight-240-screenHeight*0.12)< errorImageheight){
							StringBuilder stringBuilder2 = new StringBuilder("<html>");
							stringBuilder2.append("<h2><font face='微软雅黑' color='red'>").append("图片超出").append((int) (screenHeight-240-screenHeight*0.12)).append("像素").append("</font></h2></html>");
							JOptionPane.showMessageDialog(null,new JLabel(stringBuilder2.toString()), "错误",JOptionPane.ERROR_MESSAGE);
							System.out.println("\"<html><h2><font face='微软雅黑' color='red'>图片超出（屏幕高-240-屏幕高");
						}
					}
					else {
						//图片显示
						LableImage.setBackground(Color.GREEN);
						LableImage.setHorizontalAlignment(SwingConstants.CENTER);
						LableImage.setForeground(Color.ORANGE);
						LableImage.setBounds(0,(int)(screenHeight*0.12), screenWidth, (int)(screenHeight-240-screenHeight*0.12));
						contentPane.add(LableImage);
						//将JLabel添加到LableImage
						boundsJLabel.setBounds((screenWidth/2)- errorImagewidth/2, ((int)(screenHeight-240-screenHeight*0.12))/2- errorImageheight/2, errorImagewidth,  errorImageheight);
						boundsJLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
						LableImage.add(boundsJLabel);
						boundsJLabel.setIcon(image1Icon);
						//显示图片的大小
//				        System.out.println("imagewidth:  "+imagewidth+"imageheight:  "+imageheight);
						errorPathJLabel.setText(errorAbsPath);
						if(!errorPathJLabel.getText().equals("nullnull")&&!errorPathJLabel.getText().equals("null")){
							PictureFrame(boundsJLabel, errorImagewidth, errorImageheight,errorAbsPath,rightPathJLable);//调用画框函数
							System.out.println("test  :  "+errorPathJLabel.getText());
							System.out.println("test  get Bounds :  "+boundsJLabel.getBounds());
						}
						savaiformation = boundsJLabel;
					}

				}

			}
		});
		errorButton.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		errorButton.setBackground(Color.CYAN);
		errorButton.setBounds((int)(screenWidth*0.01354), (int)(screenHeight*0.04074), (int)(screenWidth*0.06041), (int)(screenHeight*0.04722));
		errorButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(errorButton);



		//正确图片路径
		rightPathJLable.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		rightPathJLable.setBounds((int)(screenWidth*0.48802), (int)(screenHeight*0.0092), (int)(screenWidth*0.51145), (int)(screenHeight*0.0361));
		contentPane.add(rightPathJLable);

		//错误图片路径
		errorPathJLabel.setBackground(Color.WHITE);
		errorPathJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		errorPathJLabel.setBounds((int)(screenWidth*0.48802), (int)(screenHeight*0.05740), (int)(screenWidth*0.51145), (int)(screenHeight*0.0361));
		contentPane.add(errorPathJLabel);

		//正确图片选择功能实现
		rightButton.setForeground(Color.BLACK);
		rightButton.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		rightButton.setBackground(Color.CYAN);
		rightButton.setBounds((int)(screenWidth*0.10104), (int)(screenHeight*0.0398), (int)(screenWidth*0.0604), (int)(screenHeight*0.04629));
		rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(rightButton);
		rightButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//文件打开
				FileDialog fileDialog = new FileDialog(AddData2.this,"添加",FileDialog.LOAD);
				fileDialog.setVisible(true);
				String rightAbsPath = fileDialog.getDirectory() + fileDialog.getFile();
				System.out.println("正确图片路径："+rightAbsPath);
				BufferedImage bufferedImage;
				try {
					bufferedImage = ImageIO.read(new FileInputStream(rightAbsPath));
					rigthImageHeight = bufferedImage.getHeight();
					rigthImageWidth = bufferedImage.getWidth();
//		        			 System.out.println("当前图片大小图片大小：height:"+imageheight+"  "+"width"+imagewidth);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
				//做个判断，图片是否超过屏幕宽，和图片是否超过指定宽度：（屏幕高-240-屏幕高*0.12）
				if(screenWidth< rigthImageWidth||(screenHeight-240-screenHeight*0.12)< rigthImageHeight){
					if(screenWidth< rigthImageWidth&&(screenHeight-240-screenHeight*0.12)< rigthImageHeight){
						StringBuilder stringBuilder = new StringBuilder("<html>");
						stringBuilder.append("<h2><font face='微软雅黑' color='red'>").append("图片宽超出").append((int)screenWidth).append("像素、").append("图片高超出").append((int) (screenHeight-240-screenHeight*0.12)).append("像素").append("</font></h2></html>");
						JOptionPane.showMessageDialog(null,new JLabel(stringBuilder.toString()), "错误",JOptionPane.ERROR_MESSAGE);
					}
					else if(screenWidth< rigthImageWidth){
						StringBuilder stringBuilder1 = new StringBuilder("<html>");
						stringBuilder1.append("<h2><font face='微软雅黑' color='red'>").append("图片超出").append((int) screenWidth).append("像素").append("</font></h2></html>");
						JOptionPane.showMessageDialog(null,new JLabel(stringBuilder1.toString()), "错误",JOptionPane.ERROR_MESSAGE);
					}
					else if((screenHeight-240-screenHeight*0.12)< rigthImageHeight){
						StringBuilder stringBuilder2 = new StringBuilder("<html>");
						stringBuilder2.append("<h2><font face='微软雅黑' color='red'>").append("图片超出").append((int) (screenHeight-240-screenHeight*0.12)).append("像素").append("</font></h2></html>");
						JOptionPane.showMessageDialog(null,new JLabel(stringBuilder2.toString()), "错误",JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					allPathString[1] = rightAbsPath;
					rightPathJLable.setText(rightAbsPath);
				}
			}
		});

		//类型输入框
		TextField typeTextField = new TextField();
		typeTextField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		typeTextField.setBounds((int)(screenWidth*0.2276), (int)(screenHeight*0.0574), (int)(screenWidth*0.07864), (int)(screenHeight*0.0305));
		contentPane.add(typeTextField);

		Label typeLabel = new Label("\u7C7B\u578B\uFF1A");
		typeLabel.setForeground(Color.BLUE);
		typeLabel.setAlignment(Label.CENTER);
		typeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		typeLabel.setBounds((int)(screenWidth*0.2208), (int)(screenHeight*0.02037), (int)(screenWidth*0.05989), (int)(screenHeight*0.0361));
		contentPane.add(typeLabel);

		// 难度输入框
		TextField difficultTextField = new TextField();
		difficultTextField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		difficultTextField.setBounds((int)(screenWidth*0.32447), (int)(screenHeight*0.056481), (int)(screenWidth*0.075),(int)(screenHeight*0.02962) );
		contentPane.add(difficultTextField);

		Label difficultLabel_1 = new Label("\u96BE\u5EA6\uFF1A");
		difficultLabel_1.setForeground(Color.BLUE);
		difficultLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
		difficultLabel_1.setAlignment(Label.CENTER);
		difficultLabel_1.setBounds((int)(screenWidth*0.3197), (int)(screenHeight*0.02037), (int)(screenWidth*0.05989), (int)(screenHeight*0.03611));
		contentPane.add(difficultLabel_1);

		//提示输入
		JTextPane textPane = new JTextPane();
		promptJTextPane = textPane;
		textPane.setToolTipText("");
		textPane.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		textPane.setBounds((int)(screenWidth*0.3520), (int)(screenHeight*(1-0.1851)), (int)(screenWidth*0.3223), (int)(screenHeight*0.1));
		contentPane.add(textPane);
		//返回按钮
		JButton returnJButton = new JButton("返回");
		returnJButton.setBackground(Color.CYAN);
		returnJButton.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		returnJButton.setBounds((int)(screenWidth*0.050), (int)(screenHeight*(1-0.1551)), (int)(screenWidth*0.09072), (int)(screenHeight*0.04351));
		returnJButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(returnJButton);
		returnJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new JieMian1();
				AddData2.this.dispose();

			}
		});
		//刷新重新输入
		JButton refreshJButto = new JButton("重新输入");
		refreshJButto.setBackground(Color.CYAN);
		refreshJButto.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		refreshJButto.setBounds((int)(screenWidth*0.80), (int)(screenHeight*(1-0.1551)), (int)(screenWidth*0.09072), (int)(screenHeight*0.04351));
		refreshJButto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(refreshJButto);
		refreshJButto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new AddData2();
				AddData2.this.dispose();
			}
		});
		//保存路劲到关联表中
		Button saveButton = new Button("保存");
		saveButton.setBackground(Color.CYAN);
		saveButton.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		saveButton.setBounds((int)(screenWidth*0.172916), (int)(screenHeight*0.04074), (int)(screenWidth*0.03072), (int)(screenHeight*0.04351));
		saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(saveButton);

		Label rightPathlabel = new Label("\u6B63\u786E\u8DEF\u5F84\uFF1A");
		rightPathlabel.setForeground(Color.BLUE);
		rightPathlabel.setFont(new Font("微软雅黑", Font.BOLD, 17));
		rightPathlabel.setBounds((int)(screenWidth*0.414062), (int)(screenHeight*0.00925), (int)(screenWidth*0.07864), (int)(screenHeight*0.0305));
		contentPane.add(rightPathlabel);

		Label errorPathlabel = new Label("\u9519\u8BEF\u8DEF\u5F84\uFF1A");
		errorPathlabel.setForeground(Color.BLUE);
		errorPathlabel.setFont(new Font("微软雅黑", Font.BOLD, 17));
		errorPathlabel.setBounds((int)(screenWidth*0.414062), (int)(screenHeight*0.05740), (int)(screenWidth*0.07864), (int)(screenHeight*0.0305));
		contentPane.add(errorPathlabel);




		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String  rightpathiString = rightPathJLable.getText();
				String  errorpathString = errorPathJLabel.getText();
				String  typeString = typeTextField.getText();
//
				String difficultgradeString= difficultTextField.getText();
				System.out.println(rightpathiString+errorpathString+typeString+difficultgradeString);
				if(rightpathiString!=null&&!rightpathiString.equals("null")&&!rightpathiString.equals("nullnull")&&errorpathString!=null&&!typeString.equals("")&&!difficultgradeString.equals("")) {
					System.out.println(rightpathiString+errorpathString+typeString);
					//执行数据库添加
					try {
						int difficultgradeInt = Integer.parseInt(difficultTextField.getText());
						addToRelevanMysql(rightpathiString,errorpathString,typeString,difficultgradeInt);
						markerHoming();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"请重新检查输入数据！", "警告",JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("\u63D0\u793A\u8F93\u5165\uFF1A");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		lblNewLabel.setBounds((int)(screenWidth*0.2416), (int)(screenHeight*(1-0.1851)), (int)(screenWidth*0.0833), (int)(screenHeight*0.1));
		contentPane.add(lblNewLabel);

		setVisible(true);

	}//构造函数结束



	//画框函数
	public void PictureFrame(JLabel boundsJLabel,int imagewith,int imageheigh,String urlpath,JLabel rightpathJLabel) {
		boundsJLabel.addMouseListener(new MouseAdapter() {
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
			int screenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
			int screenHeight = (int) screensize.getHeight();//获得屏幕得高1080
			int starx = 0;
			int stary = 0;
			int pos[]= new int [4];
			int towpos[] = new int [4];
			@Override
			public void mousePressed(MouseEvent e) {
				starx = e.getX();
				stary = e.getY();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				JLabel boundsJLabel = (JLabel) e.getSource();
				Graphics g = boundsJLabel.getGraphics();
				Graphics2D g2 = (Graphics2D)g;
				int endx1 = e.getX();
				int endy1 = e.getY();
				//判断是否出界
				if(endx1>imagewith) {
					endx1 = imagewith;
				}
				if(endy1>imageheigh) {
					endy1 = imageheigh;
				}
				if(endx1<0) {
					endx1 = 1;
				}
				if(endy1<0) {
					endy1 = 1;
				}
				System.out.println("x1:"+endx1+"    y1:"+endy1);
				if(starx>endx1){
					pos[0] = endx1;
					pos[2] = starx-endx1;
				}else{
					pos[0] = starx;
					pos[2] = endx1-starx;
				}
				if (stary>endy1){
					pos[1] = endy1;
					pos[3] = stary-endy1;
				}else{
					pos[1] = stary;
					pos[3] = endy1-stary;
				}
				towpos[0] = starx;
				towpos[1] = stary;
				towpos[2] = endx1;
				towpos[3] = endy1;
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa:  "+starx+"  "+stary+"  "+endx1+"  "+endy1);
				g2.setColor(Color.green);
				g2.setStroke(new BasicStroke(5.0f));
				String rightPahtString = rightpathJLabel.getText();
				if(rightPahtString.equals("null")||rightPahtString.equals("nullnull")) {
//		        	System.out.println("this is rightPathString:   "+rightPahtString);
					JOptionPane.showMessageDialog(null,"选择正确的图片！", "警告",JOptionPane.ERROR_MESSAGE);
				}
				else {
					g2.drawRect(pos[0],pos[1],pos[2],pos[3]);
					if(pos[2]<40||pos[3]<40) {
//						 System.out.println("小于40错误错误！！！！！！！！");
						JOptionPane.showMessageDialog(null,"方框宽高至少需要40个像素,请重新画。","发生错误",JOptionPane.ERROR_MESSAGE);
					}else {
						// 设置是否添加到数据库中
						JButton rightButton = new JButton("确定");
						JButton reutrnButton = new JButton("重画");
						JDialog judgeJDialog = new JDialog(AddData2.this);
						judgeJDialog.setAlwaysOnTop(true);
						judgeJDialog.setTitle("请选择");
						judgeJDialog.setBounds(screenWidth/2-30, screenHeight/2-30, 100, 100);

						double f = TableLayout.FILL;
						double size[][] = { {f,f}, {f} };
						TableLayout layout = new TableLayout(size);
						judgeJDialog.getContentPane().setLayout(layout);

						judgeJDialog.getContentPane().add(rightButton,"0,0,0,0");
						judgeJDialog.getContentPane().add(reutrnButton,"1,0,1,0");
						judgeJDialog.setVisible(true);

						//确定框的使用
						rightButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								System.out.append("添加到数据库中");
								judgeJDialog.dispose();
								try {
									addToMysql(urlpath,nummarker++,towpos,imagewith,imageheigh);
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
						});
						reutrnButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								System.out.append("重新画");
								judgeJDialog.dispose();
							}
						});
					}

				}
//                System.out.println(pos[0]+"    "+pos[1]+"    "+pos[2]+"    "+pos[3]);
			}//鼠标松开事件结束
		});//label框事件结束
	}//画框函数的结束

	//数据库添加函数--图坐标
	//file 表示文件名 path表示路径
	public void addToMysql(String urlpath,int marker,int recloction[] ,int imgwidth,int imgheight) throws SQLException {
		String filenameString = getFileName(urlpath,"file");//获取文件名
		int updatemarker = marker+1;
		//坐标归一化
		double normalizationend[] = normalization(recloction,imgwidth,imgheight);
		double norstartx = normalizationend[0];
		double norstarty = normalizationend[1];
		double norendx = normalizationend[2];
		double norendy = normalizationend[3];

		InputMysql addInputMysql = new InputMysql();
		String relativeUrlPath = getFileName(urlpath,"path");
		addInputMysql.addErrorInformation(filenameString, relativeUrlPath, updatemarker, norstartx, norstarty, norendx, norendy);
//		System.out.println(relativeUrlPath+"pathpathpathpathpathpathpathpathpathpathpath");
		addInputMysql.MysqlClose();
	}
	//添加到关联数据库中
	public void addToRelevanMysql(String rightpathiReString,String errorpathReString,String typeReString,int difficultGradeRe) throws SQLException {
		this.textPaneString = this.promptJTextPane.getText();
		String proString = this.textPaneString;
//		System.out.println("111111111111111111111111"+textPaneString);
		if(this.textPaneString.equals("")) {
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
			int screenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
			int screenHeight = (int) screensize.getHeight();//获得屏幕得高1080

			//提示信息为空时
			JButton prorightButton = new JButton("确定");
			prorightButton.setForeground(Color.BLACK);
			prorightButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
			prorightButton.setBackground(Color.ORANGE);

			JButton proreutrnButton = new JButton("取消");
			proreutrnButton.setForeground(Color.BLACK);
			proreutrnButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
			proreutrnButton.setBackground(Color.ORANGE);
			JDialog judgeJDialog = new JDialog(AddData2.this);
			judgeJDialog.setTitle("提示信息为NUll是否继续");
			judgeJDialog.setBounds(screenWidth/2-30, screenHeight/2-30, 318, 140);

			//正确鼠标事件
			prorightButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					InputMysql inputMysql = new InputMysql();
					try {
						String relativeRightP = getFileName(rightpathiReString,"path");
						String relativeErrorP = getFileName(errorpathReString,"path");
						inputMysql.addRelevanceInformation(relativeRightP, relativeErrorP, typeReString, difficultGradeRe,proString);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							JDialog saveDialog = new JDialog(AddData2.this);
							saveDialog.setTitle("保存成功");
							saveDialog.setBounds(screenWidth/2-30, screenHeight/2-30, 200, 140);
							JButton aboutJButton = new JButton("确定");
							aboutJButton.setBounds(0, 0, 200, 140);
							aboutJButton.setForeground(Color.BLACK);
							aboutJButton.setBackground(Color.CYAN);
							aboutJButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
							saveDialog.getContentPane().add(aboutJButton);
							saveDialog.setVisible(true);
							aboutJButton.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									new AddData2();
									AddData2.this.dispose();
								}
							});
						}
					});
					thread.start();
					judgeJDialog.dispose();
					try {
						inputMysql.MysqlClose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			//取消鼠标事件
			proreutrnButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					judgeJDialog.dispose();
				}
			});

			double f = TableLayout.FILL;
			double size[][] = { {150,150}, {100} };
			TableLayout layout = new TableLayout(size);
			judgeJDialog.getContentPane().setLayout(layout);

			judgeJDialog.getContentPane().add(prorightButton,"0,0,0,0");
			judgeJDialog.getContentPane().add(proreutrnButton,"1,0,1,0");
			judgeJDialog.setVisible(true);
		}
		else {
			InputMysql inputMysql = new InputMysql();
			String relativeRightP = getFileName(rightpathiReString,"path");
			String relativeErrorP = getFileName(errorpathReString,"path");
			inputMysql.addRelevanceInformation(relativeRightP, relativeErrorP, typeReString, difficultGradeRe,proString);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
					int screenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
					int screenHeight = (int) screensize.getHeight();//获得屏幕得高1080
					// TODO Auto-generated method stub
					JDialog saveDialog = new JDialog(AddData2.this);
					saveDialog.setTitle("保存成功");
					saveDialog.setBounds(screenWidth/2-30, screenHeight/2-30, 200, 140);
					JButton aboutJButton = new JButton("确定");
					aboutJButton.setBounds(0, 0, 200, 140);
					aboutJButton.setForeground(Color.BLACK);
					aboutJButton.setBackground(Color.CYAN);
					aboutJButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
					saveDialog.getContentPane().add(aboutJButton);
					saveDialog.setVisible(true);
					aboutJButton.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							new JieMian1();
							AddData2.this.dispose();
						}
					});
				}
			});
			thread.start();
			inputMysql.MysqlClose();
		}



	}
	// 将图片名字分割出来
	private String getFileName(String urlpathString,String sign) {
		String fName  = urlpathString;
		fName = fName.trim();
		String temp[] = fName.split("\\\\"); /**split里面必须是正则表达式，"\\"的作用是对字符串转义*/
		String fileName = temp[temp.length-1];
		System.out.println("fileName = " + fileName);
		String a[] = fileName.split("\\.");
		System.out.println(a[0]);
		System.out.println(a[1]);
		if(sign.equals("file")){
			return a[0];
		}
		if(sign.equals("path")){
			return temp[temp.length-2]+"/"+temp[temp.length-1];
		}
		return null;
	}
	//对图片进行归一化操作
	private double[] normalization(int nor[], int norimgwidth,int norimgheight) {
		double norend[] = new double[4];
		norend[0] = (double)nor[0]/norimgwidth;
		norend[1] = (double)nor[1]/norimgheight;
		norend[2] = (double)nor[2]/norimgwidth;
		norend[3] = (double)nor[3]/norimgheight;
//		System.out.println(norend[0]+"   "+norend[1]+"   "+norend[2]+"   "+norend[3]);
		return norend;
	}
	//更更新marker为0
	private void markerHoming() {
		this.nummarker = 0;

	}
}

