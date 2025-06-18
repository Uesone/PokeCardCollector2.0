# 🛠️ Pokedecks – Backend

This is the **backend API** for [Pokedecks](https://github.com/Uesone/pokecardcollectorfront), a pixel art web app for Pokémon TCG collectors.

---

## 🚀 Features

- RESTful API for managing cards, user collections, and Pokédex data
- Search for cards by name or attributes
- Add/remove cards from your collection
- Computes the total value of your collection
- JWT-based authentication
- Integration with the Pokémon TCG API
- Built for easy integration with the pixel-art frontend

---

## 🧰 Built With

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Spring Security (JWT)
- [Pokémon TCG API](https://pokemontcg.io/)

---

## 📦 Getting Started

Clone the repository and run:

```bash
git clone https://github.com/Uesone/PokeCardCollector2.0.git

cd PokeCardCollector2.0
./mvnw spring-boot:run

or, if using Maven installed:
mvn spring-boot:run

⚙️ Configuration

This project uses environment variables for configuration.

Create a .env file in the root directory (do not commit this file) with the following structure (circa):

# Server
SERVER_PORT=3001

# Database
PG_USERNAME=your_pg_username
PG_PW=your_pg_password
PG_URL=jdbc:postgresql://localhost:5432/PokeCardCollector

# JWT
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000

# Pokémon TCG API
APICARD_URL=https://api.pokemontcg.io/v2
APICARD_KEY=your_pokemontcg_api_key

⚠️ Never commit your .env file! Add .env to your .gitignore.

For reference, you can provide a safe .env.example file (without real secrets) in the repo.

📁 Folder Structure

PokeCardCollector2.0/
│
├── src/
│   ├── main/
│   │   ├── java/com/uesone/pokedecks/
│   │   └── resources/
│   │        └── application.properties  (for fallback/defaults)
├── .env           (not committed)
├── pom.xml
└── ...

```
📄 License  
This project is open source.
---
Made with ☕ by Umberto Amoroso
