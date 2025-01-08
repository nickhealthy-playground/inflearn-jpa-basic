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
            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HELLO");

            // 영속
            System.out.println("=== BEFORE ===");
//            em.persist(member);
            System.out.println("=== AFTER ===");

            // SELECT 쿼리가 날라가지 않음
            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);

            System.out.println("findMember.getId() = " + findMember1.getId());
            System.out.println("findMember.getName() = " + findMember1.getName());

            tx.commit();    // 커밋 이후 실제 데이터베이스에 저장

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }
}
