package org.repositories;

import org.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    UserRepository repo;
    User user;

    @BeforeEach
    void setUp() {
        repo = new UserRepository();
        user = new User("TestName1", "123123");
        repo.clear();
    }


    @Test
    void test_saveInUserStorage() {
        repo.save(user.getId(), user);
        assertEquals(1, repo.getAll().size());
    }

    @Test
    void test_getByIdReturnUser() {
        repo.save(user.getId(), user);
        User found = repo.getById(user.getId());
        assertNotNull(found);
        assertEquals("TestName1", found.getUsername());
        assertEquals(user, found);
    }

    @Test
    void test_getAllReturnEmpty() {
        assertTrue(repo.getAll().isEmpty());
    }

    @Test
    void test_GetAllReturnsAllUsers() {
        User user1 = new User("TestUser1", "111111");
        User user2 = new User("TestUser2", "222222");
        repo.save(user1.getId(), user1);
        repo.save(user2.getId(), user2);

        List<User> all = repo.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(user1));
        assertTrue(all.contains(user2));
    }

    @Test
    void test_clearUserStorage() {
        repo.save(user.getId(), user);
        repo.clear();
        assertEquals(0, repo.getAll().size());
    }

}