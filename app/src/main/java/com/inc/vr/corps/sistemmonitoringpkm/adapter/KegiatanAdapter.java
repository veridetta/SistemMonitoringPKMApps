package com.inc.vr.corps.sistemmonitoringpkm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.inc.vr.corps.sistemmonitoringpkm.R;
import com.inc.vr.corps.sistemmonitoringpkm.model.KegiatanResponse;
import com.inc.vr.corps.sistemmonitoringpkm.ui.kegiatan.Tambah_Kegiatan_PKM;
import com.inc.vr.corps.sistemmonitoringpkm.ui.progress.TambahProgress;

import java.util.ArrayList;
import java.util.List;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<KegiatanResponse.DataResult> contactList;
    private List<KegiatanResponse.DataResult> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJam, tvHariTanggal, tvJenis;
        public Button btnUbah, btnProgres;
        public MyViewHolder(View view) {
            super(view);
            tvJam = view.findViewById(R.id.tv_jam);
            tvHariTanggal = view.findViewById(R.id.tv_hari_tanggal);
            tvJenis = view.findViewById(R.id.tv_jenis_kegiatan);
            btnUbah = view.findViewById(R.id.btn_ubah);
            btnProgres = view.findViewById(R.id.btn_progres);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public KegiatanAdapter(Context context, List<KegiatanResponse.DataResult> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kegiatan, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final KegiatanResponse.DataResult contact = contactListFiltered.get(position);
        holder.tvJenis.setText(contact.getJenis());
        holder.tvJam.setText(contact.getWaktu());
        holder.tvHariTanggal.setText(contact.getHari()+", "+contact.getTanggal());
        holder.btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Tambah_Kegiatan_PKM.class);
                intent.putExtra("id", contact.getId());
                intent.putExtra("jenis", contact.getJenis());
                intent.putExtra("tanggal", contact.getTanggal());
                intent.putExtra("hari", contact.getHari());
                intent.putExtra("waktu", contact.getWaktu());
                intent.putExtra("ubah",true);
                context.startActivity(intent);
            }
        });
        holder.btnProgres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TambahProgress.class);
                intent.putExtra("id", contact.getId());
                intent.putExtra("namaKegiatan", contact.getJenis());
                intent.putExtra("ubah",true);
                context.startActivity(intent);
            }
        });
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
                    List<KegiatanResponse.DataResult> filteredList = new ArrayList<>();
                    for (KegiatanResponse.DataResult row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getJenis().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getJenis().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<KegiatanResponse.DataResult>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(KegiatanResponse.DataResult contact);
    }
}
