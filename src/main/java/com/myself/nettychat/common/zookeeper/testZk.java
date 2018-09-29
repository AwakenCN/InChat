package com.myself.nettychat.common.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 测试
 **/
public class testZk {

    /**
     * 先测试玩玩
     * @描述：XXXXXXX
     * @param args
     * @return void
     * @exception
     * @createTime：2016年5月17日
     * @author: songqinghu
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CuratorFramework client = clientTwo();
        //setListenterDateNode();
        //setListenterThreeOne(client);
        // setListenterThreeTwo(client);
        setListenterThreeThree(client);
        // getDataNode(client, "/two");
        // setDataNode(client, "/two", "sss");

    }

    /**
     *
     * @描述：创建一个zookeeper连接---连接方式一: 最简单的连接
     * @return void
     * @exception
     * @createTime：2016年5月17日
     * @author: songqinghu
     */
    private static CuratorFramework clientOne(){
        //zk 地址
        String connectString = "10.125.2.44:2181";
        // 连接时间 和重试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
        return client;
    }

    /**
     *
     * @描述：创建一个zookeeper连接---连接方式二:优选这个
     * @return void
     * @exception
     * @createTime：2016年5月17日
     * @author: songqinghu
     */
    private static CuratorFramework clientTwo(){

        //默认创建的根节点是没有做权限控制的--需要自己手动加权限???----
        ACLProvider aclProvider = new ACLProvider() {
            private List<ACL> acl ;
            @Override
            public List<ACL> getDefaultAcl() {
                if(acl ==null){
                    ArrayList<ACL> acl = ZooDefs.Ids.CREATOR_ALL_ACL;
                    acl.clear();
                    acl.add(new ACL(ZooDefs.Perms.ALL, new Id("auth", "admin:admin") ));
                    this.acl = acl;
                }
                return acl;
            }
            @Override
            public List<ACL> getAclForPath(String path) {
                return acl;
            }
        };
        String scheme = "digest";
        byte[] auth = "admin:admin".getBytes();
        int connectionTimeoutMs = 5000;
        String connectString = "127.0.0.1:2181";
        String namespace = "testnamespace";
        CuratorFramework client = CuratorFrameworkFactory.builder().aclProvider(aclProvider).
                authorization(scheme, auth).
                connectionTimeoutMs(connectionTimeoutMs).
                connectString(connectString).
                namespace(namespace).
                retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).build();
        client.start();
        return client;
    }


    /**
     *
     * @描述：第一种监听器的添加方式: 对指定的节点进行添加操作
     * 仅仅能监控指定的本节点的数据修改,删除 操作 并且只能监听一次 --->不好
     * @return void
     * @exception
     * @createTime：2016年5月18日
     * @author: songqinghu
     * @throws Exception
     */
    private static void setListenterOne(CuratorFramework client) throws Exception{
        // 注册观察者，当节点变动时触发
        byte[] data = client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("获取 two 节点 监听器 : " + event);
            }
        }).forPath("/two");
        System.out.println("two 节点数据: "+ new String(data));
    }
    /**
     *
     * @描述：第二种监听器的添加方式:
     * 也是一次性的监听操作,使用后就无法在继续监听了
     * @return void
     * @exception
     * @createTime：2016年5月18日
     * @author: songqinghu
     * @throws Exception
     */
    private static void setListenterTwo(CuratorFramework client) throws Exception{

        ExecutorService pool = Executors.newCachedThreadPool();

        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("监听器  : "+ event.toString());
            }
        };
        client.getCuratorListenable().addListener(listener,pool);
        client.getData().inBackground().forPath("/two");
        client.getData().inBackground().forPath("/two");
        client.getData().inBackground().forPath("/two");
        client.getData().inBackground().forPath("/two");
        Thread.sleep(Long.MAX_VALUE );
    }
    /**
     *
     * @描述：第三种监听器的添加方式: Cache 的三种实现 实践
     *   Path Cache：监视一个路径下1）孩子结点的创建、2）删除，3）以及结点数据的更新。
     *                  产生的事件会传递给注册的PathChildrenCacheListener。
     *  Node Cache：监视一个结点的创建、更新、删除，并将结点的数据缓存在本地。
     *  Tree Cache：Path Cache和Node Cache的“合体”，监视路径下的创建、更新、删除事件，并缓存路径下所有孩子结点的数据。
     * @return void
     * @exception
     * @createTime：2016年5月18日
     * @author: songqinghu
     * @throws Exception
     */
    //1.path Cache  连接  路径  是否获取数据
    //能监听所有的字节点 且是无限监听的模式 但是 指定目录下节点的子节点不再监听
    private static void setListenterThreeOne(CuratorFramework client) throws Exception{
        ExecutorService pool = Executors.newCachedThreadPool();
        PathChildrenCache childrenCache = new PathChildrenCache(client, "/test", true);
        PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("开始进行事件分析:-----");
                ChildData data = event.getData();
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED : "+ data.getPath() +"  数据:"+ data.getData());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED : "+ data.getPath() +"  数据:"+ data.getData());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED : "+ data.getPath() +"  数据:"+ data.getData());
                        break;
                    default:
                        break;
                }
            }
        };
        childrenCache.getListenable().addListener(childrenCacheListener);
        System.out.println("Register zk watcher successfully!");
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
    }

    //2.Node Cache  监控本节点的变化情况   连接 目录 是否压缩
    //监听本节点的变化  节点可以进行修改操作  删除节点后会再次创建(空节点)
    private static void setListenterThreeTwo(CuratorFramework client) throws Exception{
        ExecutorService pool = Executors.newCachedThreadPool();
        //设置节点的cache
        final NodeCache nodeCache = new NodeCache(client, "/test", false);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("the test node is change and result is :");
                System.out.println("path : "+nodeCache.getCurrentData().getPath());
                System.out.println("data : "+new String(nodeCache.getCurrentData().getData()));
                System.out.println("stat : "+nodeCache.getCurrentData().getStat());
            }
        });
        nodeCache.start();
    }
    //3.Tree Cache
    // 监控 指定节点和节点下的所有的节点的变化--无限监听  可以进行本节点的删除(不在创建)
    private static void setListenterThreeThree(CuratorFramework client) throws Exception{
        ExecutorService pool = Executors.newCachedThreadPool();
        //设置节点的cache
        TreeCache treeCache = new TreeCache(client, "/test");
        //设置监听器和处理过程
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if(data !=null){
                    switch (event.getType()) {
                        case NODE_ADDED:
                            System.out.println("NODE_ADDED : "+ data.getPath() +"  数据:"+ new String(data.getData()));
                            break;
                        case NODE_REMOVED:
                            System.out.println("NODE_REMOVED : "+ data.getPath() +"  数据:"+ new String(data.getData()));
                            break;
                        case NODE_UPDATED:
                            System.out.println("NODE_UPDATED : "+ data.getPath() +"  数据:"+ new String(data.getData()));
                            break;

                        default:
                            break;
                    }
                }else{
                    System.out.println( "data is null : "+ event.getType());
                }
            }
        });
        //开始监听
        treeCache.start();

    }

}
