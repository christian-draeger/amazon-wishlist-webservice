package org.github.amazon_wishlist_ws.webservice;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.github.amazon_wishlist_ws.fetcher.DomParser;
import org.github.amazon_wishlist_ws.fetcher.Wishlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by christian.draeger on 25.04.16.
 */
@Slf4j
@RestController
public class Controller {
    @Inject
    private DomParser domParser;

    @CrossOrigin
    @GetMapping(value = "/wishlist", produces = MediaType.APPLICATION_JSON_VALUE)
    public Wishlist amazonWishList(@RequestParam String url) {
        return domParser.getWishList(url);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void handleIllegalArgumentException(IllegalArgumentException iae) {
        log.error(iae.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private void handleNullPointerException(NullPointerException npe, HttpServletResponse response) {
        log.error(npe.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void handleIndexOutOfBounceException(IndexOutOfBoundsException oobe, HttpServletResponse response) {
        log.error(oobe.getLocalizedMessage());
    }
}