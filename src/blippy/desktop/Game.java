package blippy.desktop;

import blippy.system.*;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Jared
 */
class Game
   extends SceneChanger
{
   /**
    * The initial circle object. Blippy - red ColorBlind - black outline
    * SoundTest - invisible
    */
   private final Circle mStart;

   /**
    * The final circle object. Blippy - green ColorBlind - black SoundTest -
    * invisible
    */
   private final Circle mFinal;

   /**
    * Instance of the game object.
    */
   private final Game mInstance;

   /**
    * A boolean value to determine if the circle is ready to change.
    */
   private Boolean isReady;

   /**
    * The beep mp3 file.
    */
   private final Media beep;

   /**
    * Will play the beep mp3.
    */
   private MediaPlayer mp;

   /**
    * determines if circle is green.
    */
   private Boolean isStartCircle;

   /**
    * Creates a new Game object.
    *
    * @param pBlippy
    */
   public Game(Blippy pBlippy)
   {
      super(pBlippy);
      mStart = new Circle();
      mFinal = new Circle();

      mInstance = this;

      isReady = false;

      beep = new Media(ResourceGetter.getResource("/resources/beep.mp3"));
      mp = new MediaPlayer(beep);
      isStartCircle = true;
   }

   /**
    * Setter for isReady
    *
    * @param pReady
    */
   public void setIsReadyToBeClicked(boolean pReady)
   {
      isReady = pReady;
   }

   /**
    * Getter for isReady
    *
    * @return isReady
    */
   public Boolean isReadyToBeClicked()
   {
      return isReady;
   }

   /**
    * Getter for mInstance
    *
    * @return mInstance
    */
   public Game getInstance()
   {
      return mInstance;
   }

   /**
    * Getter for isStartCircle
    *
    * @return isStartCircle
    */
   public Boolean getIsStartCircle()
   {
      return isStartCircle;
   }

   /**
    * Will swap the circle from red to green or
    * green to red.
    */
   public void swapCircle()
   {
      if ((mPane.getCenter() == mStart) && (mBlippy.getIsSoundTest() == true))
      {
         mp = new MediaPlayer(beep);
         mp.play();
         mPane.setCenter(mFinal);
      }
      else if (mPane.getCenter() == mStart)
      {
         mPane.setCenter(mFinal);
      }
      else
      {
         mPane.setCenter(mStart);
      }

      isStartCircle = ! isStartCircle;
   }

   /**
    * Will setup the scene.
    */
   @Override
   public final Scene setup()
   {
      mPane = new BorderPane();

      mStart.setCenterX(100.0f);
      mStart.setCenterY(100.0f);
      mStart.setRadius(200.0f);

      mFinal.setCenterX(100.0f);
      mFinal.setCenterY(100.0f);
      mFinal.setRadius(200.0f);

      //set color of mStart and mFinal if Blippy
      mStart.setStroke(Color.BLACK);
      mStart.setFill(Color.RED);
      mFinal.setStroke(Color.BLACK);
      mFinal.setFill(Color.GREEN);
      mStart.setVisible(true);
      mFinal.setVisible(true);

      //set color of mStart and mFinal if colorBlind
      if (mBlippy.getIsColorBlind() == true)
      {
         mStart.setStroke(Color.BLACK);
         mStart.setFill(Color.WHITE);
         mFinal.setFill(Color.BLACK);
      }

      //set color of mStart and mFinal if SoundTest
      if (mBlippy.getIsSoundTest() == true)
      {
         mStart.setVisible(false);
         mFinal.setVisible(false);
      }

      mPane.setCenter(mStart);
      mPane.setTop(mMenu);
      mScene = new Scene(mPane, Height, Length);

      return mScene;
   }
}
