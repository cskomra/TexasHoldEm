
import java.util.Arrays;
import java.util.List;

/**
 * A hand of cards.
 * 
 * @author Connie Skomra
 *
 */
public class PlayerHand {
	private List<Card> cardHand;
	private HandType playerHandType;
	private String highCard;
	private int[] sortedRanks;
	private String details;
	private int hCount = 0;
	private int dCount = 0;
	private int cCount = 0;
	private int sCount = 0;
	private char repeatingChar;
	
	/** 
	 * Types of hands for Texas Hold Em
	 * 
	 * @author Connie
	 *
	 */
	enum HandType {
		STRAIGHT_FLUSH,
		FOUR_OF_KIND,
		FULL_HOUSE,
		FLUSH,
		STRAIGHT,
		THREE_OF_KIND,
		TWO_PAIR,
		PAIR,
		HIGH_CARD
	}
	
	/**
	 * Construct a player's hand.
	 * 
	 * @param cards		A list of Cards.
	 */
	public PlayerHand(List<Card> cards) {
		this.setCardHand(cards);
		this.setHandType();
	}
	
	/**
	 * Get the type of hand.
	 * 
	 * @return	A type of hand (enum).
	 */
	public HandType getHandType() {
		return this.playerHandType;
	}	
	
	/** 
	 * Get the details of the hand.
	 * 
	 * @return	A string like "Ace" or "2 over 4"
	 */
	public String getDetails() {
		return this.details;
	}
	
	/** 
	 * Get the high card.
	 * 
	 * @return	A string like "Jack" or "8".
	 */
	public String getHighCard() {
		return this.highCard;
	}
	
	/** 
	 * Get ranks as face values.
	 * 
	 * @return	Ranks sorted by value, lowest to highest.
	 */
	public int[] getSortedRanks() {
		return this.sortedRanks;
	}
	
	/** 
	 * Get the list of Cards.
	 * 
	 * @return 	List of Cards.
	 */
	public List<Card> getCards() {
		return cardHand;
	}

	/** 
	 * Set the hand's list of Cards.
	 * 
	 * @param cardHand 	A list of Cards.	
	 */
	private void setCardHand(List<Card> cardHand) {
		this.cardHand = cardHand;
	}
	
	// CHECKERS	
	/**
	 * Check if the hand has a flush.
	 * 
	 * @return True or False.
	 */
	private boolean hasFlush() {
		if(hCount == 5 || dCount == 5 || cCount == 5 || sCount == 5) {
			return true;
		}
		return false;
	}
	
	/** 
	 * Check if the hand has a straight.
	 * 
	 * @param sortedHandRanks 	An int array of the cards' ranks sorted least to greatest. 
	 * @return					True or False.
	 */
	private boolean hasStraight(int[] sortedHandRanks) {
		// based on sorted array 
		String compare = "234567891011121314";
		String[] hand = Arrays.stream(sortedHandRanks).mapToObj(String::valueOf).toArray(String[]::new);
		String result = String.join("", hand);
		
		if (compare.indexOf(result) != -1) {
			return true;
		}else {
			return false;
		}
	}
	
	/** 
	 * Check if the hand has a certain number of repeating values like 8s or Kings.
	 * @param cardRanks		A char array of the cards' ranks.
	 * @param noKinds		The number of times it repeats.
	 * @return				True or False.
	 */
	private boolean hasMultipleKinds(char[] cardRanks, int noKinds) {
		int outer = 0;
		int inner = 0;
		int repeats = 0;
		repeatingChar = '0';
		while(outer < cardRanks.length) {
			repeats = 0;
			inner = 0;
			while(inner < cardRanks.length) {
				if (cardRanks[outer] == cardRanks[inner]) {
					repeatingChar = cardRanks[outer];
					repeats++;
					if (repeats == noKinds) {
						return true;
					}
				}inner++;
			}
			outer++;
		}		
		return false;
	}
	
	/** 
	 * Remove a certain number of repeating chars from char array.
	 * 
	 * @param cardRanks		Array of chars representing the hand's ranks.	
	 * @param howMany		Number of repeating chars to remove.
	 * @return				New char array without repeating chars.
	 */
	private char[] removeChars(char[] cardRanks, int howMany) {
		char[] cardRanksReduced = new char[cardRanks.length - howMany];		
		int indexer = 0;
		for(int i = 0; i < cardRanks.length; i++) {
			if(cardRanks[i] != repeatingChar) {
				cardRanksReduced[indexer] = cardRanks[i];
				indexer++;
			}
		}
		return cardRanksReduced;
	}
	
