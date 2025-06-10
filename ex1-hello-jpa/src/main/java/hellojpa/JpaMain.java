package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
//        try {
//            Member member = new Member();
//            member.setId(2L);
//            member.setUserName("helloB");
//
//            em.persist(member);
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

        /**
         * JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리를 제공
         * - SQL과 문법 유사, SELECT, FROM, WHERE GROUP BY, HAVING, JOIN 등 지원
         * - <b>JPQL은 엔티티 객체</b>를 대상으로 쿼리
         * - <b>SQL은 데이터베이스 테이블</b>을 대상으로 쿼리
         */
//        try {
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .setFirstResult(5)
//                    .setMaxResults(8)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getUserName() = " + member.getUserName());
//            }
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

        /**
         * 영속성 컨텍스트1 - 1차 캐시
         */
//        try {
//            // 비영속
//            Member member = new Member();
//            member.setId(100L);
//            member.setUserName("HelloJPA");
//
//            System.out.println("=== BEFORE ===");
//            em.persist(member); // 영속
//            System.out.println("=== AFTER ===");
//
//            // find 메서드 두 번 호출했지만 한 번만 SELECT 쿼리 발생(영속성 컨텍스트에 저장된 캐시에서 객체를 찾음)
//            Member member1 = em.find(Member.class, 100L);
//            Member member2 = em.find(Member.class, 100L);
//
//            System.out.println("result = " + (member1 == member2));
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }


