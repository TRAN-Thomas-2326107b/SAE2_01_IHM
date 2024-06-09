package iut.jeu_echec.Jeu;

import javafx.util.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

/**
 * Classe qui gère les joueurs d'échecs.
 */
public class GameManager {

    /**
     * Classe statique représentant un joueur.
     */
    public static class Player implements Serializable {
        private String name;
        private int id;
        private int gamesPlayed;
        private int gamesWon;

        /**
         * Constructeur de la classe Player.
         *
         * @param name Le nom du joueur.
         * @param id L'identifiant du joueur.
         */
        public Player(String name, int id) {
            this.name = name;
            this.id = id;
            this.gamesPlayed = 0;
            this.gamesWon = 0;
        }

        /**
         * Méthode incrémentant le nombre de parties jouées par le joueur.
         */
        public void incrementGamesPlayed() {
            this.gamesPlayed++;
        }

        /**
         * Méthode incrémentant le nombre de parties gagnées par le joueur.
         */
        public void incrementGamesWon() {
            this.gamesWon++;
        }

        /**
         * Méthode retournant le nom du joueur.
         *
         * @return Le nom du joueur.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Méthode retournant le nombre de parties jouées
         *
         * @return Le nombre de parties jouées
         */
        public int getGamesPlayed() {
            return gamesPlayed;
        }

        /**
         * Méthode retournant le nombre de parties gagnées.
         *
         * @return Le nombre de parties gagnées.
         */
        public int getGamesWon() {
            return gamesWon;
        }

        /**
         * Méthode modifiant le nom d'un joueur.
         *
         * @param name Le nouveau nom du joueur
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Méthode retournant la représentation d'un joueur par une chaîne de
         * caractères.
         *
         * @return Une chaîne de caractères représentant les informations
         * d'un joueur.
         */
        @Override
        public String toString() {
            return "Player{" +
                    "name='" + name + '\'' +
                    ", gamesPlayed=" + gamesPlayed +
                    ", gamesWon=" + gamesWon +
                    '}';
        }

        /**
         * Méthode vérifiant si le joueur est un bot ou non.
         *
         * @return true si le joueur est un bot, false si il ne l'est pas.
         */
        public boolean isBot() {
            return id == 0;
        }
    }

    /**
     * Classe qui va représenter une partie d'échec
     */
    public static class Game implements Serializable {
        private List<Player> players;
        // mouvements joué
        private List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> moves;
        // Gagnant de la partie
        private Player winner;
        private int gameId;
        private int timer;
        private byte winnerTeam;

        /**
         * Constructeur de Game
         * @param players Les joueurs uniques de la game
         * @param gameId Id unique de la game
         */
        public Game(List<Player> players, int gameId) {
            this.players = players;
            this.moves = null;
            this.winner = null;
            this.gameId = gameId;
        }

        /**
         * Définit les déplacements
         * @param moves C'est une liste des déplacements réalisés
         */
        public void setMoves(List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> moves) {
            this.moves = moves;
        }

        /**
         * Définit le vainqueur de la partie
         * @param winner Le vainqueur
         */
        public void setWinner(Player winner) {
            this.winner = winner;
        }

        /**
         * Obtient la liste des déplacements éffectués pendant la game
         * @return
         */
        public List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> getMoves() {
            return moves;
        }

        /**
         * Obtient le vainqueur
         * @return Le vainqueur
         */
        public Player getWinner() {
            return winner;
        }

        /**
         * Obtient la liste des participants
         * @return La liste des joueurs participants
         */
        public List<Player> getPlayers() {
            return players;
        }

        /**
         * Obtient le temps écoulé du timer
         * @return Temps écoulé
         */
        public int getTimer() {
            return timer;
        }

        /**
         * Définit le temps écoulé
         * @param timer Temps écoulé
         */
        public void setTimer(int timer) {
            this.timer = timer;
        }

        /**
         * Définit l'équipe du winner
         * @param winnerTeam
         */
        public void setWinnerTeam(byte winnerTeam) {
            this.winnerTeam = winnerTeam;
        }

    }

    /**
     * Nom du fichier comprenant les informations des joueurs.
     */
    private static final String PLAYERS_FILE = "players.data";

