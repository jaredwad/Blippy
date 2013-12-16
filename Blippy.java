package blippy.desktop;

import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class for the Blippy program,
 * it will initialize everything required for the
 * program to run.
 * 
 * @author Jared
 */
public class Blippy
        extends Application
{
    /**
     * Stage for Blippy
     */
    private Stage mStage;

    /**
     * The blippy class itself.
     */
    private static Blippy mInstance;

    /**
     * Start object.
     */
    private final Start mStart;

    /**
     * Game object.
     */
    private final Game mGame;

    /**
     * Gamehelper controller. Not final because
     * it resets.
     */
    private GameHelper mGameHelper;

    /**
     * Results of the test.
     */
    private final Results mResults;

    /**
     * The current scene.
     */
    private String currentScene;

    /**
     * to determine if colorblind.
     */
    private Boolean isColorBlind;

    /**
     * to determine if sound test.
     */
    private Boolean isSoundTest;

    /**
     * Storage for scenes.
     */
    private final HashMap<String, Scene> mScenes;

    
    /**
     * Creates a new Blippy object.
     */
    public Blippy()
    {
        super();

        mInstance = this;
        isColorBlind = false;
        isSoundTest = false;

        mStart = new Start(this);
        mGame = new Game(this);
        mResults = new Results(this);
        mGameHelper = new GameHelper(this, mGame);
        mScenes = new HashMap<String, Scene>();
    }
    
    /////////////////////////////////////////////////////////// Getters

    
    /**
     * Getter for mStart.
     * 
     * @return mStart
     */
    public Start getStart()
    {
        return mStart;
    }

    /**
     * Getter for currentScene.
     *
     * @return mGameSetup
     */
    public String getCurrentScene()
    {
        return currentScene;
    }

    /**
     * Getter for mInstance.
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

    /**
     * Getter for Results.
     *
     * @return mGameHelper.getResults
     */
    public double[] getResults()
    {
        return mGameHelper.getResults();
    }

    /////////////////////////////////////////////////////////// Setters
    /**
     * Setter for isColorblind
     *
     * @param pIsColorBlind
     */
    public void setIsColorBlind(Boolean pIsColorBlind)
    {
        isColorBlind = pIsColorBlind;
    }

    /**
     * Setter for isSoundTest
     *
     * @param pIsSoundTest
     */
    public void setIsSoundTest(Boolean pIsSoundTest)
    {
        isSoundTest = pIsSoundTest;
    }

    ////////////////////////////////////////////////////////// Main Functions
    /**
     * Calls pauseGame in mGameHelper
     *
     * @param pMessage
     */
    public void pauseGame(String pMessage)
    {
        mGameHelper.pauseGame(pMessage);
    }

    /**
     *  Resets the game and resets the timer.
     */
    public void resetGame()
    {
        // Overwrites the scene in the hashmap
        addScene("mGame", mGame.setup());

        if( mGameHelper.isTimelineSet() )
        {
            // Reset the game
            mGameHelper.killTimeline();
        }

        mGameHelper = new GameHelper(this, mGame);
        setup("mGame");
    }
    
    /**
     * Stops the time and resets the timer.
     */
    public void stopGame()
    {
        mGameHelper.killTimeline();
    }

    /**
     * Will bring up about page.
     */
    public void showAbout()
    {
        String message = "\n                 Created by "
                         + "\n               Joshua Comish, "
                         + "\n              Jared Wadworth,\n "
                         + "           and John Michelsen \n"
                         + " in December of 2013 for Mr. Call\n";
        mGameHelper.pauseGame(message);
    }

    /**
     * Builds the results screen.
     */
    public void generateResults()
    {
        // this ensures that results are only generated when
        // they are expected, and that the game doesn't call 
        // this in the background
        if( getCurrentScene() == "mGame" )
        {
            mResults.setResults(mGameHelper.getResults());
            addScene("mResults", mResults.setup());
            setup("mResults");
        }
    }

    /**
     * Sets up the screens for the program.
     */
    private void initializeScenes()
    {
        // sets up start screen
        addScene("mStart", mStart.setup());
        
        // sets up the game screen
        addScene("mGame", mGame.setup());
    }

    /**
     * Adds a scene.
     * @param pName
     * @param pScene
     */
    private void addScene(String pName, Scene pScene)
    {
        mScenes.put(pName, pScene);
    }

    /**
     * Builds all instances of all objects.
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
    public void start(Stage primaryStage)
            throws InterruptedException
    {
        mStage = primaryStage;

        initializeScenes();

        setup("mStart");

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
