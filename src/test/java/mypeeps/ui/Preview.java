package mypeeps.ui;

import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import static mypeeps.Utils.log;

public class Preview
{
    public static void showInJFrame(JPanel panel)
    {
        log(Preview.class, "showInJFrame(JPanel)");
        panel.setBorder(createEmptyBorder(10, 10, 10, 10));
        JFrame frame = new JFrame(panel.getClass().getName());
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
