package com.example.contactssampleapp.contactslist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.contactssampleapp.models.Contact;
import com.example.contactssampleapp.models.Repository;

import java.util.List;


public class ContactListViewModel extends AndroidViewModel {

    private Repository mRepository;


    private LiveData<List<Contact>> mAllContacts;

    public void setmRepository(Repository mRepository) {
        this.mRepository = mRepository;
    }

    public ContactListViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllContacts = mRepository.getAllContacts();

    }



    public LiveData<List<Contact>> getContacts() {
        return mRepository.getAllContacts();
    }

    public void insertContact(Contact contact) {
        mRepository.insertContact(contact);
    }
}
