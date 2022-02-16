import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        if (initialCheck() == 1) {
            System.exit(0);
        }
        new Login();
    }

    private static int initialCheck(){
        if (!new File(Constants.MAIN_TEXTFILE_DIRECTORY).exists()) {
            JOptionPane.showMessageDialog(null,
                    "Text File Directory Could not be found\nCheck Constants.java for file paths",
                    "Error!", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        return 0;
    }
}
