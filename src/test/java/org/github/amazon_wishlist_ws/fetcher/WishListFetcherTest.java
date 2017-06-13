package org.github.amazon_wishlist_ws.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.Test;

public class WishListFetcherTest {


    private static final String AMAZON_URL = "https://www.amazon.de";
    private static final String WISH_LIST_UNDER_TEST = "/gp/registry/wishlist/CGACJDFKWTIZ/";

    private static final String WISH_LIST_HEADLINE_SELECTOR = "#wl-list-info h1";
    private static final String PAGINATION_SELECTOR = ".a-last>a";
    private static final String ITEMS = "h5 a";
    private static final String PRICES = "[id^=itemPrice_]";
    private static final String ASINS = "[data-item-prime-info]";
    private static final String PICTURES = "[id^=itemImage] a img";
    private static final String OFFERED_BY = ".itemAvailOfferedBy";

    private final Document fetchedAmazonWishList = new WishListFetcher().getFetchedAmazonWishList(AMAZON_URL + WISH_LIST_UNDER_TEST);

    @Test
    public void should_return_document() throws Exception {
        assertThat(getFirstElement(WISH_LIST_HEADLINE_SELECTOR).text()).isEqualTo("Christians Wunschzettel 2");
        assertThat(getFirstElement(PAGINATION_SELECTOR).attr("href")).contains(WISH_LIST_UNDER_TEST);
        assertThat(getFirstElement(ITEMS).attr("title")).contains("Train Simulator 2015");
        assertThat(getFirstElement(PRICES).text().trim()).isEqualTo("EUR 25,99");
        assertThat(getFirstElement(ASINS).attr("data-item-prime-info")).contains("{\"asin\":\"");
        assertThat(getFirstElement(ASINS).attr("data-item-prime-info")).contains(",\"id\":\"");
        assertThat(getFirstElement(PICTURES).attr("src")).contains("amazon.com");
        assertThat(getFirstElement(OFFERED_BY).text()).isEqualTo("Angeboten von Amazon.");
    }

    private Element getFirstElement(String selector) {
        return fetchedAmazonWishList.select(selector).get(0);
    }


}