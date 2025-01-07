package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        /**
         * 엔티티 매니저 팩토리
         * - 하나만 생성해서 애플리케이션 전체에서 공유한다.
         * - Persistence.createEntityManagerFactory("hello")
         *   - 파라미티인 `hello`는 persistence.xml 설정정보 내용 중 하나
         */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /**
         * 엔티티 매니저
         * - 쓰레드 간에 공유하면 안된다.(사용하고 버려야 한다.)
         */
        EntityManager em = emf.createEntityManager();
        /**
         * JPA의 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.
         */

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 데이터 등록
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");

            em.persist(member);

            // 데이터 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

            // JPQL
            List<Member> result = em.createQuery("SELECT m FROM Member m", Member.class)
                    .getResultList();

            for (Member member2 : result) {
                System.out.println("member2.getName() = " + member2.getName());
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
