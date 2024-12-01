import javax.swing.*;

// 연락처를 저장할 타입 클랙스
public class AddressDataType {
    private String name, number, email;
    private ImageIcon pig;

    // 모든 정보가 입력 됐을 때를 기준으로 입력이 안된 정보는 null 표시
    public AddressDataType(String name, String number) {
        this(name, number, null, null);
    }

    public AddressDataType(String name, String number, String email) {
        this(name, number, email, null);
    }

    public AddressDataType(String name, String number, ImageIcon pig) {
        this(name, number, null, pig);
    }

    public AddressDataType(String name, String number, String email, ImageIcon pig) {
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

    @Override
    public String toString() {
        return getName() + "  || " + getNumber();
    }
}
