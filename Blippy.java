/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blippy;

import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jared
 */
public class Blippy extends Application
{

    public static String screen1ID = "mStart";
    public static String screen1File = "StartMenuFXML.fxml";
    public static String screen2ID = "mResults";
    public static String screen2File = "TableFXML.fxml";

    private Stage mStage;

    private static Blippy mInstance;
    private final Start mStart;
    private final Game mGame;
    private final GameSetup mGameSetup;
    private GameHelper mGameHelper; // not final because it resets
    private final Results mResults;
    private String currentScene;

    private Boolean isColorBlind;
    private Boolean isSoundTest;

    private final HashMap<String, Scene> mScenes;

    /**
     *
     * @return mStart
     */
    public Start getStart()
    {
        return mStart;
    }

    /**
     *
     * @return mGameSetup
     */
    public GameSetup getGameSetup()
    {
        return mGameSetup;
    }

    public String getCurrentScene()
    {
        return currentScene;
    }
    
    public Blippy()
    {
        super();

        mInstance = this;
        isColorBlind = false;
        isSoundTest = false;

        mStart = new Start(this);
        mGame = new Game(this, isColorBlind, isSoundTest);
        mGameSetup = new GameSetup(this);
        mResults = new Results(this);
        mGameHelper = new GameHelper(this, mGame);
        mScenes = new HashMap<>();

    }

/////////////////////////////////////////////////////////// Getters
    /**
     *
     * @return mInstance
     */
    public static Blippy getInstance()
    {
        return mInstance;

    }

    /**
     * Getter for isColorBlind.
     *
     * @return isColorBlind
     */
    public Boolean getIsColorBlind()
    {
        return isColorBlind;
    }

    /**
     * Getter for isSoundTest.
     *
     * @return isSoundTest
     */
    public Boolean getIsSoundTest()
    {
        return isSoundTest;
    }

    public List<Double> getFormatedResults()
    {
        return mGameHelper.getFormatedResults();
    }

/////////////////////////////////////////////////////////// Setters
    /**
     *
     *
     * @param pIsColorBlind
     */
    public void setIsColorBlind(Boolean pIsColorBlind)
    {
        isColorBlind = pIsColorBlind;
    }

    /**
     *
     * @param pIsSoundTest
     */
    public void setIsSoundTest(Boolean pIsSoundTest)
    {
        isSoundTest = pIsSoundTest;
    }

////////////////////////////////////////////////////////// Main Functions
    /**
     *
     *
     * @param pMessage
     */
    public void pauseGame(String pMessage)
    {
        mGameHelper.pauseGame(pMessage);
    }

    /**
     *
     *
     */
    public void resetGame()
    {
        // Overwrites the scene in the hashmap
        addScene("mGame", mGame.setup());

        if (mGameHelper.isTimelineSet())
        {
            // Reset the game
            mGameHelper.killTimeline();
        }
        mGameHelper = new GameHelper(this, mGame);
        setup("mGame");
    }
    
    public void showAbout()
    {
        String message = "\n                 Created by "
                + "\n                Josh Comish, "
                + "\n              Jared Wadworth,\n "
                + "           and John Michelsen \n"
                + " in December of 2013 for Mr. Call\n";
        mGameHelper.pauseGame(message);
    }

    public void generateResults()
    {
        mResults.setResults(mGameHelper.getFormatedResults());
        addScene("mResults", mResults.setup());
        setup("mResults");
    }

    /**
     *
     */
    private void initializeScenes()
    {
        // sets up the game screen
        addScene("mGame", mGame.setup());
        
        addScene("mStart", mStart.setup());

        // sets up FXML
//        ScreensController mainContainer = new ScreensController(this);
//        addScene(screen1ID, mainContainer.loadScreen(Blippy.screen1ID,
//                                                     Blippy.screen1File));
    }

    /**
     *
     * @param pName
     * @param pScene
     */
    private void addScene(String pName, Scene pScene)
    {
        mScenes.put(pName, pScene);
    }

    /**
     *
     * @param pName
     */
    public void setup(String pName)
    {
        Scene scene = mScenes.get(pName);
        mStage.setScene(scene);
        
        currentScene = pName;

        if( pName == "mGame" )
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    mGameHelper.game();
                }
            });
        }
    }

    /**
     * Start is the main start off point for blippy. It gets the stage, and
     * prepares all the other classes scenes. It grabs and displays the start
     * scene, and from there the user controls where it goes.
     *
     * @param primaryStage
     * @throws java.lang.InterruptedException
     */
    @Override
    public void start(Stage primaryStage) throws InterruptedException
    {
        mStage = primaryStage;

        initializeScenes();

        setup(screen1ID);

        mStage.setTitle("Blippy");
        mStage.show();
    }

    /**
     * The main() method is ignored in JavaFX applications.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
