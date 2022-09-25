package cs523.bigdata;

import lombok.Data;

@Data
public class Hospital {
    private Integer id;
    private String hospitalName;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phoneNo;
    private String hospitalType;
    private String hospitalOwnership;
    private Boolean isEmergencyService;
    private String review;


    public Integer getId() {
        return id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public String getHospitalOwnership() {
        return hospitalOwnership;
    }

    public Boolean getEmergencyService() {
        return isEmergencyService;
    }

    public String getReview() {
        return review;
    }



    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", hospitalName='" + hospitalName + '\'' +
                ", address='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", hospitalType='" + hospitalType + '\'' +
                ", hospitalOwnership='" + hospitalOwnership + '\'' +
                ", isEmergencyService=" + isEmergencyService +
                ", review='" + review + '\'' +
                '}';
    }
}
