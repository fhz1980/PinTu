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


//�������������nummarker��������
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
		//��ȷͼƬѡ��
		Button errorButton = new Button("\u9519\u8BEF\u56FE\u7247");
		//����ͼƬѡ��
		Button rightButton = new Button("\u6B63\u786E\u56FE\u7247");
		errorButton.setActionCommand("\u9009\u62E9\u9519\u8BEF\u56FE\u7247");

		//��ȷͼƬ·��
		JLabel rightPathJLable = new JLabel("null");
		//����ͼƬ·��
		JLabel errorPathJLabel = new JLabel("null");
		//����Ļ�ߴ�
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
		int screenWidth =(int) screensize.getWidth();//�����Ļ�ÿ�1920
		int screenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080
		int row = screenWidth/3;
		int row3 = screenWidth/4;
		int rank = screenHeight/3;

		//JFrame����	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, screenWidth, screenHeight);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //�������Ǵ���һ��ʼ�������

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//����ͼƬѡ��
		errorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MouseListener[] mouseListeners1 = boundsJLabel.getMouseListeners();
				for(MouseListener m : mouseListeners1){
					boundsJLabel.removeMouseListener(m);
				}

				//�ļ���
				FileDialog fileDialog = new FileDialog(AddData2.this,"���",FileDialog.LOAD);
				fileDialog.setVisible(true);
				String errorAbsPath = fileDialog.getDirectory() + fileDialog.getFile();
				allPathString[0] = errorAbsPath;
				System.out.println(errorAbsPath);
				if(!errorAbsPath.equals("null")&&!errorAbsPath.equals("nullnull")){

					JLabel LableImage = new JLabel();
					Icon image1Icon = new ImageIcon(errorAbsPath);
					//����ͼƬ�Ŀ��
					BufferedImage bufferedImage;
					try {
						bufferedImage = ImageIO.read(new FileInputStream(errorAbsPath));
						errorImageheight = bufferedImage.getHeight();
						errorImagewidth = bufferedImage.getWidth();
		        			 System.out.println("��ǰͼƬ��СͼƬ��С��height:"+errorImageheight+"  "+"width"+errorImagewidth);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();

					}
					//�����жϣ�ͼƬ�Ƿ񳬹���Ļ����ͼƬ�Ƿ񳬹�ָ����ȣ�����Ļ��-240-��Ļ��*0.12��
					if(screenWidth< errorImagewidth||(screenHeight-240-screenHeight*0.12)< errorImageheight){
						if(screenWidth< errorImagewidth&&(screenHeight-240-screenHeight*0.12)< errorImageheight){
							StringBuilder stringBuilder = new StringBuilder("<html>");
							stringBuilder.append("<h2><font face='΢���ź�' color='red'>").append("ͼƬ����").append((int)screenWidth).append("���ء�").append("ͼƬ�߳���").append((int) (screenHeight-240-screenHeight*0.12)).append("����").append("</font></h2></html>");
							JOptionPane.showMessageDialog(null,new JLabel(stringBuilder.toString()), "����",JOptionPane.ERROR_MESSAGE);
							System.out.println("<html><h2><font face='΢���ź�' color='red'>ͼƬ������Ļ��ȡ�ָ���߶ȣ���Ļ��-240-��Ļ��*0.12��</font></h2></html>");
						}
						else if(screenWidth< errorImagewidth){
							StringBuilder stringBuilder1 = new StringBuilder("<html>");
							stringBuilder1.append("<h2><font face='΢���ź�' color='red'>").append("ͼƬ����").append((int) screenWidth).append("����").append("</font></h2></html>");
							JOptionPane.showMessageDialog(null,new JLabel(stringBuilder1.toString()), "����",JOptionPane.ERROR_MESSAGE);
							System.out.println("<html><h2><font face='΢���ź�' color='red'>ͼƬ������Ļ���</font></");
						}
						else if((screenHeight-240-screenHeight*0.12)< errorImageheight){
							StringBuilder stringBuilder2 = new StringBuilder("<html>");
							stringBuilder2.append("<h2><font face='΢���ź�' color='red'>").append("ͼƬ����").append((int) (screenHeight-240-screenHeight*0.12)).append("����").append("</font></h2></html>");
							JOptionPane.showMessageDialog(null,new JLabel(stringBuilder2.toString()), "����",JOptionPane.ERROR_MESSAGE);
							System.out.println("\"<html><h2><font face='΢���ź�' color='red'>ͼƬ��������Ļ��-240-��Ļ��");
						}
					}
					else {
						//ͼƬ��ʾ
						LableImage.setBackground(Color.GREEN);
						LableImage.setHorizontalAlignment(SwingConstants.CENTER);
						LableImage.setForeground(Color.ORANGE);
						LableImage.setBounds(0,(int)(screenHeight*0.12), screenWidth, (int)(screenHeight-240-screenHeight*0.12));
						contentPane.add(LableImage);
						//��JLabel��ӵ�LableImage
						boundsJLabel.setBounds((screenWidth/2)- errorImagewidth/2, ((int)(screenHeight-240-screenHeight*0.12))/2- errorImageheight/2, errorImagewidth,  errorImageheight);
						boundsJLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
						LableImage.add(boundsJLabel);
						boundsJLabel.setIcon(image1Icon);
						//��ʾͼƬ�Ĵ�С
//				        System.out.println("imagewidth:  "+imagewidth+"imageheight:  "+imageheight);
						errorPathJLabel.setText(errorAbsPath);
						if(!errorPathJLabel.getText().equals("nullnull")&&!errorPathJLabel.getText().equals("null")){
							PictureFrame(boundsJLabel, errorImagewidth, errorImageheight,errorAbsPath,rightPathJLable);//���û�����
							System.out.println("test  :  "+errorPathJLabel.getText());
							System.out.println("test  get Bounds :  "+boundsJLabel.getBounds());
						}
						savaiformation = boundsJLabel;
					}

				}

			}
		});
		errorButton.setFont(new Font("΢���ź�", Font.PLAIN, 17));
		errorButton.setBackground(Color.CYAN);
		errorButton.setBounds((int)(screenWidth*0.01354), (int)(screenHeight*0.04074), (int)(screenWidth*0.06041), (int)(screenHeight*0.04722));
		errorButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(errorButton);



		//��ȷͼƬ·��
		rightPathJLable.setFont(new Font("΢���ź�", Font.PLAIN, 17));
		rightPathJLable.setBounds((int)(screenWidth*0.48802), (int)(screenHeight*0.0092), (int)(screenWidth*0.51145), (int)(screenHeight*0.0361));
		contentPane.add(rightPathJLable);

		//����ͼƬ·��
		errorPathJLabel.setBackground(Color.WHITE);
		errorPathJLabel.setFont(new Font("΢���ź�", Font.PLAIN, 17));
		errorPathJLabel.setBounds((int)(screenWidth*0.48802), (int)(screenHeight*0.05740), (int)(screenWidth*0.51145), (int)(screenHeight*0.0361));
		contentPane.add(errorPathJLabel);

		//��ȷͼƬѡ����ʵ��
		rightButton.setForeground(Color.BLACK);
		rightButton.setFont(new Font("΢���ź�", Font.PLAIN, 17));
		rightButton.setBackground(Color.CYAN);
		rightButton.setBounds((int)(screenWidth*0.10104), (int)(screenHeight*0.0398), (int)(screenWidth*0.0604), (int)(screenHeight*0.04629));
		rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(rightButton);
		rightButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//�ļ���
				FileDialog fileDialog = new FileDialog(AddData2.this,"���",FileDialog.LOAD);
				fileDialog.setVisible(true);
				String rightAbsPath = fileDialog.getDirectory() + fileDialog.getFile();
				System.out.println("��ȷͼƬ·����"+rightAbsPath);
				BufferedImage bufferedImage;
				try {
					bufferedImage = ImageIO.read(new FileInputStream(rightAbsPath));
					rigthImageHeight = bufferedImage.getHeight();
					rigthImageWidth = bufferedImage.getWidth();
//		        			 System.out.println("��ǰͼƬ��СͼƬ��С��height:"+imageheight+"  "+"width"+imagewidth);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
				//�����жϣ�ͼƬ�Ƿ񳬹���Ļ����ͼƬ�Ƿ񳬹�ָ����ȣ�����Ļ��-240-��Ļ��*0.12��
				if(screenWidth< rigthImageWidth||(screenHeight-240-screenHeight*0.12)< rigthImageHeight){
					if(screenWidth< rigthImageWidth&&(screenHeight-240-screenHeight*0.12)< rigthImageHeight){
						StringBuilder stringBuilder = new StringBuilder("<html>");
						stringBuilder.append("<h2><font face='΢���ź�' color='red'>").append("ͼƬ����").append((int)screenWidth).append("���ء�").append("ͼƬ�߳���").append((int) (screenHeight-240-screenHeight*0.12)).append("����").append("</font></h2></html>");
						JOptionPane.showMessageDialog(null,new JLabel(stringBuilder.toString()), "����",JOptionPane.ERROR_MESSAGE);
					}
					else if(screenWidth< rigthImageWidth){
						StringBuilder stringBuilder1 = new StringBuilder("<html>");
						stringBuilder1.append("<h2><font face='΢���ź�' color='red'>").append("ͼƬ����").append((int) screenWidth).append("����").append("</font></h2></html>");
						JOptionPane.showMessageDialog(null,new JLabel(stringBuilder1.toString()), "����",JOptionPane.ERROR_MESSAGE);
					}
					else if((screenHeight-240-screenHeight*0.12)< rigthImageHeight){
						StringBuilder stringBuilder2 = new StringBuilder("<html>");
						stringBuilder2.append("<h2><font face='΢���ź�' color='red'>").append("ͼƬ����").append((int) (screenHeight-240-screenHeight*0.12)).append("����").append("</font></h2></html>");
						JOptionPane.showMessageDialog(null,new JLabel(stringBuilder2.toString()), "����",JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					allPathString[1] = rightAbsPath;
					rightPathJLable.setText(rightAbsPath);
				}
			}
		});

		//���������
		TextField typeTextField = new TextField();
		typeTextField.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		typeTextField.setBounds((int)(screenWidth*0.2276), (int)(screenHeight*0.0574), (int)(screenWidth*0.07864), (int)(screenHeight*0.0305));
		contentPane.add(typeTextField);

		Label typeLabel = new Label("\u7C7B\u578B\uFF1A");
		typeLabel.setForeground(Color.BLUE);
		typeLabel.setAlignment(Label.CENTER);
		typeLabel.setFont(new Font("΢���ź�", Font.BOLD, 20));
		typeLabel.setBounds((int)(screenWidth*0.2208), (int)(screenHeight*0.02037), (int)(screenWidth*0.05989), (int)(screenHeight*0.0361));
		contentPane.add(typeLabel);

		// �Ѷ������
		TextField difficultTextField = new TextField();
		difficultTextField.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		difficultTextField.setBounds((int)(screenWidth*0.32447), (int)(screenHeight*0.056481), (int)(screenWidth*0.075),(int)(screenHeight*0.02962) );
		contentPane.add(difficultTextField);

		Label difficultLabel_1 = new Label("\u96BE\u5EA6\uFF1A");
		difficultLabel_1.setForeground(Color.BLUE);
		difficultLabel_1.setFont(new Font("΢���ź�", Font.BOLD, 20));
		difficultLabel_1.setAlignment(Label.CENTER);
		difficultLabel_1.setBounds((int)(screenWidth*0.3197), (int)(screenHeight*0.02037), (int)(screenWidth*0.05989), (int)(screenHeight*0.03611));
		contentPane.add(difficultLabel_1);

		//��ʾ����
		JTextPane textPane = new JTextPane();
		promptJTextPane = textPane;
		textPane.setToolTipText("");
		textPane.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		textPane.setBounds((int)(screenWidth*0.3520), (int)(screenHeight*(1-0.1851)), (int)(screenWidth*0.3223), (int)(screenHeight*0.1));
		contentPane.add(textPane);
		//���ذ�ť
		JButton returnJButton = new JButton("����");
		returnJButton.setBackground(Color.CYAN);
		returnJButton.setFont(new Font("΢���ź�", Font.PLAIN, 17));
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
		//ˢ����������
		JButton refreshJButto = new JButton("��������");
		refreshJButto.setBackground(Color.CYAN);
		refreshJButto.setFont(new Font("΢���ź�", Font.PLAIN, 17));
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
		//����·������������
		Button saveButton = new Button("����");
		saveButton.setBackground(Color.CYAN);
		saveButton.setFont(new Font("΢���ź�", Font.PLAIN, 17));
		saveButton.setBounds((int)(screenWidth*0.172916), (int)(screenHeight*0.04074), (int)(screenWidth*0.03072), (int)(screenHeight*0.04351));
		saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(saveButton);

		Label rightPathlabel = new Label("\u6B63\u786E\u8DEF\u5F84\uFF1A");
		rightPathlabel.setForeground(Color.BLUE);
		rightPathlabel.setFont(new Font("΢���ź�", Font.BOLD, 17));
		rightPathlabel.setBounds((int)(screenWidth*0.414062), (int)(screenHeight*0.00925), (int)(screenWidth*0.07864), (int)(screenHeight*0.0305));
		contentPane.add(rightPathlabel);

		Label errorPathlabel = new Label("\u9519\u8BEF\u8DEF\u5F84\uFF1A");
		errorPathlabel.setForeground(Color.BLUE);
		errorPathlabel.setFont(new Font("΢���ź�", Font.BOLD, 17));
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
					//ִ�����ݿ����
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
					JOptionPane.showMessageDialog(null,"�����¼���������ݣ�", "����",JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("\u63D0\u793A\u8F93\u5165\uFF1A");
		lblNewLabel.setFont(new Font("΢���ź�", Font.BOLD, 20));
		lblNewLabel.setBounds((int)(screenWidth*0.2416), (int)(screenHeight*(1-0.1851)), (int)(screenWidth*0.0833), (int)(screenHeight*0.1));
		contentPane.add(lblNewLabel);

		setVisible(true);

	}//���캯������



	//������
	public void PictureFrame(JLabel boundsJLabel,int imagewith,int imageheigh,String urlpath,JLabel rightpathJLabel) {
		boundsJLabel.addMouseListener(new MouseAdapter() {
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
			int screenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
			int screenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080
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
				//�ж��Ƿ����
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
					JOptionPane.showMessageDialog(null,"ѡ����ȷ��ͼƬ��", "����",JOptionPane.ERROR_MESSAGE);
				}
				else {
					g2.drawRect(pos[0],pos[1],pos[2],pos[3]);
					if(pos[2]<40||pos[3]<40) {
//						 System.out.println("С��40������󣡣�������������");
						JOptionPane.showMessageDialog(null,"������������Ҫ40������,�����»���","��������",JOptionPane.ERROR_MESSAGE);
					}else {
						// �����Ƿ���ӵ����ݿ���
						JButton rightButton = new JButton("ȷ��");
						JButton reutrnButton = new JButton("�ػ�");
						JDialog judgeJDialog = new JDialog(AddData2.this);
						judgeJDialog.setAlwaysOnTop(true);
						judgeJDialog.setTitle("��ѡ��");
						judgeJDialog.setBounds(screenWidth/2-30, screenHeight/2-30, 100, 100);

						double f = TableLayout.FILL;
						double size[][] = { {f,f}, {f} };
						TableLayout layout = new TableLayout(size);
						judgeJDialog.getContentPane().setLayout(layout);

						judgeJDialog.getContentPane().add(rightButton,"0,0,0,0");
						judgeJDialog.getContentPane().add(reutrnButton,"1,0,1,0");
						judgeJDialog.setVisible(true);

						//ȷ�����ʹ��
						rightButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								System.out.append("��ӵ����ݿ���");
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
								System.out.append("���»�");
								judgeJDialog.dispose();
							}
						});
					}

				}
