/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.netjags.recursoshumanos.util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.netjags.recursoshumanos.controller.DashboardController;
import main.java.com.netjags.recursoshumanos.dto.UserDTO;

/**
 *
 * @author informatica
 */
public class SceneManager {
    private static SceneManager instance;

    private Stage stage;

    private SceneManager() {
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void init(Stage stage) {
        this.stage = stage;
    }

     public void showLoginView() {
        FXMLLoader loader = load("login-view.fxml");
        setRoot(loader, "Iniciar sesión");
    }

    public void showRegistroView() {
        FXMLLoader loader = load("registry-view.fxml");
        setRoot(loader, "Crear cuenta");
    }
    
    public void showDashboardView() {
        FXMLLoader loader = load("dashboard-view.fxml");
        setRoot(loader, "Crear cuenta");
    }
    




    private FXMLLoader load(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/main/resources/view/" + fxmlFile));
            loader.load();
            return loader;
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la vista: " + fxmlFile, e);
        }
    }

    private void setRoot(FXMLLoader loader, String title) {
        Parent root = loader.getRoot();

        if (stage.getScene() == null) {
            stage.setScene(new Scene(root));
        } else {
            stage.getScene().setRoot(root);
        }

        stage.setTitle(title);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }
    
}
