package com.company.project.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
@Qualifier("serverHandler")
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("client msg:"+msg);
        String clientIdToLong= ctx.channel().id().asLongText();
        log.info("client long id:"+clientIdToLong);
        String clientIdToShort= ctx.channel().id().asShortText();
        log.info("client short id:"+clientIdToShort);
        if(msg.indexOf("bye")!=-1){
            //close
            ctx.channel().close();
        }else{
            //send to client
//            ctx.channel().writeAndFlush("Yoru msg is:"+msg);
            ServerHandler.channelGroup.writeAndFlush("group message :" + msg); //群发
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("加入管道 handlerAdded ------ " + incoming.remoteAddress() + " " + incoming.id());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("激活管道 channelActive ------ " + incoming.remoteAddress()+" 在线 " + incoming.id());

        ctx.channel().writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!");

        //添加到channelGroup通道组
        ServerHandler.channelGroup.add(incoming);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel incoming = ctx.channel();
        log.info("管道通讯异常 exceptionCaught ------ " + incoming.remoteAddress()+ " ---异常 " + cause.toString());
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("管道关闭 channelInactive ------ " + ctx.channel().id());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("移除管道 handlerRemoved ------ " + incoming.remoteAddress()+" 在线 " + incoming.id());

        //移除channelGroup通道组
        ServerHandler.channelGroup.remove(ctx.channel());
    }
}