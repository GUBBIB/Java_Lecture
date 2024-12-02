import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

// 메인 클래스
public class AddressBook extends JFrame {
    public static ArrayList<AddressDataType> addressList = new ArrayList<>();
    public AddressBook(){
        addressList = loadData();

        setTitle("AddressBook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);

        createMenu();

        AddressListPanel leftPanel = new AddressListPanel(this, addressList);
        FunctionPanel rightPanel = new FunctionPanel(this, addressList, leftPanel);

        leftPanel.setSize(480, 920);
        rightPanel.setSize(460, 920);

        leftPanel.setLocation(10,10);
        rightPanel.setLocation(510,10);

        c.add(leftPanel);
        c.add(rightPanel);

        setSize(1000, 1000);
        setVisible(true);
    }

    public void createMenu(){
        // 메뉴 바 생성
        JMenuBar mb = new JMenuBar();

        // 메뉴 생성 및 등록
        JMenu screenMenu = new JMenu("File");
        mb.add(screenMenu);

        JMenuItem[] items = new JMenuItem[2];
        String[] titles = {"연락처 추가", "종료"};

        // 각 버튼의 이벤트 추가 및 메뉴 아이템 등록
        MyMenuActionListener listener = new MyMenuActionListener();
        for(int i=0; i<titles.length; i++){
            items[i] = new JMenuItem(titles[i]);
            items[i].addActionListener(listener);

            screenMenu.add(items[i]);
        }

        // 메뉴바 등록
        setJMenuBar(mb);
    }

    // ActionListener 생성
    static class MyMenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();

            switch (cmd){
                // 버튼과 동일 기능
                case "연락처 추가":
                    break;

                // 그냥 종료
                case "종료":
                    System.exit(0);
                    break;
            }
        }
    }

    // 입력한 연락처를 이름.txt 파일로 변환
    public static ArrayList<AddressDataType> saveData(AddressDataType addressAppendList) {
        File directory = new File("src\\AddressBook\\");

        // 이름 || 번호 .txt 생성
        String fileName = addressAppendList.getName() + "&" + addressAppendList.getNumber() + ".txt";
        File file = new File(directory, fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("이름: " + addressAppendList.getName());
            bw.newLine();
            bw.write("번호: " + addressAppendList.getNumber());
            bw.newLine();
            bw.write("이메일: " + ((addressAppendList.getEmail() == null ) ? addressAppendList.getEmail() : "null" ));
            bw.newLine();
            bw.write("사진: " + ((addressAppendList.getPig() != null) ? addressAppendList.getPig().getDescription() : "null"));
        } catch(IOException e){
            System.out.println("파일 접근불가!");
        }
        addressList.add(addressAppendList);
        return addressList;
    }

    // AddressBook 디렉토리 안의 .txt파일들을 읽어서 ArrayList addressList에 저장
    public ArrayList<AddressDataType> loadData(){
        File directory = new File("src\\AddressBook\\");

        if(!directory.exists()) {
            return new ArrayList<AddressDataType>();
        }

        // --------------- AddressBook 하위 폴더 이름 가져오는 부분 ---------------

        File[] allFiles = directory.listFiles();
        if(allFiles == null || allFiles.length == 0){
            return new ArrayList<AddressDataType>();
        }

        // .txt 로 끝나는 file 들을 txtFiles에 저장
        ArrayList<File> txtFiles = new ArrayList<>();
        for (File file : allFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                txtFiles.add(file);
            }
        }
        // --------------------------------------------------------------------

        for(File file : txtFiles){
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                String name = br.readLine().split(": ")[1];
                String number = br.readLine().split(": ")[1];
                String email = br.readLine().split(": ")[1];
                String pigPath = br.readLine().split(": ")[1];

                ImageIcon pig = null;
                if (pigPath != null && !pigPath.equals("null")) {
                    File pigFile = new File(pigPath);
                    pig = new ImageIcon(pigFile.getPath());
                }

                AddressDataType data = new AddressDataType(name, number, email, pig);
                addressList.add(data);

            } catch (IOException e){
                System.out.println("파일 읽기 중 오류 발생");
                continue;
            }
        }

        // 제대로 읽었는지 확인용
        System.out.println("연락처 읽기 완료");

        return addressList;

    }

    // Main 실행
    public static void main(String[] args){

        // File 클래스로 src 하위 폴더 AddressBook 생성
        File directory = new File("src\\AddressBook");

        // 폴더 생성 후 오류 확인
        if(!directory.exists()){
            if(!directory.mkdirs()){
                System.out.println("경로 오류!");
                System.exit(0);
            }
        }

        new AddressBook();
    }
}


// 지금 연락처 추가, 삭제, 검색 까지 완성
// 수정(새 다이얼로그로)