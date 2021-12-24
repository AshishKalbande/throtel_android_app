package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import books.models.GroupList;

import java.util.ArrayList;

public class GroupListData {
    @SerializedName("categoryList")
    @Expose
    private ArrayList<books.models.GroupList> groupList = null;

    public ArrayList<books.models.GroupList> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<GroupList> groupList) {
        this.groupList = groupList;
    }

}
