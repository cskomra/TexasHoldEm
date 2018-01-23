/**
 * A Player class.
 * 
 * @author Connie Skomra
 *
 */
public class Player {
	private String name;
	private PlayerHand hand;
	
	/**
	 * Construct a Player.
	 * 
	 * @param name		The player's name.
	 * @param hand		The player's hand.
	 */
	public Player(String name, PlayerHand hand) {
		this.name = name;
		this.hand = hand;
	}
	
	/**
	 * Get the player's hand.
	 * 
	 * @return 	The player's hand as a PlayerHand.
	 */
	public PlayerHand getHand() {
		return hand;
	}
	
	/**
	 * Get the player's name.
	 * 
	 * @return	The player's name as a String.
	 */
	public String getName() {
		return name;
	}
}
