/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blippy;

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
    * DOCUMENT ME!
    */
   private Boolean isStartCircle;

   /**
    * Creates a new Game object.
    *
    * @param pBlippy DOCUMENT ME!
    * @param pIsColorBlind DOCUMENT ME!
    * @param pIsSoundTest DOCUMENT ME!
    */
   public Game(Blippy pBlippy, Boolean pIsColorBlind, Boolean pIsSoundTest)
   {
      super(pBlippy);
      mStart = new Circle();
      mFinal = new Circle();

      mInstance = this;

      isReady = false;

      beep = new Media(getClass().getResource("beep.mp3").toString());
      mp = new MediaPlayer(beep);
      isStartCircle = true;
   }

   /**
    *
    * @param pReady
    */
   public void setIsReadyToBeClicked(boolean pReady)
   {
      isReady = pReady;
   }

   /**
    *
    * @return
    */
   public Boolean isReadyToBeClicked()
   {
      return isReady;
   }

   /**
    *
    * @return
    */
   public Game getInstance()
   {
      return mInstance;
   }

   /**
    * DOCUMENT ME!
    *
    * @return DOCUMENT ME!
    */
   public Boolean getIsStartCircle()
   {
      return isStartCircle;
   }

   /**
    *
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
    * DOCUMENT ME!
    *
    * @return DOCUMENT ME!
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
