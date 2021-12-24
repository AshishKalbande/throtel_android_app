package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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