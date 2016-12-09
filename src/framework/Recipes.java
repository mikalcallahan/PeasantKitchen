package framework;

import designPatterns.Visitor;

import java.util.ArrayList;

public class Recipes extends ArrayList<Recipe>
{
    private static final long serialVersionUID = -136498257016695905L;

    /**
     * Accepts a visitor
     *
     * @param visitor the visitor
     */
    public void visit(Visitor<Recipe> visitor)
    {
        for (Recipe recipe : this)
            visitor.visit(recipe);
    }

    @Override
    public String toString()
    {
       StringBuilder str = new StringBuilder();

        for (Recipe recipe : this)
        {
            str.append(recipe.toString() + "\n");
        }

        return str.toString();
    }
}
