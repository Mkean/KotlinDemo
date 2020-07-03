package com.mkean.demo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mkean.demo.R;
import com.mkean.demo.app.BaseApplication;
import com.mkean.demo.entity.MenuBean;
import com.mkean.demo.http.HttpMethod;
import com.mkean.demo.http.manager.RetrofitManager;
import com.mkean.demo.httpService.HomeService;
import com.mkean.demo.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    @BindView(R.id.rl_menu)
    RecyclerView recyclerView;
    private BaseAdapter adapter;

    private List<MenuBean.DataBean> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        initView();

        initData();
    }

    private void initView() {

        dataList = new ArrayList<>();
        if (adapter == null) {
            adapter = new BaseAdapter(dataList);

            GridLayoutManager manager = new GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false);

            recyclerView.setLayoutManager(manager);

            recyclerView.setAdapter(adapter);
        }


    }

    @SuppressLint("CheckResult")
    private void initData() {
        Observable<MenuBean> menuList = RetrofitManager.getInstance(BaseApplication.baseurlFYJ).create(HomeService.class).getMenuList();
        menuList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MenuBean>() {
                    @Override
                    public void accept(MenuBean menuBean) throws Exception {
                        if (menuBean == null) {
                            Log.e("TAG", "menuBean==null");
                            return;
                        }
                        if (menuBean.getStatus() == 200) {
                            List<MenuBean.DataBean> data = menuBean.getData();
                            Log.e("TAG", "menuBean：：：" + data.size());
                            dataList.addAll(data);
                            adapter.setList(dataList);
                            adapter.notifyItemChanged(0);
                        }
                    }
                }, throwable -> {
                    Log.e("TAG", "initData: " + throwable.getMessage());
                });

        HttpMethod instance = HttpMethod.getInstance(context);
        if (instance != null) {
            Log.e("TAG", "instance != null");
        }
    }

    public static class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

        private List<MenuBean.DataBean> list;

        public BaseAdapter(List<MenuBean.DataBean> list) {
            this.list = list;
        }

        public List<MenuBean.DataBean> getList() {
            return list;
        }

        public void setList(List<MenuBean.DataBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
            BaseViewHolder holder = new BaseViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            holder.menuTv.setText(list.get(position).getName());
            Glide.with(context)
                    .asBitmap()
                    .load(list.get(position).getImage())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            LinearLayout parent = (LinearLayout) holder.menuIv.getParent();
                            ViewGroup.LayoutParams params = parent.getLayoutParams();
                            int screenWidth = ScreenUtils.getScreenWidth(context);
                            screenWidth = (int) (screenWidth - ((ScreenUtils.dp2px(context, 28))));
                            params.width = (int) (screenWidth / 5f);
                            parent.setGravity(Gravity.CENTER);
                            parent.setLayoutParams(params);

                            LinearLayout.LayoutParams imgParams = (LinearLayout.LayoutParams) holder.menuIv.getLayoutParams();
                            imgParams.height = (int) ScreenUtils.dp2px(context, 50);
                            imgParams.width = (int) ScreenUtils.dp2px(context, 50);
                            holder.menuIv.setLayoutParams(imgParams);
                            holder.menuIv.setImageBitmap(resource);
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class BaseViewHolder extends RecyclerView.ViewHolder {

            private final ImageView menuIv;
            private final TextView menuTv;

            public BaseViewHolder(@NonNull View itemView) {
                super(itemView);
                menuIv = itemView.findViewById(R.id.menu_iv);
                menuTv = itemView.findViewById(R.id.menu_tv);
            }
        }
    }
}