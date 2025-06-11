package jpql;

import jakarta.persistence.*;
import jpql.dto.MemberDto;
import jpql.entity.Member;
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

        /**
         * 11. JPQL 기본 문법 - 프로젝션
         * - 프로젝션: SELECT 절에 조회할 대상을 지정하는 것
         *   - SELECT m FROM Member m: 엔티티 프로젝션
         *   - SELECT m.team FROM Member m: 엔티티 프로제션
         *   - SELECT m.address FROM member m: 임베디드 타입 프로젝션
         *   - SLEECT m.username, m.age FROM Member m: 스칼라 타입 프로젝션
         *
         * - 여러 값 조회 방법
         *   - query 타입 조회
         *   - Object[] 타입으로 조회
         *   - new 타입으로 조회(Dto 생성 및 전체 패키지 작성 필요)
         */
        try {
            Member member = new Member();
            member.setUsername("USER");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // JPQL도 영속성 관리 대상임 -> update 쿼리 발생
            List<Member> resultList = em.createQuery("select m from Member m", Member.class).getResultList();
            Member findMember = resultList.get(0);
            findMember.setAge(20);

            // 아래와 같이 작성 가능하지만, 가시성을 위해 특정 엔티티에 다른 엔티티는 다음과 같이 JOIN을 명시적으로 작성해야 한다.
            // em.createQuery("SELECT m.team FROM Member m");
            List<Team> result = em.createQuery("SELECT t FROM Member m JOIN m.team t", Team.class).getResultList();

            // 여러 값 조회 방법
            // 1. Query 타입으로 조회
            List memberList1 = em.createQuery("SELECT m.username, m.age FROM Member m").getResultList();
            Object o = memberList1.get(0);
            Object[] objects1 = (Object[]) o;
            System.out.println("username = " + objects1[0]);
            System.out.println("age = " + objects1[1]);

            // 2. Object[] 타입으로 조회
            List<Object[]> memberList2 = em.createQuery("SELECT m.username, m.age FROM Member m").getResultList();
            Object[] objects2 = memberList2.get(0);
            System.out.println("username = " + objects2[0]);
            System.out.println("age = " + objects2[1]);

            // 3. new 명령어로 조회
            List<MemberDto> memberList3 = em.createQuery("SELECT new jpql.dto.MemberDto(m.username, m.age) FROM Member m", MemberDto.class).getResultList();
            MemberDto memberDto = memberList3.get(0);
            System.out.println("memberDto.getUsername = " + memberDto.getUsername());
            System.out.println("memberDto.getAge() = " + memberDto.getAge());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }
}
