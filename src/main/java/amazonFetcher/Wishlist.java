package amazonFetcher;

import java.util.List;

import lombok.Value;
import lombok.experimental.NonFinal;

/**
 * Created by christian.draeger on 26.04.16.
 */
@Value
@NonFinal
public class Wishlist {
    List<AmazonElement> items;
    String name;
    String url;
}
