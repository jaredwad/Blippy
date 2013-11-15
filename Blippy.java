/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package blippy;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jared
 */
public class Blippy extends Application {
    
    private static final int HEIGHT = 700;
    private static final int LENGTH = 900;
    private GridPane mRoot;
    private Stage mStage;
    private Scene mScene;
    private static final Blippy mInstance = new Blippy();
    private Start mStart;
    private GameSetup mGameSetup;
    
    // private GameHelper mGameHelper
    
    /**
     *
     * @return
     */
        
    public Start getStart()
    {
        return mStart;
    }

    /**
     *
     * @return
     */
    public GameSetup getGameSetup()
    {
        return mGameSetup;
    }
    
    public Blippy()
    {
       // mStart     = new Start();
       //mGameSetup = new GameSetup();
    }
    
    public Stage getStage()
    {
        return mStage;
    }
    
    /**
     *
     * @return
     */
    public static Blippy getInstance()
    {
        return mInstance;
        
    }
    
    
//    public void setScene(Scene pScene);
    
    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage)
    {
        mStage = primaryStage;
        mGameSetup = new GameSetup();
        mStart     = new Start();
        
        mScene = mStart.getScene();
        mStage.setScene(mScene);
        
//        mScene.getStylesheets().add(Blippy.class.
//                getResource("Style.css").toExternalForm());
        mStage.setTitle("Blippy");
        mStage.show();
    }
    
    public void setup(SceneChanger pChanger)
    {
        assert pChanger == mStart;
        mScene = pChanger.getScene();
        mStage.setScene(mScene);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    private void sceeneSwap()
    {
        Button btn = new Button();
        btn.setText("Hello");
        btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Hello");
            }
        });
        VBox box = new VBox(10);
        box.getChildren().add(btn);
        
        
        
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        
        
        Scene newScene = new Scene(root, 900,700);
        mStage.setScene(mScene);
//        mStage.show();
    }
    
    
}
