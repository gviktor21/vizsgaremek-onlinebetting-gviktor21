# vizsgaremek-onlinebetting-gviktor21

## Rövid leírás:
Ez a backend része egy olyan online fogadást lebonyolító alkalmazásnak (weboldalnak), ahol lehet adott sportesemény győztesére fogadni vagy lottó számaira „fogadni”. A felhasználó magának tölti föl az egyenlegét. Ezzel a feltöltött mennyiségből tud fogadásokat lebonyolítani.

*Megjegyzés:* A 'bid' szó helytelenül van használva. Ahol 'bid' szó előfordul ott 'bet' szóra kell gondolni. Ez nem egy aukcióház hanem egy fogadóiroda.

## Adatbázis Táblák/ Entitások. 

<b>BIDAPPUSER</b>(username, address, birthDate, gender, password, balance, email, account_level); // A felhasználó adatait tartalmazó tábla.

<b>EVENT</b>(event_id,event_type, start_date, end_date, description); // Eseményeket foglalja magában lehet sportesemény vagy lottóhúzás is egy esemény.

<b>BID</b>(bid_id, username, event_id, bid_type, bid_amount, prize, date); // Egy fogadást foglal magában.

<b>BIDLOTTO5</b>(bid_id, matches, number1, number2, number3, number4, number5); // Egy ötöslottó fogadást foglal magában. Gyenge entitás az id-t a BID -től 'kapja'. Legkönyebben egy lottószelvényként lehet értelmezni. 

<b>BIDLOTTO6</b>(bid_id, matches, number1, number2, number3, number4, number5, number6); //Ugyanaz mint az előző csak hatoslottóval.

<b>BIDSCANDINAVIAN</b>(bid_id, matches, number1, number2, number3, number4, number5, number6, number7,comp_matches, comp_num1, comp_num2, comp_num3, comp_num4, comp_num5, comp_num6, comp_num7,matches_computer); //Ugyanaz mint az előző csak skandinávlottóval. Itt vannak gépi számok is. 

<b>PARTICIPANT</b>(participant_id, name, sport_type); // Egy sportolót foglal magában.

<b>SPORTBID</b>(sport_bid_id, bid_id, participant_id); // Ez egyfajta kapcsolótábla. Hozzáadtam egy 'sport_bid_id'-t, hogy könyebb legyen az élet.

<b>SPORTEVENT</b>(event_id, winner_id, sport_type); //Gyenge entitáns. Ez a tábla a sporteseményeket foglalja magába. 1-1 kapcsolatba van az 'Event' táblával.

<b>SPORT_PARTICIPANT</b>(id, event_id, participant_id, multiplier); //

<b>LOTTO5</b>(event_id, multiplier, number1, number2, number3, number4, number5); //Gyenge entitáns. Mint ötöslottóhúzásra kell rágondolni. 1-1 kapcsolatba van az 'Event' táblával.


<b>LOTTO6</b>(event_id, multiplier, number1, number2, number3, number4, number5, number6); //Gyenge entitáns. Mint ötöslottóhúzásra kell rágondolni.

<b>SCANDINAVIAN</b>(event_id,multiplier, number1, number2, number3, number4, number5, number6, number7, comp_num1, comp_num2, comp_num3, comp_num4, comp_num5, comp_num6, comp_num7); //Gyenge entitáns. Mint az előző kettő..


## API endpoint-ok

<b>POST</b> */event* Egy esemény (sport/lottósorsolás) feltöltése.

<b>POST</b> */user* Az adott felhasználó feltolt egy adott sporteseményre egy fogadást.

<b>PUT</b> *user/{username}* Módosás feltöltése a {username} felhasnálóhoz.

<b>POST</b> */bid* Egy fogadás feltöltése. 

 <b>GET</b> */user/{username}* Adott nevű felhasunáló lekérése.

 <b>DELETE</b> */bid/{id}* Töröljük egy fogadást az id-ja alapján.

