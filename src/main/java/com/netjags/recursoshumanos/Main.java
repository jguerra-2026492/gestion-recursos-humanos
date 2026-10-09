/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main.java.com.netjags.recursoshumanos;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.netjags.recursoshumanos.util.SceneManager;
/**
 *
 * @author informatica
 */
public class Main extends Application{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.getInstance().init(stage);
        SceneManager.getInstance().showLoginView();
    }
    
}
