package org.github.amazon_wishlist_ws.fetcher;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by christian.draeger on 26.04.16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AmazonElement {
    private String title;
    private String price;
    private String asin;
    private String id;
    private String itemUrl;
    private String pictureUrl;
    private String offeredBy;
    private int isbn;
}
