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
   

   public static void main(String[] args) throws Exception{
        // TODO code applicati logic here
        EstablishConnection();
       User user1 = new User();
       user1.firstname = "Jimmy";
       user1.lastname = "buffet";
       user1.username = "ParrotHead69";
       user1 = createUser(user1);
       System.out.println(user1);
      
   }

       
    
    public static void EstablishConnection(){
       
        
        try {
          String host= "jdbc:sqlserver://localhost:1433;databaseName=PK";
          String uName = "sa";
          String uPass = "peasantkitchen";
          Connection con = DriverManager.getConnection(host,uName,uPass);
          System.out.println("Database Connection Established");
        }
        catch(SQLException err){
         System.out.println(err.getMessage());   
    }
    }



    /*
public User getUser(String username)
	{
		//Do whatever is nessessary to query the database....
		//then: construct a User object, and set the signin flag as appropriate.
		
		//Please return null if a user with the requested username cannot be found
		boolean userExists;
		//User user = new User();
                userExists = getUserByName(userName);
                if(userExists == 1){
		Statement stmt1 = con.createStatement();
                String sql1 = "UPDATE userInfo " + "SET signedIn = 1 WHERE username = 'userName'";
                stmt1.executeUpdate(sql1);
                Statement stmt2 = con.createStatement();
                String sql2 = "SELECT username,firstName,lastName,email,password FROM userInfo";
                ResultSet rs = stmt2.executeQuery(sql2);
                
                }
		
		return user;
	}
public boolean userNameCheck(String userName){
    
       //connection is the Connection object used to connect to my Access database.
    Statement statement = this.con.createStatement();
    //"Users" is the name of the table, "Username" is the primary key.
    String sql = "SELECT * FROM userInfo WHERE username = '" + userName + "'";
    Result result = statement.executeQuery(sql);
    //There is no need for a loop because the primary key is unique.
    return result.next();
}

public User getUserByName(String userName) 
    {
        // Returns null if no matching entry is found in the map.
        if(userNameCheck(userName))== true;
        return this.userByName.get(userName);
    }

public static User createUser(User tempUserObject)
	{
	String insertUser = buildInsertUser(tempUserObject);	
            ResultSet rs = stmt1.executeQuery(insertUser);
             try{
                 
             }   
            return null;
	}
}

public String buildInsertUser(User tempUserObject)
{
 
    String Uname = tempUserObject.username;
    String Fname = "";
    String Lname = "";
    String email = tempUserObject.emailAddress;
    String pw = tempUserObject.password;
    String PPName = tempUserObject.profilePictureName;
    String diet = "";
    boolean sIn = tempUserObject.signedIn;
    
    StringBuilder buildInsertUser = new StringBuilder();

    buildInsertUser.append("INSERT INTO userInfo VALUES ");
    
    buildInsertUser.append("(");
    buildInsertUser.append(Uname);
    buildInsertUser.append(",");
    buildInsertUser.append(Fname);
    buildInsertUser.append(",");
    buildInsertUser.append(Lname);
    buildInsertUser.append(",");
    buildInsertUser.append(email);
    buildInsertUser.append(",");
    buildInsertUser.append(pw);
    buildInsertUser.append(",");
    buildInsertUser.append(sIn);
    buildInsertUser.append(",");
    buildInsertUser.append(PPName);
    buildInsertUser.append(",");
    buildInsertUser.append(diet);
    buildInsertUser.append(")");

    return buildInsertUser.toString();
}
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
System.out.println(tempUserObject.username);
		//do stuff with the command.
System.out.println(sqlCommand);
		return new User();
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
