# tikape-harkka2

Web-sovellus tarjoaa käyttöliittymän luoda kursseihin liittyviä monivalintatyylisiä kysymyksiä, jotka tallennetaan tietokantaan. Uutta
kysymystä luotaessa luodaan kurssi, jonka alle kysymys kuuluu, sekä "tägätään" kysymys aiheella ja lisätään kysymykselle kysymysteksti. Kun
kysymys on luotu, voidaan kysymystekstiin upotetusta linkistä siirtyä kysymyskohtaiselle sivulle, jossa voidaan luoda vastausvaihtoehtoja.
Vastausvaihtoehtoja voi lisätä yhden kerrallaan antamalla siihen liittyvän vastaustekstin sekä tiedon vastauksen oikeellisuudesta. Sekä
kysymyksen että vastausvaihtoehdon voi poistaa.

Sovelluksen kysymykset listaava pääsivu sijaitsee web-palvelimella polulla "/kysymykset", jonne myös ohjataan polusta "/".
