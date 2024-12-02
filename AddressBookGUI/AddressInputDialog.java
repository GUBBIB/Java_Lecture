import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 연락처 추가 버튼 클릭 이벤트 클래스
public class AddressInputDialog extends JDialog {


    private JTextField nameTextField = new JTextField(40);
    private JTextField numberTextField = new JTextField(40);
    private JTextField emailTextField = new JTextField(40);
    private JLabel nameLabel = new JLabel("이름 :");
    private JLabel numberLabel = new JLabel("번호 :");
    private JLabel emailLabel = new JLabel("이메일 :");
    private JButton okBtn = new JButton("추가");
    private JButton exitBtn = new JButton("닫기");
    private JButton chooserBtn = new JButton("사진 추가");
    private JFileChooser chooser = new JFileChooser();
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
    private ImageIcon imgIcon;


    private static AddressDataType addressData;
    // chk를 통해서 1개 입력인지 n개 입력인지 구분
    public AddressInputDialog(JFrame f, String title, ArrayList<AddressDataType> addressList, AddressListPanel leftPanel){
        super(f, title, true);

        chooser.setFileFilter(filter);
        setLayout(new GridLayout(4, 3, 5, 10));
        nameTextField.setFont(new Font("SansSerif", Font.PLAIN, 30));
        numberTextField.setFont(new Font("SansSerif", Font.PLAIN, 30));
        emailTextField.setFont(new Font("SansSerif", Font.PLAIN, 30));

        // 0 = name // 1 = number // 2 = email
        String[] arr = new String[3];


        add(nameLabel);
        add(nameTextField);
        add(new JPanel());
        add(numberLabel);
        add(numberTextField);
        add(new JPanel());
        add(emailLabel);
        add(emailTextField);
        add(new JPanel());
        add(chooserBtn);
        add(okBtn);
        add(exitBtn);


        // ---------- 버튼들 ActionListener 추가 ----------

        // 사진 추가 버튼
        chooserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int ret = chooser.showOpenDialog(null);

                if(ret == JFileChooser.APPROVE_OPTION){
                    String pigPath = chooser.getSelectedFile().getAbsolutePath();
                    imgIcon = new ImageIcon(pigPath);
                }
            }
        });

        // 추가 버튼
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameTextField.getText();
                String number = numberTextField.getText();
                String email = emailTextField.getText();

                addressData = new AddressDataType(name, number, email, imgIcon);
//                addressList.add(new AddressDataType(name, number, email, imgIcon));

                AddressBook.saveData(addressData);

                leftPanel.updateLeftPanel(addressList);
                setVisible(false);
            }
        });

        // 닫기 버튼
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                imgIcon = null;
                setVisible(false);

            }
        });


        setSize(500, 1000);
    }
}
