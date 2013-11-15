/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package blippy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jared
 */
public abstract class SceneChanger
{
    protected Blippy mBlippy;
    protected String mInstructions;
    protected Scene mScene;
    protected GridPane mRoot; 

    
    public SceneChanger()
    {
         mBlippy = Blippy.getInstance();
         mRoot = new GridPane();
         mScene = setup();//new Scene(mRoot, 900,700);
                 //setup();
         initializeRoot();
         
    }
    
    private void initializeRoot()
    {
        mRoot.setAlignment(Pos.CENTER);
        mRoot.setHgap(10);
        mRoot.setVgap(10);
        mRoot.setPadding(new Insets(25,25,25,25));
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
