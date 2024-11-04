import javax.swing.*;
import java.awt.*;

public class AlienStatisticsWindow extends JFrame {
    private JLabel greenCountLabel;
    private JLabel blueCountLabel;
    private JLabel yellowCountLabel;

    public AlienStatisticsWindow() {
        setTitle("Kiek ateivių buvo sunakinta?");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        ImageIcon greenAlien = new ImageIcon(getClass().getResource("/zalias.png"));
        ImageIcon blueAlien = new ImageIcon(getClass().getResource("/melynas.png"));
        ImageIcon yellowAlien = new ImageIcon(getClass().getResource("/geltonas.png"));

        add(new JLabel("Žaliųjų sunaikinta: ", greenAlien, JLabel.LEFT));
        greenCountLabel = new JLabel("0");
        add(greenCountLabel);

        add(new JLabel("Mėlynųjų sunakinta: ", blueAlien, JLabel.LEFT));
        blueCountLabel = new JLabel("0");
        add(blueCountLabel);

        add(new JLabel("Geltonųjų sunaikinta:  ", yellowAlien, JLabel.LEFT));
        yellowCountLabel = new JLabel("0");
        add(yellowCountLabel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateCyanCount(int count) {
       greenCountLabel.setText(String.valueOf(count));
    }

    public void updateMagentaCount(int count) {
        blueCountLabel.setText(String.valueOf(count));
    }

    public void updateYellowCount(int count) {
        yellowCountLabel.setText(String.valueOf(count));
    }
}
