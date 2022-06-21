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

<b>POST</b> */user/{username}/bid/lotto6/{event_id}* Az adott felhasználó „megvásárol”(feltölt) egy adott lottósorsolásra lottószelvényt.

<b>POST</b> *user/{username}/bid/sport/{event_id}* Az adott felhasználó feltolt egy adott sporteseményre egy fogadást.

<b>POST</b> */admin/{username}/result/sport/event_id* Az adott felhasználó ha admin jogokkal rendelkezik feltölti egy adott sporteseménynek az eredményét (beállítani a győztest). Ezután kiértékelődnek az adott győztesnek az „odds-jai és ez alapján változhatnak az egyenlegek. 

<b>POST</b> */admin/{username}/result/lotto5/event_id* Ugyanaz mint az előző csak az ötöslottóval. Ezután kiértékelődnek a lottószelvények és ez alapján változhatnak az egyenlegek. 

<b>PUT</b> *user/{username}/passw* Jelszómódosítás az adott felhasználónál

<b>GET</b> *sport/{event_id}/participants* Lekérdezni az adott sporteseményen kik induln(t)ak.

<b>DELETE</b> *user/{username}/bid/{bid_id}* A felhasználó kísérletet tesz egy adott fogadást visszavonni.

<b>Megjegyzés:</b> A lista nem teljes. Ezek még nem végső endpoint-ok. Még módosulhatnak.

## A jelnlegi állapot:

A participant, event és az event CRUD-ok tesztelve vannak. A többinél még biztosan változni fog a 'Business Logic'.

## Ami még hiányzik:

1. hozzáadni a maradék service-eket és a Controllereket, kivéve a 6-os és SkandinávLottó-t (Csak akkor lesz leimplementálva ha marad rá idő)
2. loggingolás és swagger hozzáadása
3. flyway hozzáadása
4. docker konténer elkészítése
