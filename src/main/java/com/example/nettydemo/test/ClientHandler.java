package com.example.nettydemo.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
//            ByteBuf byteBuf = (ByteBuf)msg;
//            byte[] bytes = new byte[byteBuf.readableBytes()];
//            byteBuf.readBytes(bytes);
//            String request = new String(bytes, "UTF-8");
            Response res = (Response) msg;
            System.out.println("客户端接收到信息：id = " + res.getId() + ";message = " + res.getMessage());
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }
}
