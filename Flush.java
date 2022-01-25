/**
 * Flush is a subclass of the Hand class and are used to model a hand of flush
 * @author Yim Kwan Lok
 */

public class Flush extends Hand
{
    /**
     * constructe the Flush object
     * @param player the player who use the hand flush
     * @param cards teh cardlist contains the card that the player want use
     */
    public Flush(CardGamePlayer player, CardList cards)
    {
        super(player, cards);
    }    
    
    /**
     * method for checking the combination of cards whether is valid for flush
     * @return true if the combination is valid
     */
    public boolean isValid()
    {
        if (this.size() == 5)
        {

            for (int i = 0; i < 5; i++)
            {
                for (int j = i; j < 5; j++)
                {
                    if (this.getCard(i).rank == this.getCard(j).rank && i != j)
                    {
                        return false;
                    }
                    if (this.getCard(i).suit != this.getCard(j).suit && i != j)
                    {
                        return false;
                    }
                }
            }
            return true;
            
        }
        return false;
    }
    /**
     * method for returning flush type
     * @return name of this type of hand
     */
    public String getType()
    {
        return "Flush";
    }

}
