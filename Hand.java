/**
 * the superclass to medel all hand in BigTwo game
 * @author Yim Kwan Lok
 */

public abstract class Hand extends CardList
{
    private CardGamePlayer player;

    /**
     * a constructor for building a hand with the specified player and list of cards.
     * @param player the player who use the current combination 
     * @param cards the cardlist contains the cards that the player want use
     */
    public Hand(CardGamePlayer player, CardList cards)
    {
        this.player = player;
        for(int i = 0; i < cards.size(); i++)
        {
            this.addCard(cards.getCard(i));
        }

    }

    /**
     * a method for retrieving the player of this hand.
     * @return the current player who are making move
     */
    public CardGamePlayer getPlayer()
    {
        return player;
    }


    /**
     * a method for retrieving the top card of this hand.
     * @return the card in the cardlist that has biggest value
     */
    public Card getTopCard()
    {
        this.sort();
        return this.getCard(this.size() - 1);
    }

    /**
     *  a method for checking if this hand beats a specified hand.
     */
    public boolean beats(Hand hand)
    {
        
        int scoreThis = 1, scoreHand = 1;
        
        // quantify the hand, the hand with higher score can beat the hand with lower score
        if (this.size() != hand.size()) return false;
        if (this.getType() == "StraightFlush")  scoreThis = 8;
        else if (this.getType() == "Quad" )  scoreThis = 7;
        else if (this.getType() == "FullHouse" ) scoreThis = 6;
        else if (this.getType() == "Flush") scoreThis = 5;
        else if (this.getType() == "Straight") scoreThis = 4;
        else if (this.getType() == "Triple") scoreThis = 3;
        else if (this.getType() == "Pair" ) scoreThis = 2;

        if (hand.getType() == "StraightFlush")  scoreHand = 8;
        else if (hand.getType() == "Quad" )  scoreHand = 7;
        else if (hand.getType() == "FullHouse" ) scoreHand = 6;
        else if (hand.getType() == "Flush") scoreHand = 5;
        else if (hand.getType() == "Straight") scoreHand = 4;
        else if (hand.getType() == "Triple") scoreHand = 3;
        else if (hand.getType() == "Pair" ) scoreHand = 2;


        // if two hand are same, the hand has higher top card may win
        if (this.getType() == hand.getType())
        {
            if (scoreThis == 8 || scoreThis == 5)
            {
                if (this.getCard(0).suit > hand.getCard(0).suit) return true;
                else if(this.getCard(0).suit == hand.getCard(0).suit && this.getTopCard().compareTo(hand.getTopCard()) == 1) return true;
                else return false;
            }
            else if(this.getTopCard().compareTo(hand.getTopCard()) == 1) return true;
            else return false;
        }
        else
        {
            if (scoreThis > scoreHand) return true;
            else return false;
        }

    }

    /**
     * a method for checking if this is a valid hand.
     * @return true if the hand is valid
     */
    public abstract boolean isValid();

    /**
     * a method for returning a string specifying the type of this hand.
     * @return the name of the hand type
     */
    public abstract String getType();

    
}
