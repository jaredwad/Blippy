/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blippy;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jared
 */
public abstract class SceneChanger
{

    protected Blippy mBlippy;
    protected String mInstructions;
    protected Scene mScene;
    protected Group mRoot;
    protected BorderPane mPane;
    protected VBox mMenu;

    protected final int Height;
    protected final int Length;

    public SceneChanger(Blippy pBlippy)
    {
        Height = 700;
        Length = 700;
        mBlippy = pBlippy;
        mRoot = new Group();
        mPane = new BorderPane();
        mMenu = new VBox();
        initializeRoot();
        initializeMenu();
    }

    public BorderPane getPane()
    {
        return mPane;
    }

    private void initializeMenu()
    {
        mMenu = new VBox();
        MenuBar menuBar = new MenuBar();

        // --------------------------------------- Menu File
        Menu menuFile = new Menu("File");

        //---------------------------------------- File Items
        Menu newG = new Menu("New");
        MenuItem siBlippy = new MenuItem("Blippy");
        siBlippy.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {
                mBlippy.setIsSoundTest(false);
                mBlippy.setIsColorBlind(false);
                mBlippy.resetGame();
            }
        });

        MenuItem siLCurve = new MenuItem("Learning Curve");
        siLCurve.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {
                System.out.println("HI");
            }
        });
        //add all submenus to New
        newG.getItems().addAll(siBlippy, siLCurve);

        MenuItem pause = new MenuItem("Pause");
        pause.setAccelerator(KeyCombination.keyCombination("Ctrl + X"));
        pause.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {
                if( mBlippy.getCurrentScene() == "mGame" )
                {
                    mBlippy.pauseGame("~ PAUSED ~");
                }
            }
        });
        
        MenuItem instructions = new MenuItem("Instructions");
        instructions.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {
                    mBlippy.setup("mStart");
            }
        });

        MenuItem about = new MenuItem("About");
        about.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {
                    mBlippy.showAbout();
            }
        });
        
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {
                System.exit(0);
            }
        });

        //add all items to menu
        menuFile.getItems().addAll(newG, instructions, about, pause, exit);

        //---------------------------------------- Menu Options
        Menu menuOption = new Menu("Options");

        //---------------------------------------- Options Items
        final CheckMenuItem colorBlind = new CheckMenuItem("Colorblind");
        colorBlind.setSelected(mBlippy.getIsColorBlind());
        colorBlind.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {
                mBlippy.setIsColorBlind(!mBlippy.getIsColorBlind());
                colorBlind.setSelected(mBlippy.getIsColorBlind());
                if( mBlippy.getCurrentScene() == "mGame" )
                {
                    mBlippy.resetGame();
                }
            }
        });

        final CheckMenuItem siSound = new CheckMenuItem("Sound Test");
        siSound.setSelected(mBlippy.getIsSoundTest());
        siSound.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {
                mBlippy.setIsSoundTest(!mBlippy.getIsSoundTest());
                siSound.setSelected(mBlippy.getIsSoundTest());
                if( mBlippy.getCurrentScene() == "mGame" )
                {
                    mBlippy.resetGame();
                }
            }
        });

        //adds all options to the Options menu
        menuOption.getItems().addAll(colorBlind, siSound);

        //Add all menus to menu bar
        menuBar.getMenus().addAll(menuFile, menuOption);

        mMenu.getChildren().addAll(menuBar);

    }

    private void initializeRoot()
    {
//        mPane.setAlignment(Pos.CENTER); 
//        mPane.setHgap(10);
//        mPane.setVgap(10);
//       mPane.setPadding(new Insets(25,25,25,25));
    }

    /**
     *
     * @return
     */
    public abstract Scene setup();

    public Scene getScene()
    {
        return mScene;
    }

}
