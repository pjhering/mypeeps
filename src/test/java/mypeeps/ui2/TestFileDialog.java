package mypeeps.ui2;

import static java.lang.System.out;
import javax.swing.JFrame;
import mypeeps.entity2.File;

public class TestFileDialog
{
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.setSize(100, 100);
        f.setLocationRelativeTo(null);

        File file = new File(1L, "/this/is/the/path", "this is the description");
        FileDialog d = new FileDialog(f, file);
        
        boolean saved = d.open();
        
        if(saved)
        {
            file = d.getUpdatedFile();
            out.println(file.getPath());
            out.println(file.getDescription());
        }
        
        f.dispose();
    }
}
