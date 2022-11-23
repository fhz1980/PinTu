package playgame;
import java.awt.BasicStroke;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

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
import java.awt.Point;
import java.awt.Button;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.NonWritableChannelException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import imagedivision.*;

import playgamemysql.PlayGameMysql;
import aftersuccess.*;
import defeated.PlayDefeated;

public class PlayGames2 extends JFrame {

	private JPanel contentPane;
	private int difficultDegree;
	private int time ;//时间
	private Timer threathTimer;
	private int period =1000;//时间间隔
	private int munmarker = 1;
	private String  urlErrorImg ;//错误图片路径
	//	private String playGrameName;//游戏玩家
	private int answerNumber = 0;//答对找出个数
	private int playGramelife = 5;//机会设置
	private int promptNumber = 5;//查看提示机会
	private int allErrorNumber = 0;
	private ArrayList markersArrayList = new ArrayList();
	private int imagewidth = 0 ;
	private int imageheight = 0;
	private ArrayList isdoArrayList;
	private boolean reward;//是否存在奖励
	int screenWidth;
	int screenHeight;


	private String playerNameString;//游戏玩家
	//大图片框
	private JLabel LableImage = new JLabel();
	//图片框这个框放到大图框中
	private JLabel boundsJLabel = new JLabel();
	//查看按钮
	private Button button = new Button("\u901A\u5173\u6761\u4EF6");
	//游戏标题
	private JLabel GameTitleLabel = new JLabel("\u8BF7\u627E\u51FA\u8FDD\u7AE0\u70B9");
	//名字框
	private JLabel lableName = new JLabel("\u9648\u5C0F\u4F1F");
	//正确个数JLabel
	private JLabel labelRight = new JLabel("\u4EE5\u627E\u51FA\uFF1A");
	//生命显示框
	private JLabel lableLife = new JLabel("\u5B89\u5168\u503C\uFF1A");
	//时间显示JLabel
	private JLabel lableTime = new JLabel("\u65F6\u95F4\uFF1A");
	//提示次数显示JLabel
	private JLabel promptJLabel = new JLabel("提示 :    ");
	//查看规则
	private JDialog ruleJDialog;
	//查看提示
	private JDialog proDialog;
	//选择框
	private JDialog judgeJDialog;
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
					PlayGames2 frame = new PlayGames2("configurationImage/A1reference.jpg","陈小伟",1,arrayList,true);

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
	public PlayGames2( String url,String playerGameName,int difficultDegree,ArrayList isdoArrayList,boolean reward) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 740);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //作用是是窗口一开始就是最大化

		this.playerNameString = playerGameName;
		this.isdoArrayList = isdoArrayList;
		this.reward = reward;
		this.time = giveTime();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		screenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
		screenHeight = (int) screensize.getHeight();//获得屏幕得高1080
		System.out.println("aaaaaaaaaaaaaaaaaaaaa"+screenWidth);
		System.out.println("aaaaaaaaaaaaaaaaaaaaa"+screenHeight);

