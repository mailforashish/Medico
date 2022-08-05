package com.medico.app.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.response.MyAccountList;
import com.medico.app.response.ProductList.ProductListResponse;
import com.medico.app.utils.MedicoLoading;
import com.medico.app.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class NeedHelpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MyAccountList> list;
    String type;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;

    public NeedHelpAdapter(Context context, PaginationAdapterCallback mCallback, String type) {
        this.context = context;
        this.list = new ArrayList<>();
        this.mCallback = mCallback;
        this.type = type;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                View v1 = null;
                if (type.equals("Legal")) {
                    v1 = inflater.inflate(R.layout.row_common, parent, false);
                } else if (type.equals("Need")) {
                    v1 = inflater.inflate(R.layout.row_common, parent, false);
                }
                viewHolder = new myViewHolder(v1);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hld, int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                try {
                    final myViewHolder holder = (myViewHolder) hld;
                    if (type.equals("Legal")) {
                        holder.iv_option.setImageResource(list.get(position).getOptions_image());
                        holder.tv_option.setText(list.get(position).getOptions());
                        holder.viewCommon.setVisibility(View.GONE);
                    } else {
                        holder.iv_option.setVisibility(View.GONE);
                        holder.tv_option.setText(list.get(position).getOptions());
                        holder.viewCommon.setVisibility(View.VISIBLE);
                    }
                    break;

                } catch (Exception e) {
                }
            case LOADING:
                if (retryPageLoad) {
                } else {
                }
                break;

        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_option;
        public ImageView iv_option;
        private View viewCommon;

        public myViewHolder(View itemView) {
            super(itemView);
            tv_option = itemView.findViewById(R.id.tv_option);
            iv_option = itemView.findViewById(R.id.iv_option);
            viewCommon = itemView.findViewById(R.id.viewCommon);

        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MedicoLoading progress;
        public LoadingVH(View itemView) {
            super(itemView);
            progress = itemView.findViewById(R.id.progress);
            progress.setOnClickListener(this);
            if (isLoadingAdded) {
                progress.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.progress:
                    progress.setVisibility(View.INVISIBLE);
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }
    }

    public void add(MyAccountList results) {
        list.add(results);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<MyAccountList> moveResults) {
        for (MyAccountList result : moveResults) {
            add(result);
        }
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = list.size() - 1;
        MyAccountList result = getItem(position);
        if (result != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(list.size() - 1);
        if (errorMsg != null) this.errorMsg = errorMsg;
    }
    public List<MyAccountList> getList() {
        return list;
    }

    public MyAccountList getItem(int position) {
        return list.get(position);
    }

}