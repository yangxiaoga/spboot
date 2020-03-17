# spboot spboot usage and redis
	
	并发编程

		并发部分 + ActiveMQ + spring 

		1、书籍
			Java编程思想
			企业架构模式
			Java并发编程实战


		2、注册中心
			Redis
			zookeeper
			SpringCloud

		3、IO
			BIO 阻塞IO
			NIO 非阻塞
		
		4、Netty

			用到的技术Future

		5、消息中间件
			RabbitMQ，RocketMQ,ActiveMQ，Kafka

		6、MQ

			场景1
			两系统间的数据通信，一个生产一个消费，两者不是一个节拍
					后系统会产生一个严重的内存的堆积，最后内存溢出
			使用消息中间件，做缓冲

			场景2
			分布式系统
			数据生产，统一放到MQ中，然后生成订阅关系
			减少系统间交织的接口的复杂性

			RPC Dubbos

		7、线程安全

			多个线程访问某一个类或对象或方法时，这个类
			始终都能表现出正确的行为

			【1】无static的方法,关键字synchronized取得的锁都是对象锁
				 线程持有该方法所属对象的锁
			【2】关键字synchronized如果修饰的是静态的方法
				 相当于在类上加锁，独占.Class类

			private AtomicInteger num 原子性的

			sychronized 可以在任意的对象及方法上加锁，而加锁的这段代码
			称为互斥区或临界区

			多个线程访问某线程的run方法时，以排队的方式进行处理
			这里排队是按照CPU分配的先后顺序而定的，不是按照线程在
			代码中创建的顺序执行的
			
		8、对象锁的同步和异步
			同步synchronized
			同步的概念就是共享，如果不是共享的资源，就没有必要进行同步

			异步asychronized
			异步的概念就是独立，相互之间不受到任何制约
			就好像我们学习http时，在页面发起的ajax请求，我们可以继续浏览或操作页面的内容
			二者之间没有任何关系

			同步的目的就是为了线程安全，对于线程安全来说，需要满足两个特性：
				原子性
				可见性

			对象是一把锁，如果对象的属性发生变化是不影响锁的
			
				
		9、业务数据的脏读
			
			如果设置值得方法耗时较长
			或者值得方法耗时短，会产生脏数据，即数据不一致的情况
			
			在我们对一个对象的方法加锁的时候，需要考虑业务的整体性
			即为setValue/getValue方法，同时加synchronized同步关键字
			保证业务的原子性，不让回出现业务的错误
			
			数据库的ACID

			A 原子性
			C 一致性
			I 隔离性
			D 永久性
			
		10、锁重入
			当一个线程得到一个对象的锁后，再次请求
			此对象时是可以再次得到该对象的锁
			
			如：执行一个队列任务，很多对象都去在
			等待一个对象正确执行完毕再去释放锁
			但是第一个对象由于异常的出现，导致业务逻辑
			没有正常执行完毕，就释放了锁，后续的对象执行的都是
			错误的逻辑

		11、volatile
			主要作用是使变量在多个线程间可见
			
			在Java中，每一个线程都会有一块工作内存区
			其中存放着所有线程【共享的主内存】中的变量值的拷贝
			当线程执行时，他在自己的工作内存区中操作这些变量
			为了存取一个共享的变量，一个线程通常会获取锁定并
			去清除这些内存区域，把这些共享变量从所有线程的共享区中正确的装入到
			他自己的所在的工作内存区，当线程解锁时
			保证该工作区中变量的值写回到共享内存中
			
			volatile强制线程到主内存（共享内存）里去
			读取变量，而不去线程工作内存区里去读取
			从而实现了多个线程间的变量可见，也就是线程安全的可见性
			
			volatile 虽然拥有多个线程间的可见性，但是
			却【不具备同步性】（也就是原子性），不会造成阻塞，
			对于读相关的操作比较适合要实现原子性建议使用atomic类的系列对象

		12、线程之间的通信
		
			wait 方法释放锁，notify方法不释放锁
			
            CountDownLatch

		13、BlockingQueue:对列，支持阻塞机制
		    阻塞地放入和得到数据，我们要实现LinkedBlockingQueue下面两个简答的方法put和take
			put没有空间，则调用此方法的线程被阻断，直到有空间再继续
			take取走队列排在首位的对象，为空则进入等待状态，直到有新的数据加入
			
		14、ThreadLocal
			线程局部变量，是一种多线程间并发访问变量的解决方案
			与其synchronized等加锁的方式不停，ThreadLocal安全不提供锁
			而使用以空间换时间的手段，为每个线程提供变量的独立副本
			保障线程安全
			
			从性能上说，ThreadLocal不具有绝对的优势，在并发不是很高的时候
			加锁的性能会更好，但作为一套与锁完全无关的线程安全解决方案
			在高并发两或者竞争激烈的场景，使用Threadlocal 可以在一定程度上
			减少锁竞争
			
		15、并发编程 - 同步类容器

			【同步类容器】：Vector，Hashtable
			Collections.synchronizedXXX等工厂方法创建同步容器
			底层机制无非是用传统的synchronized关键字对每个公用的方法
			进行同步，使每次只能有一个线程访问容器的状态，难以满足高并发的要求
			
			并发类容器
			
			JDK5.0以后，提供了并发类容器来替代同步类容器，从而改善性能
			【同步类容器】的状态都是串行化的，他们虽然实现了线程安全
			但是严重减低了并发性，在多线程环境时，严重降低了应用程序的吞吐量
			
			【并发类容器】是专门针对并发设计的，使用ConcurrentHashMap来替代
			基于散列的传统的Hashtable,而且在ConcurrentHashMap中，添加了
			一些常见符合操作的支持
			以及使用CopyOnWriteArrayList替代Vector，并发的CopyOnWriteArraySet
			以及并发的Queue，ConcurrentLinkedQueue和LinkedBlockingQueue
			前者是高性能的队列，后者是以阻塞形式的队列
			
			具体实现Queue还有很多，例如ArrayBlockingQueue
			PriorityBlockingQueue,SynchronousQueue
			
			ConcurrentHashMap
			ConcurrentSkipListMap(支持并发排序功能，弥补ConcurrentHashMap
			)
			
			ConcurrentHashMap内部使用段segment来表示这些不同的部分
			每个段其实就是一个小的Hashtable,他们有自己的【锁】
			只要多个修改操作发生在不同的段上
			他们就可以并发进行
			把一个整体分成了16个段，也就是最高支持16个线程的并发
			修改操作
			
			
			这也是在多线程场景时减小锁的力度从而降低锁竞争的一种方案
			并且代码中大多共享变量使用volatile关键字声明
			目的是第一时间获取修改的内容，性能非常好
			
			
			CopyOnWrite容器
			JDK中COW容器有两种：CopyOnWriteArrayList和CopyOnWriteArraySet
			
			通俗的理解：我们在一个容器中添加元素时，不宜直接往当前容器添加
			而是将【当前容器进行copy】,复制出一个新的容器
			然后新的容器里添加元素，添加完元素之后，再讲原容器的引用指向新的容器
			好处是可以对容器进行并发的读，而不需要加锁
			因为当前容器不会添加任何元素，CopyOnWrite容器也是一种读写分离的思想
			读和写不同的容器
			
			读多写少的场景比较适合
			
			
			Queue
			
			ConcurrentLinkedQueue 
			一个适用于高并发场景下的队列，
			通过无锁的方式，实现了高并发状态下
			的高性能，通常CocurrentLinkedQueue
			性能高于BlockingQueue
			他是一个基于链接节点的无界线程安全队列
			该队列的元素遵循先进先出的原则
			
			头是最先加入的，尾是最近加入的
			该队列不允许有null元素

			ConcurrentLinkedQueue方法
				add()offer()都是加入元素，在ConcurrentLinkedQueue
				中两者的区别不大，在阻塞队列中，offer可以设置等待时间
				如果超过该时间，则添加失败
				poll()peek()都是取头元素节点，前者会删除元素，后者不会
				
			阻塞Queue
			ArrayBlockingQueue
				基于数组的阻塞队列实现
				内部维护定长数组，缓存队列中的数据对象
				长度是需要定义的
				其内部没有实现读写分离，也就意味着，生产者
				和消费者不能完全并行，可以指定先进先出，或先进后出
				也叫有界队列
			
			LinkedBlockingQueue
				基于链表的阻塞对列
				内部维持一个数据缓冲队列-由链表构成
				之所以能够高效处理并发数据
				是因为其内部实现采用分离锁(读写分离两个锁)
				从而实现生产者和消费者的操作完全并行，
				是无界队列
			
			SynchronousQueue
				一种没有缓冲的队列，生产者产生的数据直接
				会被消费者获取并消费
				【如果只放，无消费，则会报出queue full的错误】
				一个方法放，一个方法拿
				一个线程阻塞等着拿元素
				只要往里边扔元素，则直接给到拿的线程
				因此需要有一个线程先启动等着拿元素
				
			    适合数据量不太大的场景
				
			PriorityBlockingQueue
				基于优先级的阻塞队列
				传入队列的对象必须实现Comparable接口
				内部控制线程同步的锁采用的是公平锁
				他是一个无界的队列
				
				并不是在add的时候对元素进行排序
				而是在【调用take的时候对容器进行排序】
			
			DelayQueue
				带有延迟时间的Queue,其中的元素只有当其
				指定的延迟时间到了，才能够从队列中获取该元素
				DelayQueue中元素必须实现Delayed接口
				队列没有大小限制，应用如对缓存超时的数据进行移除
				任务超时处理，空闲连接的关闭等等
			
				应用：网吧上网的时间
				      网民实现Delayed方法
					  
				下机take阻塞等待
			
			
			Future模式
				类似于Ajax异步请求

			生产者消费者模式
			
				若干生产者线程和若干消费者线程，他们之间共享
				内存缓冲区进行通信
				
				MQ
				
			多任务执行框架Executors
				为了更好的控制多线程，JDK提供了一套线程框架Executors
				java.util.concurrent包中
				Executors扮演者线程工厂的角色
				通过他可以创建特定功能的线程池
				
			    方法 
					newFixedThreadPool()
					返回一个固定数量的线程池，该方法的线程数始终不变
					当有一个任务提交时，若线程池中空闲，则立即执行
					若没有，则会暂缓在一个任务队列中,等待有空闲的线程去执行
					
					无界队列，任务量大，会有一定的风险，任务多了，队列逐渐变大，容易内存溢出
					
					
					newSingleThreadExecutor(),创建一个线程的线程池
					若空闲则执行，若没有空闲线程则暂缓在任务队列中
					
					newCachedThreadPool()
					返回一个可根据实际情况调整线程个数的线程池
					不限制最大线程数量，若有任务，则创建线程，若无任务
					则不创建线程，如果没有任务则线程在60s后自动回收
					
					线程池初始化，不放线程，SynchronousQueue,来一个任务
					创建一个线程执行
					
					线程有一个60秒的线程空闲时间
					
					newScheduledThreadPool()
					返回一个ScheduledExecutorService对象，但该线程池
					可以指定线程的数量,类似Java中的Timer,每一个线程都可以实现定时器
					DelayedWorkQueue - 延迟时间的移除
					
					Spring家族，你都了解什么？
					
					JPA - Spring Data - JDBCTEMPLATE
					Spring MVC
					batch
					security - shiro
					SpringBoot
					SpringCloud - 分布式SOA服务
					spring Cache - redis ,mogodb
					JMS - activemq ,rabbitmq
					Spring mail
					
					以上创建线程池的方法，最终都是使用:
					return new ThreadPoolExecutor()
					只是传递的参数不同,而创建不同类型的线程池
					
					public ThreadPoolExecutor(
						int corePoolSize,
						int maximumPoolSize,
						long keepAliveTime,
						BlockingQueue<Runnable> workQueue,
						ThreaFactory threadFactory,
						RejectedExecutionHandler handler //拒绝的策略
					)
					
					ExecutorService pool = Ececutors.newSingleThreadExecutor();
					pool.execute();
					
					
					
					自定义线程池
					使用有界队列时，基于ThreadPoolExecutor，若有新的任务需要执行
					如果线程池实际线程数小于corePoolSize,则优先创建线程，若大于
					corePoolSize,则会将任务加入队列，若队列已满，则在总线程数
					不大于maximumPoolSize的前提下，创建新的线程，若线程数大于
					maximumPoolSize,则执行拒绝策略，或其他自定义方式
					
					无界的任务队列时，
					LinkedBlockingQueue,与有界队列相比，除非系统资源耗尽
					否则无界的任务队列，不存在入队失败的情况，当有新的任务
					到来，系统的线程数小于corePoolSize,则新建线程执行任务
					当达到corePoolSize后，就不会继续增加，若后续仍有新的
					任务加入，而没有空闲的线程资源，则任务直接进入队列等待
					若任务创建和处理的速度差异很大，无界队列会保持快速增长
					直到耗尽系统内存
					
					JDK决绝策略
					AbortPolicy:直接抛出异常，组织系统正常工作
					CallerRunPolicy:只要线程池未关闭，该策略直接在调用者线程中
					运行当前被丢弃的任务
					DiscardOldestPolicy:丢弃最老的一个请求，尝试再次提交当前任务
					DiscardPolicy:丢弃无法处理的任务，不予任何处理
					
					如果需要自定义拒绝策略，可以实现RejectedExecutionHandler接口
					
					
					Executors JDK多任务的执行框架