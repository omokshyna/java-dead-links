import org.junit.Test;

import static org.junit.Assert.*;

public class FindLinksTest {


    @Test
    public void countBadLinks1() throws Exception {
        FindLinks findLinks = new FindLinks();
        int links = findLinks.countBadLinks("https://android.stackexchange.com/questions/4538/can-i-emulate-a-bluetooth-keyboard-with-my-android-device");
        int result = 10;
        assertEquals(result, links);
    }


}