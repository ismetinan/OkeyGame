import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }
    //aybüke
    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        shuffleTiles();
        Tile[] restOfTiles = new Tile[55];
       currentPlayerIndex = 0;
        for(int i=0; i<112; i++){
            int j = 0;
            if(i<15){
            players[currentPlayerIndex].addTile(tiles[i]);
            if(i==14){
                currentPlayerIndex++;
            }
            }
            else if(i>=15&&i<29){
            players[currentPlayerIndex].addTile(tiles[i]);
            if(i==28){
                currentPlayerIndex++;
            }
            }
            else if(i>=29&&i<43){
            players[currentPlayerIndex].addTile(tiles[i]);
            if(i==42){
                currentPlayerIndex++;
            }
            }
            else if(i>=43&&i<57){
            players[currentPlayerIndex].addTile(tiles[i]);
            }
            else{
               restOfTiles[j]=tiles[i];
               j++;
               currentPlayerIndex = 0;
            }
        }
            

    }
    //ismet
    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        players[currentPlayerIndex].addTile(lastDiscardedTile);
        return lastDiscardedTile.toString();
    }
    //ismet
    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */

     //DESTEDEKİ TİLELERI DENETLEMEK İÇİN EN ÜSTTEKİ ALINDIKTAN SONRA DİĞERLERİNİ BİR SIRA KAYDIRIP YENİ BİR ARRAYE ATABİLİRİZ.
     //AYBÜKE SEN DE DİSTRİBUTE FONKSİYONUNDA BENİMKİ GİBİ BİR MANTIK KULLAN YOKSA BENİMKİ ÇALIŞMAZ

    public String getTopTile() {
        if (tiles.length == 0) {
            return "No tiles left";
        }

        Tile toBeReturned = tiles[0];
        Tile[] newTiles = new Tile[tiles.length - 1];

        for (int i = 0; i < tiles.length - 1; i++) {
            tiles[i] = tiles[i+1];
            newTiles[i] = tiles[i + 1];
        }

        this.tiles = newTiles;
        players[currentPlayerIndex].addTile(toBeReturned);
        return toBeReturned.toString();
    }
    //aybüke
    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        createTiles();
        Tile[] tempTiles = new Tile[112];
        Random rand = new Random();
        ArrayList<Integer> places = new ArrayList<Integer>();

        while(places.size()<112){
            int temp = rand.nextInt(0,112);
            if(!places.contains(temp)){
                places.add(temp);     
            }
        }

        for(int i=0; i<112; i++){
            int place = places.get(i);
            tempTiles[i]=tiles[place];
        }

        tiles=tempTiles;

    }
    //eren
    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
       if (players[currentPlayerIndex].isWinningHand()){
        return true;
       }
        return false;
    }
    //eren
    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {
        boolean useful = false;
        Tile[] hand = new Tile[players[currentPlayerIndex].getTiles().length-1];
        hand=Arrays.copyOf(players[currentPlayerIndex].getTiles(),players[currentPlayerIndex].getTiles().length-1);        
        for(int i=0;i<players[currentPlayerIndex].getTiles().length-1&&!useful;i++){
            
            if(hand[i].canFormChainWith(lastDiscardedTile)){
                useful = true;
            }
        }
        if(useful){
            getLastDiscardedTile();
        }
        else{
            getTopTile();
        }
        System.out.print(Arrays.toString(players[currentPlayerIndex].getTiles()));

    }
   //emre
    /*
     * TODO: Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
    public void discardTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];
        Tile[] playerTiles = currentPlayer.getTiles();
        boolean discarded = false;
    
        for (int i = 0; i < playerTiles.length && !discarded; i++) {
            for (int j = i + 1; j < playerTiles.length; j++) {
                if (playerTiles[i] != null && playerTiles[i].compareTo(playerTiles[j])==0) {
                    discardTile(i);
                    discarded = true;
                    j =  playerTiles.length;
                }
            }
        }
    
        if (!discarded) {
            for (int i = 0; i < playerTiles.length; i++) {
                if (playerTiles[i] != null) {
                    discardTile(i);
                    i = playerTiles.length;
                }
            }
        }
        System.out.print(Arrays.toString(players[currentPlayerIndex].getTiles()));

   
    }
    //emre
    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
       
            Player currentPlayer = players[currentPlayerIndex];
            Tile[] playerTiles = currentPlayer.getTiles();  
        

            lastDiscardedTile = playerTiles[tileIndex];
            players[currentPlayerIndex].getAndRemoveTile(tileIndex);
           

            
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }
}
