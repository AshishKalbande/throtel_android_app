package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionData {
    @SerializedName("transactionChargeDetail")
    @Expose
    private TransactionChargeDetail transactionChargeDetail;

    public TransactionChargeDetail getTransactionChargeDetail() {
        return transactionChargeDetail;
    }

    public void setTransactionChargeDetail(TransactionChargeDetail transactionChargeDetail) {
        this.transactionChargeDetail = transactionChargeDetail;
    }
}
