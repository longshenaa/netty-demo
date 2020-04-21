package com.example.nettydemo.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            String msgStr = (String) msg;
            System.out.println("服务端接收：" + msgStr);
            String response = "我是响应数据$_";
//            ctx.writeAndFlush(response);
//            ByteBuf byteBuf = (ByteBuf)msg;
//            byte[] bytes = new byte[byteBuf.readableBytes()];
//            byteBuf.readBytes(bytes);
//            String request = new String(bytes, "UTF-8");
//            System.out.println("server message:" + request);
//
            ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
//            //自动断开连接
//            f.addListener(ChannelFutureListener.CLOSE);
        }
        finally {

        }
    }
}
