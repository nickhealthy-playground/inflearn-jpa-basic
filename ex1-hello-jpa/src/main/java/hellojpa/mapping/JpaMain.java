package hellojpa.mapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");

            /**
             * 모델링을 테이블 기준으로 맞출 때 문제점 1 - team.getId()
             * - 모델링을 테이블 기준으로 맞춰 외래 키를 사용 -> Team 객체에서 get을 한번 더 불러오는 코드를 사용할 수 밖에 없음
             * - 객체를 참조하는 방식으로 모델링 필요(객체 지향적인 모델링)
             */
//            member.setTeamId(team.getId());
            member.setTeam(team); // 객체 지향적으로 모델링 수정 - 영속성 컨텍스트에 의해 JPA가 자동으로 Team 객체에서 PK 값을 꺼내 세팅한다.
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
//            Long findTeamId = findMember.getTeamId(); // 모델링을 테이블 기준으로 맞출 때 문제점 2 - `member`를 조회 후 `Team`을 조회 가능
//            Team findTeam = em.find(Team.class, findTeamId);
            Team findTeam = findMember.getTeam(); // 객체 지향적으로 모델링 수정
            System.out.println("findTeam = " + findTeam.getName()); // findTeam = TeamA

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
