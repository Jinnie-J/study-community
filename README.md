# 스터디, 프로젝트 팀원 모집 커뮤니티 LINK 
</br>

##  개요
- 스터디와 프로젝트 등의 모임을 만들고, 팀원을 구하기 위한 커뮤니티를 제공합니다.
- 이 프로젝트는 다음과 같은 목표를 갖습니다.
  - 중복되는 코드들과 불필요하게 수정이 일어날 코드들을 최소화하여 유지보수가 용이한 코드를 구현하고자 노력합니다.
  - SOLID 원칙과 디자인패턴의 이해를 바탕으로 객체 지향의 원리를 적용하여 클린코드를 작성하도록 노력합니다.
  - 테스트 코드를 통해 코드 품질을 향상시키고자 노력합니다.
  

</br>

## 화면 설계
- 카카오 오븐
  https://ovenapp.io/project/08O9rlQGBuIYVQtozSKTvgevitEIyj1L#AMazN
  
- 화면 목록
  ![화면목록](https://user-images.githubusercontent.com/62706198/218517775-160b9e98-bb06-4c4a-9c42-29e2301d061e.jpeg)
  
  
</br>
</br>


## 기술 스택 & 아키텍처
### 기술 스택
- Java
- SpringBoot
- JPA, QueryDsl
- MySQL
- thymeleaf
- JUnit5

### 아키텍처
- ERD
![ERD](https://user-images.githubusercontent.com/62706198/218522366-82de566b-579b-43c6-aaed-0acb9789619a.png)
  
  
</br>


## Usecase
### Common
- user는 회원 가입을 통해 Role을 획득할 수 있다.
- user는 회원 탈퇴를 할 수 있다.
- user는 로그인을 통해 서비스를 사용할 수 있다.
- user는 로그아웃을 통해 서비스 사용을 종료할 수 있다.
- user는 자신의 지역과 기술을 등록하여 관리할 수 있다.
- user는 전체 검색을 통해 원하는 모임을 찾을 수 있다.
- user는 나의 모임 메뉴를 통해 내가 만든 모임, 참가중인 모임, 신청 대기중인 모임, 지난 모임 등을 확인할 수 있다.

### Leader
- 새로운 모임을 등록하면 모임의 Leader가 된다.
- Leader는 진행 방식, 지역, 모집 인원, 진행 기간, 사용 기술 등의 정보를 가진 모임을 개설할 수 있다.
- Leader는 모집 인원 내에서 참가자들의 참가신청을 수락 또는 거절 할 수 있다.
- Leader는 모임 모집을 마감할 수 있다.

### Member
- 모임에 참가신청을 하여 Leader의 수락을 받으면 해당 모임의 Member가 된다.
- Member는 알림을 통해 참가 신청 현황을 확인할 수 있다.
- Member는 해당 모임에 질문을 남길 수 있다.

</br>


## 프로젝트를 통해 알게된 내용(직접 정리한 내용)

### Back-end
1. [연관 관계가 복잡해질 때, @EqualsAndHashCode에서 서로 다른 연관 관계를 순환 참조 하느라 무한 루프가 발생하고, 결국 stack overflow가 발생할 수 있기 때문에 주로 id 값만 비교하도록 사용한다.](https://jinniedev.tistory.com/2)

2. [정적 리소스들 또한 Spring Security에 걸리기 때문에 Filter 설정을 해주어야 한다.](https://jinniedev.tistory.com/3)

3. [패스워드를 평문으로 저장해서는 안된다.](https://jinniedev.tistory.com/5)

4. [Open EntityManager In View 필터](https://jinniedev.tistory.com/47)

5. [Setter의 사용을 지양해야 한다.](https://jinniedev.tistory.com/6)

5. [Entity와 DTO를 분리하여 사용해야 한다.](https://jinniedev.tistory.com/10)

6. [JPA의 리턴타입이 Optional인 이유](https://jinniedev.tistory.com/7)

7. [@ManyToMany 사용할 시 주의할 점](https://jinniedev.tistory.com/11)

8. [JPA에서는 코드 테이블의 형태를 EnumType으로 작성한다.](https://jinniedev.tistory.com/12)

9. [알림 구현 방식과 Spring의 @EventListener](https://jinniedev.tistory.com/13)

10. [JPA N+1 Select 문제와 해결](https://jinniedev.tistory.com/46)

### Front-end
1. [타임리프 댓글 수정 기능 구현 시 data-target 설정](https://jinniedev.tistory.com/40)

2. [Controller에서 타임리프로 model에 객체를 넘길 경우 null 처리 오류와 해결 방법](https://jinniedev.tistory.com/41)



