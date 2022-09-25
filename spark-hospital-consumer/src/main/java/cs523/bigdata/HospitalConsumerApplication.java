package cs523.bigdata;


import java.io.IOException;
import java.util.*;

import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.fasterxml.jackson.databind.ObjectMapper;



public class HospitalConsumerApplication {
    static Set<String> statesSet = new HashSet<>();
	public static void main(String[] args) {
		if(args != null && args.length > 0){
			List<String> stringList = Arrays.asList(args[0].split(","));
			statesSet = new HashSet<>(stringList);
		}

		System.out.println(statesSet);
       // load values from spark
		SparkConf sparkConf = new SparkConf().setAppName("kafka-consumer-spark").setMaster("local[*]");
		JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

		// batchDuration - The time interval at which streaming data will be divided into batches
		JavaStreamingContext context = new JavaStreamingContext(sparkContext, new Duration(2000));

		Map<String, String> stringHashMap = new HashMap<>();
		stringHashMap.put("metadata.broker.list", "localhost:9092");
		Set<String> topics = Collections.singleton("info_hospital");
		HospitalHBaseTable table = new HospitalHBaseTable();

		JavaPairInputDStream<String, String> inputDStream = KafkaUtils.createDirectStream(context, String.class,
				String.class, StringDecoder.class, StringDecoder.class, stringHashMap, topics);

		inputDStream.foreachRDD(pairRDD ->
				{
					if (pairRDD.count() > 0) {
						pairRDD.collect().forEach(record -> {
							String hospitalString = record._2;
							try {
								System.out.println(hospitalString);
								Hospital hospital = new ObjectMapper().readValue(hospitalString, Hospital.class);
								 if(statesSet.contains(hospital.getState())) {
									table.addRecord(hospital, hospital.getState());
								 }else
									 table.addRecord(hospital, "");

							} catch (IOException e) {
								e.printStackTrace();
							}
						});

					}
				});
		context.start();
		context.awaitTermination();
	}
}