//        /**
//         * 영속성 컨텍스트2 - 쓰기 지연
//         */
//        try {
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//            em.persist(member1);
//            em.persist(member2);
//
//            System.out.println("======================"); // 해당 라인 이후에 insert 쿼리를 모아서 commit
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }
//
//        /**
//         * 영속성 컨텍스트3 - 변경 감지
//         */
//        try {
//            Member member = em.find(Member.class, 150L);
//            System.out.println("member.getId() = " + member.getId());
//            System.out.println("member.getUserName() = " + member.getUserName()); // B
//            // em.persist() 메서드를 호출하지 않아도 commit 시점에 값이 DB에 반영
//            // JPA는 컬렉션을 다루듯이(em.find) 매커니즘이 설계 되었기 때문에, 따로 반영하는 코드를 작성하지 않아도 변경사항이 있으면 이전 스냅샷과 비교해 변경분을 곧바로 반영함
//            member.setUserName("ZZZZ");
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//        * 영속성 컨텍스트4 - em.flush()
//         * - 해당 메서드를 호출해도 영속성 컨텍스트를 비우지 않음
//         * - 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화
//        */
//        try {
//            Member member1 = new Member(200L, "A");
//            Member member2 = new Member(300L, "B");
//            Member member3 = new Member(400L, "C");
//
//            em.persist(member1);
//            em.persist(member2);
//            em.persist(member3);
//
//            TypedQuery<Member> query = em.createQuery("select m from Member as m", Member.class);
//            List<Member> resultList = query.getResultList(); // JPQL 쿼리 실행 시 자동으로 flush 실행
//            System.out.println("========="); // 따라서 커밋 이전이지만 flush를 통해 데이터베이스에 저장된 값이 있으므로 쿼리 조회 시 조회가 가능함
//            System.out.println("resultList = " + resultList);
//
//            tx.commit(); // commit 실행 시 자동으로 flush 실행
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 영속성 컨텍스트5 - 준영속
//         * - 영속 -> 준영속: 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
//         * - 영속성 컨텍스트가 제공하는 기능을 사용 못함, 관리 대상 X
//         */
//        try {
//            // 100L ID를 가진 멤버를 저장
////            Member member = new Member(100L, "memberA");
////            em.persist(member);
//
//            Member member = em.find(Member.class, 100L);
//            member.setUserName("memberB"); // 엔티티 변경 시 영속성 컨텍스트의 쓰기 지연에 의해 커밋 시점에 변경분이 반영되어야 함
//
//            em.detach(member); // 영속성 컨텍스트에서 분리(해당 엔티티 관리 대상에서 제외됨)
//
//            // 위에서 해당 엔티티를(100L) 준영속으로 처리했기 때문에 영속성 컨텍스트에서 캐시 처리가 되지 않고, DB에서 새로 조회
//            // 즉, SELECT 쿼리 두 번 조회됨
//            Member member2 = em.find(Member.class, 100L);
//
//            tx.commit(); // commit 실행 시 자동으로 flush 실행
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 기본 키 매핑 주의점 - @GeneratedValue - IDENTITY 설정
//         * - em.persist 시점 데이터베이스 커밋
//         * - 영속성 컨텍스트 특성에 따른 JPA의 예외상황
//         *   1. @GeneratedValue - IDENTITY 설정은 PK 저장을 데이터베이스에 위임하는 형태.
//         *   2. 따라서 PK 값을 애플리케이션 단에서 설정하지도 않고, 확인할 수도 없다.
//         *   3. 하지만 그렇게 되면 영속성 컨텍스트에서 해당 엔티티 조회가 불가능하기 때문에
//         *   4. 이 경우에만 예외로 JPA에서 em.persist 메서드 호출 시점에 데이터베이스에 커밋한다.
//         */
//        try {
//            Member member = new Member();
//            member.setUserName("C");
//
//            System.out.println("==================");
//            em.persist(member); // 해당 라인에서 데이터베이스에 실제로 반영
//            System.out.println("member.getId() = " + member.getId());
//            System.out.println("==================");
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 기본 키 매핑 - @GeneratedValue - SEQUENCE 설정
//         * - allocationSize 옵션을 설정해두면(기본값 50) 성능 최적화를 할 수 있다.
//         * - 시퀀스 값을 데이터베이스에서 먼저 불러와야 PK 값을 설정할 수 있기 때문에 SELECT 쿼리가 N번 발생하게 된다.
//         *   - N번 발생으로 성능 이슈를 해결하기 위해선 allocationSize 옵션을 사용하면 된다.
//         */
//        try {
//            Member member1 = new Member();
//            member1.setUserName("A");
//
//            Member member2 = new Member();
//            member2.setUserName("A");
//
//            Member member3 = new Member();
//            member3.setUserName("A");
//
//            System.out.println("==================");
//            em.persist(member1);
//            em.persist(member2);
//            em.persist(member3);
//
//            System.out.println("member1.id = " + member1.getId());
//            System.out.println("member2.id = " + member2.getId());
//            System.out.println("member3.id = " + member3.getId());
//            System.out.println("==================");
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 6. 연관관계 매핑 기초 - 단방향 연관관계
//         * - 데이터베이스와 객체의 연관관계는 외래키/참조라는 매커니즘 자체가 다르기 때문에
//         * - 데이터베이스의 데이터 중심으로(외래키 방식) 엔티티를 설계하면 객체지향적으로 설계할 수 없다.
//         * - 대신 객체지향적으로 설계하기 위해 연관관계에 있는 엔티티를 참조(Member 안에 Team 필드 설정)하는 방식으로 설계한다.
//         */
//        try {
//            // 팀 저장
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            // 멤버 저장
//            Member member = new Member();
//            member.setUserName("member");
//            member.setTeam(team);
//            em.persist(member);
//
//            Member findMember = em.find(Member.class, member.getId());
//            Team findTeam = findMember.getTeam();
//            System.out.println("findTeam.getName() = " + findTeam.getName());
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 6. 연관관계 매핑 기초 - 연관관계의 주인1
//         * - 연관관계의 주인은 데이터베이스 테이블에서 외래키를 가지는 쪽의 엔티티에서 설정한다.
//         * - 연관관계의 주인의 반대편은 mappedby 키워드를 사용하여 '연관관계가 어떤 엔티티에 의해 매핑되었다는 것(Team 엔티티의 members 필드)'을 명시해준다.
//         * - 연관관계의 주인만 삽입/수정이 가능하다.
//         * - 연관관계의 주인의 반대편은 '읽기'만 가능하다.
//         */
//        try {
//            // 팀 저장
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            // 멤버 저장
//            Member member = new Member();
//            member.setUserName("member");
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            Member findMember = em.find(Member.class, member.getId());
//            Team findTeam = findMember.getTeam();
//
//            List<Member> members = findTeam.getMembers();
//            for (Member m : members) {
//                System.out.println("member.username = " + m.getUserName());
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
//         * 6. 연관관계 매핑 기초 - 연관관계의 주인2 주의점
//         *  6.1. 연관관계 매핑에서 가장 많이 하는 실수는 연관관계의 주인쪽에 값을 세팅하지 않는 것이다. 연관관계의 주인에 값을 세팅하지 않으면 값이 반영되지 않음.
//         *  6.2. 연관관계 매핑에서 값을 세팅해줄 땐 객체지향적인 관점에서 양쪽 모두 세팅해주자.
//         *     순수 자바코드로 테스트 코드를 작성할 때도 있는데 연관관계 주인 쪽에만 값을 세팅하면 장애로 이어질 수 있다.
//         *     값을 양쪽 모두 세팅해줄 때, 편의 메서드를 따로 만들어서 한 메서드 안에서 값을 세팅하도록 처리하자.
//         *  6.3. 엔티티를 설계할 땐 단방향만 우선 설정하고 양방향은 나중에 추가해도 늦지 않다. 잘 생각해보면 어차피 데이터베이스 테이블에는 영향이 가지 않는다.
//         */
//        try {
//            // 팀 저장
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            // 멤버 저장
//            Member member = new Member();
//            member.setUserName("member");
//            member.changeTeam(team); // 편의 메서드로 변경
//            em.persist(member);
//
//            // team.getMembers().add(this);
//            Member findMember = em.find(Member.class, member.getId());
//            Team findTeam = findMember.getTeam();
//
//            System.out.println("========================");
//            List<Member> members = findTeam.getMembers();
//            for (Member m : members) {
//                System.out.println("member.username = " + m.getUserName());
//            }
//            System.out.println("========================");
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }
//

