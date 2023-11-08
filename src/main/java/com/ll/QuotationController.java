package com.ll;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QuotationController {

    private Scanner scanner;
    private int id = 0;
    private ArrayList<Quotation> quotations = new ArrayList<>();

    public QuotationController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        id++;
        Quotation quotation = new Quotation(content, author, id);
        quotations.add(quotation);

        System.out.printf("%d번 명언을 등록하였습니다.\n", id);
    }

    public void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-----------------");

        if (quotations.isEmpty()) {
            System.out.println("현재 등록된 명언이 없습니다.");
        }

        for (int i = quotations.size() - 1; i >= 0; i--) {

            Quotation quotation = quotations.get(i);
            System.out.printf("%d / %s / %s\n", quotation.getId(), quotation.getAuthor(), quotation.getContent());
        }
    }

    public void actionRemove(Rq rq) {
        int indexOfId = findValidIndex(rq);
        if (indexOfId == -1) {
            return;
        }
        int valueOfId = rq.getValueByParamName("id", -1);

        quotations.remove(indexOfId);
        System.out.printf("%d번 명언이 삭제되었습니다.\n", valueOfId);

    }

    public void actionModify(Rq rq) {
        int indexOfId = findValidIndex(rq);
        if (indexOfId == -1) {
            return;
        }

        Quotation quotation = quotations.get(indexOfId);
        System.out.println("명언(기존) : " + quotation.getContent());
        System.out.print("명언(변경) : ");
        quotation.setContent(scanner.nextLine());
        System.out.println("작가(기존) : " + quotation.getAuthor());
        System.out.print("작가(변경) : ");
        quotation.setAuthor(scanner.nextLine());

        System.out.printf("%d번 명언이 수정되었습니다.\n", quotation.getId());
    }

    public void actionTestData(Rq rq) {
        int volume = rq.getValueByParamName("volume", -1);

        if (volume <= -1) {
            MessageIfInvalid(-1, "volume", volume);
        } else {
            for (int i = 0; i < volume; i++) {
                id++;
                String content = "명언" + id;
                String author = "작가" + id;

                Quotation quotation = new Quotation(content, author, id);
                quotations.add(quotation);
            }
            System.out.println("테스트 데이터 " + volume + "개가 등록되었습니다.");
        }
    }



    // 파라미터(id)의 값이 유효한 경우(정수o && 리스트에 존재) index값을 반환
    // 파라미터(id) 값이 유효하지 않는 경우(정수x || 리스트에 없음) -1을 반환
    public int findValidIndex(Rq rq) {
        int valueOfId = rq.getValueByParamName("id", -1);

        // 파라미터 값이 음수인 경우를 대비해 <= -1로 변경
        if (valueOfId <= -1) {
            MessageIfInvalid(-1, "id", valueOfId);
            return -1;
        }

        int indexOfId = findIndexById(valueOfId, -1);

        if (indexOfId == -1) {
            MessageIfInvalid(-2, "id",valueOfId);
            return -1;
        }

        return indexOfId;
    }


    // 파라미터 값이 유효하지 않는 경우, 경우에 따라 메시지 출력하는 메서드
    // code -1 : 파라미터의 값이 정수가 아닌 경우
    // code -2 : 파라미터의 값과 일치하는 객체가 리스트에 없는 경우
    public void MessageIfInvalid(int code, String paramName, int valueOfId) {
        if (code == -1) {
            System.out.printf("%s를 정확하게 입력해주세요.\n", paramName);
        }
        if (code == -2) {
            System.out.printf("%d번 명언이 존재하지 않습니다.\n", valueOfId);
        }
    }


    // id의 값이 주어졌을 때 해당 값이 quotations 리스트 몇 번째 자리(index)에 있는지 찾는 메서드
    public int findIndexById(int idValue, int defaultValue) {
        for (int i = 0; i < quotations.size(); i++) {
            Quotation quotation = quotations.get(i);
            if (quotation.getId() == idValue) {
                return i;
            }
        }
        return defaultValue;
    }

    public void actionSave() {
        try (FileOutputStream fos = new FileOutputStream("quotations.txt")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(quotations);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void actionLoad() {
        try (FileInputStream fis = new FileInputStream("quotations.txt")) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList quotations = (ArrayList) ois.readObject();
            this.quotations = quotations;
            //재실행시 명언 번호가 0으로 초기화가 되어 마지막 번호를 입력함
            //(Quotation)이 왜 들어가야 하는지 알아봐야할 듯...
            Quotation quotation = (Quotation) quotations.get(quotations.size()-1);
            this.id = quotation.getId();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}