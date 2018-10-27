package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

//import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@Autonomous(name="Scorpion: TestGyro", group="DarkMatter2019")
//@Disabled
public class TestGyro extends Scorpion_AutoOpMode {


    @Override
    public void autoRunPath() {

        while (opModeIsActive()) {
            telemetry.addData("Gyro Yaw", gyro.readGyro());
            telemetry.update();
        }

    }

}
