
//Import all necessary libraries, we need april tag and vision portal/webcam imports later on
package org.firstinspires.ftc.teamcode.Decode_25_26;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
@Disabled

public class TeleOp_Test_147pt extends OpMode {
    //All Motors
    private DcMotorEx back_right = null;
    private DcMotorEx front_left = null;
    private DcMotorEx front_right = null;
    private DcMotorEx back_left = null;
    private DcMotorEx intake = null;
    private DcMotorEx launcher = null;
    //Servos
    private Servo kicker = null;
    private Servo indexer = null;
    //private CRServo bootkicker = null;
//Color/Distance Sensor
    RevColorSensorV3 distanceSensor1;
    RevColorSensorV3 distanceSensor2;
    //Toggle Variables
    boolean inCheck = false;
    boolean inState = false;
    boolean indexerCheck = false;
    boolean indexerCheck2 = false;
    boolean launcherCheck = false;
    int launcherState = 0;
    boolean iS = false;
    boolean iCheck = false;
    double indexerDeg = 1615;
    //More generic vars
    int targetIndexPos = 780;
    int[] indexerStore = {0,0,0};
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime indexTime = new ElapsedTime();


    @Override

    public void init(){
//Assigning all motors to the hardware map
        front_left   = hardwareMap.get(DcMotorEx.class, "motor0");
        front_right  = hardwareMap.get(DcMotorEx.class, "motor1");
        back_left    = hardwareMap.get(DcMotorEx.class, "motor2");
        back_right   = hardwareMap.get(DcMotorEx.class, "motor3");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        front_left.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        front_right.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        back_left.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        back_right.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcher.setDirection(DcMotorEx.Direction.REVERSE);
        intake.setDirection(DcMotorEx.Direction.REVERSE);
        back_right.setDirection(DcMotorEx.Direction.REVERSE);
//All Servos
//bootkicker = hardwareMap.get(CRServo.class, "bootkicker");
        kicker = hardwareMap.get(Servo.class, "intakekicker");
        indexer = hardwareMap.get(Servo.class, "indexer");
//Color/Distance
        distanceSensor1 = hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        distanceSensor2 = hardwareMap.get(RevColorSensorV3.class, "colorSensor2");
//Initial indexer pos
        targetIndexPos = (int)(indexer.getPosition()*indexerDeg);
    }

    @Override

    public void loop(){
        moveTick();
        intakeTick();
        indexerTick();
        launcherTick();
        kickerTick();
//        telemetry.addData("RPM: ", motor0.getCurrentVelocity / 537.7 * 60);
  //      telemetry.addData("Launch RPM: ", launcher.getCurrentVelocity / 537.7 * 60);
        telemetry.addData("TargetPos:", targetIndexPos);
        telemetry.addData("actual Pos:", indexer.getPosition());
        telemetry.addData("KICKER ",kicker.getPosition());
        telemetry.update();
    }

    public void moveTick(){
//Taking movement inputs
        double drive  = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double twist  = gamepad1.right_stick_x;
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
    public void intakeTick(){
//Toggles between intake on and bootkicker on, then switches to bootkicker off and intake               //reversed to kick out balls
        if(gamepad1.left_trigger > 0.2 || gamepad2.left_trigger > 0.2){
            if(inCheck == false){
                if(inState == false){
                    inState = true;
                    intake.setPower(1);
                    //bootkicker.setPower(1);
                } else if (inState){
                    inState = false;
                    // bootkicker.setPower(0);
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
                    launcher.setPower(0.665 * scale());
                } else if(launcherState == 1){
                    launcherState = 2;
                    launcher.setPower(.93 * scale());
                } else if (launcherState == 2){
                    launcherState = 0;
                    launcher.setPower(0);
                }
            }
            launcherCheck = true;
        } else{
            launcherCheck = false;
        }
    }
    public void indexerTick(){
        if((gamepad1.b || gamepad2.b) && runtime.seconds() > .18){
            targetIndexPos = 780;
            indexTime.reset();
        }
        double distance = distanceSensor1.getDistance(DistanceUnit.CM);
        double distance2 = distanceSensor2.getDistance(DistanceUnit.CM);
        telemetry.addData("distance", distance);
        telemetry.addData("distance2", distance2);
        if(gamepad1.right_bumper || gamepad2.right_bumper){
            if(indexerCheck == false && runtime.seconds() > .18){
                if(targetIndexPos + 110 <= indexerDeg)
                    targetIndexPos += 120;
                indexTime.reset();
            }
            indexerCheck = true;
        }else{
            indexerCheck = false;
        }
        if(gamepad1.left_bumper || gamepad2.left_bumper){
            if(indexerCheck2 == false && runtime.seconds() > .18){
                if(targetIndexPos - 110 >= 0)
                    targetIndexPos -= 120;
                indexTime.reset();
            }
            indexerCheck2 = true;
        }else{
            indexerCheck2 = false;
        }

        if((distance < 3.5 /*|| distance2 < 3.5*/) && (indexer.getPosition() >= (targetIndexPos-10)/1800) && indexer.getPosition() <= (targetIndexPos+10)/1800){
            indexerStore[0] = 1;
        }
        setIndexerPos(targetIndexPos);
    }
    public void setIndexerPos(int degrees){
        if(degrees/indexerDeg > 1){
            indexer.setPosition(1);
        }
        if(degrees/indexerDeg < 0){
            indexer.setPosition(0);
        } else
            indexer.setPosition(degrees/indexerDeg);
    }
    public void kickerTick(){
        if(gamepad1.a && indexTime.seconds() > 0.5){
            if(iS == false && iCheck == false){
                runtime.reset();
                kicker.setPosition(.5);
                iCheck = true;
                iS = true;
            }
        }else{
            iCheck = false;
        }
        if(runtime.seconds() > .1){
            kicker.setPosition(.15);
            iS = false;
        }
    }
    public double scale(){
        return (13.40 / hardwareMap.voltageSensor.iterator().next().getVoltage());
    }
}

/*
Motors 0-3 on control are wheels 0-3
Motor 1 is intake on expansion
Launcher is motor 0
Indexer is port 3
Kicker is port 1
Bootkicker is 0
*/



