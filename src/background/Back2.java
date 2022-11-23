package background;



import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Back2 {
    public static void main(String[] args) {
        JFrame f1=new JFrame("插入背景图像");
        f1.setBounds(400, 200, 500, 300);
        f1.setVisible(true);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon img=new ImageIcon("D:\\Document\\picture\\background2.jpg");

        JLabel bg=new JLabel(img);
        f1.getLayeredPane().add(bg, new Integer(Integer.MIN_VALUE));
        bg.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        JPanel jp= (JPanel)f1.getContentPane();//强制类型转换
        jp.setOpaque(false);
        jp.setLayout(new FlowLayout());
        jp.add(bg);
    }
}
