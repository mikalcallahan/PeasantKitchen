/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 *
 * @author Ryan
 */
public class DataBase extends User{
   //private static final String DB_CONNECTION = "jdbc:sqlserver://RYAN\\SQLEXPRESS:50977;databseName=PK";
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc4.SQLServerDriver";
    private static final String DB_CONNECTION = "jdbc:sqlserver://localhost:1433;databaseName=PK";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "peasantkitchen";


   public static void main(String[] args) throws SQLException{
        // TODO code applicati logic here
       // EstablishConnection();
       User user1 = new User();
       user1.firstname = "Jimmy";
       user1.lastname = "buffet";
       user1.username = "ParrotHead69";
       user1.diets = "Meat Eater";
       user1.signedIn  = true;
       //updateSignedIn(user1.username,user1.signedIn);

       user1= selectUserDiets(user1);
      
       //user1 =createUser(user1);
     // user1 = deleteUser(user1);
      //System.out.println(user1.password);
      
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
  


    
/*
public boolean userNameCheck(User tempUserObject ){
    
     Connection dbConnection = null;
     PreparedStatement preparedStatement = null;
    
    String sql = "SELECT * FROM userInfo WHERE username = '" + tempUserObject.username + "'";
     result = statement.executeQuery(sql);
    //There is no need for a loop because the primary key is unique.
    return result.next();
}

public User getUserByName(String userName) 
    {
        // Returns null if no matching entry is found in the map.
        if(userNameCheck(userName))== true;
        return this.userByName.get(userName);
    }
*/
/*
public synchronized User signInUser(String username)
	{
		//do the nessessary stuff to sign a user in
            
		if(this.signedIn == null || this.signedIn.booleanValue() == false)
			return false;
		
		return true;
		return null;
	}
	
	public synchronized User signOutUser(String username)
	{
		
		
		return null;
	}
    public static void main(String[] args) {
        // TODO code application logic here
    }*/
    public static User createUser(User tempUserObject)
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


System.out.println(tempUserObject.username);
		//do stuff with the command.
System.out.println(sqlCommand);
		return new User();
*/
}
    private static User deleteUser(User tempUserObject) throws SQLException {

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
private static void updateSignedIn(String username,Boolean signedIn) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String updateSignedInSQL = "UPDATE userInfo SET signedIn = ? "
				                  + " WHERE userName = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(updateSignedInSQL);
                        
                        ResultSet rs = preparedStatement.executeQuery();
			preparedStatement.setBoolean(1, signedIn);
                        preparedStatement.setString(2, username);
			

			// execute update SQL stetement
			//preparedStatement.executeUpdate();

		 username = rs.getString("userName");
                        Boolean signIn = rs.getBoolean("signedIn");

		        System.out.println("username : " + username);
			System.out.println("SignedIn : " + signIn);

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

	}

	//yeah...I overthought this one in retrospect. Oh well, it should kinda work.
	//we could have just added each value from the User object one at a time into a StringBuilder object
	private static String insertInto(String tableName, LinkedHashMap<String, Object> keyValuePairs)
	{
		StringBuilder insertStatement = new StringBuilder("INSERT INTO " + tableName);

		insertStatement.append(addKeys(keyValuePairs));
		insertStatement.append(addValues(keyValuePairs));

		return insertStatement.toString();
	}

	//DIS WORKS: "INSERT INTO userInfo"+"(username,firstName,lastName,email,password,signedIn,profilePictureName,diets)" + "VALUES" +"('Mitch082','bRyan','Mitchell','@gmail.com','fuckthis',1,'null','null')";

	private static String addKeys(LinkedHashMap<String, Object> keyValuePairs)
	{
		StringBuilder insertStatement = new StringBuilder();

		insertStatement.append("(");

		boolean isFirst = true;
		String key;

		for(Map.Entry<String, Object> entry : keyValuePairs.entrySet()) {

			key = entry.getKey();
			if(isFirst) {
				insertStatement.append(key);
				isFirst = false;
			}
			else
				insertStatement.append("," + key);

		}

		insertStatement.append(")");

		return insertStatement.toString();
	}

	//DIS WORKS: "INSERT INTO userInfo"+"(username,firstName,lastName,email,password,signedIn,profilePictureName,diets)" + "VALUES" +"('Mitch082','bRyan','Mitchell','@gmail.com','fuckthis',1,'null','null')";


	private static String addValues(LinkedHashMap<String, Object> keyValuePairs)
	{
		StringBuilder values = new StringBuilder("VALUES");
		boolean isFirst = true;

		values.append("(");

		for(Map.Entry<String, Object> entry : keyValuePairs.entrySet())
		{
			if(isFirst) {
				values.append("'" + entry.getValue() + "'");
				isFirst = false;
			}
			else
				values.append(",'" + entry.getValue() + "'");
		}

		values.append(")");

		return values.toString();
	}

}
