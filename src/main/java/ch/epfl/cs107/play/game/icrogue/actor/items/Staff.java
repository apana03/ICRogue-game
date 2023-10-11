package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;


public class Staff extends Item{

    private Sprite[] sprites =  new Sprite[8];

    private Animation currentAnimation;

    public Staff(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation, coordinates);

        // Add all animation sprites
        for(int i=0; i<8;i++){
            sprites[i] = new Sprite("zelda/staff",1f,1f,this,new RegionOfInterest(i*32,0,32,32));
        }


        // Floating animation for the staff
        Animation animation = new Animation(10, sprites);
        animation.setSpeedFactor(5);
        animation.setWidth(1f);
        animation.setHeight(1f);

        currentAnimation = animation;
    }

    @Override
    public void draw(Canvas canvas) {
        if(!this.isCollected()){
            currentAnimation.draw(canvas);
        }
    }

    @Override
    public void update(float deltaTime) {
        currentAnimation.update(deltaTime);
        super.update(deltaTime);
    }

    @Override
    public boolean isViewInteractable() {
        return !isCollected();
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }
 
    @Override
    public boolean takeCellSpace() {
        return !isCollected();
    }
}
