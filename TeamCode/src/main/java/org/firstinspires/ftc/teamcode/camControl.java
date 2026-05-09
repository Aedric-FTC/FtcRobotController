package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class camControl
{
    VisionPortal cam;

    public void initCamera(HardwareMap hwMap)
    {
        // Build the VisionPortal
        cam = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "webcam")) // Name from config
                .setCameraResolution(new android.util.Size(640, 480))    // Set resolution
                .enableLiveView(true)                                   // Show preview on RC phone/Hub
                .build();
        FtcDashboard dashboard = FtcDashboard.getInstance();

        dashboard.startCameraStream(cam, 15);
    }
}
