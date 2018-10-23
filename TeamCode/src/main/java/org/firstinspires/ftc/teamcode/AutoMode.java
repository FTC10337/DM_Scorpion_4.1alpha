package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

//import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@Autonomous(name="Scorpion: AutoMode", group="DarkMatter2019")
//@Disabled
public class AutoMode extends Scorpion_AutoOpMode {


    @Override
    public void autoRunPath() {

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED,  -48,  -48, 15.0);  // S1: Forward 73 Inches with 8 Sec timeout
        sleep(300);
        gyroTurn(TURN_SPEED, 135, P_TURN_COEFF);
//        encoderDrive(TURN_SPEED,   -12, 12, 6);  // S2: Turn Right 6 Inches with 2.5 Sec timeout
        encoderDrive(DRIVE_SPEED, -86, -86, 15.0);  // S3: Forward 86 Inches with 10 Sec timeout

    }

}
