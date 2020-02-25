package com.example.graduiation.ui.Data;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.graduiation.ui.MyApplication;
import com.example.graduiation.ui.intro.IntroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FirebaseQueryHelper {
    private FirebaseAuth mAuth;
    private static final String TAG = "FirebaseQueryHelper";
    private static final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private static final DatabaseReference USER_REF = mDatabase.getReference().child("Users");
    private static final DatabaseReference FOOD_REF = mDatabase.getReference().child("Food");
    private Disposable uploadUserDataDisposable;

    public FirebaseQueryHelper() {


        mAuth = FirebaseAuth.getInstance();
        USER_REF.keepSynced(true);
        FOOD_REF.keepSynced(true);

    }

    public static void getListOfFood(MutableLiveData<ArrayList<FoodModel>> listMutableLiveData, String uid, String category) {
        Observable<String> observable = Observable.just(uid);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                FOOD_REF.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<FoodModel> list = new ArrayList<>();
                        for (DataSnapshot datasnapshot1 : dataSnapshot.getChildren()) {
                                FoodModel foodModel = datasnapshot1.getValue(FoodModel.class);
                                if (foodModel.getCategory().equals(category)) {
                                    list.add(foodModel);
                            }
                        }
                        listMutableLiveData.setValue(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.observeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(observer);

    }

    public void getListOfFoodAndUsers(MutableLiveData<ArrayList<FoodModel>> foodArrayListMutableLiveData, HashSet<String> cookIds, String category) {

        ArrayList<FoodModel> foodModelArrayList = new ArrayList<>();

        Observable<ArrayList<FoodModel>> observable = Observable.just(foodModelArrayList);
        Observer<ArrayList<FoodModel>> observer = new Observer<ArrayList<FoodModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList<FoodModel> foodModels) {

                FOOD_REF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FoodModel foodModel;
                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                                foodModel = dataSnapshot1.getValue(FoodModel.class);
                                assert foodModel != null;
                                if (foodModel.getCategory().equals(category)) {
                                    foodModelArrayList.add(foodModel);
                                    //Log.e(TAG, "onDataChange: "+dataSnapshot1 );
                                    cookIds.add(dataSnapshot1.child("cookId").getValue().toString());
                                }
                            }
                            foodArrayListMutableLiveData.setValue(foodModelArrayList);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(observer);

    }


    public void SignUp(String email, String password, String confirmPassword, ProgressDialog pB,
                       String phoneNum, String name) throws IllegalArgumentException {
        pB.setTitle("Please Wait");
        pB.show();
        if (password.length() < 6) {
            Log.i(TAG, "SignUp: " + "1");
            pB.dismiss();
            throw new IllegalArgumentException("1");
        } else if (!password.equals(confirmPassword)) {
            Log.i(TAG, "SignUp: " + "2");
            pB.dismiss();
            throw new IllegalArgumentException("2");
        } else if (phoneNum.length() < 11) {
            Log.i(TAG, "SignUp: " + "3");
            pB.dismiss();
            throw new IllegalArgumentException("3");
        } else if (name.isEmpty()) {
            Log.i(TAG, "SignUp: " + "4");
            pB.dismiss();

            throw new IllegalArgumentException("4");
        } else {
            Log.i(TAG, "SignUp: " + "1");
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                    (new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pB.dismiss();
                                sendUsersDataToDatabase(name, email, password, phoneNum);
                            } else {
                                pB.dismiss();

                            }
                        }
                    });
        }

    }

    public Disposable getUploadUserDataDisposable() {
        return uploadUserDataDisposable;
    }

    private void sendUsersDataToDatabase(String name, String email, String password,
                                         String phoneNum) {
        UserParentModel model = new UserParentModel();
        String id = mAuth.getUid();
        if (id != null) {
            model.setId(id);
            model.setName(name);
            model.setPhone(phoneNum);
            model.setEmail(email);
            model.setPassword(password);
            io.reactivex.Observable<UserParentModel> observable = Observable.just(model);
            Observer observer = new Observer() {
                @Override
                public void onSubscribe(Disposable d) {
                    uploadUserDataDisposable = d;
                }

                @Override
                public void onNext(Object o) {
                    USER_REF.child(id).setValue(o);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };

            observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                    subscribe(observer);
        } else {
            //TODO(1): Handle this Exception if the user id is null
        }
    }

    public void SignIn(String email, String password, Context context,
                       ProgressDialog progressDialog) {
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Signing you in");
        progressDialog.show();
        if (email.isEmpty()) {
            Toast.makeText(context, "Enter a valid email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(context, "Enter a valid password", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener
                    (new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                SendUserToIntro(context);
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(context, "Error " + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        }
                    });
        }

    }

    private void SendUserToIntro(Context context) {
        Intent intro = new Intent(context, IntroActivity.class);
        intro.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intro);

    }


    //this methods uploads the users profile picture and updates his JSON tree on the database
    public void uploadUserPic(Uri uri, String uId, ProgressDialog pd, Context context,
                              UserParentModel userParentModel) {

        pd.setCancelable(false);
        pd.setTitle("please wait .. ");
        pd.show();
        final String imageName = uId + ".jpg";
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Users").child(imageName);

        storageReference.putFile(uri)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {


                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                        pd.setMessage(progress + "%" + " uploaded");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                               pd.dismiss();
                                               if (task.isSuccessful()) {
                                                   // path = task.getResult().getStorage().getPath();
                                               } else {
                                                   Log.d("Failure:", "upload Failed " + task.getException().getLocalizedMessage());
                                                   Toast.makeText(context, "Uplaod Failed :( " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                               }
                                           }
                                       }

                )
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e("PhotoUploadFirebaseQueryHelper", uri + "");
                                String link = uri.toString();
                                userParentModel.setImage(link);
                                USER_REF.child(uId).setValue(userParentModel);

                            }
                        });
                    }
                });
    }


    public void uploadFoodDataToFirebase(FoodModel model, Context context, Uri photo, ProgressDialog pd) {

        pd.setCancelable(false);
        pd.setTitle("please wait .. ");
        pd.show();
        final String imageName = UUID.randomUUID().toString() + ".jpg";

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child("Food").child(model.getCookId()).child(model.getId()).child(imageName).putFile(photo)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {


                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                        pd.setMessage(progress + "%" + " uploaded");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                               pd.dismiss();
                                               if (task.isSuccessful()) {
                                                   // path = task.getResult().getStorage().getPath();
                                               } else {
                                                   Log.d("Failure:", "upload Failed " + task.getException().getLocalizedMessage());
                                                   Toast.makeText(context, "Uplaod Failed :( " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                               }
                                           }
                                       }

                )
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.child("Food").child(model.getCookId()).child(model.getId()).child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e("PhotoUploadFirebaseQueryHelper", uri + "");
                                String link = uri.toString();
                                model.setThumbnail(link);
                                uploadFoodDataToRealTimeDataBase(model, context);
                            }
                        });
                    }
                });


    }

    private void uploadFoodDataToRealTimeDataBase(FoodModel model, Context context) {
        FOOD_REF.child(model.getCookId()).child(model.getId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "your meal has been added successfully", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onComplete: " + "done");
                } else {
                    Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onComplete: " + "ERROOOOOOOOOR");
                }
            }
        });
    }

    public void getUsersData(MutableLiveData<ArrayList<UserParentModel>> usersLiveData, HashSet<String> cookIds) {
        ArrayList<UserParentModel> users = new ArrayList<>();

        Observable<HashSet<String>> observable = Observable.just(cookIds);
        Observer<HashSet<String>> observer = new Observer<HashSet<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HashSet<String> strings) {
                USER_REF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (strings.size() > 0) {
                            for (String s : strings) {

                                if (dataSnapshot.child(s).exists())
                                    users.add(dataSnapshot.child(s).getValue(UserParentModel.class));

                            }
                        }
                        usersLiveData.setValue(users);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(observer);
    }

    public void addUserToMutableLiveData(String id, MutableLiveData<UserParentModel> userParentModel) {

        Observable<String> observable = Observable.just(id);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                USER_REF.child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e(TAG, "onDataChange: " + dataSnapshot);
                        userParentModel.setValue(dataSnapshot.getValue(UserParentModel.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.observeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(observer);


    }

    public void getSingleUserData(MutableLiveData<UserParentModel> userParentModelMutableLiveData, String uid) {

        Observable<String> observable = Observable.just(uid);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

                USER_REF.child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userParentModelMutableLiveData.setValue(dataSnapshot.getValue(UserParentModel.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(observer);

    }
}
