package com.sanshy.dhanawanshi.SingleAdapters;

public class SuggetionItem {

    String Id;
    String Name;
    String Cast;
    String Village;
    String FirstRelationKey;
    String FirstRelationValue;

    public SuggetionItem(String id, String name, String cast, String village, String firstRelationKey, String firstRelationValue) {
        Id = id;
        Name = name;
        Cast = cast;
        Village = village;
        FirstRelationKey = firstRelationKey;
        FirstRelationValue = firstRelationValue;
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

    public String getFirstRelationKey() {
        return FirstRelationKey;
    }

    public void setFirstRelationKey(String firstRelationKey) {
        FirstRelationKey = firstRelationKey;
    }

    public String getFirstRelationValue() {
        return FirstRelationValue;
    }

    public void setFirstRelationValue(String firstRelationValue) {
        FirstRelationValue = firstRelationValue;
    }
}
