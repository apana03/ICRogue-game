package ch.epfl.cs107.play.game.icrogue.actor.items;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
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
public class PressurePlate extends Item{

    private boolean isPressed = false;
    final static private float DEPRESSTIME = 0.1f;
    private float COUNTER = 0.f;

    private ArrayList<Sprite> on_off = new ArrayList<>();
    private Sprite baseSprite;

    public PressurePlate(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation, coordinates);

        // Set base sprite and add "off" sprite to index 0 and "on" sprite on index 1
        baseSprite = new Sprite("GroundPlateOff", .5f, .5f, this);
        on_off.add(new Sprite("GroundLightOn", .5f, .5f, this));
        on_off.add(new Sprite("GroundLightOff", .5f, .5f, this));
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        baseSprite.draw(canvas);

        // Draw the state of the pressure plate
        if(isPressed){ // off
            on_off.get(0).draw(canvas);
        }
        else{ // on
            on_off.get(1).draw(canvas);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        COUNTER += deltaTime; 
        if (isPressed && COUNTER >= DEPRESSTIME){ // if rock or player aint on it for <DEPRESSTIME>
            isPressed = false; // Depress
            COUNTER = 0.f; // Reset counter
        }
    }


    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }
 
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    public void press(){ // Lets the interaction handler set it as pressed
        isPressed = true;
    }

    public boolean isPressed(){
        return isPressed;
    }
}
