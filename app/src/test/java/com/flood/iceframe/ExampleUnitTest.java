package com.flood.iceframe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Button;

import com.flood.iceframe.app.Main2Activity;
import com.flood.iceframe.app.MainActivity;
import com.flood.iceframe.app.SlidingActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.KITKAT)
public class ExampleUnitTest {
    @Before
    public void setUp() {
        Context context = RuntimeEnvironment.application.getApplicationContext();
    }

    @Test
    public void testActivity() {
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).create().start();
        Activity activity = activityController.get();

        Button button = (Button) activity.findViewById(R.id.button);
        assertEquals("New Button", button.getText().toString());
        activityController.destroy();
        assertEquals("destory", button.getText().toString());
    }

    @Test
    public void testClick(){
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).create().start();
        Activity activity = activityController.get();
        Button button = (Button) activity.findViewById(R.id.button);
        assertNotNull(button);
        button.performClick();

        Intent expectedIntent = new Intent(activity, SlidingActivity.class);
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent, actualIntent);
    }
}