# Square Game API

API REST pour gérer plusieurs jeux de plateau (Tic-Tac-Toe, Connect4, etc.) utilisant le moteur square-games.

## Architecture

Le projet suit une architecture MVC avec les couches suivantes:

### Model (com.example.squaregame.model)
- `Game`: Représente une partie de jeu
- `GameStatus`: États possibles d'une partie (IN_PROGRESS, FINISHED, DRAW)
- `Move`: Représente un coup joué
- `GameInfo`: Informations sur un type de jeu disponible

### DAO (com.example.squaregame.dao)
- `GameDAO` / `GameDAOImpl`: Gestion de la persistance des parties
- `MoveDAO` / `MoveDAOImpl`: Gestion de la persistance des coups
- Implémentation en mémoire avec ConcurrentHashMap

### Service (com.example.squaregame.service)
- `GameService`: Logique métier principale
- `GameCatalog`: Catalogue des jeux disponibles
- `GameFactory`: Interface pour les factories de jeu
- `TicTacToeFactory`: Factory pour Tic-Tac-Toe
- `Connect4Factory`: Factory pour Connect4

### Controller (com.example.squaregame.controller)
- `GameController`: Endpoints REST pour gérer les parties

### DTO (com.example.squaregame.dto)
- `CreateGameRequest`: Requête de création de partie
- `MakeMoveRequest`: Requête pour jouer un coup
- `GameResponse`: Réponse contenant les infos d'une partie
- `BoardResponse`: Réponse contenant l'état du plateau

## Endpoints API

### Récupérer les types de jeux disponibles
```
GET /api/games/types
```

### Créer une nouvelle partie
```
POST /api/games
Content-Type: application/json

{
  "gameType": "tictactoe",
  "boardSize": "3x3",
  "playerOneName": "Alice",
  "playerTwoName": "Bob"
}
```

### Récupérer toutes les parties
```
GET /api/games
```

### Récupérer une partie spécifique
```
GET /api/games/{gameId}
```

### Jouer un coup
```
POST /api/games/{gameId}/moves
Content-Type: application/json

{
  "x": 0,
  "y": 0,
  "playerToken": "X"
}
```

### Récupérer l'état du plateau
```
GET /api/games/{gameId}/board
```

### Récupérer l'historique des coups
```
GET /api/games/{gameId}/moves
```

### Supprimer une partie
```
DELETE /api/games/{gameId}
```

## Jeux disponibles

### Tic-Tac-Toe
- Type: `tictactoe`
- Taille du plateau: `3x3`
- Joueurs: 2

### Connect4
- Type: `connect4`
- Taille du plateau: `7x6`
- Joueurs: 2

## Configuration requise

- Java 21
- Spring Boot 3.5.7
- Maven
- Accès au repository GitHub pour le moteur square-games

## Dépendances

- Spring Boot Starter Web
- Spring Boot Starter Test
- Square Games Engine (fr.le-campus-numerique.square-games)
- Mockito (pour les tests)

## Lancer l'application

```bash
cd squareGame
./mvnw spring-boot:run
```

L'application sera disponible sur `http://localhost:8080`

## Tester l'API

Utilisez le fichier `testGames.http` à la racine du projet pour tester les différents endpoints.
