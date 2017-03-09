package com.http.demo;

import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZeroAries on 2016/1/28.
 * RxJava 操作符
 */
public class DemoRxJava {
    static int count;

    public static void testRxJava(RxOperator operator) {
        testRxJava(operator, null, null);
    }

    public static void testRxJava(RxOperator operator, Scheduler scheduler, Scheduler schedulerTwo) {
        Observable<Long> one = Observable.interval(0, 1000, TimeUnit.MILLISECONDS).map(l -> {
            return l * 5;
        }).take(5);
        Observable<Long> two = Observable.interval(500, 1000, TimeUnit.MILLISECONDS).map(l -> {
            return l * 10;
        }).take(5);
        if (null == scheduler || null == schedulerTwo) {
            scheduler = Schedulers.newThread();
            schedulerTwo = AndroidSchedulers.mainThread();
        }
        switch (operator) {
            case CREATE:
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Text String");
                        // subscribeOn设置线程
                        Log.e("subscriber", "------->call线程:" + Thread.currentThread().getName());
                    }
                }).subscribeOn(scheduler).observeOn(schedulerTwo).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("subscribe", "------->onNext线程:msg=" + e.getMessage() + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        // observeOn设置线程
                        Log.e("subscribe", "------->onNext线程:msg=" + s + Thread.currentThread().getName());
                    }
                });
                break;
            case LAMBDA:
                Observable.create(subscriber -> {
                    subscriber.onNext("Text String");
                    // subscribeOn设置线程
                    Log.e("subscriber", "------->call线程:" + Thread.currentThread().getName());
                }).subscribeOn(scheduler).observeOn(schedulerTwo).subscribe(msg -> {
                    // observeOn设置线程
                    Log.e("subscribe", "------->onNext线程:msg=" + msg + Thread.currentThread().getName());
                }, e -> {
                    Log.e("subscribe", "------->onNext线程:msg=" + e.getMessage() + Thread.currentThread().getName());
                }, () -> {
                    Log.e("subscribe", "------->onNext线程:complete" + Thread.currentThread().getName());
                });
                break;
            case FROM:
                Integer[] items = {0, 1, 2, 3, 4, 5};
                Observable.from(items).subscribe(l -> {
                    Log.e("obserableJust", "value:=" + l);
                }, e -> {
                    Log.e("obserableJust", "value:=" + e.getMessage());
                }, () -> {
                    Log.e("obserableJust", "value:= complete");
                });
                break;
            case JUST:
                count = 10;
                Observable.just(count).subscribe(l -> {
                    Log.e("obserableJust", "value:=" + l);
                }, e -> {
                    Log.e("obserableJust", "value:=" + e.getMessage());
                }, () -> {
                    Log.e("obserableJust", "value:= complete");
                });
                break;
            case DEFER:
                count = 10;
                Observable<Integer> defer = Observable.defer(() -> {
                    return Observable.just(count);
                });
                count = 20;
                defer.subscribe(l -> {
                    Log.e("obserableJust", "value:=" + l);
                }, e -> {
                    Log.e("obserableJust", "value:=" + e.getMessage());
                }, () -> {
                    Log.e("obserableJust", "value:= complete");
                });
                break;
            case BUFFER:
                Observable.interval(1, TimeUnit.SECONDS).take(20).subscribeOn(Schedulers.newThread()).observeOn(schedulerTwo).buffer(5,
                        TimeUnit.SECONDS).subscribe(msg -> {
                    Log.e("subscribe", "------->:buffer=" + msg);
                }, e -> {
                    Log.e("subscribe", "------->buffer=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case FLATMAP:
                Observable.just(Environment.getExternalStorageDirectory()).flatMap(DemoRxJava::listFile).subscribe(msg -> {
                    Log.e("subscribe", "------->:flatMap=" + msg.getAbsolutePath());
                });

                //  输出结果20 10 30 15 10 5
                Observable.just(10, 20, 30).flatMap(i -> {
                    int delay = 50;
                    if (i > 10)
                        delay = 20;
                    return Observable.from(new Integer[]{i, i / 2}).delay(delay, TimeUnit.SECONDS);
                }).subscribe(msg -> {
                    Log.e("subscribe", "------->:flatMap=" + msg);
                });

                break;
            case CONCATMAP:
                // 输出结果 10 5 20 10 30 15
                Observable.just(10, 20, 30).concatMap(i -> {
                    int delay = 50;
                    if (i > 10)
                        delay = 20;
                    return Observable.from(new Integer[]{i, i / 2}).delay(delay, TimeUnit.SECONDS);
                }).subscribe(msg -> {
                    Log.e("subscribe", "------->:concatMap=" + msg);
                });
                break;
            case SWITCHMAP:
                // 输出结果 30 15
                Observable.just(10, 20, 30).switchMap(i -> {
                    int delay = 50;
                    if (i > 10)
                        delay = 20;
                    return Observable.from(new Integer[]{i, i / 2}).delay(delay, TimeUnit.SECONDS);
                }).subscribe(msg -> {
                    Log.e("subscribe", "------->:switchMap=" + msg);
                });
                break;
            case GROUPBY:
                Observable.interval(1, TimeUnit.SECONDS).take(10).groupBy(o -> {
                    return o % 3;
                }).subscribe(result -> {
                    result.subscribe(v -> {
                        Log.e("subscribe", "------->:groupBy=" + result.getKey() + "value=" + v);
                    });
                });
                break;
            case CAST:
                Observable.just(1, 2, 3, 4, 5).cast(Integer.class).subscribe(v -> {
                    Log.e("subscribe", "------->:cast=" + v);
                });
                break;
            case SCAN:
                Observable.just(1, 2, 3, 4, 5, 6).scan((sum, item) -> {
                    return sum + item;
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:scan=" + v);
                });
                break;
            case WINDOW:
                Observable.interval(1, TimeUnit.SECONDS).take(12).window(3, TimeUnit.SECONDS).subscribe(observable -> {
                    Log.e("subscribe", "------->:window start");
                    observable.subscribe(l -> {
                        Log.e("subscribe", "------->:window=" + l);
                    });
                });
                break;
            case DEBOUNCE:
                // 输出结果：4 5 6 7 8 9 complete
                Observable.create(subscriber -> {
                    if (subscriber.isUnsubscribed())
                        return;
                    for (int i = 1; i < 10; i++) {
                        subscriber.onNext(i);
                        SystemClock.sleep(i * 100);
                    }
                    subscriber.onCompleted();
                }).subscribeOn(Schedulers.newThread()).debounce(400, TimeUnit.MILLISECONDS).subscribe(v -> {
                    Log.e("subscribe", "------->:debounce=" + v);
                }, e -> {

                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case DISTINCT:
                // 输出结果 1 2 3 4 5
                Observable.just(1, 2, 3, 4, 2, 1, 7).distinct().subscribe(v -> {
                    Log.e("subscribe", "------->:distinct=" + v);
                });
                break;
            case ELEMENTAT:
                // 输出结果 4
                Observable.just(1, 2, 3, 4, 2, 1, 7).elementAt(3).subscribe(v -> {
                    Log.e("subscribe", "------->:elementAt=" + v);
                });
                break;
            case FILTER:
                // 输出结果 1 2 3 2 1
                Observable.just(1, 2, 3, 4, 2, 1, 7).filter(integer -> {
                    return integer < 4;
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:filter=" + v);
                });
                break;
            case OFTYPE:
                // 输出结果 0.23
                Observable.just(1, "hello world", true, 200L, 0.23f).ofType(Float.class).subscribe(v -> {
                    Log.e("subscribe", "------->:ofType=" + v);
                });
                break;
            case FIRST:
                // 输出结果 1
                Observable.just(1, "hello world", true, 200L, 0.23f).first().subscribe(v -> {
                    Log.e("subscribe", "------->:first=" + v);
                });
                break;
            case LAST:
                // 输出结果 0.23
                Observable.just(1, "hello world", true, 200L, 0.23f).last().subscribe(v -> {
                    Log.e("subscribe", "------->:last=" + v);
                });
                break;
            case SINGLE:
                // 输出结果 Sequence contains no elements
                Observable.just(1, 2, 3, 4, 5).single(integer -> {
                    return integer > 10;
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:single=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:single=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case IGNOREELEMENTS:
                // 输出结果 complete
                Observable.just(1, 2, 3, 4, 5).ignoreElements().subscribe(v -> {
                    Log.e("subscribe", "------->:ignoreElements=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:ignoreElements=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case SAMPLE:
                // 输出结果 3 5 7 8 complete
                Observable.create(subscriber -> {
                    if (subscriber.isUnsubscribed())
                        return;
                    try {
                        //前8个数字产生的时间间隔为1秒，后一个间隔为3秒
                        for (int i = 1; i < 9; i++) {
                            subscriber.onNext(i);
                            Thread.sleep(1000);
                        }
                        Thread.sleep(2000);
                        subscriber.onNext(9);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }).subscribeOn(Schedulers.newThread()).sample(2200, TimeUnit.MILLISECONDS)  //采样间隔时间为2200毫秒
                        .subscribe(v -> {
                            Log.e("subscribe", "------->:sample=" + v);
                        }, e -> {
                            Log.e("subscribe", "------->:sample=" + e.getMessage());
                        }, () -> {
                            Log.e("subscribe", "------->:complete");
                        });
                break;
            case SKIP:
                // 输出结果 4 5 complete
                Observable.just(1, 2, 3, 4, 5).skip(3).subscribe(v -> {
                    Log.e("subscribe", "------->:skip=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:skip=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case SKIPLAST:
                // 输出结果 1 2 complete
                Observable.just(1, 2, 3, 4, 5).skipLast(3).subscribe(v -> {
                    Log.e("subscribe", "------->:skipLast=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:skipLast=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case TIMER:
                Observable.timer(5, TimeUnit.SECONDS, scheduler).map(l -> {
                    Log.e("observableTimer", "------->onNext线程:" + SystemClock.elapsedRealtime() + Thread.currentThread().getName());
                    return null;
                }).subscribe();
                break;
            case INTERVAL:
                int startTime = 0;//开始时间
                int space = 2;//间隔
                Observable.interval(startTime, space, TimeUnit.SECONDS).take(4).subscribe(l -> {
                    Log.e("interval", "------->:" + l);
                }, e -> {
                    Log.e("subscribe", "------->:" + e.getMessage());
                }, () -> {
                    Log.e("interval", "------->:complete");
                });
                break;
            case RANGE:
                int startCount = 2;//开始数字
                int endCount = 5;//结束数字
                Observable.range(startCount, endCount).subscribe(l -> {
                    Log.e("interval", "------->:" + l);
                }, e -> {
                    Log.e("subscribe", "------->:" + e.getMessage());
                }, () -> {
                    Log.e("interval", "------->:complete");
                });
                break;
            case REPEAT:
                int repeate = 3;//重复次数
                Observable.range(2, 5).repeat(repeate).subscribe(l -> {
                    Log.e("interval", "------->:" + l);
                }, e -> {
                    Log.e("subscribe", "------->:" + e.getMessage());
                }, () -> {
                    Log.e("interval", "------->:complete");
                });
                break;
            case TAKE:
                // 输出结果 1 2 3 complete
                Observable.just(1, 2, 3, 4, 5).take(3).subscribe(v -> {
                    Log.e("subscribe", "------->:skip=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:skip=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case TAKEFIRST:
                // 输出结果 4 complete
                Observable.just(1, 2, 3, 4, 5).takeFirst(integer -> {
                    return integer > 3;
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:takeFirst=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:takeFirst=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case TAKELAST:
                // 输出结果 4 5 complete
                Observable.just(1, 2, 3, 4, 5).takeLast(2).subscribe(v -> {
                    Log.e("subscribe", "------->:takeLast=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:takeLast=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case COMBINELATEST:
                // ont + two 输出结果 0 5 15 20 30 35 45 50 60 complete
                // ont + one 输出结果 0 5 15 20 25 30 35 40 complete
                Observable.combineLatest(one, one, (o1, o2) -> {
                    return o1 + o2;
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:combineLatest=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:combineLatest=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case JOIN:
                one.join(two, left -> {
                    //使Observable延迟600毫秒执行
                    Log.e("subscribe", "------->:left=" + left);
                    return Observable.just(left).delay(600, TimeUnit.MILLISECONDS);
                }, right -> {
                    //使Observable延迟600毫秒执行
                    Log.e("subscribe", "------->:right=" + right);
                    return Observable.just(right).delay(600, TimeUnit.MILLISECONDS);
                }, (o1, o2) -> {
                    return o1 + o2;
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:join=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:join=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case GROUPJOIN:
                // 如ObservableA每隔500毫秒产生数据为0,5,10,15,20；
                // 而ObservableB每隔500毫秒产生数据0,10,20,30,40，其中第一个数据延迟500毫秒产生。
                one.groupJoin(two, left -> {
                    //使Observable延迟600毫秒执行
                    Log.e("subscribe", "------->:left=" + left);
                    return Observable.just(left).delay(600, TimeUnit.MILLISECONDS);
                }, right -> {
                    //使Observable延迟600毫秒执行
                    Log.e("subscribe", "------->:right=" + right);
                    return Observable.just(right).delay(600, TimeUnit.MILLISECONDS);
                }, (aLong, observable) -> {
                    return observable.map(aLong1 -> {
                        return aLong + aLong1;
                    });
                }).subscribe(aObservable -> {
                    aObservable.subscribe(v -> {
                        Log.e("subscribe", "------->:groupJoin=" + v);
                    }, e -> {
                        Log.e("subscribe", "------->:groupJoin=" + e.getMessage());
                    }, () -> {
                        Log.e("subscribe", "------->:complete");
                    });
                }, e -> {
                    Log.e("subscribe", "------->:groupJoin=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case MERGE:
                // 最后合并结果为：0,0,5,10,10,20,15,30,20,40;
                Observable.merge(one, two).subscribe(v -> {
                    Log.e("subscribe", "------->:merge=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:merge=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case MERGEDELAYERROR:
                Observable.mergeDelayError(one, two).subscribe(v -> {
                    Log.e("subscribe", "------->:mergeDelayError=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:mergeDelayError=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case STARTWITH:
                // 输出结果 2 3 4 10 20 30 complete
                Observable.just(10, 20, 30).startWith(2, 3, 4).subscribe(v -> {
                    Log.e("subscribe", "------->:startWith=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:startWith=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case SWITCHONNEXT:
                // 输出结果 0 10 20 0 10 20 30 40 complete
                Observable<Observable<Long>> observable = Observable.interval(0, 500, TimeUnit.MILLISECONDS).map(aObservable -> {
                    return Observable.interval(0, 200, TimeUnit.MILLISECONDS).map(aLong -> {
                        return aLong * 10;
                    }).take(5);
                }).take(2);
                Observable.switchOnNext(observable).subscribe(v -> {
                    Log.e("subscribe", "------->:switchOnNext=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:switchOnNext=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case ZIP:
                // 输出结果 14 25 32 complete
                Observable.zip(Observable.just(10, 20, 30), Observable.just(4, 5, 12, 16), (aInt, bInt) -> {
                    return aInt + bInt;
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:zip=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:zip=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case ONERRORRETURN:
                // 输出结果 0 1 2 3 10086 complete
                Observable.create(subscriber -> {
                    if (subscriber.isUnsubscribed())
                        return;
                    try {
                        for (int i = 0; i < 10; i++) {
                            if (i == 4) {
                                throw new Exception("this is number 4 error！");
                            }
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }).onErrorReturn(e -> {
                    return 10086;
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:onErrorReturn=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:onErrorReturn=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case ONERRORRESUMENEXT:
                // 输出结果 0 1 2 3 100 101 102 complete
                Observable.create(subscriber -> {
                    if (subscriber.isUnsubscribed())
                        return;
                    try {
                        for (int i = 0; i < 10; i++) {
                            if (i == 4) {
                                throw new Exception("this is number 4 error！");
                            }
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }).onErrorResumeNext(l -> {
                    return Observable.just(100, 101, 102);
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:onErrorResumeNext=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:onErrorResumeNext=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case RETRY:
                // 输出结果 0 1 2 3 0 1 2 3 this is number 4 error！
                Observable.create(subscriber -> {
                    if (subscriber.isUnsubscribed())
                        return;
                    try {
                        for (int i = 0; i < 10; i++) {
                            if (i == 4) {
                                throw new Exception("this is number 4 error！");
                            }
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }).retry(2).subscribe(v -> {
                    Log.e("subscribe", "------->:retry=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:retry=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
            case RETRYWHEN:
                // 输出结果 subscribing delay retry by 1 second(s) subscribing delay retry by 2 second(s) subscribing delay retry by 3 second(s)
                Observable.create(subscriber -> {
                    Log.e("subscribe", "------->:subscribing");
                    subscriber.onError(new Exception("demo error"));
                }).retryWhen(aObservable -> {
                    return aObservable.zipWith(Observable.range(1, 3), (aException, aInteger) -> {
                        return aInteger;
                    }).flatMap(bInteger -> {
                        Log.e("subscribe", "------->:delay retry by " + bInteger + " second(s)");
                        return Observable.interval(bInteger, TimeUnit.SECONDS);
                    });
                }).subscribe(v -> {
                    Log.e("subscribe", "------->:retry=" + v);
                }, e -> {
                    Log.e("subscribe", "------->:retry=" + e.getMessage());
                }, () -> {
                    Log.e("subscribe", "------->:complete");
                });
                break;
        }
    }

    private static Observable<File> listFile(File file) {
        // 参数file是just操作符产生的结果，这里判断file是不是目录文件，如果是目录文件，则递归查找其子文件flatMap操作符神奇的地方在于，
        // 返回的结果还是一个Observable，而这个Observable其实是包含多个文件的Observable的，输出应该是ExternalCacheDir下的所有文件
        if (file.isDirectory()) {
            return Observable.from(file.listFiles()).flatMap(DemoRxJava::listFile);
        } else {
            return Observable.just(file);
        }
    }
}