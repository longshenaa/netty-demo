package com.example.nettydemo.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;

public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Request req = (Request) msg;
            if (req != null) {
                System.out.println("server端接收到：id=" + req.getId() + ";message=" + req.getMessage());
            }
//            String msgStr = (String) msg;
//            System.out.println("服务端接收：" + msgStr);
//            String response = "我是响应数据$_";
//            ctx.writeAndFlush(response);
//            ByteBuf byteBuf = (ByteBuf)msg;
//            byte[] bytes = new byte[byteBuf.readableBytes()];
//            byteBuf.readBytes(bytes);
//            String request = new String(bytes, "UTF-8");
//            System.out.println("server message:" + request);
//
            Response response = new Response();
            response.setId(req.getId());
            response.setMessage("接收到消息了。");
            ChannelFuture f = ctx.writeAndFlush(response);
//            //自动断开连接
//            f.addListener(ChannelFutureListener.CLOSE);
        }
        finally {

        }
    }
}
