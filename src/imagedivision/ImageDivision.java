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
		System.out.println("ת���������ĸ���ֱ��ǣ�     "+"   "+this.startx+" "+this.starty+"    "+this.endx+"    "+this.endy);
	}
	public ArrayList divisionImage() throws IOException {
		ArrayList imgeDicisionArrayList = new ArrayList();
		int imagewidth = Math.abs(startx-endx);
		int imageheight = Math.abs(starty-endy);
        String originalImg = imageAddressString;
        // �����ͼ
        File file = new File(originalImg);
        FileInputStream fis = new FileInputStream(file);
        System.out.println(file.exists());
        /*��Ҫ�����ǽ�һ��ͼƬ���ص��ڴ��У�BufferedImage���ɵ�ͼƬ���ڴ�����һ��ͼ�񻺳�����
                    ����������������ǿ��Ժܷ���ز������ͼƬ�����ṩ��û�ͼ����ͼ�����š�ѡ��ͼ��ƽ���ȵȹ��ܣ�
                     ͨ��������ͼƬ��С�任��ͼƬ��ҡ�����͸����͸���ȡ�*/
        BufferedImage image = ImageIO.read(fis);//��ȡͼƬ���л���
        // ����ÿ��Сͼ�Ŀ�Ⱥ͸߶�
        //��ͼ�е�һ����
        //����Сͼ�Ĵ�С������
        BufferedImage imgs = new BufferedImage(imagewidth, imageheight, image.getType());//ÿ�ζ���������ͼƬ�Ͻ����и�������ÿһ������һ��ͼƬ
        // �ϱ��ǽ�ͼƬ��ӵ�BufferedImage�±��Ǵ�����Ӧ�Ŀ����ڵ�ǰͼƬ�Ͻ��в����Ļ�ͼ���ߡ�
        //д��ͼ������
        Graphics2D gr = (Graphics2D)imgs.getGraphics();;//���������ڻ���BufferedImage�Ļ�ͼ����
        //�±ߺ������м��У��ڵ�ǰBufferedImage�����Ͻ��м���sx1,322   214   1   1
        gr.drawImage(image, 0, 0, imagewidth, imageheight, startx, starty, endx, endy, null);
        //�ָ��ȡͼƬ����
        String imgeNameString =  getFileName(imageAddressString);
        imageDivisionAfterAddressString = "divisionImag/" + imgeNameString+ marker+ ".png";
		System.out.println("���Сͼ����ͼƬ���ļ�����imageAddressString  :"+imageAddressString);
		System.out.println("���Сͼ����ͼƬ���ļ�����imgeNameString  :"+imgeNameString);
		System.out.println("���Сͼ����ͼƬ���ļ�����  :"+imageDivisionAfterAddressString);
		// ���Сͼ����ͼƬ���ļ�����
        ImageIO.write(imgs, "png", new File(imageDivisionAfterAddressString));
        imgeDicisionArrayList.add(imageDivisionAfterAddressString);
        imgeDicisionArrayList.add(this.startx);
        imgeDicisionArrayList.add(this.starty);
        imgeDicisionArrayList.add(this.endx);
        imgeDicisionArrayList.add(this.endy);
        imgeDicisionArrayList.add(image);
        
		return imgeDicisionArrayList;
	}
	// ��ͼƬ���ַָ����
	public String getFileName(String urlpathString) {
		String fName  = urlpathString;
        fName = fName.trim();
        String temp[] = fName.split("[\\\\/]"); /**split���������������ʽ��"\\"�������Ƕ��ַ���ת��*/
        String fileName = temp[temp.length-1];
        System.out.println("fileName = " + fileName);
        String a[] = fileName.split("\\.");
        System.out.println(a[0]);
        System.out.println(a[1]);
		return a[0];
	}
	// ��ͼƬɾ���ͷ��ļ���
	public void deleteAndrelease(String aftertString) {
        File file1 = new File(aftertString);
        boolean resulte = file1.delete();	
        System.out.println(aftertString+"======>ɾ��ͼƬ�ɹ�:  "+resulte);
	}
	public static void main(String[] args) throws IOException {
		ImageDivision imageDivision = new ImageDivision("C:\\parameter\\ImageGame\\A1.jpg", 8 ,322 , 214, 1,   1 );
		ArrayList arrayList= imageDivision.divisionImage();
//		imageDivision.deleteAndrelease(string);
	}
	
	

}
