package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.Enemy;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Ghost;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Level0StaffRoom extends Level0ItemRoom {
    private Staff staff;
    private final DiscreteCoordinates itemPosition = new DiscreteCoordinates(5,5);

    private List<Enemy> activeEnemies = new ArrayList<>();

    private Ghost ghost =  new Ghost(this, Orientation.DOWN, new DiscreteCoordinates(7, 7));

    private final static float SPAWN_COOLDOWN = 5.f;
    private float counter =0;

    public Level0StaffRoom(DiscreteCoordinates roomCoordinates){
        super(roomCoordinates);
        staff = new Staff(this, Orientation.DOWN,itemPosition);
        activeEnemies.add(ghost);
    }
    public void createArea(){
        super.createArea();
        registerActor(staff); // Register the staff

        // Register all enemies
        for (Enemy activeEnemy : activeEnemies) {
            registerActor(activeEnemy);
        }
        // Add item to list
        addItem(staff);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        counter+=deltaTime;

        if(counter>= SPAWN_COOLDOWN){ // Spawns a new ghost event <SPAWN_COOLDOWN> time
            Ghost newGhost = new Ghost(this, Orientation.DOWN, new DiscreteCoordinates(7, 7));
            addEnemy(newGhost);
            registerActor(newGhost);
            counter=0.f;
        }
    }

    @Override
    public boolean isOn() {
        return staff.isCollected() && IsVisited() ;
    }

    public void setGhostTarget(ICRogueActor a){
        // set the player as the target for all ghosts
        for (Enemy activeEnemy : activeEnemies) {
            ((Ghost)activeEnemy).setTarget(a);
        }
    }

    public void eliminateEnemy(Enemy enemy){
        activeEnemies.remove(enemy);
    }

    protected void addEnemy(Enemy enemy){
        activeEnemies.add(enemy);
    }

}
