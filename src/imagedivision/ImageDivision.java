package imagedivision;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageDivision {
	private String imageAddressString ;
	private String imageDivisionAfterAddressString;
	private int marker;
	private int startx;
	private int starty;
	private int endx;
	private int endy;
	
	
	
	public ImageDivision(String imageAddressString,int marker,int startx,int starty,int endx,int endy) {
		// TODO Auto-generated constructor stub
		this.imageAddressString = imageAddressString;
		this.marker = marker;
		int imagewidth = Math.abs(startx-endx)+1;
		int imageheight = Math.abs(starty-endy)+1;
		if(startx<endx) {
			if(starty<endy) {
				this.startx = startx;
				this.starty = starty;
				this.endx = endx;
				this.endy = endy;
			}
			else {
				this.startx = startx;
				this.starty = starty-imageheight;
				this.endx = endx;
				this.endy = endy+imageheight;				
			}
			
		}
		if(startx>endx) {
			if(starty>endy) {
				this.startx = endx;
				this.starty = endy;
				this.endx = startx;
				this.endy = starty;
			}
			else {
				this.startx = startx-imagewidth;
				this.starty = starty;
				this.endx = endx+imagewidth;
				this.endy = endy;
			}
		}
		System.out.println("转换坐标后的四个点分别是：     "+"   "+this.startx+" "+this.starty+"    "+this.endx+"    "+this.endy);
	}
	public ArrayList divisionImage() throws IOException {
		ArrayList imgeDicisionArrayList = new ArrayList();
		int imagewidth = Math.abs(startx-endx);
		int imageheight = Math.abs(starty-endy);
        String originalImg = imageAddressString;
        // 读入大图
        File file = new File(originalImg);
        FileInputStream fis = new FileInputStream(file);
        System.out.println(file.exists());
        /*主要作用是将一幅图片加载到内存中（BufferedImage生成的图片在内存里有一个图像缓冲区，
                    利用这个缓冲区我们可以很方便地操作这个图片），提供获得绘图对象、图像缩放、选择图像平滑度等功能，
                     通常用来做图片大小变换、图片变灰、设置透明不透明等。*/
        BufferedImage image = ImageIO.read(fis);//获取图片进行缓存
        // 计算每个小图的宽度和高度
        //大图中的一部分
        //设置小图的大小和类型
        BufferedImage imgs = new BufferedImage(imagewidth, imageheight, image.getType());//每次都在完整的图片上进行切割所以是每一次消耗一张图片
        // 上边是将图片添加到BufferedImage下边是创建对应的可以在当前图片上进行操作的绘图工具。
        //写入图像内容
        Graphics2D gr = (Graphics2D)imgs.getGraphics();;//创建可用于绘制BufferedImage的绘图工具
        //下边函数进行剪切，在当前BufferedImage数据上进行剪切sx1,322   214   1   1
        gr.drawImage(image, 0, 0, imagewidth, imageheight, startx, starty, endx, endy, null);
        //分割获取图片名字
        String imgeNameString =  getFileName(imageAddressString);
        imageDivisionAfterAddressString = "divisionImag/" + imgeNameString+ marker+ ".png";
		System.out.println("输出小图保存图片到文件夹中imageAddressString  :"+imageAddressString);
		System.out.println("输出小图保存图片到文件夹中imgeNameString  :"+imgeNameString);
		System.out.println("输出小图保存图片到文件夹中  :"+imageDivisionAfterAddressString);
		// 输出小图保存图片到文件夹中
        ImageIO.write(imgs, "png", new File(imageDivisionAfterAddressString));
        imgeDicisionArrayList.add(imageDivisionAfterAddressString);
        imgeDicisionArrayList.add(this.startx);
        imgeDicisionArrayList.add(this.starty);
        imgeDicisionArrayList.add(this.endx);
        imgeDicisionArrayList.add(this.endy);
        imgeDicisionArrayList.add(image);
        
		return imgeDicisionArrayList;
	}
	// 将图片名字分割出来
	public String getFileName(String urlpathString) {
		String fName  = urlpathString;
        fName = fName.trim();
        String temp[] = fName.split("[\\\\/]"); /**split里面必须是正则表达式，"\\"的作用是对字符串转义*/
        String fileName = temp[temp.length-1];
        System.out.println("fileName = " + fileName);
        String a[] = fileName.split("\\.");
        System.out.println(a[0]);
        System.out.println(a[1]);
		return a[0];
	}
	// 将图片删除释放文件夹
	public void deleteAndrelease(String aftertString) {
        File file1 = new File(aftertString);
        boolean resulte = file1.delete();	
        System.out.println(aftertString+"======>删除图片成功:  "+resulte);
	}
	public static void main(String[] args) throws IOException {
		ImageDivision imageDivision = new ImageDivision("C:\\parameter\\ImageGame\\A1.jpg", 8 ,322 , 214, 1,   1 );
		ArrayList arrayList= imageDivision.divisionImage();
//		imageDivision.deleteAndrelease(string);
	}
	
	

}
