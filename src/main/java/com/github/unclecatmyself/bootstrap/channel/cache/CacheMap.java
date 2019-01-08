package com.github.unclecatmyself.bootstrap.channel.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class CacheMap<K,V> {

    private ConcurrentHashMap<K,Node<K,V>> datas = new ConcurrentHashMap<>();

    public    boolean putData(K[] topic, V v){
        if(topic.length==1){
            Node<K, V> kvNode = buildOne(topic[0], v);
            if(kvNode!=null && kvNode.topic.equals(topic[0])){
                return true;
            }
        }
        else{
            Node<K, V> kvNode = buildOne(topic[0], null);
            for(int i=1;i<topic.length;i++){
                if(i==topic.length-1){
                    kvNode = kvNode.putNextValue(topic[i], v);
                }
                else {
                    kvNode = kvNode.putNextValue(topic[i], null);
                }
            }
        }
        return true;
    }

    public  boolean  delete(K[] ks,V v){
        if(ks.length==1){
            return datas.get(ks[0]).delValue(v);
        }
        else{
            Node<K, V> kvNode = datas.get(ks[0]);
            for(int i=1;i<ks.length&& kvNode!=null ;i++){
                kvNode =kvNode.getNext(ks[i]);
            }
            return kvNode.delValue(v);

        }
    }

    public   List<V>  getData(K[] ks){
        if(ks.length==1){
            return datas.get(ks[0]).get();
        }
        else{
            Node<K, V> node = datas.get(ks[0]);
            if(node!=null){
                List<V> all  = new ArrayList<>();
                all.addAll(node.get());
                for(int i=1;i<ks.length && node!=null;i++){
                    node= node.getNext(ks[i]);
                    if(node==null){
                        break ;
                    }
                    all.addAll(node.get());
                }
                return all;
            }
            return null;
        }
    }

    public  Node<K,V>   buildOne(K k,V v){

        Node<K, V> node = this.datas.computeIfAbsent(k, key -> {
            Node<K, V> kObjectNode = new Node<K,V>(k);
            return kObjectNode;
        });
        if(v!=null){
            node.put(v);
        }
        return node;
    }



    class Node<K,V>{

        private final K topic;


        private volatile ConcurrentHashMap<K,Node<K,V>> map =new ConcurrentHashMap<>() ;


        List<V> vs = new CopyOnWriteArrayList<>();


        public K getTopic() {return topic;}

        Node(K topic) {
            this.topic = topic;
        }

        public boolean delValue(V v){
            return vs.remove(v);
        }

        public    Node<K,V>   putNextValue(K k,V v){
            Node<K, V> kvNode = map.computeIfAbsent(k, key -> {
                Node<K, V> node = new Node<>(k);
                return node;
            });
            if(v!=null){
                kvNode.put(v);
            }
            return kvNode;
        }


        public  Node<K,V> getNext(K k){
            return  map.get(k);
        }


        public boolean  put(V v){
            return vs.add(v);
        }


        public List<V> get(){
            return  vs;
        }
    }

}
