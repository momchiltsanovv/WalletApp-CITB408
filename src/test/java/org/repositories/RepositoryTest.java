package org.repositories;

import org.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    Repository<User, UUID> repo = new UserRepository();

    @BeforeEach
    void setUp() {
        repo = new UserRepository();
        repo.clear();
    }

    @Test
    void test_SaveAndGetById() {

        User user = new User("test5", "123456");
        repo.save(user.getId(), user);

        User found = repo.getById(user.getId());
        assertNotNull(found);
        assertEquals("test5", found.getUsername());
        assertEquals(user, found);
    }

    @Test
    void test_GetAll() {
        assertTrue(repo.getAll().isEmpty());

        User user1 = new User("testu1", "222222");
        User user2 = new User("testu2", "333333");
        repo.save(user1.getId(), user1);
        repo.save(user2.getId(), user2);

        List<User> all = repo.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(user1));
        assertTrue(all.contains(user2));
    }
}

