/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.sql.DataSource;

/**
 *
 * @author jdlee
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class StartupBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Resource(mappedName = "jdbc/frenchpress")
    private DataSource dataSource;

    @PostConstruct
    public void validateDatabase() {
        try {
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            try {
                // If the users table does not exist, assume none do, build
                // the database and load the sample data
                stmt.executeQuery("select * from users");
            } catch (SQLException ex) {
                processSqlFile(connection, "ddl.sql");
                processSqlFile(connection, "sampledata.sql");
            }
            stmt.close();
            connection.close();

        } catch (Exception ex) {
            Logger.getLogger(StartupBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processSqlFile(Connection connection, String fileName) throws FileNotFoundException, URISyntaxException, IOException, SQLException {

        URL file = getClass().getResource("/sql/" + fileName);
        if (file == null) {
            throw new RuntimeException("Could not find the specified SQL file: " + fileName);
        }
        
        String vendor = getDatabaseVendor(connection);
        boolean isPsql = vendor.contains("PostgreSQL");
        boolean isMySQL = !isPsql; //for now...

        Statement stmt = connection.createStatement();
        BufferedReader reader = new BufferedReader(new FileReader(new File(file.toURI())));
        String line = reader.readLine();
        while (line != null) {
            if (!line.isEmpty()) {
                if (isPsql) {
                    line = line.replace("LONGBLOB", "bytea");
                }
                stmt.execute(line);
            }
            line = reader.readLine();
        }
        stmt.close();
        reader.close();
    }
    
    private String getDatabaseVendor(Connection conn) throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        String name = md.getDatabaseProductName();
        return name;
    }
}