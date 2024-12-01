import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    }
}
