package org.github.amazon_wishlist_ws.fetcher;

import java.util.List;

import lombok.Value;
import lombok.experimental.NonFinal;

/**
 * Created by christian.draeger on 26.04.16.
 */
@Value
@NonFinal
public class Wishlist {
    String name;
    String url;
    List<AmazonElement> items;
}
