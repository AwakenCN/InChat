package com.myself.nettychat.common.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc zookeeper 监听
 **/
public interface ZkStateListener {

    default void connectedEvent(CuratorFramework curator, ConnectionState state) {
    }

    default void ReconnectedEvent(CuratorFramework curator, ConnectionState state) {
    }

    default void lostEvent(CuratorFramework curator, ConnectionState state) {
    }

}
