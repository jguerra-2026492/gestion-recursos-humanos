/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.netjags.recursoshumanos.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import main.java.com.netjags.recursoshumanos.config.DBConnection;
import main.java.com.netjags.recursoshumanos.model.Address;

/**
 *
 * @author informatica
 */
public class AddressRepository {
   public Address save(Address address) throws SQLException {
        if (address.getAddressId() == null || address.getAddressId().trim().isEmpty()) {
            address.setAddressId(UUID.randomUUID().toString());
        }

        String sql = "INSERT INTO addresses (address_id, country, city, state, avenue, street, house_number) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, address.getAddressId());
            ps.setString(2, address.getCountry());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getState());
            ps.setString(5, address.getAvenue());
            ps.setString(6, address.getStreet());
            ps.setString(7, address.getHouse());

            ps.executeUpdate();
            return address;
        }
    }

    public Optional<Address> findById(String addressId) throws SQLException {
        String sql = "SELECT * FROM addresses WHERE address_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, addressId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToAddress(rs));
            }
            return Optional.empty();
        }
    }

    public List<Address> findAll() throws SQLException {
        String sql = "SELECT * FROM addresses";
        List<Address> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToAddress(rs));
            }
        }
        return list;
    }

    public Address update(Address address) throws SQLException {
        String sql = "UPDATE addresses SET country = ?, city = ?, state = ?, "
                   + "avenue = ?, street = ?, house_number = ? WHERE address_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, address.getCountry());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getState());
            ps.setString(4, address.getAvenue());
            ps.setString(5, address.getStreet());
            ps.setString(6, address.getHouse());
            ps.setString(7, address.getAddressId());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No se encontró la dirección con ID: " + address.getAddressId());
            }
            return address;
        }
    }

    public void saveUserAddress(Address address,String userId) throws SQLException{
        
    String sql1 = "insert into addresses values(?,?,?,?,?,?,?)";
    String sql2 = "update users set address = ? where user_id = ?"; 
    
    try(Connection conn = DBConnection.getConnection()){
        
        try(PreparedStatement prst = conn.prepareStatement(sql1);
              PreparedStatement prstAsignation = conn.prepareStatement(sql2)  
                ){
            
            conn.setAutoCommit(false);
            
        prst.setString(1, address.getAddressId());
        prst.setString(2, address.getCity());
        prst.setString(3, address.getState()); 
        prst.setString(4, address.getAvenue());
        prst.setString(5, address.getStreet());
        prst.setString(6, address.getHouse());
        
        prst.executeUpdate();
        
        prstAsignation.setString(1, address.getAddressId());
        prstAsignation.setString(2, userId);
        
        prstAsignation.executeUpdate();
        
        conn.commit();
        
        }catch(SQLException e){
        
        conn.rollback();
        
        }finally{
        
        conn.setAutoCommit(true);
        
        }
    }
        }

    
    //deleite por id
    public boolean deleteById(String addressId) throws SQLException {
        String sql = "DELETE FROM addresses WHERE address_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, addressId);
            return ps.executeUpdate() > 0;
        }
    }
    

    public boolean existsById(String addressId) throws SQLException {
        String sql = "SELECT 1 FROM addresses WHERE address_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, addressId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private Address mapResultSetToAddress(ResultSet rs) throws SQLException {
        return new Address(
                rs.getString("address_id"),
                rs.getString("country"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("avenue"),
                rs.getString("street"),
                rs.getString("house_number")
        );
    }
 
}
