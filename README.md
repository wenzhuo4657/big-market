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