//                System.out.println(pos[0]+"    "+pos[1]+"    "+pos[2]+"    "+pos[3]);
			}//����ɿ��¼�����
		});//label���¼�����
	}//�������Ľ���

	//���ݿ���Ӻ���--ͼ����
	//file ��ʾ�ļ��� path��ʾ·��
	public void addToMysql(String urlpath,int marker,int recloction[] ,int imgwidth,int imgheight) throws SQLException {
		String filenameString = getFileName(urlpath,"file");//��ȡ�ļ���
		int updatemarker = marker+1;
		//�����һ��
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
	//��ӵ��������ݿ���
	public void addToRelevanMysql(String rightpathiReString,String errorpathReString,String typeReString,int difficultGradeRe) throws SQLException {
		this.textPaneString = this.promptJTextPane.getText();
		String proString = this.textPaneString;
//		System.out.println("111111111111111111111111"+textPaneString);
		if(this.textPaneString.equals("")) {
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
			int screenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
			int screenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080

			//��ʾ��ϢΪ��ʱ
			JButton prorightButton = new JButton("ȷ��");
			prorightButton.setForeground(Color.BLACK);
			prorightButton.setFont(new Font("΢���ź�", Font.BOLD, 20));
			prorightButton.setBackground(Color.ORANGE);

			JButton proreutrnButton = new JButton("ȡ��");
			proreutrnButton.setForeground(Color.BLACK);
			proreutrnButton.setFont(new Font("΢���ź�", Font.BOLD, 20));
			proreutrnButton.setBackground(Color.ORANGE);
			JDialog judgeJDialog = new JDialog(AddData2.this);
			judgeJDialog.setTitle("��ʾ��ϢΪNUll�Ƿ����");
			judgeJDialog.setBounds(screenWidth/2-30, screenHeight/2-30, 318, 140);

			//��ȷ����¼�
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
							saveDialog.setTitle("����ɹ�");
							saveDialog.setBounds(screenWidth/2-30, screenHeight/2-30, 200, 140);
							JButton aboutJButton = new JButton("ȷ��");
							aboutJButton.setBounds(0, 0, 200, 140);
							aboutJButton.setForeground(Color.BLACK);
							aboutJButton.setBackground(Color.CYAN);
							aboutJButton.setFont(new Font("΢���ź�", Font.BOLD, 20));
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
			//ȡ������¼�
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
					Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
					int screenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
					int screenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080
					// TODO Auto-generated method stub
					JDialog saveDialog = new JDialog(AddData2.this);
					saveDialog.setTitle("����ɹ�");
					saveDialog.setBounds(screenWidth/2-30, screenHeight/2-30, 200, 140);
					JButton aboutJButton = new JButton("ȷ��");
					aboutJButton.setBounds(0, 0, 200, 140);
					aboutJButton.setForeground(Color.BLACK);
					aboutJButton.setBackground(Color.CYAN);
					aboutJButton.setFont(new Font("΢���ź�", Font.BOLD, 20));
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
	// ��ͼƬ���ַָ����
	private String getFileName(String urlpathString,String sign) {
		String fName  = urlpathString;
		fName = fName.trim();
		String temp[] = fName.split("\\\\"); /**split���������������ʽ��"\\"�������Ƕ��ַ���ת��*/
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
	//��ͼƬ���й�һ������
	private double[] normalization(int nor[], int norimgwidth,int norimgheight) {
		double norend[] = new double[4];
		norend[0] = (double)nor[0]/norimgwidth;
		norend[1] = (double)nor[1]/norimgheight;
		norend[2] = (double)nor[2]/norimgwidth;
		norend[3] = (double)nor[3]/norimgheight;
//		System.out.println(norend[0]+"   "+norend[1]+"   "+norend[2]+"   "+norend[3]);
		return norend;
	}
	//������markerΪ0
	private void markerHoming() {
		this.nummarker = 0;

	}
}

