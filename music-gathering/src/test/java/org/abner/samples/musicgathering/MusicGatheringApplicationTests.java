package org.abner.samples.musicgathering;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MusicGatheringApplication.class)
@ActiveProfiles("test")
public class MusicGatheringApplicationTests {

	@Test
	public void contextLoads() {
	}

}

