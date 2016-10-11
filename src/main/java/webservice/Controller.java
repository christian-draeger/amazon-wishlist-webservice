package webservice;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import amazonFetcher.Builder;
import amazonFetcher.Wishlist;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by christian.draeger on 25.04.16.
 */
@RestController
@Slf4j
public class Controller {

    @CrossOrigin
    @RequestMapping(value = "/wishlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Wishlist amazonWishList(@RequestParam(value = "url") final String url) {
        return new Builder().getWishListJson(url);
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException iae, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
        log.error(iae.getMessage());
    }

    @ExceptionHandler
    void handleNullPointerException(NullPointerException npe, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(npe.getMessage());
    }

    @ExceptionHandler
    void handleIndexOutOfBounceException(IndexOutOfBoundsException oobe, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NO_CONTENT.value());
        log.error(oobe.getLocalizedMessage());
        oobe.printStackTrace();
    }
}