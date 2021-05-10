/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DBConnection;
import entity.Share;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author kynhanht
 */
public class ShareDAO {

    DBConnection dBConnection;

    public ShareDAO() throws Exception {
        try {
            dBConnection = new DBConnection();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Share> selectShare() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Share> shares = null;
        try {
            conn = dBConnection.getConnection();
            String query = "select* from Share";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            shares = new ArrayList<>();
            while (rs.next()) {
                String icon = dBConnection.getImagePath() + rs.getString("icon");
                String socialNetwork = rs.getString("socialNetwork");
                String url = rs.getString("url");
                Share share = new Share(icon, socialNetwork, url);
                shares.add(share);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            dBConnection.close(conn, ps, null, rs);
        }
        return shares;
    }
}
