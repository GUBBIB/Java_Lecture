package MyGUI;

import MyPanelPackage.*;
import MyDataType.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

// 메인 클래스
public class AddressBook extends JFrame {
    public static ArrayList<AddressDataType> addressList = new ArrayList<>();
    public AddressBook(){
        addressList = loadData();

        setTitle("AddressBook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);

        AddressListPanel leftPanel = new AddressListPanel(this, addressList);
        FunctionPanel rightPanel = new FunctionPanel(this, addressList, leftPanel);

        leftPanel.setSize(480, 920);
        rightPanel.setSize(460, 920);

        leftPanel.setLocation(10,10);
        rightPanel.setLocation(510,10);

        createMenu(leftPanel);

        c.add(leftPanel);
        c.add(rightPanel);

        setSize(1000, 1000);
        setVisible(true);
    }

    public void createMenu(AddressListPanel leftPanel){
        // 메뉴 바 생성
        JMenuBar mb = new JMenuBar();

        // 메뉴 생성 및 등록
        JMenu screenMenu = new JMenu("File");
        mb.add(screenMenu);

        JMenuItem[] items = new JMenuItem[4];
        String[] titles = {"연락처 추가", "수정", "삭제", "종료"};

        // 각 버튼의 이벤트 추가 및 메뉴 아이템 등록
        for(int i=0; i<titles.length; i++){
            items[i] = new JMenuItem(titles[i]);
            items[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String cmd = e.getActionCommand();

                    switch (cmd){
                        // 버튼과 동일 기능
                        case "연락처 추가":
                            FunctionPanel.addBTN(AddressBook.this, addressList, leftPanel);
                            break;

                        case "수정":
                            FunctionPanel.modifyBTN(AddressBook.this, addressList, leftPanel, null);
                            break;

                        case "삭제":
                            FunctionPanel.deleteBTN(AddressBook.this, addressList, leftPanel, null);
                            break;

                        // 그냥 종료
                        case "종료":
                            System.exit(0);
                            break;
                    }
                }
            });

            screenMenu.add(items[i]);
        }

        // 메뉴바 등록
        setJMenuBar(mb);
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
            System.out.println(addressAppendList.getEmail() == null);
            bw.write("이메일: " + ((addressAppendList.getEmail() != null ) ? addressAppendList.getEmail() : "null" ));
            bw.newLine();
            bw.write("사진: " + ((addressAppendList.getPig() != null) ? addressAppendList.getPig().getDescription() : "null"));
        } catch(IOException e){
            System.out.println("파일 접근불가!");
        }
        addressList.add(addressAppendList);
        return addressList;
    }

    // AddressBook 디렉토리 안의 .txt파일들을 읽어서 ArrayList addressList에 저장
    public static ArrayList<AddressDataType> loadData(){
        addressList.clear();
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
        txtFiles.clear();
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
//        System.out.println("연락처 읽기 완료");

        return addressList;

    }
}


// 지금 연락처 추가, 삭제, 검색, 수정 까지 완성
// 추가 기능: 리스트 우클릭시 팝업 버튼 나와서 수정, 삭제, 추가 기능 할 수 있게 하기
// JMenuBar 에도 해당 기능 추가