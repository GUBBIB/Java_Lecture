package MyDataType;

import javax.swing.*;

// 연락처를 저장할 타입 클래스
public class AddressDataType {
    private String name, number, email;
    private ImageIcon pig;

    public AddressDataType(){
        name = number = email = "null";
        pig = null;
    }

    public AddressDataType(String name, String number, String email, ImageIcon pig) {
        this();
        this.name = name;
        this.number = number;
        this.email = email;
        this.pig = pig;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public ImageIcon getPig() {
        return pig;
    }

    // AddressListPanel 의 JList 에 보일 형태
    @Override
    public String toString() {
        return getName() + "  || " + getNumber();
    }
}
