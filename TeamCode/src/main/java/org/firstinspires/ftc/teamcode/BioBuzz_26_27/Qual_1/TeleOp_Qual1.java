package org.firstinspires.ftc.teamcode.BioBuzz_26_27.Qual_1;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;


@TeleOp

public class TeleOp_Qual1 extends OpMode {
    private DcMotor back_right = null;
    private DcMotor front_left = null;
    private DcMotor front_right = null;
    private DcMotor back_left = null;
    private DcMotor intake = null;
    private DcMotor pollenLauncher = null;
    private DcMotor channel = null;

    private double speedMod = 1;

    //Pollen Launcher Variables
    private Servo pollenStopper = null;
    private Servo pollenLauncherVert = null;
    private Servo pollenLauncherHoriz = null;
    private boolean pollenLauncherState = false;
    private boolean lastPollenLauncherState = false;
    private boolean currPollenLauncherState = false;
    private double pollenLauncherPower = 0.5;

    //Channel Variables
    private boolean channelState = false;
    private boolean lastChannelState = false;
    private boolean currChannelState = false;

    //Intake Variables
    private boolean intakeState = false;
    private boolean lastIntakeState = false;
    private boolean currIntakeState = false;

    @Override

    public void init() {
        front_left = hardwareMap.get(DcMotor.class, "lf");
        front_right = hardwareMap.get(DcMotor.class, "rf");
        back_left = hardwareMap.get(DcMotor.class, "lr");
        back_right = hardwareMap.get(DcMotor.class, "rr");
        intake = hardwareMap.get(DcMotor.class, "intake");
        //channel = hardwareMap.get(DcMotor.class, "channel");
        pollenLauncher = hardwareMap.get(DcMotor.class, "launcher");
        //gate = hardwareMap.get(Servo.class, "channel_gate");

        telemetry.addData("Stopper position: ", pollenStopper.getPosition());
        telemetry.addData("Strafe position", back_left.getCurrentPosition());
        telemetry.addData("Left position", front_left.getCurrentPosition());
        telemetry.addData("Right position", back_right.getCurrentPosition());
        telemetry.update();
    }

    @Override

    public void loop() {
        //Driving
        double drive = -1 * gamepad1.left_stick_y;
        double strafe = -gamepad1.right_stick_x;
        double twist = -gamepad1.left_stick_x;
        double[] speeds = {
                -1 * (drive + strafe - twist) * speedMod, //Front Left
                -(-drive + strafe - twist) * speedMod, //Front Right
                (-drive + strafe - twist) * speedMod, //Back Left
                (drive + strafe - twist) * speedMod //Back Right
        };
        double max = Math.abs(speeds[0]);
        for (int i = 0; i < speeds.length; i++) {
            if (max < Math.abs(speeds[i])) max = Math.abs(speeds[i]);
        }
        if (max > 1) {
            for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
        }

        //Motors
        front_left.setPower(speeds[0]);
        front_right.setPower(speeds[1]);
        back_left.setPower(speeds[2]);
        back_right.setPower(speeds[3]);
        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pollenLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pollenLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Methods
        launcherTick();
        intakeTick();
        telemetryThing();
        channel();

    }

    public void launcherTick() {
        currPollenLauncherState = gamepad1.right_trigger > 0.5;
        if(currPollenLauncherState && !lastPollenLauncherState){
            pollenLauncherState = !pollenLauncherState;
        }
        lastPollenLauncherState = currPollenLauncherState;
        if(pollenLauncherState) {
            pollenLauncher.setPower(pollenLauncherPower);
        } else {
            pollenLauncher.setPower(0.0);
        }
    }

    /*public void channelTick(){
        currChannelState = gamepad1.aWasPressed();
        if(currChannelState && !lastChannelState){
            channelState = !channelState
        }
        lastChannelState = currChannelState;
        if(channelState) {
            channel.setPower(1.0);
        } else {
            channel.setPower(0.0);
        }
    }
    */
    public void intakeTick() {
        currIntakeState = gamepad1.left_trigger > 0.5;
        if(currIntakeState && !lastIntakeState){
            intakeState = !intakeState;
        }
        lastIntakeState = currIntakeState;
        if(intakeState) {
            intake.setPower(1.0);
        } else {
            intake.setPower(-1.0);
        }

    }

    /*
    public void stopBalls() {
        if (gamepad1.aWasPressed()) {
            if (gateCheck == 0) {
                gate.setPosition(gateOpenPos);
                gateCheck = 1;
            }
            else {
                gate.setPosition(gateStartPos);
                gateCheck = 0;
            }
        }

    }
     */

    public void telemetryThing() {
        telemetry.addData("Launcher State: ", pollenLauncherState);
        telemetry.addData("Last Launcher State: ", lastPollenLauncherState);
        telemetry.addData("Current Launcher State: ", currPollenLauncherState);
        telemetry.addData("Intake State: ", intakeState);
        telemetry.addData("Last Intake State: ", lastIntakeState);
        telemetry.addData("Current Intake State: ", currIntakeState);
        //telemetry.addData("Strafe position", back_left.getCurrentPosition());
        //telemetry.addData("Left position", front_left.getCurrentPosition());
        //telemetry.addData("Right position", back_right.getCurrentPosition());
        telemetry.update();
    }
    public void channel(){
        currChannelState = gamepad1.bWasPressed();
        if(currChannelState && !lastChannelState){
            channelState = !channelState;
        }
        lastChannelState = currChannelState;
        if(channelState) {
            channel.setPower(1.0);
        } else {
            channel.setPower(0.0);
        }
    }
}