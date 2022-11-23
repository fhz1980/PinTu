package background;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Back1 extends JFrame{
    //创建一个容器
    JPanel jp;
    //创建背景面板。
    BackgroundPanel bgp;
    //创建一个按钮，用来证明我们的确是创建了背景图片，而不是一张图片。
    JButton jb;
    public static void main(String[] args){
        new Back1();
    }
    public Back1(){
//不采用任何布局方式。
        jp=(JPanel)this.getContentPane();
        this.setLayout(null);
//在这里随便找一张400*300的照片既可以看到测试结果。
        ImageIcon img=new ImageIcon("D:\\Document\\picture\\background2.jpg");
        bgp=new BackgroundPanel(img.getImage());
        bgp.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
//创建按钮
        jb=new JButton("测试按钮");
        bgp.add(jb);
        jp.add(bgp);
        this.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class BackgroundPanel extends JPanel{
    Image im;
    public BackgroundPanel(Image im){
        this.im=im;
        this.setOpaque(true);//设置透明的
    }
    //Draw the back ground.
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
    }


}

