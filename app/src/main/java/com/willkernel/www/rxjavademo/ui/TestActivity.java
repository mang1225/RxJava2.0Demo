package com.willkernel.www.rxjavademo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.willkernel.www.rxjavademo.R;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

  String TAG = "WWWW";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    testIterable();
  }

  public void testIterable() {
    ArrayList<String> arrayList = new ArrayList<String>() {
      {
        add("str01");
        add("str02");
        add("str03");
        add("str04");
        add("str05");
        add("05432");
        add("str06");
        add("1234567");
      }
    };
    Observable.fromIterable(arrayList)
        .filter(new Predicate<String>() {
          @Override
          public boolean test(@NonNull String s) throws Exception {
            return s.startsWith("str");
          }
        })
        .filter(new Predicate<String>() {
          @Override
          public boolean test(@NonNull String s) throws Exception {
            return s.length() == 5;
          }
        }).subscribe(new Consumer<String>() {
      @Override
      public void accept(@NonNull String out) throws Exception {
        Log.d("WWW", "----->" + out);
      }
    });
  }


  //mapper function returned a null value
  public void testObserveable() {
    Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
        Log.e(TAG, "start emitter data");
//        e.onNext(null);
//        e.onNext("world");
//        e.onComplete();
      }
    }).subscribe(new Observer<String>() {

      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Log.e(TAG, "onSubscribe");
      }

      @Override
      public void onNext(@NonNull String o) {
        Log.e(TAG, "onNext" + o);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        Log.e(TAG, "onError");
      }

      @Override
      public void onComplete() {
        Log.e(TAG, "onComplete");
      }
    });

 /*   Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
        Log.e(TAG, "start emitter data");
        e.onNext("");
        e.onNext("world");
        e.onComplete();
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    observable.subscribe(new Observer<String>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        // onSubscribe 是2.x新添加的方法，在发射数据前被调用，相当于1.x的onStart方法
        Log.e(TAG, "onSubscribe");
      }

      @Override
      public void onNext(@NonNull String s) {
        Log.e(TAG, "onNext--->" + s);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        Log.e(TAG, "onError");
      }

      @Override
      public void onComplete() {
        Log.e(TAG, "onComplete");
      }
    });*/

    // 订阅方式二：Consumer
//    observable.subscribe(new Consumer<String>() {
//      @Override
//      public void accept(@NonNull String o) throws Exception {
//        Log.e(TAG, "consumer:" + o);
//      }
//    });
  }

  public void testMaybe() {
    Maybe.create(new MaybeOnSubscribe<Boolean>() {
      @Override
      public void subscribe(@NonNull MaybeEmitter<Boolean> e) throws Exception {
        Log.e(TAG, "start send data");
        e.onSuccess(null);
        e.onComplete();
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MaybeObserver<Boolean>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Log.e(TAG, "onSubscribe");
      }

      @Override
      public void onSuccess(@NonNull Boolean aBoolean) {
        Log.e(TAG, "1->onSuccess:" + aBoolean);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        Log.e(TAG, "onError");
      }

      @Override
      public void onComplete() {
        Log.e(TAG, "onComplete");
      }
    });
  }
}
