package arm.davsoft.staffmanager.helpers;

import arm.davsoft.staffmanager.utils.ResourceManager;
import org.sqlite.SQLiteConfig;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by david on 7/18/16.
 */
public final class ConnectionProvider {
    private static final ConnectionProvider INSTANCE = new ConnectionProvider().initConnection();
    private Connection connection;

    private ConnectionProvider() { /* DO NOTHING */ }

    private ConnectionProvider initConnection() {
        try {
            Class.forName(ResourceManager.getParam("DEFAULT.SQLite.DRIVER"));
            this.connection = DriverManager.getConnection(String.format(ResourceManager.getParam("DEFAULT.SQLite.JDBC.URL"), ResourceManager.getSetting("DB_FILE_PATH")));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public static ConnectionProvider getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
