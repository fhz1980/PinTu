package playgamemysql;
/**
 * 2022.11.9记相对路径成功点击正确后没反应。
 */

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;

public class PlayGameMysql {
	Connection connection;
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    public PlayGameMysql(){
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
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gramemarker?serverTimezone=GMT%2B8&useSSL=false","root","nevergiveupyw51");
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
    public void MysqlClose() throws SQLException {
    	connection.close();
		System.out.print("数据库关闭成功");			
	}
    // 从错误图片数据库中获取到对应的图片地址
    public String[] getaddressinformation(String addressString) throws SQLException {
    	String alladdressString[] = new String[2];
    	PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		preparedStatement = connection.prepareStatement("select rightpath,errorpath from relevance WHERE errorpath=?");
		preparedStatement.setString(1, addressString);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			alladdressString[0] = resultSet.getString(1);
			alladdressString[1] = resultSet.getString(2);
//			System.out.println(resultSet.getString(1)+resultSet.getString(2));
		}
		return alladdressString;
    }
    //获取提示信息
    public String getPromptinformation(String addressErrorString) throws SQLException {
    	String promptInformationString = null;
    	PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		preparedStatement = connection.prepareStatement("select prompt from relevance WHERE errorpath=?");
		preparedStatement.setString(1, addressErrorString);
		resultSet = preparedStatement.executeQuery();
    	while(resultSet.next()) {
    		promptInformationString = resultSet.getString(1);
    	}
    	return promptInformationString;
    }
    
    // 获取错误图片框的信息
    public ArrayList getCoordsinformation(String addressString) throws SQLException {
    	ArrayList markersAndCoordsArrayList = new ArrayList();
    	PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		preparedStatement = connection.prepareStatement("select marker,startx,starty,endx,endy from errorimage WHERE errorpath=?");
		preparedStatement.setString(1, addressString);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
	    ArrayList markerAndCoordsArrayList = new ArrayList();
		markerAndCoordsArrayList.add(resultSet.getInt(1));
		markerAndCoordsArrayList.add(resultSet.getDouble(2));
		markerAndCoordsArrayList.add(resultSet.getDouble(3));
		markerAndCoordsArrayList.add(resultSet.getDouble(4));
		markerAndCoordsArrayList.add(resultSet.getDouble(5));
		markersAndCoordsArrayList.add(markerAndCoordsArrayList);
//		System.out.println(resultSet.getInt(1)+" startx: "+resultSet.getDouble(2)+" starty: "+resultSet.getDouble(3)+"  endx: "+resultSet.getDouble(4)+"  endy: "+resultSet.getDouble(5));
		}
		return markersAndCoordsArrayList;
    }
    //保存分数
    public void saveGrade(String playName,int abtainGrade) throws SQLException {
    	int beforeGrade = getBeforeGrade(playName);
    	int newAndGrade = beforeGrade+abtainGrade;
    	PreparedStatement preparedStatement = null;
    	preparedStatement = connection.prepareStatement("update user set Ustar=? where Uname=?");
    	preparedStatement.setInt(1, newAndGrade);
    	preparedStatement.setString(2, playName);
    	int a = preparedStatement.executeUpdate();
    	System.out.println("数据更新成功,共影响到  ："+a+"   行");
    }
    private int  getBeforeGrade(String playNameString) throws SQLException {
    	int beforeGrade = 0;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	preparedStatement = connection.prepareStatement("select Ustar from user where Uname=?");
    	preparedStatement.setString(1, playNameString);
    	resultSet = preparedStatement.executeQuery();
    	while(resultSet.next()) {
    		beforeGrade = resultSet.getInt(1);
    		System.out.println("之前的分数为"+beforeGrade);
    	}
    	
    	
    	return beforeGrade;
    }
    //数据库查询玩家名字是否真的存在
    public boolean checkNameExist(String playerNameString) throws SQLException {
    	boolean resultBoolean = false;
    	ArrayList allPlayerNameArrayList = new ArrayList();
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	preparedStatement = connection.prepareStatement("select Uname from user");
    	resultSet = preparedStatement.executeQuery();
    	while(resultSet.next()) {
    		allPlayerNameArrayList.add(resultSet.getString(1));
    	}
    	if(allPlayerNameArrayList.contains(playerNameString)) {
    		resultBoolean = true;
    	}
    	return resultBoolean;
    }
    public void updateStar(String playerNameString) throws SQLException {
    	PreparedStatement preparedStatement = null;
    	preparedStatement = connection.prepareStatement("update user set Ustar=? where Uname=?");
    	preparedStatement.setInt(1,0);
    	preparedStatement.setString(2, playerNameString);    	
    	int a = preparedStatement.executeUpdate();
    	System.out.println("star    :"+"更新影响到:"+a);
    }
    public int allPass() throws SQLException {
    	int passNumber = 0;
    	PreparedStatement preparedStatement =null;
    	ResultSet resultSet =null;
    	preparedStatement = connection.prepareStatement("select DISTINCT difficult from relevance order by difficult");
    	resultSet =preparedStatement.executeQuery();
    	while(resultSet.next()) {
    		passNumber++;
    		
    	}
    	return passNumber;
    }
    //保存人名
    public void savePlayerNmae(String name) throws SQLException {
    	PreparedStatement preparedStatement =null;
    	preparedStatement = connection.prepareStatement("insert into user values(null,?,0)");
    	preparedStatement.setString(1, name);
    	preparedStatement.executeUpdate();
    }
    
    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
    	PlayGameMysql playGameMysql = new PlayGameMysql();
//    	String allString[] = playGameMysql.getaddressinformation("D:\\Document\\ImageGame\\A1reference.jpg");
//    	ArrayList juArrayLists = playGameMysql.getCoordsinformation("D:\\Document\\ImageGame\\A1reference.jpg");
//    	System.out.println("sdlkjflsakdjfsdk;f;sdlfjkfjaadlskfjs;dalkfj    :"+juArrayLists.size());  	
//		BufferedImage bufferedImage = ImageIO.read(new FileInputStream("D:\\Document\\ImageGame\\A1reference.jpg"));
//		int juImageheight = bufferedImage.getHeight();
//		int juImagewidth = bufferedImage.getWidth();
//		ArrayList juArrayList = (ArrayList) juArrayLists.get(0);
//		int juStartx = (int)(juImagewidth*(double)juArrayList.get(1));
//		int juStarty = (int)(juImageheight*(double)juArrayList.get(2));
//		int juEndx = (int)(juImagewidth*(double)juArrayList.get(3));
//		int juEndy = (int)(juImageheight*(double)juArrayList.get(4));
//		System.out.println(juStartx+"   "+juStarty+"   "+juEndx+"   "+juEndy);
//		System.out.println(allString[0]+"          "+allString[1]);
//		String iString = playGameMysql.getPromptinformation("D:\\friend with happy time\\二狗柳州之旅\\微信图片_20220709202126.jpg");
//		System.out.println("aaaaaaaaaaaaaaaaaaaaaaa"+iString);
//    	playGameMysql.saveGrade("知足的高手", 8);
    	playGameMysql.updateStar("大忙的空中");
    }
}
