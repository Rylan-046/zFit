package edu.cmis.zfit.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class AbstractDatabaseRepository {
    DBConnectionProperties dbConnectionProperties;

    protected AbstractDatabaseRepository(DBConnectionProperties dbConnectionProperties) {
        this.dbConnectionProperties = dbConnectionProperties;
    }

    protected Connection createConnection() {
        Connection connection = null;

        try {
            System.out.println("Connection to " +  dbConnectionProperties.dbUrl());
            if (dbConnectionProperties.dbUser() == null) {
                connection = DriverManager.getConnection(
                        dbConnectionProperties.dbUrl()
                );
            } else {
                connection = DriverManager.getConnection(
                        dbConnectionProperties.dbUrl(),
                        dbConnectionProperties.dbUser(),
                        dbConnectionProperties.dbPasswd()
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return connection;
    }
}