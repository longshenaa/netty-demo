package com.example.nettydemo.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        //接收连接
        EventLoopGroup boss = new NioEventLoopGroup();
        //工作
        EventLoopGroup work = new NioEventLoopGroup();
        //给服务配置一堆参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel s) throws Exception {
                    ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes());
                    s.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
                    s.pipeline().addLast(new StringDecoder());
                    s.pipeline().addLast(new ServerHandler());
                }
            })
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture f = bootstrap.bind(5000).sync();

        f.channel().closeFuture().sync();
        boss.shutdownGracefully();
        work.shutdownGracefully();

    }
}
