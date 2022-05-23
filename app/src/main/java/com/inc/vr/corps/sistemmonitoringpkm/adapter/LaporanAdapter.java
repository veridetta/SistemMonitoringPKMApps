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
import com.inc.vr.corps.sistemmonitoringpkm.model.LaporanResponse;
import com.inc.vr.corps.sistemmonitoringpkm.model.UsahaResponse;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<LaporanResponse.DataResult> contactList;
    private List<LaporanResponse.DataResult> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTanggal, tvKategori, tvAlamat, tvPenerimaan, tvPengeluaran, tvSaldo;

        public MyViewHolder(View view) {
            super(view);
            tvTanggal = view.findViewById(R.id.tv_tanggal);
            tvKategori = view.findViewById(R.id.tv_kategori);
            tvAlamat = view.findViewById(R.id.tv_alamat);
            tvPenerimaan = view.findViewById(R.id.tv_penerimaan);
            tvPengeluaran = view.findViewById(R.id.tv_pengeluaran);
            tvSaldo = view.findViewById(R.id.tv_saldo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public LaporanAdapter(Context context, List<LaporanResponse.DataResult> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_laporan, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final LaporanResponse.DataResult contact = contactListFiltered.get(position);
        holder.tvTanggal.setText(contact.getTipe()+" "+contact.getNilai());
        holder.tvKategori.setText(contact.getKategori());
        holder.tvAlamat.setText(contact.getAlamat());
        holder.tvPenerimaan.setText(rupiah(Double.parseDouble(contact.getTotalPemasukan())));
        holder.tvPengeluaran.setText(rupiah(Double.parseDouble(contact.getTotalPengeluaran())));
        holder.tvSaldo.setText(rupiah(Double.parseDouble(contact.getTotalSaldo())));

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
                    List<LaporanResponse.DataResult> filteredList = new ArrayList<>();
                    for (LaporanResponse.DataResult row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getKategori().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getKategori().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<LaporanResponse.DataResult>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(LaporanResponse.DataResult contact);
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
