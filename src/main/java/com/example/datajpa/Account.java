package com.example.datajpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    //모든 멤버변수에는 @Column이 생략되어있음
    private String username;

    private String password;

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
}
