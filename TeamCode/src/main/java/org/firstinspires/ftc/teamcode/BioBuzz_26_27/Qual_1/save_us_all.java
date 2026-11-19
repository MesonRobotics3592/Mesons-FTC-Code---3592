//------------------------------------------------Imports------------------------------------------------------
package org.firstinspires.ftc.teamcode.BioBuzz_26_27.Qual_1;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import  org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.*;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

@Autonomous

public class save_us_all extends OpMode {
    //------------------------------------------------------NullInit----------------------------------------------

    private Limelight3A limelight;

    //Motors
    private DcMotorEx front_left = null;
    private DcMotorEx front_right = null;
    private DcMotorEx back_right = null;
    private DcMotorEx back_left = null;
    //private DcMotorEx launcher = null;

    //Random vars
    int lf_pos, rf_pos, rr_pos, lr_pos;// i# is the pos of the motor
    int t0, t1, t2, t3;
    int tolerance = 6;

    @Override
    public void init() {
        shortInit();
    }

    @Override
    public void start() {
        sleep(2000);
        strafe(5);
    }

    @Override
    public void loop(){
        //LEAVE BLANK
    }


    public void drive(int dist){
        dcPos();
        t0 = lf_pos + pulses(dist);
        t1 = rf_pos + pulses(dist);
        t2 = rr_pos + pulses(dist);
        t3 = lr_pos + pulses(dist);
        front_left.setTargetPosition(t0);
        front_right.setTargetPosition(t1);
        back_right.setTargetPosition(t2);
        back_left.setTargetPosition(t3);
        int notAbs = dist/Math.abs(dist);
        front_left.setVelocity(1500 * notAbs);
        front_right.setVelocity(-1500 * notAbs);
        back_right.setVelocity(1500 * notAbs);
        back_left.setVelocity(-1500 * notAbs);

        while((dist > 0) ? (lf_pos < t0 - tolerance || rf_pos < t1 - tolerance || rr_pos < t2 - tolerance || lr_pos < t3 - tolerance) : (lf_pos > t0 + tolerance || rf_pos > t1 + tolerance || rr_pos > t2 + tolerance || lr_pos > t3 + tolerance)){
            dcPos();
        }
        sleep(100);
    }

    public void twist(int deg){
        dcPos();
        int pulse = (int)(pulses(deg)/3.7);
        t0 = lf_pos + pulse;
        t1 = rf_pos - pulse;
        t2 = rr_pos - pulse;
        t3 = lr_pos + pulse;
        front_left.setTargetPosition(t0);
        front_right.setTargetPosition(t1);
        back_right.setTargetPosition(t2);
        back_left.setTargetPosition(t3);
        int notAbs = deg/Math.abs(deg);
        front_left.setVelocity(1500 * notAbs);
        front_right.setVelocity(1500 * notAbs);
        back_right.setVelocity(1500 * notAbs);
        back_left.setVelocity(1500 * notAbs);
        while((deg > 0) ? (lf_pos < t0 - tolerance || rf_pos > t1 + tolerance || rr_pos > t2 + tolerance || lr_pos < t3 - tolerance) : (lf_pos > t0 + tolerance || rf_pos < t1 - tolerance || rr_pos < t2 - tolerance || lr_pos > t3 + tolerance)){
            dcPos();
        }
        sleep(100);
    }

    public void strafe(int dist){
        dcPos();
         t0 = lf_pos + pulses(dist);
         t1 = rf_pos - pulses(dist);
         t2 = rr_pos + pulses(dist);
         t3 = lr_pos - pulses(dist);
        front_left.setTargetPosition(t0);
        front_right.setTargetPosition(t1);
        back_right.setTargetPosition(t2);
        back_left.setTargetPosition(t3);
        int notAbs = dist/Math.abs(dist);
        front_left.setVelocity(1500 * notAbs);
        front_right.setVelocity(1500 * notAbs);
        back_right.setVelocity(1500 * notAbs);
        back_left.setVelocity(1500 * notAbs);
        while((dist > 0) ? (lf_pos < t0 - tolerance || rf_pos > t1 + tolerance || rr_pos < t2 - tolerance || lr_pos > t3 + tolerance) : (lf_pos > t0 + tolerance || rf_pos < t1 - tolerance || rr_pos > t2 + tolerance || lr_pos < t3 - tolerance)){
            dcPos();
        }
        sleep(100);
    }

    public void shoot(){
        
    }

    public void dcPos(){
        lf_pos = front_left.getCurrentPosition();
        rf_pos = front_right.getCurrentPosition();
        rr_pos = back_right.getCurrentPosition();
        lr_pos = back_left.getCurrentPosition();
    }

    public void intake(int dist){

    }

    public void sleep(long time){
        //Waits in milliseconds
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int pulses(int dist){
        //dist in inches
        return((int)(dist / (Math.PI *  3.8) * 384.5));
    }

    public void shortInit(){
        front_left = hardwareMap.get(DcMotorEx.class, "lf");// Front left 0
        front_right = hardwareMap.get(DcMotorEx.class, "rf");// Front Right 1
        back_right = hardwareMap.get(DcMotorEx.class, "rr");// Back Right 2
        back_left = hardwareMap.get(DcMotorEx.class, "lr");// Back Left 3
        
        front_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /*
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
        */
    }

}