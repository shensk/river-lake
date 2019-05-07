package com.company.project.netty;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelMap {
    //静态管道map
    private static Map<String, SocketChannel> map = new ConcurrentHashMap<String, SocketChannel>();


    /**
     * 添加新管道
     *
     * @param clientId
     *            用户ID
     * @param socketChannel
     *            管道
     */
    public static void add(String clientId,SocketChannel socketChannel){
        map.put(clientId,socketChannel);
    }

    /**
     * 根据用户ID获取管道
     *
     * @param clientId
     *            用户ID
     */
    public static Channel get(String clientId){
        try {
            Channel channel = map.get(clientId);
            return channel;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 根据用户ID移除管道
     *
     * @param clientId
     *            用户ID
     * @param socketChannel
     *            管道
     */
    public static void removeSession(String clientId, SocketChannel socketChannel) {
        map.remove(clientId);
    }

    /**
     * 根据用户ID更新管道
     *
     * @param clientId
     *            用户ID
     * @param socketChannel
     *            管道
     */
    public static void saveSession(String clientId, SocketChannel socketChannel) {
        map.put(clientId,socketChannel);
    }

    /**
     * 根据管道ID移除
     *
     * @param socketChannel
     *            管道
     */
    public static void remove(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                map.remove(entry.getKey());
            }
        }
    }
}
