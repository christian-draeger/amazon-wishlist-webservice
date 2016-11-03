package config;

import static org.springframework.core.io.support.PropertiesLoaderUtils.loadProperties;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfigReader {
    private Properties props = new Properties();

    @PostConstruct
    private void loadProps() {
        Resource resource = new ClassPathResource("/application.properties");
        try {
            props = loadProperties(resource);
        } catch (IOException e) {
            log.warn("Error loading properties!", e);
        }
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }
}
