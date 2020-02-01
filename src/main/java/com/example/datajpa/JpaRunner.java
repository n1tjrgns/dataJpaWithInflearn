package com.example.datajpa;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//EntityManager와 관련된 Operation은 한 트랜잭션 안에서 실행되어야 한다.
@Transactional
@Component
public class JpaRunner implements ApplicationRunner {

    //JPA 가장 핵심 클래스, 이 클래스로 데이터의 영속화가 가능(영속화 = 저장)
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("n1tjrgns");
        account.setPassword("hibernate");

        Study study = new Study();
        study.setName("Spring Data Jpa");
        study.setOwner(account);


        //양방향 관계를 설정했을 경우 주인쪽에 관계를 설정해줘야함
        // ++ 양방향이니까 둘 다 설정해줘야겠지?
        /*account.getStudies().add(study); //애는 optional
        study.setOwner(account);*/

        //그러므로 객체지향 관점에서 둘을 묶어서 관리하는 것이 좋아
        //리팩토링 ㄱㄱ
        account.addStudy(study);

        //entityManager.persist(account);
        //Session으로도 저장이 가능해 신기하네
        Session session = entityManager.unwrap(Session.class);
        session.save(account);
        session.save(study);

        //캐시를 사용해 원래 가지고 있던 객체에서 가져옴
        Account n1tjrgns = session.load(Account.class, account.getId());
        System.out.println("=====================");
        System.out.println(n1tjrgns.getUsername());

        //JpaRunner 클래스에서 객체에 set 하는 행위는 JPA 입장에서는 transient 상태이다
        //save 되는 순간 Persistent 상태가 되는데
        Post post = new Post();
        post.setTitle("DATA JPA 언제보나");

        Comment comment = new Comment();
        comment.setComment("빨리 보자");
        post.addComment(comment);

        Comment comment1 = new Comment();
        comment1.setComment("응 알겠어");
        post.addComment(comment);

        session.save(post);

        //네이티브 쿼리 작성 방법
        List<Post> posts = entityManager.createNativeQuery("Select * FROM Post", Post.class)
                .getResultList();
        posts.forEach(System.out::println);
    }
}
