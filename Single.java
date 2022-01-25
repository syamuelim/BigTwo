/**
 * Single is a subclass of the Hand class and are used to model a hand of Single
 * @author Yim Kwan Lok
 */


public class Single extends Hand
{
    /**
     * constructe the single object
     * @param player the player who use the hand single
     * @param cards the cardlist contains the card that the player want use
     */
    public Single(CardGamePlayer player, CardList cards)
    {
        super(player, cards);
    }

    /**
     * method for checking the combination of cards whether is valid for single
     * @return true if it contains only 1 card in the cardlist
     */
    public boolean isValid()
    {
        if(this.size() == 1)
        {
            return true;
        }
        return false;
    }

    /**
     * method for returning single type
     * @return name of this type of hand
     */
    public String getType()
    {
        return "Signle";
    }
}
