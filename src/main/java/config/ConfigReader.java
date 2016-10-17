package config;

import static org.springframework.core.io.support.PropertiesLoaderUtils.loadProperties;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ConfigReader {

    public String getProperty(String key){
        Resource resource = new ClassPathResource("/application.properties");
        Properties props = null;
        try {
            props = loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty(key);
    }
}
