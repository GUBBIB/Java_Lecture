package MyDialogPackage;

import MyDataType.AddressDataType;
import MyPanelPackage.AddressListPanel;
import MyPanelPackage.FunctionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class DeleteDialog extends JDialog {

    private String name = "", number = "", fileName = "";
    private String[] tmpFileName = new String[2];

    public DeleteDialog(JFrame f, String title, ArrayList<AddressDataType> deleteAddress, File[] files, boolean chk, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel){
        super(f, title, true);

        // null인 경우 없음

        if(chk) {
            Vector<AddressDataType> vector = new Vector<>(deleteAddress);
            JList<AddressDataType> jList = new JList<>(vector);
            jList.setFont(new Font("D2Coding", Font.PLAIN, 30));

            JScrollPane scrollPane = new JScrollPane(jList);
            add(scrollPane);

            jList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // 더블클릭 일 때

                        if (chk) { // 동명이인이 있을 때
                            tmpFileName = jList.getSelectedValue().toString().split(" \\|\\|");

                        } else {
                            tmpFileName[0] = deleteAddress.get(0).getName();
                            tmpFileName[1] = deleteAddress.get(0).getNumber();
                        }

                        FunctionPanel.deleteFunction(tmpFileName, files, addressList, deleteAddress, leftPanel);
                        setVisible(false);
                    }
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

            FunctionPanel.deleteFunction(tmpFileName, files, addressList, deleteAddress, leftPanel);
            setVisible(false);

        }
        setSize(500, 500);
    }
}
