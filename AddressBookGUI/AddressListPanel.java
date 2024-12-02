import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

// 왼쪽 Panel에 대한 클래스 ( 연락처에 대한 정보 출력 [ 이름 번호 ] )
public class AddressListPanel extends JPanel {
    //
    private static Vector<AddressDataType> addressData;
    private JList<AddressDataType> addressJList;

    // AddressData 타입으로 연락처를 저장할 공간을 생성
    public AddressListPanel(JFrame f, ArrayList<AddressDataType> addressList){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        if (addressList != null) {
            addressData = new Vector<>(addressList);
        } else {
            addressData = new Vector<>();
        }

        addressJList = new JList<>(addressData);
        // Vector에 잘 들어갔는지 확인 코드
//        System.out.println("Vector 데이터 확인");
//        for (AddressDataType i : addressData) {
//            System.out.printf("chk : ");
//            System.out.println(i);
//        }

        JScrollPane scrollPane = new JScrollPane(addressJList);
        add(scrollPane, BorderLayout.CENTER);

        // JList에 익명함수 리스너 등록
        addressJList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    DetailDialog detailDialog = new DetailDialog(f, "자세한 정보", addressJList.getSelectedValue());
                    detailDialog.setVisible(true);
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

        addressJList.setListData(addressData);
        repaint();
    }
    // 검색할 때 JList 업데이트 메소드
    public void updateSearchLeftPanel(ArrayList<AddressDataType> newAddressData) {
        addressData.clear();
        addressData.addAll(newAddressData);
        addressJList.setListData(addressData);
        repaint();

    }
}
