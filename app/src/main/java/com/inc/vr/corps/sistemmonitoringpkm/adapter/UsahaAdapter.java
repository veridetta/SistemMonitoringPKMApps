package com.inc.vr.corps.sistemmonitoringpkm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.inc.vr.corps.sistemmonitoringpkm.R;
import com.inc.vr.corps.sistemmonitoringpkm.model.KegiatanResponse;
import com.inc.vr.corps.sistemmonitoringpkm.model.UsahaResponse;

import java.util.ArrayList;
import java.util.List;

public class UsahaAdapter extends RecyclerView.Adapter<UsahaAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<UsahaResponse.DataResult> contactList;
    private List<UsahaResponse.DataResult> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTanggal, tvNama, tvMinat, tvHP;

        public MyViewHolder(View view) {
            super(view);
            tvTanggal = view.findViewById(R.id.tv_tanggal);
            tvNama = view.findViewById(R.id.tv_nama);
            tvMinat = view.findViewById(R.id.tv_minat);
            tvHP = view.findViewById(R.id.tv_hp);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public UsahaAdapter(Context context, List<UsahaResponse.DataResult> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usaha, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final UsahaResponse.DataResult contact = contactListFiltered.get(position);
        holder.tvTanggal.setText(contact.getTanggal());
        holder.tvNama.setText("- "+contact.getName());
        holder.tvMinat.setText(contact.getMinat());
        holder.tvHP.setText("- "+contact.getNo_hp());

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<UsahaResponse.DataResult> filteredList = new ArrayList<>();
                    for (UsahaResponse.DataResult row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<UsahaResponse.DataResult>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(UsahaResponse.DataResult contact);
    }
}
