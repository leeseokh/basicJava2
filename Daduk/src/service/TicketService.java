package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.TicketDao;
import util.Jdbcutil;
import util.View;
import util.sc;

public class TicketService {
	private TicketService() {
	} 

	private static TicketService instance;
	Jdbcutil jdbc = Jdbcutil.getInstance();

	public static TicketService getInstence() {
		if (instance == null) {
			instance = new TicketService();
		}
		return instance;
	}

	private TicketDao ticketDao = TicketDao.getInstence();

	public int TicketList() {
		List<Map<String, Object>> TicketList = ticketDao.selectTicketList();

		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println(" 번호\t 이름\t\t 가격");
		System.out.println("────────────────────────────────");
		for (Map<String, Object> TICKET : TicketList) {
			System.out.printf(" %s", TICKET.get("TICKET_ID") + "\t");
			System.out.printf(" %s", TICKET.get("TICKET_NAME") + "\t");
			System.out.printf(" %s", TICKET.get("TICKET_PRICE"));
			System.out.println();
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print(" 0.뒤로가기");
		String sql4 = "SELECT CODE FROM CLIENT WHERE USER_ID = ?";
		ArrayList<Object> param2 = new ArrayList<>();
		param2.add(Controller.LoginUser.get("ADMIN_ID"));
		List<Map<String, Object>> list1 = jdbc.selectList(sql4, param2);
		for (int i = 0; i < list1.size(); i++) {
			Map<String, Object> row = list1.get(i);
			for (String key : row.keySet()) {
				if (row.get(key).equals("1")) {
					System.out.print("\t419.등록\t423.삭제"); // 관리자 전용
				}
			}
		}
		System.out.println();
		System.out.println();
		System.out.println("구매하실 티켓의 번호를 적어주세요");
		int ticket = sc.nextInt();
		if (ticket != 0 && ticket != 419 && ticket != 423) {
			int price3 = 0;
			TicketList = ticketDao.selectTicketList();
			for (Map<String, Object> TICKET : TicketList) {
				if (Integer.parseInt(String.valueOf(TICKET.get("TICKET_ID"))) == ticket) {
					String Ticket = "INSERT INTO BUY VALUES ((SELECT NVL(MAX(BUY_NUM), 0) + 1 FROM BUY),?,?,?,?,?)";
					System.out.println("방문일자를 입력해주세요");
					String date = sc.nextLine();
					System.out.println("구매하실 수량을 입력해주세요");
					int count = sc.nextInt();
					String pr = "SELECT * FROM TICKET WHERE TICKET_ID = ?";
					ArrayList<Object> price = new ArrayList<>();
					price.add(ticket);

					List<Map<String, Object>> rs = jdbc.selectList(pr, price);
					for (int i = 0; i < rs.size(); i++) {
						rs.get(i);
						Map<String, Object> p = rs.get(i);
						price3 = Integer.parseInt(String.valueOf(p
								.get("TICKET_PRICE"))) * count;
					}
					ArrayList<Object> param = new ArrayList<>();
					param.add(date);
					param.add(price3);
					param.add(count);
					param.add(Controller.LoginUser.get("USER_ID"));
					param.add(ticket);

					int result = jdbc.update(Ticket, param);
					int Deduction = 0;
					if (result > 0) {
						System.out.println("total : " + price3);
						System.out.println("1.결제 \t 2.취소");
						int input1 = sc.nextInt();

						switch (input1) {
						case 1:
							System.out.println("결제하실 금액 : " + price3);

							Object pnt4 = null;
							String pnt = "SELECT USER_CARDNUM FROM CLIENT WHERE USER_ID = ?";
							ArrayList<Object> pnt1 = new ArrayList<>();
							pnt1.add(Controller.LoginUser.get("USER_ID"));

							List<Map<String, Object>> pnt2 = jdbc.selectList(
									pnt, pnt1);

							for (int i = 0; i < pnt2.size(); i++) {
								pnt2.get(i);
								Map<String, Object> pnt3 = pnt2.get(i);
								pnt4 = pnt3.get("USER_CARDNUM");
							}
							String membership = "SELECT POINT FROM MEMBERSHIP WHERE USER_CARDNUM = ?";
							param = new ArrayList<>();
							param.add(pnt4);
							List<Map<String, Object>> pnt5 = jdbc.selectList(
									membership, param);

							for (int i = 0; i < pnt5.size(); i++) {
								Map<String, Object> map = pnt5.get(i);
								System.out.println("사용 가능하신 포인트 : "
										+ map.get("POINT"));
							}

							int total = 0;
							System.out.println("포인트를 사용하시겠습니까? 1.Y 2.N");
							int input3 = sc.nextInt();
							switch (input3) {
							case 1:
								System.out.println("사용하실 포인트를 적어주세요");
								int input4 = sc.nextInt();
								total = price3 - input4;

								Map<String, Object> map = null;
								for (int i = 0; i < pnt5.size(); i++) {
									map = pnt5.get(i);
								}
								String PointUpdate = "UPDATE MEMBERSHIP SET POINT = ? WHERE USER_CARDNUM=?";

								param = new ArrayList<>();
								param.add(Integer.parseInt(String.valueOf(map
										.get("POINT"))) - input4);
								param.add(pnt4);
								Deduction = jdbc.update(PointUpdate, param);

								System.out.println("결제 금액 : " + total);
								System.out.println("결제하시겠습니까 ? 1.Y 2.N");
								int r = sc.nextInt();
								if (r == 1) {
									System.out.println("결제가 완료되었습니다.");
									price3 = total;
									break;
								}
								if (r == 2) {
									System.out.println("결제가 취소되었습니다.");
									String sqlDelete = "DELETE FROM BUY WHERE BUY_NUM = (SELECT MAX(BUY_NUM) FROM BUY)";
									int result1 = jdbc.update(sqlDelete);

									return TicketList();
								}

							case 2:
								System.out.println("결제하실 금액 : " + price3);
								System.out.println("결제하시겠습니까 ? 1.Y 2.N");
								r = sc.nextInt();
								if (r == 1) {
									System.out.println("결제가 완료되었습니다.");
									break;
								}
								if (r == 2) {
									System.out.println("결제가 취소되었습니다.");
									String sqlDelete = "DELETE FROM BUY WHERE BUY_NUM = (SELECT MAX(BUY_NUM) FROM BUY)";
									int result1 = jdbc.update(sqlDelete);
									return TicketList();
								}
							}

							int dc = price3 / 20;
							String POINT = "UPDATE MEMBERSHIP SET POINT = ? WHERE USER_CARDNUM=?";

							String dcp = "SELECT POINT FROM MEMBERSHIP WHERE USER_CARDNUM = ?";
							ArrayList<Object> dcp1 = new ArrayList<>();
							dcp1.add(pnt4);
							List<Map<String, Object>> dcp2 = jdbc.selectList(
									dcp, dcp1);
							Map<String, Object> map = null;
							for (int i = 0; i < dcp2.size(); i++) {
								map = dcp2.get(i);
							}

							param = new ArrayList<>();
							param.add(Integer.parseInt(String.valueOf(map
									.get("POINT"))) + dc);
							param.add(pnt4);

							Deduction = jdbc.update(POINT, param);

							System.out.println("적립 포인트 : " + dc);
							break;
						case 2:
							System.out.println("결제가 취소되었습니다.");
							String sqlDelete = "DELETE FROM BUY WHERE BUY_NUM = (SELECT MAX(BUY_NUM) FROM BUY)";
							int result1 = jdbc.update(sqlDelete);
						}
					}
				}
			}
		} else if (ticket == 0) {
			return View.main;
		} else if (ticket == 419) {
			String TicketInsert = "INSERT INTO TICKET VALUES ((SELECT NVL(MAX(TICKET_ID), 0) + 1 FROM TICKET), ?, ?)";

			System.out.println("등록하실 티켓의 이름을 적어주세요");
			String ticketname = sc.nextLine();
			System.out.println("등록하실 티켓의 가격을 적어주세요");
			String ticketprice = sc.nextLine();

			ArrayList<Object> param = new ArrayList<>();
			param.add(ticketname);
			param.add(ticketprice);

			int result = jdbc.update(TicketInsert, param);
			if (result > 0) {
				System.out.println("등록이 완료 되었습니다.");
			}
		} else if (ticket == 423) {
			String TicketDelete = "DELETE FROM TICKET WHERE TICKET_ID = ?";
			System.out.println("삭제하실 티켓의 아이디를 입력해 주세요");
			int ticketid = sc.nextInt();

			ArrayList<Object> param = new ArrayList<>();
			param.add(ticketid);

			int result = jdbc.update(TicketDelete, param);
			if (result > 0) {
				System.out.println("삭제가 완료 되었습니다.");
			}
		}
		return View.Ticket;
	}
}
