package controllers;

import designPatterns.Observer;
import designPatterns.ObserverSubject;
import framework.DatabaseController;
import framework.Recipe;
import framework.Recipes;
import framework.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sun.appserv.server.LifecycleEvent;

public class SQLDatabaseController extends DatabaseController
{
	//private static final String DB_CONNECTION = "jdbc:sqlserver://RYAN\\SQLEXPRESS:50977;databseName=PK";
	private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc4.SQLServerDriver";
	private static final String DB_CONNECTION = "jdbc:sqlserver://localhost:1433;databaseName=PK";
	private static final String DB_USER = "sa";
	private static final String DB_PASSWORD = "peasantkitchen";
	
	
	public SQLDatabaseController()
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
	
	
	
   public Recipes getRecipeInfo(Recipes tempRecipe)throws SQLException
   {
       Connection dbConnection = null;
       PreparedStatement preparedStatement = null;
       
       Recipe temp = new Recipe();
       for(int i = 0; i <tempRecipe.size();i++)
       {
       String getRecipeNameSQL = "SELECT RECIPE_NAME, RECIPE_PROCESS,RECIPE_REQUIREMENTS FROM RECIPES WHERE R_ID = ? ";
       try {
         
            
            temp =tempRecipe.get(i);
            
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(getRecipeNameSQL);
			preparedStatement.setInt(1,temp.recipeID);
                        
			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next() )	{	
		        
                         temp.recipeName = rs.getString("RECIPE_NAME");
                         temp.recipeProcess = rs.getString("RECIPE_PROCESS");
                         temp.recipeRequirements = rs.getString("RECIPE_REQUIREMENTS");
                         
			
                      
                       System.out.println("Name: " + temp.recipeName);
                       System.out.println("Process: " + temp.recipeProcess);
                       System.out.println("Requirements: " + temp.recipeRequirements);
                     
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
       
       }
       return tempRecipe;
   }
   public Recipes getRecipeId(ArrayList<Integer> ingredientID)throws SQLException
   {
       Connection dbConnection = null;
       PreparedStatement preparedStatement = null;
       
       int ingredientId = 0;
       Recipes recipeResults = new Recipes();
       for(int i = 0; i < ingredientID.size(); i++) {
          
       String getRecipeIdSQL = "SELECT RECIPE_ID FROM RECIPE_INGREDIENTS WHERE INGREDIENT_ID = ? ";//
       
       try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(getRecipeIdSQL);
                        
                        ingredientId = ingredientID.get(i);
			preparedStatement.setInt(1,ingredientId);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
                        
			while (rs.next() )	
                        {	
		        Recipe tempRecipe = new Recipe();
                         tempRecipe.recipeID = rs.getInt("RECIPE_ID");
			recipeResults.add(tempRecipe);
                      
                       System.out.println("Recipe ID; " + tempRecipe.recipeID);
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
       }
       return getRecipeInfo(recipeResults);
   }
   public ArrayList<Integer> getIngredientId(ArrayList<String> ingredientsList)throws SQLException
   {
       Connection dbConnection = null;
       PreparedStatement preparedStatement = null;
       String ingredient = "";
       int IngredientID = 0;
       ArrayList<Integer> ingredientId = new ArrayList<Integer>();
       for(int i = 0; i < ingredientsList.size(); i++)
       {
           ingredient = ingredientsList.get(i);
       String getIngredientIdSQL = "SELECT INGREDIENT_ID FROM Ingredients WHERE IngrName = ? ";
       try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(getIngredientIdSQL);
			preparedStatement.setString(1,ingredient);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next() )	{	
		        
                         IngredientID = rs.getInt("INGREDIENT_ID");
			ingredientId.add(IngredientID);
                      
                       // System.out.println("Ingredient ID: " + IngredientID);
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
       
       }
   
       return ingredientId;
   }
   
	public Recipes getRecipesContainingIngredients(ArrayList<String> cleanedIngredients)
	{
            String ingredient = null;
            //int ingredientID = 0;
            Recipes recipes= new Recipes();
            ArrayList<String> ingredientsList= new ArrayList<String>();
            ArrayList<Integer> ingredientsListID= new ArrayList<Integer>();
            
            try
            {
                ingredientsList = cleanedIngredients;  
                ingredientsListID= getIngredientId(ingredientsList);
                Recipe recipe = new Recipe();
                recipes =getRecipeId(ingredientsListID);
            
            }
            
            
            catch(Exception e){
                System.err.println("getRecipesContainingIngredients blew up!");
            }
            
            return recipes;
	}
	
	public Recipes getRecipesWithOnlyTheseIngredients(ArrayList<String> cleanedIngredients)
	{
		
		
		return new Recipes();
	}
	

	public User getUser(String username) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		//database.User user = new database.User();
		
		User user = new User();

		String selectSQL = "SELECT * FROM user_info WHERE username = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			preparedStatement.setString(1,username);
			
			

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
			// if (rs.next() == true)
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


				//}
				// else { //should this be null or empty string(like in the constructor)
				//  return null;

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
			tempUserObject = signInUser(tempUserObject.username);
			preparedStatement.close();
		}   
		catch(SQLException e){


		}            
		//System.out.println(tempUserObject.username);  
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
	public User selectUserDiets(User tempUserObject) throws SQLException {

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
	public  User signInUser(String username) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		//database.User user = new database.User();
		String updateSignedInSQL = "UPDATE user_info SET signedIn = ? "
				+ " WHERE username = ?";
		
		User user = new User();

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
	public  User signOutUser(String username) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		String updateSignedOutSQL = "UPDATE user_info SET signedIn = ? "
				+ " WHERE username = ?"; 
		
		User user = new User();

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

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LifecycleEvent serverStartupTasks(LifecycleEvent startupEvent) {
		// TODO Auto-generated method stub
		return startupEvent;
	}

	@Override
	public LifecycleEvent serverShutdownTasks(LifecycleEvent shutdownEvent) {
		// TODO Auto-generated method stub
		return shutdownEvent;
	}

}