# CafeSytstem
2020-2 객체지향프로그래밍 프로젝트 : 자바 GUI를 활용한 카페 앱 시스템

<h2>프로젝트 개요</h2>
<p>- 목표 : 메뉴 검색 및 주문 등 카페 앱 기능 구현
<p>- 개발 환경 : Eclipse
<p>- 사용 언어 : Java

<h2>패키지 구성</h2>
<p>- cafe : 카페 데이터 관련 클래스
<p>- mgr : cafe 패키지의 클래스가 사용하는 인터페이스
<p>- gui : 사용자의 GUI 화면
<p>- staffgui : 관리자의 GUI 화면

![gdhyjy](https://user-images.githubusercontent.com/75527311/121787300-497e1c80-cc00-11eb-80f3-c1b5905087f8.PNG)

<hr>

<h2>실행 화면</h2>
<h3>로그인 화면</h3>
<div align="center">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787499-49325100-cc01-11eb-9353-9b76af5ab3f1.png">
  <p></p>
  <p>아이디, 비밀번호 입력하여 로그인을 진행함
  <p>고객 계정일 경우 -> 고객 메인 화면으로, 관리자 계정일 경우 -> 관리자 메인 화면으로 이동
</div>

<h3>고객 메인 화면</h3>
<div align="center">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787566-bcd45e00-cc01-11eb-9854-41ea5869fda2.png">
  <p></p>
  <p>메인 화면 구성 : 고객 맞춤 추천 메뉴, 메뉴 목록, 후기 목록, 고객 메뉴로 되어있음
  <p>고객 메뉴 : 마이페이지, 장바구니, 검색
</div>

<h3>추천 메뉴</h3>
<div align="center">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787627-16d52380-cc02-11eb-9f3d-99487fb5ec55.png">
  <p></p>
  <p>해당 고객의 주문 내역을 바탕으로 맞춤 추천 메뉴를 제공함
  <p>주문한 음료의 종류와 해시태그를 분석하여 나온 결과
</div>

<h3>메뉴 목록</h3>
<div align="center">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787661-4f74fd00-cc02-11eb-8f54-f651b9d35ced.png">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787666-60257300-cc02-11eb-8032-821980a6e37a.png">
  <p></p>
  <p>카페의 메뉴 목록(음료, 디저트)을 출력함
  <p>별점순, 이름 오름차순/내림차순, 판매량순의 정렬 가능
  <p>메뉴 카드 클릭 시 해당 메뉴의 상세 정보 확인 가능
</div>

<h3>후기</h3>
<div align="center">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787704-a5e23b80-cc02-11eb-9079-7c3f6829c69f.png">
  <p></p>
  <p>카페 앱 이용 고객이 작성한 후기 목록 출력</p>
    
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787723-ce6a3580-cc02-11eb-926f-0643a6979036.png">
  <p></p>
  <p>후기 작성 페이지
</div>

<h3>상세 검색</h3>
<div align="center">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787732-e641b980-cc02-11eb-9e78-e65c289f1a9a.png">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787735-eb9f0400-cc02-11eb-8685-39762a6da995.png">
  <p></p>
  <p>검색하고자 하는 메뉴의 가격대, 종류, 해시태그 및 알레르기 재료 제외 여부를 입력하여 통합 검색 진행
</div>

<h3>장바구니 및 주문</h3>
<div align="center">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787782-2bfe8200-cc03-11eb-8e2a-644d1f2b6b44.png">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787788-315bcc80-cc03-11eb-90f7-46467998efdf.png">
  <p></p>
  <p>주문할 메뉴들을 장바구니에 담고 전체 주문 진행
</div>

<h3>메뉴 정보 수정</h3>
<div align="center">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787813-610ad480-cc03-11eb-9715-e8c9e0833005.png">
  <img width="300" src="https://user-images.githubusercontent.com/75527311/121787819-6700b580-cc03-11eb-9888-3f9ffdfa9cbd.png">
  <p></p>
  <p>카페의 메뉴 정보를 수정함 (관리자 기능)
</div>
