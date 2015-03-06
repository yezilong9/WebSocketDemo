package com.yz.socket.server.clients.push;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yz.socket.message.MessageType;
import com.yz.socket.server.clients.ClientsManager;
import com.yz.socket.util.Util;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 测试内容自动推送
 */
public class ContentPush {

    private static final Logger logger = LoggerFactory.getLogger(ContentPush.class);

    private static ScheduledExecutorService executor;
    private static ExecutorService workers;

    private static final Map<Integer, Channel> timeShareChannelMap = Maps.newConcurrentMap();
    private static boolean started;


    public static void add(Integer userId, Channel channel) {
        logger.info(" User " + userId + " add...");
        timeShareChannelMap.put(userId, channel);
        open();
    }

    public static void remove(Integer userId) {
        timeShareChannelMap.remove(userId);
        if (timeShareChannelMap.size() == 0) {
            close();
        }
    }

    private static void Push() throws InterruptedException {
        executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                workers.execute(new Runnable() {
                    public void run() {
                        for (Map.Entry<Integer, Channel> entry : timeShareChannelMap.entrySet()) {
                            if (!ClientsManager.isOnline(entry.getKey())){
                                remove(entry.getKey());
                            } else {
                                Util.sendServerMessage(entry.getValue(), entry.getKey(), "自动推送消息", MessageType.SUCCESS);//
                                logger.info("user" + entry.getKey() + "接受到数据");
                            }
                        }
                    }
                });
            }

        }, 0, 1, TimeUnit.SECONDS);
    }

    private synchronized static void close() {
        if (started) {
            if (executor != null) {
                executor.shutdown();
            }
            if (workers != null) {
                workers.shutdown();
            }
            logger.info("循环获取要停止啦");
            started = false;
        }
    }

    private synchronized static void open() {
        if (!started) {
            executor = Executors.newScheduledThreadPool(5);
            workers = Executors.newCachedThreadPool();
            try {
                Push();
                logger.info("循环获取开始了");
                started = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
