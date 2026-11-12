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
    private DcMotor launcher = null;
    private Servo gate = null;
    private double speedMod = 1;

    //Launcher Variables
    private boolean launcherState = false;
    //launcher rising edge detection
    private boolean lastLauncherState = false;
    private boolean currLauncherState = false;
    private boolean intakeState = false;
    private boolean lastIntakeState = false;
    private boolean currIntakeState = false;

    //Gate Variables
    private int gateCheck = 0;
    private double gateStartPos = 0.4;
    private double gateOpenPos = 0.8;

    @Override

    public void init() {
        front_left = hardwareMap.get(DcMotor.class, "lf");
        front_right = hardwareMap.get(DcMotor.class, "rf");
        back_left = hardwareMap.get(DcMotor.class, "lr");
        back_right = hardwareMap.get(DcMotor.class, "rr");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(DcMotor.class, "launcher");
        gate = hardwareMap.get(Servo.class, "channel_gate");
        telemetry.addData("Launcher State: ", launcherState);
        telemetry.addData("Last Launcher State: ", lastLauncherState);
        telemetry.addData("Current Launcher State: ", currLauncherState);
        telemetry.addData("Intake State: ", intakeState);
        telemetry.addData("Last Intake State: ", lastIntakeState);
        telemetry.addData("Current Intake State: ", currIntakeState);
        telemetry.addData("Stopper position: ", gate.getPosition());
        gate.setPosition(gateStartPos);
    }

    @Override

    public void loop() {
        //Driving
        double drive = -1 * gamepad1.left_stick_y;
        double strafe = gamepad1.right_stick_x;
        double twist = gamepad1.left_stick_x;
        double[] speeds = {
                -1 * (-drive + strafe - twist) * speedMod,
                -(drive + strafe - twist) * speedMod,
                (drive - strafe - twist) * speedMod,
                (-drive - strafe - twist) * speedMod
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
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Methods
        launcherTick();
        intakeTick();
        stopBalls();

        //Telemetry Update
        telemetry.update();
    }

    public void launcherTick() {
        currLauncherState = gamepad1.right_trigger > 0.5;
        if(currLauncherState && !lastLauncherState){
            launcherState = !launcherState;
        }
        lastLauncherState = currLauncherState;
        if(launcherState) {
            launcher.setPower(1.0);
        } else {
            launcher.setPower(0.0);
        }
    }

    public void intakeTick() {
        currIntakeState = gamepad1.left_trigger > 0.5;
        if(currIntakeState && !lastIntakeState){
            intakeState = !intakeState;
        }
        lastIntakeState = currIntakeState;
        if(intakeState) {
            intake.setPower(1.0);
        } else {
            intake.setPower(0.0);
        }
    }

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
}