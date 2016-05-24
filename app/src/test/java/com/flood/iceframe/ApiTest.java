package com.flood.iceframe;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-25 14:44
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.KITKAT)
public class ApiTest {
    @Before
    public void setUp(){

    }

    @Test
    public void mocksTestsList(){
        TestSubscriber<Mock> testSubscriber = new TestSubscriber<>();
        getListMocks().flatMap(new Func1<List<Mock>, Observable<Mock>>() {
            @Override
            public Observable<Mock> call(List<Mock> mocks) {
                return Observable.from(mocks);
            }
        }).subscribe(testSubscriber);
        assertThat(testSubscriber.getOnNextEvents().size(), is(3));
//        getMocks().subscribe(testSubscriber);
    }

    @Test
    @JSpec(desc = "mockTestsdfsdfds")
    public void mocksTest(){
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations){
            if (annotation instanceof JSpec){
                String a = ((JSpec)annotation).desc();
                System.out.print(a);
            }
        }
        TestSubscriber<Mock> testSubscriber = new TestSubscriber<>();
        getMocks().subscribe(testSubscriber);
        assertThat(testSubscriber.getOnNextEvents().size(), is(4));


    }

    private Observable<Mock> getMocks(){
        return Observable.just(new Mock(), new Mock(), new Mock());
    }

    private Observable<List<Mock>> getListMocks(){
        List<Mock> list = new ArrayList<>();
        list.add(new Mock());
        list.add(new Mock());
        list.add(new Mock());

        return Observable.just(list);
    }

    class Mock{

    }
}
