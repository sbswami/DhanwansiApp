package com.sanshy.dhanawanshi.SingleItems;

import java.util.ArrayList;
import java.util.Date;

public class SinglePartnerListItem {

    String PartnerId;
    String PartnerName;
    String PartnerCast;
    String PartnerGanv;
    Date date;

    ArrayList<SingleChildListItem> childListItems = new ArrayList<>();

    public SinglePartnerListItem(String partnerId, String partnerName, String partnerCast, String partnerGanv, Date date, ArrayList<SingleChildListItem> childListItems) {
        PartnerId = partnerId;
        PartnerName = partnerName;
        PartnerCast = partnerCast;
        PartnerGanv = partnerGanv;
        this.date = date;
        this.childListItems = childListItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<SingleChildListItem> getChildListItems() {
        return childListItems;
    }

    public void setChildListItems(ArrayList<SingleChildListItem> childListItems) {
        this.childListItems = childListItems;
    }

    public String getPartnerId() {
        return PartnerId;
    }

    public void setPartnerId(String partnerId) {
        PartnerId = partnerId;
    }

    public String getPartnerName() {
        return PartnerName;
    }

    public void setPartnerName(String partnerName) {
        PartnerName = partnerName;
    }

    public String getPartnerCast() {
        return PartnerCast;
    }

    public void setPartnerCast(String partnerCast) {
        PartnerCast = partnerCast;
    }

    public String getPartnerGanv() {
        return PartnerGanv;
    }

    public void setPartnerGanv(String partnerGanv) {
        PartnerGanv = partnerGanv;
    }
}
