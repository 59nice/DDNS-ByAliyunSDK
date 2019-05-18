package cloud.thanos.ddns.schedule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author leanderli
 * @see
 * @since 2019/5/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DDNSScheduleTest {

    @Autowired
    private DDNSSchedule schedule;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute() {
        schedule.execute();
    }
}