//        /**
//         * 8. 고급매핑 - 상속관계 조인 전략
//         * - @Inheritance(strategy = InheritanceType.JOINED) 해당 어노테이션을 통해 상속 관계를 구축(데이터베이스의 슈퍼 - 서브 모델)
//         */
//        try {
//            Movie movie = new Movie();
//            movie.setDirector("aaaa");
//            movie.setActor("bbb");
//            movie.setName("바람과 함께 사라지다.");
//            movie.setPrice(10000);
//
//            em.persist(movie);
//
//            em.flush();
//            em.clear();
//
//            Movie findMovie = em.find(Movie.class, movie.getId());
//            System.out.println("findMovie = " + findMovie);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }


//        /**
//         * 8. 고급매핑 - @MappedSuperclass(매핑 정보 상속)
//         * - 상속 관계와 달리 공통으로 사용하는 속성을 한 곳에 모아둔다.
//         * - JPA에서는 @Entity, @MappedSuperclass 어노테이션을 붙이지 않으면 다른 클래스에 상속할 수 없다.
//         */
//        try {
//            Member member = new Member();
//            member.setUserName("USER1");
//            member.setCreateBy("JOO");
//            member.setCreateDate(LocalDateTime.now());
//
//            em.flush();
//            em.clear();
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 9. 프록시와 연관관계 관리 - 프록시
//         * - em.find: 데이터베이스를 통해서 실제 엔티티 조회
//         * - em.reference: 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회
//         */
//        try {
//            Member member = new Member();
//            member.setUserName("USER1");
//
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            /* 프록시의 인스턴스 초기화 및 동작 - 지연로딩 */
//            Member reference = em.getReference(Member.class, member.getId());
//            System.out.println("reference.getId() = " + reference.getId());
//            // 프록시 객체가 엔티티를 실제 사용할 때 영속성 컨텍스트에서 SELECT 쿼리를 날려 엔티티를 가져온다.
//            System.out.println("reference.getUserName() = " + reference.getUserName()); // 해당 라인에서 프록시 target 인스턴스 초기화 및 SELECT 쿼리 발생
//
//
//            /* 조회 순서에 따른 조회 대상 변경(엔티티 vs 프록시)
//            * - 어떤 순서로 조회하는냐에 따라 엔티티나 프록시가 반환된다.
//            * - JPA에서는 한 트랜잭션 안에서 객체의 동일성을('==') 보장하기 위해 em.find, em.reference로 조회된 것 중 먼저 조회된 객체로 반환한다.
//            *  */
//            // CASE1. 엔티티가 조회되는 경우
////            Member findMember = em.find(Member.class, member.getId());
////            System.out.println("findMember.getClass() = " + findMember.getClass());
////            System.out.println("findMember.getId() = " + findMember.getId());
////            System.out.println("findMember.getUserName() = " + findMember.getUserName());
////
////            Member reference = em.getReference(Member.class, findMember.getId());
////            System.out.println("reference = " + reference.getClass());
//
//            // CASE2. 프록시가 조회되는 경우
////            Member reference = em.getReference(Member.class, member.getId());
////            System.out.println("reference = " + reference.getClass());
////
////            Member findMember = em.find(Member.class, member.getId());
////            System.out.println("findMember.getClass() = " + findMember.getClass());
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
//         * 9. 프록시와 연관관계 관리 - 즉시 로딩과 지연 로딩
//         * - 지연 로딩 LAZY을 사용해서 프록시로 조회(필요한 시점에 로딩)
//         * - 즉시 로딩 EAGER을 사용해서 엔티티 바로 조회(MEMBER, TEAM 같이 조회하는 경우가 많을 경우 즉시 로딩이 유리)
//         */
//        try {
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUserName("USER1");
//            member.changeTeam(team); // 편의 메서드(연관관계 주인/가짜 주인 양쪽 값 세팅)
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            Member findMember = em.find(member.getClass(), member.getId());
//            System.out.println("findMember.getClass() = " + findMember.getClass()); // LAZY 설정: 프록시 객체, EAGER 설정: 엔티티 객체
//
//            System.out.println("=====================");
//            // 지연 로딩 LAZY을 사용해서 프록시로 조회(필요한 시점에 로딩)
//            // 실제 team을 사용하는 시점에 초기화(DB 조회)
//            findMember.getTeam().getName();
//            System.out.println("=====================");
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 9. 프록시와 연관관계 관리 - 영속성 전이(CASCADE)
//         * - Cascade는 단순히 persist 호출을 전파하는 것: 이 엔티티를 persist할 때 연관된 엔티티들도 같이 persist해라
//         * - 아래 예제에서는 em.persist(parent)만 해도 이와 관련된 em.persist(child) 호출을 자동화하는 것
//         * - 주의사항:
//         *   1. 영속 상태 엔티티가 비영속 상태인 엔티티를 참조하려고 할 때 '참조 무결성을 보장할 수 없음'으로 persist 되지 않음
//         *     - 즉, 영속 엔티티는 영속 엔티티만 참조할 수 있음
//         *   2. 외래 키 관리 주체와 엔티티 persist 순서에 따른 쿼리 동작 방식 주의(update 문 추가 발생 가능)
//         *     - 참조하는 엔티티보다 참조되는 엔티티(parent)를 먼저 persist 해야함
//         *     - 참조하는 엔티티(child)는 parent_id를 모르고 있기 때문에 먼저 persist 수행 시 child 엔티티가 저장되고,
//         *       참조되는 엔티티(parent)가 persist 될 때 parent_id를 얻고, 변경 감지(dirty check)에 의해 다시 update 문을 수행하게 됨
//         */
//        try {
//            Child child1 = new Child();
//            Child child2 = new Child();
//            child1.setName("child1");
//            child2.setName("child2");
//
//            Parent parent = new Parent();
//            parent.addChild(child1);
//            parent.addChild(child2);
//
//            em.persist(parent);
////            em.persist(child1);
////            em.persist(child2);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }


