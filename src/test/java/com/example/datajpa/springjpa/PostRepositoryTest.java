package com.example.datajpa.springjpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void crud() {
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post); //persist

        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(savedPost)).isTrue();
        assertThat(savedPost == post);

        //같은 Id에 대해 save를 했을시 insert가 발생하지 않고 update가 발생함.
        Post postUpdate = new Post();
        post.setId(1L);
        postUpdate.setTitle("hibernate");
        Post updatePost = postRepository.save(postUpdate); //merge

        assertThat(entityManager.contains(updatePost)).isTrue();
        assertThat(entityManager.contains(postUpdate)).isFalse();
        assertThat(updatePost == postUpdate);   //같지않음

        //Transient 상태인지 Detached 상태인지는 어떻게 알지?
        // - 엔티티의 @Id 프로퍼티를 찾는다. 해당 프로퍼티가 null이면 Transient 상태로 판단.
        // - id가 null이 아니면 Detached 상태로 판단. -> Detached 상태는 save시 Persistent 상태로 merge된다.

        //실수를 줄이기 위해서는 리턴받은 변수를 사용해야한다.
        //persistent 상태의 변수를 사용하게되어 결과가 달라질 수 있다.
        //savedPost.setTitle("this");

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }
}