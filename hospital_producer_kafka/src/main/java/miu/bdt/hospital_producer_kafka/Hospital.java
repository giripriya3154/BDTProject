package miu.bdt.hospital_producer_kafka;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Hospital {

    @CsvBindByName(column = "Provider ID")
    private int id;

    @CsvBindByName(column = "Hospital Name")
    private  String hospitalName;

    @CsvBindByName(column = "Address")
    private String streetAddress;

    @CsvBindByName(column = "City")
    private String city;

    @CsvBindByName(column = "State")
    private String state;

    @CsvBindByName(column = "ZIP Code")
    private String zip;

    @CsvBindByName(column = "County Name")
    private String country;

    @CsvBindByName(column = "Phone Number")
    private String phoneNo;

    @CsvBindByName(column = "Hospital Type")
    private String hospitalType;

    @CsvBindByName(column = "Hospital Ownership")
    private String hospitalOwnership;

    @CsvBindByName(column = "Emergency Services")
    private Boolean isEmergencyService;

    @CsvBindByName(column = "Hospital overall rating footnote")
    private String review;



}
