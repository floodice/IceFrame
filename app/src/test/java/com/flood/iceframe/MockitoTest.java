package com.flood.iceframe;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-25 14:05
 */
public class MockitoTest {
    @Before
    public void setUp(){

    }

    @Test
    public void MocksTest(){
        TestSubscriber<Mock> testSubscriber = new TestSubscriber<>();
        getMocks().subscribe(testSubscriber);
        assertThat(testSubscriber.getOnNextEvents().size(), is(3));
    }

    @Test
    public void ListMocksTest(){
        TestSubscriber<Mock> testSubscriber = new TestSubscriber<>();
        getListMocks().flatMap(new Func1<List<Mock>, Observable<Mock>>() {
            @Override
            public Observable<Mock> call(List<Mock> mocks) {
                return Observable.from(mocks);
            }
        }).subscribe(testSubscriber);

        assertThat(testSubscriber.getOnNextEvents().size(), is(3));
    }

    private Observable<Mock> getMocks(){
        return Observable.just(new Mock(),new Mock(), new Mock());
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
