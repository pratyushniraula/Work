import javax.swing.*;

public class MySwingApp {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Quick Swing Demo");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(300, 200);
      frame.add(new JLabel("Hi Swing!", SwingConstants.CENTER));
      frame.setVisible(true);
    });
  }
}