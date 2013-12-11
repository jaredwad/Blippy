/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blippy;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 *
 * @author Jared
 */
final class GameSetup extends SceneChanger
{

    private static GameSetup mInstance;
    private Button mBtn;
    private Circle mCircle;

    /**
     *
     * @param pBlippy
     */
    public GameSetup(Blippy pBlippy)
    {
        super(pBlippy);
        mInstance = this;
    }

    /**
     *
     * @return
     */
    public Circle getCircle()
    {
        return mCircle;
    }

    /**
     *
     * @return
     */
    public final Scene setup()
    {
        mBtn = new Button();
        mBtn.setText("To Game");
        mBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                mBlippy.setup("mGame");
            }
        });

        VBox box = new VBox(10);
        box.getChildren().add(mBtn);

        mPane.setBottom(box);

        mScene = new Scene(mPane, Height, Length);
        return mScene;
    }

}
