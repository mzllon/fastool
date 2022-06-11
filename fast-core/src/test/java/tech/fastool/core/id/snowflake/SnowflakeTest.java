package tech.fastool.core.id.snowflake;

import org.junit.jupiter.api.Test;

/**
 * Tester for {@linkplain FastSnowflake} and {@linkplain  TwitterSnowflake}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-11
 */
public class SnowflakeTest {

    @Test
    public void fast() {
        for (int j = 0; j < 10; j++) {
            long start = System.currentTimeMillis();
            FastSnowflake fastSnowflake = new FastSnowflake();
            // Set<Long> ids = new HashSet<>();
            for (int i = 0; i < 1000000; i++) {
                //ids.add(fastSnowflake.nextId());
                fastSnowflake.nextId();
            }
            System.out.println("cost times = " + (System.currentTimeMillis() - start));
        }

    }

    @Test
    public void twitter() {
        for (int j = 0; j < 10; j++) {
            long start = System.currentTimeMillis();
            TwitterSnowflake twitterSnowflake = new TwitterSnowflake();
//            Set<Long> ids = new HashSet<>();
            for (int i = 0; i < 1000000; i++) {
//                ids.add(twitterSnowflake.nextId());
                twitterSnowflake.nextId();
            }
            System.out.println("cost times = " + (System.currentTimeMillis() - start));
        }

    }

}