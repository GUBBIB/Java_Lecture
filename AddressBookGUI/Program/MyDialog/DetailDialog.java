package MyDialog;

import MyDataType.AddressDataType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DetailDialog extends JDialog {
    public DetailDialog(JFrame f, String title, AddressDataType selectedValue) {
        super(f, title, true);

        int x = 500, y = 500;
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));

        String str = "이름: " + selectedValue.getName() + "\n" +
                "번호: " + selectedValue.getNumber() + "\n" +
                "이메일: " + selectedValue.getEmail() + "\n" +
                "사진 경로: " + selectedValue.getPig();
        JTextArea label = new JTextArea(str);
        panel.add(label, BorderLayout.NORTH);

        label.setEditable(false);

        if(selectedValue.getPig() != null){
            ImageIcon pig = selectedValue.getPig();
            JLabel imgLabel = new JLabel(pig);

            panel.add(imgLabel, BorderLayout.CENTER);
            x = Math.max(pig.getIconWidth(), 500);
            y = Math.max(pig.getIconHeight()+180, 500);
        } else {
            ImageIcon pig = null;
        }

        JButton btn = new JButton("닫기");
        panel.add(btn, BorderLayout.SOUTH);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER){
                    setVisible(false);
                }
            }
        });
        label.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setVisible(false);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        setSize(x, y);
        setFocusable(true);
    }
}
