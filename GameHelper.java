/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blippy;

import java.util.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Jared
 */
public class GameHelper
{

    private final Blippy mBlippy;
    private final Game mGame;
    private double mResults[];
    private List<Double> mFormatedResults;
//   private final double mLeftResults[];

    private long mStartTime;
    private long mEndTime;

    private final int mGameIterations;

    private Boolean isClicked;
    private List<Long> mTimeSnapshots;
    private EventHandler<ActionEvent> mOnFinished;
    private int mCountChanges;
    private static final int WAIT_PERIOD = 3000;

    private Timeline mTimeline;
    private Timeline clickTimer;
    /**
     * The beep mp3 file.
     */
    private final Media error;

    /**
     * Will play the beep mp3.
     */
    private MediaPlayer mp;

    /**
     *
     * @param pBlippy
     * @param pGame
     */
    public GameHelper(Blippy pBlippy, Game pGame)
    {
        mBlippy = pBlippy;
//        mTimeline = null;
        mGame = pGame;
        error = new Media(getClass().getResource("error.mp3").toString());
        mp = new MediaPlayer(error);
        mGameIterations = 7;
        mCountChanges = 0;
        mStartTime = 0;
        mEndTime = 0;
        mResults = new double[mGameIterations];
//      mLeftResults    = new double[mGameIterations / 2];
        mTimeSnapshots = new ArrayList<>();
        mOnFinished = new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                mGame.setIsReadyToBeClicked(true);
                mGame.swapCircle();
//               mTimeSnapshots.add(System.nanoTime());
                mStartTime = System.nanoTime();

                // is the first one not subtracting the start time?
            /*
                 * if( mCountChanges - 1 < (mGameIterations / 2) ) {
                 * mRightResults[mCountChanges - 1] = (float) (mEndTime -
                 * mStartTime) / 1000000000; } else {
                 * mLeftResults[(mCountChanges - 1) % (mGameIterations / 2)] =
                 * (float) (mEndTime - mStartTime) / 1000000000; }
                 */
            }
        };
    }

    /**
     *
     * @param pBool
     */
    public void setIsClicked(Boolean pBool)
    {
        isClicked = pBool;
    }

    public Boolean isTimelineSet()
    {
        return (mTimeline != null);
    }
    
    /**
     *
     * @return
     */
    public Boolean getIsClicked()
    {
        return isClicked;
    }

    public List<Double> getFormatedResults()
    {
        return mFormatedResults;
    }

    /**
     *
     * @return
     */
    public boolean readFile()
    {
        return true;
    }

    /**
     *
     * @return
     */
    public boolean writeFile()
    {
        return true;
    }

    public void killTimeline()
    {
        mTimeline.stop();
        mTimeline = null;
    }

    public synchronized void pauseGame(String pMessage)
    {
        // if they were supposed to click,
        // reset the circle so the timer resets
        if( !mGame.getIsStartCircle() )
        {
            mGame.swapCircle();
        }

        // resets the mTimeline
        if(mTimeline != null)
             mTimeline.stop();

        final Stage errorWindow = new Stage(StageStyle.UNDECORATED);

        BorderPane pane = new BorderPane();

        Button btn = new Button("Resume Game");

        btn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(mTimeline != null)
                    mTimeline.playFromStart();
                errorWindow.close();
            }
        });

        Text message = new Text(10, 10, pMessage);

        VBox box = new VBox();

        box.getChildren().addAll(message, btn);

        pane.setTop(message);
        pane.setCenter(btn);
        pane.setAlignment(message, Pos.CENTER);

        errorWindow.setScene(new Scene(pane, 300, 150));

        errorWindow.show();
    }

    /**
     * synchronized because the Timeline.stop() method is not
     */
    public synchronized void game()
    {
        // The handler for the circle
        // this is when they click
        EventHandler handler = new EventHandler<Event>()
        {
            @Override
            public void handle(Event e)
            {
                // Olny allow if the game is ready
                if( mGame.isReadyToBeClicked() )
                {
                    // add time to the list (start timer)
//                   mTimeSnapshots.add(System.nanoTime());
                    mEndTime = System.nanoTime();
                    mResults[mCountChanges - 1]
                    = ((double) mEndTime - mStartTime) / 1000000000;

                    mStartTime = 0;
                    mEndTime = 0;
                    // Set up handler
                    mGame.setIsReadyToBeClicked(false);
                    mGame.swapCircle();
                    // ie. go to game
                    doRandomCircleChange();
                }
                else
                {
                    mp = new MediaPlayer(error);
                    mp.play();

                    pauseGame("Don't click before it changes dummy!");
                }
            }
        };

        mGame.getPane().addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
        mGame.getPane().addEventHandler(KeyEvent.KEY_PRESSED, handler);
        mGame.getPane().requestFocus();

        // Start it off
        doRandomCircleChange();
    }

    /**
     *
     */
    private void doRandomCircleChange()
    {
        // Go through 7 times
        if( mCountChanges++ >= mGameIterations )
        {
            // Send captured times to results
//         System.out.println(mGameIterations);
//         System.out.println(mCountChanges);
//         System.out.println(mTimeSnapshots.size());
//         System.out.println(mTimeSnapshots);

            mFormatedResults = new ArrayList<>();
            System.out.println("\nResults:");
            for( double item : mResults )
            {
                mFormatedResults.add(item);
            }

            mBlippy.generateResults();

        } // The actual game
        else
        {
            // Timer
            int x = new Random().nextInt(WAIT_PERIOD) + 2000;
            Duration duration = Duration.millis(x);
            KeyFrame keyFrame = new KeyFrame(duration, mOnFinished,
                                             (KeyValue[]) null);
            mTimeline = new Timeline();
            mTimeline.getKeyFrames().add(keyFrame);
            mTimeline.play();
        }
    }
}
