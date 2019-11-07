# rush

使用 **注解+Spring+SpringAOP** 实现可以设置重试次数并且带超时时间设置

该项目地址GitHub地址：[传送门](https://github.com/Stainlesswang/rush)

使用 JDK的代理对现有的Service增加不影响原逻辑的一些缓存操作，有缓存直接返货缓存，没有缓存执行原来的方法
然后刷新缓存，未命中缓存的时候要防止缓存穿透，获取所得采去进行更新Cache，其余的等待重入或者返回一个降级缓存
