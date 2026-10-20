//Import all necessary libraries, we need april tag and vision portal/webcam imports later on
package org.firstinspires.ftc.teamcode.Decode_25_26;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class Indexer_Test extends OpMode {
    private Limelight3A limelight;
    double indexerTarget;
    double indexerCurrent;
    private Servo kicker = null;
    private DcMotorEx indexer = null;
    boolean fLeft = false;
    boolean fRight = false;
    boolean rCheck = false;
    boolean lCheck = false;
    private Servo rgbLight = null;
    boolean launcherCheck = false;
    int launcherState = 0;
    private DcMotorEx launcher = null;
    private DcMotorEx launcher2 = null;
    private DcMotorEx back_right = null;
    private DcMotorEx front_left = null;
    private DcMotorEx front_right = null;
    private DcMotorEx back_left = null;
    boolean slowLauncherCheck = false;
    boolean inCheck = false;
    boolean inState = false;
    private DcMotorEx intake = null;
    boolean slowLauncherTick = false;
    boolean liamCheck = false;
    double turn = 0;
    double tv = 0;

    public void init(){
        front_left   = hardwareMap.get(DcMotorEx.class, "motor0");
        front_right  = hardwareMap.get(DcMotorEx.class, "motor1");
        back_left    = hardwareMap.get(DcMotorEx.class, "motor2");
        back_right   = hardwareMap.get(DcMotorEx.class, "motor3");
        front_left.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        front_right.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        back_left.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        back_right.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right.setDirection(DcMotorEx.Direction.REVERSE);
        front_right.setDirection(DcMotorEx.Direction.REVERSE);
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorEx.Direction.REVERSE);
        rgbLight = hardwareMap.get(Servo.class, "light");
        kicker = hardwareMap.get(Servo.class, "kicker");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        limelight.start();
        indexer = hardwareMap.get(DcMotorEx.class,  "indexer");
        indexer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//indexer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); 
        indexer.setTargetPosition(0);
        indexer.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        indexerTarget = indexer.getCurrentPosition();
        indexerCurrent = indexer.getCurrentPosition();
        kicker.setPosition(0.4);
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");
        launcher.setDirection(DcMotorEx.Direction.REVERSE);
        launcher2.setDirection(DcMotorEx.Direction.REVERSE);

        //Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    @Override

    public void loop(){
        kickerTick();
        indexerTick();
        launcherTick();
        lightTick();
        aimTick();
        moveTick();
        intakeTick();
        slowLauncherTick();
    }
    public void slowLauncherTick(){
/*if(gamepad1.b && slowLauncherTick == false){
    launcher.setPower(-0.2);
    launcher2.setPower(-0.2);
    slowLauncherTick = true;
}else if (gamepad1.b && slowLauncherTick == true){
    launcher.setPower(0);
    launcher2.setPower(0);
}*/
        if(gamepad1.b){
            if(slowLauncherTick == false && liamCheck == false){
                launcher.setPower(-0.5);
                launcher2.setPower(-0.5);
                slowLauncherTick = true;
            } else if(slowLauncherTick == true && liamCheck == false){
                launcher.setPower(0);
                launcher2.setPower(0);
                slowLauncherTick = false;
            }
            liamCheck = true;
        } else {
            liamCheck = false;
        }
    }

    public void kickerTick(){
        if(gamepad1.a){
            kicker.setPosition(.295);
        } else {
            kicker.setPosition(.4);
        }
    }

    public void lightTick(){
        rgbLight.setPosition(600);
        if(launcherState == 0){
            rgbLight.setPosition(300);
        } else if(launcherState == 1) {
            rgbLight.setPosition(600);
        } else if(launcherState == 2){
            rgbLight.setPosition(900);
        }
    }
    public void aimTick(){
        if(gamepad1.x){
// Get the horizontal offset from the Limelight (in degrees) 
            double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
// Check if a target is detected (tv = 1)
            tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0.0);
            if (tv > 0.0) {
                // Simple P-controller for steering
                turn = tx * 0.05;
            } else
                turn = 0;
            // Adjust 0.05 (Kp) as needed
// Example: Using DifferentialDrive 
// robotDrive.arcadeDrive(0, turnSpeed); 
// Turn right if tx is positive }
        }
    }

    public void indexerTick(){
        fRight = false;
        fLeft = false;
        indexerCurrent = indexer.getCurrentPosition();
        if(gamepad1.right_bumper){
            if(rCheck == false){
                indexerTarget += 769/3;
                indexer.setVelocity(1500);
            }
            rCheck = true;
        } else{
            rCheck = false;
        }
        if(gamepad1.left_bumper){
            if(lCheck == false){
                indexerTarget -= 769/3;
                indexer.setVelocity(-1500);
            }
            lCheck = true;
        } else{
            lCheck = false;
        }
        indexer.setTargetPosition((int)indexerTarget);
        telemetry.addData("indexerTarget", indexerTarget);
        telemetry.addData("indexerCurrent", indexerCurrent);
        telemetry.addData("target", indexer.getTargetPosition());
        telemetry.update();
    }

    public void intakeTick(){
        if(gamepad1.left_trigger > 0.2 || gamepad2.left_trigger > 0.2){
            if(inCheck == false){
                if(inState == false){
                    inState = true;
                    intake.setPower(1);
                } else if (inState){
                    inState = false;
                    intake.setPower(-0.3);
                }
            }
            inCheck = true;
        } else {
            inCheck = false;
        }
    }


    public void launcherTick(){
        if(gamepad1.right_trigger > 0.1){
            if(launcherCheck == false){
                if(launcherState == 0){
                    launcherState = 1;
                    launcher.setVelocity(1300);
                    launcher2.setVelocity(1300);
                } else if(launcherState == 1){
                    launcherState = 2;
                    launcher.setVelocity(3000);
                    launcher2.setVelocity(3000);
                } else if (launcherState == 2){
                    launcherState = 0;
                    launcher.setVelocity(0);
                    launcher2.setVelocity(0);
                }
            }
            launcherCheck = true;
        } else{
            launcherCheck = false;
        }
    }

    public void moveTick(){
//Taking movement inputs
        double drive  = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double twist  = gamepad1.right_stick_x;
        if(turn != 0){
            twist = turn;
        }
//Movement Formula 
        double[] speeds = {
                (drive - strafe + twist),
                (drive + strafe - twist),
                (drive - strafe - twist),
                (drive + strafe + twist)
        };
//Normalize for values greater than 1
        double max = Math.abs(speeds[0]);
        for(int i = 0; i < speeds.length; i++) {
            if ( max < Math.abs(speeds[i]) )
                max = Math.abs(speeds[i]);
        }
        if (max > 1) {
            for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
        }
//Apply Speeds
        front_left.setPower(speeds[0]);
        front_right.setPower(speeds[1]);
        back_left.setPower(speeds[2]);
        back_right.setPower(speeds[3]);
    }


    public double scale(int degrees){
        return (degrees/360 * 384.5);
    }

}







