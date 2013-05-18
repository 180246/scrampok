package com.pokware.server.netty;

import static org.jboss.netty.channel.Channels.*;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pokware.protobuf.RPCMessages;

public class PokerServerPipelineFactory implements ChannelPipelineFactory {
	
	private final Provider<PokerServerHandler> pokerServerHandlerProvider;
	
	@Inject
	public PokerServerPipelineFactory(Provider<PokerServerHandler> pokerServerHandlerProvider) {
		this.pokerServerHandlerProvider = pokerServerHandlerProvider;
	}
	
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline p = pipeline();
        p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        p.addLast("protobufDecoder", new ProtobufDecoder(RPCMessages.Request.getDefaultInstance()));

        p.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        p.addLast("protobufEncoder", new ProtobufEncoder());

        p.addLast("handler", pokerServerHandlerProvider.get());
        return p;
    }
}
