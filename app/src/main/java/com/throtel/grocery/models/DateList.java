package com.throtel.grocery.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateList implements Serializable
{

@SerializedName("deliveryDate")
@Expose
private String deliveryDate;
private final static long serialVersionUID = 8712848052418734915L;

public String getDeliveryDate() {
return deliveryDate;
}

public void setDeliveryDate(String deliveryDate) {
this.deliveryDate = deliveryDate;
}

}