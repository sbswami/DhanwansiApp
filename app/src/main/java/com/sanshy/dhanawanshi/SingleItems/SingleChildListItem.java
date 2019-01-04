package com.sanshy.dhanawanshi.SingleItems;

public class SingleChildListItem {

    String Id;
    String ChildName;
    String ChildVillage;
    boolean Male;

    public SingleChildListItem(String id, String childName, String childVillage, boolean Male) {
        Id = id;
        ChildName = childName;
        ChildVillage = childVillage;
        this.Male = Male;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getChildName() {
        return ChildName;
    }

    public void setChildName(String childName) {
        ChildName = childName;
    }

    public String getChildVillage() {
        return ChildVillage;
    }

    public void setChildVillage(String childVillage) {
        ChildVillage = childVillage;
    }

    public boolean isMale() {
        return Male;
    }

    public void setMale(boolean male) {
        Male = male;
    }
}
