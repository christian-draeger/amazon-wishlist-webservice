package amazonFetcher;

import java.io.IOException;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import config.ConfigReader;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by christian.draeger on 25.04.16.
 */
@Slf4j
@Service
public class WishListFetcher {
    @Inject
    private ConfigReader config;

    public Document getFetchedAmazonWishList(String amazonWishListUrl) {
        validateAmazonWishListUrl(amazonWishListUrl);
        try {
            return Jsoup.connect(amazonWishListUrl)
                    .userAgent(config.getProperty("config.useragent"))
                    .referrer(config.getProperty("config.referrer"))
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