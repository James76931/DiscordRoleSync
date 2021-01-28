package dev.nicho.rolesync.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLHandler extends DatabaseHandler {

    private final BasicDataSource ds;

    public MySQLHandler(JavaPlugin plugin, String host, int port, String db, String user, String passwd) throws SQLException {
        super(plugin);

        this.ds = new BasicDataSource();

        if (plugin.getConfig().getBoolean("database.mysql.disableSSL")) {
            this.ds.addConnectionProperty("useSSL", "false");
        }

        ds.setUrl("jdbc:mysql://" + host + ":" + port + "/" + db);
        ds.setUsername(user);
        ds.setPassword(passwd);
        ds.setValidationQuery("SELECT 1");
        ds.setTestOnBorrow(true);

        this.initialize();
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    protected void closeConnection(Connection c) throws SQLException {
        c.close();
    }
}
