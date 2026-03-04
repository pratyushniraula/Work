import java.util.Random;

public class client extends YoClient {
    // Counters for each message received
    private int countSendYo = 0;
    private int countSendHowdy = 0;
    private int countRecvYo = 0;
    private int countRecvHowdy = 0;

    // Track consecutive Yo's sent (based on server acknowledgements)
    private int consecutiveYoCount = 0;

    // Track how many Yo's and Howdy's received from others
    private int receivedYoCountFromOthers = 0;
    private int receivedHowdyCountFromOthers = 0;

    private final Random rand = new Random();
    private static final String[] HOWDY_REMINDERS = {"Yo, the 'Send Howdy' button is a thing.", "The 'Send Yo' button is getting a little tired, don't you think?", "Try sending a 'Howdy' to everyone."};

    //Called for each message code from the server and updates counters,
    @Override
    public void updateStats(char codeCh) {
        switch (codeCh) {
            //yo is sent
            case SEND_YO:
                countSendYo++;
                consecutiveYoCount++;
                // Every 5 Yos in a row require reminder about Howdy
                if (consecutiveYoCount % 5 == 0) {
                    String msg = HOWDY_REMINDERS[rand.nextInt(HOWDY_REMINDERS.length)];
                    updateGUIConsole(msg);
                }
                break;

            //howdy is sent
            case SEND_HOWDY: //send a Howdy
                countSendHowdy++;
                // Reset Yo counter
                consecutiveYoCount = 0;
                break;
            
            //yo is received
            case RECV_YO: // Someone else sent a Yo
                countRecvYo++;
                receivedYoCountFromOthers++;
                checkCoolFactor();
                break;
            
            //howdy is received
            case RECV_HOWDY: // Someone else sent a Howdy
                countRecvHowdy++;
                receivedHowdyCountFromOthers++;
                checkCoolFactor();
                break;

            default:
                break;
        }
    }

    //checks the cool factor every 10 messages received from others
    private void checkCoolFactor() {
        int totalReceived = receivedYoCountFromOthers + receivedHowdyCountFromOthers;
        if (totalReceived > 0 && totalReceived % 10 == 0) {
            double coolFactor = (double) receivedHowdyCountFromOthers / totalReceived;
            String text = String.format("Cool factor after %d received: %.2f", totalReceived, coolFactor);
            updateGUIConsole(text);
        }
    }

    //Called when the user disconnects. Displays final stats
    @Override
    public void finalStats() {
        updateGUIConsole("\n--- Final Stats ---");
        updateGUIConsole("Yos sent: " + countSendYo);
        updateGUIConsole("Howdys sent: " + countSendHowdy);
        updateGUIConsole("Yos received from others: " + countRecvYo);
        updateGUIConsole("Howdys received from others: " + countRecvHowdy);
    }

    // Main method to launch the client
    // Initializes the GUI, connects to the server, and starts listening for messages
    public static void main(String[] args) {
        client clienter = new client();
        try {
            clienter.initUI();
            if (clienter.connect()) {
                clienter.listen();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}