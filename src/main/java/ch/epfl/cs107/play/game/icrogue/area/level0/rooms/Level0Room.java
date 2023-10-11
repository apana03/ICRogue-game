package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICrogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import java.util.ArrayList;
import java.util.List;
import ch.epfl.cs107.play.game.areagame.actor.Background;


public class Level0Room extends ICrogueRoom{

    private final static String behaviorName = "icrogue/Level0Room"; 

    public enum LevelTypes{
        
    }

    public enum Level0Connectors implements ConnectorInRoom {

        // ordre des attributs: position , destination , orientation
        W(new DiscreteCoordinates(0, 4),
        new DiscreteCoordinates(8, 5), Orientation.RIGHT),
        S(new DiscreteCoordinates(4, 0),
        new DiscreteCoordinates(5, 8), Orientation.UP),
        E(new DiscreteCoordinates(9, 4),
        new DiscreteCoordinates(1, 5), Orientation.LEFT),
        N(new DiscreteCoordinates(4, 9),
        new DiscreteCoordinates(5, 1), Orientation.DOWN);

        private final DiscreteCoordinates position;
        private final DiscreteCoordinates destination;
        private final Orientation orientation;

        private Level0Connectors(DiscreteCoordinates position, DiscreteCoordinates destination, Orientation orientation){
            this.position = position;
            this.destination = destination;
            this.orientation = orientation;
        }

        @Override
        public int getIndex() {
            return this.ordinal();
        }

        @Override
        public DiscreteCoordinates getDestination() {
            return this.destination;
        }

        // Create a list and add all the orientations of the connectors to it
        public static List<Orientation > getAllConnectorsOrientation(){
            List<Orientation> list = new ArrayList<Orientation>();
            for (Level0Connectors level0Connectors : Level0Connectors.values()){
                list.add(level0Connectors.orientation);
            }
            return list;


        }
        // Create a list and add all the positions of the connectors to it
        public static List<DiscreteCoordinates > getAllConnectorsPosition(){
            List<DiscreteCoordinates> list = new ArrayList<DiscreteCoordinates>();
            for (Level0Connectors level0Connectors : Level0Connectors.values()){
                list.add(level0Connectors.position);
            }
            return list;
        }

    }

    public Level0Room(DiscreteCoordinates roomCoordinates){
        super(Level0Connectors.getAllConnectorsPosition(), Level0Connectors.getAllConnectorsOrientation(), behaviorName, roomCoordinates);
    }

    @Override
    public String getTitle(){
        return "icrogue/level0" + this.getCoordinates().x + this.getCoordinates().y;
    }
    
    @Override
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return new DiscreteCoordinates(5,15);
	}
	
    public String getBehaviorName() {
        return behaviorName;
    }

	protected void createArea() {
        // Base
        super.createArea();
        registerActor(new Background(this,getBehaviorName())) ;
    }
	
	
    
}
