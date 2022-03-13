package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StateData {
    @SerializedName("stateList")
    @Expose
    private ArrayList<A_Test.model.StateList> stateList = null;

    public ArrayList<StateList> getStateList() {
        return stateList;
    }

    public void setStateList(ArrayList<A_Test.model.StateList> stateList) {
        this.stateList = stateList;
    }
}
