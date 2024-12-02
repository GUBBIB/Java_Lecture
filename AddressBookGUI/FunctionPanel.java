import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 오른쪽 Panel 에 대한 클래스
public class FunctionPanel extends JPanel {

    private JTextField tf = new JTextField(10);
    private JButton search = new JButton("검색");
    private JButton add = new JButton("연락처 추가");
    private JButton modify = new JButton("수정");
    private JButton delete = new JButton("삭제");

    private AddressInputDialog dialog;
    public FunctionPanel(JFrame parentFrame, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel){
        // 레이아웃 설정
        setLayout(new GridLayout(3, 2, 5 ,100));

        // 글자 크기 수정
        tf.setFont(new Font("SansSerif", Font.PLAIN, 30));
        search.setFont(new Font("SansSerif", Font.PLAIN, 40));
        add.setFont(new Font("SansSerif", Font.PLAIN, 30));
        modify.setFont(new Font("SansSerif", Font.PLAIN, 40));
        delete.setFont(new Font("SansSerif", Font.PLAIN, 40));

        // 컴포넌트 추가
        add(tf);
        add(search);
        add(add);
        add(modify);
        add(delete);

        // --------- 모든 버튼 actionListener 추가 ---------


        // 연락처 추가 버튼
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dialog = new AddressInputDialog(parentFrame, "연락처 추가", addressList, leftPanel);
                dialog.setVisible(true);
            }
        });

        // 이름, 번호, 이메일, 사진경로 중 한개만 or 다 적었을 때 검색
        // 검색창에는 띄워쓰기를 기준으로 적음
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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
                leftPanel.updateSearchLeftPanel(tmpList);

            }
        });

        // 삭제 버튼 이벤트 리스너
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                File file = new File("src\\AddressBook\\");
                File[] files = file.listFiles();
                File tmpFile = null;
                AddressDataType deleteAddress = null;

                String delete = JOptionPane.showInputDialog("삭제할 연락처의 이름 또는 번호를 입력하세요");
                Boolean chk = false;

                try {
                    for (AddressDataType data : addressList) {
                        if (data.getName().equals(delete) || data.getNumber().equals(delete)) {
                            deleteAddress = data;
                            break;
                        } else {
                            deleteAddress = new AddressDataType();
                        }
                    }

                    for (File deleteFile : files) {
                        if (deleteFile.getName().contains(deleteAddress.getName())) {
                            tmpFile = deleteFile;
                            chk = true;
                            break;
                        }
                    }
                    // 파일 삭제 참고 https://javacpro.tistory.com/27
                    if (chk) {
                        if (tmpFile.delete()) {
                            JOptionPane.showMessageDialog(null, "삭제 완료");
                        } else {
                            JOptionPane.showMessageDialog(null, "연락처 없음", "경고", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "연락처 없음", "경고", JOptionPane.ERROR_MESSAGE);
                    }
                } catch(NullPointerException e){
                    JOptionPane.showMessageDialog(null, "연락처 없음", "경고", JOptionPane.ERROR_MESSAGE);
                }


                addressList.remove(deleteAddress);
                leftPanel.updateLeftPanel(addressList);
            }
        });
    }
}
