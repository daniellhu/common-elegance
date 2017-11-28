# common-elegance
优雅编码的一些common实现，为了以后开发依赖使用，主要是为了开发规范

## 包含如下：

### 一个非常全的utils类库
hutool

```
<dependency>
	<groupId>com.xiaoleilu</groupId>
	<artifactId>hutool-all</artifactId>
	<version>${hutool-version}</version>
</dependency>
```
[说明文档](http://hutool.mydoc.io/) 

### 前后端接口规范
1. 所有接口都必须返回ResultBean，就是说一开始就考虑好成功和失败的场景。
2. ResultBean只允许出现在controller，不允许到处传。
3. 不要出现map，json等这种复杂对象做为输入和输出。
4. 对外接口可以考虑使用细分错误码，对内接口使用异常信息即可（方便编码）。

其中ResultBean分为PageResultResponse和RestResultResponse

### 自动生成Rest接口
继承BaseController即可

### 自动生成entity的增删改查的方法
继承BaseService即可，自动注入常用的createBy、createDate等


### 用户信息保存到线程中
原来都需要在前后端接口中增加useid等和业务无关的字段，影响api简洁，现在通过  
filter将用户信息从header头中取出来放到threadlocal中，在业务中直接如下可以取到：

```
UserLocal.getLocalUser();
```

### 将用户信息自动埋到日志中
以前在log中查问题总是忘记埋用户信息，现在在common中将用户信息如下方式埋到日志的上下文中

```
	// 将用户信息放发哦slf4j中，方便日志打印
	MDC.put(userKey, optUserId);
```

 那么在logback.xml中，即可在layout中通过声明“%X{user}”来打印此信息。


