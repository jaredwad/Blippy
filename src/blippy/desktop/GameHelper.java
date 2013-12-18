package blippy.desktop;

import blippy.system.*;

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

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.util.Duration;

/**
 * This will control the game. It has a timer that will talk to the game class
 * to switch the circle, as well as send results to the results class.
 *
 * @author Jared
 */
public class GameHelper
{
   /**
    * Instance of the Blippy class being used.
    */
   private final Blippy mBlippy;

   /**
    * Instance of game interface.
    */
   private final Game mGame;

   /**
    * Results from the test.
    */
   private final double[] mResults;

   /**
    * Time started.
    */
   private long mStartTime;

   /**
    * Time ended.
    */
   private long mEndTime;

   /**
    * Number of trials done.
    */
   private final int mGameIterations;

   /**
    * Value to determine if the circle was clicked.
    */
   private Boolean isClicked;

   /**
    * Todo when the test is over.
    */
   private final EventHandler<ActionEvent> mOnFinished;

   /**
    * Todo when the user doesn't click for 5 seconds.
    */
   private final EventHandler<ActionEvent> mClick;

   /**
    * Amount of times circle has changed.
    */
   private int mCountChanges;

   /**
    * Time to wait between trials.
    */
   private static final int WAIT_PERIOD = 3000;

   /**
    * Timer for how long until the circle changes
    * and it accepts user input
    */
   private Timeline mTimeline;

   /**
    * Timer to "click" if the user doesn't after 5 seconds
    */
   private Timeline mClickTimer;

   /**
    * The error mp3 file.
    */
   private final Media error;

   /**
    * Will play the error mp3.
    */
   private MediaPlayer mp;

   /**
    * Constructor for gameHelper.
    *
    * @param pBlippy - instance of main Blippy class
    * @param pGame   - instance of game interface.
    */
   public GameHelper(Blippy pBlippy, Game pGame)
   {
      mBlippy = pBlippy;
      mGame = pGame;
      error = new Media(ResourceGetter.getResource("/resources/error.mp3"));
      mp = new MediaPlayer(error);
      mGameIterations = 7;
      mCountChanges = 0;
      mStartTime = 0;
      mEndTime = 0;
      mResults = new double[mGameIterations];

      // set up click handler
      mClick =
         new EventHandler<ActionEvent>()
            {
               public void handle(ActionEvent e)
               {
                  click();
               }
            };

      // set up game handler
      mOnFinished =
         new EventHandler<ActionEvent>()
            {
               public void handle(ActionEvent e)
               {
                  setupClickTimer();

                  mGame.setIsReadyToBeClicked(true);
                  mGame.swapCircle();
                  mStartTime = System.nanoTime();
               }
            };
   }

   /**
    * Setter for isClicked.
    *
    * @param pBool
    */
   public void setIsClicked(Boolean pBool)
   {
      isClicked = pBool;
   }

   /**
    * Determines if the Timeline
    * is set
    *
    * @return
    */
   public Boolean isTimelineSet()
   {
      return (mTimeline != null);
   }

   /**
    * getter for isClicked
    *
    * @return isClicked
    */
   public Boolean getIsClicked()
   {
      return isClicked;
   }

   /**
    * Getter for Results.
    *
    * @return mResults
    */
   public double[] getResults()
   {
      return mResults;
   }

   /**
    * Stop and delete the timeline.
    */
   public void killTimeline()
   {
       if(mTimeline != null)
       {
           mTimeline.stop();
       }
      mTimeline = null;
   }

   /**
    * Pauses the game, resetting the circle once done.
    *
    * @param pMessage
    */
   public synchronized void pauseGame(String pMessage)
   {
      // if they were supposed to click,
      // reset the circle so the timer resets
      if (! mGame.getIsStartCircle())
      {
         mGame.swapCircle();
      }

      // resets the mTimeline
      if (mTimeline != null)
      {
         mTimeline.stop();
      }

      if (mClickTimer != null)
      {
         mClickTimer.stop();
      }

      mClickTimer = null;

      final Stage errorWindow = new Stage(StageStyle.UNDECORATED);

      BorderPane pane = new BorderPane();

      Button btn = new Button("Resume Game");

      btn.setOnAction(new EventHandler<ActionEvent>()
         {
            @Override
            public void handle(ActionEvent event)
            {
               if (mTimeline != null)
               {
                  mTimeline.playFromStart();
               }

               errorWindow.close();
            }
         });

      Text message = new Text(10, 10, pMessage);

      VBox box = new VBox();

      box.getChildren().addAll(message, btn);

      pane.setTop(message);
      pane.setCenter(btn);
      BorderPane.setAlignment(message, Pos.CENTER);

      errorWindow.setScene(new Scene(pane, 300, 150));

      errorWindow.initModality(Modality.APPLICATION_MODAL);

      errorWindow.show();
   }

   /**
    * A basic setup function to setup the
    * timer to click if the user doesnt
    * after 5 seconds.
    */
   private void setupClickTimer()
   {
      Duration duration = Duration.millis(5000);
      KeyFrame keyFrame = new KeyFrame(duration, mClick, (KeyValue[]) null);
      mClickTimer = new Timeline();
      mClickTimer.getKeyFrames().add(keyFrame);
      mClickTimer.play();
   }

   /**
    * The function that preforms the click
    * action of the user, or timer. It
    * records the time and resets the timers
    */
   private void click()
   {
      // stop all timers
      mClickTimer.stop();
      mTimeline.stop();

      // an extra percaution
      mTimeline = null;
      mClickTimer = null;

      // add time to the list (start timer)
      mEndTime = System.nanoTime();

      // store time
      mResults[mCountChanges - 1] = ((double) mEndTime - mStartTime) / 1000000000;
      
      mStartTime = 0;
      mEndTime = 0;
      // Set up handler
      mGame.setIsReadyToBeClicked(false);
      mGame.swapCircle();
      // ie. go to game
      doRandomCircleChange();
   }

   /**
    * Builds the game. Synchronized because the Timeline.stop() method is not
    */
   public synchronized void game()
   {
      // The handler for the circle
      // this is when they click
      EventHandler handler =
         new EventHandler<Event>()
         {
            @Override
            public void handle(Event e)
            {
               // Olny allow if the game is ready
               if (mGame.isReadyToBeClicked())
               {
                  click();
               }
               else
               {
                  mp = new MediaPlayer(error);
                  mp.play();

                  pauseGame("Don't click before it changes!");
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
    * Switches the circle 7 times, recording results.
    * After which, it tells blippy to generate the
    * results scene.
    */
   private void doRandomCircleChange()
   {
      // Go through 7 times
      if (mCountChanges++ >= mGameIterations)
      {
         mBlippy.generateResults();
      } // The actual game
      else
      {
         // Timer
         int x = new Random().nextInt(WAIT_PERIOD) + 2000;
         Duration duration = Duration.millis(x);
         KeyFrame keyFrame =
            new KeyFrame(duration, mOnFinished, (KeyValue[]) null);
         mTimeline = new Timeline();
         mTimeline.getKeyFrames().add(keyFrame);
         mTimeline.play();
      }
   }
}
