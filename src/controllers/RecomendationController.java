package controllers;

import designPatterns.Observer;
import framework.Recipes;
import framework.User;

/**
 * The type Recomendation controller.
 */
public class RecomendationController implements Observer
{
    /**
     * Instantiates a new Recomendation controller.
     */
    public RecomendationController()
    {

    }

    /**
     * Recommend recipes.
     *
     * @param user the user
     * @return the recipes
     */
    public Recipes recommend(User user)
    {
        return null;
    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub

    }

}
