package com.example.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class JpaDataRunner implements ApplicationRunner {

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        postRepository.findAll().forEach(System.out::println);
    }
}
