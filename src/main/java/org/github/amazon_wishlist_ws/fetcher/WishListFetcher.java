package org.github.amazon_wishlist_ws.fetcher;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by christian.draeger on 25.04.16.
 */
@Slf4j
@Service
public class WishListFetcher {

    @Value("${config.useragent}")
    String userAgent;

    @Value("${config.referrer}")
    String referrer;

    public Document getFetchedAmazonWishList(String amazonWishListUrl) {
        validateAmazonWishListUrl(amazonWishListUrl);
        try {
            return Jsoup.connect(amazonWishListUrl)
                    .userAgent(userAgent)
                    .referrer(referrer)
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