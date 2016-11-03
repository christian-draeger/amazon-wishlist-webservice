package webservice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.github.amazon_wishlist_ws.fetcher.DomParser;
import org.github.amazon_wishlist_ws.fetcher.Wishlist;
import org.github.amazon_wishlist_ws.webservice.Controller;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import mockito.MockitoTest;

public class ControllerTest extends MockitoTest {
    @InjectMocks
    private Controller controller;
    @Mock
    private DomParser domParser;

    @Test
    public void amazonWishList() {
        // given
        Wishlist mockedWishList = mock(Wishlist.class);
        when(domParser.getWishList("http://amazon.de/wishlist")).thenReturn(mockedWishList);

        // when
        Wishlist wishList = controller.amazonWishList("http://amazon.de/wishlist");

        // then
        assertThat(wishList, is(mockedWishList));
    }

}
