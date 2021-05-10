/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DBConnection;
import entity.Introduction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author kynhanht
 */
public class IntroductionDAO {

    DBConnection dBConnection;

    public IntroductionDAO() throws Exception{
        try {
            dBConnection = new DBConnection();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Introduction selectIntroDuction() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Introduction introduction = null;
        try {
            conn = dBConnection.getConnection();
            String query = "select* from Intro";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String picture = dBConnection.getImagePath() + rs.getString("picture");
                String shortDescription = rs.getString("shortDescription");
                String detailDescription = rs.getString("detailDescription");
                introduction = new Introduction(title, picture, shortDescription, detailDescription);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            dBConnection.close(conn, ps, null, rs);
        }
        return introduction;

    }
}
