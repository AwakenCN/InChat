package com.myself.nettychat.utils;

import io.netty.channel.Channel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 19:30 2018\9\20 0020
 */
public class Const {

    //帧头
    public static final String HEAD = "gz";
    //帧尾
    public static final String TAIL = "xr";
    //控制锁状态
    public static final String CONTROL_TYPE = "s";
    //24锁 全部关闭状态
    public static final String LOCKS = "nnnnnnnnnnnnnnnnnnnnnnnn";
    //开锁标识
    public static final char OPEN = 'y';
    //客户端执行结果
    public static final String RESULT_TYPE = "j";
    //客户端执行测试结果
    public static final String RESULT_TEXT = "t";
    //服务器执行结果
    public static final String SUCCESS = "yyyyyyyyyyyyyyyyyyyyyyyy";
    public static final String ERROR = "nnnnnnnnnnnnnnnnnnnnnnnn";

    private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();

    /**
     * Netty连接池添加连接
     * @param clientId 连接ID
     * @param channel 连接实例
     */
    public static void add(String clientId,Channel channel){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("add ChannelId: " + clientId + " " + formatter.format(new Date()));
        map.put(clientId,channel);
    }

    /**
     * 判断是否存在该连接ID实例
     * @param channelID 连接Id
     * @return
     */
    public static boolean hasChannelID(String channelID){
        boolean state = map.containsKey(channelID);
        return state;
    }

    /**
     * 根据连接ID 获取连接实例
     * @param clientId 链接Id
     * @return
     */
    public static Channel get(String clientId){
        return map.get(clientId);
    }

    /**
     * 根据链接实例移除链接实例
     * @param channel 链接实例
     */
    public static void remove(Channel channel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==channel){
                map.remove(entry.getKey());
            }
        }
    }

    /**
     * 获取链接数组个数
     * @return
     */
    public static int getSzie(){
        return map.size();
    }

    /**
     * 获取链接数组Id
     * @return
     */
    public static Set<String> getIdList(){
        return map.keySet();
    }

    /**
     * 获取连接池数据信息
     */
    public static void mapInfo(){
        System.out.println("channel size : " + map.size());
        for (Map.Entry entry:map.entrySet()){
            System.out.println("clientId: " + entry.getKey());
        }
    }

    /**
     * 链接实例是否存在链接池中
     * @param channel 链接实例
     * @return 链接Id
     */
    public static String isChannel(Channel channel){
        String clientId = null;
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue() == channel){
                clientId = (String) entry.getKey();
            }
        }
        return clientId;
    }

    /**
     * 更新链接实例Id
     * @param clientId 原始ID
     * @param newID 新的连接ID
     */
    public static void ChangeClientId(String clientId,String newID){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getKey()==clientId){
                Channel channel = (Channel) entry.getValue();
                map.remove(entry.getKey());
                map.put(newID,channel);
            }
        }
    }

    public static void changeChannel(String channelID, Channel channel) {
        map.remove(channelID);
        map.put(channelID,channel);
    }

    public enum StateEnum{
        SUCCESS(1,"ok"),
        FALID(2,"no");
        private int state;

        private String stateInfo;

        StateEnum(int state, String stateInfo) {
            this.state = state;
            this.stateInfo = stateInfo;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateInfo() {
            return stateInfo;
        }

        public void setStateInfo(String stateInfo) {
            this.stateInfo = stateInfo;
        }

        public static StateEnum stateOf(int index){
            for (StateEnum item:values()){
                if (item.getState() == index){
                    return item;
                }
            }
            return null;
        }
    }


}
