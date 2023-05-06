package org.gjdbc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlConnectionBuilder implements DatabaseConnectionBuilder {
    private String username;
    private String password;
    private String database;
	private String address;
    private Class<?> driverClass;
	final static String LOCAL_ADDRESS  = "localhost:5432";
    @Override
    public DatabaseConnectionBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public DatabaseConnectionBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public DatabaseConnectionBuilder setDatabase(String database) {
        this.database = database;
        return this;
    }
	@Override
	public DatabaseConnectionBuilder setAddress(String address) {
		this.address = address;
		return this;
	}
    @Override
    public DatabaseConnectionBuilder setDriverClass(Class<?> driverClass) {
        this.driverClass = driverClass;
        return this;
    }

    @Override
    public DatabaseConnection build() throws SQLException {
        try {
            Class.forName(driverClass.getName());
        } catch (ClassNotFoundException e) {
            throw new SQLException("Failed to load JDBC driver class", e);
        }
        String url = "jdbc:postgresql://" + address + "/" + database;
        Connection connection = DriverManager.getConnection(url, username, password);
        return new DatabaseConnection(connection);
    }
}