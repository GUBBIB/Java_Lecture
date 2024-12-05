package MyPopupMenu;

import MyDataType.AddressDataType;
import MyPanelPackage.AddressListPanel;
import MyPanelPackage.FunctionPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// https://blog.naver.com/javaking75/140158271945 PopupMenu 에 대해서 참고
public class PopupMenu extends JPopupMenu {


    public PopupMenu(JFrame parentFrame, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel, AddressDataType selectedValue) {

        // "연락처 추가" 메뉴 항목
        JMenuItem addContactMenuItem = new JMenuItem("연락처 추가");
        addContactMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FunctionPanel.addBTN(parentFrame, addressList, leftPanel);
                setVisible(false);
            }
        });
        add(addContactMenuItem);

        JMenuItem deleteMenuItem = new JMenuItem("삭제");
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FunctionPanel.deleteBTN(parentFrame, addressList, leftPanel, selectedValue);
                setVisible(false);
            }
        });
        add(deleteMenuItem);

        // "수정" 메뉴 항목
        JMenuItem modifyMenuItem = new JMenuItem("수정");
        modifyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FunctionPanel.modifyBTN(parentFrame, addressList, leftPanel, selectedValue);
                setVisible(false);
            }
        });
        add(modifyMenuItem);

    }
}
