/**
 * StraightFlush is a subclass of the Hand class and are used to model a hand of StraightFlush
 * @author Yim Kwan Lok
 */

public class StraightFlush extends Hand
{
    /**
     * constructe the StraightFlush object
     * @param player the player who use the hand StraightFlush
     * @param cards teh cardlist contains the card that the player want use
     */
    public StraightFlush(CardGamePlayer player, CardList cards)
    {
        super(player, cards);
    }    

    /**
     * method for checking the combination of cards whether is valid for StraightFlush
     * @return true if the combination is valid
     */
    public boolean isValid()
    {
        this.sort();
        CardGamePlayer test = new CardGamePlayer();
        CardList cards1 = new CardList();
        for (int i = 0; i < 5; i++)
        {
            cards1.addCard( this.getCard(i));
        }
        cards1.sort();

        // the hand belongs to StraightFlush iff it satisfies the requirement of Straight and Flush
        Hand hand1 = new Straight(test, cards1);
        Hand hand2 = new Flush(test, cards1);

        if (hand1.isValid() == true && hand1.isValid() == hand2.isValid()) return true;
        return false;

    }


    /**
     * method for returning StraightFlush type
     * @return name of this type of hand
     */
    public String getType()
    {
        return "StraightFlush";
    }
        
}
