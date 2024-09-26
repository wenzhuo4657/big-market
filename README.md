# 项目知识点

## 零散

### redisson： redis分布式客户端

#### redisson分布式集合：RMap

参考：https://huingsn.github.io/2019/01/11/Redisson%E8%AF%A6%E8%A7%A3/

RMap 本身并没有在本地缓存数据，每次读写操作都会发送到 Redis 服务器去执行相应的命令，保证了多线程环境中保持一致性。



但是在特定的场景下，映射缓存（Map）上的高度频繁的读取操作，导致网络通信都被视为瓶颈，此时可以使用Redisson提供的带有本地缓存功能的映射。



### java集合

`List<Integer> list=new ArrayList<>(RateRange.intValue());`

该参数的传递，是为了减少即将向list存入大量元素所带来的扩容操作的损耗，提前指定其初识容量，而避免扩容，这一点从本质上需要理解ArrayList本质上其元素存储在数组`transient Object[] elementData;`



add方法中是这样写的。

```

    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }
```

可以看到，方法内部除了添加元素，还有扩容方法。

### java访问类型

在写代码时看到一个protected修饰符，突然发现自己通常只使用私有和公有两种访问修饰类型，故此去查询了一下。

<img src="C:\Users\14783\AppData\Roaming\Typora\typora-user-images\image-20240926120935494.png" alt="image-20240926120935494"  />

需要注意的时private字段在其子类中也不能直接访问，只能通过public方法、反射等其他迂回的方式进行间接的访问。



# 报错

## mapper返回值类型不受泛型约束

报错很多，但实际上仅仅java泛型的一个在应用中的错误。

java泛型是一个伪泛型，这个伪是指其在实际运行中对java对象并没有约束，仅仅在编译中生效。

由于反射是通过class对象操作，而class对象的生命周期是从加载阶段开始的，因此我们可以通过反射查看java泛型是否生效。

![image-20240924171005751](C:\Users\14783\AppData\Roaming\Typora\typora-user-images\image-20240924171005751.png)

此处实际上提醒我，java泛型在编译阶段的检查范围，我的错误是这样的，

![image-20240924171105413](C:\Users\14783\AppData\Roaming\Typora\typora-user-images\image-20240924171105413.png)

该方法的实际内容由xml定义，使用泛型约束，但是在运行中总是并没有生成对应的泛型对象。

修正过后的xml

![image-20240924171215203](C:\Users\14783\AppData\Roaming\Typora\typora-user-images\image-20240924171215203.png)

这里需要重新明确resultMap的定义，不仅仅是属性的映射，而且还管理sql语句返回的数据将要转向哪一个java类。

