package com.example.urldemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.urldemo.R;
import com.example.urldemo.adapter.MessageAdapter;
import com.example.urldemo.dto.MyMessage;
import com.example.urldemo.utils.ExecutorUtl;
import com.example.urldemo.utils.LogUtils;
import com.example.urldemo.utils.OkhttpUtl;

import java.io.IOException;

import okhttp3.Response;

/**
 * @author yangyinhua
 */
public class AiActivity extends BaseActivity implements View.OnClickListener {

    private final String AI_URL = "https://v.api.aa1.cn/api/api-xiaoai/talk.php?type=text";
    private static final int MSG_ID_UPDATE_MESSAGE_LIST_DATA = 1;
    private static final int MSG_ID_AI_MESSAGE = 2;
    private static final int MSG_ID_SEND_MESSAGE_TO_AI = 3;
    private Button sendButton;
    private EditText inputText;
    private MessageAdapter adapter;
    private RecyclerView recyclerView;

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ID_UPDATE_MESSAGE_LIST_DATA:
                    if (msg.obj != null) {
                        MyMessage obj = (MyMessage) msg.obj;
                        String message = obj.getMessage();
                        adapter.setMessage((MyMessage) msg.obj);
                    }
                    break;
                case MSG_ID_SEND_MESSAGE_TO_AI:
                    adapter.setMessage((MyMessage) msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        inputText = findViewById(R.id.inputText);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                1,
                RecyclerView.VERTICAL);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(recyclerView);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sendButton) {
            Editable text = inputText.getText();
            if (!TextUtils.isEmpty(text)) {
                inputText.setText("");
                MyMessage myMessage = new MyMessage(text, MyMessage.MY);
                mHandler.sendMessage(mHandler.obtainMessage(MSG_ID_UPDATE_MESSAGE_LIST_DATA, myMessage));
                ExecutorUtl.getExecutor().execute(() -> {
                    Response urlDataJson = OkhttpUtl.getUrlResponse(AI_URL + "&msg=" + myMessage.getMessage());
                    String urlData;
                    if (urlDataJson == null || urlDataJson.body() == null) {
                        urlData = "远端数据异常，自定义数据";
                    } else {
                        try {
                            urlData = urlDataJson.body().string().replace("\n", "");
                        } catch (IOException e) {
                            urlData = "远端数据异常，自定义数据";
                        }
                    }
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_ID_SEND_MESSAGE_TO_AI, new MyMessage(urlData, MyMessage.AI)));
                });
            }
        }
    }
}
