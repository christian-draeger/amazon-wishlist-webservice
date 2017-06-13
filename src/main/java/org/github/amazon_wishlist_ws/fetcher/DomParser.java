package org.github.amazon_wishlist_ws.fetcher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.swagger.annotations.ApiModelProperty;

@Service
public class DomParser {

    private static final String WISH_LIST_HEADLINE_SELECTOR = "#wl-list-info h1";
    private static final String PAGINATION_SELECTOR = ".a-last>a";
    private static final String ITEMS = "h5 a";
    private static final String PRICES = "[id^=itemPrice_]";
    private static final String ASINS = "[data-item-prime-info]";
    private static final String PICTURES = "[id^=itemImage] a img";
    private static final String OFFERED_BY = ".itemAvailOfferedBy";

    private final WishListFetcher wishListFetcher;
    private List<AmazonElement> amazonElementList = new ArrayList<>();

    @Inject
    public DomParser(WishListFetcher wishListFetcher) {
        this.wishListFetcher = wishListFetcher;
    }

    @JsonProperty(required = true, defaultValue = "https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ")
    @ApiModelProperty(notes = "get the amazon wish list by url", required = true)
    public Wishlist getWishListByUrl(String amazonWishlistUrl) {

        Document wl = wishListFetcher.getFetchedAmazonWishList(amazonWishlistUrl);
        return getWishlist(amazonWishlistUrl, amazonElementList, wl);
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "get the amazon wish list by tld and id", required = true)
    public Wishlist getWishListByID(String tld, String id) {
        List<AmazonElement> amazonElementList = new ArrayList<>();
        String amazonWishlistUrl = "https://www.amazon." + tld + "/gp/registry/wishlist/" + id;
        Document wl = wishListFetcher.getFetchedAmazonWishList(amazonWishlistUrl);

        return getWishlist(amazonWishlistUrl, amazonElementList, wl);
    }

    private Wishlist getWishlist(String amazonWishlistUrl, List<AmazonElement> amazonElementList, Document wl) {

        Elements listName = wl.select(WISH_LIST_HEADLINE_SELECTOR);


        Elements items = wl.select(ITEMS);
        Elements prices = wl.select(PRICES);
        Elements asins = wl.select(ASINS);
        Elements pictures = wl.select(PICTURES);
        Elements offeredBy = wl.select(OFFERED_BY);

        if (items.isEmpty()) {
            throw new IllegalArgumentException("invalid Amazon wish list URL");
        }

        for (int index = 0; index < items.size(); index++) {
            AmazonElement amzElement = new AmazonElement();

            amzElement.setTitle(items.get(index).text());
            amzElement.setItemUrl("http://www.amazon." + getAmazonTld(amazonWishlistUrl) + items.get(index).attr("href"));
            amzElement.setPictureUrl(pictures.get(index).attr("src"));

            String asin = getAsin(asins, index);

            if (isIsbn(asin)) {
                amzElement.setIsbn(asin);
            } else {
                amzElement.setAsin(asin);
            }

            amzElement.setId(getId(asins, index));
            amzElement.setPrice(prices.get(index).text());
            amzElement.setOfferedBy(offeredBy.get(index).text());

            amazonElementList.add(amzElement);
        }

        String paginationUri = wl.select(PAGINATION_SELECTOR).attr("href");

        if (!paginationUri.isEmpty()) {
            String paginationUrl = "http://www.amazon." + getAmazonTld(amazonWishlistUrl) + paginationUri;
            getWishListByUrl(paginationUrl);
        }

        return new Wishlist(listName.get(0).text(), amazonWishlistUrl, amazonElementList);
    }

    private boolean isIsbn(String asin) {
        return asin.matches("[0-9]+") && asin.length() > 2;
    }

    private String getAmazonTld(String url) {
        String tld = StringUtils.substringAfterLast(url, "amazon.");
        return StringUtils.substringBefore(tld, "/");
    }

    private String getAsin(Elements asins, int index) {
        String asin = getAsinAndId(asins, index).get("asin").toString();
        return asin.substring(1, asin.length() - 1);
    }

    private String getId(Elements asins, int index) {
        String id = getAsinAndId(asins, index).get("id").toString();
        return id.substring(1, id.length() - 1);
    }

    private JsonObject getAsinAndId(Elements asins, int index) {
        String asinAndID = asins.get(index).attr("data-item-prime-info");
        JsonElement asinAndId = new JsonParser().parse(asinAndID);
        return asinAndId.getAsJsonObject();
    }
}
