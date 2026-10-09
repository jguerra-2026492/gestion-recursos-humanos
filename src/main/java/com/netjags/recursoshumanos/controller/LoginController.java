/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.netjags.recursoshumanos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.netjags.recursoshumanos.dto.UserDTO;
import main.java.com.netjags.recursoshumanos.service.AuthService;
import main.java.com.netjags.recursoshumanos.exception.ServiceException;
import main.java.com.netjags.recursoshumanos.util.SceneManager;

/**
 *
 * @author informatica
 */
public class LoginController {
    @FXML
    private TextField txtFieldEmail;

    @FXML
    private PasswordField fieldPassword;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleLogin() {
        String email = txtFieldEmail.getText();
        String password = fieldPassword.getText();

        try {
            UserDTO usuario = authService.login(email, password);
            SceneManager.getInstance().showDashboardView();
        } catch (ServiceException e) {
            findError(e.getMessage());
        }
    }

    @FXML
    private void handleReturnToRegistryView() {
        SceneManager.getInstance().showRegistroView();
    }

    @FXML
    private void handleReturnToDashboard() {
        SceneManager.getInstance().showDashboardView();
    }

    private void findError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error al intentar el inicio de sesion");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
