package amazonFetcher;

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

    private String title, price, asin, id, itemUrl, pictureUrl, offeredBy;
    private int isbn;
}
