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

/**
 *
 * @author Jared
 */
class Start extends SceneChanger
{
    private Button mStartBtn;
    
    public Start()
    {
        super();
//        setup();
    }

    @Override
    public Scene setup() 
    {
        mStartBtn = new Button();
        mStartBtn.setText("Change Scene");
        mStartBtn.setOnAction(new EventHandler<ActionEvent>()
        {
        @Override
            public void handle(ActionEvent event)
            {
                mBlippy.setup(mBlippy.getGameSetup());
//                mBlippy.setup(new GameSetup());
            }
        });
        VBox box = new VBox(10);
        box.getChildren().add(mStartBtn);
        
        mRoot.add(box, 1, 1);
        Scene scene = new Scene(mRoot, 900, 700);
        return scene;
    }
    
}
