/**
 * The BigTwoDeck class is a subclass of the Deck class and is used to model a deck of cards
 * used in a Big Two card game
 * @author Yim Kwan Lok
 */
public class BigTwoDeck extends Deck 
{
    /**
     * a method for initializing a deck of Big Two cards.
     */
    public void initialize()
    {

        this.removeAllCards();
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                Card card = new BigTwoCard(i,j);
                this.addCard(card);
            }
        }
    }
}
