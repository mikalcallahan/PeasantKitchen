package framework;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashMap;


/*
 * Stores all of the information associated with a user
 * If this class starts to explode with data, we can move some of the data elsewhere
 */

public class User implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 6793586014562415022L;
    public String username;
    public String emailAddress; //super secure
    public String profilePictureName;
    public Boolean signedIn = false;
    public String password; //also super secure
    public String firstname = "";
    public String lastname = "";
    public String ppn;
    public String diets = "";

    //public HashSet<String> diets = new HashSet<String>(); //All of the diets' that the user is following.
    //(This would include the user being vegan, for example)

    public User()
    {

    }


    public User(String jsonMessage)
    {

    }

    public User(User user)
    {
        this.username = user.username;
        this.emailAddress = user.emailAddress;
        this.profilePictureName = user.profilePictureName;
        this.signedIn = user.signedIn;
        this.password = user.password;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.ppn = user.ppn;
        this.diets = user.diets;
    }

    public boolean isSignedIn()
    {
        return !(this.signedIn == null || !this.signedIn);

    }

    public LinkedHashMap<String, Object> toMap()
    {
        LinkedHashMap<String, Object> fieldNameToValue = new LinkedHashMap<String, Object>();

        try
        {
            for (Field field : this.getClass().getFields())
                fieldNameToValue.put(field.getName(), field.get(this));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }

        return fieldNameToValue;
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof User))
            return false;

        User otherUser = (User) other;

        this.sameObject(this.diets, otherUser.diets);
        this.sameObject(this.emailAddress, otherUser.emailAddress);
        this.sameObject(this.firstname, otherUser.firstname);
        this.sameObject(this.lastname, otherUser.lastname);
        this.sameObject(this.password, otherUser.password);
        this.sameObject(this.ppn, otherUser.ppn);
        this.sameObject(this.profilePictureName, otherUser.profilePictureName);
        this.sameObject(this.signedIn, otherUser.signedIn);
        this.sameObject(this.username, otherUser.username);

        return true;
    }

    private boolean sameObject(Object a, Object b)
    {
        if (a == null && b == null)
            return true;
        else if (a == null || b == null)
            return false;
        else
            return a.equals(b);
    }

    //where keys = unique identifier for recipe\category of food in the database.
    public class likes
    {
        public HashSet<String> recipeKeys = new HashSet<String>();
        public HashSet<String> foodCatagoryKeys = new HashSet<String>();
    }
}
