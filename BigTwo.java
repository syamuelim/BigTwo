import java.util.ArrayList;
/**
 * The BigTwo class implements the CardGame interface and is used to model a Big Two card game.
 */

public class BigTwo implements CardGame{


    private int numOfPlayers;
    private Deck deck = new BigTwoDeck();
    private ArrayList<CardGamePlayer> playerList = new ArrayList<CardGamePlayer>();
    private ArrayList<Hand> handsOnTable = new ArrayList<Hand>();
    private int currentPlayerIdx;
    private BigTwoGUI gui;
    private int passedTurn = 0;


    /**
     * anything is true if the last three players have passed the turn.
     */
    public static boolean anything = false;

    /**
     * constructor of class BigTwo
     * it is designed to create the player and the user interface
     */
    public BigTwo()
    {
        
        this.deck.shuffle();
        this.numOfPlayers = 4;
    
        for(int i = 0; i < numOfPlayers; i++)
        {
            CardGamePlayer cardGamePlayer = new CardGamePlayer();
            playerList.add(cardGamePlayer);
        }

        this.deck.shuffle();

    }


    /**
     * method for obtaining the number of players
     * @return number of players
     */
    public int getNumOfPlayers()
    {
        return numOfPlayers;
    }

    public void setCurrentPlayer(int PlayerIdx)
    {
        currentPlayerIdx = PlayerIdx;
    }

    /**
     * method for obtaining the deck of cards
     * @return the deck of cards
     */
    public Deck getDeck()
    {
        return deck;
    }

    /**
     * method for setting the deck of cards
     */

    public void setDeck()
    {
        this.deck = new BigTwoDeck();
        deck.shuffle();
    }

    /**
     * method for obtaining the list of players.
     * @return the list of players.
     */
    public ArrayList<CardGamePlayer> getPlayerList()
    {
        return playerList;
    }

    /**
     * method for the list of hands played on the table.
     * @return the list of hands played on the table.
     */
    public ArrayList<Hand> getHandsOnTable()
    {
        return handsOnTable;
    }

    /**
     * method for obtaining the index of current player
     * @return the index of current player
     */
    public int getCurrentPlayerIdx()
    {
        return currentPlayerIdx;
    }

    /**
     * a method for starting/restarting the game with a given
     * shuffled deck of cards.
     * @param deck the shuffled deck
     */
    public void start(Deck deck)
    {
        // remove the cards from hands and table
        Card target = new Card(0, 2);

        int num = 0;
        for(int i = 0; i < 4; i++)
        {   
            playerList.get(i).removeAllCards();
            CardList PlayerHand = new CardList();
            for (int j = 0; j < 13; j++)
            {
                // distributing the cards
                
                PlayerHand.addCard(deck.getCard(num));
                num++;
                if( num < 52 && deck.getCard(num).getRank() == target.getRank() && deck.getCard(num).getSuit() == target.getSuit()) // finding the players who has three of Diamonds
                {
                    // setting the current player
                    currentPlayerIdx = i;
                    
                }

            }
            PlayerHand.sort();
            for (int j = 0; j < 13; j++)
            {
                playerList.get(i).addCard(PlayerHand.getCard(j));
            }

        }

        //gui.setPlayerList(getPlayerList());
        gui = new BigTwoGUI(this);
        gui.setActivePlayer(currentPlayerIdx);
        gui.printMsg("Player " + gui.activePlayer + "'s turn:\n");

        

    }

    /**
     * a method for making a move by a
     * player with the specified index using the cards specified by the list of indices
     * @param playerIdx the player would like to make a move
     * @param cardIdx the combination that the player would like to use
     */
    public void makeMove(int playerIdx, int[] cardIdx)
    {
        if (endOfGame() == false) checkMove(playerIdx, cardIdx);
    }


