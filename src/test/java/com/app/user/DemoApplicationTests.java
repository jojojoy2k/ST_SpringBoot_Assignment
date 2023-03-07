package com.app.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    public void testMain() {
        assertDoesNotThrow(() -> UserManagementApplication.main(new String[] {}));
    }
}
