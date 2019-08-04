package net.bnbstudy.dev;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.event.ListSelectionEvent;

import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.influxdb.dto.BoundParameterQuery.QueryBuilder;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import net.bnbstudy.dev.domain.Bnb_DHT_Sensor;
import net.bnbstudy.dev.domain.Bnb_Soil_Moisture_Sensor;


@Service
public class InfluxService {

	@Autowired
	private InfluxDBTemplate<Point> influxDBTemplate;

	public List<String> listMeasurements() {
		Query query = QueryBuilder.newQuery("SHOW MEASUREMENTS")
		        .forDatabase("mqtt")
		        .create();
		
		QueryResult queryResult = influxDBTemplate.query(query);
		
		
		List<String> list = new ArrayList<>();
		for(Result result : queryResult.getResults()) {
			for(Series series : result.getSeries()) {
				//sensor.setTime(Instant.from(series.getColumns().get(0)));
				
				ListIterator<List<Object>> iterator = series.getValues().listIterator();
				while(iterator.hasNext()) {
					List<Object> lst = iterator.next();
					for(Object obj : lst) {
						list.add( obj.toString() );
					}
				}
			}
		}
		
		//return queryResult.toString();
		return list;
	}
	
	public List<Bnb_DHT_Sensor> lastBnb_DHT_Sensor() {
		Query query = QueryBuilder.newQuery("SELECT * FROM Bnb_DHT_Sensor ORDER BY time DESC LIMIT 1")
				.forDatabase("mqtt")
				.create();
		
		QueryResult queryResult = influxDBTemplate.query(query);
		System.out.println("lastBnb_DHT_Sensor ["+ queryResult +"]");
		
		//[QueryResult [results=[Result [series=[Series [name=Bnb_DHT_Sensor, tags=null, columns=[time, Humidity, Temperature, device, sensor, sensor_id, student_id, student_name], values=[[2019-08-04T12:35:04.967384Z, 44, 26.7, WemosD1R1, DHT11_Sensor, Bnb_DHT_Sensor_001, 201934-363896, Narae]]]], error=null]], error=null]]
		/*
		Bnb_DHT_Sensor sensor = new Bnb_DHT_Sensor();
		for(Result result : queryResult.getResults()) {
			for(Series series : result.getSeries()) {
				sensor.setTime(Instant.from(series.getColumns().get(0)));
			}
		}
		*/
		return new InfluxDBResultMapper().toPOJO(queryResult, Bnb_DHT_Sensor.class);
	}
	
	public List<Bnb_Soil_Moisture_Sensor> lastBnb_Soil_Moisture_Sensor() {
		Query query = QueryBuilder.newQuery("SELECT * FROM Bnb_Soil_Moisture_Sensor ORDER BY time DESC LIMIT 1")
				.forDatabase("mqtt")
				.create();
		
		QueryResult queryResult = influxDBTemplate.query(query);
		System.out.println("lastBnb_Soil_Moisture_Sensor ["+ queryResult +"]");
		
		//[QueryResult [results=[Result [series=[Series [name=Bnb_DHT_Sensor, tags=null, columns=[time, Humidity, Temperature, device, sensor, sensor_id, student_id, student_name], values=[[2019-08-04T12:35:04.967384Z, 44, 26.7, WemosD1R1, DHT11_Sensor, Bnb_DHT_Sensor_001, 201934-363896, Narae]]]], error=null]], error=null]]
		/*
		Bnb_DHT_Sensor sensor = new Bnb_DHT_Sensor();
		for(Result result : queryResult.getResults()) {
			for(Series series : result.getSeries()) {
				sensor.setTime(Instant.from(series.getColumns().get(0)));
			}
		}
		 */
		return new InfluxDBResultMapper().toPOJO(queryResult, Bnb_Soil_Moisture_Sensor.class);
	}
}
