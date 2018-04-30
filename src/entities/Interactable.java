package entities;

/**
 * 
 * The <code>Interactable</code> interface should be implemented by any class whose instances
 * can interact with the player.
 * This means that those objects can respond to the player.
 *
 */



public interface Interactable
{
	//public <T> T interact() throws Exception;
	
	/**
	 * 
	 * This method defines how the object responds to the player.
	 * Each type of object has its own way to respond
	 */
	public void respond();
}