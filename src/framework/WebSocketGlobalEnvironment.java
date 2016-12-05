package framework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllersImplementations.BackendControllerImpl;
import framework.controllers.BackendController;

/**
 * The type Web socket global environment.
 */
public class WebSocketGlobalEnvironment
{
    private static WebSocketGlobalEnvironment environment;

    private Gson jsonConverter;
    private BackendController backendController;

    private WebSocketGlobalEnvironment()
    {
        this.backendController = new BackendControllerImpl();
        this.jsonConverter = new GsonBuilder().create();
    }

    /**
     * Instance web socket global environment.
     *
     * @return the web socket global environment
     */
    public static WebSocketGlobalEnvironment instance()
    {
        if (WebSocketGlobalEnvironment.environment == null)
            WebSocketGlobalEnvironment.environment = new WebSocketGlobalEnvironment();

        return WebSocketGlobalEnvironment.environment;
    }

    /**
     * Gets backend controller.
     *
     * @return the backend controller
     */
    public BackendController getBackendController()
    {
        return this.backendController;
    }

    /**
     * Gets json converter.
     *
     * @return the json converter
     */
    public Gson getJsonConverter()
    {
        return this.jsonConverter;
    }
}