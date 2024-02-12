//there is a .doclick() method from the jbottoms class which can perform an action indipendently, the problem is i dont know where to place it
//i have to fix the delay of printing the values and add more ways for an ai to move 
// i dont remember what else to add but it has to do whith the ai -there are special moves to add

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class TicTacToeGUI {
    private char[][] arr;
    private String player, AI;
    private JFrame frame;
    private JButton[][] buttons;
    private boolean isPlayerTurn = true;
    // private int row, col;
    private boolean mult = false;
    // private boolean gameOn = true;
    private String[] winningArr = new String[8];
    private Map<Integer, String> key = new HashMap<>();
    private AI ai;
    private boolean isFstMove = false;
    private Players[] playxo = Players.values();
    private final String stMove = playxo[0].toString(), ndMove = playxo[1].toString();

    private static JPanel mainPanel;
    private static RoundedButton currentPage;

    TicTacToeGUI() {
        ai = new AI();
        setArr();
        start();
    }

    // Initialize the game board
    private void setArr() {
        arr = new char[3][3];
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[row].length; col++) {
                arr[row][col] = ' ';
            }
        }
        setKey();
    }

    // Create the game GUI
    private void createGUI(JFrame frame) {
        mainPanel.removeAll();
        this.frame = frame;
        frame.remove(mainPanel);
        frame.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                frame.add(buttons[row][col]);
            }
        }

        frame.setSize(new Dimension(300, 300));

        if (isFstMove && !isPlayerTurn) {
            ai.makeFstMove();
        }
    }

    // Update the GUI buttons based on the game board state
    private void updateButtons() {
        // ai.moveAi();
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                buttons[row][col].setText(Character.toString(arr[row][col]));
            }
        }
    }

    // Check the game results (win, draw)
    private void results() {
        checkWinner();
        checkDraw();
        updateButtons();
    }

    // Check if there is a winner
    private void checkWinner() {
        for (int i = 0; i < arr.length; i++) {
            if (checkRow(i) || checkColumn(i)) {
                announceWinner(arr[i][i]);
                System.exit(0);
            }
        }

        if (checkDiagonals()) {
            announceWinner(arr[1][1]);
            System.exit(0);
        }
    }

    // Check if a row has a winning combination
    private boolean checkRow(int row) {
        return arr[row][0] == arr[row][1] && arr[row][1] == arr[row][2] && arr[row][1] != ' ';
    }

    // Check if a column has a winning combination
    private boolean checkColumn(int col) {
        return arr[0][col] == arr[1][col] && arr[1][col] == arr[2][col] && arr[1][col] != ' ';
    }

    // Check if the diagonals have a winning combination
    private boolean checkDiagonals() {
        return (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[1][1] != ' ') ||
                (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0] && arr[1][1] != ' ');
    }

    // Check if the game is a draw
    private void checkDraw() {
        boolean isDraw = true;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == ' ') {
                    isDraw = false;
                    break;
                }
            }
        }

        if (isDraw) {
            System.out.println("It's a tie - DRAW");
            load();
            System.exit(0);
        }
    }

    // Announce the winner and perform some actions
    private void announceWinner(char winner) {
        System.out.println("\nCongratulations " + winner + " won");
        load();
    }

    // Simulate a loading delay
    public void load() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ActionListener for button clicks
    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // updateButtons();
            if (mult) {
                if (arr[row][col] == ' ' && isPlayerTurn) {
                    arr[row][col] = stMove.toCharArray()[0];
                }

                if (arr[row][col] == ' ' && !isPlayerTurn) {
                    arr[row][col] = ndMove.toCharArray()[0];
                }
            } else {

                if (arr[row][col] == ' ' && isPlayerTurn) {
                    arr[row][col] = player.toCharArray()[0];
                    results();
                    isPlayerTurn = !isPlayerTurn;
                }

                updateButtons();
                if (!isPlayerTurn) {
                    Timer time = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ai.moveAi();
                            updateButtons();
                            results();
                            ((Timer) e.getSource()).stop();
                        }
                    });
                    time.setRepeats(false);
                    time.start();

                }
            }
            // updateButtons();
            results();
            isPlayerTurn = !isPlayerTurn;
        }
    }

    // Allow the AI to make a move
    // thsis is the ai class
    private class AI {
        private final String[] digoArr = { "00", "02", "20", "22" };
        private final String[] midArr = { "01", "10", "12", "21" };
        private boolean specialMoveDone = false;

        public void moveAi() {
            aiMove();
        }

        public void makeFstMove() {
            Timer time = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ai.moveAi();
                    updateButtons();
                    results();
                    ((Timer) e.getSource()).stop();
                }
            });

            isPlayerTurn = !isPlayerTurn;
            setIsFstMove();
            time.setRepeats(false);
            time.start();
        }

        private void aiMove() {

            setWinningArr();
            boolean moveMade = false;
            String winMove;
            int x, y;

            // if theres a win go for a win
            OUTERLOOP: for (int i = 0; i < winningArr.length; i++) {
                if ((winningArr[i].contains(AI + AI) || winningArr[i].contains(AI + " " + AI))
                        && winningArr[i].contains(" ")) {
                    winMove = key.get(i);
                    for (int j = 0; j < winMove.length(); j = j + 2) {
                        x = Character.getNumericValue(winMove.charAt(j));
                        y = Character.getNumericValue(winMove.charAt(j + 1));
                        if (arr[x][y] == ' ') {
                            arr[x][y] = AI.toCharArray()[0];
                            // updateButtons();
                            moveMade = true;
                            break OUTERLOOP;
                        }
                    }
                }
            }

            // player is winning block
            if (!moveMade) {
                OUTERLOOPp: for (int i = 0; i < winningArr.length; i++) {
                    if ((winningArr[i].contains(player + "" + player) || winningArr[i].contains(player + " " + player))
                            && winningArr[i].contains(" ")) {
                        winMove = key.get(i);
                        for (int j = 0; j < winMove.length(); j = j + 2) {
                            x = Character.getNumericValue(winMove.charAt(j));
                            y = Character.getNumericValue(winMove.charAt(j + 1));
                            if (arr[x][y] == ' ') {
                                arr[x][y] = AI.toCharArray()[0];
                                moveMade = true;
                                break OUTERLOOPp;
                            }
                        }
                    }
                }
            }

            // special move
            if (!moveMade) {
                int count = 0;
                for (int i = 0; i < arr.length; i++) {
                    for (int j = 0; j < arr.length; j++) {
                        if (arr[i][j] == player.toCharArray()[0]) {
                            count++;
                        }
                    }
                }

                BREAKTOOUTERLOOP: if (count == 1) {
                    for (int i = 0; i < digoArr.length; i++) {
                        int j = Character.getNumericValue(digoArr[i].toCharArray()[0]);
                        int k = Character.getNumericValue(digoArr[i].toCharArray()[1]);

                        if (arr[j][k] == player.toCharArray()[0]) {
                            arr[1][1] = AI.toCharArray()[0];
                            moveMade = true;
                            break BREAKTOOUTERLOOP;
                        }
                    }
                } else if (count == 0) {
                    // this is like the first move- this places the first move at the corner
                    String xy = digoArr[new Random().nextInt(digoArr.length)];

                    int j = Character.getNumericValue(xy.toCharArray()[0]);
                    int k = Character.getNumericValue(xy.toCharArray()[1]);

                    arr[j][k] = AI.toCharArray()[0];
                    moveMade = true;
                }
            }
            // to avoid any loose- use the other initialized array
            // if()

            // if there is no move yet make a move
            if (!moveMade) {
                while (true) {
                    x = new Random().nextInt(3);
                    y = new Random().nextInt(3);

                    if (arr[x][y] == ' ') {
                        arr[x][y] = AI.toCharArray()[0];
                        break;
                    }
                }
            }
        }

    }

    // Start the game and let the player choose game mode
    public void start() {
        choice();
    }

    // Display the game mode choice to the player
    public String choice() {
        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        RoundedButton mulButton = new RoundedButton("MultiPlayer");
        mulButton.setBackground(Color.GRAY);
        mulButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        mulButton.setAlignmentY(JButton.CENTER_ALIGNMENT);
        mulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mult = true;
                showMultiPlayerPage();
            }
        });

        RoundedButton singleButton = new RoundedButton("SinglePlayer");
        singleButton.setBackground(Color.GRAY);
        singleButton.setAlignmentX(RoundedButton.CENTER_ALIGNMENT);
        singleButton.setAlignmentY(RoundedButton.CENTER_ALIGNMENT);
        singleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSinglePlayerPage();
            }
        });

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(mulButton);
        mainPanel.add(singleButton);
        mainPanel.add(Box.createVerticalGlue());

        frame.getContentPane().add(mainPanel);
        frame.setSize(new Dimension(400, 300));
        frame.setVisible(true);

        return "";
    }

    // Initialize the winning combinations
    private void setKey() {
        key.put(0, "001020");
        key.put(1, "011121");
        key.put(2, "021222");

        key.put(3, "000102");
        key.put(4, "101112");
        key.put(5, "202122");

        key.put(6, "001122");
        key.put(7, "021120");
    }

    // Set the winning combinations based on the current game board
    private void setWinningArr() {
        winningArr[0] = "" + arr[0][0] + arr[1][0] + arr[2][0];
        winningArr[1] = "" + arr[0][1] + arr[1][1] + arr[2][1];
        winningArr[2] = "" + arr[0][2] + arr[1][2] + arr[2][2];

        winningArr[3] = "" + arr[0][0] + arr[0][1] + arr[0][2];
        winningArr[4] = "" + arr[1][0] + arr[1][1] + arr[2][2];
        winningArr[5] = "" + arr[2][0] + arr[2][1] + arr[2][2];

        winningArr[6] = "" + arr[0][0] + arr[1][1] + arr[2][2];
        winningArr[7] = "" + arr[0][2] + arr[1][1] + arr[2][0];
    }

    // Display the SinglePlayer page and let the player choose X or O
    private void showSinglePlayerPage() {
        mainPanel.removeAll();
        RoundedButton singlePlayerPage = new RoundedButton();
        singlePlayerPage.setLayout(new BoxLayout(singlePlayerPage, BoxLayout.Y_AXIS));

        singlePlayerPage.setAlignmentX(RoundedButton.CENTER_ALIGNMENT);
        singlePlayerPage.setAlignmentY(RoundedButton.CENTER_ALIGNMENT);

        JLabel label = new JLabel("Welcome to SinglePlayer Page");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        singlePlayerPage.add(label);

        RoundedButton playerXButton = new RoundedButton("Player X");
        playerXButton.setBackground(Color.GRAY);
        playerXButton.setAlignmentX(RoundedButton.CENTER_ALIGNMENT);
        playerXButton.setAlignmentY(RoundedButton.CENTER_ALIGNMENT);
        playerXButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPlayer(playxo[0].toString());
                // setPlayer("X");
                setOpponent(playxo[1].toString());

                createGUI(frame);
            }
        });

        RoundedButton playerOButton = new RoundedButton("Player O");
        playerOButton.setBackground(Color.GRAY);
        playerOButton.setAlignmentX(RoundedButton.CENTER_ALIGNMENT);
        playerOButton.setAlignmentY(RoundedButton.CENTER_ALIGNMENT);
        playerOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // x should make first move
                isPlayerTurn = false;
                setIsFstMove();
                setPlayer(playxo[1].toString());
                setOpponent(playxo[0].toString());
                createGUI(frame);
            }
        });

        singlePlayerPage.add(Box.createVerticalGlue());
        singlePlayerPage.add(playerXButton);
        singlePlayerPage.add(Box.createRigidArea(new Dimension(0, 10)));
        singlePlayerPage.add(playerOButton);
        singlePlayerPage.add(Box.createVerticalGlue());

        switchPage(singlePlayerPage);
    }

    private void setIsFstMove() {
        isFstMove = !isFstMove;
    }

    // Set the opponent for the game
    protected void setOpponent(String opponent) {
        this.AI = opponent;
    }

    // Set the player for the game
    protected void setPlayer(String player) {
        this.player = player;
    }

    // Switch between different pages in the GUI
    private void switchPage(RoundedButton newPage) {
        if (currentPage != null) {
            mainPanel.remove(currentPage);
        }

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(newPage);
        mainPanel.add(Box.createVerticalGlue());

        currentPage = newPage;
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Display the MultiPlayer page and start the game
    private void showMultiPlayerPage() {
        createGUI(frame);
    }

}

class RoundedButton extends JButton {
    public RoundedButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    public RoundedButton() {
        super();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(java.awt.Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
    }

}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGUI());

    }

}
