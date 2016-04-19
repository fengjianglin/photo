package photo.baby.utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by apple on 16/4/18.
 */
public class Utils {

    private static final AtomicInteger sequenceGenerator = new AtomicInteger();

    public static String token() {
        final int LENGTH = 10;
        int seq = sequenceGenerator.getAndIncrement();
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer().append(seq).append("_");
        for (int i = 0; i < LENGTH; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String accessToken() {
        final int LENGTH = 6;
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < LENGTH; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
