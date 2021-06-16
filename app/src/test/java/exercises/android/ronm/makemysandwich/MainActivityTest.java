package exercises.android.ronm.makemysandwich;


import android.widget.CheckBox;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class MainActivityTest
{

    private ActivityController<MainActivity> activityController;

    @Before
    public void setup(){
        activityController = Robolectric.buildActivity(MainActivity.class);
    }

    @Test
    public void whenNoOrder_then_checkBoxHummus_shouldBeUnchecked(){
        activityController.create().visible();
        MainActivity activityUnderTest = activityController.get();
        CheckBox checkBoxHummus = activityUnderTest.findViewById(R.id.checkBoxHummus);
        assertFalse(checkBoxHummus.isChecked());
    }

    @Test
    public void whenClickingOnCheckBox_then_checkBoxShouldBeChecked(){
        activityController.create().visible();
        MainActivity activityUnderTest = activityController.get();
        CheckBox checkBoxTahini = activityUnderTest.findViewById(R.id.checkBoxTahini);
        assertFalse(checkBoxTahini.isChecked());
        checkBoxTahini.performClick();
        assertTrue(checkBoxTahini.isChecked());
    }

    @Test
    public void whenClickingOnCreateOrderButton_then_orderShouldBeCreated(){
        activityController.create().visible();
        MainActivity activityUnderTest = activityController.get();
        FloatingActionButton fabCreateOrder = activityUnderTest.findViewById(R.id.fabCreateOrder);
        assertEquals("", ((MakeMySandwichApp) activityUnderTest.getApplication()).info.getOrderId());
        fabCreateOrder.performClick();
        assertNotEquals("", ((MakeMySandwichApp) activityUnderTest.getApplication()).info.getOrderId());
    }

}