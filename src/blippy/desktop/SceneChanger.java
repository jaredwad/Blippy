package blippy.desktop;

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
 * Changes the scene as prompted.
 * @author Jared
 */
public abstract class SceneChanger
{
   /**
    * instance of the main Blippy class.
    */
   protected Blippy mBlippy;

   /**
    * String of instructions.
    */
   protected String mInstructions;

   /**
    * Instance of the scene.
    */
   protected Scene mScene;

   /**
    * Where the scenes are placed.
    */
   protected Group mRoot;

   /**
    * BorderPane to display.
    */
   protected BorderPane mPane;

   /**
    * Menu bar.
    */
   protected VBox mMenu;

   /**
    * Height of the window.
    */
   protected final int Height;

   /**
    * Length of the window.
    */
   protected final int Length;

   /**
    * Creates a new SceneChanger object.
    *
    * @param pBlippy
    */
   public SceneChanger(Blippy pBlippy)
   {
      Height = 700;
      Length = 700;
      mBlippy = pBlippy;
      mRoot = new Group();
      mPane = new BorderPane();
      mMenu = new VBox();
      initializeMenu();
   }

   /**
    * Getter for mPane.
    *
    * @return mPane
    */
   public BorderPane getPane()
   {
      return mPane;
   }

   /**
    * Creates the menu bar.
    */
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
               if (mBlippy.getCurrentScene() == "mGame")
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
               mBlippy.stopGame();
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
               mBlippy.setIsColorBlind(! mBlippy.getIsColorBlind());
               colorBlind.setSelected(mBlippy.getIsColorBlind());

               if (mBlippy.getCurrentScene() == "mGame")
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
               mBlippy.setIsSoundTest(! mBlippy.getIsSoundTest());
               siSound.setSelected(mBlippy.getIsSoundTest());

               if (mBlippy.getCurrentScene() == "mGame")
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

   /**
    *
    * @return
    */
   public abstract Scene setup();
}
