package org.github.amazon_wishlist_ws.fetcher;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by christian.draeger on 25.04.16.
 */
@Slf4j
@Service
public class WishListFetcher {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6";
    private static final String REFERRER = "http://www.google.com";

    public Document getFetchedAmazonWishList(String amazonWishListUrl) {
        validateAmazonWishListUrl(amazonWishListUrl);
        try {


            return Jsoup.connect(amazonWishListUrl)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .get();
        } catch (IOException e) {
            log.warn("Error querying Amazon!", e);
        }
        return null;
    }

    private void validateAmazonWishListUrl(String amazonWishListUrl) {
        if (!(amazonWishListUrl.contains("www.amazon.") && amazonWishListUrl.contains("registry/wishlist/")))
            throw new IllegalArgumentException("invalid Amazon wish list URL");
    }

}