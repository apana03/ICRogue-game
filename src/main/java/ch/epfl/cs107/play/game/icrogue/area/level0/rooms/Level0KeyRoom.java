package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Level0KeyRoom extends Level0ItemRoom {
    private Key key;
    private final DiscreteCoordinates itemPosition = new DiscreteCoordinates(5,5);

    public Level0KeyRoom(DiscreteCoordinates roomCoordinates, int keyId){
        super(roomCoordinates);
        this.key = new Key(this, Orientation.DOWN,itemPosition, keyId);
    }
    
    public void createArea(){
        super.createArea();
        registerActor(key);
        addItem(key);
    }

    @Override
    public boolean isOn() {
        return key.isCollected() && IsVisited();
    }

}
