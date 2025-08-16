# Installatiehandleiding - Toffshop Backend API

## 1. INLEIDING

Toffshop is een professionele Spring Boot REST API voor een webshop. De applicatie biedt uitgebreide functionaliteiten voor gebruikersbeheer, productcatalogus, winkelwagen beheer en contactformulieren.

### Hoofdfunctionaliteiten:
- **Gebruikersbeheer** - CRUD operaties voor gebruikers met validatie
- **Productcatalogus** - Uitgebreide productbeheer met specifieke eigenschappen
- **Winkelwagen** - Volledige shopping cart functionaliteit
- **Bestellingen** - Order management met verschillende statussen
- **Contact** - Contactformulier met email validatie

## 2. BENODIGDHEDEN

### Software Vereisten:
- **Java 21** 
- **Maven 3.6+** (of gebruik de meegeleverde Maven Wrapper)
- **pgAdmin 4 / PostgresQL** database server
- **IntelliJ IDEA** (aanbevolen) of andere Java IDE
- **Postman** (voor API testing)

### Database Vereisten:
- PostgreSQL server draaiend op `localhost:5432`
- Database genaamd `toffshop`
- Gebruiker `postgres` met wachtwoord

## 3. INSTALLATIE INSTRUCTIES (stappenplan)

### Stap 1: Repository Clonen
git clone https://github.com/jouw-gebruiker/eindopdracht-toffshop.git
of projectbestand openen vanaf intelliJ -> File -> open -> zoek de betreffende map.

### Stap 2: Project Openen in IntelliJ IDEA
1. Open IntelliJ IDEA
2. Kies "Open" en selecteer de map met `pom.xml`
3. Wacht tot Maven alle dependencies heeft gedownload
4. Controleer of Java 21 is geselecteerd

### Stap 3: Database Configuratie

#### Installatie pgAdmin 4
-Download en installeer pgAdmin 4 van https://www.pgadmin.org/ 

#### Installatie Postman
-Download en installeer Postman https://www.postman.com/downloads/ 

#### Application Properties Configureren:
Pas `src/main/resources/application.properties` aan:

# Database configuratie
spring.datasource.url=jdbc:postgresql://localhost:5432/toffshop
spring.datasource.username=postgres
spring.datasource.password=jouw_wachtwoord

# JPA/Hibernate configuratie
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

# Server configuratie
server.port=8080

### Stap 4: Applicatie Starten

**Via IntelliJ IDEA:**
Runnen via de groene pijl rechts bovenin.

### Stap 5: Verificatie
Na het starten draait de applicatie op: `http://localhost:8080`

## 4. TESTGEBRUIKERS

De applicatie start automatisch met de volgende testgebruikers:

| ID | Naam | Email | Telefoon | Wachtwoord |
|----|------|-------|----------|------------|
| 1 | Piet Puk | piet@email.com | 0610203040 | secret123 |
| 2 | Tessa Pluk | tessa@email.com | 0640506070 | secret456 |
| 3 | Anna Jolie | anna@email.com | 620304050 | secret789 |

### Test Data Overzicht:
- **3 gebruikers** met verschillende profielen
- **7 producten** (Fashion, Health, Kitchen, Tools)
- **2 winkelwagens** met verschillende items
- **4 order items** met verschillende statussen
- **2 contact berichten** (inclusief email test data)

## 5. Postman collecties

### Basis Configuratie:
- **Base URL:** `http://localhost:8080`

### Voorbeelden voor Postman:

#### Gebruiker Aanmaken:
POST http://localhost:8080/users
{
    "name": "Test User",
    "email": "test@example.com",
    "phone": 612345678,
    "password": "testpassword"
}

#### Item aan Winkelwagen Toevoegen:
POST http://localhost:8080/carts/user/1/items

{
    "productName": "PortDance PD025 Latin",
    "productPrice": 129.99,
    "quantity": 1
}

#### Contact Bericht Versturen:
POST http://localhost:8080/contacts