<b>POST</b> */bidlotto5* Hozzáadunk egy lotto5 fogadást. Gyakorlatilag úgy kell elképzelni mint, ha kitöltenénk egy lottószelvényt.

## A jelenlegi állapot:
Csak az ötöslottó lett megvalósítva, a hatosra és a skandinávra nem maradt idő. A sportfogadás rész is igen gyéren lett tesztelve.
- "In-memory" h2-s adatbáziskezelő-rendszer van használva.
- 13 entitás(9 van használva). Több n-1 kapcsolat.
- 9 kontroller és szervíz osztály.
- 45 enpoint( Jópár nem lett tesztelve, tehát nem garantált a működésük )
- Az itt felsorolt endpointok tesztelve vannak és működnek.
- Van loggolás.
- Flyway migráció van.
- Van validáció.
- Swagger /openapi dokumentáció nincs. Az okokról lentebb írok.
- Tesztlefedettség: 60% feletti (Beépített Intellij plugin szerint). Van 9 db unit teszt a kontroller osztályokra. Három a szervíz osztályokra. Továbbá két integrációs tesztosztály. Tehát összesen 117 db teszteset van.
- A kezdeti adatbázis üres.
## Az alkalmazás telepítése és indítása:
A következő parancsokat a program főkönyvtárában kell kiadni.
1. A docker image fájlt lebuildelni windows alatt a 'dockerbuild.bat' kell elindítani. Linux rendszerek alatt a 'dockerbuild.sh' nevű scriptet.

2. A 'lebuildelt' image fájlból futtatni az alkalmazást windows alatt a 'dockerrun.bat' elindításával kell. Linux rendszerek esetében 'dockerrun.sh' shellscriptet kell futtatni.
3. Az alkalmazás alapvetően 8080-as porton érhető el.

A teszteket állítólag a következő utasítással lehet elindítani:

```
mvn test
```
*Megjegyzés:* Csak windows alatt lett tesztelve.
## Használati utasítás/ Endpointok működése.
Itt egy példasorozton keresztül mutatom be az endpointok működését. A példák sorrendje számíthat. A példában az alkalmazás a 'localhost' 8080-as portján keresztül érhető el. A legtöbb endpoint JSON-t használ bemenő adatként (vagy semmit). Nincsen url-ben tárolt adatküldés.

### 1. endpoint: <b>POST</b> */event*
Feltöltünk egy eseményt. Fontos ha múltbeli eseményt töltünk föl, arra már nem lehet fogadást(Bid) hozzáadni.

Input példa: Feltöltünk egy Lottóhúzás eseményt. Az eseményt jellemző adatokat JSON-ben adjuk át a http-kérés 'body'-ában. Az eredményt a 'localhost:8080/event' -re küldött 'GET' kéréssel ellenőrizhetjük. 
```
{
    "eventType": "LOTTO5",
    "startDate": "2022-11-11",
    "endDate": "2022-11-11",
    "description": "Lotto 5 sorsolas 55 het"
}
```
### 2. Endpoint : <b>POST</b> */user*
Feltöltünk egy felhasználót. A felhasználó adatait JSON-ben adjuk át a http-kérés 'body'-ában. Az eredményt a 'localhost:8080/user' -re küldött 'GET' kéréssel ellenőrizhetjük. 

```
{
    "username": "viktor",
    "address": "America",
    "birthDate": "1991-11-04",
    "gender": "male",
    "password": "admin",
    "balance": 0,
    "email": "godov2@freemail.hu",
    "accountLevel": 3
}
```

### 3. Endpoint: <b>PUT</b> *user/{username}* 
Módosítunk egy felhasználót.

Példa: Feltöltjük az előző üres felhasználó egyenlegét 1000-re. És jelszót is módosítjuk:'newFancyPassword'-re.
A user/viktor endpointra ment a kérés.
```
{
    "username": "viktor",
    "address": "America",
    "birthDate": "1991-11-04",
    "gender": "male",
    "password": "newFancyPassword",
    "balance": 1000,
    "email": "godov2@freemail.hu",
    "accountLevel": 3
}
```

