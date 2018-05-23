package server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.AsciiString;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

/**
 * Created by Alikin E.A. on 18.05.18.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final byte[] CONTENT = "{\"mem\":2251460,\"mem.free\":1205247,\"processors\":4,\"instance.uptime\":420410,\"uptime\":450226,\"systemload.average\":0.31,\"heap.committed\":2097152,\"heap.init\":2097152,\"heap.used\":890880,\"heap\":2097152,\"nonheap.committed\":158704,\"nonheap.init\":2496,\"nonheap.used\":154312,\"nonheap\":0,\"threads.peak\":25,\"threads.daemon\":21,\"threads.totalStarted\":42,\"threads\":23,\"classes\":12764,\"classes.loaded\":12764,\"classes.unloaded\":0,\"gc.g1_young_generation.count\":17,\"gc.g1_young_generation.time\":818,\"gc.g1_old_generation.count\":0,\"gc.g1_old_generation.time\":0,\"cache.availableLVL.size\":36,\"httpsessions.max\":-1,\"httpsessions.active\":0,\"datasource.primary.active\":0,\"datasource.primary.usage\":0.0,\"gauge.response.purchaseDetailedObject.statistic\":201.0,\"gauge.response.procedure.list\":33.0,\"gauge.response.procedure.protocols.id\":4.0,\"gauge.response.references.singlePurchaseReasons\":34.0,\"gauge.response.lot.id\":653.0,\"gauge.response.references.customers\":226.0,\"gauge.response.unmapped\":3.0,\"gauge.response.unitedCommissionApplication.statistic\":15.0,\"gauge.response.tariff.list\":457.0,\"gauge.response.procedure.eis.procedureEntityId\":12.0,\"gauge.response.purchaseDetailedObject.id\":77.0,\"gauge.response.procedure.grbs.statistic\":9.0,\"gauge.response.procedure.history.id\":5.0,\"gauge.response.nmcExaminationApplication.statistic\":10.0,\"gauge.response.single.commission.meeting.statistic\":9.0,\"gauge.response.eis.change.id.systemType\":5.0,\"gauge.response.lot.statistic\":75.0,\"gauge.response.unitedCommissionApplication.list.procedure.procedureEntityId\":8.0,\"gauge.response.purchaseDetailedObject.history.id\":10.0,\"gauge.response.purchaseDetailedObject.NMCMethodList.dozIds\":8.0,\"gauge.response.procedure.application.statistic\":6.0,\"gauge.response.plan.schedule.mz.statistic\":12.0,\"gauge.response.procedure.reference.statuses\":12.0,\"gauge.response.system.chain.type.entityId\":500.0,\"gauge.response.attachments.objectId.id.objectTypeId-objectTypeId\":7.0,\"gauge.response.normative.list\":414.0,\"gauge.response.procedure.mz.statistic\":8.0,\"gauge.response.procedure.id\":101.0,\"gauge.response.lot.history.id\":15.0,\"gauge.response.zpk.context\":11.0,\"gauge.response.plan.schedule.grbs.statistic\":13.0,\"gauge.response.procedure.application.applicationByProcedure.id\":5.0,\"gauge.response.contract.price.list\":252.0,\"gauge.response.procedure.statistic\":5.0,\"gauge.response.plan.schedule.mrg.statistic\":191.0,\"gauge.response.contract.statistic\":33.0,\"gauge.response.references.methodOfSupplier\":20.0,\"gauge.response.references.search.okei\":3.0,\"counter.status.500.lot.statistic\":17,\"counter.status.200.attachments.objectId.id.objectTypeId-objectTypeId\":10,\"counter.status.200.procedure.grbs.statistic\":48,\"counter.status.200.procedure.mz.statistic\":52,\"counter.status.200.lot.statistic\":48,\"counter.status.200.procedure.history.id\":10,\"counter.status.200.lot.history.id\":1,\"counter.status.200.contract.price.list\":4,\"counter.status.200.references.methodOfSupplier\":1,\"counter.status.200.unitedCommissionApplication.list.procedure.procedureEntityId\":10,\"counter.status.200.unitedCommissionApplication.statistic\":47,\"counter.status.200.procedure.eis.procedureEntityId\":10,\"counter.status.200.purchaseDetailedObject.id\":12,\"counter.status.200.procedure.protocols.id\":10,\"counter.status.200.single.commission.meeting.statistic\":48,\"counter.status.401.unmapped\":1,\"counter.status.200.purchaseDetailedObject.history.id\":11,\"counter.status.200.plan.schedule.mz.statistic\":48,\"counter.status.500.unitedCommissionApplication.statistic\":17,\"counter.status.500.procedure.grbs.statistic\":13,\"counter.status.200.purchaseDetailedObject.statistic\":47,\"counter.status.200.procedure.application.statistic\":48,\"counter.status.500.plan.schedule.mrg.statistic\":17,\"counter.status.200.eis.change.id.systemType\":20,\"counter.status.200.normative.list\":3,\"counter.status.500.contract.statistic\":17,\"counter.status.200.procedure.id\":10,\"counter.status.200.references.singlePurchaseReasons\":1,\"counter.status.500.purchaseDetailedObject.statistic\":17,\"counter.status.200.plan.schedule.grbs.statistic\":48,\"counter.status.200.procedure.statistic\":52,\"counter.status.500.plan.schedule.mz.statistic\":17,\"counter.status.200.plan.schedule.mrg.statistic\":47,\"counter.status.200.system.chain.type.entityId\":23,\"counter.status.200.references.search.okei\":12,\"counter.status.200.tariff.list\":5,\"counter.status.500.nmcExaminationApplication.statistic\":17,\"counter.status.500.single.commission.meeting.statistic\":17,\"counter.status.200.procedure.application.applicationByProcedure.id\":10,\"counter.status.500.plan.schedule.grbs.statistic\":17,\"counter.status.200.purchaseDetailedObject.NMCMethodList.dozIds\":2,\"counter.status.200.zpk.context\":56,\"counter.status.200.references.customers\":28,\"counter.status.200.procedure.list\":1,\"counter.status.400.procedure.grbs.statistic\":4,\"counter.status.500.procedure.application.statistic\":17,\"counter.status.200.contract.statistic\":48,\"counter.status.200.procedure.reference.statuses\":1,\"counter.status.200.lot.id\":1,\"counter.status.500.procedure.statistic\":13,\"counter.status.500.procedure.mz.statistic\":13}".getBytes();

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
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;

            if (req.uri().equals("/api/test/json")) {
                boolean keepAlive = HttpUtil.isKeepAlive(req);
                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
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
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
