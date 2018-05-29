package server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

/**
 * Created by Alikin E.A. on 18.05.18.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH =  new AsciiString("Content-Length");
    private static final AsciiString CONNECTION =  new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE =  new AsciiString("keep-alive");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof HttpRequest) {
                HttpRequest req = (HttpRequest) msg;

                boolean keepAlive = HttpUtil.isKeepAlive(req);
                if (HttpMethod.GET.equals(req.method())) {
                    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(Service.getByRequest(req)));
                    response.headers().set(CONTENT_TYPE, "application/json");
                    response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
                    if (!keepAlive) {
                        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                    } else {
                        response.headers().set(CONNECTION, KEEP_ALIVE);
                        ctx.write(response);
                    }
                } else {
                    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                }

            }
        } catch (Exception e) {
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
