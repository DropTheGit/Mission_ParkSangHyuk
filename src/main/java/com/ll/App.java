package com.ll;

import java.util.Scanner;

public class App {

    private Scanner scanner;
    private QuotationController qc;

    public void run() {
        System.out.println("== 명언 앱 ==");
        scanner = new Scanner(System.in);
        qc = new QuotationController(scanner);


        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();
            Rq rq = new Rq(cmd);

            switch (rq.action) {
                case "종료":
                    return;
                case "등록":
                    qc.actionWrite();
                    break;
                case "목록":
                    qc.actionList();
                    break;
                case "삭제":
                    qc.actionRemove(rq);
                    break;
                case "수정":
                    qc.actionModify(rq);
                    break;
                // 테스트 데이터를 입력하기 위한 명령 (예시)테스트?volume=10
                case "테스트":
                    qc.actionTestData(rq);
                    break;
            }
        }
    }
}