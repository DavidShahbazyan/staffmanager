package arm.davsoft.staffmanager.helpers;

import arm.davsoft.staffmanager.helpers.ConnectionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by david on 7/18/16.
 */
public class ConnectionProviderTest {

    private Connection connection;

    @Before
    public void init() {
        this.connection = ConnectionProvider.getInstance().getConnection();
    }

    @Test
    public void getInstance() throws Exception {
        Assert.assertNotNull(ConnectionProvider.getInstance());
    }

    @Test
    public void getConnection() throws Exception {
        Assert.assertNotNull(connection);
    }

    @Test
    public void getRetreiveData() throws Exception {
        Statement st = connection.createStatement();
        st.execute("SELECT * FROM C_Gender");
        ResultSet rs = st.getResultSet();
        Assert.assertNotNull(rs);
//        while (rs.next()) {
//            System.out.println(rs.getInt("id") + "->" + rs.getString("name"));
//        }
        rs.close();
        st.close();
    }

}