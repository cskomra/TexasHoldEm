
/**
 * A Card like from a deck of cards.
 * 
 * @author Connie Skomra
 *
 */
public class Card {
	private String face;
	
	/**
	 * Construct a card with a string representing a card face.
	 * 
	 * @param rankSuite		A string representing a card face (like 2H for 2 of hearts).
	 */
	public Card(String rankSuite) {
		this.setFace(rankSuite);
	}

	/**
	 * Set the card face.
	 * 
	 * @param face		A string representing the card face (like 2H).
	 */
	private void setFace(String face) {
		this.face = face;
	}

	/**
	 * Get the card's suite.
	 * 
	 * @return 		A char representing the card's suite (like H for hearts, or C for clubs).
	 */
	public char getSuite() {
		return face.charAt(1);
	}

	/**
	 * Get the card's rank.
	 * 
	 * @return		A char representing the card's rank (like 2, 5, T, K).
	 */
	public char getRank() {
		return face.charAt(0);
	}
}
