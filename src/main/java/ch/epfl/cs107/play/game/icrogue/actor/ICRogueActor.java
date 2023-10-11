package ch.epfl.cs107.play.game.icrogue.actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collections;
import java.util.List;

public abstract class ICRogueActor extends MovableAreaEntity{
    public ICRogueActor(Area area, Orientation orientation, DiscreteCoordinates position){
        super(area,orientation,position);
    }
    
    // Code to run whenever we enter a new area
    public void enterArea(Area area, DiscreteCoordinates position){

        area.registerActor(this); // Register actor
        area.setViewCandidate(this); // Set him as a view candidate
        setOwnerArea(area); // Set the current area
        setCurrentPosition(position.toVector()); // Set the position
        resetMotion();
    }

    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
    }
}
