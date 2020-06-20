package com.altHealth;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppTest {

	/*
	 * @Test public void contextLoads() { }
	 * 
	 * @Autowired private TestRestTemplate restTemplate;
	 * 
	 * @Test public void homeResponse() { String body =
	 * this.restTemplate.getForObject("/", String.class);
	 * assertThat(body).isEqualTo("Spring is here!"); }
	 */
	
	/**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
