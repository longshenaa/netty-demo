package com.example.nettydemo.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Client {
    private static class SingletonHolder {
        static final Client instance = new Client();
    }
    public static Client getInstance() {
        return SingletonHolder.instance;
    }
    private EventLoopGroup group;
    private Bootstrap b;
    private ChannelFuture cf;

    private Client() {
        group = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
//                        ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes());
//                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
//                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });

    }
    private void connect() {
        try {
            this.cf = b.connect("127.0.0.1", 5000).sync();
            System.out.println("连接成功！");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public ChannelFuture getChannelFuture() {
        return this.cf;
    }
    public static void main(String[] args) throws InterruptedException {
        final Client c = Client.getInstance();
        c.connect();
        ChannelFuture cf = c.getChannelFuture();
        for (int i = 0; i < 10; i++) {
            Request request = new Request();
            request.setId(String.valueOf(i));
            request.setMessage("你好" + i);
            cf.channel().writeAndFlush(request);
        }
        cf.channel().closeFuture().sync();

//        EventLoopGroup work = new NioEventLoopGroup();
//        Bootstrap bootstrap = new Bootstrap();
//        bootstrap.group(work)
//                .channel(NioSocketChannel.class)
//                .handler(new LoggingHandler(LogLevel.DEBUG))
//                .handler(new ChannelInitializer<SocketChannel>() {
//                    @Override
//                    protected void initChannel(SocketChannel ch) throws Exception {
////                        ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes());
////                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
//                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
//                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
////                        ch.pipeline().addLast(new StringDecoder());
//                        ch.pipeline().addLast(new ClientHandler());
//                    }
//                });
//        ChannelFuture f = bootstrap.connect("127.0.0.1", 5000).sync();
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        for (int i = 0; i < 5; i++) {
//            Request request = new Request();
//            request.setId(String.valueOf(i));
//            request.setMessage("你好" + i);
//            f.channel().writeAndFlush(request);
//        }
//        f.channel().closeFuture().sync();
//        work.shutdownGracefully();

    }
}