//		screenWidth = 1280;//获得屏幕得宽1920
//		screenHeight = 800;//获得屏幕得高1080
		int row = screenWidth/3;
		int row3 = screenWidth/4;
		int rank = screenHeight/3;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//难度值初始化
		this.difficultDegree = difficultDegree;

		//加载图片
		this.urlErrorImg =url; //"configurationImage/A1.jpg";
		ImageIcon imageIcon = new ImageIcon(urlErrorImg);

		//错误点初始化
		try {
			this.allErrorNumber = allErrorNumberFuntion();
//			System.out.println("一共有错误点   ：    "+this.allErrorNumber);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new FileInputStream(urlErrorImg));
			imageheight = bufferedImage.getHeight();
			imagewidth = bufferedImage.getWidth();
//			 System.out.println("当前图片大小图片大小：height:"+imageheight+"  "+"width"+imagewidth);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		//查看
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(PlayGames2.this.ruleJDialog == null||!PlayGames2.this.ruleJDialog.isVisible()) {
					PlayGames2.this.ruleJDialog = new JDialog(PlayGames2.this);
					PlayGames2.this.ruleJDialog.setBounds(row, screenHeight/4, screenHeight/2, screenHeight/2);
					PlayGames2.this.ruleJDialog.setAlwaysOnTop(true);
					//				ruleJDialog.setUndecorated(true);
					//				ruleJDialog.setOpacity(0.9f);
					double f = TableLayout.FILL;
					double size[][] = { {f}, {f,f} };
					TableLayout layout = new TableLayout(size);
					PlayGames2.this.ruleJDialog.getContentPane().setLayout(layout);
					String rule1String = "1.找到全部的错误点可获得胜利。";
					String rule2String = "2.随机获得安全值5~8个，点击错误安全值减少一个点，安全值为0时游戏失败。";
					String rule3String = "3.基础时间为两分钟，未在规定时间内找出所有错误点游戏失败。";
					String rule4String = "4.积分  =  难度  x 找出错误点数。";
					StringBuilder rule1Builder = new StringBuilder("<html>");
					rule1Builder.append(rule1String).append("<br/>").append("<br/>").append("<br/>").append(rule2String).append("<br/>").append("<br/>").append("</html>");
					StringBuilder rule2Builder = new StringBuilder("<html>");
					rule2Builder.append(rule3String).append("<br/>").append("<br/>").append("<br/>").append(rule4String).append("<br/>").append("<br/>").append("<html>");

					JLabel rule1JLabel = new JLabel(rule1Builder.toString());
					rule1JLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
					rule1JLabel.setForeground(Color.red);


					JLabel rule2JLabel = new JLabel(rule2Builder.toString());
					rule2JLabel.setFont(new Font("微软雅黑",  Font.BOLD, 20));
					rule2JLabel.setForeground(Color.red);

					PlayGames2.this.ruleJDialog.getContentPane().add(rule1JLabel,"0,0,0,0");
					PlayGames2.this.ruleJDialog.getContentPane().add(rule2JLabel,"0,1,0,1");
					PlayGames2.this.ruleJDialog.setVisible(true);
				}


			}
		});
		button.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		button.setBackground(Color.CYAN);
		button.setBounds((row/2)-60, (int) (((screenHeight*0.12)/2)-20), 120, 51);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(button);

		//游戏标题
		GameTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GameTitleLabel.setForeground(Color.BLACK);
		GameTitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		GameTitleLabel.setBounds((int)(screenWidth)/2-195, 47,390, 51  );//806, 44, 349, 58
		GameTitleLabel.setText("共有错误点： "+allErrorNumber+"个    "+"难度："+this.difficultDegree);
		contentPane.add(GameTitleLabel);

		//名字设置
		lableName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lableName.setForeground(new Color(0, 0, 0));
		lableName.setHorizontalAlignment(SwingConstants.CENTER);
		lableName.setBounds((row*2+(row/2)-60), (int) (((screenHeight*0.12)/2)-20), 132, 58);
		lableName.setText(this.playerNameString);
		contentPane.add(lableName);

		//图片显示
		LableImage.setBackground(Color.GREEN);
		LableImage.setHorizontalAlignment(SwingConstants.CENTER);
		LableImage.setForeground(Color.ORANGE);
		LableImage.setBounds(0,(int)(screenHeight*0.12), screenWidth, (int)(screenHeight-240-(int)(screenHeight*0.12)));
		Point labelimagePoint = LableImage.getLocation();
//		System.out.println("AAAAAAAAAAAAAAAAAAAAAA         "+screenWidth+"         "+(int)(screenHeight-240-screenHeight*0.12)+"LalbeImagePoit:   "+labelimagePoint);

		//将JLabel添加到LableImage
		boundsJLabel.setBounds((int)((screenWidth/2)-imagewidth/2), ((int)(screenHeight-240-(int)(screenHeight*0.12)))/2-imageheight/2,imagewidth, imageheight);
		boundsJLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		LableImage.add(boundsJLabel);
		boundsJLabel.setIcon(imageIcon);
		Point a = boundsJLabel.getLocation();
