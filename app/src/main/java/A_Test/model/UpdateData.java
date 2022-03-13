package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateData {

    @SerializedName("testUserResponse")
    @Expose
    private UpdateUserLists testUserResponse;

    public UpdateUserLists getTestUserResponse() {
        return testUserResponse;
    }

    public void setTestUserResponse(UpdateUserLists testUserResponse) {
        this.testUserResponse = testUserResponse;
    }
}
