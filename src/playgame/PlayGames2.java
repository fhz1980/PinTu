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
	private int time ;//ʱ��
	private Timer threathTimer;
	private int period =1000;//ʱ����
	private int munmarker = 1;
	private String  urlErrorImg ;//����ͼƬ·��
	//	private String playGrameName;//��Ϸ���
	private int answerNumber = 0;//����ҳ�����
	private int playGramelife = 5;//��������
	private int promptNumber = 5;//�鿴��ʾ����
	private int allErrorNumber = 0;
	private ArrayList markersArrayList = new ArrayList();
	private int imagewidth = 0 ;
	private int imageheight = 0;
	private ArrayList isdoArrayList;
	private boolean reward;//�Ƿ���ڽ���
	int screenWidth;
	int screenHeight;


	private String playerNameString;//��Ϸ���
	//��ͼƬ��
	private JLabel LableImage = new JLabel();
	//ͼƬ�������ŵ���ͼ����
	private JLabel boundsJLabel = new JLabel();
	//�鿴��ť
	private Button button = new Button("\u901A\u5173\u6761\u4EF6");
	//��Ϸ����
	private JLabel GameTitleLabel = new JLabel("\u8BF7\u627E\u51FA\u8FDD\u7AE0\u70B9");
	//���ֿ�
	private JLabel lableName = new JLabel("\u9648\u5C0F\u4F1F");
	//��ȷ����JLabel
	private JLabel labelRight = new JLabel("\u4EE5\u627E\u51FA\uFF1A");
	//������ʾ��
	private JLabel lableLife = new JLabel("\u5B89\u5168\u503C\uFF1A");
	//ʱ����ʾJLabel
	private JLabel lableTime = new JLabel("\u65F6\u95F4\uFF1A");
	//��ʾ������ʾJLabel
	private JLabel promptJLabel = new JLabel("��ʾ :    ");
	//�鿴����
	private JDialog ruleJDialog;
	//�鿴��ʾ
	private JDialog proDialog;
	//ѡ���
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
					PlayGames2 frame = new PlayGames2("configurationImage/A1reference.jpg","��Сΰ",1,arrayList,true);

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
		setExtendedState(JFrame.MAXIMIZED_BOTH); //�������Ǵ���һ��ʼ�������

		this.playerNameString = playerGameName;
		this.isdoArrayList = isdoArrayList;
		this.reward = reward;
		this.time = giveTime();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
		screenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
		screenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080
		System.out.println("aaaaaaaaaaaaaaaaaaaaa"+screenWidth);
		System.out.println("aaaaaaaaaaaaaaaaaaaaa"+screenHeight);

