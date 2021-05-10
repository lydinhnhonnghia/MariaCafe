/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DBConnection;
import entity.Information;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author kynhanht
 */
public class InformationDAO {

    DBConnection dBConnection;

    public InformationDAO() throws Exception {
        try {
            dBConnection = new DBConnection();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Information selectInformation() throws Exception{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Information information = null;
        try {
            conn = dBConnection.getConnection();
            String query = "select* from Information";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
               String shortDescription=rs.getString("shortDescription");
               String adress=rs.getString("address");
               String tel=rs.getString("tel");
               String email=rs.getString("email");
               String openingHours=rs.getString("OpeningHours");
               String signature=rs.getString("signature");
               information=new Information(shortDescription, adress, tel, email, openingHours, signature);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            dBConnection.close(conn, ps, null, rs);
        }
        return information;
    }

}