	// UTILITIES	
	/** 
	 * Sort char array of ranks.
	 * 
	 * @param handRanks		Array of ranks to sort.
	 * @return				The sorted array of ranks least to greatest.
	 */
	private int[] sortRankHand(char[] handRanks) {
		int[] sortedHandRanks = new int[handRanks.length];
		
		// replace ten and face cards with ordinal numerics
		 for (int i = 0; i < handRanks.length; i++) {
			 		sortedHandRanks[i] = getOrdinalRank(handRanks[i]);
			 	}			 	
		 Arrays.sort(sortedHandRanks); 
		 return sortedHandRanks;
	 }	

	/** 
	 * Get the numeric value of Ten, Jack, Queen, King, or Ace.
	 * 
	 * @param rank		The char representing the rank (T, J, Q, K, A)
	 * @return			The int value representing the rank.
	 */
	private int getOrdinalRank(char rank) {
		if(rank == 'T') {
	 		return 10;
	 	}else if(rank == 'J'){
	 		return 11;
	 	}else if(rank == 'Q'){
	 		return 12;
	 	}else if(rank == 'K'){
	 		return 13;
	 	}else if(rank == 'A'){
	 		return 14;
	 	}else {
	 		return Character.getNumericValue(rank);
	 	}		
	}
	
	/** 
	 * Set high card property as a string (like Jack, Queen, King, Ace, 7).
	 * 
	 * @param sortedRanks		An integer array of ranks sorted least to greatest.
	 */
	private void setHighCard(int[] sortedRanks) {
		//sets high card as a string
		int maxRank = sortedRanks[0];
		String maxRankString = "";
		for(int i = 1; i < sortedRanks.length; i++) {
			if(sortedRanks[i] > maxRank) {
				maxRank = sortedRanks[i];
			}
		}		
		maxRankString = String.valueOf(maxRank);
		
		switch(maxRank){
		case 11:
			this.highCard = "Jack";
			return;
		case 12:
			this.highCard = "Queen";
			return;
		case 13:
			this.highCard = "King";
			return;
		case 14:
			this.highCard = "Ace";
			return;
		}		
		this.highCard = maxRankString;
	}
	
	/**
	 * Set the hand type property as an enum (like HIGH_CARD, STRAIGHT, and so forth.)
	 */
	public void setHandType() {
		this.playerHandType = HandType.HIGH_CARD;
		char[] cardRanks = new char[this.cardHand.size()];
		
		// organize hand - all suites together, cards in order		
		for(int i = 0; i< cardHand.size(); i++) {			
			Card aCard = cardHand.get(i);
			switch(Character.toLowerCase(aCard.getSuite())) {
				case 'h': 
					hCount++;
					break;
				case 'd':
					dCount++;
					break;
				case 'c':
					cCount++;
					break;
				case 's':
					sCount++;
					break;
			}			
			cardRanks[i] = aCard.getRank();	// array of (char) ranks	
		}	

		this.sortedRanks = this.sortRankHand(cardRanks); 
		this.setHighCard(sortedRanks);
		this.details = this.highCard;
		
		if(hasFlush() && hasStraight(sortedRanks)) { 
			this.playerHandType = HandType.STRAIGHT_FLUSH;
			this.setHighCard(sortedRanks);
			this.details = this.highCard;
		}else if(hasMultipleKinds(cardRanks, 4)) {
			this.playerHandType = HandType.FOUR_OF_KIND;
			this.details = String.valueOf(this.repeatingChar);
		}else if(this.hasMultipleKinds(cardRanks, 3)) {
			char[] remainingRanks = removeChars(cardRanks, 3);
			this.details = String.valueOf(this.repeatingChar);
			if(hasMultipleKinds(remainingRanks, 2)) {	
				this.details += " over " + String.valueOf(this.repeatingChar);
				this.playerHandType = HandType.FULL_HOUSE;
			}else {
				this.playerHandType = HandType.THREE_OF_KIND;
			} 		
		}else if(hasFlush()){
			this.playerHandType = HandType.FLUSH;
			this.setHighCard(sortedRanks);
			this.details = this.highCard;
		}else if(hasStraight(sortedRanks)) {
			this.playerHandType = HandType.STRAIGHT;
			this.setHighCard(sortedRanks);
			this.details = this.highCard;
		}else if(hasMultipleKinds(cardRanks, 2)) {
			char[] remainingRanks = removeChars(cardRanks, 2);
			this.details = String.valueOf(this.repeatingChar);
			if(hasMultipleKinds(remainingRanks, 2)) {
				this.details += " and " + String.valueOf(this.repeatingChar);
				this.playerHandType = HandType.TWO_PAIR;
			}else {
				this.playerHandType = HandType.PAIR;
			}
		}
	}
}
