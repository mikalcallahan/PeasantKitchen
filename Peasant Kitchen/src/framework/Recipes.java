package framework;

import designPatterns.Visitor;

import java.util.ArrayList;

/*
 * Created for future flexibility
 */

public class Recipes extends ArrayList<Recipe>
{
    /**
     *
     */
    private static final long serialVersionUID = -136498257016695905L;

    public void visit(Visitor<Recipe> visitor)
    {
        for (Recipe recipe : this)
            visitor.visit(recipe);
    }
}