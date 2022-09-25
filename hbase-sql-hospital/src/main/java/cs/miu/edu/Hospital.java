package cs.miu.edu;


import java.io.Serializable;

public class Hospital implements Serializable {
    private String hospitalName;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phoneNo;
    private String hospitalType;
    private String hospitalOwnership;
    private String review;



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



    public String getReview() {
        return review;
    }


    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public void setHospitalOwnership(String hospitalOwnership) {
        this.hospitalOwnership = hospitalOwnership;
    }



    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                ", hospitalName='" + hospitalName + '\'' +
                ", address='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", hospitalType='" + hospitalType + '\'' +
                ", hospitalOwnership='" + hospitalOwnership + '\'' +

                ", review='" + review + '\'' +
                '}';
    }
}
