package com.medico.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.interfaceClass.AddressAction;
import com.medico.app.interfaceClass.DeleteAddress;
import com.medico.app.response.Address.AddressResult;
import com.medico.app.utils.SessionManager;

import java.util.List;

public class MainAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<AddressResult> list;
    String type;
    private static final int ITEM = 0;
    public int SelectedPosition = 0;
    DeleteAddress deleteAddress;

    public MainAddressAdapter(Context context, List<AddressResult> list, String type, int SelectedPosition) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.SelectedPosition = SelectedPosition;
        new SessionManager(context).setAddressPosition(0);
    }

    public MainAddressAdapter(Context context, List<AddressResult> list, String type, DeleteAddress deleteAddress) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.deleteAddress = deleteAddress;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                View v1 = null;
                if (type.equals("cart")) {
                    v1 = inflater.inflate(R.layout.address_layout, parent, false);
                } else if (type.equals("manage")) {
                    v1 = inflater.inflate(R.layout.row_address_item, parent, false);
                }
                viewHolder = new myViewHolder(v1);
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
                    if (type.equals("cart")) {
                        holder.checkbox.setOnClickListener(view -> {
                            SelectedPosition = holder.getAdapterPosition();
                            new SessionManager(context).setAddressPosition(position);
                            notifyDataSetChanged();
                        });
                        if (SelectedPosition == position) {
                            holder.checkbox.setChecked(true);
                        } else {
                            holder.checkbox.setChecked(false);
                        }
                        holder.tv_user_name.setText(list.get(position).getName() + " " + list.get(position).getType().getValue());
                        holder.tv_complete_address.setText(list.get(position).getAddress1() + "\n" + list.get(position).getAddress2());
                        holder.tv_pin_code.setText(Integer.toString(list.get(position).getPincode()));
                    } else {
                        holder.tv_address_type.setText(list.get(position).getType().getValue());
                        holder.tv_user_name.setText(list.get(position).getName());
                        holder.tv_phone.setText(list.get(position).getMobile());
                        holder.tv_flat_number.setText(list.get(position).getAddress1());
                        holder.tv_street_name.setText(list.get(position).getAddress2());
                        holder.tv_landmark.setText(list.get(position).getLandmark());
                        holder.tv_city.setText(list.get(position).getState().getName() + " " + list.get(position).getPincode());
                        if (list.get(position).getType().getValue().equals("Home")) {
                            holder.iv_address_type_icon.setImageResource(R.drawable.ic_home_vector);
                        } else if (list.get(position).getType().getValue().equals("Office")) {
                            holder.iv_address_type_icon.setImageResource(R.drawable.ic_work_vector);
                        } else if (list.get(position).getType().getValue().equals("Other")) {
                            holder.iv_address_type_icon.setImageResource(R.drawable.ic_other);
                        }
                        AddressResult listNew = list.get(position);
                        holder.delete.setOnClickListener(view -> {
                            deleteAddress.editDeleteData(true, "delete", listNew, position);
                        });
                        holder.edit.setOnClickListener(view -> {
                            deleteAddress.editDeleteData(true, "edit", listNew, position);
                        });
                    }

                } catch (Exception e) {
                    Log.e("MainAddressAdapter", "" + e.getMessage());
                }
                break;

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == list.size() - 1 ? ITEM : 0;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkbox;
        public TextView tv_user_name, tv_complete_address, tv_pin_code;
        //edit address variables
        public TextView tv_address_type, tv_flat_number, tv_street_name, tv_landmark, tv_city, tv_phone, delete, edit;
        public ImageView iv_address_type_icon;

        public myViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_complete_address = itemView.findViewById(R.id.tv_complete_address);
            tv_pin_code = itemView.findViewById(R.id.tv_pin_code);

            tv_address_type = itemView.findViewById(R.id.tv_address_type);
            tv_flat_number = itemView.findViewById(R.id.tv_flat_number);
            tv_street_name = itemView.findViewById(R.id.tv_street_name);
            tv_landmark = itemView.findViewById(R.id.tv_landmark);
            tv_city = itemView.findViewById(R.id.tv_city);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
            iv_address_type_icon = itemView.findViewById(R.id.iv_address_type_icon);
        }
    }


}