package imagedivision;

import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import layout.TableLayout;
import org.omg.CORBA.PUBLIC_MEMBER;

import playgamemysql.PlayGameMysql;

public class SeeRightImage2 {
	private ArrayList coordinatArrayList = new ArrayList();
	private String rightImagePathString;
	private String errorImagePathString	;
	public SeeRightImage2(String rightImagePathString ,String errorImagePathString) {
		// TODO Auto-generated constructor stub
		this.rightImagePathString = rightImagePathString;
		this.errorImagePathString = errorImagePathString;
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
	    }

   }
	//显示图片
	public JFrame showRightImage() throws FileNotFoundException, IOException {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		int screenWidth = (int) screensize.getWidth();//获得屏幕得宽1920
		int screenHeight = (int) screensize.getHeight();//获得屏幕得高1080
		
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(this.rightImagePathString));
		int showImagewidth = bufferedImage.getWidth();
		int showImageheight = bufferedImage.getHeight();
		
		JFrame jFrame = new JFrame("正确答案");
		int jframeShowLocationx =0;
		int jframeShowLocationy = 0;
		if(screenWidth<showImagewidth) {
			jframeShowLocationx = 0;
		}
		else {
			jframeShowLocationx = (int)(screenWidth/2-showImagewidth/2);
		}
		if(screenHeight<showImageheight) {
			jframeShowLocationx = 0;
		}
		else {
			jframeShowLocationy = (int)(screenHeight/2-showImageheight/2);
		}
		jFrame.setBounds(jframeShowLocationx,jframeShowLocationy, showImagewidth+20, showImageheight+150);
		JPanel contenpane = (JPanel)jFrame.getContentPane();
		double fill = TableLayout.FILL ;
		double [][] size = {{fill,fill,fill},{30,fill,fill,fill,100,100}} ;
		TableLayout tableLayout = new TableLayout(size);
		contenpane.setLayout(tableLayout);
		

		BufferedImage fileImgeShowString = picturFrame(this.rightImagePathString);
		JLabel imageJLabel = new JLabel();	
		imageJLabel.setIcon(new ImageIcon(fileImgeShowString.getScaledInstance(showImagewidth,showImageheight,Image.SCALE_DEFAULT)));		
		imageJLabel.setBounds(0,0, showImagewidth, showImageheight);
		
		JButton buttonColoseButton = new JButton("关闭");
		buttonColoseButton.setForeground(Color.BLACK);
		buttonColoseButton.setFont(new Font("微软雅黑", Font.BOLD, 50));
		buttonColoseButton.setBackground(Color.CYAN);
//		buttonColoseButton.setBounds(0, showImageheight, showImagewidth, 100);
        buttonColoseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contenpane.add(buttonColoseButton,"0,5,2,5");
		
		contenpane.add(imageJLabel,"0,0,2,4");
		jFrame.setVisible(true);
		buttonColoseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jFrame.dispose();
			}
		});
		return jFrame;
	}
	//在图片上画框
	public BufferedImage picturFrame(String  pictureRightImagePathiString) {
		BufferedImage imageBufferedImage = null;
		try {
			imageBufferedImage = ImageIO.read(new FileInputStream(pictureRightImagePathiString));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Graphics2D graphics2d = (Graphics2D)imageBufferedImage.getGraphics();
		
		graphics2d.setColor(Color.RED);
		graphics2d.setStroke(new BasicStroke(5));
		for(int i=0;i<coordinatArrayList.size();i++) {
			ArrayList fourCoordinatArrayList = (ArrayList) coordinatArrayList.get(i);
			//调用坐标修正函数
			ArrayList affterChangeArrayList = coordinatChange((int)fourCoordinatArrayList.get(0), (int)fourCoordinatArrayList.get(1), (int)fourCoordinatArrayList.get(2), (int)fourCoordinatArrayList.get(3));
			int widthChange = (int)affterChangeArrayList.get(2)-(int)affterChangeArrayList.get(0);
			int heightChange = (int)affterChangeArrayList.get(3)-(int)affterChangeArrayList.get(1);
			graphics2d.drawRect((int)affterChangeArrayList.get(0), (int)affterChangeArrayList.get(1), widthChange, heightChange);		
		}
		return imageBufferedImage;
		
	}
	private ArrayList coordinatChange(int startx,int starty,int endx,int endy) {
		ArrayList afterChangeArrayList =new  ArrayList();
		 int coorstartx=0;
		 int coorstarty=0;
		 int coorendx=0;
		 int coorendy=0;
			int imagewidth = Math.abs(startx-endx)+1;
			int imageheight = Math.abs(starty-endy)+1;
			if(startx<endx) {
				if(starty<endy) {
					coorstartx = startx;
					coorstarty = starty;
					coorendx = endx;
					coorendy = endy;
				}
				else {
					coorstartx = startx;
					coorstarty = starty-imageheight;
					coorendx = endx;
					coorendy = endy+imageheight;				
				}
				
			}
			if(startx>endx) {
				if(starty>endy) {
					coorstartx = endx;
					coorstarty = endy;
					coorendx = startx;
					coorendy = starty;
				}
				else {
					coorstartx = startx-imagewidth;
					coorstarty = starty;
					coorendx = endx+imagewidth;
					coorendy = endy;
				}
			}
			afterChangeArrayList.add(coorstartx);
			afterChangeArrayList.add(coorstarty);
			afterChangeArrayList.add(coorendx);
			afterChangeArrayList.add(coorendy);
//			System.out.println(coorstartx+"  "+coorstarty+"  "+coorendx+"  "+coorendy);
		return afterChangeArrayList;
		
	}
	// 将图片名字分割出来
		private String getFileName(String urlpathString) {
			String fName  = urlpathString;
	        fName = fName.trim();
	        String temp[] = fName.split("\\\\"); /**split里面必须是正则表达式，"\\"的作用是对字符串转义*/
	        String fileName = temp[temp.length-1];
	        System.out.println("fileName = " + fileName);
	        String a[] = fileName.split("\\.");
	        System.out.println(a[0]);
	        System.out.println(a[1]);	
			return a[0];
		}
		// 将图片删除释放文件夹
		private void deleteAndrelease(String aftertString) {
	        File file1 = new File(aftertString);
	        boolean resulte = file1.delete();	
	        System.out.println(aftertString+"======>删除图片成功:  "+resulte);
		}
   public static void main(String[] args) throws FileNotFoundException, SQLException, IOException {
	   SeeRightImage2 seerightImage = new SeeRightImage2("C:\\parameter\\ImageGame\\A1.jpg","D:\\Document\\ImageGame\\A1reference.jpg");
	   seerightImage.checkcoordina();//获取坐标初始化获取六个框的坐标
	   seerightImage.showRightImage();//因为初始成功后开始使用六个框的坐坐标进行图像画框
//	   seerightImage.deleteAndrelease("D:\\Document\\rightImageShow\\Rect_A1.jpg");
   }
   

}

