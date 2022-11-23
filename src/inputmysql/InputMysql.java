package inputmysql;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import gongJu.JDBCUtils;

public class InputMysql {
	 Connection connection;
	    private static String driver;
	    private static String url;
	    private static String username;
	    private static String password;
	    public InputMysql(){
	        try {
	            Properties p = new Properties();
	            try {
	                p.load(new FileReader("jdbc.properties"));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            driver = p.getProperty("driver");
	            url = p.getProperty("url");
	            username = p.getProperty("user");
	            password = p.getProperty("password");
	            Class.forName(driver);//加载驱动
	        } catch (ClassNotFoundException e) {
	            System.out.println("找不到驱动程序类，加载驱动失败！");
	            e.printStackTrace();
	        }
	        try {
	            this.connection = DriverManager.getConnection(url,username,password);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    	
//	        try {
//	            Class.forName("com.mysql.cj.jdbc.Driver");
//	            connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gramemarker?serverTimezone=GMT%2B8&useSSL=false","root","nevergiveupyw51");
//	        } catch (ClassNotFoundException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        } catch (SQLException e) {
//	            throw new RuntimeException(e);
//	        }
	    }
	    public void MysqlClose() throws SQLException {
	    	connection.close();
			System.out.print("数据库关闭成功");			
		}
	    //添加正确的图片的信息
	    public void addErrorInformation(String fileName,String urlPath,int marker,double startx,double starty,double endx,double endy) throws SQLException {
	    	PreparedStatement preparedStatement = null;
	    	preparedStatement = connection.prepareStatement("insert into errorimage values(null,?,?,?,?,?,?,?)");
	    	preparedStatement.setString(1, fileName);
	    	preparedStatement.setString(2, urlPath);
	    	preparedStatement.setInt(3, marker);
	    	preparedStatement.setDouble(4, startx);
	    	preparedStatement.setDouble(5, starty);
	    	preparedStatement.setDouble(6, endx);
	    	preparedStatement.setDouble(7, endy);
	    	int num = preparedStatement.executeUpdate();
//	    	System.out.println("坐标添加共影响到"+num+"行"+fileName+"  "+urlPath);
	    }
	    //添加到关联数据库
	    public void addRelevanceInformation(String rightpathiString,String errorpathString,String typeString,int difficultgrade,String promptString) throws SQLException {
//	    	//判断isdo
//	    	int minDifficult = myGetIsdoInforamtion();
//	    	int thisisdo = 0;
//	    	if(difficultgrade<minDifficult) {
//	    		thisisdo = 1;
//	    		modify(minDifficult);
//	    	}
//	    	if(difficultgrade==minDifficult) {
//				thisisdo = 1;
//			}
//	    	if(difficultgrade>minDifficult) {
//	    		thisisdo = 0;
//	    	}	    	
	    	PreparedStatement preparedStatement = null;
	    	preparedStatement = connection.prepareStatement("insert into relevance values(null,?,?,?,?,?,null)");
	    	preparedStatement.setString(1, rightpathiString);
	    	preparedStatement.setString(2, errorpathString);
	    	preparedStatement.setString(3, typeString);
	    	preparedStatement.setInt(4, difficultgrade);
	    	preparedStatement.setString(5, promptString);
//	    	preparedStatement.setInt(6, thisisdo);
	    	int num = preparedStatement.executeUpdate();
//	    	System.out.println("坐标添加共影响到"+num+"行"+rightpathiString+"  "+errorpathString+"  "+typeString+"  "+difficultgrade+"  "+promptString);
	    }
	    //添加关联数据库的时候先查看一下isdo
	    private int myGetIsdoInforamtion() throws SQLException {
	    	PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			int minDifficult = 0;
			preparedStatement = connection.prepareStatement("select min(difficult) from relevance ");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				minDifficult = resultSet.getInt(1);
			}
	    	return minDifficult;
	    }
	    //修改isdo
	    private void modify(int needModifyDifficult) throws SQLException {
	    	PreparedStatement preparedStatement = null;
	    	preparedStatement = connection.prepareStatement("update relevance set isdo=? where difficult=?");
	    	preparedStatement.setInt(1, 0);
	    	preparedStatement.setInt(2, needModifyDifficult);
	    	int a = preparedStatement.executeUpdate();
	    	System.out.println("isdo 更新:    "+a);
	    }
	    
	    public int getIsdoInformation() throws SQLException {
	    Connection connection = JDBCUtils.getConnection();
	    Statement statement =connection.createStatement() ;
	    String sql ="select min(difficult) from relevance " ;
	    ResultSet resultSet =statement.executeQuery(sql) ;
	    resultSet.next() ;
	    int d = resultSet.getInt("difficult") ;	    
	    String sql1 = "update relevance set isdo= '1' where difficult='"+d+"'" ;
	    statement.executeUpdate(sql1) ;
	    
	    //这个要在插入数据之后调用
	    return 1;
	    }

	    public static void main(String[] args) throws SQLException {
	    	InputMysql inputMysql = new InputMysql();
	    	inputMysql.addErrorInformation("A101", "C\\fime\\imga.jpg", 1, 1.22354, 1.2542, 1.234, 1.234);
//	    	inputMysql.addRelevanceInformation("aaaaa", "adadfa", "gansi", 4,"头盔");
	    }


	    
}

