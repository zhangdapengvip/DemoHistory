package com.zero.library.base.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by ZeroAries on 2016/3/9.
 * Rx事件总线
 */
public class RxBus {
    private volatile static RxBus mRxBusInstance;
    private final Subject mBus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus() {
        mBus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例RxBus
    public static RxBus getInstance() {
        if (mRxBusInstance == null) {
            synchronized (RxBus.class) {
                if (mRxBusInstance == null) {
                    mRxBusInstance = new RxBus();
                }
            }
        }
        return mRxBusInstance;
    }

    // 提供了一个新的事件
    public void send(Object o) {
        mBus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T extends Object> Observable<T> regist(final Class<T> eventType) {
        return mBus.ofType(eventType);
    }
}