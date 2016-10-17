**This webservice will fetch data from public amazon wish lists and responds in json format**

e.g. this public amazon wishlist:
https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ/ref=cm_wl_list_o_2?

just call
`mvn spring-boot:run`
on your command line

Afterwards you can ask the webservice for any public amazon wish list url (It can handle big wish lists with paginantion as well).

curl example:
`curl -H "Content-Type: application/json" -X GET http://localhost:8585/ist_o_2t?url=https://www.amazon.de/gp/registry/wishlist/CGACJDFKWTIZ/ref=cm_wl_li`

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

