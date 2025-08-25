# Installatiehandleiding - Toffshop Backend API
## 1. INLEIDING
Toffshop is een professionele Spring Boot REST API voor een webshop. De applicatie biedt uitgebreide functionaliteiten voor gebruikersbeheer, productcatalogus, winkelwagen beheer en contactformulieren.

Hoofdfunctionaliteiten:
- Gebruikersbeheer - CRUD operaties voor gebruikers; customer en admin met validatie
- Productcatalogus - Uitgebreide productbeheer met specifieke eigenschappen
- Winkelwagen - Volledige shopping cart functionaliteit
- Bestellingen - Order creation met verschillende statussen
- Contact - Contactformulier met email validatie


## 2. BENODIGDHEDEN
Software Vereisten:
-Java 21
-Maven 3.6+ (of gebruik de meegeleverde Maven Wrapper)
-pgAdmin 4 / PostgresQL database server
-IntelliJ IDEA (aanbevolen) of andere Java IDE
-Postman (voor API testing)

Database Vereisten:
PostgreSQL server draaiend op localhost:5432
Database genaamd toffshop
Gebruiker postgres met wachtwoord

## 3. INSTALLATIE INSTRUCTIES (stappenplan)

Stap 1: Repository Clonen
git clone https://github.com/MaryLCrea/eindopdracht-toffshop.v2 
of projectbestand openen vanaf intelliJ -> File -> open -> zoek de betreffende map.

Stap 2: Project Openen in IntelliJ IDEA
Open IntelliJ IDEA
Kies "Open" en selecteer de map met pom.xml
Wacht tot Maven alle dependencies heeft gedownload
Controleer of Java 21 is geselecteerd

Stap 3: Database Configuratie

Installatie pgAdmin 4
-Download en installeer pgAdmin 4 van https://www.pgadmin.org/
    
Maak een nieuwe database aan genaamd toffshop:
Ga met je muis op database staan, rechtermuisklik en kies create database.

Installatie Postman
-Download en installeer Postman https://www.postman.com/downloads/

Application Properties Configureren:
Pas src/main/resources/application.properties aan:

Database configuratie
spring.datasource.url=jdbc:postgresql://localhost:5432/toffshop 
spring.datasource.username=postgres 
spring.datasource.password=jouw_wachtwoord

JPA/Hibernate (standaard) configuratie, aan te passen indien gewenst
spring.jpa.hibernate.ddl-auto=create
spring.sql.init.mode=always
spring.jpa.show-sql=true

Server configuratie; standaard ingesteld op 8080
Wil je een andere server poort voeg dan toe: server.port=8081

Stap 4: Applicatie Starten
Via IntelliJ IDEA: Runnen via de groene pijl rechts bovenin.

Stap 5: Verificatie
Na het starten draait de applicatie op: http://localhost:8080

## 4. TESTGEBRUIKERS
De applicatie start automatisch met de volgende testgebruikers:

ID	Naam 	     Email	     Telefoon	    Wachtwoord
1	Piet Puk	piet@email.com	0610203040	secret123
2	Tessa Pluk	tessa@email.com	0640506070	secret456
3	Anna Jolie	anna@email.com	620304050	secret789

Test Data Overzicht in data.sql
3 gebruikers met verschillende profielen
7 producten (Fashion, Health, Kitchen, Tools)
2 winkelwagens met verschillende items
4 order items met verschillende statussen
2 contact berichten (inclusief email test data)

5. Postman collecties
Basis Configuratie:
Base URL: http://localhost:8080
   
Voorbeelden voor Postman:
   
Gebruiker Aanmaken:
POST http://localhost:8080/users 
[{ "name": "Test User", "email": "test@example.com", "phone": 612345678 }]

Item aan Winkelwagen Toevoegen:
POST http://localhost:8080/carts/user/1/items
[{ "productName": "PortDance PD025 Latin", "productPrice": 129.99, "quantity": 1 }]

Contact Bericht Versturen:
POST http://localhost:8080/contacts
[{ "name": "Test Gebruiker", "email": "test@email.nl", "subject": "Test Onderwerp", "message": "Dit is een test bericht." }]
        
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
      
### Users Endpoints (/users):
GET/users # Alle gebruikers ophalen 
GET/users/{id} # Specifieke gebruiker ophalen 
POST/users # Nieuwe gebruiker aanmaken 
PUT/users/{id} # Gebruiker bijwerken 
DELETE /users/{id} # Gebruiker verwijderen

### Rollen Endpoints (/roles)

GET /roles
→ Haal alle rollen op uit de database.

### Producten Endpoints (/products):

Producten ophalen / zoeken: 
GET /products
→ Alle producten ophalen, inclusief totaal aantal.

GET /products/active
→ Alleen actieve producten ophalen.

GET /products/{id}
→ Eén product ophalen op basis van ID.

GET /products/search?name=...
→ Producten zoeken op naam.

