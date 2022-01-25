/**
 * Pair is a subclass of the Hand class and are used to model a hand of Pair
 * @author Yim Kwan Lok
 */

public class Pair extends Hand
{
    /**
     * constructe the Pair object
     * @param player the player who use the hand Pair
     * @param cards the cardlist contains the card that the player want use
     */
    public Pair(CardGamePlayer player, CardList cards)
    {
        super(player, cards);
    }

    /**
     * method for checking the combination of cards whether is valid for pair
     * @return true if the combination is valid
     */
    public boolean isValid()
    {
        if (this.size() == 2)
        {
            if (this.getCard(0).rank == this.getCard(1).rank && this.getCard(0).suit != this.getCard(1).suit)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * method for returning Pair type
     * @return name of this type of hand
     */
    public String getType()
    {
        return "Pair";
    }
    
}
