package MyDialogPackage;

import MyDataType.AddressDataType;
import MyPanelPackage.AddressListPanel;
import MyGUI.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;

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
        nameTextField.setFont(new Font("D2Coding", Font.PLAIN, 30));
        numberTextField.setFont(new Font("D2Coding", Font.PLAIN, 30));
        emailTextField.setFont(new Font("D2Coding", Font.PLAIN, 30));

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
                ArrayList<AddressDataType> tmpList;
                String name = nameTextField.getText();
                String number = numberTextField.getText();
                String email = emailTextField.getText();

                addressData = new AddressDataType(name, number, email, imgIcon);

                tmpList = AddressBook.saveData(addressData);

                for (AddressDataType data : tmpList) {
                    System.out.println(data);
                }

                leftPanel.updateLeftPanel(tmpList);
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

        // esc로 닫기
        nameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setVisible(false);
                }
            }
        });


        setSize(500, 1000);
    }
}
