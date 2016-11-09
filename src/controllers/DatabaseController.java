package controllers;

import designPatterns.Observer;
import designPatterns.ObserverSubject;
import framework.User;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseController extends ObserverSubject 
{
      private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc4.SQLServerDriver";
    private static final String DB_CONNECTION = "jdbc:sqlserver://localhost:1433;databaseName=PK";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "peasantkitchen";

	public DatabaseController()
	{
		
	}
        private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
                            DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

 public User getUser(String username) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
                database.User user = new database.User();

		String selectSQL = "SELECT * FROM user_info WHERE username = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			preparedStatement.setString(1,username);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
                        if (!rs.next()){
                            //return null;
                        }
                        else{
			while (rs.next()){
				user.username = rs.getString("username");
                                user.firstname = rs.getString("firstName");
                                user.lastname = rs.getString("lastName");
                                user.emailAddress = rs.getString("emailAddress");
                                user.password = rs.getString("password");
                                //user.signedIn = convertString("1");
                                user.ppn = rs.getString("profilePictureName");
                                user.diets = rs.getString("diets");
                                user.signedIn = convertString("1");
				

			}
                                               
                       }           
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

                 }
                
        return user;
	}
	
	public synchronized User createUser(User tempUserObject)
	{
            
            Connection dbConnection = null;
            PreparedStatement preparedStatement = null;
            String sql = "INSERT INTO user_info"+"(username,firstName,lastName,emailAddress,password,signedIn,profilePictureName,diets)" 
                + "VALUES" +"(?,?,?,?,?,?,?,?)";
    try{
        //String host= "jdbc:sqlserver://RYAN\\SQLEXPRESS:50977;databseName = PK";
        dbConnection = getDBConnection();
        preparedStatement = dbConnection.prepareStatement(sql);
        preparedStatement.setString(1, tempUserObject.username);
        preparedStatement.setString(2, tempUserObject.firstname);
        preparedStatement.setString(3, tempUserObject.lastname);
        preparedStatement.setString(4, tempUserObject.emailAddress);
        preparedStatement.setString(5, tempUserObject.password);
        preparedStatement.setBoolean(6,tempUserObject.signedIn);
        preparedStatement.setString(7,tempUserObject.ppn);
        preparedStatement.setString(8,tempUserObject.diets);
        preparedStatement.executeUpdate();
        preparedStatement.close();
      tempUserObject = updateSignedIn(tempUserObject.username);
             }   
             catch(SQLException e){
                 
            
	}            
              System.out.println(tempUserObject.username);  
		return tempUserObject;
    
            
	}
    private User deleteUser(User tempUserObject) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String deleteUserSQL = "DELETE user_info WHERE username = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(deleteUserSQL);
			preparedStatement.setString(1, tempUserObject.username);

			// execute delete SQL stetement
			preparedStatement.executeUpdate();
                        
			//System.out.println("User is deleted!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
            return  new User();
}
	

	
        private static User selectUserDiets(User tempUserObject) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT username, diets FROM user_info WHERE username = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			preparedStatement.setString(1,tempUserObject.username);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next() )	{	
		        String username = rs.getString("username");
                        String diets = rs.getString("diets");

                      // System.out.println("user : " + username);
                       // System.out.println("user diets : " + diets);
                        }

			

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
                return tempUserObject;
                
	}
public  User updateSignedIn(String username) throws SQLException {
             
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
                database.User user = new database.User();
                String updateSignedInSQL = "UPDATE user_info SET signedIn = ? "
				                  + " WHERE username = ?"; 
               
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(updateSignedInSQL);
                     
			preparedStatement.setString(1, "1");
                        preparedStatement.setString(2, username);
                        
			preparedStatement.executeUpdate();
                        user.signedIn = convertString("1");                                
                       // System.out.println("username : " + username);
			//System.out.println("SignedIn : " + user.signedIn);
                        
		        

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
return user;
	}
public  User updateSignedOut(String username) throws SQLException {
             
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
                database.User user = new database.User();
                String updateSignedOutSQL = "UPDATE user_info SET signedIn = ? "
				                  + " WHERE username = ?"; 
                
		try {
                       
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(updateSignedOutSQL);
			preparedStatement.setString(1,"0");
                        preparedStatement.setString(2,username);
                     
			// execute update SQL stetement
			preparedStatement.executeUpdate();
                        user.signedIn = convertString("0");                              
                        //System.out.println("username : " + username);
			//System.out.println("SignedIn : " + user.signedIn);
                        
		        

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
return user;
	}
       private Boolean convertString(String string)
       {
           if(string != "0")
               return true;
           else
               return false;
       }
	
}