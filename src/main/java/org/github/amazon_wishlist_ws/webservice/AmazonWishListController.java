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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by christian.draeger on 25.04.16.
 */
@Slf4j
@RestController
public class AmazonWishListController {

    private static final String DEFAULT_URL = "https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ";
    private static final String DEFAULT_TLD = "de";
    private static final String DEFAULT_ID = "CGACJDFKWTIZ";

    @Inject
    private DomParser domParser;

    @CrossOrigin
    @ApiOperation(value = "get amazon wish list by url", nickname = "get wish list by url")
    @GetMapping(value = "/wishlistByUrl", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DomParser.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Wishlist amazonWishList(@RequestParam String url) {
        return domParser.getWishListByUrl(url);
    }

    @CrossOrigin
    @ApiOperation(value = "get amazon wish list by tld and wishListID", nickname = "get wish list by wish list ID and tld")
    @GetMapping(value = "/wishlistById", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DomParser.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Wishlist amazonWishList(@RequestParam String tld, @RequestParam String id) {
        return domParser.getWishListByID(tld, id);
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