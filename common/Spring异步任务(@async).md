# 简介
```text
    从Spring 3开始提供了@Async注解，该注解可以呗标注在方法上，以便异步地调用该方法。调用者将在调用时立即返回，方法的实际执行将提交给
Spring TaskExecutor的任务中，由指定的线程池的线程执行。(推荐使用自定义的线程池，否者可能回造成OOM异常)
    同步: 同步就是整个处理过程顺序执行，当各个过程都执行完毕，并返回结果。
    异步: 异步调用则是只是发送调用的指令，调用者无序等待被调用的方法完全执行完毕，主线程不会阻塞继续执行下去
```
# Spring 已经实现的线程池
```text
    1.SimpleAsyncTaskExecutor: 不重用线程，默认每次调用都会创建新的线程。(Spring Async默认使用的线程池)
    2.SyncTaskExecutor: 没有实现异步，仍然是同步线程。
    3.ConcurrentTaskExecutor: Executor的适配类，不推荐使用。
    4.SimpleThreadPoolTaskExecutor: 是Quartz的SimpleThreadPool类。线程同时被Quartz和非Quartz使用。
    5.ThreadPoolTaskExecutor: 推荐使用。(Spring对java.util.concurrent.ThreadPoolExecutor的包装)
```
# 异步方法
```text
    1. 直接调用，无返回值
    2. 带参数的异步调用，异步方法可以传参
    3. 存在返回值，常调用返回Future
```
## 开启方式
```text
    @EnableAsync: 加在配置类上
    
    @Async: 加在类上(这个类的所有方法都是异步方法), 或者加在方法上
```
## 使用方式
### 无返回值方法调用
```text
    @Async
    public void voidMethod(String param){
        log.info("无返回值调用,{}",param);
    }
```
### 有返回值方法调用(Future)
```text
    @Async
    public Future<String> futureMethod(String param){
        log.info("有返回值调用");
        Future<String> future = new AsyncResult<String>(param);
        return future;
    }
```
### 有返回值方法调用(CompletableFuture)
#### 简介
```text
    ComletableFuture并不使用@Async注解，可以达到调用系统线程池处理业务的功能。
    JDK1.5新增的Future接口，用于接收异步线程执行返回的结果。对结果的获取只能通过阻塞方式或者轮询的方式，阻塞违背了异步的初衷，轮询浪费CPU资源，
又不能及时获得结果。
    JDK1.8提供了ComletableFuture是更强大的Future的扩展功能，可以简化异步编程的复杂性，并且提供函数式编程能力，可以通过回调的方式处理计算结果，
也提供了转换和组合CompletableFuture的方法。
    ComletableFuture可以表示一个明确完成的Future，也有可能表示一个完成阶段(CompletionStage)，支持在计算完成以后触发一些函数或执行某些动作。
例如: ComletableFuture.supplyAsync(...).thenApplyAsync(...);
    public class CompletableFuture<T> implements Future<T>, CompletionStage<T>
```
# @Async应用自定义线程池
## 默认线程池的弊端
```text
    **(Executors创建)
    1.newFixedThreadPool和newSingleThreadExecutor: 其使用的任务队列长度最大是Integer.MAX_VALUE，任务堆积多了以后会造成OOM。
    2.newCachedThreadPool和newScheduledThreadPool: 最大线程数是Integer.MAX_VALUE，创建线程会分配栈空间，过多创建线程消耗大量内存，
造成OOM。
    @Async默认配置使用的是SimpleAsyncTaskExecutor，该线程池默认是来一个任务创建一个线程，若不断地创建线程就会造成OOM。
```
## 配置自定义线程池
```text
    1.重新实现接口AsyncConfigurer
    2.继承AsyncConfigurerSupport
    3.配置自定义地TaskExecutor替代内置的任务执行器
```
```java
@Configuration
@EnableAsync
@ConfigurationProperties(prefix = "spring.async")
@Slf4j
public class SpringAsyncConfiguration implements AsyncConfigurer {

    /**
     * 核心线程数
     */
    @Value("${core-pool-size:2}")
    private int corePoolSize;

    /**
     * 最大线程数
     */
    @Value("${max-pool-size:5}")
    private int maxPoolSize;

    /**
     * 非核心线程，活跃线程存活时间
     */
    @Value("${keep-alive-seconds:60}")
    private int keepAliveSeconds;

    /**
     * 任务队列长度容量
     */
    @Value("${queue-capacity:10}")
    private int queueCapacity;

    //@Bean(name = "") ---> @Async(value = "") 执行线程池配置
    //@ConditionalOnMissingBean
//    @Bean("defaultAsyncThreadPool")
    private ThreadPoolExecutor executor(){
        ThreadFactory threadFactory = ThreadFactoryBuilder.create().setNamePrefix("async-thread-id-").build();

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(this.corePoolSize);
        taskExecutor.setMaxPoolSize(this.maxPoolSize);
        taskExecutor.setKeepAliveSeconds(this.keepAliveSeconds);
        taskExecutor.setQueueCapacity(this.queueCapacity);
        //拒绝策略
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        taskExecutor.setThreadFactory(threadFactory);
        return taskExecutor.getThreadPoolExecutor();
    }

    /**
     * 获取异步任务线程池
     * @return Executor
     */
    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }

    /**
     * 异步线程报错处理
     * @return AsyncUncaughtExceptionHandler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            ex.printStackTrace();
            log.error("异步线程出错, 方法: {}, 参数: {}, 原因: {}", method, params, ex.getMessage());
        };
    }
}
```
### 配置自定义的TaskExecutor
```text
    由于AsyncConfigurer的默认线程池在源码中为空，Spring通过beanFactory.getBean(TaskExecutor.class)先查看是否有线程池，未配置时通过
beanFactory.getBean(DEFAULT_TASK_EXECUTOR_BEAN_NAME, Executor.class)获取线程池，我们只需要定义名称为TaskExecutor
(类型为TaskExecutor.class)的bean线程池即可。
```
```text
Executor.class:ThreadPoolExecutorAdapter->ThreadPoolExecutor->AbstractExecutorService->ExecutorService->Executor
（这样的模式，最终底层为Executor.class，在替换默认的线程池时，需设置默认的线程池名称为TaskExecutor）

TaskExecutor.class:ThreadPoolTaskExecutor->SchedulingTaskExecutor->AsyncTaskExecutor->TaskExecutor
（这样的模式，最终底层为TaskExecutor.class，在替换默认的线程池时，可不指定线程池名称。）
```
# @Async源码
## @EnableAsync
```text
    通过此注解开启Spring异步任务配置，@Import(AsyncConfigurationSelector.class)引入相关配置。
    
    注意：EnableAysnc的proxyTargetClass不能随便开启，开启后代理都会变成子级代理，将会影响所有的Spring的bean。详情看注释。
```
## @Import(AsyncConfigurationSelector.class)
```java
public class AsyncConfigurationSelector extends AdviceModeImportSelector<EnableAsync> {

	private static final String ASYNC_EXECUTION_ASPECT_CONFIGURATION_CLASS_NAME =
			"org.springframework.scheduling.aspectj.AspectJAsyncConfiguration";


	/**
	 * Returns {@link ProxyAsyncConfiguration} or {@code AspectJAsyncConfiguration}
	 * for {@code PROXY} and {@code ASPECTJ} values of {@link EnableAsync#mode()},
	 * respectively.
	 */
	@Override
	@Nullable
	public String[] selectImports(AdviceMode adviceMode) {
		switch (adviceMode) {
			case PROXY:
				return new String[] {ProxyAsyncConfiguration.class.getName()};
			case ASPECTJ:
				return new String[] {ASYNC_EXECUTION_ASPECT_CONFIGURATION_CLASS_NAME};
			default:
				return null;
		}
	}

}
```
```text
    通过Import注解，引入相关的bean。EnableAsync默认的模式是AdviceMode.PROXY，主要看这个ProxyAsyncConfiguration
```
## ProxyAsyncConfiguration
```java
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ProxyAsyncConfiguration extends AbstractAsyncConfiguration {

	@Bean(name = TaskManagementConfigUtils.ASYNC_ANNOTATION_PROCESSOR_BEAN_NAME)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public AsyncAnnotationBeanPostProcessor asyncAdvisor() {
		Assert.notNull(this.enableAsync, "@EnableAsync annotation metadata was not injected");
		AsyncAnnotationBeanPostProcessor bpp = new AsyncAnnotationBeanPostProcessor();
		bpp.configure(this.executor, this.exceptionHandler);
		Class<? extends Annotation> customAsyncAnnotation = this.enableAsync.getClass("annotation");
		if (customAsyncAnnotation != AnnotationUtils.getDefaultValue(EnableAsync.class, "annotation")) {
			bpp.setAsyncAnnotationType(customAsyncAnnotation);
		}
		bpp.setProxyTargetClass(this.enableAsync.getBoolean("proxyTargetClass"));
		bpp.setOrder(this.enableAsync.<Integer>getNumber("order"));
		return bpp;
	}

}
```
```text
ProxyAsyncConfiguration配置类构成:

ProxyAsyncConfiguration (org.springframework.scheduling.annotation)
    AbstractAsyncConfiguration (org.springframework.scheduling.annotation)
        Object (java.lang)
        ImportAware (org.springframework.context.annotation)
            Aware (org.springframework.beans.factory)
```
```text
扩展：

ProxyAsyncConfiguration实现了ImportAware接口，再bean初始化之后将会调用setImportMetadata接口方法，将EnableAsync注解相关的属性获取。
(ConfigurationClassPostProcessor.ImportAwareBeanPostProcessor后置处理器执行了这个方法)
```
```text
AbstractAsyncConfiguration：会将继承AsyncConfigurerSupport和实现AsyncConfigurer的配置类加载相关配置到配置类中。
                            读取了executor(线程池)和exceptionHandler(线程异常处理)。

    /**
	 * Collect any {@link AsyncConfigurer} beans through autowiring.
	 */
	@Autowired(required = false)
	void setConfigurers(Collection<AsyncConfigurer> configurers) {
		if (CollectionUtils.isEmpty(configurers)) {
			return;
		}
		if (configurers.size() > 1) {
			throw new IllegalStateException("Only one AsyncConfigurer may exist");
		}
		AsyncConfigurer configurer = configurers.iterator().next();
		this.executor = configurer::getAsyncExecutor;
		this.exceptionHandler = configurer::getAsyncUncaughtExceptionHandler;
	}
```
```text
ProxyAsyncConfiguration：超类处理完成以后，再回到此类。此类通过@Bean向Spring容器中注册了一个名为
                        TaskManagementConfigUtils.ASYNC_ANNOTATION_PROCESSOR_BEAN_NAME的bean后置处理器。

    @Bean(name = TaskManagementConfigUtils.ASYNC_ANNOTATION_PROCESSOR_BEAN_NAME)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public AsyncAnnotationBeanPostProcessor asyncAdvisor() {
		Assert.notNull(this.enableAsync, "@EnableAsync annotation metadata was not injected");
		AsyncAnnotationBeanPostProcessor bpp = new AsyncAnnotationBeanPostProcessor();
		//配置线程池，配置线程异常处理
		bpp.configure(this.executor, this.exceptionHandler);
		//设置自定义异步注解
		Class<? extends Annotation> customAsyncAnnotation = this.enableAsync.getClass("annotation");
		if (customAsyncAnnotation != AnnotationUtils.getDefaultValue(EnableAsync.class, "annotation")) {
			bpp.setAsyncAnnotationType(customAsyncAnnotation);
		}
		//代理模式
		bpp.setProxyTargetClass(this.enableAsync.getBoolean("proxyTargetClass"));
		//权重
		bpp.setOrder(this.enableAsync.<Integer>getNumber("order"));
		return bpp;
	}
```
## AsyncAnnotationBeanPostProcessor
```java
@SuppressWarnings("serial")
public class AsyncAnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {
    
	public static final String DEFAULT_TASK_EXECUTOR_BEAN_NAME =
			AnnotationAsyncExecutionInterceptor.DEFAULT_TASK_EXECUTOR_BEAN_NAME;
    
	protected final Log logger = LogFactory.getLog(getClass());

	@Nullable
	private Supplier<Executor> executor;

	@Nullable
	private Supplier<AsyncUncaughtExceptionHandler> exceptionHandler;

	@Nullable
	private Class<? extends Annotation> asyncAnnotationType;
    
	public AsyncAnnotationBeanPostProcessor() {
		setBeforeExistingAdvisors(true);
	}
    
	public void configure(
			@Nullable Supplier<Executor> executor, @Nullable Supplier<AsyncUncaughtExceptionHandler> exceptionHandler) {

		this.executor = executor;
		this.exceptionHandler = exceptionHandler;
	}
    
	public void setExecutor(Executor executor) {
		this.executor = SingletonSupplier.of(executor);
	}

	public void setExceptionHandler(AsyncUncaughtExceptionHandler exceptionHandler) {
		this.exceptionHandler = SingletonSupplier.of(exceptionHandler);
	}
    
	public void setAsyncAnnotationType(Class<? extends Annotation> asyncAnnotationType) {
		Assert.notNull(asyncAnnotationType, "'asyncAnnotationType' must not be null");
		this.asyncAnnotationType = asyncAnnotationType;
	}


	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		super.setBeanFactory(beanFactory);

		AsyncAnnotationAdvisor advisor = new AsyncAnnotationAdvisor(this.executor, this.exceptionHandler);
		if (this.asyncAnnotationType != null) {
			advisor.setAsyncAnnotationType(this.asyncAnnotationType);
		}
		advisor.setBeanFactory(beanFactory);
		this.advisor = advisor;
	}

}
```
```text
AsyncAnnotationBeanPostProcessor类构成：

AsyncAnnotationBeanPostProcessor (org.springframework.scheduling.annotation)
    AbstractBeanFactoryAwareAdvisingPostProcessor (org.springframework.aop.framework.autoproxy)
        AbstractAdvisingBeanPostProcessor (org.springframework.aop.framework)
            ProxyProcessorSupport (org.springframework.aop.framework)
                ProxyConfig (org.springframework.aop.framework)
                    Object (java.lang)
                    Serializable (java.io)
                Ordered (org.springframework.core)
                BeanClassLoaderAware (org.springframework.beans.factory)
                    Aware (org.springframework.beans.factory)
                AopInfrastructureBean (org.springframework.aop.framework)
            BeanPostProcessor (org.springframework.beans.factory.config)
        BeanFactoryAware (org.springframework.beans.factory)
            Aware (org.springframework.beans.factory)
```
```text
AsyncAnnotationBeanPostProcessor实现BeanFactoryAware通过此方法注入bean工厂，并且配置好通知器。

    @Override
	public void setBeanFactory(BeanFactory beanFactory) {
		super.setBeanFactory(beanFactory);

		AsyncAnnotationAdvisor advisor = new AsyncAnnotationAdvisor(this.executor, this.exceptionHandler);
		if (this.asyncAnnotationType != null) {
			advisor.setAsyncAnnotationType(this.asyncAnnotationType);
		}
		advisor.setBeanFactory(beanFactory);
		this.advisor = advisor;
	}
```
```java
@SuppressWarnings("serial")
public class AsyncAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

	private Advice advice;

	private Pointcut pointcut;
    
	@SuppressWarnings("unchecked")
	public AsyncAnnotationAdvisor(
			@Nullable Supplier<Executor> executor, @Nullable Supplier<AsyncUncaughtExceptionHandler> exceptionHandler) {

		Set<Class<? extends Annotation>> asyncAnnotationTypes = new LinkedHashSet<>(2);
		asyncAnnotationTypes.add(Async.class);
		try {
			asyncAnnotationTypes.add((Class<? extends Annotation>)
					ClassUtils.forName("javax.ejb.Asynchronous", AsyncAnnotationAdvisor.class.getClassLoader()));
		}
		catch (ClassNotFoundException ex) {
			// If EJB 3.1 API not present, simply ignore.
		}
        //构建拦截器
		this.advice = buildAdvice(executor, exceptionHandler);
        //构建切入点
		this.pointcut = buildPointcut(asyncAnnotationTypes);
	}
    
	protected Advice buildAdvice(
			@Nullable Supplier<Executor> executor, @Nullable Supplier<AsyncUncaughtExceptionHandler> exceptionHandler) {

		AnnotationAsyncExecutionInterceptor interceptor = new AnnotationAsyncExecutionInterceptor(null);
		interceptor.configure(executor, exceptionHandler);
		return interceptor;
	}

	protected Pointcut buildPointcut(Set<Class<? extends Annotation>> asyncAnnotationTypes) {
		ComposablePointcut result = null;
		for (Class<? extends Annotation> asyncAnnotationType : asyncAnnotationTypes) {
			Pointcut cpc = new AnnotationMatchingPointcut(asyncAnnotationType, true);
			Pointcut mpc = new AnnotationMatchingPointcut(null, asyncAnnotationType, true);
			if (result == null) {
				result = new ComposablePointcut(cpc);
			}
			else {
				result.union(cpc);
			}
			result = result.union(mpc);
		}
		return (result != null ? result : Pointcut.TRUE);
	}

}
```
```text
AsyncAnnotationAdvisor: 通过此类构建方法拦截器(AnnotationAsyncExecutionInterceptor iml MethodInterceptor)，构建切入点(Pointcut)
```
### AbstractAdvisingBeanPostProcessor
```java
@SuppressWarnings("serial")
public abstract class AbstractAdvisingBeanPostProcessor extends ProxyProcessorSupport implements BeanPostProcessor {

	@Nullable
	protected Advisor advisor;

	protected boolean beforeExistingAdvisors = false;

	private final Map<Class<?>, Boolean> eligibleBeans = new ConcurrentHashMap<>(256);

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		if (this.advisor == null || bean instanceof AopInfrastructureBean) {
			// Ignore AOP infrastructure such as scoped proxies.
			return bean;
		}
        //如果这个bean是一个代理类，就生成代理类链路
		if (bean instanceof Advised) {
			Advised advised = (Advised) bean;
			if (!advised.isFrozen() && isEligible(AopUtils.getTargetClass(bean))) {
				// 设置代理类链路前后执行顺序，beforeExistingAdvisors  这个被AsyncAnnotationBeanPostProcessor的构造器设置成了true
				if (this.beforeExistingAdvisors) {
					advised.addAdvisor(0, this.advisor);
				}
				else {
					advised.addAdvisor(this.advisor);
				}
				return bean;
			}
		}

        //是否需要生成代理类，由代理工厂去生成代理类
		if (isEligible(bean, beanName)) {
			ProxyFactory proxyFactory = prepareProxyFactory(bean, beanName);
			if (!proxyFactory.isProxyTargetClass()) {
                //使用JDK接口代理
				evaluateProxyInterfaces(bean.getClass(), proxyFactory);
			}
			proxyFactory.addAdvisor(this.advisor);
			customizeProxyFactory(proxyFactory);
            //创建代理类
			return proxyFactory.getProxy(getProxyClassLoader());
		}

		// No proxy needed.
		return bean;
	}
    
	protected boolean isEligible(Object bean, String beanName) {
		return isEligible(bean.getClass());
	}
    
	protected boolean isEligible(Class<?> targetClass) {
		Boolean eligible = this.eligibleBeans.get(targetClass);
		if (eligible != null) {
			return eligible;
		}
		if (this.advisor == null) {
			return false;
		}
		eligible = AopUtils.canApply(this.advisor, targetClass);
		this.eligibleBeans.put(targetClass, eligible);
		return eligible;
	}

}
```
```text
AsyncAnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor

此类实现了postProcessor的bean前后置方法。通过postProcessAfterInitialization去完成代理类的生成。
```
```text
判断这个类是否需要生成代理类：

    protected boolean isEligible(Class<?> targetClass) {
		Boolean eligible = this.eligibleBeans.get(targetClass);
		if (eligible != null) {
			return eligible;
		}
		if (this.advisor == null) {
			return false;
		}
		eligible = AopUtils.canApply(this.advisor, targetClass);
		this.eligibleBeans.put(targetClass, eligible);
		return eligible;
	}
	
	public static boolean canApply(Advisor advisor, Class<?> targetClass, boolean hasIntroductions) {
		if (advisor instanceof IntroductionAdvisor) {
			return ((IntroductionAdvisor) advisor).getClassFilter().matches(targetClass);
		}
		//AsyncAnnotationAdvisor是这个类型
		else if (advisor instanceof PointcutAdvisor) {
			PointcutAdvisor pca = (PointcutAdvisor) advisor;
			return canApply(pca.getPointcut(), targetClass, hasIntroductions);
		}
		else {
			// It doesn't have a pointcut so we assume it applies.
			return true;
		}
	}
	
	public static boolean canApply(Pointcut pc, Class<?> targetClass, boolean hasIntroductions) {
		Assert.notNull(pc, "Pointcut must not be null");
		if (!pc.getClassFilter().matches(targetClass)) {
			return false;
		}

		MethodMatcher methodMatcher = pc.getMethodMatcher();
		if (methodMatcher == MethodMatcher.TRUE) {
			// No need to iterate the methods if we're matching any method anyway...
			return true;
		}

		IntroductionAwareMethodMatcher introductionAwareMethodMatcher = null;
		if (methodMatcher instanceof IntroductionAwareMethodMatcher) {
			introductionAwareMethodMatcher = (IntroductionAwareMethodMatcher) methodMatcher;
		}

		Set<Class<?>> classes = new LinkedHashSet<>();
		if (!Proxy.isProxyClass(targetClass)) {
			classes.add(ClassUtils.getUserClass(targetClass));
		}
		classes.addAll(ClassUtils.getAllInterfacesForClassAsSet(targetClass));

		for (Class<?> clazz : classes) {
			Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
			//循环这个类的方法，找到含有异步注解匹配的方法
			for (Method method : methods) {
				if (introductionAwareMethodMatcher != null ?
						introductionAwareMethodMatcher.matches(method, targetClass, hasIntroductions) :
						methodMatcher.matches(method, targetClass)) {
					return true;
				}
			}
		}

		return false;
	}
```
```text
Async执行过程参考AOP执行过程
```