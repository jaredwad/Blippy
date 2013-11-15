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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jared
 */
final class GameSetup extends SceneChanger 
{
    
    private Button mBtn;
    //private final Scene scene;
    
    public GameSetup()
    {
        super();
//        setup();
    }
    
    public Scene setup()
    {
        mBtn = new Button();
        mBtn.setText("Hello");
        mBtn.setOnAction(new EventHandler<ActionEvent>()
        {
        @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Hello");
            }
        });
        VBox box = new VBox(10);
        box.getChildren().add(mBtn);
        
        mRoot.add(box, 1, 1);
        Scene scene = new Scene(mRoot, 900, 700);
        return scene;
    }
    
}
