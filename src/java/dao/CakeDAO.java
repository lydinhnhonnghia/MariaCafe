/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DBConnection;
import entity.Cake;
import entity.Information;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author kynhanht
 */
public class CakeDAO {

    DBConnection dBConnection;

    public CakeDAO() throws Exception {
        try {
            dBConnection = new DBConnection();
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Cake> selectAllCake() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Cake> cakes = null;
        try {
            conn = dBConnection.getConnection();
            String query = "select* from Products";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            cakes = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String picture = dBConnection.getImagePath() + rs.getString("picture");
                String shortDescription = rs.getString("shortDescription");
                String detailDescription = rs.getString("detailDescription");
                float price = rs.getFloat("price");
                Date dateCreated = new Date(rs.getDate("dateCreated").getTime());
                Cake cake = new Cake(id, name, picture, shortDescription, detailDescription, price, dateCreated);
                cakes.add(cake);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            dBConnection.close(conn, ps, null, rs);
        }
        return cakes;
    }

    public ArrayList<Cake> selectTop2Cake() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Cake> cakes = null;
        try {
            conn = dBConnection.getConnection();
            String query = "select top 2* from Products order by dateCreated desc";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            cakes = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String picture = dBConnection.getImagePath() + rs.getString("picture");
                String shortDescription = rs.getString("shortDescription");
                String detailDescription = rs.getString("detailDescription");
                float price = rs.getFloat("price");
                Date dateCreated = new Date(rs.getDate("dateCreated").getTime());
                Cake cake = new Cake(id, name, picture, shortDescription, detailDescription, price, dateCreated);
                cakes.add(cake);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            dBConnection.close(conn, ps, null, rs);
        }
        return cakes;
    }

    public Cake selectById(int id) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cake cake = null;
        try {
            conn = dBConnection.getConnection();
            String query = "select* from Products where id=?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String picture = dBConnection.getImagePath() + rs.getString("picture");
                String shortDescription = rs.getString("shortDescription");
                String detailDescription = rs.getString("detailDescription");
                float price = rs.getFloat("price");
                Date dateCreated = new Date(rs.getDate("dateCreated").getTime());
                cake = new Cake(id, name, picture, shortDescription, detailDescription, price, dateCreated);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            dBConnection.close(conn, ps, null, rs);
        }
        return cake;
    }

    public int getTotalProducts() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rows = 0;
        try {
            String query = "select count(*) from Products";
            conn = dBConnection.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            rows = 0;
            if (rs.next()) {
                rows = rs.getInt(1);
            }
            dBConnection.close(conn, ps, null, rs);
        } catch (Exception e) {
            dBConnection.close(conn, ps, null, rs);
            throw e;
        }
        return rows;
    }

    public int getTotalPages() throws Exception {
        if (getTotalProducts() % 3 == 0) {
            return getTotalProducts() / 3;
        }
        return getTotalProducts() / 3 + 1;
    }

    public ArrayList<Cake> selectCakeByPage(int page) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Cake> cakes = null;
        try {
            conn = dBConnection.getConnection();
            String query = "select p.* from (select *,ROW_NUMBER() Over(Order by id) RN  from Products ) p where RN between ? and ?";
            int from = (page - 1) * 3 + 1;
            int to = page * 3;
            ps = conn.prepareStatement(query);
            ps.setInt(1, from);
            ps.setInt(2, to);
            rs = ps.executeQuery();
            cakes = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String picture = dBConnection.getImagePath() + rs.getString("picture");
                String shortDescription = rs.getString("shortDescription");
                String detailDescription = rs.getString("detailDescription");
                float price = rs.getFloat("price");
                Date dateCreated = new Date(rs.getDate("dateCreated").getTime());
                Cake cake = new Cake(id, name, picture, shortDescription, detailDescription, price, dateCreated);
                cakes.add(cake);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            dBConnection.close(conn, ps, null, rs);
        }
        return cakes;
    }
}
