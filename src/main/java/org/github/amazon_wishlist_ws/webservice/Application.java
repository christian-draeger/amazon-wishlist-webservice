package org.github.amazon_wishlist_ws.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by christian.draeger on 25.04.16.
 */
@SpringBootApplication(scanBasePackages = "org.github.amazon_wishlist_ws")
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}