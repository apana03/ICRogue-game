package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import static ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICrogueCellType.HOLE;
import static ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.ICrogueCellType.WALL;


public class Skull extends Projectile {

        private static int damage = 2;
        private static final int frameNb = 5;
        private SkullInteractionHandler handler = new SkullInteractionHandler();

        private Sprite[][] sprites = new Sprite[4][3];
        private Sprite[] spriteLine = new Sprite[3];

        private Animation currentAnimation;

        public Skull(Area area, Orientation orientation, DiscreteCoordinates startCoords){
            super(area,orientation,startCoords,damage,frameNb);

            //Sets up animation
            for(int i=0; i<3;i++){
                for(int j=0;j<4;j++){
                    sprites[j][i] = new Sprite("zelda/flameskull",1.5f,1.5f,this,new RegionOfInterest(i*32,j*32,32,32));
                }
            }
            
            // Set spriteline according to the orientation
            switch (orientation){
                case UP -> spriteLine = sprites[0];
                case LEFT -> spriteLine = sprites[1];
                case DOWN -> spriteLine = sprites[2];
                case RIGHT -> spriteLine = sprites[3];
            }

            // Create an animation with a frame duration of 10 and the sprites
            Animation animation = new Animation(10, spriteLine);
            animation.setSpeedFactor(5);
            animation.setAnchor(new Vector(0, 0));
            animation.setWidth(1.5f);
            animation.setHeight(1.5f);

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


        // Class that manages the interactions of the skull
        private class SkullInteractionHandler implements ICRogueInteractionHandler {

            //If it collides with the wall consume itself
            @Override
            public void interactWith(ICRogueBehavior.ICrogueCell other, boolean isCellInteraction) {
                if(other.getType()==WALL || other.getType() == HOLE){
                    consume();
                }
            }

            //If it collides with the player damage him
            @Override
            public void interactWith(ICRoguePlayer other, boolean isCellInteraction){
                consume();
                other.setHp((int)other.getHp()-getDamage());
            }
        }
}
