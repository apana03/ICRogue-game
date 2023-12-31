package ch.epfl.cs107.play.game.icrogue;


import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.window.Window;

public class ICRogueBehavior extends AreaBehavior {
	public enum ICrogueCellType{
		NULL(0, false),
		GROUND(-16777216, true), // traversable
        WALL(-14112955, false),
        HOLE(-65536, true);

		final int type;
		final boolean isWalkable;

		ICrogueCellType(int type, boolean isWalkable){
			this.type = type;
			this.isWalkable = isWalkable;
		}

		public static ICrogueCellType toType(int type){
			for(ICrogueCellType ict : ICrogueCellType.values()){
				if(ict.type == type)
					return ict;
			}
			// When you add a new color, you can print the int value here before assign it to a type
			System.out.println(type);
			return NULL;
		}
	}

	/**
	 * Default Tuto2Behavior Constructor
	 * @param window (Window), not null
	 * @param name (String): Name of the Behavior, not null
	 */
	public ICRogueBehavior(Window window, String name){
		super(window, name);
		int height = getHeight();
		int width = getWidth();
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width ; x++) {
				ICrogueCellType color = ICrogueCellType.toType(getRGB(height-1-y, x));
				setCell(x,y, new ICrogueCell(x,y,color));
			}
		}
	}
	
	/**
	 * Cell adapted to the Tuto2 game
	 */
	public class ICrogueCell extends Cell {
		/// Type of the cell following the enum
		private final ICrogueCellType type;
		
		/**
		 * Default Tuto2Cell Constructor
		 * @param x (int): x coordinate of the cell
		 * @param y (int): y coordinate of the cell
		 * @param type (EnigmeCellType), not null
		 */
		public ICrogueCell(int x, int y, ICrogueCellType type){
			super(x, y);
			this.type = type;
		}
	
		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
            
            // this refers to current cell
            for (Interactable entity__ : this.entities){
                if(entity__.takeCellSpace() && entity.takeCellSpace()){
                    return false;
                }
            }
            
			return type.isWalkable;
	    }

		public ICrogueCellType getType() {
			return type;
		}
		@Override
		public boolean isCellInteractable() {
			return true;
		}

		@Override
		public boolean isViewInteractable() {
			return false;
		}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
			((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
		}

	}
}


