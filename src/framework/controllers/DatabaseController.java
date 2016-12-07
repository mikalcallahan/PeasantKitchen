package framework.controllers;

import com.sun.appserv.server.LifecycleEvent;
import designPatterns.ObserverSubject;
import framework.Recipes;
import framework.User;

import java.util.ArrayList;

/**
 * The type Database controller.
 */
public abstract class DatabaseController extends ObserverSubject
{
    /**
     * Gets recipes containing ingredients.
     *
     * @param cleanedIngredients the cleaned ingredients
     * @return the recipes containing ingredients
     */
    public abstract Recipes getRecipesContainingIngredients(ArrayList<String> cleanedIngredients);

    /**
     * Gets recipes with only these ingredients.
     *
     * @param cleanedIngredients the cleaned ingredients
     * @return the recipes with only these ingredients
     * @throws Exception the exception
     */
    public abstract Recipes getRecipesWithOnlyTheseIngredients(ArrayList<String> cleanedIngredients) throws Exception;

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     * @throws Exception the exception
     */
    public abstract User getUser(String username) throws Exception;

    /**
     * Create user user.
     *
     * @param tempUserObject the temp user object
     * @return the user
     */
    public abstract User createUser(User tempUserObject);

    /**
     * Sign in user user.
     *
     * @param username the username
     * @return the user
     * @throws Exception the exception
     */
    public abstract User signInUser(String username) throws Exception;

    /**
     * Sign out user user.
     *
     * @param username the username
     * @return the user
     * @throws Exception the exception
     */
    public abstract User signOutUser(String username) throws Exception;

    /**
     * Remove user user.
     *
     * @param username the username
     * @return the user
     * @throws Exception the exception
     */
    public abstract User removeUser(String username) throws Exception;

    /**
     * Server startup tasks lifecycle event.
     *
     * @param startupEvent the startup event
     * @return the lifecycle event
     */
//server related methods
    public abstract LifecycleEvent serverStartupTasks(LifecycleEvent startupEvent);

    /**
     * Server shutdown tasks lifecycle event.
     *
     * @param shutdownEvent the shutdown event
     * @return the lifecycle event
     */
    public abstract LifecycleEvent serverShutdownTasks(LifecycleEvent shutdownEvent);
    

}
