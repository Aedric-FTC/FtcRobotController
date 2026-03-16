package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GoonBotTeleOp extends OpMode
{

// ------------------------------------------------------------------------------------------------------------------------|
// Class Declarations ---------------------------------------------------------------------------------- Class Declarations
// ------------------------------------------------------------------------------------------------------------------------|

//region Class Calls
    motorDrive motors = new motorDrive();
    light light = new light();
    Intake intake = new Intake();
    Launcher launcher = new Launcher();
    Transfer transfer = new Transfer();
    Menu menu = new Menu(this);
//endregion

// ------------------------------------------------------------------------------------------------------------|
// Methods ---------------------------------------------------------------------------------------------Methods
//-------------------------------------------------------------------------------------------------------------|

//region Toggler Method
// Toggler Method -------------------------------------------------------------------------------------- Toggler Method
    public boolean lastInput;
    public boolean outputToggle;
    private boolean buttonToggle(boolean input)
    {
        boolean output;
        if (input && !lastInput)
        {
            outputToggle = !outputToggle;
        }

        lastInput = input;

        if (outputToggle)
        {
            output = true;
        } else {
            output = false;
        }
        return output;
    }
//endregion

//region Increment
// Increment ------------------------------------------------------------------------------------------- Increment Method
    public boolean wasIncInput;
    public double increment(boolean inputKey, double inputValue, int incrementValue, int max)
    {
        int increment;
        if (inputKey && !wasIncInput)
        {
            increment = incrementValue;
        }
        else
        {
            increment = 0;
        }
        wasIncInput = inputKey;
        if (inputValue + increment <= max)
        {
            inputValue += increment;
        }
        return inputValue;
    }
//endregion

//region Decrement
// Decrement ------------------------------------------------------------------------------------------- Decrement Method
    public boolean wasDecInput;
    public double decrement(boolean inputKey, double inputValue, int decrementValue, int min)
    {
        int decrement;
        if (inputKey && !wasDecInput)
        {
            decrement = decrementValue;
        }
        else
        {
            decrement = 0;
        }
        wasDecInput = inputKey;
        if (inputValue - decrement >= min)
        {
            inputValue -= decrement;
        }
        return inputValue;
    }
//endregion

//region Menu Test
// Menu Test ------------------------------------------------------------------------------------------- Menu Test
    boolean wasMenuInputPlus;
    boolean wasMenuInputMinus;
    int outputValue;
    public int menuScroller(boolean incrementKey, boolean decrementKey, int inputValue, int modValue, int itemCount)
    {
        int increment;

        // Increment
        if (incrementKey && !wasMenuInputPlus)
        {
            increment = modValue;
        }
        else
        {
            increment = 0;
        }
        wasMenuInputPlus = incrementKey;
        if (inputValue + increment <= itemCount)
        {
            inputValue += increment;
            outputValue = inputValue;
        }

        // Decrement
        if (decrementKey && !wasMenuInputMinus)
        {
            increment = modValue;
        }
        else
        {
            increment = 0;
        }
        wasMenuInputMinus = decrementKey;
        if (inputValue - increment > 0)
        {
            inputValue -= increment;
            outputValue = inputValue;
        }

        if (outputValue > 0)
        {
            return outputValue;
        }
        else
        {
            return inputValue;
        }
    }

    public int menuCounter = 1;
    public double intakeSpeed = 100;
    public double transferSpeed = 100;
    public double launcherSpeed = 50;
//endregion

// -----------------------------------------------------------------------------------------------------------------|
// OpMode Code ----------------------------------------------------------------------------------------- OpMode Code
// -----------------------------------------------------------------------------------------------------------------|

//region Init
// Init ------------------------------------------------------------------------------------------------ Init
    double driveSpeed;
    @Override
    public void init()
    {
        motors.init(hardwareMap);
        light.init(hardwareMap);
        intake.init(hardwareMap);
        transfer.init(hardwareMap);
        launcher.init(hardwareMap);

        driveSpeed = 100;
    }
//endregion

//region Init Loop
// Init Loop ------------------------------------------------------------------------------------------- Init Loop
    @Override
    public void init_loop()
    {
        telemetry.addData("Drive Speed", driveSpeed);

        driveSpeed = increment(gamepad1.dpad_right, driveSpeed, 5, 100);
        driveSpeed = decrement(gamepad1.dpad_left, driveSpeed, 5, 0);
    }
//endregion

//region Loop
// Loop ------------------------------------------------------------------------------------------------ Loop
    @Override
    public void loop()
    {
        menu.setMenuMode();
        if (menu.menuMode)
        {
            menu.setMenuCounter(4);
            menu.setMenuItem(1,"Drive Speed", driveSpeed, 5, 0, 100);
            menu.setMenuItem(2,"Launcher Speed", launcherSpeed, 1, 0, 100);
            menu.setMenuItem(3,"Transfer Speed", transferSpeed, 5, 0, 100);
            menu.setMenuItem(4,"Intake Speed", intakeSpeed, 5, 0, 100);
        }
        else
        {
            telemetry.addLine("Press START for menu");
            telemetry.addLine();

            // Direction Indicators
            if (motors.isReversed) {
                telemetry.addData("Direction", "Reversed");
                light.lightYellow();
            } else {
                telemetry.addData("Direction", "Forward");
                light.lightGreen();
            }

            // Drivetrain
            motors.drive(gamepad1.left_stick_y, gamepad1.left_stick_x,
                    gamepad1.right_stick_x, gamepad1.dpad_down, driveSpeed);

            // Intake
            intake.spin(intakeSpeed, gamepad1.left_bumper, gamepad1.right_bumper);

            // Transfer
            transfer.spin(transferSpeed, gamepad1.left_bumper, gamepad1.right_bumper);

            // Launcher
            launcher.Launch(launcherSpeed, gamepad1.right_trigger, gamepad1.left_trigger);
        }
    }
//endregion
}
