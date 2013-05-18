package com.pokware.protobuf;

import java.util.Map;

import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message;
import com.pokware.client.errors.Errors;
import com.pokware.protobuf.RPCMessages.Response;

public class ProtobufTools {

	public static String convertToString(Response response) {
		int id = response.getId();
		String status = "OK";
		if (response.getStatus() != 0) {
			status = Errors.getFormattedError(response.getStatus(), response.getErrorMessageParameterList());
		}
		return "Response #" + id + " status=" + status;
	}

	public static String convertToString(Message message) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("[" + message.getSerializedSize() + "]");
		buffer.append(message.getClass().getName().substring(message.getClass().getName().lastIndexOf("$") + 1) + ":");

		Map<FieldDescriptor, Object> allFields = message.getAllFields();
		for (FieldDescriptor fieldDescriptor : allFields.keySet()) {
			Object value = allFields.get(fieldDescriptor);
			if (value instanceof EnumValueDescriptor) {
				value = ((EnumValueDescriptor) value).getName();
			}

			buffer.append(fieldDescriptor.getName() + "=" + value.toString() + " ");
		}

		return buffer.toString();
	}

}