//        /**
//         * 10. 값 타입 - 임베디드 타입
//         * - C언어의 구조체처럼 값들을(대부분 기본 타입) 모아놓은 사용자 정의 타입(클래스)
//         * - 장점: 여러 곳에서 재사용 가능하다.
//         */
//        try {
//            Member member = new Member();
//            member.setUserName("USER1");
//            member.setHomeAddress(new Address("nowon", "street", "10000"));
//            member.setWorkPeriod(new Period());
//
//            em.persist(member);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

//        /**
//         * 10. 값 타입 - 값 타입과 불변 객체
//         * - 값 타입을 불변으로 만들어서 사전에 값이 동시에 변경되는 것을 방지해야 한다.
//         * - 기본 타입은 call by value 이므로 기본적으로 불변 타입이고,
//         * - 인스턴스는 call by address 이므로 객체에 대해서 불변 객체로 설정해야한다.(setter 제약, 생성자에서만 최초 값 설정)
//         */
//        try {
//            Address address = new Address("city", "street", "10000");
//
//            Member member1 = new Member();
//            member1.setUserName("USER1");
//            member1.setHomeAddress(address);
//
//            // 이와 같이 값을 새로 만들어서 엔티티에 값을 세팅해야함
//            Address copyAddress = new Address("city", "street", "10000");
//
//            Member member2 = new Member();
//            member2.setUserName("USER2");
//            member2.setHomeAddress(copyAddress);
//
//            member1.getHomeAddress().setCity("newCity");
//
//            em.persist(member1);
//            em.persist(member2);
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//            emf.close();
//        }

        /**
         * 10. 값 타입 - 값 타입 컬렉션
         * - 값 타입 컬렉션으로 별도의 테이블을 만들어도, Member 엔티티의 생명주기를 따라간다.
         *   - 즉, Member 엔티티의 값을 persist 하면 cascade, orphanRemoval(고아객체 자동 삭제) 옵션이 자동으로 붙은 것으로 생각할 수 있다.
         * - 값 타입 컬렉션은 기본 전략으로 LAZY 로딩을 사용한다. 즉, 실제로 사용할 때 쿼리를 호출한다.
         */
        try {

            // 값 타입 저장 예제
            Member member = new Member();
            member.setUserName("USER1");
            member.setAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFood().add("치킨");
            member.getFavoriteFood().add("족발");
            member.getFavoriteFood().add("피자");

            // 값 타입 컬렉션을 엔티티로 승격해서 사용
            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            // 값 타입 조회 예제
            System.out.println("==============START===============");
            Member findMember = em.find(Member.class, member.getId()); // 여기까지 Member 객체만 조회

            // LAZY 로딩에 의해 이때 쿼리 조회
            List<AddressEntity> addressHistory = findMember.getAddressHistory();
            for (AddressEntity address : addressHistory) {
                System.out.println("address.getCity() = " + address.getAddress().getCity());
            }

            // LAZY 로딩에 의해 이때 쿼리 조회
            Set<String> favoriteFood = findMember.getFavoriteFood();
            for (String s : favoriteFood) {
                System.out.println("s = " + s);
            }

            // 값 타입 컬렉션 수정 예제
            Address address = findMember.getAddress();
            // 임베디드 타입(값 들을 모아놓은 객체)은 앞서 학습했던 것처럼 기존 인스턴스를 수정하는 것이 아니라, 새롭게 생성해서 수정해야 한다.(불변 객체 취급)
            findMember.setAddress(new Address("newCity", address.getStreet(), address.getZipcode()));

            // 값 타임 컬렉션 수정
            // 치킨 -> 한식(delete -> insert 쿼리 발생)
            findMember.getFavoriteFood().remove("치킨");
            findMember.getFavoriteFood().add("한식");


            /**
             * 값 타입 컬렉션 수정 주의점(결론: 사용하지 말자)
             * - 값 타입 컬렉션은 엔티티와 다르게 '식별자'가 따로 존재하지 않기 때문에 레코드 대상을 특정할 수 없음.
             * - 따라서 엔티티와 연관된 값 타입 컬렉션의 레코드를 모두 delete 한 이후, insert 를 통해 수정할 레코드를 새로운 레코드로 갈아 끼우는 방식
             * - 대안으로 '일대다 관계'를 활용하자
             */
//            // eqauls 기본적으로 사용해서 대상을 찾음, 따라서 equals, hashcode 오버라이딩 필요
//            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "10000"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }
}