{
    "name": "Test Gebruiker",
    "email": "test@email.nl",
    "subject": "Test Onderwerp",
    "message": "Dit is een test bericht."
}

## 6. REST-endpoints

### Gebruikers Endpoints:

GET/users           # Alle gebruikers ophalen
GET/users/{id}      # Specifieke gebruiker ophalen
POST/users           # Nieuwe gebruiker aanmaken
PUT/users/{id}      # Gebruiker bijwerken
DELETE /users/{id}      # Gebruiker verwijderen

###  Producten Endpoints:

GET    /products                                     # Alle producten
GET    /products/active                              # Alleen actieve producten
GET    /products/{id}                                # Product op ID
GET    /products/category/{cat}                      # Producten per categorie
GET    /products/brand/{brand}                       # Producten per merk
GET    /products/search?name=...                     # Zoeken op naam
GET    /products/price-range?minPrice=...&maxPrice=...  # Prijs filter
POST   /products                                     # Nieuw product
PUT    /products/{id}                                # Product bijwerken
PATCH  /products/{id}/stock                          # Voorraad bijwerken
DELETE /products/{id}                                # Product verwijderen


###  Winkelwagen Endpoints:

GET    /carts/user/{userId}                    # Winkelwagen ophalen
POST   /carts/user/{userId}/items              # Item toevoegen
PUT    /carts/user/{userId}/items/{itemId}     # Item quantity wijzigen
DELETE /carts/user/{userId}/items/{itemId}     # Item verwijderen
DELETE /carts/user/{userId}/clear              # Winkelwagen legen


###  Order Items Endpoints:

GET/orderitems/{id}                        # Order item ophalen
GET/orderitems/user/{userId}               # Alle items van gebruiker
GET/orderitems/user/{userId}/status/{status} # Items per status
GET/orderitems/status/{status}             # Alle items per status
GET/orderitems/order/{orderId}             # Items per order
PUT/orderitems/{id}/status                 # Status wijzigen


###  Contact Endpoints:

GET    /contacts                          # Alle berichten
GET    /contacts/unread                   # Ongelezen berichten
GET    /contacts/read                     # Gelezen berichten
GET    /contacts/unread/count             # Aantal ongelezen berichten
GET    /contacts/{id}                     # Specifiek bericht
GET    /contacts/search?subject=...       # Zoeken op onderwerp
POST   /contacts                          # Nieuw bericht
PATCH  /contacts/{id}/read                # Markeer als gelezen
PATCH  /contacts/{id}/unread              # Markeer als ongelezen
DELETE /contacts/{id}                     # Bericht verwijderen

### Enum Waarden:
- **Category:** FASHION, HEALTH, KITCHEN, TOOLS
- **Brand:** PORTDANCE, RAYROSE, DIAMANT, BLOCH, CAPEZIO, DANSCO, SANSHA, WERNERKERN, DANCENATURALS, NUEVAEPOCA
- **Color:** BLACK, WHITE, NUDE, PINK, RED, GLITTER, DARKTAN, SILVER, GOLD, BURGUNDY
- **Heel:** NONE, XLOW, MEDIUM, HIGH, XLHIGH, PLATFORM
- **Size:** SIZE_35, SIZE_36, SIZE_37, SIZE_38, SIZE_39, SIZE_40, SIZE_41, SIZE_42, SIZE_43, SIZE_44, SIZE_45
- **OrderItemStatus:** INCART, ORDERED, SHIPPED, DELIVERED, CANCELLED

## 7. Overige commando's

### Database Commando's:

# Database connectie testen
psql -h localhost -U postgres -d toffshop


### Maven Commando's:

# Applicatie starten
mvn spring-boot:run

# Dependencies updaten
mvn dependency:resolve

# Maven cache legen
mvn dependency:purge-local-repository


### Troubleshooting Commando's:

# Poort 8081 controleren
lsof -i :8080

java -version

# Maven versie controleren
mvn -version


**Ontwikkeld door Mary Leenen als eindopdracht voor NOVI Hogeschool** 