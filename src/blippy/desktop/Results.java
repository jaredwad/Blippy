
package blippy.desktop;

import java.text.DecimalFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Jared
 */
class Results
   extends SceneChanger
{
   /**
    * Button to return to start.
    */
   private Button mStartBtn;

   /**
    * Table to put data in.
    */
   private ListView mTable;

   /**
    * List to display data.
    */
   private ObservableList<Text> mData;

   /**
    * Creates a new Results object.
    *
    * @param pBlippy
    */
   public Results(Blippy pBlippy)
   {
      super(pBlippy);
   }

   /**
    * Sets the results.
    *
    * @param pData
    */
   public void setResults(double[] pData)
   {
      String string;
      Text text;
      mData = FXCollections.observableArrayList();

      DecimalFormat formater = new DecimalFormat("0.00");
      int i = 0;
      String clickNumber;

      for (Double item : pData)
      {
         i++;

         if (i < 10)
         {
            clickNumber = "# ";
         }
         else
         {
            clickNumber = "#";
         }

         clickNumber += Integer.toString(i);

         string = " Click " + clickNumber + "                  " +
            formater.format(item) + " seconds";
         
         if (item >= 5.0)
         {
             string += " (No User Input)";
         }
         text = new Text(string);

         // not doing anything
         text.setTextAlignment(TextAlignment.CENTER);

         text.setFont(new Font("Meiryo UI Bold", 15));
         mData.add(text);
      }
   }

   /**
    * Creates the table to display
    */
   private void initializeTable()
   {
      mTable = new ListView(mData);
      mTable.setPrefSize(200, 250);
      mTable.setEditable(false);
   }

   /**
    * Creates the scene for results
    *
    * @return
    */
   @Override
   public final Scene setup()
   {
      mPane = new BorderPane();

      initializeTable();

      mStartBtn = new Button("to start");
      mStartBtn.setOnAction(new EventHandler<ActionEvent>()
         {
            @Override
            public void handle(ActionEvent event)
            {
               mBlippy.setup("mStart");
            }
         });

      Button resetButton = new Button("Restart Game");
      resetButton.setOnAction(new EventHandler<ActionEvent>()
         {
            @Override
            public void handle(ActionEvent event)
            {
               mBlippy.resetGame();
            }
         });

      mStartBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
      resetButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
      mStartBtn.setMinWidth(Control.USE_PREF_SIZE);
      resetButton.setMinWidth(Control.USE_PREF_SIZE);

      VBox box = new VBox(10);
      box.getChildren().addAll(mStartBtn, resetButton);
      box.setSpacing(10);
      box.setPadding(new Insets(10, 20, 10, 20));

      mPane.setTop(mMenu);
      mPane.setRight(box);

      final VBox vbox = new VBox();
      vbox.setSpacing(5);
      vbox.setPadding(new Insets(10, 0, 0, 10));
      vbox.getChildren().addAll(mTable);

      mPane.setCenter(vbox);
      mScene = new Scene(mPane, Height, Length);

      return mScene;
   }
}
