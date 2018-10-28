package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Scorpion {

    private HardwareMap hwMap = null;


    //Creating new public classes to combine Hardware classes and reuse in Arcade and Auto modes
    public DriveTrain driveTrain = new DriveTrain();
    public IntakePivot intakePivot  = new IntakePivot();
    public LatchLift latch = new LatchLift();

    public void init (HardwareMap ahwMap) {

        hwMap = ahwMap;

    }

}
