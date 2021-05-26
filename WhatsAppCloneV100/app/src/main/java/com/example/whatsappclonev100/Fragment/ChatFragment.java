package com.example.whatsappclonev100.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclonev100.Adapter.UsersAdapter;
import com.example.whatsappclonev100.AllContactsActivity;
import com.example.whatsappclonev100.Model.InfoModel;
import com.example.whatsappclonev100.Model.PhoneUsers;
import com.example.whatsappclonev100.Model.Users;
import com.example.whatsappclonev100.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatFragment extends Fragment {


    FirebaseFirestore firestore;
    ArrayList<InfoModel> list = new ArrayList<>();
    RecyclerView chatrecycler;
    UsersAdapter usersAdapter;
    boolean flag =true;
    private static ArrayList<PhoneUsers> allContacts = new ArrayList<PhoneUsers>();
    ArrayList<PhoneUsers> allContactsFinal=new ArrayList<PhoneUsers>();


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_chat, container, false);
        ImageView fab=root.findViewById(R.id.fab);
        firestore=FirebaseFirestore.getInstance();
        chatrecycler = root.findViewById(R.id.chatrecycler);
        checkPermission();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        chatrecycler.setLayoutManager(layoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllContactsActivity.class);
                startActivity(intent);
            }
        });

        firestore.collection("Chats").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                list.clear();
                for(DocumentSnapshot snapshot:value.getDocuments()){
                    if(snapshot.getId().substring(0,28).equals(FirebaseAuth.getInstance().getUid())){
                        InfoModel infoModel = snapshot.toObject(InfoModel.class);

                        if(!infoModel.getRecieverId().equals(FirebaseAuth.getInstance().getUid()))
                        {
                            list.add(infoModel);
                        }
                        for(int i=0;i<allContactsFinal.size();i++){
                            String listnumber = list.get(list.size()-1).getMobileNo().replace("+91","");
                            String allcontactnumber = allContactsFinal.get(i).getUserMobileNo().replace("+91","");
                            Log.i("numberssss",listnumber+"  "+allcontactnumber);
                            if(listnumber.equals(allcontactnumber)){
                                infoModel.setInContact(false);
                            }
                            Log.i("hello",list.get(list.size()-1).getMobileNo()+"   "+allContactsFinal.get(i).getUserMobileNo()+"   "+infoModel.isInContact());

                        }

                        usersAdapter  = new UsersAdapter(list, getContext(),flag);
                        chatrecycler.setAdapter(usersAdapter);
                        usersAdapter.notifyDataSetChanged();


                    }

                }


            }
        });



        return root;
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},100);
        }else{
            getContactList();
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getContactList();
        }else{
            Toast.makeText(getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
            checkPermission();
        }
    }

    private void getContactList() {
        allContacts.clear();
        allContactsFinal.clear();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, sort);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";
                Cursor phoneCursor = getActivity().getContentResolver().query(uriPhone, null, selection, new String[]{id}, null);
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

                                }

                            }
                        }
                    });

                    phoneCursor.close();
                }

            }
            cursor.close();}


    }
}