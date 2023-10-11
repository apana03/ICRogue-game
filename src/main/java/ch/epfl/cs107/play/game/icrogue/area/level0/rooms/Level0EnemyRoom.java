package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.Enemy;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import java.util.ArrayList;
import java.util.List;

public class Level0EnemyRoom extends Level0Room {
    private List<Enemy> activeEnemies;
    private List<Cherry> cherries;

    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates){
        super(roomCoordinates);

        //Initialise Enemy list and Cherry list
        activeEnemies = new ArrayList<Enemy>();
        cherries = new ArrayList<Cherry>();
    }

    protected void addEnemy(Enemy enemy){
        activeEnemies.add(enemy);
    }

    public void eliminateEnemy(Enemy enemy){
        activeEnemies.remove(enemy);
    }
    public void addCherry(Cherry cherry){
        cherries.add(cherry);
    }

    // Register all actors after creating the area
    @Override
    protected void createArea() {
        super.createArea();
        for (Enemy activeEnemy : activeEnemies) {
            registerActor(activeEnemy);
        }
        for (Cherry cherry : cherries){
            registerActor(cherry);
        }
    }

    @Override
    public boolean isOn() {
        return activeEnemies.isEmpty() && IsVisited();
    }
}
