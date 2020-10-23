import com.tty.task.main.CompetitionCrawlingTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: dukeqiang
 * @Date: 2020/10/16 10:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:appcontext/app-spring-config-tty-task.xml")
public class TaskTest {
    @Test
    public void task1(){
        new CompetitionCrawlingTask().start();
    }
}
