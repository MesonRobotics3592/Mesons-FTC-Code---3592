//----------------------------------------------------- Imports -------------------------------------------------
package org.firstinspires.ftc.teamcode.BioBuzz_26_27.Qual_1;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous

public class Blue_Back_Down extends OpMode {

    //------------------------------------------- NullIit --------------------------------------
    //declaring variables
    private DcMotorEx back_right = null;
    private DcMotorEx front_left = null;
    private DcMotorEx front_right = null;
    private DcMotorEx back_left = null;
    int pulse = 0;
    double i0 = 0, i1 = 0, i2 = 0, i3 = 0;
    double desiredRpm = 0;
    double diameter = 1;
    public int tolerance = 6;


    public void init() {
        shortInit();
    }

    public void start() {
        drive(5);
    }

    public void loop() {
        //LEAVE BLANK
    }



    public int pulses(double inches){
        return (int)(inches / diameter * Math.PI * 384.5);
    }

    public void dcPos(){
        i0 = front_left.getCurrentPosition();
        i1 = front_right.getCurrentPosition();
        i2 = back_left.getCurrentPosition();
        i3 = back_right.getCurrentPosition();
    }

    public void shortInit(){
        //Variable initialization, brake functions and setting proper wheel direction for mecanum drive.
        front_left   = hardwareMap.get(DcMotorEx.class, "lf");
        front_right  = hardwareMap.get(DcMotorEx.class, "rf");
        back_left    = hardwareMap.get(DcMotorEx.class, "lr");
        back_right   = hardwareMap.get(DcMotorEx.class, "rr");
        //Encoders and basic directions; 0 power is set to brake, holding its position
        front_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_left.setDirection(DcMotor.Direction.REVERSE);
        front_right.setDirection(DcMotor.Direction.REVERSE);
        front_left.setDirection(DcMotor.Direction.REVERSE);
        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Sets the motors from running by speed and time over to a more consistent pulse-based      //movement
        front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //Movement Methods
    public void drive(double inches){
        double i0 = 0;
        double i1 = 0;
        double i2 = 0;
        double i3 = 0;
        pulse = pulses(inches);
        front_left.setTargetPosition(front_left.getCurrentPosition() + pulse);
        front_left.setVelocity(desiredRpm/60 * 384.5 * inches/Math.abs(inches));
        front_right.setTargetPosition(front_left.getCurrentPosition() + pulse);
        front_right.setVelocity(desiredRpm/60 * 384.5 * inches/Math.abs(inches));
        back_left.setTargetPosition(front_left.getCurrentPosition() + pulse);
        back_left.setVelocity(desiredRpm/60 * 384.5 * inches/Math.abs(inches));
        back_right.setTargetPosition(front_left.getCurrentPosition() + pulse);
        back_right.setVelocity(desiredRpm/60 * 384.5 * inches/Math.abs(inches));
        dcPos();
        while((inches > 0) ? (front_left.getCurrentPosition() < i0 - 5 || front_right.getCurrentPosition() < i1 - 5 || back_left.getCurrentPosition() < i2 - 5 || back_right.getCurrentPosition() < i3 - 5) : (front_left.getCurrentPosition() > i0 + 5 || front_right.getCurrentPosition() > i1 + 5 || back_left.getCurrentPosition() > i2 + 5 || back_right.getCurrentPosition() > i3 + 5)){
            dcPos();
        }
        sleep(50);
    }

    public void sleep(long time){
        //Waits in milliseconds
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void twist(int deg){
        dcPos();
        int pulse = (int)(pulses(deg)/3.7);
        int t0 = (int)(i0 + pulse);
        int t1 = (int)(i1 - pulse);
        int t2 = (int)(i2 - pulse);
        int t3 = (int)(i3 + pulse);
        //motor0.setTargetPosition(i0 + pulse);
        //motor1.setTargetPosition(i1 - pulse);
        //motor2.setTargetPosition(i2 - pulse);
        //motor3.setTargetPosition(i3 + pulse);
        int notAbs = deg/Math.abs(deg);
        //motor0.setVelocity(1500 * notAbs);
        //motor1.setVelocity(1500 * notAbs);
        //motor2.setVelocity(1500 * notAbs);
        //motor3.setVelocity(1500 * notAbs);
        while((deg > 0) ? (i0 < t0 - tolerance || i1 > t1 + tolerance || i2 > t2 + tolerance || i3 < t3 - tolerance) : (i0 > t0 + tolerance || i1 < t1 - tolerance || i2 < t2 - tolerance || i3 > t3 + tolerance)){
            dcPos();
        }
        sleep(100);
    }

    public void strafe(int dist){
        dcPos();
        int t0 = (int)(i0 + pulses(dist));
        int t1 = (int)(i1 - pulses(dist));
        int t2 = (int)(i2 + pulses(dist));
        int t3 = (int)(i3 - pulses(dist));
        //motor0.setTargetPosition(i0 + pulses(dist));
        //motor1.setTargetPosition(i1 - pulses(dist));
        //motor2.setTargetPosition(i2 + pulses(dist));
        //motor3.setTargetPosition(i3 - pulses(dist));
        int notAbs = dist/Math.abs(dist);
        //motor0.setVelocity(1500 * notAbs);
        //motor1.setVelocity(1500 * notAbs);
        //motor2.setVelocity(1500 * notAbs);
        //motor3.setVelocity(1500 * notAbs);
        while((dist > 0) ? (i0 < t0 - tolerance || i1 > t1 + tolerance || i2 < t2 - tolerance || i3 > t3 + tolerance) : (i0 > t0 + tolerance || i1 < t1 - tolerance || i2 > t2 + tolerance || i3 < t3 - tolerance)){
            dcPos();
        }
        sleep(100);
    }

    /*
    public void shoot(){
        kicker.setPosition(0.245);
        sleep(300);
        indexer.setTargetPosition(indexer.getCurrentPosition() - 769);
        indexer.setVelocity(1500);
        sleep(800);
        kicker.setPosition(0.4);
    }
    */

}