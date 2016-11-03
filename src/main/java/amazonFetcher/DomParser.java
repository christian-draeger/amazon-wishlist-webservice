package amazonFetcher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class DomParser {
    @Inject
    private WishListFetcher wishListFetcher;

    public Wishlist getWishList(String amazonWishlistUrl) {
        List<AmazonElement> amazonElementList = new ArrayList<>();
        Document wl = wishListFetcher.getFetchedAmazonWishList(amazonWishlistUrl);

        String paginationUri = wl.select(".a-last>a").attr("href");
        String paginationUrl = "http://www.amazon." + getAmazonTld(amazonWishlistUrl) + paginationUri;
        Elements listName = wl.select("#wl-list-info h1");

        Elements items = wl.select("h5 a");
        Elements prices = wl.select("[id^=itemPrice_]");
        Elements asins = wl.select("[data-item-prime-info]");
        Elements pictures = wl.select("[id^=itemImage] a img");
        Elements offeredBy = wl.select(".itemAvailOfferedBy");

        if (items.isEmpty()) {
            throw new IllegalArgumentException("invalid Amazon wish list URL");
        }

        for (int index = 0; index < items.size(); index++) {
            AmazonElement amzElement = new AmazonElement();

            amzElement.setTitle(items.get(index).text());
            amzElement.setItemUrl("http://www.amazon." + getAmazonTld(amazonWishlistUrl) + items.get(index).attr("href"));
            amzElement.setPictureUrl(pictures.get(index).attr("src"));

            String asinAndID = asins.get(index).attr("data-item-prime-info");
            JsonElement asinAndId = new JsonParser().parse(asinAndID);
            String asin = asinAndId.getAsJsonObject().get("asin").toString();
            asin = asin.substring(1, asin.length() - 1);

            if (isIsbn(asin)) {
                amzElement.setIsbn(Integer.parseInt(asin));
            } else {
                amzElement.setAsin(asin);
            }

            String id = asinAndId.getAsJsonObject().get("id").toString();

            amzElement.setId(id.substring(1, id.length() - 1));
            amzElement.setPrice(prices.get(index).text());
            amzElement.setOfferedBy(offeredBy.get(index).text());

            amazonElementList.add(amzElement);
        }

        if (!paginationUri.isEmpty()) {
            getWishList(paginationUrl);
        }

        return new Wishlist(amazonElementList, listName.get(0).text(), amazonWishlistUrl);
    }

    private boolean isIsbn(String asin) {
        return asin.matches("[0-9]+") && asin.length() > 2;
    }

    private String getAmazonTld(String url) {
        String tld = StringUtils.substringAfterLast(url, "amazon.");
        return StringUtils.substringBefore(tld, "/");
    }
}