//		screenWidth = 1280;//�����Ļ�ÿ�1920
//		screenHeight = 800;//�����Ļ�ø�1080
		int row = screenWidth/3;
		int row3 = screenWidth/4;
		int rank = screenHeight/3;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//�Ѷ�ֵ��ʼ��
		this.difficultDegree = difficultDegree;

		//����ͼƬ
		this.urlErrorImg =url; //"configurationImage/A1.jpg";
		ImageIcon imageIcon = new ImageIcon(urlErrorImg);

		//������ʼ��
		try {
			this.allErrorNumber = allErrorNumberFuntion();
//			System.out.println("һ���д����   ��    "+this.allErrorNumber);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new FileInputStream(urlErrorImg));
			imageheight = bufferedImage.getHeight();
			imagewidth = bufferedImage.getWidth();
//			 System.out.println("��ǰͼƬ��СͼƬ��С��height:"+imageheight+"  "+"width"+imagewidth);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		//�鿴
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
					String rule1String = "1.�ҵ�ȫ���Ĵ����ɻ��ʤ����";
					String rule2String = "2.�����ð�ȫֵ5~8�����������ȫֵ����һ���㣬��ȫֵΪ0ʱ��Ϸʧ�ܡ�";
					String rule3String = "3.����ʱ��Ϊ�����ӣ�δ�ڹ涨ʱ�����ҳ����д������Ϸʧ�ܡ�";
					String rule4String = "4.����  =  �Ѷ�  x �ҳ����������";
					StringBuilder rule1Builder = new StringBuilder("<html>");
					rule1Builder.append(rule1String).append("<br/>").append("<br/>").append("<br/>").append(rule2String).append("<br/>").append("<br/>").append("</html>");
					StringBuilder rule2Builder = new StringBuilder("<html>");
					rule2Builder.append(rule3String).append("<br/>").append("<br/>").append("<br/>").append(rule4String).append("<br/>").append("<br/>").append("<html>");

					JLabel rule1JLabel = new JLabel(rule1Builder.toString());
					rule1JLabel.setFont(new Font("΢���ź�", Font.BOLD, 20));
					rule1JLabel.setForeground(Color.red);


					JLabel rule2JLabel = new JLabel(rule2Builder.toString());
					rule2JLabel.setFont(new Font("΢���ź�",  Font.BOLD, 20));
					rule2JLabel.setForeground(Color.red);

					PlayGames2.this.ruleJDialog.getContentPane().add(rule1JLabel,"0,0,0,0");
					PlayGames2.this.ruleJDialog.getContentPane().add(rule2JLabel,"0,1,0,1");
					PlayGames2.this.ruleJDialog.setVisible(true);
				}


			}
		});
		button.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		button.setBackground(Color.CYAN);
		button.setBounds((row/2)-60, (int) (((screenHeight*0.12)/2)-20), 120, 51);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(button);

		//��Ϸ����
		GameTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GameTitleLabel.setForeground(Color.BLACK);
		GameTitleLabel.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		GameTitleLabel.setBounds((int)(screenWidth)/2-195, 47,390, 51  );//806, 44, 349, 58
		GameTitleLabel.setText("���д���㣺 "+allErrorNumber+"��    "+"�Ѷȣ�"+this.difficultDegree);
		contentPane.add(GameTitleLabel);

		//��������
		lableName.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		lableName.setForeground(new Color(0, 0, 0));
		lableName.setHorizontalAlignment(SwingConstants.CENTER);
		lableName.setBounds((row*2+(row/2)-60), (int) (((screenHeight*0.12)/2)-20), 132, 58);
		lableName.setText(this.playerNameString);
		contentPane.add(lableName);

		//ͼƬ��ʾ
		LableImage.setBackground(Color.GREEN);
		LableImage.setHorizontalAlignment(SwingConstants.CENTER);
		LableImage.setForeground(Color.ORANGE);
		LableImage.setBounds(0,(int)(screenHeight*0.12), screenWidth, (int)(screenHeight-240-(int)(screenHeight*0.12)));
		Point labelimagePoint = LableImage.getLocation();
//		System.out.println("AAAAAAAAAAAAAAAAAAAAAA         "+screenWidth+"         "+(int)(screenHeight-240-screenHeight*0.12)+"LalbeImagePoit:   "+labelimagePoint);

		//��JLabel��ӵ�LableImage
		boundsJLabel.setBounds((int)((screenWidth/2)-imagewidth/2), ((int)(screenHeight-240-(int)(screenHeight*0.12)))/2-imageheight/2,imagewidth, imageheight);
		boundsJLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		LableImage.add(boundsJLabel);
		boundsJLabel.setIcon(imageIcon);
		Point a = boundsJLabel.getLocation();
