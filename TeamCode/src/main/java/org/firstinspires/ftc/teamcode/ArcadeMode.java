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
    boolean turbo = false;
    double turnCoefficient = 4;
    double driveCoefficient = 4             ;

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
        double liftPower;
//        double intakePowerIn;
//        double intakePowerOut;
        double pivotPower;

        // Arcade(POV) Mode uses left stick to go forward, and right stick to turn.
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;

        double lift = gamepad2.left_stick_y;
//        double intakeControlIn = gamepad2.right_trigger;
//        double intakeControlOut = gamepad2.left_trigger;
        double pivotControl = gamepad2.right_stick_y;


        //Activating slowMo slow motion mode with controller left bumper
        if (gamepad1.right_bumper) {
            turbo = true;
            turnCoefficient = 2;
            driveCoefficient = 1;
        }else{
            turbo = false;
            turnCoefficient = 4;
            driveCoefficient = 4;
        }
        telemetry.addData("TURBO mode is ", turbo);


        if (gamepad2.left_bumper) {
            scorpion.intake.setPower(-1.0);
        }else if (gamepad2.right_bumper) {
            scorpion.intake.setPower(1.0);
        }else {
            scorpion.intake.setPower(0);
        }


        // Smooth and deadzone the joystick values
        drive = scorpion.smoothPowerCurve(scorpion.deadzone(drive, 0.10)) / driveCoefficient;
        turn = scorpion.smoothPowerCurve(scorpion.deadzone(turn, 0.10)) / turnCoefficient;


        leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
        rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

        // Smooth and deadzone the lift and pivot inputs before using
        lift = scorpion.smoothPowerCurve(scorpion.deadzone(lift, 0.1));
        liftPower    = Range.clip(lift, -1.0, 1.0);
//        intakePowerIn  = Range.clip(intakeControlIn, 0, 1.0);
//        intakePowerOut = Range.clip(intakeControlOut, -1.0, 0);
        pivotControl = scorpion.smoothPowerCurve(scorpion.deadzone(pivotControl, 0.1));
        pivotPower = Range.clip(pivotControl/3, -0.5, 0.5);
//
//        scorpion.intake.setPower(intakePowerIn);
//        scorpion.intake.setPower(intakePowerOut);
        scorpion.pivot.setPower(pivotPower);
        scorpion.latchLift.setPower(liftPower);

        // Update the encoder data every 1/10 second
        if (slowTelemetry.milliseconds() > 10) {
            slowTelemetry.reset();

            telemetry.addData("Path0",  "Now at %7d :%7d :%7d :%7d",
                    scorpion.leftFront.getCurrentPosition(),    // labeled B
                    scorpion.leftRear.getCurrentPosition(),     // labeled C
                    scorpion.rightFront.getCurrentPosition(),   // labeled A
                    scorpion.rightRear.getCurrentPosition());   // labeled D
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
