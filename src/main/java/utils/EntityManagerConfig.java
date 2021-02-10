package utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EntityManagerConfig {

    /**
     * Load database variables from local.properties file instead
     * of persistence.xml
     **/
    public static EntityManagerFactory getManager() throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream("local.properties"));
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CiklumTask", props);
        return factory;
    }

}
