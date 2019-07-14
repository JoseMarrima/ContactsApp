package com.example.contactssampleapp.models;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {

    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    public Repository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabaseInstance(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    public void insertContact(Contact contact) {
        new insertAsyncTask(mContactDao).execute(contact);
    }

    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao contactDao;

        public insertAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insertContact(contacts[0]);
            return null;
        }
    }
}
