package MyDialog;

import MyDataType.AddressDataType;
import MyGUI.AddressBook;
import MyPanel.AddressListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class ModifyDialog extends JDialog {
    private  JPanel manyModifyPanel;
    private JPanel modifyPanel;
    private File connection;
    private static BufferedReader br;
    private static BufferedWriter bw;
    private JTextArea ta = new JTextArea();
    private JButton okBtn = new JButton("수정");
    private JButton escBtn = new JButton("닫기");
    private String name = "", number = "", fileName = "";

    public ModifyDialog(JFrame f, String title, ArrayList<AddressDataType> modifyAddress, boolean chk, AddressListPanel leftPanel) {
        super(f, title, true);

        setLayout(new BorderLayout());
        JPanel allPanel = new JPanel(new BorderLayout());

        // 중복되는 이름, 번호가 있다면 JList로 표시후 어떤거 수정할 건지 표시
        if(chk){
            manyModifyPanel = new JPanel();
            manyModifyPanel.setLayout(new BorderLayout());

            // Vector에 null이 들어가는 경우는 없음
            // modifyAddress에는 같은 이름을 가지고 있는 사람들의 값들이 들어가있습니다.
            Vector<AddressDataType> vector = new Vector<>(modifyAddress);
            JList modifyList = new JList(vector);
            modifyList.setFont(new Font("D2Coding", Font.PLAIN, 30));

            JScrollPane scrollPane = new JScrollPane(modifyList);
            manyModifyPanel.add(scrollPane, BorderLayout.CENTER);

            // 더블 클릭 or 엔터키로 수정할거 선택
            modifyList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2){
                        // toString으로 들고 오는 값이 "이름 || 번호" 라서 split(" \\|\\|")을 구분자로 설정
                        String[] tmpFileName = modifyList.getSelectedValue().toString().split(" \\|\\|");
                        name = tmpFileName[0]; number = tmpFileName[1];
                        fileName = tmpFileName[0].trim() + "&" + tmpFileName[1].trim();
                        connection = new File("src\\AddressBook\\" + fileName + ".txt");

                        try {
                            ta.setText(readFile(connection.getAbsoluteFile()));
                            ta.setFont(new Font("D2Coding", Font.PLAIN, 30));
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "파일 읽기 실패", "I/O Exception", JOptionPane.ERROR_MESSAGE);
                        }

                        panelRepaint(manyModifyPanel);

                        manyModifyPanel.add(ta);

                    }
                }
            });
            modifyList.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        // toString으로 들고 오는 값이 "이름 || 번호" 라서 split(" \\|\\|")을 구분자로 설정
                        String[] tmpFileName = modifyList.getSelectedValue().toString().split(" \\|\\|");
                        name = tmpFileName[0]; number = tmpFileName[1];
                        fileName = tmpFileName[0].trim() + "&" + tmpFileName[1].trim();
                        connection = new File("src\\AddressBook\\" + fileName + ".txt");

                        try {
                            ta.setText(readFile(connection.getAbsoluteFile()));
                            ta.setFont(new Font("D2Coding", Font.PLAIN, 30));
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "파일 읽기 실패", "I/O Exception", JOptionPane.ERROR_MESSAGE);
                        }

                        panelRepaint(manyModifyPanel);

                        manyModifyPanel.add(ta);
                    }
                }
            });

            allPanel.add(manyModifyPanel, BorderLayout.CENTER);

        } else { // 아니면 그냥 수정 진행

            modifyPanel = new JPanel();
            modifyPanel.setLayout(new BorderLayout());
            allPanel.add(modifyPanel, BorderLayout.CENTER);

            name = modifyAddress.get(0).getName(); number = modifyAddress.get(0).getNumber();

            fileName = name + "&" + number;
            connection = new File("src\\AddressBook\\" + fileName + ".txt");

            try {
                ta.setText(readFile(connection.getAbsoluteFile()));
                ta.setFont(new Font("D2Coding", Font.PLAIN, 20));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "파일 읽기 실패", "I/O Exception", JOptionPane.ERROR_MESSAGE);
            }

            modifyPanel.add(ta, BorderLayout.CENTER);
        }

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.add(okBtn);
        btnPanel.add(escBtn);
        allPanel.add(btnPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(allPanel);
        add(scrollPane);


        setSize(1000, 500);

        // --------------------------- 리스너 추가 ---------------------------

        // 수정 버튼
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                modifyFile(connection, ta, fileName);

                ArrayList<AddressDataType> tmpAddressList =  AddressBook.loadData();

                leftPanel.updateLeftPanel(tmpAddressList);

                setVisible(false);
            }
        });

        // 닫기 버튼 ( 클릭, esc )
        escBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });
        escBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    setVisible(false);
                }
            }
        });

        ta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    setVisible(false);
                }
            }
        });


    }

    public static String readFile(File connection) throws IOException {
        String line = "", fileContent = "";
        try {
            br = new BufferedReader(new FileReader(connection));

            while ((line = br.readLine()) != null){
                fileContent += line;
                fileContent += "\n";
            }
            br.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "잘 못 된 접근입니다. FileNotFoundException", "경고", JOptionPane.ERROR_MESSAGE);
            return fileContent;
        }



        return fileContent;
    }

    // 수정 버튼 누르면 실행 될 것
    public static void modifyFile(File file, JTextArea modifyTa, String fileName) {
        String str = modifyTa.getText(), name = "", number = "";

        // 정규표현식으로 공백(" ")과 개행문자("\n")를 기준으로 구분
        String[] tmpStr = str.split("\\s+|\\n");

        for(int i=0; i<tmpStr.length; i++){
            if(tmpStr[i].equals("이름:")){
                name = tmpStr[i+1].trim();
            } else if(tmpStr[i].equals("번호:")) {
                number = tmpStr[i+1].trim();
            }
        }

        try {
            // 연락처 수정 후, 번호 또는 이름이 바꼈을 경우 https://hianna.tistory.com/615 참고
            // 권한 문제인지 안되서 복사 -> 삭제 방법으로 변경 https://hianna.tistory.com/604 참고
            if (!fileName.equals(name + "&" + number)) {
                File newFile = new File("src\\AddressBook\\" + name + "&" + number + ".txt");
                FileCopy(file, newFile);
                file = newFile;
            }
//            <!--------- 실패코드 --------->
//            if (!fileName.equals(name + "&" + number)) {
//                File rename = new File("src\\AddressBook\\" + name + "&" + number + ".txt");
//                if(file.renameTo(rename)){
//                } else {
//                    JOptionPane.showMessageDialog(null, "파일 이름 변경 실패!", "수정 실패", JOptionPane.ERROR_MESSAGE);
//                }
//            }

            bw = new BufferedWriter (new FileWriter(file));

            bw.write(str);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "잘 못 된 접근입니다. IOException", "경고", JOptionPane.ERROR_MESSAGE);
            return;        
        }

    }

    public static void FileCopy(File copyFile, File destination) {
        try {
            Files.copy(copyFile.toPath(), destination.toPath());
            if(!copyFile.delete()){
                JOptionPane.showMessageDialog(null, "파일 수정 실패!", "IOException", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "파일 복사 및 삭제 실패", "IOException", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void panelRepaint(JPanel panel){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
}

