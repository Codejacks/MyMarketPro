package me.chenfuduo.mymarketpro.test;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenfuduo on 2015/10/2.
 */
public class ThreadPoolDemo {

    int maxCount = 3;
    //int count = 0;
    //同步
    AtomicInteger count = new AtomicInteger(0);

    LinkedList<Runnable> runnables = new LinkedList<>();

    public void execute(Runnable runnable) {
        runnables.add(runnable);
        //count++;
        if (count.incrementAndGet() <= maxCount) {
            createThread();
        }
    }

    private void createThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    //取出来一个异步任务
                    if (runnables.size() > 0) {
                        Runnable runnable = runnables.remove(0);
                        if (runnable != null) {
                            runnable.run();
                        }
                    }else{
                        //等待 wake();

                    }
                }
            }
        }).start();
    }
}
