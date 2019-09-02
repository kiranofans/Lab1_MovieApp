package Utils;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

public class RxJavaBus {

    private static RxJavaBus rxBus;

    public static RxJavaBus getInstance(){
        //lazy init instance
        return rxBus == null ? rxBus = new RxJavaBus() : rxBus;
    }

    private RxJavaBus(){}

    private PublishSubject<String> publishSubject = PublishSubject.create();

    public void publishStr(String str){
        publishSubject.onNext(str);
    }
    public void publishImgResID(int resId){
        publishSubject.onNext(resId+"");
    }
    public Observable<String> observableListener() {
        return publishSubject;
    }


}
