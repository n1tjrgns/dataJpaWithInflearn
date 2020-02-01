package com.example.datajpa;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

        //entityManager.persist(account);
        //Session으로도 저장이 가능해 신기하네
        Session session = entityManager.unwrap(Session.class);
        session.save(account);
    }
}
