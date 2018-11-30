package com.myself.unclecatmyself.bootstrap.channel;

import com.myself.unclecatmyself.bootstrap.BaseApi;
import com.myself.unclecatmyself.bootstrap.ChannelService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.myself.unclecatmyself.bootstrap.bean.MqttChannel;
import com.myself.unclecatmyself.bootstrap.bean.RetainMessage;
import com.myself.unclecatmyself.bootstrap.channel.cache.CacheMap;
import com.myself.unclecatmyself.bootstrap.scan.ScanRunnable;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 抽象类
 **/
@Slf4j
public abstract class AbstractChannelService extends PublishApiSevice implements ChannelService , BaseApi {

    protected AttributeKey<Boolean> _login = AttributeKey.valueOf("login");

    protected   AttributeKey<String> _deviceId = AttributeKey.valueOf("deviceId");

    protected  static char SPLITOR = '/';

    protected ExecutorService executorService =Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);


    protected static CacheMap<String, MqttChannel> cacheMap= new CacheMap<>();


    protected static ConcurrentHashMap<String ,MqttChannel> mqttChannels = new ConcurrentHashMap<>(); // deviceId - mqChannel 登录


    protected  static  ConcurrentHashMap<String,ConcurrentLinkedQueue<RetainMessage>> retain = new ConcurrentHashMap<>(); // topic - 保留消息



    protected  static  Cache<String, Collection<MqttChannel>> mqttChannelCache = CacheBuilder.newBuilder().maximumSize(100).build();

    public AbstractChannelService(ScanRunnable scanRunnable) {
        super(scanRunnable);
    }


    protected  Collection<MqttChannel> getChannels(String topic,TopicFilter topicFilter){
        try {
            return  mqttChannelCache.get(topic, () -> topicFilter.filter(topic));
        } catch (Exception e) {
            log.info(String.format("guava cache key topic【%s】 channel   value== null ",topic));
        }
        return null;
    }


    @FunctionalInterface
    interface TopicFilter{
        Collection<MqttChannel> filter(String topic);
    }

    protected boolean deleteChannel(String topic,MqttChannel mqttChannel){
        return  Optional.ofNullable(topic).map(s -> {
            mqttChannelCache.invalidate(s);
            return  cacheMap.delete(getTopic(s),mqttChannel);
        }).orElse(false);
    }

    protected boolean addChannel(String topic,MqttChannel mqttChannel)
    {
        return  Optional.ofNullable(topic).map(s -> {
            mqttChannelCache.invalidate(s);
            return cacheMap.putData(getTopic(s),mqttChannel);
        }).orElse(false);
    }

    /**
     * 获取channel
     */
    public MqttChannel getMqttChannel(String deviceId){
        return Optional.ofNullable(deviceId).map(s -> mqttChannels.get(s))
                .orElse(null);

    }

    /**
     * 获取channelId
     */
    public String  getDeviceId(Channel channel){
        return  Optional.ofNullable(channel).map( channel1->channel1.attr(_deviceId).get())
                .orElse(null);
    }



    protected String[] getTopic(String topic)  {
        return Optional.ofNullable(topic).map(s ->
                StringUtils.split(topic,SPLITOR)
        ).orElse(null);
    }

}
