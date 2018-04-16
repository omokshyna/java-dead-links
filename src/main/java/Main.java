public class Main {
    public static void main(String[] args) throws Exception {
        DeadLinks deadLinks = new JsoupBadLinks();
        DeadLinks deadLinkss = new SeleniumBadLinks();

        deadLinks.badLinks("https://android.stackexchange.com/questions/4538/can-i-emulate-a-bluetooth-keyboard-with-my-android-device");

    }

    }




