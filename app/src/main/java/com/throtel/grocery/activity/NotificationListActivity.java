package com.throtel.grocery.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.adapter.NotificationListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.models.notifications.NotificationListResponse;
import com.throtel.grocery.views.MyProgress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationListActivity extends BaseActivity {
    private static final String TAG = NotificationListActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        setUpToolbarBackButton("Notification");

        recyclerView = findViewById(R.id.rv_notification_list);
        getNotificationList();
    }

    private void getNotificationList() {
        MyProgress.start(this);

        Call<NotificationListResponse> call = RetrofitClient.getRetrofitClient().getNotificationList(
                localData.getCustomerId(), "Customer"
        );
        call.enqueue(new Callback<NotificationListResponse>() {
            @Override
            public void onResponse(Call<NotificationListResponse> call, Response<NotificationListResponse> response) {
                MyProgress.stop();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(NotificationListActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        NotificationListAdapter adapter = new NotificationListAdapter(NotificationListActivity.this, response.body().getData().getNotificationList());
                        recyclerView.setAdapter(adapter);
                        readNotifications();
                    } else {
                        Toast.makeText(NotificationListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NotificationListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationListResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(NotificationListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void readNotifications() {

        Call<DataResponse> call = RetrofitClient.getRetrofitClient().readNotifications(
                localData.getCustomerId(), "Customer"
        );

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    Log.d(TAG, response.body().getMessage());
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
