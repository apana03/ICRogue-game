package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.ICrogue;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class Level extends AreaGame implements Logic{

    private ICrogueRoom map[][];
    private DiscreteCoordinates startCoordinates;

    private int[] roomsDistribution;

    boolean randomMap;

    private boolean isCompletedOnce = false;

    private String title;
    private DiscreteCoordinates bossPosition = new DiscreteCoordinates(0,0);
    private Random random = new Random();

    protected enum MapState {
        NULL , // Empty space
        PLACED , // The room has been placed but not yet
        //explored by the room placement algorithm
        EXPLORED , // The room has been placed and
        //explored by the algorithm
        BOSS_ROOM , // The room is a boss room
        CREATED; // The room has been instantiated in
        //the room map
        @Override
        public String toString() {
            return Integer.toString(ordinal());
        }
    }

    public Level(boolean randomMap , DiscreteCoordinates startPosition , int[] roomsDistribution , int width , int height){
        this.startCoordinates = startPosition;
        this.randomMap = randomMap;
        map = new ICrogueRoom[width][height];
        this.roomsDistribution = roomsDistribution;
    }

    // Generates the map for this level
    public void generateMap(){
        if (!randomMap){
            // If the map is not supposed to be random, generate a fixed map
            generateFixedMap();
        }
        else{
            // If the map is supposed to be random, generate a random map
            generateRandomMap(roomsDistribution);
        }
    }

    // Returns the starting coordinates for the player in this level
    public DiscreteCoordinates getStartCoordinates() {
        return startCoordinates;
    }
    // Sets the room at the specified coordinates in the map
    protected void setRoom(DiscreteCoordinates coords, ICrogueRoom room){
        map[coords.x][coords.y] = room;
    }
    // Sets the destination of the specified connector in the room at the specified coordinates
    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setDestination(destination);
    }
    // Sets the destination and state of the specified connector in the room at the specified coordinates
    protected void setRoomConnector(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setDestination(destination);
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setState(Connector.ConnectorStates.CLOSED);
    }
    // Self Explanatory
    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector, int keyId){
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setNeededKeyId(keyId);
        map[coords.x][coords.y].getConnectors().get(connector.getIndex()).setState(Connector.ConnectorStates.LOCKED);
    }

    protected void setTitle(DiscreteCoordinates coordinates) {
        this.title = map[coordinates.x][coordinates.y].getTitle();
    }

    public String getTitle() {
        return title;
    }
    
    // Adds the rooms in this level to the game
    public void addAreas(ICrogue icrogue){
        for(int i=0;i<map.length; i++){
            for(int j=0; j<map[i].length; j++){
                if (map[i][j] != null){
                    icrogue.addArea(map[i][j]);
                }
            }
        }
    }
    // Generates a fixed map for this level (to be implemented in the subclass)
    public void generateFixedMap(){};


    public void generateRandomMap(int[] roomsDistribution) {

        int roomNMBR = 0;
        for (int x = 0; x < roomsDistribution.length; x++) {
            roomNMBR = roomNMBR + roomsDistribution[x];
        }

        MapState[][] roomsPlacement = generateRandomRoomPlacement(roomNMBR);

        //printMap(roomsPlacement);

        // For each room type
        for(int i = 0; i < roomsDistribution.length; i++){

            //Get room type
            int k = roomsDistribution[i];

            //Get coordinates of all available locations of that type
            List<DiscreteCoordinates> usableLocations = getUsableLocations(roomsPlacement);

            //Chose <k> elements from list of all the indexes possible
            List<Integer> choices = RandomHelper.chooseKInList(k, getNumbersToK(usableLocations.size()-1));

            //create a list with the coordinates of chosen locations
            ArrayList<DiscreteCoordinates> positions = new ArrayList<>();
            for (Integer choice : choices){
                DiscreteCoordinates coords = usableLocations.get(choice);
                positions.add(coords);
            }

            // Create a room of type i at each of the usable locations
            for (DiscreteCoordinates coordinates : positions){
                ICrogueRoom room = createRoom(coordinates, i);
                setRoom(coordinates, room);
                setUpConnector(roomsPlacement, room);
                roomsPlacement[coordinates.x][coordinates.y] = MapState.CREATED;
            }
        }

        // Creates the boss room and set up its connectors
        for(int i=0; i<roomsPlacement.length ;i++){
            for(int j=0; j<roomsPlacement.length;j++){
                if(roomsPlacement[i][j]==MapState.BOSS_ROOM){
                    ICrogueRoom bossRoom =createBossRoom(new DiscreteCoordinates(i,j));
                    setRoom(new DiscreteCoordinates(i,j), bossRoom);
                    setUpConnector(roomsPlacement,bossRoom);
                    return;
                }
            }
        }
    }

    // Function to get all numbers from 0 to k (inclusive)
    private List<Integer> getNumbersToK(int k){
        List<Integer> nums = new ArrayList<>();

        for (int i = 0; i <= k; i++){
            nums.add(i);
        }

        return nums;
    }

    // Gets all Coordinates that are either PLACED or EXPLORED
    private ArrayList<DiscreteCoordinates> getUsableLocations(MapState[][] roomsPlacement) {
        ArrayList<DiscreteCoordinates> usableLocations = new ArrayList<>();
        int width = roomsPlacement.length;
        int height = roomsPlacement[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (roomsPlacement[i][j] == MapState.PLACED || roomsPlacement[i][j] == MapState.EXPLORED) {
                    usableLocations.add(new DiscreteCoordinates(i, j));
                }
            }
        }
        return usableLocations;
    }


    // Coded in the subclasses
    protected abstract void setUpConnector(MapState[][] roomsPlacement, ICrogueRoom room);
    protected abstract ICrogueRoom createRoom(DiscreteCoordinates discreteCoordinates, int i);
    protected abstract ICrogueRoom createBossRoom(DiscreteCoordinates discreteCoordinates);

    protected MapState[][] generateRandomRoomPlacement(int nbRooms){
        //Initialise everything to null
        MapState mapstate[][] = new MapState[map.length][map[0].length];
        for (int x = 0; x < mapstate.length; x++){
            for (int y = 0; y < mapstate[x].length; y++){
                mapstate[x][y] = MapState.NULL;
            }
        }

        //Place spawn at the center room
        mapstate[(int)map.length/2][(int)map[0].length/2] = MapState.PLACED;
        int roomsToPlace = nbRooms-1;
        while (roomsToPlace > 0){
            ArrayList<DiscreteCoordinates> placedRooms = new ArrayList<>();

            //Get placed rooms
            for (int x = 0; x < mapstate.length; x++){
                for (int y = 0; y < mapstate[x].length; y++){
                    if ((mapstate[x][y] == MapState.PLACED) || (mapstate[x][y] == MapState.EXPLORED)) {
                        placedRooms.add(new DiscreteCoordinates(x, y));
                    }
                }
            }

            for (DiscreteCoordinates coords : placedRooms){

                //Get free spots
                ArrayList<DiscreteCoordinates> freeSpots = getEmptyNeighbors(mapstate, coords);
                
                //MaxRoomsToPlace = Min between freespots and Roomstoplace
                int MaxRoomsToPlace = freeSpots.size();
                if (roomsToPlace < freeSpots.size()) {MaxRoomsToPlace = roomsToPlace;}

                //Random Between 1 and MaxRoomsToPlace
                int rooms_toPlace = random.nextInt(MaxRoomsToPlace + 1);

                //Select <rooms_toPlace> coordinates from <freeSpots>
                ArrayList<DiscreteCoordinates> SelectedRoooms = selectKFCoordinates(freeSpots, rooms_toPlace);

                for (DiscreteCoordinates d : SelectedRoooms){
                    mapstate[d.x][d.y] = MapState.PLACED;
                    //System.out.println(d.x + " " + d .y);
                }
    
                roomsToPlace = roomsToPlace - rooms_toPlace;
                mapstate[coords.x][coords.y] = MapState.EXPLORED;
            }
        }


        //Get placed rooms again
        ArrayList<DiscreteCoordinates> placedRooms = new ArrayList<DiscreteCoordinates>();
        for (int x = 0; x < mapstate.length; x++){
            for (int y = 0; y < mapstate[x].length; y++){
                if (mapstate[x][y] == MapState.PLACED) {
                    placedRooms.add(new DiscreteCoordinates(x, y));
                }
            }
        }

        //Get all available spots for the boss room
        ArrayList<DiscreteCoordinates> availableSlots = new ArrayList<>();
        for (DiscreteCoordinates placedroom : placedRooms){
            availableSlots.addAll(getEmptyNeighbors(mapstate, placedroom));
        }

        //Chose random spot for the boss room
       DiscreteCoordinates bossCoords = selectKFCoordinates(availableSlots, 1).get(0);
       mapstate[bossCoords.x][bossCoords.y] = MapState.BOSS_ROOM;
       bossPosition = bossCoords;

        return mapstate;
    }

    private ArrayList<DiscreteCoordinates> getEmptyNeighbors(MapState[][] map, DiscreteCoordinates coords) {
        ArrayList<DiscreteCoordinates> neighbors = new ArrayList<>();

        // Get the coordinates of the cell
        int x = coords.x;
        int y = coords.y;


        // Checks that the neighbouring cells are within the dimensions and are NULL
        if (x > 0 && map[x - 1][y] == MapState.NULL) {
            neighbors.add(new DiscreteCoordinates(x - 1, y));
        }
        if (x < map.length - 1 && map[x + 1][y] == MapState.NULL) {
            neighbors.add(new DiscreteCoordinates(x + 1, y));
        }

        if (y > 0 && map[x][y - 1] == MapState.NULL) {
            neighbors.add(new DiscreteCoordinates(x, y - 1));
        }
        if (y < map[x].length - 1 && map[x][y + 1] == MapState.NULL) {
            neighbors.add(new DiscreteCoordinates(x, y + 1));
        }

        return neighbors;

    }


    // Print map (Mostly for debugging or cheating)
     private void printMap(MapState [][] map) {
         System.out.println("Generated map:");
         System.out.print(" | ");
         for (int j = 0; j < map[0]. length; j++) {
             System.out.print(j + " ");
         }
         System.out.println();
         System.out.print("--|-");
         for (int j = 0; j < map[0]. length; j++) {
             System.out.print("--");
         }
         System.out.println();
         for (int i = 0; i < map.length; i++) {
             System.out.print(i + " | ");
         for (int j = 0; j < map[i].length; j++) {
             System.out.print(map[j][i] + " ");
         }
         System.out.println();
         }
         System.out.println();
     }

    // Select k random different coordinates from array list
    private static ArrayList<DiscreteCoordinates> selectKFCoordinates(ArrayList<DiscreteCoordinates> choices, int count) {
        ArrayList<DiscreteCoordinates> selected = new ArrayList<>();
        Random random = new Random();
    
        // Select count elements from choices at random
        for (int i = 0; i < count; i++) {
          int index = random.nextInt(choices.size());
          selected.add(choices.get(index));
          choices.remove(index);
        }
    
        return selected;
      }


    //implements Logic
    @Override
    public boolean isOn() {
        //If the boss position exists return its state else return true
        if(map[bossPosition.x][bossPosition.y]!=null){
            isCompletedOnce = map[bossPosition.x][bossPosition.y].isOn();
            return map[bossPosition.x][bossPosition.y].isOn();
        }else{
            isCompletedOnce = true;
            System.out.println("There is no boss");
            return true;
        }
    }

    public boolean isCompletedOnce() {
        return isCompletedOnce;
    }
}
