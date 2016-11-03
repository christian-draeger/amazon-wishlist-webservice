package amazonFetcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.github.amazon_wishlist_ws.fetcher.DomParser;
import org.github.amazon_wishlist_ws.fetcher.WishListFetcher;
import org.github.amazon_wishlist_ws.fetcher.Wishlist;
import org.jsoup.nodes.Document;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import mockito.MockitoTest;

public class DomParserTest extends MockitoTest {
    @InjectMocks
    private DomParser domParser;
    @Mock
    private WishListFetcher wishListFetcher;

    @Test(enabled = false, description = "Think about mocking of document")
    public void getWishList() {
        // given
        Document document = mock(Document.class);
        when(wishListFetcher.getFetchedAmazonWishList("http://amazon.de/wishlist")).thenReturn(document);

        // when
        Wishlist wishList = domParser.getWishList("http://amazon.de/wishlist");

        // then
        assertThat(wishList, notNullValue());
    }
}
