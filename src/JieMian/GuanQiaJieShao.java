package JieMian;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import gongJu.JDBCUtils;
import layout.TableLayout;
import playgame.PlayGames2;
import playgamemysql.PlayGameMysql;

public class GuanQiaJieShao extends JFrame {

	private JPanel contentPane;
	private int diff;
	private String right;
	private String error;
	private ArrayList isdoArrayList;
	private boolean reward;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//allcount关卡
					ArrayList arrayList = new ArrayList(1);
					GuanQiaJieShao frame = new GuanQiaJieShao(2,"sajdfklfj",arrayList,true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public GuanQiaJieShao(int allcount,String playNameString,ArrayList isdoArrayList,boolean reward) throws SQLException {
		information(allcount);
		this.isdoArrayList = isdoArrayList;
		this.reward = reward;
		int allErrorNumber = checkErrorNumber(this.error);
		
		double fill = TableLayout.FILL ;
		double [][] size = {{fill,fill,fill,fill,fill,fill,fill,fill,fill,fill,fill},
							{fill,fill,fill,fill,fill,fill,fill}} ;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900,650);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new TableLayout(size));
		setContentPane(contentPane);
		
		JLabel jLabel =new JLabel("",SwingUtilities.CENTER) ;
		String text = "第"+allcount+"关" ;
		jLabel.setText(text);
		jLabel.setFont(new Font("宋体",Font.PLAIN,85));
		jLabel.setForeground(Color.red);
		contentPane.add(jLabel,"4,0,6,0") ;
		
		JLabel jLabel2 =new JLabel("",SwingUtilities.CENTER) ;
		String text2_1 = "&nbsp&nbsp通关条件" ; 
		String text2_2 ="1.找出"+allErrorNumber+"个违章点" ;//这个需要开数据库找
		String text2_3 = "2.允许找错次数随机5~8次" ;
		String text2 = "<html><body>"+text2_1+"<br><br>"+text2_2+"<br>"+text2_3+"</body></html>" ;
		jLabel2.setText(text2);
		jLabel2.setFont(new Font("宋体",Font.PLAIN,70));
		contentPane.add(jLabel2,"1,1,9,4") ;

		JButton jButton =new JButton("确定") ;
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							PlayGames2 frame = new PlayGames2(error,playNameString,diff,GuanQiaJieShao.this.isdoArrayList,GuanQiaJieShao.this.reward);
							
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				GuanQiaJieShao.this.dispose();
			}
		});
		
		jButton.setBackground(Color.orange);
		jButton.setFont(new Font("宋体",Font.PLAIN,55));
		jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(jButton,"4,5,6,5");
		setVisible(true);
		
		
		
	}
	//数据查询获取随机的图片地址
	private void information(int allcount) {
		
		try {
			int elsecount = allcount ;
			Connection connection =JDBCUtils.getConnection();
			Statement statement =connection.createStatement() ;
			String sql1 = "select distinct difficult from relevance" ;//=====================================
			ResultSet rs1 = statement.executeQuery(sql1) ;
			while(elsecount>0) {
				rs1.next() ;
				elsecount--;
			}
			diff = rs1.getInt("difficult") ;
			//=========================
			String sql2 = "select rightpath,errorpath from relevance where difficult='"+diff+"'order by rand()" ;
			ResultSet rs = statement.executeQuery(sql2) ;
			rs.next() ;
			right = rs.getString("rightpath") ;
			error =  rs.getString("errorpath") ;
			
			JDBCUtils.close(statement, connection,rs);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	//执行数据库查看有错误点的个数
	private int checkErrorNumber(String errorImageUrl) throws SQLException {
		int number = 0;
		PlayGameMysql playGameMysql = new PlayGameMysql();
		ArrayList arrayList = playGameMysql.getCoordsinformation(errorImageUrl);
		number = arrayList.size();
		
		return number;
	}

}
