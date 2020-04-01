package com.example.nettydemo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * description:
 *
 * @author zhangyanqing
 */
public class DiscardServer implements Runnable {
    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
    public static void main(String[] args) {
        DiscardServer discardServer = new DiscardServer();
        discardServer.setPort(8000);
        discardServer.run();
    }
}
