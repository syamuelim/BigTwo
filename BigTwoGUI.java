import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class BigTwoGUI implements CardGameUI
{
    private BigTwo game;
    /**
     * activePlayer is the player that currently making move
     */
    public int activePlayer;
    /**
     * success is true when the move is correct, passed represent the turn is passed
     */
    public boolean success = false, passed = false;
    private JFrame frame = new JFrame();
    private JButton playButton = new JButton();
    private JButton passButton = new JButton();
    private ArrayList<CardLabel> cardLabel = new ArrayList<CardLabel>();
    private ArrayList<JLabel> faceDown = new ArrayList<JLabel>(); // store the back of the card
    private JTextArea msgArea = new JTextArea(25, 40);
    private JTextArea chatArea = new JTextArea(25, 41);
    private JTextField chatInput = new JTextField(30);
    private ArrayList<Integer> selectedCard = new ArrayList<Integer>();  // store the index of selected cards
    private BigTwoPanel bigTwoPanel0;
    private BigTwoPanel bigTwoPanel1;
    private BigTwoPanel bigTwoPanel2;
    private BigTwoPanel bigTwoPanel3;
    private tablePanel tablJPanel;
    private JPanel textPanel;
    private int currentActivePanel; // basically it is a last player's game panel
    
    /**
     * contruct the BigTwo game GUI
     * @param game  The parameter game is a reference to a Big Two card game associates with this GUI.
     */
    public BigTwoGUI(BigTwo game)
    {
        this.game = game;
        cardInitializer();
        frameSetter();
        playButton.addActionListener(new PlayButtonListener());
        passButton.addActionListener(new PassButtonListener());
        
    }

    /**
     * method for setting the active player
     * @param activePlayer an integer specifying the index of the active player
     * @return the seted active player
     */
    public void setActivePlayer(int activePlayer)
    {
        if (activePlayer == 4)  this.activePlayer = 0;
        this.activePlayer = activePlayer;
    }
    
    /**
     * a method for repainting the GUI. 
     */
    public void repaint()
    {
        printMsg("{" + game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getType() + "} ");
        for (int i = 0; i < selectedCard.size(); i++)
        {
            if (currentActivePanel == 0)
            {
                printMsg( "[" + bigTwoPanel0.playerHand.get(selectedCard.get(i)).cardFace+ "] ");
            }
            if (currentActivePanel == 1)
            {
                printMsg( "[" + bigTwoPanel1.playerHand.get(selectedCard.get(i)).cardFace+ "] ");
            }
            if (currentActivePanel == 2)
            {
                printMsg( "[" + bigTwoPanel2.playerHand.get(selectedCard.get(i)).cardFace+ "] ");
            }
            if (currentActivePanel == 3)
            {
                printMsg( "[" + bigTwoPanel3.playerHand.get(selectedCard.get(i)).cardFace+ "] ");
            }
           
        }
        printMsg("\n");
        frame.repaint();
        printMsg("Player " + activePlayer + "'s turn:\n");
    }

    /**
	 * Resets the card game user interface.
	 */
	public void reset()
    {
        selectedCard = new ArrayList<Integer>();
        clearMsgArea();
        enable();

    }

    public void clearMsgArea()
    {
        msgArea.setText(null);
        chatArea.setText(null);
    }

	/**
	 * Enables user interactions.
	 */
	public void enable()
    {
        playButton.setEnabled(true);
        passButton.setEnabled(true);
        chatInput.setEnabled(true);
        for (int i = 0; i < cardLabel.size(); i++)
        {
            cardLabel.get(i).clickable = true;
        }
    }

    /**
     * a method for prompting the active player to select cards and make his/her move. 
     */
    public void promptActivePlayer()
    {
        if(selectedCard.size() == 0) return;
        int[] cardIdx = new int[selectedCard.size()];
        for (int i = 0; i < selectedCard.size(); i++)
        {
            cardIdx[i] = selectedCard.get(i);
        }
        Arrays.sort(cardIdx);
		game.makeMove(activePlayer, cardIdx);
        if (success == true)
        {
            
            repaint();
            selectedCard = new ArrayList<Integer>();
            success = false;

        }
        selectedCard = new ArrayList<Integer>();
        frame.repaint();
        frame.getContentPane().revalidate();
        frame.repaint();
        
        
       
    }

    /**
     * a method for printing the specified string to the message area of the GUI. 
     * @param msg the string that will append on the message area
     */
    public void printMsg(String msg)
    {
        msgArea.append(msg);
    }

    /**
     * a method for enabling user interactions with the GUI
     */
    public void disable()
    {
        playButton.setEnabled(false);
        passButton.setEnabled(false);
        chatInput.setEnabled(false);
        for (int i = 0; i < cardLabel.size(); i++)
        {
            cardLabel.get(i).clickable = false;
        }
    }

    // initializing the text box
    private void textBoxinitializer(JPanel panel)
    {
        

        // basic setting for the text area
        msgArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(msgArea);
        msgArea.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatArea.setEditable(false);
        chatArea.setForeground(new Color(0,0,204));
        chatInput.addActionListener(new chatKeyListener());
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = 0;
        c.fill = GridBagConstraints.VERTICAL;
	    panel.add(scrollPane, c);
        c.gridy = 1;
        
        JScrollPane scrollPane1 = new JScrollPane(chatArea);
        chatArea.setLineWrap(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        c.gridy = 2;
        panel.add(scrollPane1, c);

        c.gridy = 3;
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Message: "));
        panel2.add(chatInput);
        panel.add(panel2, c);
        printMsg("[] represent the card of the hand on table\n");
        printMsg("first symbol is the rank of the card, a is Ace, t is ten\n");
        printMsg("second symbol is the suit of the card,\n");
        printMsg("d is diamonds, h is hearts, c is clubs, s is spades\n");
   

    }
    
    // initializing the menu bar
    private void menuBarinitializer()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenu message = new JMenu("Message");
        JMenuItem menuItem1 = new JMenuItem("Restart");
        JMenuItem menuItem2 = new JMenuItem("Quit");
        JMenuItem menuItem3 = new JMenuItem("clear message");
        menuItem1.addActionListener(new RestartMenuItemListener());
        menuItem2.addActionListener(new QuitMenuItemListener());
        menuItem3.addActionListener(new ClearMsgListener());
        menu.add(menuItem1);
        menu.add(menuItem2);
        message.add(menuItem3);
        menuBar.add(menu);
        menuBar.add(message);
        frame.setJMenuBar(menuBar);
    }
    
    // initializing the card face
    private void cardInitializer()
    {
        for (int i = 0; i < 4; i ++)
        {
            for (int j = 0; j < 13; j++)
            {
                cardLabel.add(new CardLabel(i,j));
            }
        }
        ImageIcon image1 = new ImageIcon("./src/cards/b.gif");
        JLabel faceDownLabel = new JLabel(image1);
        for (int i = 0; i < 52; i++)
        {
            faceDown.add(faceDownLabel);
        }
    }

    // initializing the game panel
    private JPanel gamePanelinitializater()
    {
        JPanel interfacePanel = new JPanel();
        interfacePanel.setLayout(new BoxLayout(interfacePanel, BoxLayout.Y_AXIS));
        interfacePanel.setPreferredSize(new Dimension(900, 1039));
        interfacePanel.setMinimumSize(new Dimension(900, 1039));
        interfacePanel.setMaximumSize(new Dimension(900, 1039));
        interfacePanel.setBackground(new Color(0, 153, 0));
        bigTwoPanel0= new BigTwoPanel(0);
        bigTwoPanel1 = new BigTwoPanel(1);
        bigTwoPanel2 = new BigTwoPanel(2);
        bigTwoPanel3 = new BigTwoPanel(3);
        tablJPanel = new tablePanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton);
        buttonPanel.add(passButton);
        interfacePanel.add(bigTwoPanel0);
        interfacePanel.add(bigTwoPanel1);
        interfacePanel.add(bigTwoPanel2);
        interfacePanel.add(bigTwoPanel3);
        interfacePanel.add(tablJPanel);
        interfacePanel.add(buttonPanel);

        
        return interfacePanel;
    }

    // initializing the frame
    private void frameSetter()
    {
        frame.setPreferredSize(new Dimension(1378, 1039));
        frame.setMinimumSize(new Dimension(1378,1000));
        frame.setMaximumSize(new Dimension(2000, 2000));
      
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuBarinitializer();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        textPanel = new JPanel();
        textBoxinitializer(textPanel);
        
        panel.add(gamePanelinitializater());
        panel.add(textPanel);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

    // class to modelify the card face
    private class CardLabel extends JLabel implements MouseListener
    {
        private boolean selected = false;
        private JLabel down = new JLabel();
        private Card card;
        private CardLabel label;
        private int playerIdx;
        private int slot;
        private String cardFace = "";
        private boolean clickable = true;
        /**
         * contrustor for creating the cardLabel 
         * @param suit the suit of the card
         * @param rank the rank of the card
         */
        public CardLabel(int suit, int rank)
        {
            
            card = new Card(suit, rank);
            
            label = this;
            if (card.rank == 10) cardFace += "j";
            else if (card.rank == 11) cardFace += "q";
            else if (card.rank == 12) cardFace += "k";
            else if (card.rank == 0) cardFace += "a";
            else if (card.rank == 9) cardFace += "t";
            else cardFace +=  (card.rank + 1);
            
            if (card.suit == 0) cardFace += "d";
            else if (card.suit == 1) cardFace += "c";
            else if (card.suit == 2) cardFace += "h";
            else if (card.suit == 3) cardFace += "s";
            ImageIcon image = new ImageIcon("./src/cards/"+ cardFace+ ".gif");
            this.addMouseListener( this);
            this.setIcon(image);
            ImageIcon image2 = new ImageIcon("./src/cards/b.gif");
            down.setIcon(image2);
        }

        private JLabel faceDownLabel()
        {
            return down;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            
        }
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            
        }
        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            
        }
        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
            if (selected == false && clickable == true)
            {
                this.setLocation(this.getX(), this.getY() - 20);
                selected = true;
                selectedCard.add(slot);
                frame.getContentPane().revalidate();
            }
            else if (selected == true && clickable == true)
            {
                this.setLocation(this.getX(), this.getY() + 20);
                selected = false;
                if (selectedCard.size() > 0)selectedCard.remove(searchSelectedSlot(slot));
                frame.getContentPane().revalidate();
            }
            
            
        }
        // to remove the card from the selected card list
        private int searchSelectedSlot(int target)
        {
            int idx = -1;
            for (int i = 0; i < selectedCard.size(); i++)
            {
                if(selectedCard.get(i) == target) idx = i;
            }
            return idx;
        }

        // set the idx of the player hand
        private void setSlot(int slot, int idx)
        {
            this.slot = slot;
            this.playerIdx = idx;
        }
        

    }

    // class to model the table of the bigTwo game
    private class tablePanel extends JPanel
    {
        private JLabel name = new JLabel();
        public tablePanel()
        {
            this.setPreferredSize(new Dimension(1000, 300));
            this.setMinimumSize(new Dimension(1000, 300));
            this.setMaximumSize(new Dimension(1000, 300));
            this.setBackground(new Color(0, 153, 0));
            this.setLayout(new BorderLayout());
            playButton.setText("Play");
            passButton.setText("Pass");

        }
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            removeAll();
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
        
            c.gridx = 0;
            c.gridy = 0;

            if (game.getHandsOnTable().size() != 0)
            {
                String nameStr = "Played by player" + currentActivePanel;
                name.setText(nameStr);
                name.setLocation(108,22);
                this.add(name,c);
                for (int i = 0; i < game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).size(); i++)
                {
                    Card card = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getCard(i);
                    this.add(cardLabel.get(searchCard(card)));
                    c.gridx++;
                }
            }
            frame.getContentPane().revalidate();

        }
        private int searchCard(Card card)
        {
            int idx = -1;
            for (int i = 0; i < 52; i++)
            {
                if (card.rank == cardLabel.get(i).card.rank && card.suit == cardLabel.get(i).card.suit)
                {
                    idx = i;
                    break;
                }
            }
            return idx;
        }

    }

    // class to model the BigTwoPanel which will display the player avater, name and hand
    private class BigTwoPanel extends JPanel 
    {
        private ArrayList<CardLabel> playerHand = new ArrayList<CardLabel>();
        private int playerIdx;
        private JLabel nameJLabel, You;
        private JLabel iconLabel;
        private int  start = 15;

        public BigTwoPanel(int playerIdx)
        {
            this.playerIdx = playerIdx;
            this.setPreferredSize(new Dimension(1000, 160));
            this.setMinimumSize(new Dimension(1000, 160));
            this.setMaximumSize(new Dimension(1000, 160));
            this.setBackground(new Color(0, 153, 0));
            
            //setup icon and name
            nameJLabel = new JLabel("player " + playerIdx);
            ImageIcon image = new ImageIcon("./src/cards/"+ playerIdx + ".png");
            iconLabel = new JLabel();
            iconLabel.setIcon(image);
            iconLabel.setMinimumSize(new Dimension(73, 97));
            iconLabel.setPreferredSize(new Dimension(73, 97));
            iconLabel.setMaximumSize(new Dimension(73, 97));
            You = new JLabel("You");
            You.setForeground(new Color(0,0,204));


        }
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            this.removeAll();

            playerHand = new ArrayList<CardLabel>();
            for (int i = 0; i < game.getPlayerList().get(playerIdx).getNumOfCards(); i++)
            {
                Card card = game.getPlayerList().get(playerIdx).getCardsInHand().getCard(i);
                playerHand.add(cardLabel.get(searchCard(card)));
                cardLabel.get(searchCard(card)).setSlot(i, playerIdx);
            }
            
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0,-40,0,55);
            nameJLabel.setLocation(100, 22);
            You.setLocation(100, 22);
            iconLabel.setLocation(108, 40);
            if(activePlayer == playerIdx) 
            {
                this.add(You,c);
            }
            else this.add(nameJLabel,c);
            
            c.gridy++;
            
            this.add(iconLabel, c);

            c.insets = new Insets(0,0,0,-30);
            c.gridx = start;
            for (int i = playerHand.size() - 1; i >= 0; i--)
            {
                if (playerHand.get(i).selected == true)
                {
                    playerHand.get(i).setLocation(playerHand.get(i).getX(), playerHand.get(i).getY() -20);
                }
                if(activePlayer == playerIdx) 
                {
                    this.add(playerHand.get(i),c);
                    currentActivePanel = playerIdx;
                }
                else 
                {
                    this.add(playerHand.get(i).faceDownLabel(),c);
                    
                }
                c.gridx--;
            }
            frame.getContentPane().revalidate();
        }
        // search the card idx from the cardLabel 
        private int searchCard(Card card)
        {
            int idx = -1;
            for (int i = 0; i < 52; i++)
            {
                if (card.rank == cardLabel.get(i).card.rank && card.suit == cardLabel.get(i).card.suit)
                {
                    idx = i;
                    break;
                }
            }
            return idx;
        }
    }

    private class PlayButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
             promptActivePlayer();
        }
    }

    private class PassButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {

            game.makeMove(activePlayer, null);
            frame.repaint();
        }
    }

    private class RestartMenuItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            frame.removeAll();
            frame.setVisible(false);
            game = new BigTwo();
            game.setDeck();
            game.start(game.getDeck());
        }
    }

    private class QuitMenuItemListener implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    }

    private class ClearMsgListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            
            clearMsgArea();
        }
    }

    private class chatKeyListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            String text = chatInput.getText();
            chatArea.append("Player " + game.getCurrentPlayerIdx() + ": " + text + "\n");
            chatInput.setText("");

        }
    }


}