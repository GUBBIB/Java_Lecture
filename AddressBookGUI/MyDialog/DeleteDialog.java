package MyDialog;

import MyDataType.AddressDataType;
import MyPanel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class DeleteDialog extends JDialog {

    // [0] = 이름, [1] = 번호
    private String[] tmpFileName = new String[2];

    private JButton deleteButton = new JButton("삭제");
    private JButton closeButton = new JButton("닫기");

    public DeleteDialog(JFrame f, String title, ArrayList<AddressDataType> deleteAddress, ArrayList<File> files, boolean chk, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel){
        super(f, title, true);
        setLayout(new BorderLayout());

        if(chk) {
            Vector<AddressDataType> vector = new Vector<>(deleteAddress);
            JList<AddressDataType> jList = new JList<>(vector);
            jList.setFont(new Font("D2Coding", Font.PLAIN, 30));

            JScrollPane scrollPane = new JScrollPane(jList);
            add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
            buttonPanel.add(deleteButton);
            buttonPanel.add(closeButton);
            add(buttonPanel, BorderLayout.SOUTH);

            // 동명이인 중에서 삭제할 연락처 선택
            jList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // 더블클릭 일 때

                        AddressDataType selected = jList.getSelectedValue();

                        if(selected != null){
                            tmpFileName[0] = selected.getName();
                            tmpFileName[1] = selected.getNumber();

                            deleteFunction(tmpFileName, files, addressList, deleteAddress, leftPanel);
                            leftPanel.updateLeftPanel(addressList);
                            setVisible(false);
                        }
                    }
                }
            });
            jList.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        AddressDataType selected = jList.getSelectedValue();

                        if(selected != null){
                            tmpFileName[0] = selected.getName();
                            tmpFileName[1] = selected.getNumber();

                            deleteFunction(tmpFileName, files, addressList, deleteAddress, leftPanel);
                            leftPanel.updateLeftPanel(addressList);
                            setVisible(false);
                        }
                    }
                }
            });
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    AddressDataType selected = jList.getSelectedValue();

                    if(selected != null){
                        tmpFileName[0] = selected.getName();
                        tmpFileName[1] = selected.getNumber();

                        deleteFunction(tmpFileName, files, addressList, deleteAddress, leftPanel);
                        leftPanel.updateLeftPanel(addressList);
                        setVisible(false);
                    }
                }
            });

            // 닫기 버튼 또는 esc 로 끄기
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setVisible(false);
                }
            });
            jList.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        setVisible(false);
                    }
                }
            });
        } else {
            tmpFileName[0] = deleteAddress.get(0).getName();
            tmpFileName[1] = deleteAddress.get(0).getNumber();

            deleteFunction(tmpFileName, files, addressList, deleteAddress, leftPanel);

            leftPanel.updateLeftPanel(addressList);
            setVisible(false);

        }


        setSize(500, 500);
    }

    // 삭제 기능 주 메소드
    public static void deleteFunction(String[] fileName, ArrayList<File> files, ArrayList<AddressDataType> addressList, ArrayList<AddressDataType> deleteAddress, AddressListPanel leftPanel){
        boolean isFile = false;
        File tmpFile = null;
        AddressDataType tmpAddressData = new AddressDataType();

        for (AddressDataType data : deleteAddress) {
            if(
                    (data.getName().contains(fileName[0]) && data.getNumber().contains(fileName[0])) ||
                            (data.getName().contains(fileName[0]) && data.getNumber().contains(fileName[1])) ||
                            (data.getName().contains(fileName[1]) && data.getNumber().contains(fileName[0])) ||
                            (data.getName().contains(fileName[1]) && data.getNumber().contains(fileName[1]))
            ){
                tmpAddressData = data;
                break;
            }
        }
        for (File deleteFile : files) {
            if (deleteFile.getName().contains(fileName[0].trim()) && deleteFile.getName().contains(fileName[1].trim())) {
                tmpFile = deleteFile;
                isFile = true;
                break;
            }
        }

        if (isFile) {
            if (tmpFile.delete()) {
                JOptionPane.showMessageDialog(null, "삭제 완료");
            } else {
                JOptionPane.showMessageDialog(null, "연락처 없음", "경고", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "연락처 없음", "경고", JOptionPane.ERROR_MESSAGE);
        }


        addressList.remove(tmpAddressData);
        leftPanel.updateLeftPanel(addressList);
    }
}
