package DAOTest;


import dao.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionTest {
    @Test
    public void getConnection_Test() throws ClassNotFoundException { //TODO: Need add connection!!!
        Assert.assertNotNull(ConnectionFactory.getConnection());
    }
}
