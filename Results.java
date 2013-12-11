/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blippy;

import java.text.DecimalFormat;
import java.util.List;
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
class Results extends SceneChanger
{

    private Button mStartBtn;
    private ListView mTable;
    private ObservableList<Text> mData;

    public Results(Blippy pBlippy)
    {
        super(pBlippy);

    }

    public void setResults(List<Double> pData)
    {
        String string;
        Text text;
        mData = FXCollections.observableArrayList();
        DecimalFormat formater = new DecimalFormat("0.00");
        int i = 0;
        String clickNumber;
        for( Double item : pData )
        {
            i++;

            if( i < 10 )
            {
                clickNumber = "# ";
            }
            else
            {
                clickNumber = "#";
            }

            clickNumber += Integer.toString(i);

            string = " Click " + clickNumber + "                  "
                      + formater.format(item) + " seconds";
            text = new Text(string);
            
            // not doing anything
            text.setTextAlignment(TextAlignment.CENTER);
            
            text.setFont(new Font("Meiryo UI Bold", 15));
            mData.add(text);
        }
    }

    private void initializeTable()
    {

        mTable = new ListView(mData);
        mTable.setPrefSize(200, 250);
        mTable.setEditable(false);
    }

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
//        ObservableList<Double> list = FXCollections.observableArrayList(mBlippy.
//                getFormatedResults());
//        ListView<Double> listView = new ListView<>(list);
//        mPane.setCenter(listView); 

        mScene = new Scene(mPane, Height, Length);
        mScene.getStylesheets().add(Blippy.class.
                getResource("Style.css").toExternalForm());

        return mScene;
    }

}
