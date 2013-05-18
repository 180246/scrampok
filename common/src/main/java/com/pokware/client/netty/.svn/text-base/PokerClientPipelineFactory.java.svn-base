package com.pokware.client.netty;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import com.pokware.client.ServerEventListener;
import com.pokware.protobuf.RPCMessages;

public class PokerClientPipelineFactory implements ChannelPipelineFactory {

	private ServerEventListener listener;

	public PokerClientPipelineFactory(ServerEventListener listener) {
		this.listener = listener;
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline p = pipeline();
		p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
		p.addLast("protobufDecoder", new ProtobufDecoder(RPCMessages.Response.getDefaultInstance()));

		p.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
		p.addLast("protobufEncoder", new ProtobufEncoder());

		p.addLast("handler", new PokerClientHandler(listener));
		return p;

	}
}
