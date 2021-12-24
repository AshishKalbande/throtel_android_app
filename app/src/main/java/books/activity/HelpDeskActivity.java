package books.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.throtel.grocery.R;

public class HelpDeskActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_desk);
        if (getIntent() != null) {
            String name = getIntent().getStringExtra("DATA");
            if (!TextUtils.isEmpty(name))
                setUpToolbarBackButton(name);
        }
//        tvNumber=findViewById(R.id.tvNumber);
//        tvNumber=findViewById(R.id.tvEmail);
    }
}