GET /products/category/{category}
→ Alle producten binnen een bepaalde categorie (enum Category).

GET /products/brand/{brand}
→ Alle producten van een merk (enum Brand).

GET /products/price-range?minPrice=...&maxPrice=...
→ Producten binnen een opgegeven prijsrange.

GET /products/dance-shoes?brand=...&color=...&size=...
→ Dansschoenen filteren op merk, kleur en maat.

Enums ophalen:

GET /products/categories
→ Lijst met alle categorieën.

GET /products/brands
→ Lijst met alle merken.

GET /products/sizes
→ Lijst met alle maten.

GET /products/colors
→ Lijst met alle kleuren.

Producten Toevoegen / Bewerken:

POST /products
→ Nieuw product aanmaken met ProductRequestDto.

PUT /products/{id}
→ Product bijwerken.

PUT /products/{id}/stock
→ Alleen de voorraad (stockQuantity) van een product updaten.

PUT /products/{id}/deactivate
→ Een product deactiveren (bijvoorbeeld niet meer verkopen).

Afbeeldingen:

POST /products/{id}/upload-image
→ Afbeelding uploaden en koppelen aan product.

DELETE /products/{id}/delete-image
→ Afbeelding van een product verwijderen.

### Winkelwagen Endpoints (/carts):

Toevoegen aan winkelwagen: 

POST /carts/user/{userId}/items
→ Voeg één of meerdere items toe aan de cart van een gebruiker.

Request body: List<OrderItemRequestDto>

Response: de bijgewerkte CartResponseDto.

Winkelwagen ophalen: 

GET /carts/user/{userId}
→ Haal de cart van een specifieke gebruiker op.

Winkelwagen Bijwerken

PUT /carts/user/{userId}/items/{orderItemId}
→ Pas één orderitem in de cart aan (bijvoorbeeld aantal).

Winkelwagen Leegmaken

DELETE /carts/user/{userId}/items/{orderItemId}
→ Verwijder één orderitem uit de cart van een gebruiker.

DELETE /carts/user/{userId}/clear
→ Leeg de cart van een gebruiker volledig.
Response: HTTP 204 No Content

Extra opmerkingen:
Alle endpoints zijn gebruiker-gebaseerd (/user/{userId}) i.p.v. cart-ID.
Endpoints retourneren altijd de bijgewerkte cart (behalve bij clear, die alleen 204 terugstuurt).

### OrderItems Endpoints (/order-item):

GET /order-items/{id}
→ Haal één orderitem op basis van zijn ID.

GET /order-items/user/{userId}
→ Haal alle orderitems op van een specifieke gebruiker.

GET /order-items/user/{userId}/status/{status}
→ Haal alle orderitems op van een gebruiker met een specifieke status.
Status is van enum OrderItemStatus (bijv. IN_CART, ORDERED, SHIPPED)

GET /order-items/status/{status}
→ Haal alle orderitems op met een specifieke status (voor admin of overzicht).


### E-mail endpoints (/contacts)

E-mail Toevoegen: 

POST /contacts
→ Voeg één of meerdere e-mails toe.
HTTP status: 201 Created

Ophalen

GET /contacts
→ Haal alle e-mails op.

GET /contacts/unread
→ Haal alleen ongelezen e-mails op.

GET /contacts/read
→ Haal alleen gelezen e-mails op.

GET /contacts/unread/count
→ Haal het aantal ongelezen e-mails op.
Response: { "unreadCount": <number> }

GET /contacts/{id}
→ Haal een specifieke e-mail op via ID.

GET /contacts/search?subject=...
→ Zoek e-mails op onderwerp (subject).

E-mails bijwerken

PATCH /contacts/{id}/read
→ Markeer een contact als gelezen.

PATCH /contacts/{id}/unread
→ Markeer een contact als ongelezen.

E-mails verwijderen

DELETE /contacts/{id}
→ Verwijder een e-mail.
Response: HTTP 204 No Content

Opmerkingen: 
POST ondersteunt meerdere contacten tegelijk (batch []).
PATCH endpoints worden gebruikt om o.a. status updates door te voeren.

Enum Waarden:
Category: FASHION, HEALTH, KITCHEN, TOOLS
Brand: PORTDANCE, RAYROSE, DIAMANT, BLOCH, CAPEZIO, DANSCO, SANSHA, WERNERKERN, DANCENATURALS, NUEVAEPOCA
Color: BLACK, WHITE, NUDE, PINK, RED, GLITTER, DARKTAN, SILVER, GOLD, BURGUNDY
Heel: FLAT, LOW, MEDIUM, HIGH, PLATFORM
Size: SIZE_35, SIZE_36, SIZE_37, SIZE_38, SIZE_39, SIZE_40, SIZE_41, SIZE_42, SIZE_43, SIZE_44, SIZE_45
OrderItemStatus: INCART, ORDERED, SHIPPED, DELIVERED, CANCELLED

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