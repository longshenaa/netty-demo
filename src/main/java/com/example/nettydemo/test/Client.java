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

public class Client {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup work = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
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
        ChannelFuture f = bootstrap.connect("127.0.0.1", 5000).sync();
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
//        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes()));
        for (int i = 0; i < 5; i++) {
            Request request = new Request();
            request.setId(String.valueOf(i));
            request.setMessage("你好" + i);
            f.channel().writeAndFlush(request);
        }
        f.channel().closeFuture().sync();
        work.shutdownGracefully();

    }
}
