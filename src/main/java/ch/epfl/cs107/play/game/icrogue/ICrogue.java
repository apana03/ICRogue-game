package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICrogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.io.FileSystem;

import static ch.epfl.cs107.play.game.icrogue.area.level0.Level0.POSITION_OF_ARRIVAL;


public class ICrogue extends AreaGame{
    public final static float CAMERA_SCALE_FACTOR = 11.f;

	private ICrogueRoom currentRoom;

	private Level0 currentLevel;

	private ICRoguePlayer player;

	/**
	 * Add all the areas
	 */
	private void initLevel(){
		
		//Create level and generate the map
		currentLevel = new Level0();
		currentLevel.generateMap();
		currentLevel.addAreas(this);
		//Set the current room and visit it
		currentRoom = (ICrogueRoom)setCurrentArea(currentLevel.getTitle(), true);
		currentRoom.visit();
		//Init player
        player = new ICRoguePlayer(getCurrentArea(), Orientation.DOWN, new DiscreteCoordinates(2,2), "zelda/player");
		player.enterArea(getCurrentArea(), new DiscreteCoordinates(2,2));
    }


	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			initLevel();
			return true;
		   }
		return false;
	}

	@Override
	public void update(float deltaTime) {
		//If is passing door, switch room and switch the ispassingdoor back to false
		if(player.isPassingDoor()){
			switchRoom((player.getDestination()));
			player.setPassingDoor(false);
		}

		// If level is completed show screen
		if(!currentLevel.isCompletedOnce() && currentLevel.isOn()){
			System.out.println("You Win");
			getCurrentArea().registerActor(new Foreground(getCurrentArea(),new RegionOfInterest(0,0,1200,1600),"icrogue/youwin"));
			getCurrentArea().unregisterActor(player);
		}
		super.update(deltaTime);
		//Keyboard initialising
		Keyboard ICRogueKeyboard = getCurrentArea().getKeyboard();
		restartIfPressed(ICRogueKeyboard.get(Keyboard.R));
	}

	@Override
	public void end() {

	}

	@Override
	public String getTitle() {
		return "icrogue";
	}

	//Code called everytime player switches rooms 
	protected void switchRoom(String destination) {
		if (destination != null ){// Make sure the destination exists
			currentRoom = (ICrogueRoom)setCurrentArea(destination,true);

			//Make sure we arrive in the correct spot from where we entered the connector
			DiscreteCoordinates arrivalCoordinates;
			// West
			if (player.getPosition().x <= 1){
				arrivalCoordinates = new DiscreteCoordinates(8, (int)player.getPosition().y);
			}
			// South
			else if(player.getPosition().y <= 1){
				arrivalCoordinates = new DiscreteCoordinates((int)player.getPosition().x, 8);
			}
			// East
			else if (player.getPosition().x >= 8){
				arrivalCoordinates = new DiscreteCoordinates(1, (int)player.getPosition().y);
			}
			else if (player.getPosition().y >= 8){
				arrivalCoordinates = new DiscreteCoordinates((int)player.getPosition().x, 1);
			}
			//Something went wrong
			else{
				arrivalCoordinates = POSITION_OF_ARRIVAL;
			}

			//Enter area at the calculated <arrivalCoordinates>
			player.enterArea(currentRoom, arrivalCoordinates);
			currentRoom.visit();
		}
	}

	private void restartIfPressed(Button r){
		if(r.isPressed()){
			initLevel();
		}
	}

}
