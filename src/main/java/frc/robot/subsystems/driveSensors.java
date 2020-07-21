/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class driveSensors extends SubsystemBase {
  Encoder leftEncoder, rightEncoder;
  ADXRS450_Gyro gyro = null;

  public driveSensors() {
    leftEncoder = new Encoder(Constants.leftDriveEncoder1, Constants.leftDriveEncoder2);
    rightEncoder = new Encoder(Constants.rightDriveEncoder1, Constants.rightDriveEncoder2);
    gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);

    leftEncoder.setDistancePerPulse(Constants.wheelCircumference/Constants.pulsesPerRotation);
    rightEncoder.setDistancePerPulse(Constants.wheelCircumference/Constants.pulsesPerRotation);
    leftEncoder.setReverseDirection(true);
  }
 
  public void resetEncoders(){
    leftEncoder.reset();
    rightEncoder.reset();
  }

  public double getAngle(){
    return gyro.getAngle();
  }

  public double getRate(){
    return gyro.getRate();
  }

  public double getLeftDistance(){
    return leftEncoder.getDistance();
  }

  public double getRightDistance(){
    return rightEncoder.getDistance();
  }

  public double getLeftRate(){
    return leftEncoder.getRate();
  }

  public double getRightRate(){
    return rightEncoder.getRate();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("leftEncoder",getLeftDistance());
    SmartDashboard.putNumber("rightEncoder",getRightDistance());
    SmartDashboard.putNumber("gyro", getAngle());
    // This method will be called once per scheduler run
  }
}
