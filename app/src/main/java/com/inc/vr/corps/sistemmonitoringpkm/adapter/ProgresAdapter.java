package com.inc.vr.corps.sistemmonitoringpkm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.inc.vr.corps.sistemmonitoringpkm.R;
import com.inc.vr.corps.sistemmonitoringpkm.model.ProgresResponse;
import com.inc.vr.corps.sistemmonitoringpkm.model.ProgresResponse;
import com.inc.vr.corps.sistemmonitoringpkm.ui.progress.TambahProgress;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class ProgresAdapter extends RecyclerView.Adapter<ProgresAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<ProgresResponse.DataResult> contactList;
    private List<ProgresResponse.DataResult> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama, tvMinat, tvAlamat, tvHP,tvPendapatan;
        public Spinner spStatus;
        public Button btnUbah;

        public MyViewHolder(View view) {
            super(view);
            tvNama = view.findViewById(R.id.tv_nama);
            tvMinat = view.findViewById(R.id.tv_minat);
            tvAlamat = view.findViewById(R.id.tv_alamat);
            tvHP = view.findViewById(R.id.tv_hp);
            tvPendapatan = view.findViewById(R.id.tv_pendapatan);
            spStatus = view.findViewById(R.id.sp_status);
            btnUbah = view.findViewById(R.id.btn_ubah);

        }
    }


    public ProgresAdapter(Context context, List<ProgresResponse.DataResult> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_progres, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ProgresResponse.DataResult contact = contactListFiltered.get(position);
        holder.tvNama.setText(contact.getName());
        holder.tvMinat.setText(contact.getJenis_usaha());
        holder.tvAlamat.setText(contact.getAlamat());
        holder.tvHP.setText(contact.getNo_hp());
        holder.tvPendapatan.setText(rupiah(Double.parseDouble(contact.getPendapatan())));
        //set spstatus adapter
        String[] status = {"Belum","Progres","Selesai"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, status);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spStatus.setAdapter(adapter);
        if (contact.getLaporan().equals("Belum")) {
            holder.spStatus.setSelection(0);
        } else if (contact.getLaporan().equals("Progres")) {
            holder.spStatus.setSelection(1);
        } else if (contact.getLaporan().equals("Selesai")) {
            holder.spStatus.setSelection(2);
            holder.btnUbah.setVisibility(View.GONE);
            holder.spStatus.setEnabled(false);
        }
        holder.btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TambahProgress.class);
                intent.putExtra("id", contact.getId());
                intent.putExtra("namaKegiatan", contact.getJenis_usaha());
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
                    List<ProgresResponse.DataResult> filteredList = new ArrayList<>();
                    for (ProgresResponse.DataResult row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getJenis_usaha().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getJenis_usaha().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<ProgresResponse.DataResult>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(ProgresResponse.DataResult contact);
    }
    String rupiah(double harga){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(harga);
    }
}
