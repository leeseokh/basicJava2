package controller;

import util.ScanUtil;

public class Map {
 public static void main(String[] args) {
	
	 
			System.out.println("━━━━━━━━━━━━━━━━MINI MAP━━━━━━━━━━━━━━━━");
			System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
			System.out.println("┃ 1.🎠	       2.🎃            	9.🎪                        0.입구 ");
			System.out.println("┃       3.🎇                                                                                  ┗━ ");
			System.out.println("┃                 5.🎡   	   4.🎆               8.출구 ");
			System.out.println("┃  10.🎪                                                                                              ┏━ ");
			System.out.println("┃                  6.🎢                                                 ┗━ ");
			System.out.println("┃12.🛍 	   7.🎄                                         11.🍔🍕                     13.🚙🚕🏍   ");
			System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");		
			System.out.println("0.입구          1.회전목마   2.유령의집    3.자이드롭    4.드롭자이   5.대관람차        ");
			System.out.println("6.롤러코스터 7.숲속체험관8.출구   9-10.화장실     11.식당       12.기념품 가게   ");
			System.out.println("13.주차장                                                                                                             ");
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");		
			System.out.print("확인 하실 시설 번호를 입력해주세요 (1-7) >>");		
			int select = ScanUtil.nextInt();
			if(select == 1)System.out.println("🎠-회전목마: 영유아는 보호자의 보호아래 같이 탑승 가능합니다.");							
		    if(select == 2)System.out.println("🎃-유령의 집: 심장이 약하신분은 입장을 삼가해주세요.");	
			if(select == 3)System.out.println("🎇-자이드롭: 신장 135cm이하는 입장을 삼가해주세요. ");	
			if(select == 4)System.out.println("🎆-드롭자이: 신장 135cm이하는 입장을 삼가해주세요. ");
			if(select == 5)System.out.println("🎡-대관람차: 영유아는 보호자의 보호아래 같이 탑승 가능합니다.");
			if(select == 6)System.out.println("🎢-롤러코스터: 신장 145cm이하는 입장을 삼가해주세요.");
			if(select == 7)System.out.println("🎄-숲속체험관: 식물 전시관으로 숲속 체험이 가능합니다.");
								
			}
			
			
		}






