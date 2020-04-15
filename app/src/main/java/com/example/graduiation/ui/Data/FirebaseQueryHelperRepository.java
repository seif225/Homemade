package com.example.graduiation.ui.Data;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.graduiation.ui.intro.IntroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FirebaseQueryHelperRepository {
    private FirebaseAuth mAuth;
    private static final String TAG = "FirebaseQueryHelper";
   // private static final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private static final DatabaseReference USER_REF = FirebaseDatabase.getInstance().getReference().child("Users");
    private static final DatabaseReference FOOD_REF = FirebaseDatabase.getInstance().getReference().child("Food");
    private Disposable uploadUserDataDisposable;
    private static FirebaseQueryHelperRepository Instance;

    public static FirebaseQueryHelperRepository getInstance() {
        if (Instance == null) {
            Instance = new FirebaseQueryHelperRepository(); }
        return Instance;
    }

    private FirebaseQueryHelperRepository() {
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
                            /*if (foodModel.getCategory().equals(category)) {*/
                            list.add(foodModel);
                            /*}*/
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

    public static void deleteItemFromCart(String uid, String id) {
        USER_REF.child(uid).child("cart").child(id).removeValue();

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
                                Toast.makeText(context, "Error " + task.getResult(),
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

                                if (dataSnapshot.child(s).exists()) {
                                    UserParentModel model = new UserParentModel();
                                    model.setEmail(dataSnapshot.child(s).child("email").getValue().toString());
                                    model.setId(dataSnapshot.child(s).child("id").getValue().toString());
                                    if (dataSnapshot.child(s).hasChild("image"))
                                        model.setImage(dataSnapshot.child(s).child("image").getValue().toString() + "");
                                    model.setName(dataSnapshot.child(s).child("name").getValue().toString());
                                    model.setPhone(dataSnapshot.child(s).child("phone").getValue().toString());
                                    users.add(model);
                                }

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

        USER_REF.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: " + dataSnapshot);
                UserParentModel model = new UserParentModel();
                model.setEmail(dataSnapshot.child("email").getValue().toString());
                model.setId(dataSnapshot.child("id").getValue().toString());
                if (dataSnapshot.hasChild("image"))
                    model.setImage(dataSnapshot.child("image").getValue().toString());
                model.setName(dataSnapshot.child("name").getValue().toString());
                model.setPhone(dataSnapshot.child("phone").getValue().toString());
                userParentModel.setValue(model);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      /*  Observable<String> observable = Observable.just(id);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {




            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.observeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(observer);*/


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
                        UserParentModel model = new UserParentModel();
                        model.setEmail(dataSnapshot.child("email").getValue().toString());
                        model.setId(dataSnapshot.child("id").getValue().toString());
                        if (dataSnapshot.hasChild("image"))
                            model.setImage(dataSnapshot.child("image").getValue().toString());
                        model.setName(dataSnapshot.child("name").getValue().toString());
                        model.setPhone(dataSnapshot.child("phone").getValue().toString());
                        if (dataSnapshot.hasChild("bio"))
                            model.setBio(dataSnapshot.child("bio").getValue().toString());
                        userParentModelMutableLiveData.setValue(model);
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


    public void isFollowed(String myId, String userId, MutableLiveData<Boolean> mutableFlag) {

        USER_REF.child(myId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("following")) {
                    if (dataSnapshot.child("following").hasChild(userId)) {
                        Log.e(TAG, "onDataChange: " + " \n \n \n the flag should be true here  \n \n \n ");
                        mutableFlag.setValue(true);

                    } else {
                        Log.e(TAG, "flag: " + "false");

                        mutableFlag.setValue(false);

                    }
                    Log.e(TAG, "onDataChange: " + "if the user has following list check");
                } else {
                    Log.e(TAG, "flag: " + "false");
                    USER_REF.child(myId).child("following").child("createFollowingList").setValue("true");
                    mutableFlag.setValue(false);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    public void follow(String myId, String userId) {
        Log.e(TAG, "follow: " + userId + myId);
        USER_REF.child(myId).child("following").child(userId).setValue("true");
        USER_REF.child(userId).child("follower").child(myId).setValue("true");


    }

    public void unFollow(String myId, String userId) {
        Log.e(TAG, "unfollow:               " + userId + myId);
        USER_REF.child(myId).child("following").child(userId).removeValue();
        USER_REF.child(userId).child("follower").child(myId).removeValue();
    }

    public void addItemToCart(Context context, String uid, FoodModel model) {

        USER_REF.child(uid).child("cart").child(model.getId()).setValue(model);
        USER_REF.child(uid).child("cart").child(model.getId()).child("quantity").setValue("1");
        Toast.makeText(context, "this item has been  added to cart", Toast.LENGTH_SHORT).show();


    }



    public void getCartItems(String uid, MutableLiveData<ArrayList<FoodModel>> listMutableLiveData) {

        ArrayList<FoodModel> listOfFoodModel  = new ArrayList<>();
        USER_REF.child(uid).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    FoodModel foodModel = new FoodModel();
                    foodModel.setCategory(dataSnapshot1.child("category").getValue().toString());
                    foodModel.setCookId(dataSnapshot1.child("cookId").getValue().toString());
                    foodModel.setDescribtion(dataSnapshot1.child("describtion").getValue().toString());
                    foodModel.setId(dataSnapshot1.child("id").getValue().toString());
                    foodModel.setMax(dataSnapshot1.child("max").getValue().toString());
                    foodModel.setMin(dataSnapshot1.child("min").getValue().toString());
                    foodModel.setPrice(dataSnapshot1.child("price").getValue().toString());
                    foodModel.setThumbnail(dataSnapshot1.child("thumbnail").getValue().toString());
                    foodModel.setTitle(dataSnapshot1.child("title").getValue().toString());
                    foodModel.setQuantity(dataSnapshot1.child("quantity").getValue().toString());
                    listOfFoodModel.add(foodModel);
                }

                listMutableLiveData.setValue(listOfFoodModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void getNumberOfFollowers(MutableLiveData<Integer> numberOfFollowers, String id) {
        USER_REF.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("follower")){

                    numberOfFollowers.setValue((int)dataSnapshot.child("follower").getChildrenCount());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void addCartToOrders(HashMap<String, OrderModel> orderModelHashSet,String orderId) {
        //remove order from cart
        USER_REF.child(FirebaseAuth.getInstance().getUid()).child("cart").removeValue();
        // add the order in the buyer's database
        USER_REF.child(FirebaseAuth.getInstance().getUid()).child("ordersSent").child(orderId).setValue(orderModelHashSet);
        //add the order in each seller database
        for (String s:orderModelHashSet.keySet()) {
            USER_REF.child(s).child("ordersReceived").child(orderId).setValue(orderModelHashSet.get(s));
        }



    }


}
