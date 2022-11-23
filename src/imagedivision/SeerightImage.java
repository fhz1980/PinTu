package imagedivision;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.omg.CORBA.PUBLIC_MEMBER;

import playgamemysql.PlayGameMysql;

public class SeerightImage {
	private ArrayList coordinatArrayList = new ArrayList();
	private String rightImagePathString;
	private String errorImagePathString	;
	public SeerightImage() {
		// TODO Auto-generated constructor stub
		this.rightImagePathString = "D:\\Document\\ImageGame\\A1.jpg";
		this.errorImagePathString = "D:\\Document\\ImageGame\\A1reference.jpg";
	}
	//进行数据库查询查框坐标
	public void checkcoordina() throws SQLException, FileNotFoundException, IOException {
		
		PlayGameMysql playGameMysql = new PlayGameMysql();
		ArrayList markerAndlocationArrayLists = playGameMysql.getCoordsinformation(this.errorImagePathString);
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(this.rightImagePathString));
		int juImageheight = bufferedImage.getHeight();
		int juImagewidth = bufferedImage.getWidth();
		for(int i =0;i<markerAndlocationArrayLists.size();i++) {
			ArrayList  fourcoorArrayList = new ArrayList();
			ArrayList juArrayList = (ArrayList) markerAndlocationArrayLists.get(i);
			int juStartx = (int)(juImagewidth*(double)juArrayList.get(1));
			int juStarty = (int)(juImageheight*(double)juArrayList.get(2));
			int juEndx = (int)(juImagewidth*(double)juArrayList.get(3));	
//			System.out.println("sssssdgsdssssssssssssssssssssssss"+juEndx+"       "+(double)juArrayList.get(3)+"   "+(double)juArrayList.get(4));
			int juEndy = (int)(juImageheight*(double)juArrayList.get(4));
			fourcoorArrayList.add(juStartx);
			fourcoorArrayList.add(juStarty);
			fourcoorArrayList.add(juEndx);
			fourcoorArrayList.add(juEndy);
			this.coordinatArrayList.add(fourcoorArrayList);
			System.out.println(juStartx+"  "+juStarty+"  "+juEndx+"  "+juEndy);
	    }

   }
	//显示图片
	public void showRightImage() throws FileNotFoundException, IOException {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		int screenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
		int screenHeight = (int) screensize.getHeight();//获得屏幕得高1080
		
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(this.rightImagePathString));
		int showImagewidth = bufferedImage.getWidth();
		int showImageheight = bufferedImage.getHeight();
		
		JFrame jFrame = new JFrame();
		jFrame.setBounds((int)(screenWidth/2-showImagewidth/2),(int)(screenHeight/2-showImageheight/2), showImagewidth+15, showImageheight+30);
		JPanel contenpane = (JPanel)jFrame.getContentPane();
		contenpane.setLayout(null);
		
		ImageIcon icon = new ImageIcon(this.rightImagePathString);
		JLabel imageJLabel = new JLabel(icon);
		imageJLabel.setBounds(0,0, showImagewidth, showImageheight);
		contenpane.add(imageJLabel);
		picturFrame(imageJLabel);
		jFrame.setVisible(true);
	}
	//在图片上画框
	public void picturFrame(JLabel pictureJLabel) {
		ArrayList pictureArrayList = this.coordinatArrayList;
		pictureJLabel.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				JLabel jLabel = (JLabel)e.getSource();
				Graphics graphics = jLabel.getGraphics();
				Graphics2D g2 = (Graphics2D)graphics;
				for(int i=0;i<pictureArrayList.size();i++) {
					ArrayList fourArrayList = (ArrayList) pictureArrayList.get(i);
					for(int j=0;j<fourArrayList.size();j++) {
						 int imagewidth = Math.abs((int)fourArrayList.get(0)-(int)fourArrayList.get(1));
						 int imageheight = Math.abs((int)fourArrayList.get(2)-(int)fourArrayList.get(3));
						 int startx=0;
						 int starty=0;
						 int endx=0;
						 int endy=0;
						 if((int)fourArrayList.get(0)<(int)fourArrayList.get(2)) {
								if((int)fourArrayList.get(1)<(int)fourArrayList.get(3)) {
									startx = (int)fourArrayList.get(0);
									starty = (int)fourArrayList.get(1);
									endx = (int)fourArrayList.get(2);
									endy = (int)fourArrayList.get(3);
								}
								else {
									startx =  (int)fourArrayList.get(0);
									starty = (int)fourArrayList.get(1)-imageheight;
									endx = (int)fourArrayList.get(2);
									endy = (int)fourArrayList.get(3)+imageheight;				
								}
								
							}
							if((int)fourArrayList.get(0)>(int)fourArrayList.get(2)) {
								if((int)fourArrayList.get(1)>(int)fourArrayList.get(3)) {
									startx = (int)fourArrayList.get(2);
									starty = (int)fourArrayList.get(3);
									endx = (int)fourArrayList.get(0);
									endy = (int)fourArrayList.get(1);
								}
								else {
									startx = (int)fourArrayList.get(0)-imagewidth;
									starty = (int)fourArrayList.get(1);
									endx = (int)fourArrayList.get(2)+imagewidth;
									endy = (int)fourArrayList.get(3);
								}
							}
						System.out.println(startx+"  "+starty+"  "+endx+"  "+endy);
					}
				}
				
			}
		});
	}
   public static void main(String[] args) throws FileNotFoundException, SQLException, IOException {
	   SeerightImage seerightImage = new SeerightImage();
	   seerightImage.checkcoordina();
	   seerightImage.showRightImage();
   }

}
