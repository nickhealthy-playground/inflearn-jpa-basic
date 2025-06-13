package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpql.entity.Member;
import jpql.entity.MemberType;
import jpql.entity.Team;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

//        /**
//         * 11. JPQL 기본 문법 - 기본 API
//         * - TypedQuery vs Query: 타입을 명확히 지정할 수 있을 때와 없을 때 구분해서 사용
//         * - getSingleResult() 주의사항 - 결과가 단 하나여야 에러가 발생하지 않음
//         * - 파라미터 바인딩 종류: 이름 기반(':이름' 형식으로 사용), 위치 기반(?1(위치번호))
//         */
//        try {
//            Member member = new Member();
//            member.setUsername("USER");
//            member.setAge(10);
//            em.persist(member);
//
//            // 타입을 정확히 알 수 있을 때 TypedQuery 사용
//            TypedQuery<Member> typedQuery = em.createQuery("select m from Member as m", Member.class);
//
//            // 타입을 정확히 지정할 수 없을 때(ex: String - username, int - age) Query 사용
//            Query query = em.createQuery("select m.username, m.age from Member as m");
//
//            // getSingleResult 사용 시 주의사항
//            // 1. 결과가 단 하나여야 한다. 아니면 NonUniqueResultException 에러 발생
//            // 2. 결과가 없으면 NoResultException 발생
//            Member singleResult = typedQuery.getSingleResult();
//            System.out.println("singleResult = " + singleResult.getUsername()); // USER
//
//            // 파라미터 바인딩 - 이름 기반(:이름)
//            // 메서드 체이닝도 가능
//            Member result = em.createQuery("select m from Member as m where username = :username", Member.class)
//                    .setParameter("username", "USER")
//                    .getSingleResult();
//
//            System.out.println("result.getUsername() = " + result.getUsername()); // USER
//
//            // 파라미터 바인딩 - 위치 기반(?) 사용
//            Member result2 = em.createQuery("select m from Member as m where username = ?1", Member.class)
//                    .setParameter(1, "USER")
//                    .getSingleResult();
//
//            System.out.println("result2.getUsername() = " + result2.getUsername()); // USER
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 11. JPQL 기본 문법 - 프로젝션
//         * - 프로젝션: SELECT 절에 조회할 대상을 지정하는 것
//         *   - SELECT m FROM Member m: 엔티티 프로젝션
//         *   - SELECT m.team FROM Member m: 엔티티 프로제션
//         *   - SELECT m.address FROM member m: 임베디드 타입 프로젝션
//         *   - SLEECT m.username, m.age FROM Member m: 스칼라 타입 프로젝션
//         *
//         * - 여러 값 조회 방법
//         *   - query 타입 조회
//         *   - Object[] 타입으로 조회
//         *   - new 타입으로 조회(Dto 생성 및 전체 패키지 작성 필요)
//         */
//        try {
//            Member member = new Member();
//            member.setUsername("USER");
//            member.setAge(10);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            // JPQL도 영속성 관리 대상임 -> update 쿼리 발생
//            List<Member> resultList = em.createQuery("select m from Member m", Member.class).getResultList();
//            Member findMember = resultList.get(0);
//            findMember.setAge(20);
//
//            // 아래와 같이 작성 가능하지만, 가시성을 위해 특정 엔티티에 다른 엔티티는 다음과 같이 JOIN을 명시적으로 작성해야 한다.
//            // em.createQuery("SELECT m.team FROM Member m");
//            List<Team> result = em.createQuery("SELECT t FROM Member m JOIN m.team t", Team.class).getResultList();
//
//            // 여러 값 조회 방법
//            // 1. Query 타입으로 조회
//            List memberList1 = em.createQuery("SELECT m.username, m.age FROM Member m").getResultList();
//            Object o = memberList1.get(0);
//            Object[] objects1 = (Object[]) o;
//            System.out.println("username = " + objects1[0]);
//            System.out.println("age = " + objects1[1]);
//
//            // 2. Object[] 타입으로 조회
//            List<Object[]> memberList2 = em.createQuery("SELECT m.username, m.age FROM Member m").getResultList();
//            Object[] objects2 = memberList2.get(0);
//            System.out.println("username = " + objects2[0]);
//            System.out.println("age = " + objects2[1]);
//
//            // 3. new 명령어로 조회
//            List<MemberDto> memberList3 = em.createQuery("SELECT new jpql.dto.MemberDto(m.username, m.age) FROM Member m", MemberDto.class).getResultList();
//            MemberDto memberDto = memberList3.get(0);
//            System.out.println("memberDto.getUsername = " + memberDto.getUsername());
//            System.out.println("memberDto.getAge() = " + memberDto.getAge());
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 11. JPQL 기본 문법 - 페이징
//         * - setFirstResult: 조회 시작 위치
//         * - setMaxResult: 조회할 데이터 수
//         */
//        try {
//
//            for (int i = 0; i < 100; i++) {
//                Member member = new Member();
//                member.setUsername("USER" + i);
//                member.setAge(i);
//                em.persist(member);
//            }
//
//            List<Member> resultList = em.createQuery("SELECT m FROM Member m order by age desc", Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("member.getAge() = " + member.getAge());
//            }
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 11. JPQL 기본 문법 - 조인
//         * - 조인 종류
//         *   - 내부 조인, 외부 조인, 세타 조인
//         * - 조인 ON 기능 지원, 연관관계 관련 없는 엔티티 외부 조인 지원(하이버네이트 5.1 부터)
//         */
//        try {
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("USER");
//            member.setAge(10);
//
//            member.changeTeam(team); // 편의 메서드(양방향 값 설정)
//            em.persist(member);
//
//            // 내부 조인(inner join)
//            String query = "SELECT m FROM Member m INNER JOIN m.team t";
//            List<Member> result = em.createQuery(query, Member.class).getResultList();
//
//            // 외부 조인(left/right outer join)
//            String query2 = "SELECT m FROM Member m LEFT OUTER JOIN m.team t";
//            List<Member> result2 = em.createQuery(query2, Member.class).getResultList();
//
//            // 세타 조인
//            String query3 = "SELECT m FROM Member m, Team t";
//            List<Member> result3 = em.createQuery(query3, Member.class).getResultList();
//
//            // ON - 조인 대상 필터링
//            String query4 = "SELECT m FROM Member m left join m.team t on t.name = 'teamA'";
//            List<Member> result4 = em.createQuery(query4, Member.class).getResultList();
//
//            // 연관관계 없는 엔티티 외부 조인
//            String query5 = "SELECT m FROM Member m LEFT JOIN Team t ON m.username = t.name";
//            List<Member> result5 = em.createQuery(query5, Member.class).getResultList();
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 11. JPQL 기본 문법 - 서브 쿼리
//         * 1. 서브 쿼리 지원 함수
//         *   - [NOT] EXISTS: 서브 쿼리에 결과가 존재하면 참
//         *   - {ALL | ANY | SOME}
//         *     - ALL: 모두 만족하면 참
//         *     - ANY, SOME: 같은 의미, 조건을 하나라도 만족하면 참
//         *   - [NOT] IN: 서브 쿼리의 결과 중 하나라도 같은 것이 있으면 참
//         *
//         * 2. JPA 서브 쿼리는 SELECT, FROM(하이버네이트 6이상), WHERE, HAVING 모두 가능
//         */
//        try {
//            Member member = new Member();
//            member.setUsername("USER");
//            member.setAge(10);
//
//            Team team = new Team();
//            team.setName("teamA");
//
//            member.changeTeam(team);
//
//            em.persist(member);
//            em.persist(team);
//
//            // 팀A 소속인 회원
//            String query = "SELECT m FROM Member m WHERE EXISTS(SELECT t FROM Team t WHERE t.name = 'teamA')";
//            List<Member> resultList = em.createQuery(query, Member.class).getResultList();
//            Member findMember = resultList.get(0);
//            System.out.println("findMember.getUsername() = " + findMember.getUsername());
//
//            // 어떤 팀이든 팀에 소속된 회원
//            String query2 = "SELECT m FROM Member m WHERE m.team = ANY(SELECT t FROM Team t)";
//            List<Member> resultList2 = em.createQuery(query2, Member.class).getResultList();
//            System.out.println("resultList2.size() = " + resultList2.size());
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }


//        /**
//         * 11. JPQL 기본 문법 - JPQL 타입 표현
//         * - 문자: '' 싱글 쿼터 사용
//         * - 숫자: 10L(Long), 10D(Double), 10F(Float)
//         * - Boolean: TRUE, FALSE
//         * - ENUM: 풀패키지명 또는 DTO 생성 및 파라미터 바인딩 방식
//         * - 엔티티 타입(상속 관계에서 사용): TYPE(m) = Member
//         */
//        try {
//            Member member = new Member();
//            member.setUsername("USER");
//            member.setAge(10);
//            member.setType(MemberType.ADMIN);
//
//            Team team = new Team();
//            team.setName("teamA");
//
//            member.changeTeam(team);
//
//            em.persist(member);
//            em.persist(team);
//
//            // JPQL 다양한 표현식
//            // ENUM: 아래와 같이 파라미터 바인딩 방식이(DTO 생성) 아닌 쿼리 안에 직접 작성할 때 풀패키지명 포함
//            String query = "SELECT m.username, 'HELLO', true FROM Member m WHERE m.type = :userType";
//            List<Object[]> result = em.createQuery(query)
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList();
//
//            for (Object[] objects : result) {
//                System.out.println("objects[0] = " + objects[0]); // USER
//                System.out.println("objects[1] = " + objects[1]); // HELLO
//                System.out.println("objects[2] = " + objects[2]); // TRUE
//            }
//
//            // 엔티티 타입 type(상속 관계에서 사용)
//            // DTYPE을 비교해서 필터링 한다. (ex: DTYPE = 'BOOK')
////            String query2 = "SELECT i FROM Item i WHERE type(i) = Book";
////            em.createQuery(query2, Item.class).getResultList();
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 11. JPQL 기본 문법 - 조건식
//         * - CASE
//         * - COALESCE: 하나씩 조회해서 null이 아니면 두번째 값 반환, 값이 있으면 첫 번째 값 반환
//         * - NULLIF: 두 값이 같으면 NULL 반환, 다르면 첫번째 값 반환
//         */
//        try {
//            Member member = new Member();
//            member.setUsername("관리자");
//            member.setAge(10);
//
//            em.persist(member);
//
//            // CASE
//            List<String> result = em.createQuery(
//                    "SELECT " +
//                            "case   when m.age <= 10 then '학생요금' " +
//                            "       when m.age <= 10 then '경로요금' " +
//                            "       else '일반요금' " +
//                            "end " +
//                            "FROM Member m ", String.class).getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }
//
//            // COALESCE - 값이 있으면 username 출력, 없으면 '이름 없는 회원' 출력
//            String query = "SELECT COALESCE(m.username, '이름 없는 회원') FROM Member m";
//            List<String> resultList = em.createQuery(query, String.class).getResultList();
//            for (String s : resultList) {
//                System.out.println("s = " + s);
//            }
//
//            // NULLIF: 사용자 이름이 '관리자'면 null 반환, 나머지는 본인의 이름을 반환
//            String query2 = "SELECT NULLIF(m.username, '관리자') FROM Member m";
//            Iterator<String> iterator = em.createQuery(query2, String.class).getResultList().iterator();
//
//            while (iterator.hasNext()) {
//                String value = iterator.next();
//                System.out.println("value = " + value);
//            }
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 11. JPQL 기본 문법 - JPQL 함수
//         * 1. concat, substring, trim,  lower, upper, length, locate, abs, sqrt, mod etc..
//         * 2. JPA에서 제공하는 특별한 함수: size, index
//         *   - size: 컬렉션의 사이즈를 알게 해준다.
//         * 3. JPA 입장에서 DB 함수를 알 방법이 없기 때문에 사용자정의 함수를 등록해야 한다.
//         *   @See: jpql.custom.CustomFunctionContributor
//         */
//        try {
//            Member member1 = new Member();
//            member1.setUsername("관리자1");
//            member1.setAge(10);
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setUsername("관리자2");
//            member2.setAge(10);
//            em.persist(member2);
//
//            // locate 예제: 문자의 위치를 반환
//            List<Integer> resultList = em.createQuery("select locate('de', 'abcdef') from Member m", Integer.class)
//                    .getResultList();
//            for (Integer s : resultList) {
//                System.out.println("s = " + s);
//            }
//
//            // size 예제
//            List<Integer> resultList1 = em.createQuery("select size(t.members) from Team t", Integer.class)
//                    .getResultList();
//            for (Integer i : resultList1) {
//                System.out.println("size = " + i);
//            }
//
//            // 사용자 정의 함수
//            String query = "select group_concat(m.username) from Member m";
//
//            List<String> resultList2 = em.createQuery(query, String.class).getResultList();
//            for (String s : resultList2) {
//                System.out.println("s = " + s);
//            }
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 12. JPQL 중급 문법 - 경로 표현식
//         * 1. 경로 표현식: .(점)을 찍어 객체 그래프를 탐색하는 것
//         *   - 상태 필드: 단순히 값을 저장하기 위한 필드(m.username)
//         *   - 연관 필드: 연관관계를 위한 필드
//         *     - 단일 값 연관 필드: target 대상이 하나인 엔티티(ex: @ManyToOne, @OneToOne, m.team)
//         *     - 컬렉션 값 연관 필드: target 대상이 컬렉션(ex: @OneToMany, @ManyToMany, m.orders)
//         * 2. 연관 필드는 기본적으로 '묵시적 내부 조인(inner join)'이 발생하게 되는데, 결론부터 말하면 '명시적 내부 조인'만 사용할 것.
//         *   - 묵시적 조인은 쿼리 분석 파악이 쉽지 않음
//         * 3. 연관 필드의 특징
//         *   - 단일 값 연관 경로: 탐색O
//         *   - 컬렉션 값 연관 경로: 컬렉션 탐색 X, 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능.
//         */
//        try {
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member1 = new Member();
//            member1.setUsername("관리자1");
//            member1.setAge(10);
//            member1.changeTeam(team);
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setUsername("관리자2");
//            member2.setAge(10);
//            member2.changeTeam(team);
//            em.persist(member2);
//
//            // 단일 값 연관 경로(team 객체에서 내부적으로 'name' 필드를 탐색 가능함)
//            // 대상이 엔티티일 경우 가능
//            String query = "select m.team.name from Member m";
//
//            // 컬렉션 값 연관 경로(members 컬렉션에서 내부적으로 탐색이 불가능)
//            String collectionsQuery1 = "select t.members from Team t";
//            // 대신 명시적 조인의 별칭을 통해 내부적으로 탐색 가능(m.username)
//            String collectionsQuery2 = "select m.username from Team t inner join Member m";
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 12. JPQL 중급 문법 - 페치 조인: 연관된 엔티티나 컬렉션을 SQL 한 번에 조회하는 기능(즉시 로딩, Eager와 비슷)
//         * 1. JPQL에서 성능 최적화를 위해 제공하는 기능(SQL의 조인과는 관련 X)
//         *   - [ LEFT [OUTER] | INNER ]JOIN FETCH 명령어 사용
//         *   - <b>패치 조인을 사용하지 않을 시 지연 로딩에 의해 필요한 데이터가 있을 때마다 질의함</b>
//         * 2. 다대일 조회와 일대다 조회 결과는 다름.
//         *   - 다대일 조회는 뻥튀기 되지 않는다. Member 테이블 여러명 - Team 테이블 하나 관계
//         *   - 일대다 조회는 뻥튀기 된다. Team 테이블 하나 - Member 테이블 여러명
//         *   - 일대다는 구조적으로 하나에서 여러 개를 조회하려고 조인하기 때문에 어쩔 수 없이 데이터가 뻥튀기 됨
//         * 3. 일반 조인 vs 패치 조인
//         *   - 일반 조인은 즉시 필요한 데이터만 영속성 컨텍스트에 올리는 것이고,(필요할 때마다 추가 지연 로딩)
//         *   - 패치 조인은 관련된 엔티티를 영속성 컨텍스트에 모두 올린다.
//         *   - <b>JPQL은 결과를 반환할 때 연관관계를 고려하지 않고, 단지 SELECT 절에 지정한 엔티티를 조회한다.
//         *     패치 조인을 사용할 때만 연관된 엔티티도 함께 즉시 조회된다.</b>
//         */
//        try {
//            Team teamA = new Team();
//            teamA.setName("teamA");
//            em.persist(teamA);
//
//            Team teamB = new Team();
//            teamB.setName("teamB");
//            em.persist(teamB);
//
//            Member member1 = new Member();
//            member1.setUsername("USER1");
//            member1.changeTeam(teamA);
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setUsername("USER2");
//            member2.changeTeam(teamA);
//            em.persist(member2);
//
//            Member member3 = new Member();
//            member3.setUsername("USER3");
//            member3.changeTeam(teamB);
//            em.persist(member3);
//
//            em.flush();
//            em.clear();
//
//            // 지연 로딩으로 새로운 Team을 조회할 때마다 쿼리 발생
//            // Member 1, Team 2(TeamA, TeamB) 질의 발생
//            List<Member> noFetchJoin = em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
//            for (Member member : noFetchJoin) {
//                System.out.println("member: " + member.getUsername() + " | Team: " + member.getTeam().getName());
//            }
//
//            // 다대일 관계 패치 조인
//            // SELECT M.*, T.* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID = T.ID 와 동일
//            String fetchJoinManyToOneQuery = "SELECT m FROM Member m join fetch m.team";
//            List<Member> resultList = em.createQuery(fetchJoinManyToOneQuery, Member.class).getResultList();
//            for (Member member : resultList) {
//                System.out.println("member = " + member.getUsername() + " | " + "Team: " + member.getTeam().getName());
//            }
//
//            // 일대다 관계 패치 조인
//            // 하이버네이트 6버전 이상부터 자동으로 distinct 적용으로 중복 제거됨
//            // SELECT T.*, M.* FROM TEAM T INNER JOIN MEMBER M ON T.ID = M.TEAM_ID 와 동일
//            String fetchJoinOneToManyQuery = "SELECT t FROM Team t join fetch t.members";
//            List<Team> resultList1 = em.createQuery(fetchJoinOneToManyQuery, Team.class).getResultList();
//            for (Team team : resultList1) {
//                System.out.println("teamName: " + team.getName() + ", team: " + team);
//                for (Member member : team.getMembers()) {
//                    System.out.println("-> username: " + member.getUsername() + ", member: " + member);
//                }
//            }
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 12. JPQL 중급 문법 - 엔티티 직접 사용
//         * 1. 기본 키 값
//         *   - 엔티티를 직접 사용하면 SQL에서 해당 엔티티의 기본 키 값 사용(식별자를 직접 전달하는 것과 같은 효과)
//         * 2. 외래 키 값
//         *   - 외래 키 값도 동일하게 엔티티를 넘겨 사용 가능하다.
//         *   - @JoinColumn에 적용되어 있는 FK 값을 이용한다.
//         */
//        try {
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("USER");
//            member.changeTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            // 엔티티로 조회 가능
//            String jpql = "SELECT m FROM Member m WHERE m = :member";
//            Member findMember = em.createQuery(jpql, Member.class)
//                    .setParameter("member", member)
//                    .getSingleResult();
//
//            System.out.println("findMember = " + findMember.getUsername());
//
//            // 식별자를 직접 전달
//            String jpql2 = "SELECT m FROM Member m WHERE m.id = :member_id";
//            Member findMember2 = em.createQuery(jpql2, Member.class)
//                    .setParameter("member_id", member.getId())
//                    .getSingleResult();
//
//            System.out.println("findMember2 = " + findMember2.getUsername());
//
//            // 외래키 값
//            String foreignKeyQuery = "SELECT m FROM Member m WHERE m.team = :team";
//            Member findMember3 = em.createQuery(foreignKeyQuery, Member.class)
//                    .setParameter("team", team)
//                    .getSingleResult();
//
//            System.out.println("findMember3 = " + findMember3.getUsername());
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 12. JPQL 중급 문법 - Named 쿼리(정적 쿼리)
//         * 1. Named 쿼리
//         *   - 미리 정의해서 이름을 부여해두고 사용하는 JPQL
//         *   - 어노테이션, XML에 정의
//         *   - 애플리케이션 로딩 시점에 초기화 후 재사용
//         *   - <b>애플리케이션 로딩 시점에 쿼리를 검증</b>
//         */
//        try {
//            Member member = new Member();
//            member.setAge(10);
//            member.setUsername("USER");
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            // createNamedQuery 메서드명 주의
//            Member singleResult = em.createNamedQuery("Member.findByUsername", Member.class)
//                    .setParameter("username", "USER")
//                    .getSingleResult();
//
//            System.out.println("singleResult = " + singleResult);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//            emf.close();
//        }

        /**
         * 12. JPQL 중급 문법 - 벌크연산
         * 1. 벌크 연산: 쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
         *   - JPA 변경 감지(dirty check) 기능으로 많은 데이터를 변경하려면 건당 쿼리가 발생해 너무 많은 쿼리 발생
         *   - `executeUpdate()` 메서드를 사용하면 벌크연산 실행 가능
         *   - 참고로 JPA 표준은 아니지만 하이버네이트를 사용하면 INSERT - SELECT 쿼리도 가능
         * 2. 벌크 연산 주의
         *   - 벌크 연산은 영속성 컨텍스트를 무시하고 DB에 직접 쿼리하므로 데이터 정합성이 깨질 수 있음
         *   - 해결법 2가지
         *     - 벌크 연산을 먼저 실행
         *     - 벌크 연산 수행 후 영속성 컨텍스트 초기화
         */
        try {
            Member member1 = new Member();
            member1.setAge(10);
            member1.setUsername("USER1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setAge(10);
            member2.setUsername("USER2");
            em.persist(member2);

            Member member3 = new Member();
            member3.setAge(10);
            member3.setUsername("USER3");
            em.persist(member3);

            int resultCount = em.createQuery("UPDATE Member m SET m.age = 30")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount); // 3

            em.clear(); // 영속성 컨텍스트를 초기화하지 않고 조회할 시 위에 세팅한 age = 10으로 나옴
            // 즉, 벌크 연산을 먼저 수행하는 것을 추천

            Member findMember1 = em.find(Member.class, member1.getId());
            Member findMember2 = em.find(Member.class, member2.getId());
            Member findMember3 = em.find(Member.class, member3.getId());

            System.out.println("findMember1 = " + findMember1.getAge()); // 10
            System.out.println("findMember2 = " + findMember2.getAge()); // 10
            System.out.println("findMember3 = " + findMember3.getAge()); // 10

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }
}
