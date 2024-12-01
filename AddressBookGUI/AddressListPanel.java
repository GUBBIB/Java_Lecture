import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

// 왼쪽 Panel에 대한 클래스 ( 연락처에 대한 정보 출력 [ 이름 번호 ] )
public class AddressListPanel extends JPanel {
    //
    private static Vector<AddressDataType> addressData;
    private JList<AddressDataType> addressJList;

    // AddressData 타입으로 연락처를 저장할 공간을 생성
    public AddressListPanel(ArrayList<AddressDataType> addressList){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        if (addressList != null) {
            addressData = new Vector<>(addressList);
        } else {
            addressData = new Vector<>();
        }

        addressJList = new JList<>();
        // Vector에 잘 들어갔는지 확인 코드
//        System.out.println("Vector 데이터 확인");
//        for (AddressDataType i : addressData) {
//            System.out.printf("chk : ");
//            System.out.println(i);
//        }
        
        JScrollPane scrollPane = new JScrollPane(addressJList);
        add(scrollPane, BorderLayout.CENTER);



    }

    // 추가, 삭제, 수정시 JList 업데이트 메소드
    public  void updateLeftPanel(ArrayList<AddressDataType> newAddressData) {
        addressData.clear();
        addressData.addAll(newAddressData);

        addressJList.setListData(addressData);
        repaint();
    }
}
