package mypeeps.ui2;

import java.awt.Dimension;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class TestTop
{

    public static void main(String[] args)
    {
        Top top = new Top();
        Dimension size = top.FRAME.getToolkit().getScreenSize();
        top.FRAME.setSize(size.width * 9 / 10, size.height * 9 / 10);
        top.FRAME.setDefaultCloseOperation(EXIT_ON_CLOSE);
        top.FRAME.setLocationRelativeTo(null);
        top.FRAME.setVisible(true);
    }
}
