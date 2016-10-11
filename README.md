**This webservice will fetch data from public amazon wish lists and responds in json format**

e.g. thispublic amazon wishlist:
https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ/ref=cm_wl_list_o_2?)

just call
`mvn spring-boot:run`
on your command line

Afterwards you can just ask the webservice for any public amazon wish list url.
It also can handle big wish lists with paginantion.

e.g.
http://localhost:8585/wishlist?url=https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ/ref=cm_wl_list_o_2?

you will get a result including:
* name of the wish list
* url of the wish list
* array of items including item specific data like:
* * item title
* * item price (if available)
* * item price (if available)
* * asin or iban (denpends on item)
* * id
* * item url
* * item picture url
* * offered by (if available)

