/**
 * Straight is a subclass of the Hand class and are used to model a hand of straight
 * @author Yim Kwan Lok
 */

public class Straight extends Hand
{

    /**
     * constructe the Straight object
     * @param player the player who use the hand Straight
     * @param cards teh cardlist contains the card that the player want use
     */
    public Straight(CardGamePlayer player, CardList cards)
    {
        super(player, cards);
    }    


    /**
     * method for checking the combination of cards whether is valid for Straight
     * @return true if the combination is valid
     */
    public boolean isValid()
    {
        this.sort();

        int first = this.getCard(0).rank + 1;
        
        if (first > 13) first -= 13; // adjust the order since A and 2 is greater than K
        if (this.size() == 5)
        {
            for (int i = 1; i < 5; i++)
            {
                int temp = this.getCard(i).rank + 1;
                if (temp > 13) temp -= 13; // same as first
                if (first + 1 != temp)
                {
                    return false; // if the card in cardlist is not continuous. return false
                }

                first += 1;
                if (first > 12)
                {
                    first = 0;
                }

                for (int j = i; j < 5; j++)
                {
                    if (this.getCard(i) == this.getCard(j) && i != j) // check the repeated card
                    {
                        return false;
                    }
                }
            }

        }
        else return false;
        return true;

    }

    /**
     * method for returning Straight type
     * @return name of this type of hand
     */
    public String getType()
    {
        return "Straight";
    }
}
