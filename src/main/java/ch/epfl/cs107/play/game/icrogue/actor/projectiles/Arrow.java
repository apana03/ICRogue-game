package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICrogueCell;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;


import static ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICrogueCellType.HOLE;
import static ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICrogueCellType.WALL;

public class Arrow extends Projectile{

    private static float damage = 2.f;
    private static final int frameNb = 5;
    private ArrowInteractionHandler handler = new ArrowInteractionHandler();

    public Arrow(Area area, Orientation orientation, DiscreteCoordinates startCoords){
        super(area,orientation,startCoords,damage,frameNb);
        setSprite(new Sprite("zelda/arrow", 1f, 1f, this ,new RegionOfInterest (32* orientation.ordinal(), 0, 32, 32),new Vector(0, 0)));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        move(frameNb);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler,isCellInteraction);
    }

    // Class to impliment interactions between walls/holes and the player
    private class ArrowInteractionHandler implements ICRogueInteractionHandler{
        @Override
        public void interactWith(ICrogueCell other, boolean isCellInteraction) {
            if(other.getType()==WALL || other.getType() == HOLE){
                consume();
            }
        }

        @Override
        public void interactWith(ICRoguePlayer other, boolean isCellInteraction) {
            consume();
            other.setHp(other.getHp()-getDamage());
        }
    }
}
