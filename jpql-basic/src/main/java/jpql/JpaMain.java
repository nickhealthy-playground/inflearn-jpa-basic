package jpql;

import jakarta.persistence.*;
import jpql.entity.Member;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /**
         * JPQL - 기본 문법
         * - TypedQuery vs Query: 타입을 명확히 지정할 수 있을 때와 없을 때 구분해서 사용
         * - getSingleResult() 주의사항 - 결과가 단 하나여야 에러가 발생하지 않음
         * - 파라미터 바인딩 종류: 이름 기반(':이름' 형식으로 사용), 위치 기반(?1(위치번호))
         */
        try {
            Member member = new Member();
            member.setUsername("USER");
            member.setAge(10);
            em.persist(member);

            // 타입을 정확히 알 수 있을 때 TypedQuery 사용
            TypedQuery<Member> typedQuery = em.createQuery("select m from Member as m", Member.class);

            // 타입을 정확히 지정할 수 없을 때(ex: String - username, int - age) Query 사용
            Query query = em.createQuery("select m.username, m.age from Member as m");

            // getSingleResult 사용 시 주의사항
            // 1. 결과가 단 하나여야 한다. 아니면 NonUniqueResultException 에러 발생
            // 2. 결과가 없으면 NoResultException 발생
            Member singleResult = typedQuery.getSingleResult();
            System.out.println("singleResult = " + singleResult.getUsername()); // USER

            // 파라미터 바인딩 - 이름 기반(:이름)
            // 메서드 체이닝도 가능
            Member result = em.createQuery("select m from Member as m where username = :username", Member.class)
                    .setParameter("username", "USER")
                    .getSingleResult();

            System.out.println("result.getUsername() = " + result.getUsername()); // USER

            // 파라미터 바인딩 - 위치 기반(?) 사용
            Member result2 = em.createQuery("select m from Member as m where username = ?1", Member.class)
                    .setParameter(1, "USER")
                    .getSingleResult();

            System.out.println("result2.getUsername() = " + result2.getUsername()); // USER

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
