package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletDetailData {
    @SerializedName("walletDetail")
    @Expose
    private WalletDetail walletDetail;

    public WalletDetail getWalletDetail() {
        return walletDetail;
    }

    public void setWalletDetail(WalletDetail walletDetail) {
        this.walletDetail = walletDetail;
    }

}
