package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.*;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Ghost;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0EnemyRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0StaffRoom;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import static ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICrogueCellType.HOLE;
import static ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICrogueCellType.WALL;

public class Fire extends Projectile{

    private static int damage = 1;
    private static final int frameNb = 5;
    private FireInteractionHandler handler = new FireInteractionHandler();

    private Sprite[] sprites = new Sprite[frameNb];

    private Animation currentAnimation;

    public Fire(Area area, Orientation orientation, DiscreteCoordinates startCoords){
        super(area,orientation,startCoords,damage,frameNb);

        //Sets up animation for Fire
        for(int i = 0; i < frameNb; i++) {
            sprites[i] = new Sprite("zelda/fire", 1f, 1f, this, new RegionOfInterest(16 * i, 0, 16, 16), new Vector(0, 0));
        }

        // Create an animation with a frame duration of 10 and the sprites
        Animation animation = new Animation(10, sprites);
        animation.setSpeedFactor(5);
        animation.setAnchor(new Vector(0, 0));
        animation.setWidth(1f);
        animation.setHeight(1f);

        currentAnimation = animation;

    }

    @Override
    public void update(float deltaTime) {
        move(frameNb);
        currentAnimation.update(deltaTime);
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler,isCellInteraction);
    }

    // Class that implioments interaction of fire with enemies and holes
    private class FireInteractionHandler implements ICRogueInteractionHandler{

        //If it collides with a wall destroy itself
        @Override
        public void interactWith(ICrogueCell other, boolean isCellInteraction) {
            if(other.getType()==WALL || other.getType() == HOLE){
                consume();
            }
        }

        //If it collides with a turret kill it
        @Override
        public void interactWith(Turret turret, boolean isCellInteraction){
            turret.die();
            ((Level0EnemyRoom)getOwnerArea()).eliminateEnemy(turret);
            consume();
        }

        //If it collides with the boss add damage
        @Override
        public void interactWith(DarkLord other, boolean isCellInteraction) {
            consume();
            other.setHp(other.getHp()-getDamage());
            if(other.getHp()<=0){
                ((Level0EnemyRoom)getOwnerArea()).eliminateEnemy(other);
            }
        }


        //If it collides with a ghost kill it
        @Override
        public void interactWith(Ghost ghost, boolean isCellInteraction){
            consume();
            ghost.die();
            ((Level0StaffRoom)getOwnerArea()).eliminateEnemy(ghost);
        }
    }
}
