package JieMian;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import gongJu.JDBCUtils;
import layout.TableLayout;

public class PaiHangBang extends JFrame {

	private JPanel contentPane;
	private int screenWidth;
	private int screenHeight;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaiHangBang frame = new PaiHangBang();
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
	public PaiHangBang() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ����
		screenWidth = (int) screensize.getWidth();//�����Ļ�ÿ�1920
		screenHeight = (int) screensize.getHeight();//�����Ļ�ø�1080

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("������");
		setVisible(true);
		setSize((int) (this.screenWidth*0.6), (int)(this.screenHeight*0.6));
//		setBounds(100, 100, 578, 555);
		setLocationRelativeTo(null);


		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		double fill = TableLayout.FILL ;
		double [][] size = {{fill,fill,fill},{30,fill,fill,fill,100,100}} ;
		TableLayout tableLayout = new TableLayout(size);
		contentPane.setLayout(tableLayout);
		setContentPane(contentPane);
		
		MyTableModel myTableModel =MyTableModel.getTableModel(getTableBody()) ;
		
		JTable jTable =new JTable(myTableModel) ;
		//���ñ��ͷ
		JTableHeader jTableHeader =jTable.getTableHeader() ;
		jTableHeader.setReorderingAllowed(false);//�����в����϶�
		jTableHeader.setFont(new Font(null,Font.BOLD,30));
        jTableHeader.setForeground(Color.RED);
        //���ñ����
        jTable.setFont(new Font(null,Font.PLAIN,27));
        jTable.setForeground(Color.black);
        jTable.setGridColor(Color.BLACK);
        jTable.setRowHeight(50);
        
		JScrollPane jScrollPane = new JScrollPane(jTable) ;
		contentPane.add(jScrollPane,"0,0,2,4") ;
		//�˳�
		JButton quitJButto = new JButton("�˳�");
		quitJButto.setBackground(Color.CYAN);
		quitJButto.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		quitJButto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(quitJButto,"0,5,2,5");
		quitJButto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PaiHangBang.this.dispose();
			}
		});

		
	}

	public Vector<Vector<Object>> getTableBody(){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>() ;
	    try {
			Connection connection = JDBCUtils.getConnection() ;
			Statement statement = connection.createStatement() ;
			String sql ="select * from user order by Ustar DESC" ;
			ResultSet resultSet = statement.executeQuery(sql) ;
			int n = 1 ;//����
			while(resultSet.next()) {
				Vector<Object> row = new Vector<Object>();
				row.addElement(n);
				row.addElement(resultSet.getString("Uname"));
				row.addElement(resultSet.getInt("Ustar"));
				n++ ;
				data.addElement(row);
			}
			
			JDBCUtils.close(statement, connection,resultSet);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
		return data ;
	}
	
	
	
}


class MyTableModel extends DefaultTableModel{
	static Vector<String> head = new Vector<String>() ;
	
	private static MyTableModel myTableModel = new MyTableModel() ;
	
	public static MyTableModel getTableModel(Vector<Vector<Object>> data) {
		head = new Vector<String>() ;
		head.addElement("����");
		head.addElement("�û�");
		head.addElement("������");
		myTableModel.setDataVector(data, head);
		return myTableModel ;
	}
	private MyTableModel() {
		super(null,head);
	}
	
	@Override//���ñ�����ݲ����޸�
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
