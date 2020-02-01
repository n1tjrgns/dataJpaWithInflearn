package com.example.datajpa;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    //모든 멤버변수에는 @Column이 생략되어있음
    private String username;

    private String password;

    //한 사람이 여러 스터디를 가지고있을 수 있다.
    //Many로 끝나면 컬렉션 타입이어야함.
    //하지만 @onetomany , @manytoone 만 사용하면 여러 단방향 관계에 지나지않는다.
    //양방향 관계를 만들어주기 위해서는 mappedBy를 사용해줘야 한다.
    //양방향 관계를 설정했다면 @ManyToOne 어노테이션이 달려있는 곳이 주인이 된다.(fk를 가진쪽)
    //어떤 멤버 변수와 매핑될건지를 선언해야 한다.
    @OneToMany(mappedBy = "owner")
    private Set<Study> studies = new HashSet<>();



    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }

    //요즘엔 LocalDateTime.now 같은걸 사용함
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Transient //@Transient 컬럼으로 매핑을 안해줌
    private String no;

    @Embedded //comosite value 사용방법 @Embedded 어노테이션
    @AttributeOverrides({ //오버라이딩해서 사용 할 수도 있음.
            @AttributeOverride(name = "street", column = @Column(name = "office_street"))
    })
    private Address officeAddress;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addStudy(Study study) {
        this.getStudies().add(study);
        study.setOwner(this);
    }

    public void removeStudy(Study study) {
        this.getStudies().remove(study);
        study.setOwner(null);
    }


}
