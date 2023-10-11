package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Ghost;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0EnemyRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0StaffRoom;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;


public class Bomb extends Projectile{

    private int counter = 60;
    private boolean exploded;
    private static float damage = 5.f;
    private static final int frameNb = 0;
    private Sprite[] bombSprites = new Sprite[2];
    private Sprite[] explosionSprites = new Sprite[7];
    private Animation currentAnimation;
    private
    BombInteractionHandler handler = new BombInteractionHandler();

    public Bomb(Area area, Orientation orientation, DiscreteCoordinates startCoords){
        super(area,orientation,startCoords,damage,frameNb);

        // Set bomb animation sprites
        for(int i = 0; i < 2; i++) {
            bombSprites[i] = new Sprite("zelda/bomb", 1f, 1f, this, new RegionOfInterest(16 * i, 0, 16, 16), new Vector(0, 0));
        }

        // Create an animation with a frame duration of 6 and the sprites
        Animation animation = new Animation(6, bombSprites);
        animation.setAnchor(new Vector(0, 0));
        animation.setWidth(1f);
        animation.setHeight(1f);

        currentAnimation = animation;
    }


    @Override
    public void update(float deltaTime) {
        currentAnimation.update(deltaTime);
        counter -= deltaTime;
        // If the counter has reached the explosion threshold, explode
        if (counter <= 15 && !exploded ) {
            explode();
        }
        //If the explosion finishes it dissapears
        if(counter <=0){
            consume();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
    }

    private void explode(){
        exploded = true;

        // Set explosion sprites
        for(int i = 0; i < 7; i++) {
            explosionSprites[i] = new Sprite("zelda/explosion", 2f, 2f, this, new RegionOfInterest(32* i, 0, 32, 32), new Vector(0, 0));
        }

        // Create an animation with a frame duration of 5 and the sprites
        currentAnimation = new Animation(3, explosionSprites);
        currentAnimation.setAnchor(new Vector(0, 0));
        currentAnimation.setWidth(1f);
        currentAnimation.setHeight(1f);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler,isCellInteraction);
    }


    // Class that impliments the intreaction of the bomb if its exploded
    private class BombInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(ICRoguePlayer other, boolean isCellInteraction) {
            if (exploded){
                other.setHp(other.getHp()-getDamage());
            }
        }

        @Override
        public void interactWith(Turret turret, boolean isCellInteraction){
            if(exploded){
                turret.die();
                ((Level0EnemyRoom)getOwnerArea()).eliminateEnemy(turret);
            }
        }

        @Override
        public void interactWith(Ghost ghost, boolean isCellInteraction){
            if(exploded){
                consume();
                ghost.die();
                ((Level0StaffRoom)getOwnerArea()).eliminateEnemy(ghost);
            }
        }
    }

}
