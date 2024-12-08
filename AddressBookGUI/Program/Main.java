import MyGUI.AddressBook;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        File directory = new File("src\\AddressBook");
        if (!directory.exists() && !directory.mkdirs()) {
            System.out.println("경로 오류!");
            System.exit(0);
        }

        new AddressBook();
    }
}
