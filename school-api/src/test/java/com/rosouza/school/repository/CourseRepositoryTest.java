package com.rosouza.school.repository;

import com.rosouza.school.config.LiquibaseConfiguration;
import com.rosouza.school.domain.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


import static com.rosouza.school.CourseTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles(SPRING_PROFILE_TEST)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableConfigurationProperties({LiquibaseProperties.class})
@TestPropertySource(properties = {
"spring.jpa.hibernate.ddl-auto=validate"
})
@Import({LiquibaseConfiguration.class})
class CourseRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CourseRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repository.flush();
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void should_delete_all_course() {
        entityManager.persist(entity());
        entityManager.persist(entityOther());

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    void should_delete_course_by_id() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entity();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        repository.deleteById(entity2.getId());

        var entities = repository.findAll();
        assertThat(entities).hasSize(2).contains(entity1, entity3);
    }

    @Test
    void should_find_all_course() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entity();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        var entities = repository.findAll();
        assertThat(entities).hasSize(3).contains(entity1, entity2, entity3);
    }

    @Test
    void should_find_course_by_id() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entity();
        entityManager.persist(entity2);

        var foundEntity = repository.findById(entity1.getId()).get();
        assertThat(foundEntity).isEqualTo(entity1);

        foundEntity = repository.findById(entity2.getId()).get();
        assertThat(foundEntity).isEqualTo(entity2);
    }

    @Test
    void should_find_no_course_if_repository_is_empty() {
        var entities = repository.findAll();
        assertThat(entities).isEmpty();
    }

    @Test
    void should_store_a_course() {
        var entity = repository.save(entity());
        assertThat(entity).hasFieldOrPropertyWithValue("name", NAME);

        var entityOther = repository.save(entityOther());
        assertThat(entityOther).hasFieldOrPropertyWithValue("name", NAME_OTHER);
    }

    @Test
    void should_update_course_by_id() {
        var entity1 = entity();
        entityManager.persist(entity1);

        var entity2 = entity();
        entityManager.persist(entity2);

        var entity3 = entityOther();
        entityManager.persist(entity3);

        var entity = repository.findById(entity2.getId()).get();
        entity.setName(entity3.getName());
        repository.save(entity);

        var checkEntity = repository.findById(entity2.getId()).get();
        assertThat(checkEntity.getId()).isEqualTo(entity2.getId());
        assertThat(checkEntity.getName()).isEqualTo(entity3.getName());
    }

    Course entity() {
        var entity = new Course();
        entity.setName(NAME);
        return entity;
    }

    Course entityOther() {
        var entity = new Course();
        entity.setName(NAME_OTHER);
        return entity;
    }

}
