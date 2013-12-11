/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blippy;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

/**
 *
 * @author Jared
 */
class Start extends SceneChanger
{

    private Button mStartBtn;

    public Start(Blippy pBlippy)
    {
        super(pBlippy);
    }

    @Override
    public final Scene setup()
    {
        Text text = new Text("Reaction Time Test\n\n This is a simple test to me"
                + "asure your reaction time.  You can choose a visual or audit"
                + "ory test.  The visual test will display a red circle in the"
                + " middle of the screen.  When the circle turns green, tap an"
                + "y key on the keyboard and your reaction time will be record"
                + "ed.  The circle will then turn red again.  Wait until it tu"
                + "rns green again and then hit a key again.  Each time the ci"
                + "rcle turns green, hit a key and your reaction time will be "
                + "recorded.  After the seventh time, the test ends and a grap"
                + "h will appear showing each of the seven reaction times.  Fo"
                + "r those who are color blind or prefer black and white, you "
                + "can choose to have a dark ring turn into a filled-in black "
                + "circle rather than red and green.\n\nIf you choose the audito"
                + "ry test, wait until you hear the sound and then tap a key. "
                + " Your reaction time will be measured.  This also happens se"
                + "ven times and then a graph appears showing your seven react"
                + "ion times.\n\nThis test can be used to compare reaction times"
                + " of right hands versus left hands, between responses to sig"
                + "hts versus sounds, and for testing response times under dif"
                + "ferent environmental conditions such as sleep deprivation, "
                + "after listening to various types of music, or eating or dri"
                + "nking.  I hope you come up with other ideas to test as well"
                + ".\n\nEnjoy testing your reaction times.");
        
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
//                mBlippy.setup("mGame");
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
        mScene.getStylesheets().add(Blippy.class.
                getResource("Style.css").toExternalForm());

        return mScene;
    }

    class TextBox extends Group
    {

        private final Text text;
        private final Rectangle rectangle;
//        private Rectangle clip;

        public StringProperty textProperty()
        {
            return text.textProperty();
        }

        TextBox(String string, double width, double height)
        {
            this.text = new Text(string);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setFill(Color.FORESTGREEN);
            text.setTextOrigin(VPos.CENTER);
            text.setFont(Font.font("Comic Sans MS", 25));
            text.setFontSmoothingType(FontSmoothingType.LCD);

            this.rectangle = new Rectangle(width, height);
            rectangle.setFill(Color.BLACK);
        }
    }
}
