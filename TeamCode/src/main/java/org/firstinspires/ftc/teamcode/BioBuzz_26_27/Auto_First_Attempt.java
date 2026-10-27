//----------------------------------------------------- Imports -------------------------------------------------
package org.firstinspires.ftc.teamcode.BioBuzz_26_27;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous

public class Auto_First_Attempt extends OpMode {

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


    public void init() {
        shortInit();
    }

    public void start() {
    drive(5);
    }

    public void loop() {
//LEAVE BLANK
    }

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
        front_left   = hardwareMap.get(DcMotorEx.class, "front_left");
        front_right  = hardwareMap.get(DcMotorEx.class, "front_right");
        back_left    = hardwareMap.get(DcMotorEx.class, "back_left");
        back_right   = hardwareMap.get(DcMotorEx.class, "back_right");
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

    public void sleep(long time){
        //Waits in milliseconds
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}