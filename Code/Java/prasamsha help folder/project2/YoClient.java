/**
 * Yo App client
 * Possibly the most awesome communication app ever created.
 * Why text someone when you can send a Yo to everyone?
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Objects;

public abstract class YoClient implements ActionListener {

    /**
     * IP address of the Yo server.
     */
    public final String SERVER_ADDRESS = "10.176.149.20";
    //public final String SERVER_ADDRESS = "127.0.0.1";

    /**
     * Message codes.
     * A message code represents a message (a Yo or a Howdy) as a single character.  The Yo client will send uppercase
     * codes to the server.  The Yo server sends message codes back to the client according to the following:
     * - Uppercase message codes are responses from what we have sent to it.  This is the server acknowledging that it
     *   received our message (either a Yo or a Howdy).  In other words, we send a Y or H code to the server, and it
     *   simply sends the same code back to us to let us know it received our Yo or Howdy.
     * - Lowercase message codes represent messages that other clients have sent to the server.
     */
    public final char SEND_YO = 'Y';  // I sent a Yo
    public final char RECV_YO = 'y';  // someone else sent a Yo
    public final char SEND_HOWDY = 'H';  // I sent a Howdy
    public final char RECV_HOWDY = 'h';  // someone else sent a Howdy

    /* Additional GUI related fields which are private. */
    private JScrollPane scrollPane;
    private JTextArea recvPane;
    private JButton yoButton;
    private JButton howdyButton;
    private JButton disconnectButton;

    /* Networking related stuff */
    private Socket socket;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;

    private LinkedList<String> consoleText = new LinkedList<>();
    private final int LINE_LIMIT = 100;

    /* Sends a character (message code) to the Yo server. */
    private void send(char sendCh) {
        try {
            this.dataOut.writeByte(sendCh);
            this.dataOut.flush();
        } catch (IOException ex) {
            updateConsoleText("-- On send data attempt: " + ex.getMessage());
        }
    }

    /* Updates the GUI text console with given text. */
    private void updateConsoleText(final String text) {

        this.consoleText.addLast(text);
        if (this.consoleText.size() > LINE_LIMIT) {
            // remove oldest line (line defined as oldest 'text' string added to list, 'text' may have newlines)
            this.consoleText.removeFirst();
        }

        recvPane.setText(String.join("\n", this.consoleText));

        JScrollBar sb = scrollPane.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
    }


    /* abstract methods - these are callbacks, in that they are called in this class, and you implement them in yours */
    /**
     * Called when a message code is received from the Yo server.  Should compute stats and print text to the GUI text
     * console as required in the project specification.
     * @param codeCh message code character
     */
    public abstract void updateStats(char codeCh);

    /**
     * Called after disconnecting from the Yo server when pressing the 'Disconnect' button.  Should compute final stats
     * and print text to the GUI text console as required in the project specification.
     */
    public abstract void finalStats();


    /* concrete methods */
    /**
     * Build and display the GUI (user interface).  Should be called once at the beginning of client program execution.
     * @throws IOException when an IO operation fails within this method (must catch or throw in your calling method)
     */
    public void initUI() throws IOException {
        JFrame mainFrame = new JFrame("Yo App");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setIconImage(ImageIO.read(Objects.requireNonNull(YoClient.class.getResourceAsStream("yo.png"))));
        mainFrame.getContentPane().setLayout(new BorderLayout(10, 10));  // set layout of frame

        // add the text area for receiving stuff
        recvPane = new JTextArea("Not connected\n");
        recvPane.setEditable(false);
        recvPane.setBackground(Color.DARK_GRAY);
        recvPane.setForeground(Color.WHITE);

        scrollPane = new JScrollPane(recvPane);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setPreferredSize(new Dimension(500, 720));

        /* This panel is where the buttons will go. */
        JPanel bPanel = new JPanel();
        yoButton = new JButton("Send Yo");  //add buttons to frame
        yoButton.setEnabled(false);
        yoButton.addActionListener(this);
        bPanel.add(yoButton);

        howdyButton = new JButton("Send Howdy");
        howdyButton.setEnabled(false);
        howdyButton.addActionListener(this);
        bPanel.add(howdyButton);

        disconnectButton = new JButton("Disconnect");
        disconnectButton.setEnabled(false);
        disconnectButton.addActionListener(this);
        bPanel.add(disconnectButton);

        mainFrame.getContentPane().add(scrollPane, BorderLayout.NORTH);
        mainFrame.getContentPane().add(bPanel, BorderLayout.SOUTH);

        // display the client app window
        mainFrame.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((screenSize.width / 2) - (mainFrame.getWidth() / 2),
                (screenSize.height / 2) - (mainFrame.getHeight() / 2));  // center the client app window

        mainFrame.setResizable(false);
        mainFrame.setVisible(true);  // show the client app on the screen
    }

    /**
     * This method is called when a button is pressed.
     * Method runs in the GUI thread (do not call this method directly).
     * @param e the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.yoButton) {
            this.send(this.SEND_YO);
        }
        else if (e.getSource() == this.howdyButton) {
            this.send(this.SEND_HOWDY);
        }
        else if (e.getSource() == this.disconnectButton){
            try {
                this.socket.close();
            } catch(IOException ex) {
                updateConsoleText("-- On disconnection attempt: " + ex.getMessage());
            }

            updateConsoleText("Disconnected from Yo server\n");

            this.finalStats();
        }
    }

    /**
     * Connect to the Yo server.
     * @return true if connect attempt was successful, false otherwise
     * @throws IOException if connection attempt fails
     */
    public boolean connect() throws IOException {
        this.socket = new Socket();


        this.updateGUIConsole("Connecting to Yo server ...");

        try {
            this.socket.connect(new InetSocketAddress(this.SERVER_ADDRESS, 2336));
        } catch (ConnectException ex) {
            this.updateGUIConsole("-- Could not connect to server: " + ex.getMessage());
            return false;
        }

        // successfully connected
        this.dataOut = new DataOutputStream(this.socket.getOutputStream());
        this.dataIn = new DataInputStream(this.socket.getInputStream());

        this.updateGUIConsole("Connected to Yo server");

        SwingUtilities.invokeLater(() -> {
            // run this on the GUI thread
            disconnectButton.setEnabled(true);
            yoButton.setEnabled(true);
            howdyButton.setEnabled(true);
        });

        return true;
    }

    /**
     * Listens for incoming data from Yo server.  Once the app has successfully connected to the Yo server, this method
     * needs to be called.  Received data are message codes (each code is 1 byte, cast to a char).
     */
    public void listen() {
        while (true) {
            char codeCh;

            try {
                codeCh = (char) this.dataIn.readByte();  // block here until we receive a byte from the server
            }
            catch (IOException ex) {
                // socket probably closed
                SwingUtilities.invokeLater(() -> {
                    disconnectButton.setEnabled(false);
                    yoButton.setEnabled(false);
                    howdyButton.setEnabled(false);
                });

                if(!this.socket.isClosed()) {
                    // connection lost (ie, we did not click the 'Disconnect' button)
                    // afaik, socket.isClosed() will only return true on an explicit socket.close() call locally
                    String msg = ex.getMessage();

                    try {
                        this.socket.close();
                    } catch(IOException ex2) {
                        this.updateGUIConsole("-- On socket close: " + ex2.getMessage());
                    }

                    if (msg != null) {
                        this.updateGUIConsole("Disconnected from Yo server: " + ex.getMessage());
                    }
                    else {
                        this.updateGUIConsole("Disconnected from Yo server: got booted");
                    }
                }

                return;
            }
            catch (NullPointerException ex) {
                // this may occur if listen() is called without successfully connecting to the Yo server
                this.updateGUIConsole("-- Buffer not instantiated (are we connected to the Yo server?)");

                return;
            }

            if (codeCh == this.SEND_YO) {
                this.updateGUIConsole("I said: Yo!");
            }
            else if (codeCh == this.RECV_YO) {
                this.updateGUIConsole("Someone else said: Yo!");
            }
            else if (codeCh == this.SEND_HOWDY) {
                this.updateGUIConsole("I said: Howdy!");
            }
            else if (codeCh == this.RECV_HOWDY) {
                this.updateGUIConsole("Someone else said: Howdy!");
            }
            else {
                // invalid message code received (this probably should not happen)
                this.updateGUIConsole("-- Invalid message code received: " + codeCh);
                continue;
            }

            this.updateStats(codeCh);  // polymorphism in action
        }
    }

    /**
     * Updates the app's text display with additional text, adding a newline character at the end.
     * @param text the text to display
     */
    public void updateGUIConsole(final String text) {
        SwingUtilities.invokeLater(() -> {  // execute this code in the GUI thread
            this.updateConsoleText(text);
        });
    }
}
