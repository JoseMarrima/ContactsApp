package com.example.contactssampleapp.contactslist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
//import androidx.databinding.DataBindingUtil;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.contactssampleapp.R;
//import com.example.contactssampleapp.databinding.ContactsItemLayoutBinding;
import com.example.contactssampleapp.databinding.ContactsItemLayoutBinding;
import com.example.contactssampleapp.models.Contact;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder>
implements Filterable {

    private final Context context;
    private List<Contact> contacts;

    private static final String TAG = "ContactListAdapter";
    private List<Contact> contactListFiltered;

    public ContactListAdapter(Context context) {
        this.context = context;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        this.contactListFiltered = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ContactsItemLayoutBinding itemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.contacts_item_layout, parent, false);

        return new MyViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Contact contactCurrent = contactListFiltered.get(position);
        holder.itemLayoutBinding.setContact(contactCurrent);

        holder.itemLayoutBinding.getRoot().findViewById(R.id.imageButton_call).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uri = "tel:" + contactCurrent.getPhone_number();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(uri));
                        context.startActivity(intent);

                    }
                }
        );

        holder.itemLayoutBinding.getRoot().findViewById(R.id.imageButton_email).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String to = contactCurrent.getEmail();
//
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});

                        email.setType("message/rfc822");

                        context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        if (contactListFiltered != null) {
            return contactListFiltered.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ContactsItemLayoutBinding itemLayoutBinding;

        public MyViewHolder(ContactsItemLayoutBinding itemLayoutBinding){
            super(itemLayoutBinding.getRoot());
            this.itemLayoutBinding = itemLayoutBinding;
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contacts;
                } else {
                    List<Contact> filteredList = new ArrayList<>();
                    for (Contact row : contacts) {

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getEmail().toLowerCase().contains(charString.toLowerCase())){
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
                contactListFiltered = (ArrayList<Contact>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

//    public interface ContactsAdapterListener {
//        void onContactSelected(Contact contact);
//    }
}
