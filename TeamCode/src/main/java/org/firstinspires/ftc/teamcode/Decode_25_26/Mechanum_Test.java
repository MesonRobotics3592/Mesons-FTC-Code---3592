package org.firstinspires.ftc.teamcode.Decode_25_26;/*
//Import all necessary libraries, we need april tag and vision portal/webcam imports later on
package org.firstinspires.ftc.teamcode.Decode_25_26;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import java.util.List;

@TeleOp(name="Mechanum_Test", group="Iterative Opmode")

public class Mechanum_Test extends OpMode {
    //All Motors
    private DcMotorEx back_right = null;
    private DcMotorEx front_left = null;
    private DcMotorEx front_right = null;
    private DcMotorEx back_left = null;

    @Override

    public void init(){
//Assigning all motors to the hardware map
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
//if needed
//back_right.setDirection(DcMotorEx.Direction.REVERSE);
    }

    @Override

    public void loop(){

            }

    Public void moveTick(){
//movement formula
        double[] speeds ={
                (drive - strafe + twist),
                (drive + strafe - twist),
                (drive - strafe - twist),
                (drive + strafe + twist)
        };
    }

}
*/