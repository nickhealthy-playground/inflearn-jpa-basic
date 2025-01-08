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
            // JPA와 아무 관계 없는 비영속 상태
            Member member = new Member();
            member.setId(100L);
            member.setName("HELLO");

            // 영속 상태
            System.out.println("=== BEFORE ===");
            em.persist(member);
//            em.detach(member);      // 엔티티를 영속성 컨트스트에서 분리, 준영속 상태
            System.out.println("=== AFTER ===");

//            em.remove(member);    // 삭제
            tx.commit();    // 커밋 이후 실제 데이터베이스에 저장

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }
}
