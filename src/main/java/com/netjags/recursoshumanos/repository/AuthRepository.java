/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.netjags.recursoshumanos.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import main.java.com.netjags.recursoshumanos.config.DBConnection;
import main.java.com.netjags.recursoshumanos.dto.UserDTO;
import main.java.com.netjags.recursoshumanos.model.Address;
import main.java.com.netjags.recursoshumanos.model.User;

/**
 *
 * @author informatica
 */
public class AuthRepository {
        
    public boolean saveUser(User user) throws SQLException{
    
    String sql = "Insert into users (first_name,last_name,email,password_hash,role_id) Values(?,?,?,?,?);";
    
      try(Connection conn = DBConnection.getConnection();PreparedStatement prst = conn.prepareStatement(sql)){
        
    prst.setString(1,user.getFirstName());
    prst.setString(2, user.getLastName());
    prst.setString(3, user.getEmail());
    prst.setString(4, user.getEmail());
    prst.setString(5, user.getEmail());    
    
        return prst.execute();
    
    }
    }
    
    public boolean save(User user, String addressId) throws SQLException {

    String sql = "Insert into users (first_name,last_name,email,password_hash,role_id) Values(?,?,?,?,?);";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPasswordHash());
        ps.setInt(5, user.getRoleId());

        return ps.executeUpdate() > 0;
    }
}
    
    public UserDTO findUserByEmail(String email) throws SQLException{

        String sql = "Select u.user_id,u.first_name,u.last_name,u.email,u.password_hash,r.role_name from users as u inner join roles as r on u.role_id = r.role_id where u.email = ?";

        try(Connection conn = DBConnection.getConnection();PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, email);

            try(ResultSet rs = ps.executeQuery()){

                if(rs.next()){

                    UserDTO dto = new UserDTO(rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password_hash"));
                    dto.setUserId(rs.getString("user_id"));
                    dto.setRole(rs.getString("role_name"));
                    return dto;

                }else{

                    return null;
                }
            }
        }
    }



}
