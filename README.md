# Amazon wish list api
This is a little API to retrieve Amazon Wish List data. There is no official API, as Amazon shut it down a couple years ago.

**This webservice will fetch data from public amazon wish lists and responds in json format**
>e.g. [this public amazon wish list]

amazon-wishlist-webservice uses [jSoup](https://jsoup.org/) (server-side CSS3 selector driven DOM API) to scrape Amazon's Wish List page and [spring-boot](https://projects.spring.io/spring-boot/) to have to have a standalone webservice instance that exports the response as JSON.

if you want to put some public or shared Amazon Wish List on your web page for instance, this project will fit your needs perfectly.

How to use
==========
to start the application navigate to the appropriate  path and call
`mvn spring-boot:run`
on your command line 

Afterwards you can ask the webservice for any public Amazon Wish List URL 
(It can handle big wish lists with paginantion as well).

curl example:
`curl -H "Content-Type: application/json" -X GET http://localhost:8585/ist_o_2t?url=https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ/ref=cm_wl_li`

you will get a result including:
* name of the wish list
* url of the wish list
* array of items including item specific data like:
  * item title
  * item price (if available)
  * item price (if available)
  * asin or iban (depends on item)
  * id
  * item url
  * item picture url
  * offered by (if available)

```
{
    items: [
        {
            title: "Apple Mac Pro",
            price: "EUR 12.851,77",
            asin: "B00JSLJF4Y",
            id: "I35H0B9VBOXXYH",
            itemUrl: "http://www.amazon.de/dp/B00JSLJF4Y/ref=wl_it_dp_v_nS_ttl/255-9550544-5201764?_encoding=UTF8&colid=CGACJDFKWTIZ&coliid=I35H0B9VBOXXYH",
            pictureUrl: "https://images-eu.ssl-images-amazon.com/images/G/03/wishlist/print_icon._CB286433166_.png",
            offeredBy: "Angeboten von DASTRO ® --- Einfach. Genial. Günstig."
        },
        {
            title: "Apple CTO MGEQ2D/A-03254​6 Mac mini Desktop-PC (Intel Core i7 4578U, 3GHz, 8GB RAM, 1TB HDD/SSD, Intel Iris, Mac OS) silber",
            asin: "B00TYGWIII",
            id: "I1GMNGASED7EMI",
            itemUrl: "http://www.amazon.de/dp/B00TYGWIII/ref=wl_it_dp_v_nS_ttl/255-9550544-5201764?_encoding=UTF8&colid=CGACJDFKWTIZ&coliid=I1GMNGASED7EMI",
            pictureUrl: "https://images-eu.ssl-images-amazon.com/images/I/31%2BzeJZDTrL._SL500_SL135_.jpg"
        },
        {
            title: "TRENDnet TEW-714TRU N150 Wireless Travel Router",
            price: "EUR 28,37",
            asin: "B00FEE7LW2",
            id: "IDD5PJECJQUU5",
            itemUrl: "http://www.amazon.de/dp/B00FEE7LW2/ref=wl_it_dp_v_nS_ttl/255-9550544-5201764?_encoding=UTF8&colid=CGACJDFKWTIZ&coliid=IDD5PJECJQUU5",
            pictureUrl: "https://images-eu.ssl-images-amazon.com/images/I/51kPCLt9k3L._SL500_SL135_.jpg",
            offeredBy: "Angeboten von Amazon."
        },
        {
            title: "Jenkins: The Definitive Guide",
            price: "EUR 27,95",
            id: "I6GEDEDYNXI9V",
            itemUrl: "http://www.amazon.de/dp/1449305350/ref=wl_it_dp_v_S_ttl/255-9550544-5201764?_encoding=UTF8&colid=CGACJDFKWTIZ&coliid=I6GEDEDYNXI9V",
            pictureUrl: "https://images-eu.ssl-images-amazon.com/images/I/41g%2BQme62zL._SL500_SL135_.jpg",
            offeredBy: "Angeboten von Amazon.",
            isbn: 1449305350
        }
    ],
    name: "Christians Wunschzettel 2",
    url: "https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ/ref=cm_wl_list_o_2"
}
```
[this public amazon wish list]: <https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ/ref=cm_wl_list_o_2?>


Support
=======

https://github.com/christian-draeger/amazon-wishlist-webservice/issues
