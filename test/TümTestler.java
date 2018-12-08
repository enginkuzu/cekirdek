import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({test2sözcükler.BirimTestleri.class, test3cümleler.BirimTestleri.class})
public class TümTestler {
}