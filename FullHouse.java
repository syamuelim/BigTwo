
/**
 * Full of House is a subclass of the Hand class and are used to model a hand of Full of house
 * @author Yim Kwan Lok
 */

public class FullHouse extends Hand
{
    /**
     * constructe the full of house object
     * @param player the player who use the hand full of house
     * @param cards the cardlist contains the card that the player want use
     */


    public FullHouse(CardGamePlayer player, CardList cards)
    {
        super(player, cards);
    }    
  

    private static int rank1 = 0, rank2 = 0; 

    /**
     * a method for retrieving the top card of this hand.
     * @return the card in the cardlist that has biggest value
     */
    public Card getTopCard()
    {
        this.sort();
        rank1 = 0; rank2 = 0;
        isValid();
        if (rank1 == 3) return this.getCard(2);
        return this.getCard(4);
    }

    /**
     * method for checking the combination of cards whether is valid for full of house
     * @return true if the combination is valid
     */
    public boolean isValid()
    {
        if (this.size() == 5)
        {
            this.sort();
            rank1 = 0; rank2 = 0;
            int firstrank = this.getCard(0).rank, lastRank = this.getCard(4).rank;
            
            for (int i = 0; i < 5; i ++)
            {
                // to find the 3a2b or 2a3b combination belongs to the cardlist
                if (this.getCard(i).rank == firstrank)
                {
                    for (int j = i; j < 5; j++)
                    {
                        if (this.getCard(j).rank != firstrank)
                        {
                            break;
                        }
                        if (this.getCard(i).suit == this.getCard(j).suit && i != j) // check repeated card
                        {
                            return false; 
                        }
                    }
                    rank1 ++;
                }
                else if (this.getCard(i).rank == lastRank)
                {
                    for (int j = i; j < 5; j++)
                    {
                        if (this.getCard(j).rank != lastRank)
                        {
                            break;
                        }
                        if (this.getCard(i).suit == this.getCard(j).suit && i != j)
                        {
                    
                            return false;
                            
                        }
                    }
                    rank2 ++;
                }
            }

            if (rank1 + rank2 == 5)
            {
                return true;
            }
        }
        
        return false;
    }
    /**
     * method for returning Full of House type
     * @return name of this type of hand
     */
    public String getType()
    {
        return "FullHouse";
    }

}
