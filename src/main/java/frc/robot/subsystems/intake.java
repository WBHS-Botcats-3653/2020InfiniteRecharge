/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Constants;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class intake extends SubsystemBase {
  private double maxIntake = 1;
  private WPI_VictorSPX m_intake = null;
  private double direction;

  public intake() {
    direction = 0;
    m_intake = new WPI_VictorSPX(Constants.intake);
  }

  public void driveIntake(double speed){
    direction = speed;
    m_intake.set(-speed*maxIntake);
  }
  
  public double getDirection(){
    return direction;
  }
  @Override
  public void periodic() {
    maxIntake = NetworkTableInstance.getDefault().getTable("Preferences").getEntry("maxIntake").getDouble(0.0);
  }
}