    /**
     * Nom du fichier comprenant les informations des parties.
     */
    private static final String GAMES_FILE = "games.data";

    /**
     * Instance unique de la classe PlayerManager
     */
    private static GameManager instance = null;

    /**
     * Liste des joueurs
     */
    private static Map<Integer, Player> players;

    /**
     * Liste des parties
     */
    private static Map<Integer, Game> games;

    /**
     * Constructeur privé du singleton.
     */
    private GameManager() {
    }

    /**
     * Méthode obtenant l'instance de PlayerManager.
     *
     * @return L'instance de PlayerManager.
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
            players = new HashMap<>();
            games = new HashMap<>();

            Player bot = new Player("Bot", 0);
            players.put(0, bot);

            // Charge les joueurs depuis le fichier
            try {
                instance.loadPlayers();
            } catch (Exception e) {
                // Le fichier n'existe pas encore, alors on le crée
            }

            // Charge les parties depuis le fichier
            try {
                instance.loadGames();
            } catch (Exception e) {
                // Le fichier n'existe pas encore, alors on le crée
            }
        }
        return instance;
    }

    /**
     * Méthode ajoutant un joueur avec un nom et un identifiant.
     *
     * @param name Le nom du nouveau joueur.
     * @return L'identifiant du nouveau joueur.
     */
    public int addPlayer(String name) {
        Random rand = new Random();
        int id = rand.nextInt(1337);

        if (players.containsKey(id)) {
            // Génère un nouvel identifiant en cas de collision
            return addPlayer(name);
        }

        Player newPlayer = new Player(name, id);
        players.put(id, newPlayer);

        return id;
    }

    /**
     * Méthode ajoutant une partie.
     *
     * @param players Les joueurs participant à la partie.
     * @return L'identifiant de la nouvelle partie.
     */
    public int addGame(List<Player> players) {
        Random rand = new Random();
        int gameId = rand.nextInt(999999);

        if (games.containsKey(gameId)) {
            // Génère un nouvel identifiant en cas de collision
            return addGame(players);
        }

        Game newGame = new Game(players, gameId);
        games.put(gameId, newGame);

        return gameId;
    }

    /**
     * Méthode obtenant la liste des joueurs.
     *
     * @return Une map de joueurs avec leurs identifiants comme clé.
     */
    public Map<Integer, Player> getPlayers() {
        return players;
    }

    /**
     * Méthode obtenant la liste des parties.
     *
     * @return Une map des parties avec leurs identifiants comme clé.
     */
    public Map<Integer, Game> getGames() {
        return games;
    }

    /**
     * Supprime un joueur grâce à son nom.
     *
     * @param name Le nom du joueur que l'on veut supprimer.
     */
    public void removePlayer(String name) {
        // Crée une copie de la clé pour éviter ConcurrentModificationException
        players.keySet().removeIf(key -> players.get(key).getName().equals(name));
    }

    /**
     * Méthode sauvegardant les joueurs dans un fichier.
     */
    public void savePlayers() {
        // Vérifie si des modifications ont été faites
        try (FileOutputStream fileOut = new FileOutputStream(PLAYERS_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(players);
            System.out.println("Serialized data is saved in " + PLAYERS_FILE);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Méthode sauvegardant les parties dans un fichier.
     */
    public void saveGames() {
        // Vérifie si des modifications ont été faites
        try (FileOutputStream fileOut = new FileOutputStream(GAMES_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(games);
            System.out.println("Serialized data is saved in " + GAMES_FILE);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Charge les joueurs depuis un fichier.
     */
    public void loadPlayers() {
        try (FileInputStream fileIn = new FileInputStream(PLAYERS_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            players = (Map<Integer, Player>) in.readObject();
            System.out.println("Players loaded from " + PLAYERS_FILE);
        } catch (IOException | ClassNotFoundException i) {
        }
    }

    /**
     * Charge les parties depuis un fichier.
     */
    public void loadGames() {
        try (FileInputStream fileIn = new FileInputStream(GAMES_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            games = (Map<Integer, Game>) in.readObject();
            System.out.println("Games loaded from " + GAMES_FILE);
        } catch (IOException | ClassNotFoundException i) {
        }
    }
}
