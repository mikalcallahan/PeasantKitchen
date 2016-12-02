package constants;

import framework.AddnSet;

import java.io.File;

/**
 * The type Constants.
 */
public class Constants
{
    /**
     * The constant storedUsersObjectFileName.
     */
    public static final String storedUsersObjectFileName = "users.obj";
    /**
     * The constant storedRecipesObjectFileName.
     */
    public static final String storedRecipesObjectFileName = "recipies.obj";
    /**
     * The constant storedObjectsFolderName.
     */
    public static final String storedObjectsFolderName = "StoredObjects";
    /**
     * The constant databaseCSVFolder.
     */
    public static final String databaseCSVFolder = "DatabaseCSVs";
    /**
     * The constant recipesCSV.
     */
    public static final String recipesCSV = "RECIPES.csv";

    /**
     * The constant webSocketTestingDir.
     */
    public static final File webSocketTestingDir = new File("/home/stoffel/Documents/School/Software Engineering/SemesterProject/PeasantKitchen/backend/testing/web sockets/");
    /**
     * The constant applicationDataFolder.
     */
    public static final File applicationDataFolder = getApplicationDataFolder();

    private static File getApplicationDataFolder()
    {
        String glassfishDomainConfigFolder = System.getProperty("com.sun.aas.instanceRoot");

        if (glassfishDomainConfigFolder == null)
            return new File(".");
        else
            return new File(glassfishDomainConfigFolder).getParentFile();
    }

    /**
     * The type Application data.
     */
    public static class ApplicationData
    {
        /**
         * The constant openElement.
         */
        public static final String openElement = "<";
        /**
         * The constant closingElement.
         */
        public static final String closingElement = ">";
        /**
         * The constant elementSeperator.
         */
        public static final String elementSeperator = ",";
        /**
         * The constant quantityRange.
         */
        public static final String quantityRange = "-";

        /**
         * The constant defaultRecipeID.
         */
        public static final Integer defaultRecipeID = 1;
        /**
         * The constant defaultIngredientQuantity.
         */
        public static final Double defaultIngredientQuantity = 1.0;
    }

    /**
     * The type Contexts.
     */
    public static class Contexts
    {
        /**
         * The constant all.
         */
        public static AddnSet<String> all = new AddnSet<String>();

        /**
         * The type User.
         */
        public static class User
        {
            private static final String user = "/user";

            /**
             * The constant signIn.
             */
            public static final String signIn = all.put(user + "/signin");
            /**
             * The constant signOut.
             */
            public static final String signOut = all.put(user + "/signout");
            /**
             * The constant create.
             */
            public static final String create = all.put(user + "/create");
        }


//		private String descendingContextPath(ArrayList<String> contexts)
//		{
//			StringBuilder absolutePath = new StringBuilder();
//
//			for(String uri : contexts)
//				absolutePath.append("/" + uri);
//
//			return absolutePath.toString();
//		}
    }

    /**
     * The type Post web socket request keys.
     */
    public static class PostWebSocketRequestKeys
    {
        /**
         * The constant id.
         */
        public static final String id = "id";
        /**
         * The constant payload.
         */
        public static final String payload = "payload";
    }

    /**
     * The type Message i ds.
     */
    public static class MessageIDs
    {
        /**
         * The constant signInUser.
         */
        public static final String signInUser = "user.signin";
        /**
         * The constant signOutUser.
         */
        public static final String signOutUser = "user.signout";
        /**
         * The constant createNewUser.
         */
        public static final String createNewUser = "user.create";
    }

    /**
     * The type Standard response object keys.
     */
    public static class StandardResponseObjectKeys
    {
        /**
         * The constant keys.
         */
        public static AddnSet<String> keys = new AddnSet<String>();

        /**
         * The constant response.
         */
        public static final String response = keys.put("response");
        /**
         * The constant error.
         */
        public static final String error = keys.put("error");
    }

    /**
     * The type Exception message keys.
     */
    public static class ExceptionMessageKeys
    {
        /**
         * The constant keys.
         */
        public static AddnSet<String> keys = new AddnSet<String>();

        /**
         * The constant type.
         */
        public static final String type = keys.put("type");
        /**
         * The constant message.
         */
        public static final String message = keys.put("message");
        /**
         * The constant stackTrace.
         */
        public static final String stackTrace = keys.put("stackTrace");
    }
}
