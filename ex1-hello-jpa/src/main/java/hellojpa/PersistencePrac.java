package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class PersistencePrac {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션 시작

        try {
            /*
            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HELLO");

            // 영속
            System.out.println("=== BEFORE ===");
            em.persist(member);
            System.out.println("=== AFTER ===");

            // SELECT 쿼리가 날라가지 않음
            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);

            System.out.println("findMember.getId() = " + findMember1.getId());
            System.out.println("findMember.getName() = " + findMember1.getName());
            */

            /**
             * 영속성 컨텍스트 2 - 쓰기 지연
             */
            /*
            // 영속
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "A");

            em.persist(member1);
            em.persist(member2);

            System.out.println("=======================");  // INSERT 쿼리가 실행되지 않음

            tx.commit();    // 커밋 이후 실제 데이터베이스에 저장
             */

            /**
             * 영속성 컨텍스트 2 - 변경 감지
             */

            // 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("JPA");

            // 영속
//            em.persist(member);

            /**
             * 준영속 상태(Detached State)
             */
            Member findMember1 = em.find(Member.class, member.getId());
            findMember1.setName("JPA1");

            System.out.println("===============");

            // 주의!! 엔티티는 member가 아니라 findMember 임
//            em.detach(findMember);

            // 영속성 컨텍스트에서 관리하는 모든 엔티티를 초기화
            em.clear();

            // 초기화 작업으로 인해 다시 동일한 엔티티를 조회했을 때, 1차 캐시 조회가 아닌 SELECT 쿼리로 조회하는 것을 확인할 수 있다.
            Member findMember2 = em.find(Member.class, member.getId());


            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }
}
