package MyPanel;

import MyDialog.*;
import MyDataType.*;
import MyPopupMenu.PopupMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// 왼쪽 Panel에 대한 클래스 ( 연락처에 대한 정보 출력 [ 이름 번호 ] )
public class AddressListPanel extends JPanel {
    //
    private static Vector<AddressDataType> addressData;
    private JList<AddressDataType> addressJList;
    private PopupMenu popupMenu;

    // AddressData 타입으로 연락처를 저장할 공간을 생성
    public AddressListPanel(JFrame f, ArrayList<AddressDataType> addressList){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        if (addressList == null || !addressList.isEmpty()) {
            addressData = new Vector<>(addressList);
        } else {
            addressData = new Vector<>();
        }

        addressJList = new JList<>(addressData);
        addressJList.setFont(new Font("D2Coding", Font.PLAIN, 30));

        JScrollPane scrollPane = new JScrollPane(addressJList);
        add(scrollPane);

        // JList에 익명함수 리스너 등록
        addressJList.addMouseListener(new MouseAdapter() {
            // 마우스 우클릭에 관해서 https://velog.io/@maddie/JAVA-SWING-%EB%A7%88%EC%9A%B0%EC%8A%A4-%EB%A6%AC%EC%8A%A4%EB%84%88 참고
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // 더블클릭
                    DetailDialog detailDialog = new DetailDialog(f, "자세한 정보", addressJList.getSelectedValue());
                    detailDialog.setVisible(true);
                }
                else if(e.getButton() == MouseEvent.BUTTON3){ // 우클릭 시 팝업 창
                    popupMenu = new PopupMenu(f, addressList, AddressListPanel.this, addressJList.getSelectedValue());
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        addressJList.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    DetailDialog detailDialog = new DetailDialog(f, "자세한 정보", addressJList.getSelectedValue());
                    detailDialog.setVisible(true);
                }
            }
        });
    }

    // 추가, 삭제, 수정시 JList 업데이트 메소드
    public  void updateLeftPanel(ArrayList<AddressDataType> newAddressData) {
        addressData.clear();
        addressData.addAll(newAddressData);

        addressData.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        addressJList.setListData(addressData);
        repaint();
    }
}
