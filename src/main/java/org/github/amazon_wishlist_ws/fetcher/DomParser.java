package org.github.amazon_wishlist_ws.fetcher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.swagger.annotations.ApiModelProperty;

@Service
public class DomParser {

    private final WishListFetcher wishListFetcher;

    @Inject
    public DomParser(WishListFetcher wishListFetcher) {
        this.wishListFetcher = wishListFetcher;
    }

    @JsonProperty(required = true, defaultValue = "https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ")
    @ApiModelProperty(notes = "get the amazon wish list by url", required = true)
    public Wishlist getWishListByUrl(String amazonWishlistUrl) {
        List<AmazonElement> amazonElementList = new ArrayList<>();
        return getWishlist(amazonWishlistUrl, amazonElementList);
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "get the amazon wish list by tld and id", required = true)
    public Wishlist getWishListByID(String tld, String id) {
        List<AmazonElement> amazonElementList = new ArrayList<>();

        String amazonWishlistUrl = "https://www.amazon." + tld + "/gp/registry/wishlist/" + id;
        return getWishlist(amazonWishlistUrl, amazonElementList);
    }

    private Wishlist getWishlist(String amazonWishlistUrl, List<AmazonElement> amazonElementList) {

        Document wl = wishListFetcher.getFetchedAmazonWishList(amazonWishlistUrl);
        boolean isNewWishListLayout = wl.select("#wl-list-info h1").isEmpty();

        String wishListHeadlineSelector;
        String paginationSelector;
        String itemsSelector = "[id^=item_]";
        String titleSelector = "h5 a";
        String pricesSelector = "[id^=itemPrice_]";
        String asinsSelector = "[data-item-prime-info]";
        String picturesSelector = "[id^=itemImage] a img";
        String offeredBySelector;

        if (isNewWishListLayout) {
            wishListHeadlineSelector = "#list-header h3";
            paginationSelector = "a.wl-see-more";
            offeredBySelector = ".itemVailOfferedBy";
        } else {
            wishListHeadlineSelector = "#wl-list-info h1";
            paginationSelector = ".a-last>a";
            offeredBySelector = ".itemAvailOfferedBy";
        }

        Elements items = wl.select(itemsSelector);

        String wishListHeadline = wl.select(wishListHeadlineSelector).text();

        if (wishListHeadline.isEmpty()) {
            throw new IllegalArgumentException("invalid Amazon wish list URL");
        }

        for (Element item : items) {
            AmazonElement amzElement = new AmazonElement();

            amzElement.setTitle(item.select(titleSelector).text());
            amzElement.setItemUrl("http://www.amazon." + getAmazonTld(amazonWishlistUrl) + item.select(titleSelector).attr("href"));
            amzElement.setPictureUrl(item.select(picturesSelector).attr("src"));

            String asin = getAsin(item, asinsSelector);

            if (isIsbn(asin)) {
                amzElement.setIsbn(asin);
            } else {
                amzElement.setAsin(asin);
            }

            amzElement.setId(getId(item, asinsSelector));
            amzElement.setPrice(item.select(pricesSelector).text());
            amzElement.setOfferedBy(item.select(offeredBySelector).text());

            amazonElementList.add(amzElement);
        }

        String paginationUri = wl.select(paginationSelector).attr("href");

        if (!paginationUri.isEmpty()) {
            String paginationUrl = "http://www.amazon." + getAmazonTld(amazonWishlistUrl) + paginationUri;
            getWishlist(paginationUrl, amazonElementList);
        }

        return new Wishlist(wishListHeadline, amazonWishlistUrl, amazonElementList);
    }

    private boolean isIsbn(String asin) {
        return asin.matches("[0-9]+") && asin.length() > 2;
    }

    private String getAmazonTld(String url) {
        String tld = StringUtils.substringAfterLast(url, "amazon.");
        return StringUtils.substringBefore(tld, "/");
    }

    private String getAsin(Element item, String asinsSelector) {
        String asin = getAsinAndId(item, asinsSelector).get("asin").toString();
        return asin.substring(1, asin.length() - 1);
    }

    private String getId(Element item, String asinsSelector) {
        String id = getAsinAndId(item, asinsSelector).get("id").toString();
        return id.substring(1, id.length() - 1);
    }

    private JsonObject getAsinAndId(Element item, String asinsSelector) {
        String asinAndID = item.select(asinsSelector).attr("data-item-prime-info");
        if (!asinAndID.isEmpty()) {
            return new JsonParser().parse(asinAndID).getAsJsonObject();
        }
        return new JsonParser().parse("{\"asin\":\"\",\"id\":\"\"}").getAsJsonObject();
    }
}
