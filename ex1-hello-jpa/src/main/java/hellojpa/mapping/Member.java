package hellojpa.mapping;

import jakarta.persistence.*;

// JPA가 관리할 객체
@Entity(name = "MEMBER2")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    /**
//     * 객체를 테이블에 맞춰 모델링
//     * - 참조 대신에 외래 키를 그대로 사용 - 객체지향적이지 못함
//     */
//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne // Member와 Team은 N:1 관계
    @JoinColumn(name = "TEAM_ID") // Team 엔티티의 TEAM_ID 컬럼과 외래키 관계
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public Long getTeamId() {
//        return teamId;
//    }
//
//    public void setTeamId(Long teamId) {
//        this.teamId = teamId;
//    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
