/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.netjags.recursoshumanos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.netjags.recursoshumanos.draft.RegistryDraft;
import main.java.com.netjags.recursoshumanos.service.AuthService;
import main.java.com.netjags.recursoshumanos.exception.ServiceException;
import main.java.com.netjags.recursoshumanos.util.SceneManager;

/**
 *
 * @author informatica
 */
public class RegistryController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmpasswordField;

    private final AuthService authService = new AuthService();
    private RegistryDraft draft = new RegistryDraft();

    
    @FXML
    private void handleContinue() throws ServiceException {
       try {
            authService.validateAccountData(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    passwordField.getText(),
                    confirmpasswordField.getText());

            draft.setFirstName(firstNameField.getText().trim());
            draft.setLastName(lastNameField.getText().trim());
            draft.setEmail(emailField.getText().trim());
            draft.setPassword(passwordField.getText());

        } catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registro");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleReturnToLogin() {
        SceneManager.getInstance().showLoginView();
    }
}
