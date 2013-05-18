package com.pokware.client.netty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.pokware.client.ServerEventListener;
import com.pokware.client.errors.Errors;
import com.pokware.client.errors.Errors.Key;
import com.pokware.protobuf.Events.ServerEvent;
import com.pokware.protobuf.Events.ServerEvent.Type;
import com.pokware.protobuf.ProtobufTools;
import com.pokware.protobuf.RPCMessages.Request;
import com.pokware.protobuf.RPCMessages.Response;

public class PokerClientHandler extends SimpleChannelUpstreamHandler {

	private volatile Channel channel;
	private final Map<Integer, BlockingQueue<Response>> currentRequestMap = new ConcurrentHashMap<Integer, BlockingQueue<Response>>();
	private ServerEventListener listener;

	public PokerClientHandler(ServerEventListener listener) {
		this.listener = listener;
	}

	public Response sendRequest(Request request) throws RequestFailedException {

		LinkedBlockingQueue<Response> linkedBlockingQueue = new LinkedBlockingQueue<Response>();
		currentRequestMap.put(request.getId(), linkedBlockingQueue);

		channel.write(request);

		Response response = null;
		boolean interrupted = false;
		for (;;) {
			try {
				response = linkedBlockingQueue.take();
				currentRequestMap.remove(request.getId());
				break;
			} catch (InterruptedException e) {
				interrupted = true;
			}
		}

		if (interrupted) {
			Thread.currentThread().interrupt();
		}

		checkResponse(request, response);

		return response;
	}

	private void checkResponse(Request request, Response response) throws RequestFailedException {

		int statusCode = response.getStatus();
		if (statusCode != 0) {
			Key key = Errors.getKey(statusCode);
			List<String> errorMessageParameterList = response.getErrorMessageParameterList();

			String message = null;
			switch (errorMessageParameterList.size()) {
			case 0:
				message = key.getFormat();
				break;
			case 1:
				message = String.format(key.getFormat(), errorMessageParameterList.get(0));
				break;
			case 2:
				message = String.format(key.getFormat(), errorMessageParameterList.get(0), errorMessageParameterList.get(1));
				break;
			case 3:
				message = String.format(key.getFormat(), errorMessageParameterList.get(0), errorMessageParameterList.get(1), errorMessageParameterList.get(2));
				break;
			case 4:
				message = String.format(key.getFormat(), errorMessageParameterList.get(0), errorMessageParameterList.get(1), errorMessageParameterList.get(2),
						errorMessageParameterList.get(3));
				break;
			}

			throw new RequestFailedException("Request failed " + request.getType() + " (id=" + request.getId() + ") due to an unexpected error [" + message
					+ "]");
		}
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		super.handleUpstream(ctx, e);
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		this.channel = e.getChannel();
		super.channelOpen(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, final MessageEvent e) {
		Object message = e.getMessage();
		if (message instanceof Response) {
			Response response = (Response) message;
			if (response.hasServerEvent()) {
				dispatchEvent(response.getServerEvent());
			} else {
				int responseId = response.getId();
				if (responseId == 0) {
					throw new RuntimeException("request/response Id not set on server response: " + ProtobufTools.convertToString(response));
				}
				BlockingQueue<Response> responseQueue = currentRequestMap.get(responseId);
				if (responseQueue == null) {
					throw new RuntimeException("no response queue found for request Id = " + responseId + ". response = "
							+ ProtobufTools.convertToString(response));
				}
				responseQueue.offer(response);
			}
		} else {
			throw new RuntimeException("Can't find type of server-side message: " + message.getClass().getName());
		}
	}

	private void dispatchEvent(ServerEvent event) {
		try {
			Type type = event.getType();

			// get event
			Method getServerEventMethod = event.getClass().getMethod("get" + type.name());
			Object serverEvent = getServerEventMethod.invoke(event);

			// callback method
			Method method = listener.getClass().getMethod("on" + type.name(), serverEvent.getClass());
			method.invoke(listener, serverEvent);
		} catch (InvocationTargetException e) {
			e.getTargetException().printStackTrace();
			// System.err.println("Can't dispatch server event " + event.toString());
			// throw new RuntimeException("Can't dispatch server event " + event.toString(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}
}
