import java.util.Arrays;
import java.util.Comparator;

public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }
    
//EGE player sınıfı
    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {
        Tile[] newPlayerTiles = new Tile[14];
        if (index < 0 || index > numberOfTiles) {
            return null;
        }
        Tile removedTile = playerTiles[index];
        for (int i = index; i < numberOfTiles - 1; i++) {
            playerTiles[i] = playerTiles[i + 1];

        }
        newPlayerTiles = Arrays.copyOf(playerTiles, 14);
        numberOfTiles--;
        this.playerTiles = newPlayerTiles;
        return removedTile;

    }

    
    public void addTile(Tile t) {
        if (numberOfTiles >= playerTiles.length) {
            if (numberOfTiles >= 15) {
                System.out.println("Cannot add more tiles. Maximum limit reached.");
                return;
            }
            Tile[] newPlayerTiles = new Tile[playerTiles.length + 1];
            System.arraycopy(playerTiles, 0, newPlayerTiles, 0, playerTiles.length);
            playerTiles = newPlayerTiles;
        }
        
        playerTiles[numberOfTiles] = t;
        numberOfTiles++;
        
        // Sort the hand after adding the new tile
        sortHand();
    }

    public void sortHand() {
        Arrays.sort(playerTiles, 0, numberOfTiles, new Comparator<Tile>() {
            @Override
            public int compare(Tile t1, Tile t2) {
                if (t1 == null && t2 == null) return 0;
                if (t1 == null) return 1;
                if (t2 == null) return -1;
                return t1.compareTo(t2);
            }
        });
    }

    public void preAddTile(Tile t) {
        
        if(numberOfTiles<15){
            int currentIndex = 0;
        
            while (currentIndex < numberOfTiles && playerTiles[currentIndex].compareTo(t) > 0) {
            currentIndex++;
            }
                for (int i = numberOfTiles; i > currentIndex; i--) {
                    playerTiles[i] = playerTiles[i - 1];
                }
                playerTiles[currentIndex]=t;

                numberOfTiles++;
               
        }
    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand() {
        
        if (numberOfTiles < 12) {
            return false;
        }

        int chainCount = 0;
        int currentChainLength = 1;

        for (int i = 1; i < numberOfTiles-2; i++) {

            if (playerTiles[i].canFormChainWith(playerTiles[i - 1])) {
                currentChainLength++;

                if (currentChainLength == 4) {
                    chainCount++;
                    currentChainLength = 0;
                }
            } else {
                currentChainLength = 1;
            }
        }
        
        if(chainCount>=3){
            return true;
        }
        else{
            return false;
        }
        
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles-2; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
<<<<<<< HEAD
}
=======
    public void sortHand(Tile[] t) {

        
    }
}
>>>>>>> origin/main