//		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB        "+(int)((screenWidth/2)-imagewidth/2)+"         "+(((int)(screenHeight-240-screenHeight*0.12))/2-imageheight/2)+"         "+imagewidth+"         "+imageheight+"   laction   :"+a);

		contentPane.add(LableImage);
		//���û�����
		PictureFrame(boundsJLabel,urlErrorImg,this,threathTimer);


		//��ȷ����
		String urlRightString = "configurationImage/right3.png";
		Icon imageRightIcon = new ImageIcon(urlRightString);
		labelRight.setHorizontalAlignment(SwingConstants.CENTER);
		labelRight.setForeground(Color.BLACK);
		labelRight.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		labelRight.setBounds(row3/2-60, screenHeight-180, 180, 60);
		labelRight.setIcon(imageRightIcon);
		labelRight.setText("���ҳ�:  "+String.valueOf(this.answerNumber));
		contentPane.add(labelRight);

		//ʣ������ֵ
		String urlShieldString = "configurationImage/shield.png";
		Icon imageShieldIcon = new ImageIcon(urlShieldString);
		lableLife.setHorizontalAlignment(SwingConstants.CENTER);
		lableLife.setForeground(Color.BLACK);
		lableLife.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		lableLife.setBounds(row3*1+row3/2-60, screenHeight-180, 180, 60);
		lableLife.setIcon(imageShieldIcon);
		contentPane.add(lableLife);
		//��������ֵ����
		startHealthFuntion(lableLife);


		//ʱ����ʾ
		String urlClockString = "configurationImage/clock3.png";
		Icon imageClockIcon = new ImageIcon(urlClockString);
		lableTime.setHorizontalAlignment(SwingConstants.CENTER);
		lableTime.setForeground(Color.BLACK);
		lableTime.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		lableTime.setBounds(row3*2+row3/2-60, screenHeight-180, 180, 60);
		lableTime.setIcon(imageClockIcon);
		contentPane.add(lableTime);
		//����ʱ�䵹��ʱ����
		threathTimer = countDown(lableTime,period,this);


		//��ʾ������
		String urlEyeString = "configurationImage/eye2.png";
		Icon imageEyeIcon = new ImageIcon(urlEyeString);
		promptJLabel.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		promptJLabel.setBounds(row3*3+row3/2-60, screenHeight-180, 180, 60);
		promptJLabel.setIcon(imageEyeIcon);
		promptJLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		promptJLabel.setText("  ��ʾ   ");
		contentPane.add(promptJLabel);
		promptJLabelMouseAction(promptJLabel,this.urlErrorImg);

	}//���캯��


	//===============================================================================================================//
	//����һ���ж��ٸ������
	public int  allErrorNumberFuntion() throws SQLException {
		PlayGameMysql playGameMysql = new PlayGameMysql();
		System.out.println("����һ���ж��ٸ������·��!!!!!!!!!"+this.urlErrorImg);
		ArrayList arr = playGameMysql.getCoordsinformation(this.urlErrorImg);
		int allmarker = arr.size();
		playGameMysql.MysqlClose();
		System.out.println("����һ���ж��ٸ������!!!!!!!!!"+allmarker);
		return allmarker;

	}

	//������
	public void PictureFrame(JLabel boundsJLabel,String imgUrlStrign, JFrame piJFrame,Timer timerOver) {
		boundsJLabel.addMouseListener(new MouseAdapter() {
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
			ArrayList mysqlMarkerAndbooleanArrayList = new ArrayList();
			JFrame PijFrame = piJFrame;
			int screenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
			int screenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080
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
				//��Բ��
				g2.setColor(Color.white);
				g2.drawOval(starx,stary,1,1);
				//ȷ�����ʹ��
				// �����жϿ�
				JButton rightButton = new JButton("ȷ��");
				JButton reutrnButton = new JButton("�ػ�");
				if(judgeJDialog==null||!judgeJDialog.isVisible()) {
					judgeJDialog = new JDialog();
					judgeJDialog.setTitle("��ѡ��");
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
							System.out.println("���ݿ��ѯ");
							try {
								try {
									mysqlMarkerAndbooleanArrayList = getLocationMysql(pos,imgUrlStrign);
									//�Ĵ����߼�������ʹ�ú��������ж�
									if((boolean) mysqlMarkerAndbooleanArrayList.get(0)) {
										boolean markerJudgeRepeat = matchMarkerFuntion((int)mysqlMarkerAndbooleanArrayList.get(1));
										if(markerJudgeRepeat) {
											g2.setColor(Color.gray);
											g2.fillOval(starx,stary,1,1);
											System.out.println("This is right!");
											//ִ��ͼƬת������
											changeImage(jLabel, imgUrlStrign,(int)mysqlMarkerAndbooleanArrayList.get(1), (int)mysqlMarkerAndbooleanArrayList.get(2),  (int)mysqlMarkerAndbooleanArrayList.get(3),  (int)mysqlMarkerAndbooleanArrayList.get(4),  (int)mysqlMarkerAndbooleanArrayList.get(5));
											//��� ���ҳ� ��1
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
														//ִ��ʤ����ĺ���
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
									//�������
									if((boolean)mysqlMarkerAndbooleanArrayList.get(0)==false) {
										g2.setColor(Color.red);
										g2.fillOval(starx,stary,20,20);
										System.out.println("This is error!");
										//�԰�ȫֵ���в��������ٰ�ȫֵ
										int actionPlayGramelife = reduceHealthFuntion();
										//����ֵ������Ϸ��������==========���Ż�
										if(actionPlayGramelife==0) {
											System.out.println("��Ϸ������");
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
													//������
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
							System.out.append("���»�");
							judgeJDialog.dispose();
						}
					});
				}

			}
		});//label���¼�����
	}//�������Ľ���



	//�������ݿ��ȡͼƬ��Ϣ
	public ArrayList getLocationMysql(int circular[],String imgurlAbSqlString) throws SQLException, FileNotFoundException, IOException {
		ArrayList resultArrayList = new ArrayList();
		//�������ݿ���в�ѯ
		PlayGameMysql playGameMysql = new PlayGameMysql();
		//��ȡ��ַ
		String alladdressString[] = playGameMysql.getaddressinformation(imgurlAbSqlString);
		//��ȡ�����λ��
		ArrayList markerAndlocationArrayLists = playGameMysql.getCoordsinformation(imgurlAbSqlString);
		//ִ���жϺ���
		ArrayList resultMarkerAndbooleanArrayList = judgeAddress( circular, markerAndlocationArrayLists, imgurlAbSqlString);
		System.out.println("�������ݿ��ȡͼƬ��Ϣ---����ִ��");
		//�ر����ݿ�
		playGameMysql.MysqlClose();
		return resultMarkerAndbooleanArrayList;

	}
	// �ж��Ƿ���ȷ
	//�������ĵ����꣬�ĸ������꣬ͼƬ��ַ
	public ArrayList judgeAddress(int juCircular[], ArrayList juArrayLists,String juImgErrorUrlString ) throws FileNotFoundException, IOException {
		boolean resultboolean = false ;
		int marker = 0;
		int resultstartx = 0;
		int resultstarty = 0;
		int resultendx = 0;
		int resultendy = 0;
		ArrayList markerAndbooleanArrayList = new ArrayList();
		//ת��Ϊ�����
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(juImgErrorUrlString));
		int juImageheight = bufferedImage.getHeight();
		int juImagewidth = bufferedImage.getWidth();
		int xlocation = juCircular[0];
		int ylocation = juCircular[1];
//		System.out.println("�����ж���ȷ�ĺ���ִ��   "+juImageheight+"  "+juImagewidth+" xlocation "+xlocation+" ylocation "+ylocation);
		for(int i =0;i<juArrayLists.size();i++) {
			ArrayList juArrayList = (ArrayList) juArrayLists.get(i);
			int juStartx = (int)(juImagewidth*(double)juArrayList.get(1));
			int juStarty = (int)(juImageheight*(double)juArrayList.get(2));
			int juEndx = (int)(juImagewidth*(double)juArrayList.get(3));
//					System.out.println("sssssdgsdssssssssssssssssssssssss"+juEndx+"       "+(double)juArrayList.get(3)+"   "+(double)juArrayList.get(4));
			int juEndy = (int)(juImageheight*(double)juArrayList.get(4));
//					System.out.println("This is marker : "+i+"  juStartx: "+juStartx+" juStarty: "+juStarty+" juEndx: "+juEndx+" juEndy: "+juEndy);
			//����ǰ���ж�
			if((xlocation<juStartx&&xlocation<juEndx)||(xlocation>juStartx&&xlocation>juEndx)||(ylocation<juStarty&&ylocation<juEndy)||(ylocation>juStarty&&ylocation>juEndy)){
				resultboolean = false;
				marker = 0;
//						System.out.println("������Ǵ���ģ�"+resultboolean);
			}
			else {
				resultboolean = true;
				marker = (int) juArrayList.get(0);
				resultstartx = juStartx;
				resultstarty = juStarty;
				resultendx = juEndx;
				resultendy = juEndy;
//						System.out.println("���������ȷ�ģ�"+resultboolean+"   "+juStartx+"   "+juStarty+"   "+juEndx+juEndy);
				break;
			}
		}
		markerAndbooleanArrayList.add(resultboolean);
		markerAndbooleanArrayList.add(marker);
		markerAndbooleanArrayList.add(resultstartx);
		markerAndbooleanArrayList.add(resultstarty);
		markerAndbooleanArrayList.add(resultendx);
		markerAndbooleanArrayList.add(resultendy);

//		System.out.println("�����ж���ȷ�ĺ���ִ��        "+resultboolean+"    "+marker);
		return markerAndbooleanArrayList;
	}
	// ���л�ͼƬ
	public void changeImage(JLabel jLabel ,String changeErrorAddressString,int changemarker, int changeStartx,int changeStarty,int changeEndx,int changeEndy) throws SQLException, IOException {
		//��ִ�����ݿ������ȡ��ӦͼƬ�ĵ�ַ
		//�������ݿ���в�ѯ
		PlayGameMysql playGameMysql = new PlayGameMysql();
		//��ȡ��ַ
		String alladdressString[] = playGameMysql.getaddressinformation(changeErrorAddressString);
		//ִ��ͼƬ�ָ��
		ImageDivision imageDivision = new ImageDivision(alladdressString[0], changemarker, changeStartx, changeStarty, changeEndx, changeEndy);
		ArrayList changeArrayList = imageDivision.divisionImage();

		int allchangestartx = (int) changeArrayList.get(1);
		int allchangestraty = (int) changeArrayList.get(2);
		int allchangeendx = (int) changeArrayList.get(3);
		int allchangeendy = (int) changeArrayList.get(4);
//			System.out.println("change �е��ĸ�������꣺   "+"   "+allchangestartx+"   "+allchangestraty+"   "+allchangeendx+"   "+allchangeendy);

		int changeImagewidth = Math.abs(changeStartx-changeEndx)+1;
		int  changeImageheight = Math.abs(changeStarty-changeEndy)+1;

		int changeDivisionImgeWidth =0;
		int chageDivisionImgeHight = 0;

		BufferedImage changebufferedImage;
		try {
			changebufferedImage = ImageIO.read(new FileInputStream((String) changeArrayList.get(0)));
			chageDivisionImgeHight = changebufferedImage.getHeight();
			changeDivisionImgeWidth = changebufferedImage.getWidth();
//				 System.out.println("��ǰͼƬ��СͼƬ��С��height:"+chageDivisionImgeHight+"  "+"width"+changeDivisionImgeWidth+"      "+(String)changeArrayList.get(0));
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
//			System.out.println("7�����أ�������������������������   :"+imgDialogstartx1+"    "+imgDialogstarty1);

		imgaDialog.setBounds(imgDialogstartx1, imgDialogstarty1, changeDivisionImgeWidth,chageDivisionImgeHight);
		imgaDialog.getContentPane().add(jLabel3);
		imgaDialog.setVisible(true);
//			System.out.println("Dialog     :"+imgDialogstartx+"   "+imgDialogstarty+"  "+imgaDialog.getWidth()+"  "+imgaDialog.getHeight());
		playGameMysql.MysqlClose();

	}
	//����markerƥ��
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
	//ʱ�丳ֵ
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

	//����ʱ
	public Timer countDown(JLabel countDownJLabel,int period1, JFrame jFrame) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				time--;
				countDownJLabel.setText("ʱ�䣺"+String.valueOf(time));
//	                label_count.setText(String.valueOf(time));
				if(time==3) {
					System.out.println("���뵹��ʱ");
				}
				if(time==0) {
					countDownJLabel.setText("ʱ�䣺"+String.valueOf(time));
					timer.cancel();//��ʱ����ȡ��Ҳ�������ʱ���߳̽�����
					System.out.println("��Ϸ����");

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
							// ���ӵ�����һ������PlayDefeated
							int getGrade = PlayGames2.this.difficultDegree*PlayGames2.this.answerNumber;
							//�������ر�
							new PlayDefeated(PlayGames2.this.urlErrorImg,getGrade,PlayGames2.this.playerNameString);
							jFrame.dispose();//����ر�

//	            					System.exit(0);
						}
					});
					timeThread.start();
				}

			}
		}, 0,period1) ;//ÿ��һ�봥��һ�Ρ�
		return timer;
	}
	//����������ֵ��ʼ��
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
				jLabelstratHF.setText("��ȫֵ:  "+startHealthlifeString);
			}
		});
		thread.start();
	}
	//��������ֵ������С����
	public int reduceHealthFuntion() {
		this.playGramelife--;
		String reduceAfterString = String.valueOf(this.playGramelife);
		this.lableLife.setText(reduceAfterString);
		return this.playGramelife;
	}
	// ��Ը���
	public int answerNumberFuntion() {
		this.answerNumber = this.answerNumber+1;
		String answernString = String.valueOf(this.answerNumber);
		labelRight.setText("���ҳ�:  "+answernString);
		return this.answerNumber;
	}
	// ��ʾ��������
	public void promptFacility() {
		this.promptNumber = this.promptNumber-1;
		String promptString = String.valueOf(this.promptNumber);
		promptJLabel.setText("��ʾ:  "+promptString);
	}
	// ��ȫ��ȷ��Ϸ��������
	public void allRight() throws InterruptedException {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
		int allRightscreenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
		int allRightscreenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080
		ImageIcon endaImageIcon = new ImageIcon("configurationImage/success3.png");


		JDialog endDialog = new JDialog();

		JLabel endJLabel = new JLabel(endaImageIcon);

		endJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		endJLabel.setForeground(Color.BLACK);
		endJLabel.setFont(new Font("΢���ź�", Font.BOLD, 25));
		endDialog.setUndecorated(true);
		endDialog.setOpacity(0.9f);
		endDialog.setBackground(new Color(0,0,0,0));
		endDialog.setBounds(allRightscreenWidth/2-600,allRightscreenHeight/2-600,1174,944);
		endDialog.getContentPane().add(endJLabel);
		endDialog.setVisible(true);
		Thread.sleep(3000);
		endDialog.dispose();
	}
	// ʱ�䵽
	public void timeOver() throws InterruptedException {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
		int timeOverscreenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
		int timeOverscreenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080
		double f = TableLayout.FILL;
		double size[][] = { {600,636}, {f} };
		TableLayout layout = new TableLayout(size);
		JDialog timeOverDialog = new JDialog();
		timeOverDialog.getContentPane().setLayout(layout);
		JLabel timeOverTextJLabel = new JLabel("ʱ�䵽����Ϸ����!");
		timeOverTextJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeOverTextJLabel.setForeground(Color.BLACK);
		timeOverTextJLabel.setFont(new Font("΢���ź�", Font.BOLD, 45));
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
	//����ֵ����
	public void lifeOver() throws InterruptedException {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
		int lifeOverscreenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
		int lifeOverscreenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080
		JDialog lifeOverDialog = new JDialog();
		ImageIcon lifeOverIcon = new ImageIcon("configurationImage/defeated.png");
		JLabel endJLabel = new JLabel(lifeOverIcon);
		endJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		endJLabel.setForeground(Color.BLACK);
		endJLabel.setFont(new Font("΢���ź�", Font.PLAIN, 25));
		lifeOverDialog.setBounds(lifeOverscreenWidth/2-600,lifeOverscreenHeight/2-600,1094,1126);
		lifeOverDialog.getContentPane().add(endJLabel);

		lifeOverDialog.setUndecorated(true);
		lifeOverDialog.setOpacity(0.9f);
		lifeOverDialog.setBackground(new Color(0,0,0,0));
		lifeOverDialog.setVisible(true);
		Thread.sleep(3000);
		lifeOverDialog.dispose();
	}
	//��ʾ����갴ť�¼�
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
							//ִ�����ݿ��ѯ��ȡ��ʾ��Ϣ��Ϣ
							String promptInformationString = null;
							PlayGameMysql playGameMysql = new PlayGameMysql();
							try {
								promptInformationString = playGameMysql.getPromptinformation(errorpathiString);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//���ú������л���
							StringBuilder newPromptinformationTypeBuilder = lineFeed(promptInformationString);

							Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
							int lifeOverscreenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
							int lifeOverscreenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080

							proDialog = new JDialog();
							proDialog.setAlwaysOnTop(true);
							JLabel proJLabel = new JLabel(newPromptinformationTypeBuilder.toString());
							proJLabel.setHorizontalAlignment(SwingConstants.CENTER);
							proJLabel.setForeground(Color.red);
							proJLabel.setFont(new Font("΢���ź�", Font.BOLD, 25));
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
	//ִ��ʤ����ĺ�������
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
	//ִ��ʧ�ܺ�Ľ������
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
	//����ʾ��Ϣ���л��д���
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
