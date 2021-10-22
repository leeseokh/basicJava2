package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Hotel implements Serializable {

	static Scanner scan = new Scanner(System.in);
	static Map<Integer, Hotel1> hotelmap = new HashMap<>();

	public void displayMenu() {
		System.out.println("어떤 업무를 하시겠습니까?");
		System.out.print("1.체크인\t2.체크아웃\t3.객실상태\t4.업무종료");
		System.out.println("번호입력>>>");
	}

	public void Hotelbookstart() {
		System.out.println("====================");
		System.out.println("호텔문을 열었습니다.");
		System.out.println("====================");

		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("d:/D_Other/Hotel.bin")));

			Object obj = null;
			obj = ois.readObject();

			hotelmap = (HashMap<Integer, Hotel1>) obj;

			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		while (true) {
			displayMenu();
			int menu = scan.nextInt();
			switch (menu) {
			case 1:
				checkin();
				break;
			case 2:
				checkout();
				break;
			case 3:
				room();
				break;
			case 4:
				try {
					// 객체를 파일에 저장하기

					// 출력용 스트림 객체 생성
					ObjectOutputStream oos = new ObjectOutputStream(
							new BufferedOutputStream(new FileOutputStream("d:/D_Other/Hotel.bin")));

					oos.writeObject(hotelmap); // 직렬화

					System.out.println("쓰기 작업 완료");
					oos.close();

					// ========================================================

				} catch (Exception e) {
					e.printStackTrace();
					// 더 이상 읽어올 객체가 없으면 예외 발생함.
				}
				System.exit(0);

			}
		}
	}

	private void room() {
		System.out.println("어느방을 조회하시겠습니까?");
		System.out.println("방 벙호>");
		Integer roomnum = scan.nextInt();
		Hotel1 h = hotelmap.get(roomnum);
		if (h == null) {
			System.out.println(roomnum + " 번 방은 아무도 체크인 하시지않았습니다.");
		} else {
			System.out.println(roomnum + "호의 정보.");
			System.out.println("투숙객 :" + h.getName());
			System.out.println("호실 : " + h.getRoomnum());
		}
		System.out.println("조회 완료.");
	}

	private void displayAll() {
		Set<Integer> keySet = hotelmap.keySet();
		System.out.println("==============================");
		System.out.println("t이름\t방번호");
		System.out.println("==============================");

		if (keySet.size() == 0) {
			System.out.println("등록된 게 없다");
		} else {
			Iterator<Integer> it = keySet.iterator();

			while (it.hasNext()) {

				Integer roomnum = it.next();// 키값
				Hotel1 h = hotelmap.get(roomnum);// value
				System.out.println("\t" + h.getRoomnum() + "\t" + h.getName());
			}

			System.out.println("=======================================");
			System.out.println("출력완료,,");
		}
	}

	public static void main(String[] args) {

		new Hotel().Hotelbookstart();
	}

	private void checkout() {
		System.out.println();
		System.out.println("어느방에서 나가시나요?");
		System.out.println("방 벙호>");
		Integer roomnum = scan.nextInt();
		if (hotelmap.remove(roomnum) == null) {
			System.out.println(roomnum + "에 묵은적이 없습니다.");
		} else {
			System.out.println(" 안녕히가세여");
		}
	}

	private void checkin() {
		System.out.println();
		System.out.println("어느방에 체크인 하시겠습니까?");
		System.out.println("방 벙호>");
		int roomnum = scan.nextInt();
		if (hotelmap.get(roomnum) != null) {
			System.out.println(roomnum + "은 이미 팔렸습니다.");
			return;
		}
		System.out.println("이름 >");
		String name = scan.next();
		hotelmap.put(roomnum, new Hotel1(roomnum, name));
		System.out.println(roomnum + "호실 등록완료.");
	}

	class Hotel1 implements Serializable {

		public int getRoomnum() {
			return roomnum;
		}

		public Hotel1(int roomnum, String name) {
			super();
			this.roomnum = roomnum;
			this.name = name;
		}

		public void setRoomnum(int roomnum) {
			this.roomnum = roomnum;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Hotel1 [roomnum=" + roomnum + ", name=" + name + "]";
		}

		private int roomnum;
		private String name;
	}
}
