package com.http.demo;

/**
 * Created by ZeroAries on 2016/1/28.
 * RxJava 操作符
 */
public enum RxOperator {//compose、reduce、toList()
    //  创建Observable 调用onNext、onError、onCompleted。
    CREATE,
    //  创建Observable lambda方式调用onNext、onError、onCompleted。
    LAMBDA,
    //  把其他类型的对象和数据类型转化成Observable。
    FROM,
    //  把其他类型的对象和数据类型转化成Observable。
    JUST,
    //  直到有订阅者订阅时,才通过Observable的工厂方法创建Observable并执行,defer操作符能够保证Observable的状态是最新的。
    DEFER,
    //  buffer操作符周期性地收集源Observable产生的结果到列表中,并把这个列表提交给订阅者,订阅者处理后,清空buffer列表,
    //  同时接收下一次收集的结果并提交给订阅者,周而复始。
    //  需要注意的是,一旦源Observable在产生结果的过程中出现异常,即使buffer已经存在收集到的结果,订阅者也会马上收到这个异常,并结束整个过程。
    BUFFER,
    //  flatMap操作符是把Observable产生的结果转换成多个Observable,然后把这多个Observable“扁平化”成一个Observable,并依次提交产生的结果给订阅者。
    //  flatMap操作符在合并Observable结果时,有可能存在交叉的情况。
    FLATMAP,
    //  concatMap操作符在处理产生的Observable时,采用的是“连接(concat)”的方式,而不是“合并(merge)”的方式,
    //  这就能保证产生结果的顺序性,也就是说提交给订阅者的输出结果按照顺序提交的,不会存在交叉的情况。
    CONCATMAP,
    //  switchMap操作符会保存最新的Observable产生的结果而舍弃旧的结果。
    SWITCHMAP,
    //  groupBy操作符是对源Observable产生的结果进行分组,形成一个类型为GroupedObservable的结果集,
    //  GroupedObservable中存在一个方法为getKey(),可以通过该方法获取结果集的Key值（类似于HashMap的key)。
    //  值得注意的是,由于结果集中的GroupedObservable是把分组结果缓存起来,如果对每一个GroupedObservable
    //  不进行处理（既不订阅执行也不对其进行别的操作符运算）,就有可能出现内存泄露。
    //  因此,如果你对某个GroupedObservable不进行处理,最好是对其使用操作符take(0)处理。
    GROUPBY,
    //  cast操作符类似于map操作符,不同的地方在于map操作符可以通过自定义规则,把一个值A1变成另一个值A2,A1和A2的类型可以一样也可以不一样；
    //  而cast操作符主要是做类型转换的,传入参数为类型class,如果源Observable产生的结果不能转成指定的class,则会抛出ClassCastException运行时异常。
    CAST,
    //  scan操作符通过遍历源Observable产生的结果,依次对每一个结果项按照指定规则进行运算,
    //  计算后的结果作为下一个迭代项参数,每一次迭代项都会把计算结果输出给订阅者。
    SCAN,
    //  window操作符非常类似于buffer操作符,区别在于buffer操作符产生的输出结果一个List缓存,而window操作符产生的输出结果一个Observable,
    //  订阅者可以对这个结果Observable重新进行订阅处理。
    WINDOW,
    //  debounce操作符对源Observable每产生一个结果后,如果在规定的间隔时间内没有别的结果产生,则把这个结果提交给订阅者处理,否则忽略该结果。
    //  值得注意的是,如果源Observable产生的最后一个结果后在规定的时间间隔内调用了onCompleted,
    //  那么通过debounce操作符也会把这个结果提交给订阅者。
    DEBOUNCE,
    //  distinct操作符对源Observable产生的结果进行过滤,把重复的结果过滤掉,只输出不重复的结果给订阅者,非常类似于SQL里的distinct关键字。
    DISTINCT,
    //  elementAt操作符在源Observable产生的结果中,仅仅把指定索引的结果提交给订阅者,索引是从0开始的。
    ELEMENTAT,
    //  filter操作符是对源Observable产生的结果按照指定条件进行过滤,只有满足条件的结果才会提交给订阅者
    FILTER,
    //  ofType操作符类似于filter操作符,区别在于ofType操作符是按照类型对结果进行过滤
    OFTYPE,
    //  first操作符是把源Observable产生的结果的第一个提交给订阅者,first操作符可以使用elementAt(0)和take(1)替代。
    FIRST,
    //  last操作符把源Observable产生的结果的最后一个提交给订阅者,last操作符可以使用takeLast(1)替代。
    LAST,
    //  single操作符是对源Observable的结果进行判断,如果产生的结果满足指定条件的数量不为1,则抛出异常,否则把满足条件的结果提交给订阅者
    SINGLE,
    //  ignoreElements操作符忽略所有源Observable产生的结果,只把Observable的onCompleted和onError事件通知给订阅者。
    //  ignoreElements操作符适用于不太关心Observable产生的结果,只是在Observable结束时(onCompleted)或者出现错误时能够收到通知。
    IGNOREELEMENTS,
    //  sample操作符定期扫描源Observable产生的结果,在指定的时间间隔范围内对源Observable产生的结果进行采样
    SAMPLE,
    //  skip操作符针对源Observable产生的结果,跳过前面n个不进行处理,而把后面的结果提交给订阅者处理
    SKIP,
    //  skipLast操作符针对源Observable产生的结果,忽略Observable最后产生的n个结果,而把前面产生的结果提交给订阅者处理
    SKIPLAST,
    //  timer定时器操作符
    TIMER,
    //  interval创建一组在从0开始,个数为N的连续数字,间隔时间startTime开始,产生间隔space
    //  .take(count)设置次数
    INTERVAL,
    //  range创建一组在从startCount开始,个数为endCount的连续数字
    RANGE,
    //  repeat创建一组在从startCount开始,个数为endCount的连续数字,重复repeate次(repeatWhen)
    REPEAT,
    //  take操作符是把源Observable产生的结果,提取前面的n个提交给订阅者,而忽略后面的结果
    TAKE,
    //  takeFirst操作符类似于take操作符,同时也类似于first操作符,都是获取源Observable产生的结果列表中符合指定条件的前一个或多个,
    //  与first操作符不同的是,first操作符如果获取不到数据,则会抛出NoSuchElementException异常,
    //  而takeFirst则会返回一个空的Observable,该Observable只有onCompleted通知而没有onNext通知。
    TAKEFIRST,
    //  takeLast操作符是把源Observable产生的结果的后n项提交给订阅者,提交时机是Observable发布onCompleted通知之时。
    TAKELAST,
    //  combineLatest操作符把两个Observable产生的结果进行合并,合并的结果组成一个新的Observable。
    //  这两个Observable中任意一个Observable产生的结果,都和另一个Observable最后产生的结果,按照一定的规则进行合并。
    COMBINELATEST,
    //  join操作符把类似于combineLatest操作符,也是两个Observable产生的结果进行合并,合并的结果组成一个新的Observable,
    //  但是join操作符可以控制每个Observable产生结果的生命周期,在每个结果的生命周期内,可以与另一个Observable产生的结果按照一定的规则进行合并
    //  join方法的用法如下：
    //  observableA.join(observableB,observableA产生结果生命周期控制函数,
    //  observableB产生结果生命周期控制函数,observableA产生的结果与observableB产生的结果的合并规则）
    JOIN,
    //  groupJoin操作符非常类似于join操作符,区别在于join操作符中第四个参数的传入函数不一致
    GROUPJOIN,
    //  merge操作符是按照两个Observable提交结果的时间顺序,对Observable进行合并,
    MERGE,
    //  merge操作符一旦合并的某一个Observable中出现错误,就会马上停止合并,并对订阅者回调执行onError方法,
    //  mergeDelayError操作符会把错误放到所有结果都合并完成之后才执行
    MERGEDELAYERROR,
    //  startWith操作符是在源Observable提交结果之前,插入指定的某些数据
    STARTWITH,
    //  switchOnNext操作符是把一组Observable转换成一个Observable,转换规则为：对于这组Observable中的每一个Observable所产生的结果,
    //  如果在同一个时间内存在两个或多个Observable提交的结果,只取最后一个Observable提交的结果给订阅者
    SWITCHONNEXT,
    //  zip操作符是把两个observable提交的结果,严格按照顺序进行合并
    ZIP,
    //  onErrorReturn操作符是在Observable发生错误或异常的时候（即将回调oError方法时）,
    //  拦截错误并执行指定的逻辑,返回一个跟源Observable相同类型的结果,最后回调订阅者的onComplete方法
    ONERRORRETURN,
    //  onErrorResumeNext操作符跟onErrorReturn类似,只不过onErrorReturn只能在错误或异常发生时只返回一个和源Observable相同类型的结果,
    //  而onErrorResumeNext操作符是在错误或异常发生时返回一个Observable,也就是说可以返回多个和源Observable相同类型的结果

