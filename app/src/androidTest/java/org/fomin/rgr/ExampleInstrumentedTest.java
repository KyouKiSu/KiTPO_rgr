package org.fomin.rgr;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.fomin.rgr.types.UserInteger;
import org.fomin.rgr.types.UserType;
import org.fomin.rgr.vtree.VTree;
import org.fomin.rgr.vtree.VTreeFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("org.fomin.rgr", appContext.getPackageName());
    }
    @Test
    public void testTree(){
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();

        int totalSize=500_000;
        int stepSize=50_000;
        long startTime = System.currentTimeMillis();
        System.out.print(totalSize+";");
        for (int i = 0; i<totalSize; i++){
            tree.Insert(new UserInteger(Integer.valueOf(new Random().nextInt())));
            if(i!=0&&i%stepSize==0){
                System.out.print(System.currentTimeMillis()-startTime+";");
            }
        }
    }
}