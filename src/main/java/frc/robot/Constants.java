/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //CAN
    //  Victor SPXs
    public static final int leftDriveM = 2;
    public static final int leftDriveS = 1;
    public static final int rightDriveM = 3;
    public static final int rightDriveS = 4;

    public static final int leftClimb = 7;
    public static final int rightClimb = 5;
    public static final int leftWinch = 8;
    public static final int rightWinch = 6;
    
    public static final int controlPanel = 13;

    public static final int intake = 9;
    public static final int delivery = 10;
    public static final int storage = 0;

    //  Talon SRXs
    public static final int outtake1 = 0;
    public static final int outtake2 = 1;    

    //PWM
    public static final int controlPanelLift = 0;


    //Analog
    public static final int ultraMid = 0;

    //DIO
    public static final int photoElectricIntake = 0;
    public static final int photoElectricDelivery = 1;
    public static final int photoElectricOuttake = 2;
    public static final int leftDriveEncoder1 = 6;
    public static final int leftDriveEncoder2 = 5;
    public static final int rightDriveEncoder1 = 3;
    public static final int rightDriveEncoder2 = 4;

    //Autoaim PID constants
    public static final double kP = 0.012;
    public static final double kI = 0.002;
    public static final double kD = 0.009;

    //Encoder constants
    public static final double pulsesPerRotation = 4096;
    //4*360 maybe
    public static final double wheelCircumference = 3*2*Math.PI;

    //Drive constants
    public static final double minDrive = 0.6;
    public static final double minTurn = 0.6;

    //Auto constants
    public static final double autoDrive = -0.8;
    public static final double shootTiming = 2;
    public static final double detectionToShoot = 0.25;
    public static final double threeBallTime = 5;

    //Servo constants
    public static final double servoTheta1 = 37;
    public static final double servoTheta2 = 45;
}
