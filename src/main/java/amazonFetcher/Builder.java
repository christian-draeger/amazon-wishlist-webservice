package amazonFetcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Builder {

    private List<AmazonElement> amazonElementList = new ArrayList<>();
    private Elements listName;


    public Wishlist getWishListJson(final String url) {
        amazonElementList = new ArrayList<>();
        return getAmazonWishListJsonAsString(url);
    }

    private Wishlist getAmazonWishListJsonAsString(final String amazonWishlistUrl) {

        Document wl = null;
        try {
            wl = new WishListFetcher().getFetchedAmazonWishList(amazonWishlistUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String paginationUri = wl.select(".a-last>a").attr("href");
        String paginationUrl = "http://www.amazon." + getAmazonTld(amazonWishlistUrl) + wl.select(".a-last>a").attr("href");
        listName = wl.select("#wl-list-info h1");

        Elements items = wl.select("h5 a");
        Elements prices = wl.select("[id^=itemPrice_]");
        Elements asins = wl.select("[data-item-prime-info]");
        Elements pictures = wl.select(".a-link-normal.a-declarative>img");
        Elements offeredBy = wl.select(".itemAvailOfferedBy");

        if (items.size() == 0){
            throw new IllegalArgumentException("invalid Amazon wish list URL");
        } else {

            for (int index = 0; index < items.size(); index++) {
                AmazonElement amzElement = new AmazonElement();

                amzElement.setTitle(items.get(index).text());
                amzElement.setItemUrl("http://www.amazon." + getAmazonTld(amazonWishlistUrl) + items.get(index).attr("href"));
                amzElement.setPictureUrl(pictures.get(index).attr("src"));

                String asinAndID = asins.get(index).attr("data-item-prime-info");
                JsonElement asinAndId = new JsonParser().parse(asinAndID);
                String asin = asinAndId.getAsJsonObject().get("asin").toString();
                asin = asin.substring(1, asin.length() - 1);

                if (isIsbn(asin)){
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
        }

        if (!paginationUri.isEmpty()){
            getAmazonWishListJsonAsString(paginationUrl);
        }

        return new Wishlist(amazonElementList, listName.get(0).text(), amazonWishlistUrl);
    }

    private boolean isIsbn(String asin){
        if (asin.matches("[0-9]+") && asin.length() > 2){
            return true;
        }
        return false;
    }

    private String getAmazonTld(String url){
        String tld = StringUtils.substringAfterLast(url, "amazon.");
        return StringUtils.substringBefore(tld, "/");
    }
}
