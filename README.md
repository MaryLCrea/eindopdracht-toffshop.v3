# Installatiehandleiding - Toffshop Backend API

Installatie Handleiding

Eindopdracht Back-End Mary Leenen
In opdracht van Novi-Hogeschool
Groningen, 26 september 2025

Inhoudsopgave:

1. Inleiding
2. Benodigdheden
3. Installatie Instructies
4. Testgebruikers
5. Postman Collecties
6. Rest Endpoints
7. Overige Commando’s

## 1. INLEIDING
Toffshop is een professionele Spring Boot REST API voor een webshop. De applicatie biedt uitgebreide functionaliteiten voor gebruikersbeheer, productcatalogus, winkelwagen beheer en contactformulieren.

Hoofdfunctionaliteiten:
• Gebruikersbeheer→  CRUD operaties voor customer en admin met validatie.
• Productcatalogus - Uitgebreid productbeheer met specifieke eigenschappen.
• Winkelwagen - Volledige shopping cart functionaliteit.
• Bestellingen - Order creation met verschillende statussen.
• Contact - Contactformulier met email validatie.
• Up- en download functionaliteit van bestanden.


## 2. BENODIGDHEDEN

Software + vereisten:
Java versie 21 met JDK Coretto 23
IntelliJ IDEA (aanbevolen) of andere Java IDE
Maven 3.6+ (of gebruik de meegeleverde Maven Wrapper)
pgAdmin 4 / PostgreSQL database server
Postman (voor API testing)

Database Vereisten:
PostgreSQL server draaiend op localhost:5432
Database genaamd toffshop
Gebruiker postgres met wachtwoord password


## 3. INSTALLATIE INSTRUCTIES (stappenplan)

Stap 1:
- Repository Clonen
  Git link: https://github.com/MaryLCrea/eindopdracht-toffshop.v3  
  Ga naar IntelliJ→ klik linksbovenin op de 4 streepjes→  klik op “new”→ klik op project from version control→ plak bovenstaande link bij URL en klik op “clone”.
- Projectbestand openen vanaf intelliJ→ File→ open→ zoek de betreffende map.

Stap 2: Project Openen in IntelliJ IDEA
Open IntelliJ IDEA
Kies "Open" en selecteer de map met pom.xml
Wacht tot Maven alle dependencies heeft gedownload
Controleer of Java 21 is geselecteerd met JDK versie Coretto 23.

Stap 3: Database Configuratie
(hou onderstaande installatie volgorde aan)

Installatie PostgreSQL
Ga naar  https://www.postgresql.org/download/
Selecteer je besturingssysteem→ klink links bovenin op download de installer→ klik op de nieuwste versie bij je besturingssysteem en volg de wizard door op next te klikken→ alle instellingen kunnen blijven staan, echter klik bij “select components” de optie Stack Builder uit. Voltooi de installatie.


Installatie pgAdmin 4

-Ga naar https://www.pgadmin.org/download/ en download de nieuwste versie passend bij je besturingssysteem→ installeer met de wizard en start de installatie. De eerste keer wordt een wachtwoord gevraagd. Dit is het PostgreSQL wachtwoord, en stel deze in op “password”.
-Open pgAdmin en maak een nieuwe database aan genaamd: toffshop
Je kunt dit doen door met je muis linksbovenin op database te gaan staan, rechtermuisklik en kies create database→ met naam toffshop.

Installatie Postman
-Download en installeer Postman https://www.postman.com/downloads/ middels de installatie wizard, maak een account aan (oranje knop) en open Postman.


Application Properties Configureren:
Pas src/main/resources/application.properties aan door onderstaande te kopieren en te plakken in application properties:
spring.application.name=eindopdracht-toffshop

spring.sql.init.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/toffshop
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database=postgresql
spring.jpa.show-sql=true

spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create

spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

Database configuratie
De Server configuratie staat standaard ingesteld op 8080
Wil je een andere server poort voeg dan toe: server.port=8081

Stap 4: Applicatie Starten
Via IntelliJ IDEA: Runnen via de groene pijl rechts bovenin.

Stap 5: Verificatie
Na het starten draait de applicatie op: http://localhost:8080


## 4. TESTGEBRUIKERS DATA.SQL
ID
Naam
Email
Wachtwoord
1
Piet Puk
piet@email.com
$2a$14$xA9v7sQGqiniWdh0AE0qQ.BlERiBbk60FaJRWdFg3wBWLgKH.pIK2
2
Tessa Pluk
tessa@email.com
$2a$14$vwtLcU.9HivcnD8Ne7SHMeGaXP.SVDJCujqRe4.KCtXTHpfuSVE6q


