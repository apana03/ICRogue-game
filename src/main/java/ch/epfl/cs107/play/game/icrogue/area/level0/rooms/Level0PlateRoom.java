package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.PressurePlate;
import ch.epfl.cs107.play.game.icrogue.actor.items.Rock;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Level0PlateRoom extends Level0Room {

    //Arraylist of pressure plates
    private ArrayList<PressurePlate> plates = new ArrayList<>();
    
    
    public Level0PlateRoom(DiscreteCoordinates roomCoordinates){
        super(roomCoordinates);

        // Add two pressure plates to two random coordinates in room
        plates.add(new PressurePlate(this, Orientation.DOWN, new DiscreteCoordinates(new Random().nextInt(8) + 1, new Random().nextInt(8) + 1)));
        plates.add(new PressurePlate(this, Orientation.DOWN, new DiscreteCoordinates(new Random().nextInt(8) + 1, new Random().nextInt(8) + 1)));
    }

    public void createArea(){
        super.createArea();

        // Register all plates
        for (PressurePlate plate : plates){
            registerActor(plate);
        }

        //register rock
        registerActor(new Rock(this, Orientation.DOWN, new DiscreteCoordinates(4, 4)));
    }

    @Override
    public boolean isOn() {
        //Check if pressure plates are pressed 
        for (PressurePlate plate : plates){
            if (!plate.isPressed()){
                return false;
            }
        }
        return true;

    }

}
