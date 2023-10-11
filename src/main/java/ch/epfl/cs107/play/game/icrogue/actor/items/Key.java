package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Key extends Item{

    private final int id;

    public Key(Area area, Orientation orientation, DiscreteCoordinates coordinates,int id){
        super(area, orientation, coordinates);
        this.id = id;
        setSprite(new Sprite("icrogue/key", 0.6f, 0.6f, this));
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    // Returns the key's id
    public int getId() {
        return id;
    }

}