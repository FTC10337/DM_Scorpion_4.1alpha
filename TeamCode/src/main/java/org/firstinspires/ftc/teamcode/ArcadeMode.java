package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

@TeleOp(name="ArcadeMode", group="DarkMatter2019")
//@Disabled
public class ArcadeMode extends OpMode
{
    DriveTrain scorpion = new DriveTrain();

    private ElapsedTime runtime = new ElapsedTime();
    boolean slowMo = false;
    double turnCoefficient = 1;
    double driveCoefficient = 1;

    private ElapsedTime slowTelemetry = new ElapsedTime();

    @Override
    public void init() {

        telemetry.addData("Scorpion Says", "Hello DarkMatter!");
        scorpion.init(hardwareMap);
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        RobotLog.i("Initialized, Ready to Start!");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        slowTelemetry.reset();
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

        // Arcade(POV) Mode uses left stick to go forward, and right stick to turn.
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;

        //Activating slowMo slow motion mode with controller left bumper
        if (gamepad1.left_bumper) {
            slowMo = true;
            turnCoefficient = 3;
            driveCoefficient = 1.5;
        }else{
            slowMo = false;
            turnCoefficient = 1.5;
            driveCoefficient = 1;
        }
        telemetry.addData("SlowMo mode is ", slowMo);

        // Smooth and deadzone the joystick values
        drive = scorpion.smoothPowerCurve(scorpion.deadzone(drive, 0.10)) / driveCoefficient;
        turn = scorpion.smoothPowerCurve(scorpion.deadzone(turn, 0.10)) / turnCoefficient;


        leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
        rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

        // Update the encoder data every 1/10 second
        if (slowTelemetry.milliseconds() > 100) {
            slowTelemetry.reset();
            telemetry.addData("Path0",  "Now at %7d :%7d :%7d :%7d",
                    scorpion.leftFront.getCurrentPosition(),
                    scorpion.leftRear.getCurrentPosition(),
                    scorpion.rightFront.getCurrentPosition(),
                    scorpion.rightRear.getCurrentPosition());
                    // Show the elapsed game time and wheel power.
                    //telemetry.addData("Status", "Run Time: " + runtime.toString());
                    telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
                    telemetry.update();
        }


        // Send calculated power to wheels
        scorpion.setPower(leftPower, rightPower);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
