package ch.epfl.cs107.play.game.icrogue.actor;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Connector extends AreaEntity{


    private final int NO_KEY_ID = 6969;

    private String destination;
    private ConnectorStates state = ConnectorStates.INVISIBLE;
    private int neededKeyId = NO_KEY_ID;

    private final ArrayList<Sprite> connectorSprites = new ArrayList<>();
    public enum ConnectorStates{
        OPEN, CLOSED, LOCKED, INVISIBLE;
    }

    public Connector(Area ownerArea, Orientation orientation, DiscreteCoordinates position){
        super(ownerArea,orientation, position);
        connectorSprites.add(new Sprite("icrogue/invisibleDoor_"+orientation.ordinal(),(orientation.ordinal()+1)%2+1,orientation.ordinal()%2+1, this));
        // pour fermé:
        connectorSprites.add(new Sprite("icrogue/door_"+orientation.ordinal(),(orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this));
        // pour verrouillé
        connectorSprites.add(new Sprite("icrogue/lockedDoor_" +orientation.ordinal(), (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1,this));

    }
    public Connector(Area ownerArea, Orientation orientation, DiscreteCoordinates position, int neededKeyId){
        super(ownerArea,orientation, position);
        // pour invisible
        connectorSprites.add(new Sprite("icrogue/invisibleDoor_"+orientation.ordinal(),(orientation.ordinal()+1)%2+1,orientation.ordinal()%2+1, this));
        // pour fermé:
        connectorSprites.add(new Sprite("icrogue/door_"+orientation.ordinal(),(orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this));
        // pour verrouillé
        connectorSprites.add(new Sprite("icrogue/lockedDoor_" +orientation.ordinal(), (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1,this));

        this.neededKeyId = neededKeyId;

    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setNeededKeyId(int neededKeyId) {
        this.neededKeyId = neededKeyId;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates();
        return List.of(coord , coord.jump(new Vector((getOrientation().ordinal()+1)%2, getOrientation().ordinal()%2)));
    }

    // If the connector is open dont take cell space 
    @Override
    public boolean takeCellSpace() {
        if (this.state == ConnectorStates.OPEN){
            return false;
        }
        return true;
    }
    
    @Override
    public void draw(Canvas canvas) {

        // What to draw following the state of the connector
        switch(this.state){
            case INVISIBLE -> connectorSprites.get(0).draw(canvas);
            case CLOSED -> connectorSprites.get(1).draw(canvas);
            case LOCKED -> connectorSprites.get(2).draw(canvas);
            // If open dont do anything
            
        }
        
    }

    //Impliments Interactable
    @Override
    public boolean isCellInteractable() {
        return true;
    }
    @Override
    public boolean isViewInteractable() {
        return true;
    }
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    public int getNeededKeyId() { return neededKeyId; }

    public ConnectorStates getState(){
        return this.state;
    }

    public void setState(ConnectorStates state){
        this.state = state;
    }
}
