package iut.jeu_echec;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerManager {
    public static class Player implements Serializable {


        private String name;
        private int id;
        private int gamesPlayed;
        private int gamesWon;



        public Player(String name, int id) {
            this.name = name;
            this.id = id;
            this.gamesPlayed = 0;
            this.gamesWon = 0;
        }

        public void incrementGamesPlayed() {
            this.gamesPlayed++;
        }

        public void incrementGamesWon() {
            this.gamesWon++;
        }

        public String getName() {
            return this.name;
        }

        public int getGamesPlayed() {
            return gamesPlayed;
        }
        public int getGamesWon() {
            return gamesWon;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "name='" + name + '\'' +
                    ", gamesPlayed=" + gamesPlayed +
                    ", gamesWon=" + gamesWon +
                    '}';
        }

        public boolean isBot() {
            return id == 0;
        }
    }

    private static final String PLAYERS_FILE = "players.data";
    private static final String GAMES_FILE = "games.ser";
    private static PlayerManager instance = null;

    // list of players maybe but without loading the games data at first
    private static Map<Integer, Player> players;

    private PlayerManager() {
    }

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
            players = new HashMap<>();

            Player bot = new Player("Bot", 0);
            players.put(0, bot);

            // load players from file
            try {
                instance.loadPlayers();
            } catch (Exception e) {
                // original doesn't exist so we create it
            }
        }
        return instance;
    }

    public int addPlayer(String name) {
        Random rand = new Random();
        int id = rand.nextInt(1337);

        if (players.containsKey(id)) {
            // Retry generating ID if collision occurs
            return addPlayer(name);
        }

        Player newPlayer = new Player(name, id);
        players.put(id, newPlayer);

        return id;
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public void removePlayer(String name) {
        // Create a copy of keys to avoid ConcurrentModificationException
        players.keySet().removeIf(key -> players.get(key).getName().equals(name));
    }

    public void savePlayers() {
        // check if any change has been made




        try (FileOutputStream fileOut = new FileOutputStream(PLAYERS_FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(players);
            System.out.println("Serialized data is saved in " + PLAYERS_FILE);
        } catch (IOException i) {
        }
    }

    public void loadPlayers() {
        try (FileInputStream fileIn = new FileInputStream(PLAYERS_FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            players = (Map<Integer, Player>) in.readObject();
            System.out.println("Players loaded from " + PLAYERS_FILE);
        } catch (IOException | ClassNotFoundException i) {
        }
    }
}
