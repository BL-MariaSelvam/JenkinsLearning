package com.demo.jenkinslearning;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JenkinsLearningApplicationTests {

	 @Autowired
	    private org.springframework.context.ApplicationContext context;

	    @Test
	    void contextLoads() {
	        assertNotNull(context);
	    }

}
