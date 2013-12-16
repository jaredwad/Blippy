package blippy.desktop;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;

/**
 * A start scene.  Simply displays directions and has
 * a button to start the game.
 * @author Jared
 */
class Start
   extends SceneChanger
{
   /**
    * Button to start.
    */
   private Button mStartBtn;

   /**
    * Creates a new Start object.
    *
    * @param pBlippy
    */
   public Start(Blippy pBlippy)
   {
      super(pBlippy);
   }

   /**
    * Sets up the scene
    *
    * @return
    */
   @Override
   public final Scene setup()
   {
      Text text =
         new Text("Reaction Time Test\n\n This is a simple test to me" +
            "asure your reaction time.  You can choose a visual or audit" +
            "ory test.  The visual test will display a red circle in the" +
            " middle of the screen.  When the circle turns green, tap an" +
            "y key on the keyboard and your reaction time will be record" +
            "ed.  The circle will then turn red again.  Wait until it tu" +
            "rns green again and then hit a key again.  Each time the ci" +
            "rcle turns green, hit a key and your reaction time will be " +
            "recorded.  After the seventh time, the test ends and a grap" +
            "h will appear showing each of the seven reaction times.  Fo" +
            "r those who are color blind or prefer black and white, you " +
            "can choose to have a dark ring turn into a filled-in black " +
            "circle rather than red and green.\n\nIf you choose the audito" +
            "ry test, wait until you hear the sound and then tap a key. " +
            " Your reaction time will be measured.  This also happens se" +
            "ven times and then a graph appears showing your seven react" +
            "ion times.\n\nThis test can be used to compare reaction times" +
            " of right hands versus left hands, between responses to sig" +
            "hts versus sounds, and for testing response times under dif" +
            "ferent environmental conditions such as sleep deprivation, " +
            "after listening to various types of music, or eating or dri" +
            "nking.  I hope you come up with other ideas to test as well" +
            ".\n\nEnjoy testing your reaction times.");

      text.setWrappingWidth(800);
      text.setTextAlignment(TextAlignment.CENTER);
      text.setFont(new Font("Meiryo UI Bold", 20));
      mStartBtn = new Button("Start Game");

      mStartBtn.setOnAction(new EventHandler<ActionEvent>()
         {
            @Override
            public void handle(ActionEvent event)
            {
               mBlippy.resetGame();
            }
         });

      mStartBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
      mStartBtn.setMinWidth(Control.USE_PREF_SIZE);

      VBox box = new VBox(10);
      box.setSpacing(10);
      box.setPadding(new Insets(10, 20, 10, 20));
      box.getChildren().add(mStartBtn);

      mPane.setBottom(box);
      mPane.setTop(mMenu);
      mPane.setCenter(text);

      mScene = new Scene(mPane, 1000, 700);

      return mScene;
   }
}
