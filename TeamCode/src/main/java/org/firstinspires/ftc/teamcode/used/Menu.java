package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Menu extends OpMode
{
    //region Useless OpMode Necessities
    @Override
    public void init() {

    }
    @Override
    public void loop() {

    }
    //endregion

    public boolean menuMode;
    public int menuCounter;
    boolean menuWasIncremented;
    boolean menuWasDecremented;
    public boolean lastInput;
    public boolean outputToggle;
    public void setMenuMode(boolean menuButton)
    {
        boolean output;
        if (menuButton && !lastInput)
        {
            outputToggle = !outputToggle;
        }

        lastInput = menuButton;

        if (outputToggle)
        {
            output = true;
        } else {
            output = false;
        }

        menuMode = output;
    }
    public void setMenuCounter(boolean nextButton, boolean backButton, int itemCount)
    {
        if (menuMode)
        {
            if (nextButton && menuCounter + 1 <= itemCount && !menuWasIncremented)
            {
                menuCounter += 1;
            } else if (nextButton)
            {
                menuCounter = 1;
            }
            menuWasIncremented = nextButton;

            if (backButton && menuCounter - 1 > 0 && !menuWasDecremented)
            {
                menuCounter -= 1;
            } else if (backButton)
            {
                menuCounter = itemCount;
            }
            menuWasDecremented = backButton;
        }
    }

    boolean wasIncremented;
    boolean wasDecremented;
    public double changeMenuItem(int menuNumber, String itemName, boolean incButton, boolean decButton,
                                 double input, double increment, double min, double max)
    {
        if (menuMode)
        {
            telemetry.addData(itemName, input);
            String itemSelected = ">  " + itemName;

            if (menuNumber == menuCounter)
            {
                telemetry.addData(itemSelected, input);
                if (incButton && input + increment <= max && !wasIncremented)
                {
                    input += increment;
                }

                wasIncremented = incButton;

                if (decButton && input - increment != min - 1 && !wasDecremented)
                {
                    input -= increment;
                }

                wasDecremented = decButton;
            }
        }
        return input;
    }
}
