package com.example.urldemo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urldemo.R;
import com.example.urldemo.adapter.MovieAdapter;
import com.example.urldemo.dto.Movie;
import com.example.urldemo.utils.ExecutorUtil;
import com.example.urldemo.utils.LogUtils;
import com.example.urldemo.utils.MovieUtil;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author yangyinhua
 */
@SuppressLint("HandlerLeak")
public class MovieMainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private static Context mContext;
    private long lastLoadTime = 0;
    private final long LOAD_INTERVAL = 500;
    public static boolean debug = true;
    private int mPage = 1;
    private ViewGroup parentView;

    private static String url = "http://172.16.1.106:8080/selectMovieAll";
    private static String movieUrl = "https://pcw-api.iqiyi.com/search/recommend/list?channel_id=1&data_type=1&mode=11&ret_num=48&session=0ddd98b967719e085113809a81e843ad&three_category_id=9";

    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ID_UPDATE_MOVIE:
                    if (msg.obj != null) {
                        if (mMovieAdapter == null) {
                            mMovieAdapter = new MovieAdapter();
                            mRecyclerView.setAdapter(mMovieAdapter);
                        }
                        mMovieAdapter.setMovies(mContext, (List<Movie>) msg.obj);
                        mMovieAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mContext == null){
            mContext = this;
        }
        setContentView(R.layout.activity_moves);
        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    //判断是否滑动到了列表最后
                    assert layoutManager != null;
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    int totalCount = layoutManager.getItemCount();

                    if (lastVisibleItemPosition == totalCount - 1) {
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - lastLoadTime >= LOAD_INTERVAL) {
                            lastLoadTime = currentTime;
                            //加载更多数据
                            loadData(movieUrl + "&page_id=" + mPage, mHandler);
                            mPage ++;
                        }

                    }
                }
            }
        });
        loadData(movieUrl + "&page_id=" + mPage, mHandler);
        mPage ++;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void loadData(String url, Handler handler){
        executor.execute(() -> {
            List<Movie> movies = null;
            try {
                movies = MovieUtil.getMoviesFromServer(url);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            if (movies != null){
                Message msg = handler.obtainMessage(MSG_ID_UPDATE_MOVIE, movies);
                handler.sendMessage(msg);
            }
        });
    }

}
