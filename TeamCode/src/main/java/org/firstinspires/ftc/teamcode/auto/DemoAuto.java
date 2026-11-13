package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous
public class DemoAuto extends OpMode {
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    //--------------------------------------------------------------
    //Poses go here:
    private final Pose startPose = p.of(24, 24, 0);
    private final Pose endPose = p.of(48, 48, 90);
    private final Pose launchPose = p.of(36, 36, 0);
    private final Pose controlPose = p.of(36, 60, 45);
    //--------------------------------------------------------------

    //--------------------------------------------------------------
    // Paths go here:
    private Path startToEndLinear()
    {
        return line(startPose, endPose).linear(startPose, endPose);
    }
    private Path startToLaunch()
    {
        return line(startPose, launchPose).linear(startPose, launchPose);
    }
    private Path launchToEnd()
    {
        return line(launchPose, endPose).linear(launchPose, endPose);
    }
    private Path startToEndCurve() {
        return curve(startPose, controlPose, endPose).linear(startPose, endPose);
    }
    //--------------------------------------------------------------

    private Command autoRoutine()
    {
        return sequential(
                follow(follower, startToLaunch()),
                // Add mechanism commands here
                follow(follower, launchToEnd())
        );
    }
    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
        // Add other loop methods here
    }
}