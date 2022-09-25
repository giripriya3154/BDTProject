package cs.miu.edu;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.AnalysisException;

public class HBaseSqlHospitalApplication {

    public static void main(String[] args) throws AnalysisException, IOException {
        String tableName = args[0];
        SparkConf conf = new SparkConf().setAppName("hbase-sql-hospital").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession spark = SparkSession
                .builder()
                .appName("hbase-sql-hospital")
                .config(conf)
                .getOrCreate();

        fetchFromHBaseAndWriteIntoCSV(sc, spark, tableName);

        spark.stop();
        sc.close();
    }


    private static void fetchFromHBaseAndWriteIntoCSV(JavaSparkContext sc, SparkSession spark, String tableName) throws IOException {
        HBaseDataScanner hBaseDataScanner = new HBaseDataScanner(tableName);
        JavaRDD<Hospital> javaRDD = sc.parallelize(hBaseDataScanner.fetchHospitalInfoFromHbase());
        List<StructField> fields = new ArrayList<>();
        fields.add(DataTypes.createStructField("hospitalName", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("streetAddress", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("state", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("city", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("zip", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("country", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("phoneNo", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("hospitalType", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("hospitalOwnership", DataTypes.StringType, true));
      //  fields.add(DataTypes.createStructField("isEmergencyService", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("review", DataTypes.StringType, true));

        StructType schema = DataTypes.createStructType(fields);

        JavaRDD<Row> rowRDD = javaRDD.map((Function<Hospital, Row>) hospital ->
                RowFactory.create(
                        hospital.getHospitalName(),
                        hospital.getStreetAddress(),
                        hospital.getCity(),
                        hospital.getState(),
                        hospital.getZip(),
                        hospital.getCountry(),
                        hospital.getPhoneNo(),
                        hospital.getHospitalType(),
                        hospital.getHospitalOwnership(),
                        hospital.getReview()));

        Dataset<Row> dataFrame = spark.createDataFrame(rowRDD, schema);
        dataFrame.createOrReplaceTempView("hospitalTableView");

        Dataset<Row> hospitalInformation = spark.sql("SELECT  hospitalName, streetAddress,city, state, zip,country,phoneNo,hospitalType,hospitalOwnership ,review FROM hospitalTableView");
        hospitalInformation.show(200);

        hospitalInformation.repartition(1).write().mode("append").option("header", "true").csv("hdfs://localhost/user/cloudera/"+ tableName);

    }
}

