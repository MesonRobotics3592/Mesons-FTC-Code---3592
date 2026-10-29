package org.firstinspires.ftc.teamcode.BioBuzz_26_27;

//Import all necessary libraries, we need april tag and vision portal/webcam imports later on

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;


@TeleOp
//@Disabled
//Include @Disabled or comment out the OpMode to remove the program from the Driver Hub.

public class TeleOp_Test extends OpMode {

    private DcMotor back_right = null;
    private DcMotor front_left = null;
    private DcMotor front_right = null;
    private DcMotor back_left = null;
    private DcMotor intake = null;
    private DcMotor launcher = null;
    private Servo gate = null;
    private double speedMod = 1;
    private int launcherCheck = 0;
    private int intakeCheck = 0;
    private int launcherState = 0;
    private int intakeState = 0;
    private int gateCheck = 0;


    @Override

    public void init() {
        front_left = hardwareMap.get(DcMotor.class, "front_left");
        front_right = hardwareMap.get(DcMotor.class, "front_right");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        back_right = hardwareMap.get(DcMotor.class, "back_right");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(DcMotor.class, "launcher");
        gate = hardwareMap.get(Servo.class, "channel_gate");
        telemetry.addData("Launcher State: ", launcherState);
    }

    @Override

    public void loop() {
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
        launcherTick();
        intakeTick();
        stopBalls();
        telemetry.addData("Launcher State: ", launcherState);
        telemetry.addData("Launcher Check", launcherCheck);
        telemetry.addData("Intake State: ", intakeState);
        telemetry.addData("Intake Check", intakeCheck);
        telemetry.addData("Stopper position", gate.getPosition());
        telemetry.update();

    }

    public void launcherTick() {
        if (gamepad1.right_trigger_pressed) {
            if (launcherCheck == 0) {
                if (launcherState == 0) {
                    launcher.setPower(1);
                    launcherState = 1;
                } else if (launcherState == 1) {
                    launcher.setPower(0);
                    launcherState = 0;
                } else {
                    launcherCheck = 0;
                }
            }
        }

    }
    public void intakeTick() {
        if (gamepad1.left_trigger_pressed) {
            if (intakeCheck == 0) {
                if (intakeState == 0) {
                    intake.setPower(.75);
                    intakeState = 1;
                } else if (intakeState == 1) {
                    intake.setPower(0);
                    intakeState = 0;
                } else {
                    intakeCheck = 0;
                }
            }
        }

    }
    public void stopBalls() {
        if (gamepad1.aWasPressed()) {
            if (gateCheck == 0) {
                gate.setPosition(-.6);
                gateCheck = 1;
                } else {
                gate.setPosition(0);
                gateCheck = 0;
                }
            }
        }

    }