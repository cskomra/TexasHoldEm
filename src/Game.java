import java.util.ArrayList;

/**
 * A game to play Texas Hold Em.
 * 
 * @author Connie Skomra
 *
 */
public class Game {	
	private String gameHands = "";
	ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * Construct a game with input in the specified format: </br>
	 * Black: 2H 3D 5S 9C KD  White: 2D 3H 5C 9S KH
	 * 
	 * @param input		A string representing two players.
	 */
	public Game(String input) {
		this.gameHands = input;
	}
	
	/**
	 * The main entry for the Game. </br>
	 * 
	 * @param args		A string in the specified format.
	 */
	public static void main(String[] args) {
		Game g = new Game(args[0]);
		g.setPlayers();
		System.out.println(g.getWinner());
	}

	/**
	 * Get the winner of the the game to output in the specified format: </br>
	 * White wins. - with high card: Ace 
	 * 
	 * @return		A string in the specified format.
	 */
	private String getWinner() {
		Player winner = players.get(0);
		String winnerInfo = "";
		
		int winnerHandType = winner.getHand().getHandType().ordinal();
		for (int counter = 1; counter < players.size(); counter++) {
			Player challenger = players.get(counter);
			int challengerHandType = challenger.getHand().getHandType().ordinal();
			if(challengerHandType < winnerHandType) {
				winner = players.get(counter);
			}else if(challengerHandType == winnerHandType) { // Tie. (maybe can be broken, maybe not)
				if(challengerHandType == PlayerHand.HandType.HIGH_CARD.ordinal()) {
					winner = compareRanks(winner, challenger);
				}
			}
		}
		if(winner != null) {
			String winnerName = winner.getName();
			PlayerHand winningHand = winner.getHand();
			String handType = convertHandType(winningHand.getHandType());
			
			winnerInfo = winnerName.trim() + " wins. - with " + handType + ": " + winningHand.getDetails();
		}else {
			winnerInfo = "Tie.";
		}
		return winnerInfo;
	}

	/**
	 * Compare each players' cards to see which one has the highest card.
	 * 
	 * @param player1		The first player.
	 * @param player2		The second player.
	 * @return				The winning Player or no player if a tie.
	 */
	private Player compareRanks(Player player1, Player player2) {		
		Player winner = null;
		int counter = player1.getHand().getCards().size();
		
		int[] player1Ranks = player1.getHand().getSortedRanks();
		int[] player2Ranks = player2.getHand().getSortedRanks();
		for(int i = counter - 1; i > 0; i--) {
			if(player2Ranks[i] == player1Ranks[i]) {
				if(i == 0) {
					return null;
				}
				continue;
			}else if(player2Ranks[i] > player1Ranks[i]) {
				return player2;
			}else if(player1Ranks[i] > player2Ranks[i]) {
				return player1;
			}
		}		
		return winner;
	}

	/**
	 * Set players with the game's information.
	 */
	private void setPlayers() {		
		String playerName = "";
		String strPlayerHand = "";

		String[] strPlayers = gameHands.split("  ");
		for(String p : strPlayers) {
			String[] strPlayer = p.split(":");

			playerName = strPlayer[0];

			// get the Cards and create a PlayerHand
			strPlayerHand = strPlayer[1];
			String[] cardFaces = strPlayerHand.trim().split(" ");

			// set cards
			ArrayList<Card> cards = new ArrayList<Card>();
			for(String cF : cardFaces) {				
				cards.add(new Card(cF));
			}
			
			// create PlayerHand from Cards (sets up everything needed)
			PlayerHand hand = new PlayerHand(cards);

			// add new Player
			players.add(new Player(playerName, hand));
		}
	}
	
	/**
	 * Convert the high card integer rank to a string.
	 * 
	 * @param rank		The integer rank to convert.
	 * @return			The rank as a string (like Jack, Queen, King, Ace, 7)
	 */
	public int getHighCardRank(String rank) {
		if(rank == "Jack"){
	 		return 11;
	 	}else if(rank == "Queen"){
	 		return 12;
	 	}else if(rank == "King"){
	 		return 13;
	 	}else if(rank == "Ace"){
	 		return 14;
	 	}else {
	 		return Integer.parseInt(rank);
	 	}
	}
	
	/**
	 * Convert the HandType enum to a String.
	 * 
	 * @param hand		The HandType to convert.
	 * @return			The HandType as a String.
	 */
	private String convertHandType(PlayerHand.HandType hand) {
		String handType = "" + hand;
		String lcHandType = handType.toLowerCase();
		return lcHandType.replaceAll("_", " ");
	}
}