Test Data Overzicht in data.sql
2
Gebruikers met verschillende profielen
7
Producten (Fashion, Health, Kitchen, Tools)
2
Winkelwagens met verschillende items
4
Order items met verschillende statussen
4
Orders met verschillende statussen
2
E-Mail berichten

## 5. Postman collecties

{
"info": {
"name": "Toffshop - Test Collection",
"description": "Een compacte collectie met 6 endpoints voor testen",
"schema": "https://schema.getpostman.com/json/collection/v2.0.0/collection.json"
},
"item": [
{
"name": "users | Register Marina",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\n  \"name\": \"Marina de Waard\",\n  \"email\": \"marina@email.com\",\n  \"password\": \"Wachtwoord124\",\n  \"roles\": [\"USER\"]\n}",
"options": { "raw": { "language": "json" } }
},
"url": "http://localhost:8080/users"
}
},
{
"name": "auth | Login Marina",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\n  \"email\": \"marina@email.com\",\n  \"password\": \"Wachtwoord124\"\n}",
"options": { "raw": { "language": "json" } }
},
"url": "http://localhost:8080/auth"
}
},
{
"name": "carts/me/items | Product toevoegen aan eigen cart",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "[\n  {\n    \"sku\": 1825,\n    \"productName\": \"Ray Rose 825 Ballroom\",\n    \"productPrice\": 149.99,\n    \"quantity\": 1\n  }\n]",
"options": { "raw": { "language": "json" } }
},
"url": "http://localhost:8080/carts/me/items"
}
},
{
"name": "orders | Plaats order",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\n  \"userId\": 1,\n  \"totalPrice\": 150.99,\n  \"status\": \"IN_CART\",\n  \"orderItems\": [\n    { \"productId\": 5, \"quantity\": 2, \"price\": 45.99 },\n    { \"productId\": 8, \"quantity\": 1, \"price\": 59.99 }\n  ]\n}",
"options": { "raw": { "language": "json" } }
},
"url": "http://localhost:8080/orders"
}
},
{
"name": "products | Alle actieve producten ophalen",
"request": {
"method": "GET",
"header": [],
"url": "http://localhost:8080/products/active"
}
},
{
"name": "contacts | E-mail sturen",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "[\n  {\n    \"name\": \"Wilma de Kater\",\n    \"email\": \"wilma@email.nl\",\n    \"subject\": \"Vraag over de hakhoogtes\",\n    \"message\": \"Hallo, ik zou graag willen weten welke hakhoogte ik het beste kan bestellen als beginnende danser\"\n  }\n]",
"options": { "raw": { "language": "json" } }
},
"url": "http://localhost:8080/contacts"
}
}
]
}


Image up- en downloaden:

Uploaden:
POST http://localhost:8080/products/1/upload-image
- Ga naar de Body
- Kies voor form-data
- Typ onder Key file en kies recht in de dropdown ook voor file
- Selecteer bij value het bestand en bevestig met de download
- Klik vervolgens op Send

Downloaden:
GET http://localhost:8080/files/download/{image.jpg}
- het kan ook een url zijn {image_url}


## 6. REST-endpoints

Endpoint

POST/auth
Authenticatie
permitAll

POST /users
Maak één of meerdere gebruikers aan
permitAll

GET/users
Haal alle gebruikers op
ADMIN

GET /users/{id}
Haal één gebruiker op basis van ID
ADMIN

DELETE /users/{id}
Verwijder een gebruiker op basis van ID
ADMIN

PUT /users/{id}
Werk een bestaande gebruiker bij
ADMIN

POST/carts
Maak een nieuwe cart voor een gebruiker aan
x

POST /carts/user/{userId}/items
Voeg items toe aan de cart van een specifieke gebruiker
CUSTOMER/ADMIN

POST /carts/me/items
Voeg items toe aan je eigen cart (gebruiker via authenticatie)
CUSTOMER/ADMIN

GET /carts/user/{userId}
Haal de cart op van een specifieke gebruiker
ADMIN

PUT /carts/user/{userId}/items/{orderItemId}
Werk een specifiek orderitem in de cart van een
ADMIN

