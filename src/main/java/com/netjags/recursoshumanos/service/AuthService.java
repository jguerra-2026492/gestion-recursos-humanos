/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.netjags.recursoshumanos.service;

import main.java.com.netjags.recursoshumanos.exception.ServiceException;
import java.sql.SQLException;
import main.java.com.netjags.recursoshumanos.dto.UserDTO;
import main.java.com.netjags.recursoshumanos.model.Address;
import main.java.com.netjags.recursoshumanos.model.User;
import main.java.com.netjags.recursoshumanos.repository.AuthRepository;
import main.java.dev.alpha.alphalogin.security.jbcrypt.BCrypt;

/**
 *
 * @author informatica
 */
public class AuthService {
    
    private final AuthRepository authRepository;
    private final AddressService addressService = new AddressService();

    public AuthService() {
        this.authRepository = new AuthRepository();
    }

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void validateAccountData(String firstName, String lastName, String email,
            String password, String confirmPassword) throws ServiceException {
        if (firstName == null || firstName.isBlank()
                || lastName == null || lastName.isBlank()
                || email == null || email.isBlank()
                || password == null || password.isBlank()) {
            throw new ServiceException("Todos los campos son obligatorios.");
        }
        if (!email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            throw new ServiceException("Ingresa un correo electrónico válido.");
        }
        if (!password.equals(confirmPassword)) {
            throw new ServiceException("Las contraseñas no coinciden.");
        }
        
    }

    public UserDTO login(String email, String password) throws ServiceException {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new ServiceException("Debes ingresar correo y contraseña.");
        }

        UserDTO user;
        try {
            user = authRepository.findUserByEmail(email.trim());
        } catch (SQLException e) {
            throw new ServiceException("No se pudo conectar con la base de datos.");
        }

        if (user == null || !passwordMatches(password, user.getPasswordHash())) {
            throw new ServiceException("Correo o contraseña incorrectos.");
        }

        user.setPasswordHash(null);
        return user;
    }

    private boolean passwordMatches(String plainPassword, String hash) {
        try {
            return BCrypt.checkpw(plainPassword, hash);
        } catch (Exception e) {
            return false;
        }
    }

        public void register(String firstName, String lastName, String email, String password, Address address)
            throws ServiceException {

        validateAccountData(firstName, lastName, email, password, password);

        Address savedAddress = null;
        boolean created = false;

        try {
            String addressId = null;
            if (address != null) {
                savedAddress = addressService.create(address);
                addressId = savedAddress.getAddressId();
            }

            String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
            User newUser = new User(null, firstName.trim(), lastName.trim(), email.trim(), passwordHash, addressId, 0);
            created = authRepository.save(newUser, addressId);

        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new ServiceException("Ya existe una cuenta con ese correo.");
            }
            throw new ServiceException("No se pudo conectar con la base de datos.");
        } finally {
            if (!created && savedAddress != null) {
                try {
                    addressService.delete(savedAddress.getAddressId());
                } catch (Exception ignored) {
                }
            }
        }

        if (!created) {
            throw new ServiceException("No se pudo crear el usuario.");
        }
    }

    
}
