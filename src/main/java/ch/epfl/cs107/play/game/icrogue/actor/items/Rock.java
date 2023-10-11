package ch.epfl.cs107.play.game.icrogue.actor.items;

import java.util.ArrayList;
import java.util.List;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
/*
 * Author: Andrei Pana
 * Date:
 */
public class Rock extends MovableAreaEntity implements Interactor{

    private ICRogueRockHandler handler = new ICRogueRockHandler();
    private Sprite sprite = new Sprite("rock.1", .6f, .6f, this);

    public Rock(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation, coordinates);
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }
 
    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    // Interaction class to interact with the pressure plate
    private class ICRogueRockHandler implements ICRogueInteractionHandler{
        @Override
        public void interactWith(PressurePlate other, boolean isCellInteraction) {
            other.press();
        }
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    
    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        ArrayList<DiscreteCoordinates> coords = new ArrayList<>();
        coords.add(super.getCurrentMainCellCoordinates()); 
        return coords;
    }

    // Allows the player to push it in the given orientation
    public void push(int length, Orientation direction){
        orientate(direction);
        move(length);
    }


}
