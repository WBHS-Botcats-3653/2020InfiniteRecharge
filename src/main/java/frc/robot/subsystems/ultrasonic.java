/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ultrasonic extends SubsystemBase {
  /**
   * Creates a new ultrasonic.
   */
  AnalogInput ultrasonicMid = null;
  double scaling = 1;

  public ultrasonic() {
    ultrasonicMid = new AnalogInput(Constants.ultraMid);
    ultrasonicMid.setAverageBits(8);
  }
  public double getAvgVoltage(){
    return ultrasonicMid.getAverageValue();
  }
  public double getValue(){
    return ultrasonicMid.getValue();
  }
  public double getVoltage(){
    return ultrasonicMid.getVoltage();
  }

  public double getDistance(){
    return ultrasonicMid.getAverageVoltage()/scaling;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("ultravalue", getDistance()*100);
  }
}