    //  onExceptionResumeNext操作符和onErrorResumeNext操作符类似,不同的地方在于onErrorResumeNext操作符是当Observable发生错误或异常时触发,
    //  而onExceptionResumeNext是当Observable发生异常时才触发。

    //  这里要普及一个概念就是,java的异常分为错误（error）和异常（exception）两种,它们都是继承于Throwable类。

    //  错误（error）一般是比较严重的系统问题,比如我们经常遇到的OutOfMemoryError、StackOverflowError等都是错误。
    //  错误一般继承于Error类,而Error类又继承于Throwable类,如果需要捕获错误,需要使用try..catch(Error e)
    //  或者try..catch(Throwable e)句式。使用try..catch(Exception e)句式无法捕获错误

    //  异常（Exception）也是继承于Throwable类,一般是根据实际处理业务抛出的异常,分为运行时异常（RuntimeException）和普通异常。
    //  普通异常直接继承于Exception类,如果方法内部没有通过try..catch句式进行处理,必须通过throws关键字把异常抛出外部进行处理（即checked异常）；
    //  而运行时异常继承于RuntimeException类,如果方法内部没有通过try..catch句式进行处理,不需要显式通过throws关键字抛出外部,
    //  如IndexOutOfBoundsException、NullPointerException、ClassCastException等都是运行时异常,
    //  当然RuntimeException也是继承于Exception类,因此是可以通过try..catch(Exception e)句式进行捕获处理的。
    ONERRORRESUMENEXT,
    //  retry操作符是当Observable发生错误或者异常时,重新尝试执行Observable的逻辑,如果经过n次重新尝试执行后仍然出现错误或者异常,
    //  则最后回调执行onError方法；当然如果源Observable没有错误或者异常出现,则按照正常流程执行。
    RETRY,
    //  retryWhen操作符类似于retry操作符,都是在源observable出现错误或者异常时,重新尝试执行源observable的逻辑,
    //  不同在于retryWhen操作符是在源Observable出现错误或者异常时,通过回调第二个Observable来判断是否重新尝试执行源Observable的逻辑,
    //  如果第二个Observable没有错误或者异常出现,则就会重新尝试执行源Observable的逻辑,否则就会直接回调执行订阅者的onError方法。
    RETRYWHEN
}