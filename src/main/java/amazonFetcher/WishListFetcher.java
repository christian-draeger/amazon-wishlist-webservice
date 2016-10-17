package amazonFetcher;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import config.ConfigReader;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by christian.draeger on 25.04.16.
 */
@Slf4j
public class WishListFetcher {

//    private String userAgent = "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6";
//    private String referrer = "http://www.google.com";
    ConfigReader config = new ConfigReader();
    private String userAgent = config.getProperty("config.useragent");
    private String referrer = config.getProperty("config.referrer");


    public WishListFetcher() throws IOException {
    }

    public Document getFetchedAmazonWishList(final String amazonWishlistUrl) {

        Document wl = null;

        log.info("\n\tuserAgent: {}\n\treferrer: {}", userAgent, referrer);

        if (isValideAmazonWishListUrl(amazonWishlistUrl)){
            try {
                return Jsoup.connect(amazonWishlistUrl)
                    /*.userAgent(env.getRequiredProperty("config.useragent"))
                    .referrer(env.getRequiredProperty("config.referrer"))*/
                        .userAgent(userAgent)
                        .referrer(referrer)
                        .get();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(wl);
        return wl;
    }

    private boolean isValideAmazonWishListUrl(String amazonWishlistUrl){
        if(amazonWishlistUrl.contains("www.amazon.") && amazonWishlistUrl.contains("registry/wishlist/")){
            return true;
        } else {
            throw new IllegalArgumentException("invalid Amazon wish list URL");
        }
    }

}