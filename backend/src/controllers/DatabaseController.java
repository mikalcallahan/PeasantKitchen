package controllers;

import designPatterns.Observer;
import designPatterns.ObserverSubject;
import framework.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.sql.DriverManager;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseController extends ObserverSubject 
{
     //private static final String DB_CONNECTION = "jdbc:sqlserver://RYAN\\SQLEXPRESS:50977;databseName=PK";
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc4.SQLServerDriver";
    private static final String DB_CONNECTION = "jdbc:sqlserver://localhost:1433;databaseName=PK";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "peasantkitchen";
	public DatabaseController()
	{
		
	}

 private Connection getDBConnection() {

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

		String selectSQL = "SELECT * FROM userInfo WHERE username = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			preparedStatement.setString(1,username);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
                        if (rs.next() == true)
			while (rs.next()){
				String uname = rs.getString("username");
                                String fname = rs.getString("firstName");
                                String lname = rs.getString("lastName");
                                String email = rs.getString("email");
                                String pw = rs.getString("password");
                                boolean signedIn = rs.getBoolean("signedIn");
                                String ppn = rs.getString("profilePictureName");
                                String diets = rs.getString("diets");
				user.username = uname;
                                user.firstname = fname;
                                user.lastname = lname;
                                user.emailAddress = email;
                                user.password = pw;
                                user.signedIn = signedIn;
                                user.ppn = ppn;
                                user.diets = diets;

			}
                        else { //should this be null or empty string(like in the constructor)
                            user.username = null;
                            user.firstname = null;
                            user.lastname = null;
                            user.emailAddress = null;
                            user.password = null;
                            user.signedIn = null;
                            user.ppn = null;
                            user.diets = null;
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
            String sql = "INSERT INTO userInfo"+"(username,firstName,lastName,email,password,signedIn,profilePictureName,diets)" 
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
      
             }   
             catch(SQLException e){
                 
            
	}            
              System.out.println(tempUserObject.username);  
		return tempUserObject;
    
            /*
		//DIS WORKS: "INSERT INTO userInfo"+"(username,firstName,lastName,email,password,signedIn,profilePictureName,diets)" + "VALUES" +"('Mitch082','bRyan','Mitchell','@gmail.com','fuckthis',1,'null','null')";

		LinkedHashMap<String, Object> keyValuePairs = new LinkedHashMap<String, Object>();
		keyValuePairs.put("username", tempUserObject.username);
		keyValuePairs.put("firstName", tempUserObject.firstname);
		keyValuePairs.put("lastName", tempUserObject.lastname);
		keyValuePairs.put("email", tempUserObject.emailAddress);
		keyValuePairs.put("password", tempUserObject.password);
		keyValuePairs.put("signedIn", false);
		keyValuePairs.put("profilePictureName", ""); //this field isn't populated as yet
		keyValuePairs.put("diets", ""); //nor is this one

		String sqlCommand = insertInto("userInfo", keyValuePairs);

		//do stuff with the command.

		return new User();
            */
	}
    private User deleteUser(User tempUserObject) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String deleteUserSQL = "DELETE userInfo WHERE username = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(deleteUserSQL);
			preparedStatement.setString(1, tempUserObject.username);

			// execute delete SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("User is deleted!");

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
	
	public synchronized User signInUser(String username)
	{
		//do the necessary stuff to sign a user in
		
		return null;
	}

	
	public synchronized User signOutUser(String username)
	{
		
		
		return null;
	}

	@Override
	public void addObserver(Observer observer) 
	{
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) 
	{
		this.observers.remove(observer);
	}
        private static User selectUserDiets(User tempUserObject) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT username, diets FROM userInfo WHERE username = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			preparedStatement.setString(1,tempUserObject.username);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next() )	{	
		        String username = rs.getString("username");
                        String diets = rs.getString("diets");

                       System.out.println("user : " + username);
                        System.out.println("user diets : " + diets);
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
}