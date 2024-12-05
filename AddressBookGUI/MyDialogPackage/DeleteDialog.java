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

    private JButton deleteButton = new JButton("삭제");
    private JButton closeButton = new JButton("닫기");

    public DeleteDialog(JFrame f, String title, ArrayList<AddressDataType> deleteAddress, File[] files, boolean chk, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel){
        super(f, title, true);
        setLayout(new BorderLayout());
        // null인 경우 없음

        if(chk) {
            Vector<AddressDataType> vector = new Vector<>(deleteAddress);
            JList<AddressDataType> jList = new JList<>(vector);
            jList.setFont(new Font("D2Coding", Font.PLAIN, 30));

            JScrollPane scrollPane = new JScrollPane(jList);
            add(scrollPane, BorderLayout.CENTER);

            jList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // 더블클릭 일 때

                        AddressDataType selected = jList.getSelectedValue();

                        System.out.println(selected);

                        if(selected != null){
                            tmpFileName[0] = selected.getName();
                            tmpFileName[1] = selected.getNumber();

                            FunctionPanel.deleteFunction(tmpFileName, files, addressList, deleteAddress, leftPanel);
                            leftPanel.updateLeftPanel(addressList);
                            setVisible(false);
                        }
                    }
                }
            });

            // esc 로 끄기
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

            leftPanel.updateLeftPanel(addressList);
            setVisible(false);

        }


        setSize(500, 500);
    }
}
