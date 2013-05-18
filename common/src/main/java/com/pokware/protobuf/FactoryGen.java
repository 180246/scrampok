package com.pokware.protobuf;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.pokware.protobuf.Events.ServerEvent;

public class FactoryGen {
		
	public static class TemplateSetter {
		private String fieldName;
		private String capitalizedFieldName;				
		public TemplateSetter(String fieldName, String capitalizedFieldName, boolean requiresProtobufConversion) {
			super();			
			if (!requiresProtobufConversion) {
				this.fieldName = fieldName;
			}
			else {
				this.fieldName = fieldName + ".toProtocolBuffer()";
			}
			
			this.capitalizedFieldName = capitalizedFieldName;			
		}
		public String getFieldName() {
			return fieldName;
		}
		public String getCapitalizedFieldName() {
			return capitalizedFieldName;
		}		
	}
	
	public static class TemplateMethod {
		private StringBuffer parameterList = new StringBuffer();
		private String eventName;		
		private List<TemplateSetter> setters = new ArrayList<TemplateSetter>();
		
		public TemplateMethod(String[] paramNames, String[] paramTypes, boolean[] requiresProtobufConversion, String eventName) {
			for (int i = 0; i < paramTypes.length; i++) {
				parameterList.append(paramTypes[i] + " " + paramNames[i]);
				
				TemplateSetter templateSetter = new TemplateSetter(paramNames[i], upperCaseFirstLetter(paramNames[i]), requiresProtobufConversion[i]);
				setters.add(templateSetter);
				
				if (i < paramTypes.length - 1) {
					parameterList.append(", ");								
				}
			}
			
			this.eventName = eventName;
		}
		public String getParameterList() {
			return parameterList.toString();
		}		
		public String getEventName() {
			return eventName;
		}		
		public List<TemplateSetter> getSetters() {
			return setters;
		}
	}
	
	public static String upperCaseFirstLetter(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
	
	public static void main(String[] args) throws Exception {	
		
		List<TemplateMethod> methodList = new ArrayList<TemplateMethod>();
		
		Class<?>[] classes = Events.class.getClasses();		
		for (Class<?> clazz : classes) {		
			if(clazz.getSimpleName().equals(ServerEvent.class.getSimpleName())) continue;
			
			Descriptor descriptor = (Descriptor) clazz.getMethod("getDescriptor").invoke(null);
			List<FieldDescriptor> fields = descriptor.getFields();
			
			String[] paramNames = new String[fields.size()];
			String[] paramTypes = new String[fields.size()]; 
			boolean[] requiresProtobufConversion = new boolean[fields.size()];
			int index = 0;
			for (FieldDescriptor fieldDescriptor : fields) {
				paramNames[index] = fieldDescriptor.getName();
				String primitiveType = fieldDescriptor.getJavaType().toString().toLowerCase();
				if (primitiveType.equals("message")) {
					requiresProtobufConversion[index] = true;
					paramTypes[index] = upperCaseFirstLetter(fieldDescriptor.getName());	
				}
				else {
					paramTypes[index] = primitiveType;
				}				
				index++;						
			}
			
			TemplateMethod templateMethod = new TemplateMethod(paramNames, paramTypes, requiresProtobufConversion, clazz.getSimpleName());
			methodList.add(templateMethod);
		}
		
		StringTemplateGroup group =  new StringTemplateGroup("myGroup", "./src/main/resources", DefaultTemplateLexer.class);
		StringTemplate eventResponseFactoryTemplate = group.getInstanceOf("EventResponseFactory");
		
		eventResponseFactoryTemplate.setAttribute("methods", methodList);
		
		String path = new File(FactoryGen.class.getResource("FactoryGen.class").toURI()).getAbsolutePath();
		path = path.replace("common", "pokerserver").replace("target/classes", "src/main/java").replace(FactoryGen.class.getSimpleName()+".class", "EventResponseFactory.java");
		
		FileWriter fileWriter = new FileWriter(path, false);
		fileWriter.write(eventResponseFactoryTemplate.toString());
		fileWriter.flush();
		fileWriter.close();
	}

}
