/**
 * Quad is a subclass of the Hand class and are used to model a hand of quad
 * @author Yim Kwan Lok
 */

public class Quad extends Hand
{
    /**
     * constructe the Quad object
     * @param player the player who use the hand quad
     * @param cards teh cardlist contains the card that the player want use
     */
    public Quad(CardGamePlayer player, CardList cards)
    {
        super(player, cards);
    }    


    private static int four;

    /**
     * a method for retrieving the top card of this hand.
     * @return the card in the cardlist that has biggest value
     */
    public Card getTopCard()
    {
        this.sort();
        if (this.getCard(4).rank == four) return this.getCard(4);
        return this.getCard(3);
    }

    /**
     * method for checking the combination of cards whether is valid for quad
     * @return true if the combination is valid
     */
    public boolean isValid()
    {
        this.sort();

        if (this.size() == 5)
        {
            if (this.getCard(0).rank == this.getCard(1).rank) //if first two card share same rank, the first four cards must have same rank
            {
                four = this.getCard(0).rank;
                for(int i = 0; i < 4; i++)
                {
                    if (four != this.getCard(i).rank) return false; // if first four cards do not have same rank, return false
                    if (this.getCard(4) == this.getCard(i)) return false;
                    for (int j = i; j < 4; j++)
                    {
                        if (this.getCard(i).suit == this.getCard(j).suit && i != j) return false; //check the repeated card
                    }
                }
            }
            else if (this.getCard(4).rank == this.getCard(3).rank) //if last two card share same rank, the last four cards must have same rank
            {
                four = this.getCard(4).rank;
                for(int i = 4; i > 0; i--)
                {
                    if (four != this.getCard(i).rank) return false;
                    if (this.getCard(0) == this.getCard(i)) return false;
                    for (int j = i; j > 1; j--)
                    {
                        if (this.getCard(i).suit == this.getCard(j).suit && i != j) 
                        {
                            return false;
                        }
                    }
                }
            }
            else return false;

        }
        return true;


    }
    /**
     * method for returning quad type
     * @return name of this type of hand
     */
    public String getType()
    {
        return "Quad";
    }
        
}
