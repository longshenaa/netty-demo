package com.example.nettydemo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * description:
 *
 * @author zhangyanqing
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf byteBuf = (ByteBuf)msg;
            while (byteBuf.isReadable()) {
                System.out.println(byteBuf.readByte());
                System.out.flush();
            }
//        super.channelRead(ctx, msg);
        }
        finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
