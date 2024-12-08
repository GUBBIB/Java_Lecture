package MyPanel;

import MyDataType.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import MyDialog.AddressInputDialog;
import MyDialog.DeleteDialog;
import MyDialog.ModifyDialog;

// 오른쪽 Panel 에 대한 클래스
public class FunctionPanel extends JPanel {

    private JTextField tf = new JTextField(10);
    private JButton search = new JButton("검색");
    private static JButton add = new JButton("연락처 추가");
    private JButton modify = new JButton("수정");
    private JButton delete = new JButton("삭제");

    private static AddressInputDialog inputDialog;
    private static ModifyDialog modifyDialog;
    private static DeleteDialog deleteDialog;
    public FunctionPanel(JFrame parentFrame, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel){
        // 레이아웃 설정
        setLayout(new GridLayout(3, 2, 5 ,100));

        // 글자 크기 수정
        tf.setFont(new Font("D2Coding", Font.PLAIN, 30));
        search.setFont(new Font("D2Coding", Font.PLAIN, 40));
        add.setFont(new Font("D2Coding", Font.PLAIN, 30));
        modify.setFont(new Font("D2Coding", Font.PLAIN, 40));
        delete.setFont(new Font("D2Coding", Font.PLAIN, 40));

        // 컴포넌트 추가
        add(tf);
        add(search);
        add(add);
        add(modify);
        add(delete);

        // -------------------------------------- 모든 버튼 actionListener 추가 --------------------------------------


        // 연락처 추가 버튼
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addBTN(parentFrame, addressList, leftPanel);
            }
        });

        // ----------------------------- 검색 기능 -----------------------------
        // 이름, 번호, 이메일, 사진경로 중 한개만 or 다 적었을 때 검색
        // 검색창에는 띄워쓰기를 기준으로 적음
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                searchEnterClickedListener(tf, addressList, leftPanel);
            }
        });
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchEnterClickedListener(tf, addressList, leftPanel);
                }
            }
        });
        // -----------------------------------------------------------------------

        // ----------------------------- 삭제 기능 -----------------------------
        // 삭제 버튼 이벤트 리스너
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                deleteBTN(parentFrame, addressList, leftPanel, null);
            }
        });
        // -----------------------------------------------------------------------

        // ----------------------------- 수정 기능 -----------------------------
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                modifyBTN(parentFrame, addressList, leftPanel, null);
            }
        });

        // -----------------------------------------------------------------------


    }

    // 검색 이벤트 리스너 안에서의 코드
    public static void searchEnterClickedListener(JTextField tf, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel){
        String str = tf.getText();
        String[] arr;
        ArrayList<AddressDataType> tmpList = new ArrayList<>();

        if(str.contains(" ")){
            StringTokenizer st = new StringTokenizer(str);

            arr = new String[st.countTokens()];
            for(int i=0; st.hasMoreTokens(); i++){
                arr[i] = st.nextToken();
            }
        } else {
            arr = new String[]{str};
        }

        for(AddressDataType data : addressList){
            String tmp = data.getName()+" "+data.getNumber()+" "+data.getEmail()+" "+data.getPig();

            for(int i=0; i<arr.length; i++){
                if(tmp.contains(arr[i])){
                    tmpList.add(data);
                }
            }
        }
        leftPanel.updateLeftPanel(tmpList);
    }

    // 추가 기능 메소드화
    public static void addBTN(JFrame parentFrame, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel){
        inputDialog = new AddressInputDialog(parentFrame, "연락처 추가", addressList, leftPanel);
        inputDialog.setVisible(true);
    }

    // 삭제 기능 메소드화
    public static void deleteBTN(JFrame parentFrame, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel, AddressDataType selectedValue){
        File file = new File("src\\AddressBook\\");
        File[] allFiles = file.listFiles();
        ArrayList<AddressDataType> deleteAddress = new ArrayList<>();

        String delete = "";
        Boolean chk = false;

        // .txt 로 끝나는 file 들을 txtFiles에 저장
        ArrayList<File> txtFiles = new ArrayList<>();
        txtFiles.clear();
        for (File files : allFiles) {
            if (files.isFile() && files.getName().endsWith(".txt")) {
                txtFiles.add(files);
            }
        }


        // 일반적 삭제 기능
        if(selectedValue == null) {
            delete = JOptionPane.showInputDialog("삭제할 연락처의 이름 또는 번호를 입력하세요");
            for (AddressDataType data : addressList) {
                if (data.getName().equals(delete) || data.getNumber().equals(delete)) {
                    deleteAddress.add(data);
                }
            }
        } else { // 우클릭으로 선택 후, 삭제 기능
            deleteAddress.add(selectedValue);
        }

        // 동명이인 있을 때
        if(deleteAddress.size() > 1){

            deleteDialog = new DeleteDialog(parentFrame, "삭제 리스트", deleteAddress, txtFiles, true, addressList, leftPanel);
            deleteDialog.setVisible(true);

        } else if(deleteAddress.size() == 1){ // 한명일 때

            deleteDialog = new DeleteDialog(parentFrame, "삭제 리스트", deleteAddress, txtFiles, false, addressList, leftPanel);
            deleteDialog.setVisible(false);

        } else {
            JOptionPane.showMessageDialog(null, "연락처 없습니다.", "경고", JOptionPane.ERROR_MESSAGE);
        }
        // 파일 삭제 참고 https://javacpro.tistory.com/27
    }



    // 수정 기능 메소드화
    public static void modifyBTN(JFrame parentFrame, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel, AddressDataType selectedValue){

        ArrayList<AddressDataType> modifyAddress = new ArrayList<>();

        if(selectedValue == null) { // 일반 수정
            String modify = JOptionPane.showInputDialog("수정할 연락처의 이름 또는 번호를 입력하세요");

            for (AddressDataType data : addressList) {
                if (data.getName().equals(modify) || data.getNumber().equals(modify)) {
                    modifyAddress.add(data);
                }
            }
        } else { // JList에서 우클릭으로 선택 한 수정
            modifyAddress.add(selectedValue);
        }

        // 같은 이름 또는 같은 번호가 2개 이상이라면
        if (modifyAddress.size() > 1) {
            modifyDialog = new ModifyDialog(parentFrame, "연락처 수정", modifyAddress, true, leftPanel);
            modifyDialog.setVisible(true);
        } else if (modifyAddress.size() == 1) {
            modifyDialog = new ModifyDialog(parentFrame, "연락처 수정", modifyAddress, false, leftPanel);
            modifyDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "연락처가 없습니다.");
        }




    }

}
