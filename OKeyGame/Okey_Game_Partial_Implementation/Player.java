public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {
        if(index==0||index>numberOfTiles){
            return null;
        }
        Tile removedTile=playerTiles[index];
            for(int i=index;i<numberOfTiles;i++){
                playerTiles[i]=playerTiles[i+1];

            }
            playerTiles[numberOfTiles-1]=null;
            numberOfTiles--;
            return removedTile;

    }

    /*
     * TODO: adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {

        if(numberOfTiles<15){
            int currentIndex = 0;
        
            while (currentIndex < numberOfTiles && playerTiles[currentIndex].compareTo(t) < 0) {
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

        for (int i = 1; i < numberOfTiles; i++) {

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
        for (int i = 0; i < numberOfTiles; i++) {
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
}
