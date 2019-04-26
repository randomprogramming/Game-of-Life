import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        setTitle("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(new MainPanel());
        pack();
    }
}