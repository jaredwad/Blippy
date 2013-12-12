/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blippy;

import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 *
 * @author Jared
 */
public class Blippy
        extends Application
{
    /**
     * DOCUMENT ME!
     */
    private Stage mStage;

    /**
     * DOCUMENT ME!
     */
    private static Blippy mInstance;

    /**
     * DOCUMENT ME!
     */
    private final Start mStart;

    /**
     * DOCUMENT ME!
     */
    private final Game mGame;

    /**
     * DOCUMENT ME!
     */
    private GameHelper mGameHelper; // not final because it resets

    /**
     * DOCUMENT ME!
     */
    private final Results mResults;

    /**
     * DOCUMENT ME!
     */
    private String currentScene;

    /**
     * DOCUMENT ME!
     */
    private Boolean isColorBlind;

    /**
     * DOCUMENT ME!
     */
    private Boolean isSoundTest;

    /**
     * DOCUMENT ME!
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
        mGame = new Game(this, isColorBlind, isSoundTest);
        mResults = new Results(this);
        mGameHelper = new GameHelper(this, mGame);
        mScenes = new HashMap<String, Scene>();
    }
    
    /////////////////////////////////////////////////////////// Getters

    
    /**
     *
     * @return mStart
     */
    public Start getStart()
    {
        return mStart;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getCurrentScene()
    {
        return currentScene;
    }

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

    /**
     * DOCUMENT ME!
     * used?
     * @return DOCUMENT ME!
     */
    public double[] getResults()
    {
        return mGameHelper.getResults();
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

        if( mGameHelper.isTimelineSet() )
        {
            // Reset the game
            mGameHelper.killTimeline();
        }

        mGameHelper = new GameHelper(this, mGame);
        setup("mGame");
    }
    
    /**
     * 
     */
    public void stopGame()
    {
        mGameHelper.killTimeline();
    }

    /**
     * DOCUMENT ME!
     */
    public void showAbout()
    {
        String message = "\n                 Created by "
                         + "\n                Josh Comish, "
                         + "\n              Jared Wadworth,\n "
                         + "           and John Michelsen \n"
                         + " in December of 2013 for Mr. Call\n";
        mGameHelper.pauseGame(message);
    }

    /**
     * DOCUMENT ME!
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
     *
     */
    private void initializeScenes()
    {
        // sets up start screen
        addScene("mStart", mStart.setup());
        
        // sets up the game screen
        addScene("mGame", mGame.setup());
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
