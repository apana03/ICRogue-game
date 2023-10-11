package ch.epfl.cs107.play.game.icrogue.area.level0;

import ch.epfl.cs107.play.game.icrogue.area.ICrogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.*;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0KeyRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0PlateRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0StaffRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0TurretRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0 extends Level  {    
    private static final int BOSS_KEY_ID = 2;

    private static final DiscreteCoordinates SPAWN_COORDINATES = new DiscreteCoordinates(1, 0);

    private  DiscreteCoordinates bossRoomCoords;

    public static final DiscreteCoordinates POSITION_OF_ARRIVAL = new DiscreteCoordinates(2, 2);
    private static final int PART_1_KEY_ID = 1;

    private DiscreteCoordinates startPosition = SPAWN_COORDINATES;

    public Level0(){
        super(true, new DiscreteCoordinates(6/2, 6/2), RoomType.getRoomDistribution(), 7,7);
    }
    public Level0(boolean randomMap){
        super(randomMap, new DiscreteCoordinates(4/2, 2/2), RoomType.getRoomDistribution() , 4, 2);
    }

    @Override
    public String getTitle() {
        return "icrogue/level0"+startPosition.x+startPosition.y;
    }

    public enum RoomType {
        TURRET_ROOM(3), // type and number of rooms
        STAFF_ROOM(1),
        BOSS_KEY_ROOM(1),
        SPAWN(1),
        NORMAL(1),
        PRESSUREPLATE_ROOM(2);

        private int id;

        private RoomType(int id) {
            this.id = id;
        }

        public static RoomType getType(int i) {
            return values()[i];
        }

        public static int[]getRoomDistribution(){

            // Creates the array getRoomDistribution of the length of values 
            int[] roomDistribution = new int[values().length];
            // sets each index to the amount of rooms of that type needed to be created 
            for(int i=0; i< values().length;i++){
                roomDistribution[i] = values()[i].id;
            }
            return roomDistribution;
        }
    }

    @Override
    public void generateFixedMap() {
        //generateMap1();
        //generateMap2();
        generateFinalMap();

    }

    //Debug rooms
     private void generateMap1() {
         DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
         setRoom(room00, new Level0KeyRoom(room00, PART_1_KEY_ID));
         setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);
         lockRoomConnector(room00, Level0Room.Level0Connectors.E, PART_1_KEY_ID);

         DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
         setRoom(room10, new Level0Room(room10));
         setRoomConnector(room10, "icrogue/level000", Level0Room.Level0Connectors.W);
     }

     private void generateMap2() {
         DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
         setRoom(room00, new Level0Room(room00));
         setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);

         DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
         setRoom(room10, new Level0Room(room10));
         setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connectors.S);
         setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connectors.E);

         lockRoomConnector(room10, Level0Room.Level0Connectors.W, BOSS_KEY_ID);
         setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connectors.W);

         DiscreteCoordinates room20 = new DiscreteCoordinates(2, 0);
         setRoom(room20, new Level0StaffRoom(room20));
         setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connectors.W);
         setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connectors.E);

         DiscreteCoordinates room30 = new DiscreteCoordinates(3, 0);
         setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
         setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connectors.W);

         DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
         setRoom(room11, new Level0Room(room11));
         setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connectors.N);
     }

     //Final map, good for debugging too
     private void generateFinalMap() {
         DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
         setRoom(room00, new Level0TurretRoom(room00));
         setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);

         DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
         setRoom(room10, new Level0Room(room10));
         setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connectors.S);
         setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connectors.E);

         lockRoomConnector(room10, Level0Room.Level0Connectors.W, BOSS_KEY_ID);
         setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connectors.W);

         DiscreteCoordinates room20 = new DiscreteCoordinates(2, 0);
         setRoom(room20, new Level0StaffRoom(room20));
         setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connectors.W);
         setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connectors.E);

         DiscreteCoordinates room30 = new DiscreteCoordinates(3, 0);
         setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
         setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connectors.W);

         DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
         setRoom(room11, new Level0Room(room11));
         setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connectors.N);
     }

     // Creates a boss room at the given coordinates
    @Override
    protected ICrogueRoom createBossRoom(DiscreteCoordinates discreteCoordinates) {
        Level0DarkLordRoom bossRoom = new Level0DarkLordRoom(discreteCoordinates);
        bossRoomCoords = discreteCoordinates;
        return bossRoom;
    }

    // Creates a room of type <i> at <discreeteCoordinates> 
    @Override
    protected ICrogueRoom createRoom(DiscreteCoordinates discreteCoordinates, int i) {
        ICrogueRoom room = null;

        switch (RoomType.getType(i)) { // Get the type at index i and create room accordingly
            case BOSS_KEY_ROOM -> room = new Level0KeyRoom(discreteCoordinates, BOSS_KEY_ID);
            case STAFF_ROOM -> room = new Level0StaffRoom(discreteCoordinates);
            case TURRET_ROOM -> room = new Level0TurretRoom(discreteCoordinates);
            case NORMAL -> room = new Level0Room(discreteCoordinates);
            case SPAWN->{
                room = new Level0Room(discreteCoordinates);
                startPosition = discreteCoordinates;
            }
            case PRESSUREPLATE_ROOM -> room = new Level0PlateRoom(discreteCoordinates);
        }
        return room;
    }


    //Sets the connectors in each room to the required orientation and type 
    @Override
    protected void setUpConnector(MapState[][] roomsPlacement, ICrogueRoom room) {
        DiscreteCoordinates coords = room.getCoordinates();


        //Following code is done for the north south east and west of each room
        //Checks if bordering room is not null 
            // Checks if bordering room is a boss room and locks the connectors
            //else sets the connector to the neighbouring room

        if (coords.x + 1 < roomsPlacement.length && !roomsPlacement[coords.x + 1][coords.y].equals(MapState.NULL)) {
            if (roomsPlacement[coords.x + 1][coords.y].equals(MapState.BOSS_ROOM)) {
                setRoomConnector(coords, "icrogue/level0" + (coords.x + 1) + coords.y, Level0Room.Level0Connectors.E);
                lockRoomConnector(coords, Level0Room.Level0Connectors.E, BOSS_KEY_ID);
            } else {
                setRoomConnector(coords, "icrogue/level0" + (coords.x + 1) + coords.y, Level0Room.Level0Connectors.E);
            }
        }
        if (coords.x - 1 > 0 && !roomsPlacement[coords.x - 1][coords.y].equals(MapState.NULL)) {
            if (roomsPlacement[coords.x - 1][coords.y].equals(MapState.BOSS_ROOM)) {
                setRoomConnector(coords, "icrogue/level0" + (coords.x - 1) + coords.y, Level0Room.Level0Connectors.W);
                lockRoomConnector(coords, Level0Room.Level0Connectors.W, BOSS_KEY_ID);
            } else {
                setRoomConnector(coords, "icrogue/level0" + (coords.x - 1) + coords.y, Level0Room.Level0Connectors.W);
            }
        }
        if (coords.y + 1 < roomsPlacement[coords.x].length && !roomsPlacement[coords.x][coords.y + 1].equals(MapState.NULL)) {
            if (roomsPlacement[coords.x][coords.y + 1] == MapState.BOSS_ROOM) {
                setRoomConnector(coords, "icrogue/level0" + coords.x + (coords.y + 1), Level0Room.Level0Connectors.N);
                lockRoomConnector(coords, Level0Room.Level0Connectors.N, BOSS_KEY_ID);
            } else {
                setRoomConnector(coords, "icrogue/level0" + coords.x + (coords.y + 1), Level0Room.Level0Connectors.N);
            }
        }
        if (coords.y - 1 > 0 && !roomsPlacement[coords.x][coords.y - 1].equals(MapState.NULL)) {
            if (roomsPlacement[coords.x][coords.y - 1].equals(MapState.BOSS_ROOM)) {
                setRoomConnector(coords, "icrogue/level0" + coords.x + (coords.y - 1), Level0Room.Level0Connectors.S);
                lockRoomConnector(coords, Level0Room.Level0Connectors.S, BOSS_KEY_ID);
            } else {
                setRoomConnector(coords, "icrogue/level0" + coords.x + (coords.y - 1), Level0Room.Level0Connectors.S);
            }
        }
    }
}
