/**
 * The BigTwoCard class is a subclass of the Card class and is used to model a card used in a
 * Big Two card game.
 * @author Yim Kwan Lok
 */

public class BigTwoCard extends Card 
{   

    /**
     * a constructor for building a card with the specified suit and rank.
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public BigTwoCard(int suit, int rank)
    {
        super(suit,rank);

    }

    /**
     * a method for comparing the order of this card with the specified card.
     * @param card the card will be compare to the current card 
     * @return 1 if the current card is bigger then the inputed card
     */
    public int compareTo(Card card)
    {
        if (this.rank > card.rank && this.rank != 0 && this.rank != 1 && card.rank != 0 && card.rank != 1)
        {
            return 1;
        }
        else if (this.rank == 1 && card.rank != 1)
        {
            return 1;
        }
        else if (this.rank == 0 && card.rank != 1 && card.rank != 0)
        {
            return 1;
        }
        else if (this.rank == card.rank)
        {
            if (this.suit > card.suit) return 1;
            else if ( this.suit == card.suit) return 0;
            else return -1;
        }
        else return -1;
    }
    
}
