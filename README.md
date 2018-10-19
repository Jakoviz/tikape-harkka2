# tikape-harkka2

Web-sovellus tarjoaa käyttöliittymän luoda kursseihin liittyviä monivalintatyylisiä kysymyksiä, jotka tallennetaan tietokantaan. Uutta
kysymystä luotaessa luodaan kurssi, jonka alle kysymys kuuluu, sekä "tägätään" kysymys aiheella ja lisätään kysymykselle kysymysteksti. Kun kysymys on luotu, voidaan kysymystekstiin upotetusta linkistä siirtyä kysymyskohtaiselle sivulle, jossa voidaan luoda vastausvaihtoehtoja. Vastausvaihtoehtoja voi lisätä yhden kerrallaan antamalla siihen liittyvän vastaustekstin sekä tiedon vastauksen oikeellisuudesta. Sekä kysymyksen että vastausvaihtoehdon voi poistaa.

Sovelluksen kysymykset listaava pääsivu sijaitsee web-palvelimella polulla "/kysymykset", jonne myös ohjataan polusta "/".

Tässä sovelluksen käyttämät tietokantataulut:

                                     Table "public.kysymys"
    Column     |       Type        | Collation | Nullable |               Default
---------------+-------------------+-----------+----------+-------------------------------------
 id            | integer           |           | not null | nextval('kysymys_id_seq'::regclass)
 aihe          | character varying |           |          |
 kysymysteksti | character varying |           |          |
 kurssi_id     | integer           |           |          |
Indexes:
    "kysymys_pkey" PRIMARY KEY, btree (id)
Foreign-key constraints:
    "kysymys_kurssi_fk" FOREIGN KEY (kurssi_id) REFERENCES kurssi(id)
Referenced by:
    TABLE "vastausvaihtoehto" CONSTRAINT "vastausvaihtoehto_kysymys_id_fkey" FOREIGN KEY (kysymys_id) REFERENCES kysymys(id)
    
                                 Table "public.kurssi"
 Column |       Type        | Collation | Nullable |              Default
--------+-------------------+-----------+----------+------------------------------------
 id     | integer           |           | not null | nextval('kurssi_id_seq'::regclass)
 nimi   | character varying |           |          |
Indexes:
    "kurssi_pkey" PRIMARY KEY, btree (id)
Referenced by:
    TABLE "kysymys" CONSTRAINT "kysymys_kurssi_fk" FOREIGN KEY (kurssi_id) REFERENCES kurssi(id)
    
                                     Table "public.vastausvaihtoehto"
    Column     |       Type        | Collation | Nullable |                    Default
---------------+-------------------+-----------+----------+-----------------------------------------------
 id            | integer           |           | not null | nextval('vastausvaihtoehto_id_seq'::regclass)
 vastausteksti | character varying |           |          |
 kysymys_id    | integer           |           |          |
 oikein        | boolean           |           |          |
Indexes:
    "vastausvaihtoehto_pkey" PRIMARY KEY, btree (id)
Foreign-key constraints:
    "vastausvaihtoehto_kysymys_id_fkey" FOREIGN KEY (kysymys_id) REFERENCES kysymys(id)
