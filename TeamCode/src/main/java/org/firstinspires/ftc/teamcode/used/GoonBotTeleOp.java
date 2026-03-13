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
    Menu menu = new Menu();
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
    double ballForward;
    double ballBackward;
    @Override
    public void loop()
    {
        boolean menuMode = buttonToggle(gamepad1.start);
        if (menuMode)
        {
            telemetry.addLine("Modifier Menu");
            telemetry.addLine();
            telemetry.addLine("CONTROLS:");
            telemetry.addLine("D-Pad up/down to scroll");
            telemetry.addLine("D-Pad left/right to change values");
            telemetry.addLine();

            menuCounter = menuScroller(gamepad1.dpad_down, gamepad1.dpad_up, menuCounter, 1, 4);
            if (menuCounter == 1)
            {
                // Drive Speed Mods
                driveSpeed = increment(gamepad1.dpad_right, driveSpeed, 5, 100);
                driveSpeed = decrement(gamepad1.dpad_left, driveSpeed, 5, 0);
                telemetry.addData(">  Drive Speed", driveSpeed);
            } else
            {
                telemetry.addData("Drive Speed", driveSpeed);
            }

            if (menuCounter == 2)
            {
                intakeSpeed = increment(gamepad1.dpad_right, intakeSpeed, 1, 100);
                intakeSpeed = decrement(gamepad1.dpad_left, intakeSpeed, 1, 0);
                telemetry.addData(">  Intake Speed", intakeSpeed);
            } else
            {
                telemetry.addData("Intake Speed", intakeSpeed);
            }

            if (menuCounter == 3)
            {
                transferSpeed = increment(gamepad1.dpad_right, transferSpeed, 1, 100);
                transferSpeed = decrement(gamepad1.dpad_left, transferSpeed, 1, 0);
                telemetry.addData(">  Transfer Speed", transferSpeed);
            }
            else
            {
                telemetry.addData("Transfer Speed", transferSpeed);
            }

            if (menuCounter == 4)
            {
                launcherSpeed = increment(gamepad1.dpad_right, launcherSpeed, 1, 100);
                launcherSpeed = decrement(gamepad1.dpad_left, launcherSpeed, 1, 0);
                telemetry.addData(">  Launcher Speed", launcherSpeed);
            } else
            {
                telemetry.addData("Launcher Speed", launcherSpeed);
            }
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

            // Drivetrain Code Execution
            motors.drive(gamepad1.left_stick_y, gamepad1.left_stick_x,
                    gamepad1.right_stick_x, gamepad1.dpad_down, driveSpeed);

            // Intake
            if (gamepad1.left_bumper)
            {
                ballForward = intakeSpeed;
            }
            else
            {
                ballForward = 0.0;
            }
            if (gamepad1.right_bumper)
            {
                ballBackward = intakeSpeed;
            }
            else
            {
                ballBackward = 0.0;
            }

            intake.suck(ballForward);
            intake.blow(ballBackward);

            // Transfer
            if (gamepad1.left_bumper)
            {
                ballForward = transferSpeed;
            }
            else
            {
                ballForward = 0.0;
            }
            if (gamepad1.right_bumper)
            {
                ballBackward = transferSpeed;
            }
            else
            {
                ballBackward = 0.0;
            }

            transfer.spinTransfer(ballForward);
            transfer.reverseTransfer(ballBackward);

            // Launcher
            launcher.Launch(gamepad1.right_trigger, launcherSpeed);
            launcher.reverseLaunch(gamepad1.left_trigger, launcherSpeed);
        }
    }
//endregion
}
