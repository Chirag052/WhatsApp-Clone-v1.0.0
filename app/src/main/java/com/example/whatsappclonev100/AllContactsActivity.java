package com.example.whatsappclonev100;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclonev100.Adapter.AllContactsAdapter;
import com.example.whatsappclonev100.Model.PhoneUsers;
import com.example.whatsappclonev100.Model.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllContactsActivity extends AppCompatActivity {

    RecyclerView allContactsRecyclerView;
    AllContactsAdapter allContactsAdapter;
    ProgressBar loader;
    private static ArrayList<PhoneUsers> allContacts = new ArrayList<PhoneUsers>();
    Toolbar toolbar;
    LinearLayout linearLayout;
    TextView totalContacts;
    FirebaseFirestore firestore;
    PhoneUsers phoneusers;
    private static ArrayList<PhoneUsers> allContactsFinal=new ArrayList<>();
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        allContactsRecyclerView=findViewById(R.id.all_contacts_recycler_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loader=findViewById(R.id.loader);
        linearLayout=(LinearLayout)findViewById(R.id.newContact);
        totalContacts=findViewById(R.id.totalContacts);
        firestore=FirebaseFirestore.getInstance();
        checkPermission();

        ImageView backarrow=findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case (R.id.inviteFriend):
                Toast.makeText(this, "Invite Successfully Send!", Toast.LENGTH_SHORT).show();

                break;
            case (R.id.contacts):
            {
                Intent intent= new Intent(Intent.ACTION_VIEW,  ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(intent, 33);

                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AllContactsActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},100);
        }else{
            getContactList();
        }
    }

    private void getContactList() {
        allContacts.clear();
        allContactsFinal.clear();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = getContentResolver().query(uri, null, null, null, sort);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";
                Cursor phoneCursor = getContentResolver().query(uriPhone, null, selection, new String[]{id}, null);
                if (phoneCursor.moveToNext()) {
                    String number = phoneCursor.getString(phoneCursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    PhoneUsers phoneUsers = new PhoneUsers();
                    firestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable  QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for(DocumentSnapshot documentSnapshot: value.getDocuments()){



                                Users users = documentSnapshot.toObject(Users.class);
                                String data = users.getUserMobileNo();
                                if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                                {
                                    if(number.contains("+91"))
                                    {
                                        if(data.contains(number.replaceAll("\\s","")))
                                        {
                                            phoneUsers.setUserId(users.getUserId());
                                            phoneUsers.setUserName(users.getUserName());
                                            phoneUsers.setStatus(users.getStatus());
                                            phoneUsers.setUserMobileNo(data);
                                            allContactsFinal.add(phoneUsers);

                                        }
                                    }
                                    else
                                    {
                                        if(data.contains("+91"+number.replaceAll("\\s","")))
                                        {
                                            phoneUsers.setUserId(users.getUserId());
                                            phoneUsers.setUserName(users.getUserName());
                                            phoneUsers.setStatus(users.getStatus());
                                            phoneUsers.setUserMobileNo("+91"+data);
                                            allContactsFinal.add(phoneUsers);


                                        }

                                    }

                                    count++;

                                }

                                allContactsAdapter.notifyDataSetChanged();

                            }
                            totalContacts.setText(String.valueOf(allContactsFinal.size()));


                        }
                    });


                    phoneCursor.close();
                }

            }
            cursor.close();}

        loader.setVisibility(View.GONE);
        allContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allContactsAdapter = new AllContactsAdapter(this,allContactsFinal);
        allContactsRecyclerView.setAdapter(allContactsAdapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getContactList();
        }else{
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            checkPermission();
        }
    }

}