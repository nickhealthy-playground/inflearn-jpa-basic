package hellojpa.mapping;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    /**
     * 양방향 매핑 - 추천
     * - 사실 양방향이라는 개념보단 단방향 매핑을 양쪽으로 설정하는 개념이다.
     * - 또한 양방향 매핑보다도 JPA를 고려한 시스템 설계 시 '단방향 매핑'이 제일 좋은 설계이며, 단방향 매핑으로도 프로그램 로직을 모두 구현할 수 있다.
     * - 다만 프로그램 로직을 짤 때 반대로 조회해야 하는 경우가 있어 '편의성'을 위해 양방향 매핑을 하는 경우가 종종 있다.
     */
//    @OneToMany(mappedBy = "team") // 양방향 연관관계 설정, mappedBy 속성으로 주인을 지정
//    private List<Member> members = new ArrayList<>();

    /**
     * 일대다 단방향 매핑 - 비추천
     * - TEAM 엔티티에서 수정이 이루어지지만 MEMBER 테이블의 FK가 없데이트가 되는 현상이 발생
     * - 논리적으로 코드를 파악하기가 힘들고, 유지보수가 어렵다.
     */
    @OneToMany
    @JoinColumn(name = "TEAM_ID") // MEMBER 테이블의 FK
    private List<Member> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
