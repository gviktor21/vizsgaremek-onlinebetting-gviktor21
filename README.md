# vizsgaremek-onlinebetting-gviktor21

## Rövid leírás:
Ez a backend része egy olyan online fogadást lebonyolító alkalmazásnak (weboldalnak), ahol lehet adott sportesemény győztesére fogadni vagy lottó számaira „fogadni”. A felhasználó magának tölti föl az egyenlegét. Ezzel a feltöltött mennyiségből tud fogadásokat lebonyolítani.

## Az egyed-kapcsolat diagram alapján létrehozott táblák:

<b>BIDAPPUSER</b>(username, address, birthDate, gender, password, balance, email, account_level);

<b>EVENT</b>(event_id,event_type, start_date, end_date, description);

<b>BID</b>(bid_id, username, event_id, bid_type, bid_amount, prize, date);

<b>BIDLOTTO5</b>(bid_id, matches, number1, number2, number3, number4, number5);

<b>BIDLOTTO6</b>(bid_id, matches, number1, number2, number3, number4, number5, number6);

<b>BIDSCANDINAVIAN</b>(bid_id, matches, number1, number2, number3, number4, number5, number6, number7,comp_matches, comp_num1, comp_num2, comp_num3, comp_num4, comp_num5, comp_num6, comp_num7,matches_computer);

<b>PARTICIPANT</b>(participant_id, name, sport_type);

<b>SPORTBID</b>(sport_bid_id, bid_id, participant_id);

<b>SPORTEVENT</b>(event_id, winner_id, sport_type);

<b>SPORT_PARTICIPANT</b>(id, event_id, participant_id, multiplier);

<b>LOTTO5</b>(event_id, multiplier, number1, number2, number3, number4, number5);

<b>LOTTO6</b>(event_id, multiplier, number1, number2, number3, number4, number5, number6);

<b>SCANDINAVIAN</b>(event_id,multiplier, number1, number2, number3, number4, number5, number6, number7, comp_num1, comp_num2, comp_num3, comp_num4, comp_num5, comp_num6, comp_num7);

## API endpoint-ok

<b>POST</b> */event* Egy esemény (sport/lottósorsolás) feltöltése.

<b>POST</b> */user* Az adott felhasználó feltolt egy adott sporteseményre egy fogadást.

<b>POST</b> */admin/{username}/result/sport/event_id* Az adott felhasználó ha admin jogokkal rendelkezik feltölti egy adott sporteseménynek az eredményét (beállítani a győztest). Ezután kiértékelődnek az adott győztesnek az „odds-jai és ez alapján változhatnak az egyenlegek. 

<b>POST</b> */bid* Ugyanaz mint az előző csak az ötöslottóval. Ezután kiértékelődnek a lottószelvények és ez alapján változhatnak az egyenlegek. 

<b>PUT</b> *user/{username}* 

<b>GET</b> *sport/{event_id}/participants* Lekérdezni az adott sporteseményen kik induln(t)ak.

<b>DELETE</b> *user/{username}/bid/{bid_id}* A felhasználó kísérletet tesz egy adott fogadást visszavonni.


## A jelnlegi állapot:

A participant, event és az event CRUD-ok tesztelve vannak. A többinél még biztosan változni fog a 'Business Logic'.

## Használati utasítás:
Itt egy példasorozton keresztül mutatom be az endpointok működését. A példák sorrendje számíthat. A példában az alkalmazás a 'localhost' 8080-as portján keresztül érhető el.

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
Hozzáadunk egy fogadást (Bid)-et. Fontos múltbeli eseményre nem lehet fogadni. És a 'bidAmount' nem lépheti túl a felhasználó egyenlegét. Természetesen csak adatbázisban létező eseményre lehet fogadni. Ez igaz a felhasználóra is. A date nem lehet kisebb a napi dátumnál. Az adatokat itt JSON-ben küldtük.
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
### 7. Endpoint:



