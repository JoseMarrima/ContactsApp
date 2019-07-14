package com.example.contactssampleapp.contactslist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
//import androidx.databinding.DataBindingUtil;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contactssampleapp.R;
import com.example.contactssampleapp.addcontact.AddContactActivity;
//import com.example.contactssampleapp.databinding.ActivityContactListBinding;
import com.example.contactssampleapp.databinding.ActivityContactListBinding;
import com.example.contactssampleapp.models.Contact;
import com.example.contactssampleapp.models.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class ContactListActivity extends AppCompatActivity {

    private List<Contact> contacts = new ArrayList<>();
    private ContactListAdapter contactListAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private ContactListViewModel mContactListViewModel;
    private Repository mRepository;

    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "ContactListActivity";
    private SearchView searchView;

    private ActivityContactListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = new Repository(getApplication());
        mContactListViewModel = ViewModelProviders.of(this).get(ContactListViewModel.class);
        mContactListViewModel.setmRepository(mRepository);

        // Set title of the action bar
        getSupportActionBar().setTitle(R.string.toolbar_title);


        binding = DataBindingUtil.setContentView(this,R.layout.activity_contact_list);

        fab = findViewById(R.id.fab);

        binding.recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewContacts.setHasFixedSize(true);
        contactListAdapter = new ContactListAdapter(this);
        binding.recyclerViewContacts.setAdapter(contactListAdapter);

        observeListOfContacts();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            }
        });

    }


    private void observeListOfContacts() {
        mContactListViewModel.getContacts().observe(this, contacts ->
                contactListAdapter.setContacts(contacts));
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Contact contact = new Contact(data.getStringExtra(AddContactActivity.EXTRA_NAME),
                                            data.getStringExtra(AddContactActivity.EXTRA_EMAIL),
                                            data.getIntExtra(AddContactActivity.EXTRA_PHONE, 0),
                                            data.getStringExtra(AddContactActivity.EXTRA_IMAGE));
            Log.i(TAG, "onActivityResult: " + contact.getImage());
            mContactListViewModel.insertContact(contact);
        } else {
            Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setMaxWidth(Integer.MAX_VALUE);

        MenuItem search = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);

        search(searchView);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                // When query is submitted filter the recycler view
                contactListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                contactListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
