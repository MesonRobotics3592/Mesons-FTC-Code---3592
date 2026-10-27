//----------------------------------------------------- Imports -------------------------------------------------
package org.firstinspires.ftc.teamcode.Decode_25_26;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous
@Disabled
//Include @Disabled or comment out OpMode to remove from Driver Hub

public class AutoTest extends OpMode {

    //------------------------------------------- NullIit --------------------------------------
//declaring variables
    private DcMotorEx motor0 = null;
    private DcMotorEx motor1 = null;
    private DcMotorEx motor2 = null;
    private DcMotorEx motor3 = null;
    int pulse = 0;
    double i0 = 0, i1 = 0, i2 = 0, i3 = 0;
    double desiredRpm = 0;
    double diameter = 1;


    public void init() {
        shortInit();
    }

    public void start() {
    }

    public void loop(){
//LEAVE BLANK
    }

    public void drive(double inches){
        double i0 = 0;
        double i1 = 0;
        double i2 = 0;
        double i3 = 0;
        pulse = pulses(inches);
        motor0.setTargetPosition(motor0.getCurrentPosition() + pulse);
        motor0.setVelocity(desiredRpm/60 * 384.5 * inches/Math.abs(inches));
        motor1.setTargetPosition(motor0.getCurrentPosition() + pulse);
        motor1.setVelocity(desiredRpm/60 * 384.5 * inches/Math.abs(inches));
        motor2.setTargetPosition(motor0.getCurrentPosition() + pulse);
        motor2.setVelocity(desiredRpm/60 * 384.5 * inches/Math.abs(inches));
        motor3.setTargetPosition(motor0.getCurrentPosition() + pulse);
        motor3.setVelocity(desiredRpm/60 * 384.5 * inches/Math.abs(inches));
        dcPos();
        while((inches > 0) ? (motor0.getCurrentPosition() < i0 - 5 || motor1.getCurrentPosition() < i1 - 5 || motor2.getCurrentPosition() < i2 - 5 || motor3.getCurrentPosition() < i3 - 5) : (motor0.getCurrentPosition() > i0 + 5 || motor1.getCurrentPosition() > i1 + 5 || motor2.getCurrentPosition() > i2 + 5 || motor3.getCurrentPosition() > i3 + 5)){
            dcPos();
        }
        sleep(50);
    }

    public int pulses(double inches){
        return (int)(inches / diameter * Math.PI * 384.5);
    }

    public void dcPos(){
        i0 = motor0.getCurrentPosition();
        i1 = motor1.getCurrentPosition();
        i2 = motor2.getCurrentPosition();
        i3 = motor3.getCurrentPosition();
    }

    public void shortInit(){
        //Variable initialization, brake functions and setting proper wheel direction for mecanum drive.
        motor0 = hardwareMap.get(DcMotorEx.class, "motor0");
        motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        motor2 = hardwareMap.get(DcMotorEx.class, "motor2");
        motor3 = hardwareMap.get(DcMotorEx.class, "motor3");
        //Encoders and basic directions; 0 power is set to brake, holding its position
        motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor0.setDirection(DcMotor.Direction.REVERSE);
        motor0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Sets the motors from running by speed and time over to a more consistent pulse-based      //movement
        motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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









