package ch.epfl.cs107.play.game.icrogue.area;

import java.util.ArrayList;
import java.util.List;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.ICrogue;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.Connector.ConnectorStates;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public abstract class ICrogueRoom extends Area implements Logic{
    private DiscreteCoordinates coordinates;

    private String behaviorName;
    private String title;

    private ICRogueBehavior behavior;

    private boolean isVisited = false;
    private Logic solved = Logic.FALSE;

    private ArrayList<Connector> connectors = new ArrayList<>();
    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected void createArea(){
        for (Connector connector : connectors){
            registerActor(connector);
        }
    }

    /// EnigmeArea extends Area

    @Override
    public final float getCameraScaleFactor() {
        return ICrogue.CAMERA_SCALE_FACTOR;
    }

    public abstract DiscreteCoordinates getPlayerSpawnPosition();
    
    /// Demo2Area implements Playable

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
        	behavior = new ICRogueBehavior(window, behaviorName);
            setBehavior(behavior);
            //Create the area
            createArea();
            return true;
        }
        return false;
    }

    public ICrogueRoom(List<DiscreteCoordinates> connectorsCoordinates , List<Orientation > orientations , String behaviorname, DiscreteCoordinates discreteCoordinates){
        this.behaviorName = behaviorname;
        coordinates = discreteCoordinates;

        //Add connectors to all coordinates
        for (int i =0; i < connectorsCoordinates.size(); i++){
            connectors.add(new Connector(this, orientations.get(i), connectorsCoordinates.get(i)));
        }
    }

    public String getTitle(){
        return "icrogue";
    }
    public DiscreteCoordinates getCoordinates(){
        return coordinates;
    }

    public ArrayList<Connector> getConnectors(){
        return connectors;
    }


    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        Keyboard keyboard = this.getKeyboard();

        // Code to change all connector's state (useful for debbuging)
        if (keyboard.get(Keyboard.O).isPressed()){
            for (Connector connector : connectors){
                connector.setState(ConnectorStates.OPEN);
            }
        }
        if(keyboard.get(Keyboard.L).isPressed()){
            connectors.get(0).setState(ConnectorStates.LOCKED);
        }
        if(keyboard.get(Keyboard.T).isPressed()){
            for (Connector connector : connectors){
                if(connector.getState() == ConnectorStates.CLOSED){
                    connector.setState(ConnectorStates.OPEN);
                }else if(connector.getState() == ConnectorStates.OPEN){
                    connector.setState(ConnectorStates.CLOSED);
                }
            }
        }
        updateSolvedRoom();
    }
    
    public boolean IsVisited(){
        return isVisited;
    }
    public Logic isSolved(){return solved;}

    public void setSolved(Logic solved) {
        this.solved = solved;
    }
    //set the room to visited 
    public void visit(){
        if (!isVisited){
            isVisited = true;
        }
    }

    public void updateSolvedRoom(){
        // Impliments logic to open and close connectors according to if its solved or not
        if(this.isOn()){
            for(int i = 0; i<getConnectors().size();i++){
                if(getConnectors().get(i).getState() == ConnectorStates.CLOSED){
                    getConnectors().get(i).setState(ConnectorStates.OPEN);
                }
            }
        }
    }

    //implements Logic
    @Override
    public boolean isOn() {
        return IsVisited();
    }
}
