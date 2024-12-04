package MyDialogPackage;

import MyDataType.AddressDataType;
import MyGUI.AddressBook;
import MyPanelPackage.AddressListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ModifyDialog extends JDialog {
    private static JPanel manyModifyPanel;
    private JPanel modifyPanel;
    private File connection;
    private static BufferedReader br;
    private static BufferedWriter bw;
    private JTextArea ta;
    private JButton okBtn = new JButton("수정");
    private JButton escBtn = new JButton("닫기");
    private String name = "", number = "";

    public ModifyDialog(JFrame f, String title, ArrayList<AddressDataType> address, boolean chk, AddressListPanel leftPanel) {
        super(f, title, true);

        setLayout(new BorderLayout());
        JPanel allPanel = new JPanel(new BorderLayout());

        // 중복되는 이름, 번호가 있다면 JList로 표시후 어떤거 수정할 건지 표시
        if(chk){
            manyModifyPanel = new JPanel();
            manyModifyPanel.setLayout(new BorderLayout());

            // Vector에 null이 들어가는 경우는 없음
            Vector<AddressDataType> vector = new Vector<>(address);
            JList modifyList = new JList(vector);
            modifyList.setFont(new Font("D2Coding", Font.PLAIN, 30));

            JScrollPane scrollPane = new JScrollPane(modifyList);
            manyModifyPanel.add(scrollPane, BorderLayout.CENTER);

            // 더블 클릭 or 엔터키로 수정할거 선택
            modifyList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2){
                        String[] tmpFileName = modifyList.getSelectedValue().toString().split(" \\|\\|");
                        name = tmpFileName[0]; number = tmpFileName[1];
                        String fileName = tmpFileName[0].trim() + "&" + tmpFileName[1].trim();
                        connection = new File("src\\AddressBook\\" + fileName + ".txt");

                        try {
                            ta = new JTextArea(readFile(connection.getAbsoluteFile()));
                            ta.setFont(new Font("D2Coding", Font.PLAIN, 30));
                        } catch (IOException ex) {
                            System.out.println("파일 읽기 실패");
                        }
                        panelRepaint();

                        manyModifyPanel.add(ta);

                    }
                }
            });

            allPanel.add(manyModifyPanel, BorderLayout.CENTER);

        } else { // 아니면 그냥 수정 진행

            modifyPanel = new JPanel();
            modifyPanel.setLayout(new BorderLayout());
            allPanel.add(modifyPanel, BorderLayout.CENTER);

            name = address.get(0).getName(); number = address.get(0).getNumber();

            String fileName = name + "&" + number;
            connection = new File("src\\AddressBook\\" + fileName + ".txt");

            try {
                ta = new JTextArea(readFile(connection.getAbsoluteFile()));
                ta.setFont(new Font("D2Coding", Font.PLAIN, 20));
            } catch (IOException ex) {
                System.out.println("파일 읽기 실패");
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
                modifyFile(connection, ta);

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
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "잘 못 된 접근입니다. FileNotFoundException", "경고", JOptionPane.ERROR_MESSAGE);
            return fileContent;
        }

        while ((line = br.readLine()) != null){
            fileContent += line;
            fileContent += "\n";
        }

        return fileContent;
    }

    // 수정 버튼 누르면 실행 될 것
    public static void modifyFile(File file, JTextArea modifyTa) {
        String str = modifyTa.getText();
        System.out.println(str);
        try {
            bw = new BufferedWriter (new FileWriter(file));
            bw.write(str);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "잘 못 된 접근입니다. IOException", "경고", JOptionPane.ERROR_MESSAGE);
            return;        
        }
    }

    public static void panelRepaint(){
        manyModifyPanel.removeAll();
        manyModifyPanel.revalidate();
        manyModifyPanel.repaint();
    }
}

