package net.bnbstudy.dev;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.stream.CharacterStreamReadingMessageSource;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

@SpringBootApplication
public class SpringIntegrationMqttApplication {

	@Autowired
	InfluxDBTemplate<Point> influxDBTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationMqttApplication.class, args);
	}
	
	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
	    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
	    MqttConnectOptions options = new MqttConnectOptions();
	    options.setServerURIs(new String[] { "tcp://1.212.145.110:1883" });
	    //options.setServerURIs(new String[] { "tcp://192.168.219.120:1883" });
	    //options.setUserName("ACCESS TOKEN HERE");
	    factory.setConnectionOptions(options);
	    return factory;
	}
	
	@Bean
	public IntegrationFlow mqttOutFlow() {
		return IntegrationFlows.from(CharacterStreamReadingMessageSource.stdin(),
						e -> e.poller(Pollers.fixedDelay(1000)))
				.transform(p -> p + " sent to MQTT")
				.handle(mqttOutbound())
				.get();
	}

	@Bean
	@ServiceActivator(inputChannel = "toMqtt")
	public MessageHandler mqttOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("siSamplePublisher", mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic("siSampleTopic");
		return messageHandler;
	}
	
	@MessagingGateway(defaultRequestChannel = "toMqtt")
    public interface MyGate {

        void send(String data, @Header(MqttHeaders.TOPIC) String topic);

    }
	
	
	// consumer

	@Bean
	public IntegrationFlow mqttInFlow() {
	    return IntegrationFlows.from(mqttInbound())
	            .transform(p -> p)
	            //.handle(System.out::println)
	            
	            .handle(m -> {
	            	System.out.println(m.getPayload());
	            	System.out.println(m.getHeaders());
	            	
	            	Object payload = m.getPayload();
	            	MessageHeaders headers = m.getHeaders();
	            	
	            	String topic = "";
	            	if(headers.containsKey("mqtt_receivedTopic")) {
	            		topic = (String) headers.get("mqtt_receivedTopic");
	            	}
	            	System.out.println("topic["+ topic +"]");
	            	
	            	//Gson gson = new Gson();
	            	
	            	//JsonObject jsonObject = null;
	            	JsonArray jsonArray = null;
	            	//ArrayList<JsonObject> t = null;
	            	JsonElement element = null;
	            	
	            	//Type listType = null;
	            	//List<JsonObject> jsonList = null;
	            	
	            	//System.out.println("jsonList["+ jsonList +"]");
	            	
	            	//for(JsonObject jo : jsonList) {
	            	//	System.out.println("["+ jo +"]");
	            	//}
	            	
	            	//System.out.println("jsonArray.size()["+ jsonArray.size() +"]");
	            	
	            	//String _payload_ = "";
	            	String measurement = "";
	            	//String _tags_ = "";
	            	//String _fields_ = "";
	            	//Map<String, String> tags = new HashMap<>();
	            	//Map<String, String> fields = new HashMap<>();
	            	
	            	Point point = null;
	            	
	            	try {
	            		//jsonObject = gson.fromJson(payload.toString(), JsonObject.class);
	            		
	            		
	            		//jsonArray = gson.fromJson(payload.toString(), JsonArray.class);
	            		
	            		//listType = new TypeToken<ArrayList<JsonObject>>(){}.getType();
	            		//jsonList = new Gson().fromJson(payload.toString(), listType);
	            		
	            		JsonArray arr = new JsonParser().parse(payload.toString()).getAsJsonArray();
	            		JsonElement el = null;
	            		JsonObject obj = null;
	            		JsonObject $tags = null;
	            		JsonObject $fields = null;
	            		
	            		if(arr.isJsonArray() && arr.size() > 0) {
	            			el = arr.get(0);
	            			obj = el.getAsJsonObject();
	            			System.out.println("jsonObject["+ obj +"]");
	            			System.out.println("obj has measurement["+ obj.has("measurement") +"]");
	            			System.out.println("obj has tags ["+ obj.has("tags") +"]");
	            			System.out.println("obj has fields ["+ obj.has("fields") +"]");
	            			
	            			measurement = obj.get("measurement").getAsString();
	            			$tags = obj.get("tags").getAsJsonObject();
	            			$fields = obj.get("fields").getAsJsonObject();
	            			System.out.println("measurement ["+ measurement +"]");
	            			System.out.println("$tags ["+ $tags +"]");
	            			System.out.println("$fields ["+ $fields +"]");
	            			
	            			if(null != measurement) {
	            				Point.Builder builder = Point.measurement(measurement);
	            				
	            				Map<String, String> _tags = new HashMap<String, String>();
	            				Set<Entry<String, JsonElement>> entrySet = $tags.entrySet();
	            				for(Map.Entry<String, JsonElement> entry : entrySet) {
	            					_tags.put(entry.getKey(), $tags.get(entry.getKey()).getAsString());
	            				}
	            				builder.tag(_tags);
	            				
	            				Map<String, Object> _fields = new HashMap<String, Object>();
	            				Set<Entry<String, JsonElement>> entrySet1 = $fields.entrySet();
	            				for(Map.Entry<String, JsonElement> entry : entrySet1) {
	            					_fields.put(entry.getKey(), (Object)$fields.get(entry.getKey()).getAsString());
	            				}
	            				builder.fields(_fields);
	            				
	            				point = builder.build();
	            				System.out.println("point ["+ point +"]");
	            			}
	            			
	            		}
	            		
	            		System.out.println("[InfluxDBTemplate<Point>]"+ influxDBTemplate);
	            		if(null != point) {
	            			System.out.println("[InfluxDBTemplate<Point>] before write ");
	            			influxDBTemplate.write(point);
	            			System.out.println("[InfluxDBTemplate<Point>] after write ");
	            		}
						
					} catch (Exception e) {
						System.out.println("[ERROR]"+ e.getLocalizedMessage());
					}
	            	
	            	
	            	
	            })
	            
	            //.handle(logger())
	            .get();
	}

	private LoggingHandler logger() {
	    LoggingHandler loggingHandler = new LoggingHandler("INFO");
	    loggingHandler.setLoggerName("LoggerBot");
	    return loggingHandler;
	}

	@Bean
	public MessageProducerSupport mqttInbound() {
	    MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("Consumer",
	            mqttClientFactory(), "#");
	    adapter.setCompletionTimeout(5000);
	    adapter.setConverter(new DefaultPahoMessageConverter());
	    adapter.setQos(1);
	    return adapter;
	}

}
