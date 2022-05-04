package com.example.netty_study.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class IOSPush {

    private static final Logger logger = LoggerFactory.getLogger(IOSPush.class);

    // private static ApnsClient apnsClient = null;

    //private static final Semaphore semaphore = new Semaphore(10000);//Semaphore又称信号量，是操作系统中的一个概念，在Java并发编程中，信号量控制的是线程并发的数量。

    @SuppressWarnings("rawtypes")
    public void push(final String deviceToken, String alertTitle, String alertBody,boolean contentAvailable,Map<String, Object> customProperty,int badge) {

        // long startTime = System.currentTimeMillis();

        ApnsClient apnsClient = APNSConnect.getAPNSConnect();

        // long total = deviceTokens.size();

        //  final CountDownLatch latch = new CountDownLatch(deviceTokens.size());//每次完成一个任务（不一定需要线程走完），latch减1，直到所有的任务都完成，就可以执行下一阶段任务，可提高性能

        final AtomicLong successCnt = new AtomicLong(0);//线程安全的计数器

        // long startPushTime =  System.currentTimeMillis();

        // for (String deviceToken : deviceTokens) {

        ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();

        if(alertBody!=null&&alertTitle!=null) {
            payloadBuilder.setAlertBody(alertBody);
            payloadBuilder.setAlertTitle(alertTitle);
        }

        //如果badge小于0，则不推送这个右上角的角标，主要用于消息盒子新增或者已读时，更新此状态
        if(badge>0) {
            payloadBuilder.setBadgeNumber(badge);
        }

        //将所有的附加参数全部放进去
        if(customProperty!=null) {
            for(Map.Entry<String, Object> map:customProperty.entrySet()) {
                payloadBuilder.addCustomProperty(map.getKey(),map.getValue());
            }
        }



        payloadBuilder.setContentAvailable(contentAvailable);

        String payload = payloadBuilder.buildWithDefaultMaximumLength();
        final String token = TokenUtil.sanitizeTokenString(deviceToken);
        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, "com.Huali-tec.HLSmartWay", payload);

//            try {
//                semaphore.acquire();//从信号量中获取一个允许机会
//            } catch (Exception e) {
//                logger.error("ios push get semaphore failed, deviceToken:{}", deviceToken);//线程太多了，没有多余的信号量可以获取了
//                e.printStackTrace();
//            }

        final Future<PushNotificationResponse<SimpleApnsPushNotification>> future = apnsClient.sendNotification(pushNotification);

        future.addListener(new GenericFutureListener<Future<PushNotificationResponse>>() {
            @Override
            public void operationComplete(Future<PushNotificationResponse> pushNotificationResponseFuture) throws Exception {
                if (future.isSuccess()) {
                    final PushNotificationResponse<SimpleApnsPushNotification> response = future.getNow();
                    if (response.isAccepted()) {
                        successCnt.incrementAndGet();
                    } else {
                        Date invalidTime = response.getTokenInvalidationTimestamp();
                        logger.error("Notification rejected by the APNs gateway: " + response.getRejectionReason());
                        if (invalidTime != null) {
                            logger.error("\t…and the token is invalid as of " + response.getTokenInvalidationTimestamp());
                        }
                    }
                } else {
                    logger.error("send notification device token={} is failed {} ", token, future.cause().getMessage());
                }
                //  latch.countDown();
                // semaphore.release();//释放允许，将占有的信号量归还
            }
        });

        //}

//        try {
//            latch.await(20, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            logger.error("ios push latch await failed!");
//            e.printStackTrace();
//        }

        //  long endPushTime = System.currentTimeMillis();

        //  logger.info("test pushMessage success. [共推送" + total + "个][成功" + (successCnt.get()) + "个],totalcost= " + (endPushTime - startTime) + ", pushCost=" + (endPushTime - startPushTime));
    }

    public void checkDeviceToken(final String deviceToken){

    }

}
