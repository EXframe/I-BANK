import java.sql.*;

public class DBManager {
  Connection con;
  ResultSet rs;
  Statement stmt;
  public DBManager() {
    try {
      String url = "jdbc:sqlserver://HP:1433;DatabaseName=IBANK";
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	  String user="library";
	  String password="123456";
	  Connection con=DriverManager.getConnection(url,user,password);  
	  this.con=con;
	  stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      System.out.println("ok!");
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
    }
    catch (ClassNotFoundException cnfex) {
      cnfex.printStackTrace();
    }
  }

  public ResultSet getResult(String strSQL) {
    try {
      rs = stmt.executeQuery(strSQL);
      return rs;
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
      return null;
    }
  }

  public int updateSql(String strSQL) {
    try {
      int i=stmt.executeUpdate(strSQL);
      con.commit();
      return i;
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
      return -1;
    }
  }

  public void closeConnection() {
    try {
    	con.close();      
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
    }
  }

}
