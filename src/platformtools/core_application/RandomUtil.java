package platformtools.core_application;

import java.util.Random;

/**
 * Random utilities class
 * Created by NeilDG on 5/27/2017.
 */

public class RandomUtil {
    public static int randomRangeInclusive(int min, int max) {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /*
     * Random where max is exclusive
     */
    public static int randomRangeExclusive(int min, int max) {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        int randomNum = rand.nextInt(max - min) + min;

        return randomNum;
    }
}
