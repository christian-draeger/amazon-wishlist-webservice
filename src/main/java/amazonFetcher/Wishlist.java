package amazonFetcher;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by christian.draeger on 26.04.16.
 */
@Data
@AllArgsConstructor
public class Wishlist {

    List<AmazonElement> items;
    String name, url;

}
