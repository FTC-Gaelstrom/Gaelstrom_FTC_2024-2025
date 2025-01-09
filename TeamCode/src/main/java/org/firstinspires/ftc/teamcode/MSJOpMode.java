/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

/**
 * Gamepad1: Driver
 * Gamepad2: Controller
 */

//Push to GH after commit
@TeleOp(name="Basic: MSJ Linear OpMode", group="Linear Opmode")
//@Disabled
public class MSJOpMode extends LinearOpMode {

    MSJHardware robot = new MSJHardware();

    private ElapsedTime runtime = new ElapsedTime();

    private boolean macroToggle = false;
    public void macro() {
        if (macroToggle){
            macroToggle = false;
        } else {
            macroToggle = true;
        }
        robot.armMotor.setPower(0); //change
        robot.wristServo.setPosition(0); //change
        robot.clawServo.setPosition(0); //change
    }

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        double pos = 0.5;

        //int count = 0; //unused from previous build

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Path0", "Starting at %7d :%7d",
                    robot.frontLeftMotor.getCurrentPosition(),
                    robot.frontRightMotor.getCurrentPosition(),
                    robot.backLeftMotor.getCurrentPosition(),
                    robot.backRightMotor.getCurrentPosition()
            );

            double frontRightPower;
            double frontLeftPower;
            double backRightPower;
            double backLeftPower;

            double lsx = gamepad1.left_stick_x;     // desmos: X
            double rsx = gamepad1.right_stick_x;    // desmos: Y
            double lsy = gamepad1.left_stick_y;     // desmos: Z
            //double rsy = gamepad1.right_stick_y;

            // movement v1:
            // - left stick for drive/turn
            // - right stick for strafing
            // (according to Curtis)
            frontRightPower = -Range.clip(lsx + lsy + rsx, -.5, .5);
            frontLeftPower = -Range.clip(rsx + lsx - lsy, -.5, .5);
            backRightPower = Range.clip(-lsx - lsy + rsx, -.5, .5);
            backLeftPower = -Range.clip(-lsx + lsy + rsx, -.5, .5);

            //robot.LinActMotor.setPower(gamepad1.right_stick_y);

            robot.frontRightMotor.setPower(frontRightPower);
            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.backRightMotor.setPower(backRightPower);
            robot.backLeftMotor.setPower(backLeftPower);

            telemetry.addData("Front Right Motor", "frontRightMotor", frontRightPower);
            telemetry.addData("Front Left Motor", "frontLeftMotor", frontLeftPower);
            telemetry.addData("Back Right Motor", "backRightMotor", backRightPower);
            telemetry.addData("Back Left Motor", "backLeftMotor", backLeftPower);

            /*if (gamepad2.dpad_up) {
                robot.armServo.setPosition(1);
            } else if (gamepad2.dpad_down) {
                robot.armServo.setPosition(0.2);
            }*/

            if (gamepad2.left_stick_y == 0)
                robot.armMotor.setPower(-0.1);
            else
                robot.armMotor.setPower(0.3 * gamepad2.left_stick_y);

            if (gamepad2.right_bumper) {
                robot.clawServo.setPosition(0);
            }

            if (gamepad2.left_bumper) {
                robot.clawServo.setPosition(0.5);
            }

            // "Macro" to raise the arm into position with one button press
            boolean PLACEHOLDER = false; //replace with desired keystroke
            if (PLACEHOLDER) {
                macro();
            }

            boolean PLACEHOLDER2 = false; //replace with desired keystroke
            if (PLACEHOLDER2) {
                //thing
            }

            /*
            if (gamepad2.a){
                if (count % 2 == 0) {
                    robot.clawServo.setPosition(0.5);
                    count++;
                } else {
                    robot.clawServo.setPosition(0);
                    count++;
                }
            }
            */

            telemetry.update();

        }
    }
}
