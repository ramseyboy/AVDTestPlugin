package com.demo.sample;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class SampleTest {

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @After
    public void tearDown() throws Exception {
        context = null;
    }

    @Test
    public void injected_context_should_be_available_to_test() throws Exception {
        assertNotNull(context);
    }

    @Test
    public void given_null_null_should_be_null_when_null_is_null() throws Exception {
        assertNull(null);
    }
}
