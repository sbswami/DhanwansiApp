package com.sanshy.dhanawanshi.SingleItems;

public class SingleDonationItem {
    String Id;
    String Name;
    String PhotoURL;
    String FatherName;
    String Cast;
    String Village;
    double DonationMoney;

    public SingleDonationItem(String id, String name, String photoURL, String fatherName, String cast, String village, double donationMoney) {
        Id = id;
        Name = name;
        PhotoURL = photoURL;
        FatherName = fatherName;
        Cast = cast;
        Village = village;
        DonationMoney = donationMoney;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getCast() {
        return Cast;
    }

    public void setCast(String cast) {
        Cast = cast;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String village) {
        Village = village;
    }

    public double getDonationMoney() {
        return DonationMoney;
    }

    public void setDonationMoney(double donationMoney) {
        DonationMoney = donationMoney;
    }
}