DELETE /carts/user/{userId}/items/{orderItemId}
Verwijder een specifiek orderitem uit de cart van een gebruiker  
CUSTOMER/ADMIN

DELETE /carts/user/{userId}/clear
Maak de cart van een gebruiker leeg
CUSTOMER/ADMIN

GET /products
Haal alle producten op
permitAll

GET /products/active
Haal alle actieve producten op
permitAll

GET /products/{id}
Haal een product op basis van ID
permitAll

GET /products/search?productName=
Zoek producten op naam
permitAll

GET /products/category/{category}
Haal producten op basis van categorie
permitAll

GET /products/brand/{brand}
Haal producten op basis van merk
permitAll

GET /products/price-range?minPrice=&maxPrice=
Haal producten op binnen een prijsbereik
permitAll

GET /products/dance-shoes?brand=&color=&size=
Haal dansschoenen op met filters
permitAll

GET /products/categories
Haal alle categorieën op
permitAll

GET /products/brands
Haal alle merken op
permitAll

GET /products/sizes
Haal alle maten op
permitAll

GET /products/colors
Haal alle kleuren op
permitAll

POST /products
Maak een nieuw product aan
ADMIN

POST /products/{id}/upload-image
Upload een afbeelding voor een product
x

DELETE /products/{id}/delete-image
Verwijder de afbeelding van een product
x

PUT /products/{id}
Werk een product bij
ADMIN

PUT /products/{id}/stock
Werk de voorraad van een product bij
x

PUT /products/{id}/deactivate
Deactiveer een product
x

DELETE /products/{id}
Verwijder een product
ADMIN

GET /orders
Haal alle bestellingen op
ADMIN

GET /orders/{id}
Haal één bestelling op basis van ID
AUTH

POST /orders
Maak een nieuwe bestelling aan (gebruiker via authenticatie)
AUTH

DELETE /orders/{id}
Verwijder een bestelling op basis van ID
ADMIN

GET /order-items/{id}
Haal één orderitem op basis van ID
ADMIN

GET /order-items/user/{userId}
Haal alle orderitems op voor een specifieke gebruiker
ADMIN

PUT /order-items/user/{userId}/items/{orderItemId}
Werk een specifiek orderitem bij voor een gebruiker
ADMIN

DELETE /order-items/user/{userId}/items/{orderItemId}
Verwijder een specifiek orderitem voor een gebruiker
ADMIN

POST /contacts
Maak één of meerdere contactberichten aan
permitAll

GET /contacts
Haal alle contactberichten op
ADMIN

GET /contacts/unread
Haal alle ongelezen contactberichten op
ADMIN

GET /contacts/search?subject=
Zoek contactberichten op onderwerp
x

PATCH /contacts/{id}/read
Markeer een contactbericht als gelezen
ADMIN

DELETE /contacts/{id}
Verwijder een contactbericht op basis van ID
ADMIN

* De Endpoints met een x erachter zijn niet getest en verwerkt in de postman collectie.



Enum Waarden:

    • Category: FASHION, HEALTH, KITCHEN, TOOLS 
      
    • Brand: PORTDANCE, RAYROSE, DIAMANT, BLOCH, CAPEZIO, DANSCO, SANSHA, WERNERKERN, DANCENATURALS, NUEVAEPOCA 
      
    • Color: BLACK, WHITE, NUDE, PINK, RED, GLITTER, DARKTAN, SILVER, GOLD, BURGUNDY 
      
    • Heel: FLAT, LOW, MEDIUM, HIGH, PLATFORM 
      
    • Size: SIZE_35, SIZE_36, SIZE_37, SIZE_38, SIZE_39, SIZE_40, SIZE_41, SIZE_42, SIZE_43, SIZE_44, SIZE_45 
      
    • OrderAndItemStatus: IN_CART, PENDING, CANCELLED_CART, RETURNED_CART, ORDERED, SHIPPED, DELIVERED, CANCELLED_ORDER, RETURNED_ORDER 



## 7. Overige commando's

Database Commando's:
Database connectie testen
psql -h localhost -U postgres -d toffshop

Maven Commando's:
Applicatie starten
mvn spring-boot:run

Dependencies updaten
mvn dependency:resolve
Maven cache legen
mvn dependency:purge-local-repository

Troubleshooting Commando's:
Poort 8080 controleren
lsof -i :8080
java -version

Maven versie controleren
mvn -version



Ontwikkeld door Mary Leenen als eindopdracht voor NOVI Hogeschool
