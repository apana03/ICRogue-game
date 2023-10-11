package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Cherry extends Item{
    private final float hp = 1;
    public Cherry(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation, coordinates);
        setSprite(new Sprite("icrogue/cherry", 0.6f, 0.6f, this));
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    // Returns the heal power of the cherry
    public float gethealvalue(){
        return hp;
    }

}
