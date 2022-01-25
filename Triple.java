/**
 * Triple is a subclass of the Hand class and are used to model a hand of Triple
 * @author Yim Kwan Lok
 */

public class Triple extends Hand
{
    /**
     * constructe the Triple object
     * @param player the player who use the hand triple
     * @param cards the cardlist contains the card that the player want use
     */
    public Triple(CardGamePlayer player, CardList cards)
    {
        super(player, cards);
    }

    /**
     * method for checking the combination of cards whether is valid for triple
     * @return true if the combination is valid
     */
    public boolean isValid()
    {

        if (this.size() == 3)
        {
            if (this.getCard(0).rank == this.getCard(1).rank && this.getCard(0).rank == this.getCard(2).rank) // check every card has same rank
            {
                for (int i = 0; i < 3; i++)
                {
                    for (int j = i; j < 3; j++)
                    {
                        if (this.getCard(i) == this.getCard(j) && i != j) // check if the cards repeated
                        {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * method for returning triple type
     * @return name of this type of hand
     */
    public String getType()
    {
        return "Triple";
    }
    
}
