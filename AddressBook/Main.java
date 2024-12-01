//학번 : 2021963057
//이름 : 장문용
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        FileWriter fw; BufferedReader br; StringTokenizer st;
        HashMap<String, String> hm = new HashMap<>();

        String line = "", str;

        System.out.println("전화번호 입력 프로그램입니다.");
        // IoException 오류가 생기는걸 대비해서 try catch 문으로 예외처리
        try {
            fw = new FileWriter("c:\\temp\\phone.txt");

            // line 변수에 입력값을 받고 line이 "그만"인 경우 while문 탈출 아닌 경우 fw에 저장된 파일에 값 입력
            // 만약 아무 것도 입력하지 않았으면 이 사실을 알린 후, fw.write()는 넘기기 위해 continue 사용
            System.out.print("이름 전화번호 >> ");
            while(!(line = in.nextLine()).equals("그만")){


                if(line.isEmpty()){
                    System.out.println("아무 것도 입력하지 않았습니다. 다시 입력하세요.");
                    System.out.print("이름 전화번호 >> ");
                    continue;
                }
                fw.write(line);
                // windows에서 개행문자 처리 하는 방법
                fw.write("\r\n");
                System.out.print("이름 전화번호 >> ");
            }
            fw.close();
            System.out.println("c\\temp\\phone.txt에 저장하였습니다.");
            System.out.print("=================================\n\n");



            // BufferedReader를 이용해서 한 줄 씩 읽은 다음 읽은 줄은 출력 후 
            // StringTokenzier를 이용해서 띄워쓰기를 기준으로 나눈 다음, HashMap hm에 저장했습니다.
            br = new BufferedReader(new FileReader("c:\\temp\\phone.txt"));
            System.out.println("c:\\temp\\phone.txt를 출력합니다.");
            while((str = br.readLine()) != null){
                System.out.println(str);

                st = new StringTokenizer(str);
                hm.put(st.nextToken(), st.nextToken());
            }
            br.close();
            System.out.print("=================================\n\n");
            




            // line 변수에 입력값을 받고 line이 "그만" 인 경우 탈출
            // 아닌 경우, line을 키값으로 hm에 들어있는지 비교후 들어있으면 value 값 출력 
            // 아닌 경우, 없다고 출력
            System.out.printf("총 %d개의 전화번호를 읽었습니다.\n", hm.size());
            System.out.print("이름 >> ");
            while(!(line = in.nextLine()).equals("그만")){
                if(hm.containsKey(line)) System.out.println(hm.get(line));
                else System.out.println("찾는 이름이 없습니다.");
                System.out.print("이름 >> ");
            }
            System.out.print("=================================\n\n");

        } catch (IOException e){
            System.out.println("입출력 오류");
        }

    }
}
