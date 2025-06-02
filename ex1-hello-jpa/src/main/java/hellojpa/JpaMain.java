package hellojpa;

import jakarta.persistence.*;

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

        /**
         * 영속성 컨텍스트5 - 준영속
         * - 영속 -> 준영속: 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
         * - 영속성 컨텍스트가 제공하는 기능을 사용 못함, 관리 대상 X
         */
        try {
            // 100L ID를 가진 멤버를 저장
//            Member member = new Member(100L, "memberA");
//            em.persist(member);

            Member member = em.find(Member.class, 100L);
            member.setUserName("memberB"); // 엔티티 변경 시 영속성 컨텍스트의 쓰기 지연에 의해 커밋 시점에 변경분이 반영되어야 함

            em.detach(member); // 영속성 컨텍스트에서 분리(해당 엔티티 관리 대상에서 제외됨)

            // 위에서 해당 엔티티를(100L) 준영속으로 처리했기 때문에 영속성 컨텍스트에서 캐시 처리가 되지 않고, DB에서 새로 조회
            // 즉, SELECT 쿼리 두 번 조회됨
            Member member2 = em.find(Member.class, 100L);

            tx.commit(); // commit 실행 시 자동으로 flush 실행
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }
}