    /**
     * a method for checking the move is valid to use
     * @param playerIdx the player would like to make a move
     * @param cardIdx the combination that the player would like to use
     */
    public void checkMove(int playerIdx, int[] cardIdx)
    {
        Card target = new BigTwoCard(0,2);
        if (endOfGame() == true) return;
        // checking the validation of the first move
        if (playerList.get(playerIdx).getCardsInHand().getCard(0).getRank() == target.getRank() && playerList.get(playerIdx).getCardsInHand().getCard(0).getSuit() == target.getSuit())
        {
            // if the combination does not contain diamond of 3, the move is illegal
            if (cardIdx == null ||cardIdx[0] != 0 ) 
            {   
                gui.printMsg("Not a legal move!!!");
                gui.success = false;
                return;
            }
            else
            {
                // composing the hand
                CardList cards = new CardList();
                for (int i = 0; i < cardIdx.length; i++)
                {
                    Card card = playerList.get(playerIdx).getCardsInHand().getCard(cardIdx[i]);
                    cards.addCard(card);
                }
                Hand currentHand = composeHand(playerList.get(playerIdx), cards); 

                // the first player is prohibit to pass the turn
                if (currentHand == null) 
                {
                    gui.printMsg("Not a legal move!!!");
                    gui.success = false;
                    return;
                    
                }
                

                handsOnTable.add(currentHand);
                currentPlayerIdx ++;
                if (currentPlayerIdx == 4) currentPlayerIdx = 0;
                gui.setActivePlayer(currentPlayerIdx);
                for (int j = 0; j < cardIdx.length; j++)
                {
                    playerList.get(playerIdx).getCardsInHand().removeCard(cardIdx[j] - j); // reduce the hand on table for boosting the programme efficiency
                }

                gui.success = true;
                
            }
        }
        // if the player want to pass the turn
        else if(cardIdx == null && anything == false)
        {
            passedTurn++;
            
            if (passedTurn == 3)
            {
                anything = true;
            }
            currentPlayerIdx ++;
            if (currentPlayerIdx == 4) currentPlayerIdx = 0;
            gui.setActivePlayer(currentPlayerIdx);
            if(endOfGame() == false)
            {
                gui.printMsg("{Pass}\n");

               
            }
            else return;
        
        }
        // if the last three players have passed the turn, the current player is allowed to make any valid combination without beating the last valid hand on table
        else if (anything == true && endOfGame() == false)
        {
            passedTurn = 0;
            if (cardIdx == null)
            {
                gui.printMsg("Not a legal move!!!");
                gui.success = false;
                return;
                
            }
            CardList cards = new CardList();
            
            for (int i = 0; i < cardIdx.length; i++)
            {
                Card card = playerList.get(playerIdx).getCardsInHand().getCard(cardIdx[i]);
                cards.addCard(card);
            }
            Hand currentHand = composeHand(playerList.get(playerIdx), cards);
            if (currentHand == null) 
            {
                gui.printMsg("Not a legal move!!!");
                gui.success = false;
                return;
               
            }

            handsOnTable.add(currentHand);
            currentPlayerIdx ++;
            if (currentPlayerIdx == 4) currentPlayerIdx = 0;

            gui.setActivePlayer(currentPlayerIdx);
            for (int j = 0; j < cardIdx.length; j++)
            {
                playerList.get(playerIdx).getCardsInHand().removeCard(cardIdx[j] - j);
            }

            if(handsOnTable.size() > 4)
            {
                handsOnTable.remove(0);
            }
            anything = false;
            if (endOfGame() == false)
            {   
                gui.success = true;
               
            }
            else return;

            
        }
        else
        {
            passedTurn = 0;
            CardList cards = new CardList();
            
            for (int i = 0; i < cardIdx.length; i++)
            {
                Card card = playerList.get(playerIdx).getCardsInHand().getCard(cardIdx[i]);
                cards.addCard(card);
            }

            Hand currentHand = composeHand(playerList.get(playerIdx), cards);
            if (currentHand == null) 
            {
                gui.printMsg("Not a legal move!!!");
                gui.success = false;
                return;
               
            }



            int lastHand = handsOnTable.size() - 1;
            for (int i = lastHand; i >=0; i--)
            {
                if (handsOnTable.get(lastHand) == null)
                {
                    lastHand --;
                }
                
            }

            if (currentHand.beats(handsOnTable.get(lastHand)) == true) 
            {
                handsOnTable.add(currentHand);
                for (int j = 0; j < cardIdx.length; j++)
                {
                    playerList.get(playerIdx).getCardsInHand().removeCard(cardIdx[j] - j);
                }

                currentPlayerIdx ++;
                if (currentPlayerIdx == 4) currentPlayerIdx = 0;

                gui.setActivePlayer(currentPlayerIdx);


                if(handsOnTable.size() > 4)
                {
                    handsOnTable.remove(0);
                }
                if (endOfGame() == false)
                {
                    gui.success = true;
                    
                }
                else return;
            }
            else 
            {
                gui.printMsg("Not a legal move!!!");
                gui.success = false;
                return;
                
            }
        }

    }

    /**
     * a method for checking the game is ended
     * @return true if the game ended
     */
    public boolean endOfGame()
    {
        for (int i = 0; i < 4; i++)
        {
            if(playerList.get(i).getCardsInHand().size() == 0)
            {
                gui.repaint();
                gui.printMsg("\n" +  "Game ends\n");
                for (int j = 0; j < 4; j++)
                {
                    if (j != i)
                    {
                        gui.printMsg("Player " + j + " has " + playerList.get(j).getNumOfCards() + " cards in hand.\n");
                    }
                    else
                    {
                        gui.printMsg("Player " + i + " wins the game.\n");
                    }
                }
                gui.printMsg("You can restart a new game or quit\n");
                gui.disable();
                return true; 
            }
        }
        return false;
    }


    /**
     *  a method for starting a Big Two card game.
     *  @param args
     */
    public static void main(String[] args)
    {
        BigTwo game = new BigTwo();
        game.start(game.deck);
    }

    /**
     * a method for returning a valid hand from the specified list of cards of the player
     * @param player the player that made the combination
     * @param cards the combination that may need to be composed
     * @return the valid hand
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards)
    {
        if (cards.size() == 5)
        {
            Hand SFHand = new StraightFlush(player, cards);
            if (SFHand.isValid() == true) return SFHand;

            Hand QHand = new Quad(player, cards);
            if (QHand.isValid() == true) return QHand;

            Hand FHHand = new FullHouse(player, cards);
            if (FHHand.isValid() == true) return FHHand;

            Hand FHand = new Flush(player, cards);
            if (FHand.isValid() == true) return FHand;

            Hand SHand = new Straight(player, cards);
            if (SHand.isValid() == true) return SHand;
            
        }
        else if (cards.size() == 3)
        {
            Hand THand = new Triple(player, cards);
            if (THand.isValid() == true) return THand;
        }
        else if (cards.size() == 2)
        {
            Hand PHand = new Pair(player, cards);
            if (PHand.isValid() == true) return PHand;
        }
        else if (cards.size() == 1)
        {
            Hand Single = new Single(player, cards);
            if (Single.isValid() == true) return Single;
        }
        return null;
    
    }
    
}
