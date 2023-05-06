package org.gjdbc.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseConnectionBuilder {
	DatabaseConnectionBuilder setUsername(String username);
    DatabaseConnectionBuilder setPassword(String password);
    DatabaseConnectionBuilder setDatabase(String database);
    DatabaseConnectionBuilder setAddress(String address);
    DatabaseConnectionBuilder setDriverClass(Class<?> driverClass);
    DatabaseConnection build() throws SQLException;

class DatabaseConnection {
    private final Connection connection;

    DatabaseConnection(Connection connection) {
        this.connection = connection;
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public ResultSet executeQuery(String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            // Process the result set and return the result
            return resultSet;
        }
    }

    public int executeUpdate(String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int result = statement.executeUpdate(query);
            // Process the result and return the result
            return result;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
}
