package webservice;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;

@SpringBootTest
public class ControllerIT {
    @Value("${local.server.port}")
    int port;

    @Test
    public void amazonWishList() {
        RestAssured.given()
                .baseUri("http://localhost").port(port)
                .when()
                .param("url", "https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ/ref=cm_wl_list_o_2")
                .get("wishlist")
                .then()
                .statusCode(200)
                .body("url", any(String.class))
                .body("name", any(String.class))
                .body("items", hasSize(greaterThan(3)))
                .body("items[0].id", any(String.class))
                .body("items[0].title", any(String.class))
                .body("items[0].itemUrl", any(String.class))
                .body("items[0].pictureUrl", any(String.class));
    }
}
