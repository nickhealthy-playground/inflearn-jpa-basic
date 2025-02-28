package hellojpa.mapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * JPA 양방향 연관관계와 연관관계의 주인 2 - 주의점, 정리
 */
public class JpaMain2 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("MEMBER1");
//            member.changeTeam(team); // 연관관계의 주인에 값을 세팅하는 경우
//            em.persist(member);
//
//            Team findTeam = em.find(Team.class, team.getId());
//            List<Member> members = findTeam.getMembers();
//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            team.getMembers().add(member); // team 엔티티를 건드리지만, MEMBER 테이블의 외래키가 변경되게 된다.
            em.persist(team);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