//		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB        "+(int)((screenWidth/2)-imagewidth/2)+"         "+(((int)(screenHeight-240-screenHeight*0.12))/2-imageheight/2)+"         "+imagewidth+"         "+imageheight+"   laction   :"+a);

		contentPane.add(LableImage);
		//调用画框函数
		PictureFrame(boundsJLabel,urlErrorImg,this,threathTimer);


		//正确个数
		String urlRightString = "configurationImage/right3.png";
		Icon imageRightIcon = new ImageIcon(urlRightString);
		labelRight.setHorizontalAlignment(SwingConstants.CENTER);
		labelRight.setForeground(Color.BLACK);
		labelRight.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		labelRight.setBounds(row3/2-60, screenHeight-180, 180, 60);
		labelRight.setIcon(imageRightIcon);
		labelRight.setText("已找出:  "+String.valueOf(this.answerNumber));
		contentPane.add(labelRight);

		//剩余声命值
		String urlShieldString = "configurationImage/shield.png";
		Icon imageShieldIcon = new ImageIcon(urlShieldString);
		lableLife.setHorizontalAlignment(SwingConstants.CENTER);
		lableLife.setForeground(Color.BLACK);
		lableLife.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lableLife.setBounds(row3*1+row3/2-60, screenHeight-180, 180, 60);
		lableLife.setIcon(imageShieldIcon);
		contentPane.add(lableLife);
		//调用生命值函数
		startHealthFuntion(lableLife);


		//时间显示
		String urlClockString = "configurationImage/clock3.png";
		Icon imageClockIcon = new ImageIcon(urlClockString);
		lableTime.setHorizontalAlignment(SwingConstants.CENTER);
		lableTime.setForeground(Color.BLACK);
		lableTime.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lableTime.setBounds(row3*2+row3/2-60, screenHeight-180, 180, 60);
		lableTime.setIcon(imageClockIcon);
		contentPane.add(lableTime);
		//调用时间倒计时函数
		threathTimer = countDown(lableTime,period,this);


		//提示次数框
		String urlEyeString = "configurationImage/eye2.png";
		Icon imageEyeIcon = new ImageIcon(urlEyeString);
		promptJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		promptJLabel.setBounds(row3*3+row3/2-60, screenHeight-180, 180, 60);
		promptJLabel.setIcon(imageEyeIcon);
		promptJLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		promptJLabel.setText("  提示   ");
		contentPane.add(promptJLabel);
		promptJLabelMouseAction(promptJLabel,this.urlErrorImg);

	}//构造函数


	//===============================================================================================================//
	//查找一共有多少个错误点
	public int  allErrorNumberFuntion() throws SQLException {
		PlayGameMysql playGameMysql = new PlayGameMysql();
		System.out.println("查找一共有多少个错误点路径!!!!!!!!!"+this.urlErrorImg);
		ArrayList arr = playGameMysql.getCoordsinformation(this.urlErrorImg);
		int allmarker = arr.size();
		playGameMysql.MysqlClose();
		System.out.println("查找一共有多少个错误点!!!!!!!!!"+allmarker);
		return allmarker;

	}

	//画框函数
	public void PictureFrame(JLabel boundsJLabel,String imgUrlStrign, JFrame piJFrame,Timer timerOver) {
		boundsJLabel.addMouseListener(new MouseAdapter() {
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
			ArrayList mysqlMarkerAndbooleanArrayList = new ArrayList();
			JFrame PijFrame = piJFrame;
			int screenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
			int screenHeight = (int) screensize.getHeight();//获得屏幕得高1080
			int starx = 0;
			int stary = 0;
			int pos[]= new int [2];
			int newGetAnswer = 0;
			@Override
			public void mousePressed(MouseEvent e) {
				JLabel jLabel = (JLabel) e.getSource();
				Graphics g = jLabel.getGraphics();
				Graphics2D g2 = (Graphics2D)g;
				starx = e.getX();
				stary = e.getY();
				pos[0] = starx;
				pos[1] = stary;
				//画圆点
				g2.setColor(Color.white);
				g2.drawOval(starx,stary,1,1);
				//确定框的使用
				// 设置判断框
				JButton rightButton = new JButton("确定");
				JButton reutrnButton = new JButton("重画");
				if(judgeJDialog==null||!judgeJDialog.isVisible()) {
					judgeJDialog = new JDialog();
					judgeJDialog.setTitle("请选择");
					judgeJDialog.setAlwaysOnTop(true);
					judgeJDialog.setBounds(screenWidth/2-30, screenHeight/2-30, 100, 100);

					double f = TableLayout.FILL;
					double size[][] = { {f,f}, {f} };
					TableLayout layout = new TableLayout(size);
					judgeJDialog.getContentPane().setLayout(layout);

					judgeJDialog.getContentPane().add(rightButton,"0,0,0,0");
					judgeJDialog.getContentPane().add(reutrnButton,"1,0,1,0");

					judgeJDialog.setVisible(true);
					rightButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							System.out.println("数据库查询");
							try {
								try {
									mysqlMarkerAndbooleanArrayList = getLocationMysql(pos,imgUrlStrign);
									//改存在逻辑错误建议使用函数进行判断
									if((boolean) mysqlMarkerAndbooleanArrayList.get(0)) {
										boolean markerJudgeRepeat = matchMarkerFuntion((int)mysqlMarkerAndbooleanArrayList.get(1));
										if(markerJudgeRepeat) {
											g2.setColor(Color.gray);
											g2.fillOval(starx,stary,1,1);
											System.out.println("This is right!");
											//执行图片转换函数
											changeImage(jLabel, imgUrlStrign,(int)mysqlMarkerAndbooleanArrayList.get(1), (int)mysqlMarkerAndbooleanArrayList.get(2),  (int)mysqlMarkerAndbooleanArrayList.get(3),  (int)mysqlMarkerAndbooleanArrayList.get(4),  (int)mysqlMarkerAndbooleanArrayList.get(5));
											//答对 以找出 加1
											int answerupdate =  answerNumberFuntion();
											newGetAnswer = answerupdate;
											if(answerupdate==allErrorNumber) {
												Thread answerThread = new Thread(new Runnable() {

													@Override
													public void run() {
														// TODO Auto-generated method stub
//    														try {
//    															allRight();
//    														} catch (InterruptedException e) {
//    															// TODO Auto-generated catch block
//    															e.printStackTrace();
//    														}
														//执行胜利后的函数
														int getGrade = answerupdate*PlayGames2.this.difficultDegree;
														victoryJframe(imgUrlStrign,getGrade);
														PijFrame.dispose();

//    														System.exit(0);
													}
												});
												answerThread.start();
//    												timerOver.cancel();
											}

										}
									}
									//点击错误
									if((boolean)mysqlMarkerAndbooleanArrayList.get(0)==false) {
										g2.setColor(Color.red);
										g2.fillOval(starx,stary,20,20);
										System.out.println("This is error!");
										//对安全值进行操作答错减少安全值
										int actionPlayGramelife = reduceHealthFuntion();
										//健康值用完游戏结束！！==========待优化
										if(actionPlayGramelife==0) {
											System.out.println("游戏结束！");
											Thread endThread = new Thread(new Runnable() {
												@Override
												public void run() {
													// TODO Auto-generated method stub
//    												try {
//    													lifeOver();
//    												} catch (InterruptedException e) {
//    													// TODO Auto-generated catch block
//    													e.printStackTrace();
//    												}
													int getGrade = newGetAnswer*PlayGames2.this.difficultDegree;
													//结束框
													failJrame(imgUrlStrign,getGrade);
//    												timerOver.cancel();
													PijFrame.dispose();
//    												 System.exit(0);
												}
											});
											endThread.start();

										}
									}


								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							judgeJDialog.dispose();
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
		});//label框事件结束
	}//画框函数的结束



	//调用数据库获取图片信息
	public ArrayList getLocationMysql(int circular[],String imgurlAbSqlString) throws SQLException, FileNotFoundException, IOException {
		ArrayList resultArrayList = new ArrayList();
		//调用数据库进行查询
		PlayGameMysql playGameMysql = new PlayGameMysql();
		//获取地址
		String alladdressString[] = playGameMysql.getaddressinformation(imgurlAbSqlString);
		//获取坐标和位置
		ArrayList markerAndlocationArrayLists = playGameMysql.getCoordsinformation(imgurlAbSqlString);
		//执行判断函数
		ArrayList resultMarkerAndbooleanArrayList = judgeAddress( circular, markerAndlocationArrayLists, imgurlAbSqlString);
		System.out.println("调用数据库获取图片信息---函数执行");
		//关闭数据库
		playGameMysql.MysqlClose();
		return resultMarkerAndbooleanArrayList;

	}
	// 判断是否正确
	//参数中心点坐标，四个点坐标，图片地址
	public ArrayList judgeAddress(int juCircular[], ArrayList juArrayLists,String juImgErrorUrlString ) throws FileNotFoundException, IOException {
		boolean resultboolean = false ;
		int marker = 0;
		int resultstartx = 0;
		int resultstarty = 0;
		int resultendx = 0;
		int resultendy = 0;
		ArrayList markerAndbooleanArrayList = new ArrayList();
		//转化为坐标点
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(juImgErrorUrlString));
		int juImageheight = bufferedImage.getHeight();
		int juImagewidth = bufferedImage.getWidth();
		int xlocation = juCircular[0];
		int ylocation = juCircular[1];
//		System.out.println("这是判断正确的函数执行   "+juImageheight+"  "+juImagewidth+" xlocation "+xlocation+" ylocation "+ylocation);
		for(int i =0;i<juArrayLists.size();i++) {
			ArrayList juArrayList = (ArrayList) juArrayLists.get(i);
			int juStartx = (int)(juImagewidth*(double)juArrayList.get(1));
			int juStarty = (int)(juImageheight*(double)juArrayList.get(2));
			int juEndx = (int)(juImagewidth*(double)juArrayList.get(3));
//					System.out.println("sssssdgsdssssssssssssssssssssssss"+juEndx+"       "+(double)juArrayList.get(3)+"   "+(double)juArrayList.get(4));
			int juEndy = (int)(juImageheight*(double)juArrayList.get(4));
//					System.out.println("This is marker : "+i+"  juStartx: "+juStartx+" juStarty: "+juStarty+" juEndx: "+juEndx+" juEndy: "+juEndy);
			//进行前后判断
			if((xlocation<juStartx&&xlocation<juEndx)||(xlocation>juStartx&&xlocation>juEndx)||(ylocation<juStarty&&ylocation<juEndy)||(ylocation>juStarty&&ylocation>juEndy)){
				resultboolean = false;
				marker = 0;
//						System.out.println("这个点是错误的！"+resultboolean);
			}
			else {
				resultboolean = true;
				marker = (int) juArrayList.get(0);
				resultstartx = juStartx;
				resultstarty = juStarty;
				resultendx = juEndx;
				resultendy = juEndy;
//						System.out.println("这个点是正确的！"+resultboolean+"   "+juStartx+"   "+juStarty+"   "+juEndx+juEndy);
				break;
			}
		}
		markerAndbooleanArrayList.add(resultboolean);
		markerAndbooleanArrayList.add(marker);
		markerAndbooleanArrayList.add(resultstartx);
		markerAndbooleanArrayList.add(resultstarty);
		markerAndbooleanArrayList.add(resultendx);
		markerAndbooleanArrayList.add(resultendy);

//		System.out.println("这是判断正确的函数执行        "+resultboolean+"    "+marker);
		return markerAndbooleanArrayList;
	}
	// 进行换图片
	public void changeImage(JLabel jLabel ,String changeErrorAddressString,int changemarker, int changeStartx,int changeStarty,int changeEndx,int changeEndy) throws SQLException, IOException {
		//先执行数据库操作获取对应图片的地址
		//调用数据库进行查询
		PlayGameMysql playGameMysql = new PlayGameMysql();
		//获取地址
		String alladdressString[] = playGameMysql.getaddressinformation(changeErrorAddressString);
		//执行图片分割函数
		ImageDivision imageDivision = new ImageDivision(alladdressString[0], changemarker, changeStartx, changeStarty, changeEndx, changeEndy);
		ArrayList changeArrayList = imageDivision.divisionImage();

		int allchangestartx = (int) changeArrayList.get(1);
		int allchangestraty = (int) changeArrayList.get(2);
		int allchangeendx = (int) changeArrayList.get(3);
		int allchangeendy = (int) changeArrayList.get(4);
//			System.out.println("change 中的四个点的坐标：   "+"   "+allchangestartx+"   "+allchangestraty+"   "+allchangeendx+"   "+allchangeendy);

		int changeImagewidth = Math.abs(changeStartx-changeEndx)+1;
		int  changeImageheight = Math.abs(changeStarty-changeEndy)+1;

		int changeDivisionImgeWidth =0;
		int chageDivisionImgeHight = 0;

		BufferedImage changebufferedImage;
		try {
			changebufferedImage = ImageIO.read(new FileInputStream((String) changeArrayList.get(0)));
			chageDivisionImgeHight = changebufferedImage.getHeight();
			changeDivisionImgeWidth = changebufferedImage.getWidth();
//				 System.out.println("当前图片大小图片大小：height:"+chageDivisionImgeHight+"  "+"width"+changeDivisionImgeWidth+"      "+(String)changeArrayList.get(0));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ImageIcon imageIcon3 = new ImageIcon((String) changeArrayList.get(0));
		JLabel jLabel3  = new JLabel(imageIcon3);

		JDialog  imgaDialog= new JDialog(PlayGames2.this);
		imgaDialog.setUndecorated(true);
		imgaDialog.setOpacity(0.9f);
		imgaDialog.setBackground(new Color(0,0,0,0));
//			int imgDialogstartx = (int)(screenWidth/2)-(int)(imagewidth/2);
//			int imgDialogstarty = (((int)(screenHeight-240-(int)(screenHeight*0.12)))/2)-(int)(imageheight/2)+(int)(screenHeight*0.12)-2;
//			System.out.println("imgDialogstartx and  "+imgDialogstartx+"   "+imgDialogstarty);
//			imgDialogstartx = imgDialogstartx + allchangestartx;
//			imgDialogstarty = imgDialogstarty +allchangestraty+30;
//			System.out.println("aaaaaaaaaaaaaaaa  "+imgDialogstartx+"   "+imgDialogstarty);


		//			Point boundsJLabelPoint = boundsJLabel.getLocation();
		int imgDialogstartx1 = (int) boundsJLabel.getX();
//			System.out.println("!!!!!!!!!!!!!!!!!!!!!   "+imgDialogstartx1);
		int imgDialogstarty1 = (int)(LableImage.getY()+boundsJLabel.getY());
//			System.out.println("!!!!!!!!!!!!!!!!!!!!!   "+imgDialogstarty1);
		imgDialogstartx1 = imgDialogstartx1+allchangestartx;
		imgDialogstarty1 = imgDialogstarty1+allchangestraty+(PlayGames2.this.getInsets().top-Math.abs(PlayGames2.this.getY()));
		Insets insets = PlayGames2.this.getInsets();

//			System.out.println("  11111111111111111111111111111111       :   "+insets);
//			System.out.println("PG   "+ PlayGames2.this.getSize());
//			System.out.println("PG   "+ PlayGames2.this.getBounds());
//			System.out.println(contentPane.getSize());
//		    System.out.println(contentPane.getBounds());
//			System.out.println("7个像素！！！！！！！！！！！！！   :"+imgDialogstartx1+"    "+imgDialogstarty1);

		imgaDialog.setBounds(imgDialogstartx1, imgDialogstarty1, changeDivisionImgeWidth,chageDivisionImgeHight);
		imgaDialog.getContentPane().add(jLabel3);
		imgaDialog.setVisible(true);
//			System.out.println("Dialog     :"+imgDialogstartx+"   "+imgDialogstarty+"  "+imgaDialog.getWidth()+"  "+imgaDialog.getHeight());
		playGameMysql.MysqlClose();

	}
	//进行marker匹配
	public boolean matchMarkerFuntion(int maMaker) {
		boolean maBooleanMarker  = false;
		if(!this.markersArrayList.contains(maMaker))
		{
			maBooleanMarker =true;
			this.markersArrayList.add(maMaker);
		}
		else {
			maBooleanMarker = false;
		}



		return maBooleanMarker;
	}
	//时间赋值
	private int giveTime() {
		int gtime=0;
		if(this.reward) {
			gtime = 120+10;
		}
		else {
			gtime =120;
		}
		return gtime;
	}

	//倒计时
	public Timer countDown(JLabel countDownJLabel,int period1, JFrame jFrame) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				time--;
				countDownJLabel.setText("时间："+String.valueOf(time));
//	                label_count.setText(String.valueOf(time));
				if(time==3) {
					System.out.println("进入倒计时");
				}
				if(time==0) {
					countDownJLabel.setText("时间："+String.valueOf(time));
					timer.cancel();//定时间器取消也就是这个时间线程结束。
					System.out.println("游戏结束");

					Thread timeThread = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								timeOver();

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// 连接到下载一个窗体PlayDefeated
							int getGrade = PlayGames2.this.difficultDegree*PlayGames2.this.answerNumber;
							//这个窗体关闭
							new PlayDefeated(PlayGames2.this.urlErrorImg,getGrade,PlayGames2.this.playerNameString);
							jFrame.dispose();//窗体关闭

//	            					System.exit(0);
						}
					});
					timeThread.start();
				}

			}
		}, 0,period1) ;//每隔一秒触发一次。
		return timer;
	}
	//生命命健康值初始化
	public void startHealthFuntion(JLabel jLabelstratHF) {

		Random random = new Random();
		int addfile = random.nextInt(4);
		if(this.reward) {
			this.playGramelife = this.playGramelife+addfile+1;
		}
		else {
			this.playGramelife = this.playGramelife+addfile;
		}

		System.out.println(this.playGramelife);
		String startHealthlifeString = String.valueOf(this.playGramelife);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				jLabelstratHF.setText("安全值:  "+startHealthlifeString);
			}
		});
		thread.start();
	}
	//生命健康值函数减小函数
	public int reduceHealthFuntion() {
		this.playGramelife--;
		String reduceAfterString = String.valueOf(this.playGramelife);
		this.lableLife.setText(reduceAfterString);
		return this.playGramelife;
	}
	// 答对个数
	public int answerNumberFuntion() {
		this.answerNumber = this.answerNumber+1;
		String answernString = String.valueOf(this.answerNumber);
		labelRight.setText("以找出:  "+answernString);
		return this.answerNumber;
	}
	// 提示次数减少
	public void promptFacility() {
		this.promptNumber = this.promptNumber-1;
		String promptString = String.valueOf(this.promptNumber);
		promptJLabel.setText("提示:  "+promptString);
	}
	// 完全正确游戏结束界面
	public void allRight() throws InterruptedException {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		int allRightscreenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
		int allRightscreenHeight = (int) screensize.getHeight();//获得屏幕得高1080
		ImageIcon endaImageIcon = new ImageIcon("configurationImage/success3.png");


		JDialog endDialog = new JDialog();

		JLabel endJLabel = new JLabel(endaImageIcon);

		endJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		endJLabel.setForeground(Color.BLACK);
		endJLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
		endDialog.setUndecorated(true);
		endDialog.setOpacity(0.9f);
		endDialog.setBackground(new Color(0,0,0,0));
		endDialog.setBounds(allRightscreenWidth/2-600,allRightscreenHeight/2-600,1174,944);
		endDialog.getContentPane().add(endJLabel);
		endDialog.setVisible(true);
		Thread.sleep(3000);
		endDialog.dispose();
	}
	// 时间到
	public void timeOver() throws InterruptedException {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		int timeOverscreenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
		int timeOverscreenHeight = (int) screensize.getHeight();//获得屏幕得高1080
		double f = TableLayout.FILL;
		double size[][] = { {600,636}, {f} };
		TableLayout layout = new TableLayout(size);
		JDialog timeOverDialog = new JDialog();
		timeOverDialog.getContentPane().setLayout(layout);
		JLabel timeOverTextJLabel = new JLabel("时间到！游戏结束!");
		timeOverTextJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeOverTextJLabel.setForeground(Color.BLACK);
		timeOverTextJLabel.setFont(new Font("微软雅黑", Font.BOLD, 45));
		timeOverTextJLabel.setForeground(Color.green);
		ImageIcon imageIcon = new ImageIcon("configurationImage/timeOver1.gif");
		JLabel timeOvergifJLabel = new JLabel(imageIcon);
		timeOvergifJLabel.setSize(630, 636);
		timeOverDialog.getContentPane().add(timeOverTextJLabel,"0,0,0,0");
		timeOverDialog.getContentPane().add(timeOvergifJLabel,"0,1,0,1");
		timeOverDialog.setBounds(timeOverscreenWidth/2-300,timeOverscreenHeight/2-350,650,670);
		timeOverDialog.setUndecorated(true);
		timeOverDialog.setOpacity(0.9f);
		timeOverDialog.setBackground(new Color(0,0,0,0));
//		timeOverDialog.add(timeOverTextJLabel);
		timeOverDialog.setVisible(true);
		Thread.sleep(3000);
		timeOverDialog.dispose();

	}
	//生命值用完
	public void lifeOver() throws InterruptedException {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		int lifeOverscreenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
		int lifeOverscreenHeight = (int) screensize.getHeight();//获得屏幕得高1080
		JDialog lifeOverDialog = new JDialog();
		ImageIcon lifeOverIcon = new ImageIcon("configurationImage/defeated.png");
		JLabel endJLabel = new JLabel(lifeOverIcon);
		endJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		endJLabel.setForeground(Color.BLACK);
		endJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		lifeOverDialog.setBounds(lifeOverscreenWidth/2-600,lifeOverscreenHeight/2-600,1094,1126);
		lifeOverDialog.getContentPane().add(endJLabel);

		lifeOverDialog.setUndecorated(true);
		lifeOverDialog.setOpacity(0.9f);
		lifeOverDialog.setBackground(new Color(0,0,0,0));
		lifeOverDialog.setVisible(true);
		Thread.sleep(3000);
		lifeOverDialog.dispose();
	}
	//提示框鼠标按钮事件
	public void promptJLabelMouseAction(JLabel proJLabel,String errorpathiString) {
		proJLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Thread proThread = new Thread(new Runnable() {

					@Override
					public void run() {
						System.out.println(proDialog);

						if(proDialog == null || !proDialog.isVisible()) {

							// TODO Auto-generated method stub
							//执行数据库查询获取提示信息信息
							String promptInformationString = null;
							PlayGameMysql playGameMysql = new PlayGameMysql();
							try {
								promptInformationString = playGameMysql.getPromptinformation(errorpathiString);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//调用函数进行换行
							StringBuilder newPromptinformationTypeBuilder = lineFeed(promptInformationString);

							Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
							int lifeOverscreenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
							int lifeOverscreenHeight = (int) screensize.getHeight();//获得屏幕得高1080

							proDialog = new JDialog();
							proDialog.setAlwaysOnTop(true);
							JLabel proJLabel = new JLabel(newPromptinformationTypeBuilder.toString());
							proJLabel.setHorizontalAlignment(SwingConstants.CENTER);
							proJLabel.setForeground(Color.red);
							proJLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
							proDialog.setBounds(lifeOverscreenWidth/2-250,lifeOverscreenHeight/2-250,500,500);
							proDialog.getContentPane().add(proJLabel);
							proDialog.setVisible(true);
							try {
								playGameMysql.MysqlClose();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				});
				proThread.start();
			}
		});
	}
	//执行胜利后的函数调用
	public void victoryJframe(String imageErrorUrlString,int getGrade) {
		threathTimer.cancel();
		Thread victoryThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Aftersuccess frame = new Aftersuccess(imageErrorUrlString,getGrade,PlayGames2.this.playerNameString,PlayGames2.this.isdoArrayList);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		victoryThread.start();
	}
	//执行失败后的界面调用
	public void failJrame(String imageErrorUrlString,int getGrade) {
		threathTimer.cancel();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayDefeated frame = new PlayDefeated(imageErrorUrlString,getGrade,PlayGames2.this.playerNameString);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//对提示信息进行换行处理
	public StringBuilder lineFeed(String promptinfromation) {
		StringBuilder builder = new StringBuilder("<html>");
		char allInformation[] = promptinfromation.toCharArray();
		int divisionSegmentation = (int)(allInformation.length/25)+1;
		ArrayList divisionSegmentationArrayList = new ArrayList();


		for(int i=0;i<divisionSegmentation;i++) {
//	            char segmentation[] = new char[25];
			ArrayList segmentationArrayList = new ArrayList();
			int k=0;
			for(int j=i*25;j<(i+1)*25;j++) {
				if(j!=(int)(allInformation.length)){
//	                    segmentation[k++]=allInformation[j];
					segmentationArrayList.add(allInformation[j]);
				}
				else {
					break;
				}

			}
			char newSegmentation[] = new char[segmentationArrayList.size()];
			for(int g=0;g<segmentationArrayList.size();g++) {
				newSegmentation[g]= (char) segmentationArrayList.get(g);
			}
			String afterInfromation = String.valueOf(newSegmentation);
			divisionSegmentationArrayList.add(afterInfromation);
		}
		for(int i=0;i<divisionSegmentation;i++) {
			builder.append((String)divisionSegmentationArrayList.get(i));
		}
		builder.append("</html>");

		return builder;
	}

}