### 4. Endpoint: <b>POST</b> */bid*
Hozzáadunk egy fogadást (Bid)-et. Fontos múltbeli eseményre nem lehet fogadni. És a 'bidAmount' nem lépheti túl a felhasználó egyenlegét. Természetesen csak adatbázisban létező eseményre lehet fogadni. Ez igaz a felhasználóra is. A date nem lehet kisebb a napi dátumnál. Az adatokat itt JSON-ben kell elküldeni.
 Az eredményt a 'localhost:8080/bid' -re küldött 'GET' kéréssel ellenőrizhetjük. De működik a bid/{id}- is.
 
Példa: Feltöltünk egy fogadást a 'viktor' felhasználóra, és az 1. számú event-re. Figyelem a 'date' hibát jelezhet ha '2022-07-05' után teszteljük.
```
{
    "bidType": "lottofogadas",
    "username": "viktor",
    "eventId": 1,}
    "bidAmount": 100,
    "prize": 1000,
    "date": "2022-07-05"
}
```

### 5. Endpoint: <b>GET</b> */user/{username}*
Lekérünk egy felhasználót a neve alapján. Vissza is kap.

Példa: Ha lekérjük a példában használt felhasználót akkor, ha sikeresen hozzáadtuk a fogadást akkor az egyenlege kevesebb lett.

### 6. Endpoint: <b>DELETE</b> */bid/{id}*
Törölünk egy fogadást az id-ja alapján.

Fun fact: A pénzt nem kapja vissza a felhasználó. :) (Idő hiányában nem lett implementálva)

### 7. Endpoint: <b>POST</b> */bidlotto5*
Hozzáad egy ötöslottó fogadást. Ehhez a bidId-nak egy létező Bid táblára mutató bejegyzésre kell mutatni. Továbbá ennek a sornak egy olyan eseményre kell mutattni, ami Lotto5 típusú. A számok 1-90 között vannak és különbözőeknek kell lenniük.

Hogy világos legyen:
1. Ha Bid táblára viszek fel egy sort az olyan mintha vennék egy lottószelvényt( Amennyiben egy lottóhúzás eseményre "fogadsz".)
2. Ha a BidLotto5 táblára viszek fel egy sort az olyan mintha egy megvásárolt lottószelvényt töltenék ki.

példa: Ha az előző példában letöröltük a fogadás táblában lévő sort, akkor új fogadást kell felvinnünk.
```
{
    "bidId":1,
    "number1" :22,
    "number2" : 53,
    "number3" : 5,
    "number4" : 7,
    "number5" : 24
}
```

## Miért nincs swagger?

Tesztírás közben előjött, ahhoz hogy a 'jackson' 'szerilizálni' tudjon 'LocalDate'-eket, hozzá kellet adnom a következő függőséget:

```
	<dependency>
			<groupId>com.fasterxml.jackson.datatype</groupId>
			<artifactId>jackson-datatype-jsr310</artifactId>
	</dependency>
```

Ez viszon  valamilyen konfliktusban áll a Swagger-el, amit használok:
```
        <dependency>-->
			<groupId>org.springdoc</groupId>-->
			<artifactId>springdoc-openapi-ui</artifactId>-->
			<version>1.6.9</version>-->
	</dependency>-->
```
Mivel a swagger 2-őt annyira nem ismertem ezért kikommentelve de benne maradt a programban. Próbálkoztam többmindennel is, hogy műkösésre bírjam a kettőt de nem ment.  A probléma nem ismeretlen:

https://stackoverflow.com/questions/51636477/conflict-between-swagger2-and-jackson-datatype-jsr310-in-spring-boot-application

https://stackoverflow.com/questions/70937537/swagger-generated-code-date-issue-java-8-date-time-type-java-time-offsetdatetim


Az esetleges nyelvtani hibákért és ez álltal okozott traumákért semmilyen felelősséget nem vállalok.
