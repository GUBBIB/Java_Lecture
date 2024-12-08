import java.io.File;
import MyGUI.*;

public class Main {
    // Main 실행
    public static void main(String[] args) {

        // File 클래스로 src 하위 폴더 AddressBook 생성
        File directory = new File("src\\AddressBook");

        // 폴더 생성 후 오류 확인
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.out.println("경로 오류!");
                System.exit(0);
            }
        }

        new AddressBook();

    }
}