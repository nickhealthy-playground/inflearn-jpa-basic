package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
//        try {
//            tx.begin();
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("helloB");
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
        try {
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.getName() = " + member.getName());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
