/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class climb extends SubsystemBase {
  // Initialization of motor objects and variables
  private WPI_VictorSPX m_lClimb = null;
  private WPI_VictorSPX m_rClimb = null;
  private WPI_VictorSPX m_rWinch = null;
  private WPI_VictorSPX m_lWinch = null;

  private double maxWinch = 1;
  private double maxClimb = 1;

  public climb() {
    m_rClimb = new WPI_VictorSPX(Constants.rightClimb);
    m_rWinch = new WPI_VictorSPX(Constants.rightWinch);
    m_lClimb = new WPI_VictorSPX(Constants.leftClimb);
    m_lWinch = new WPI_VictorSPX(Constants.leftWinch);
  }
  
  // Methods that set climb and winch motor speeds
  public void driveLeftClimb(double speed){
    m_lClimb.set(speed*maxClimb);
  }

  public void driveRightClimb(double speed){
    m_rClimb.set(-speed*maxClimb);
  }

  public void driveLeftWinch(double speed){
    m_lWinch.set(-speed*maxWinch);
  }

  public void driveRightWinch(double speed){
    m_rWinch.set(speed*maxWinch);
  }


  @Override
  public void periodic() {
    // Grabs climb and winch max speeds from SmartDashboard
    maxClimb = NetworkTableInstance.getDefault().getTable("Preferences").getEntry("maxClimb").getDouble(0.0);
    maxWinch = NetworkTableInstance.getDefault().getTable("Preferences").getEntry("maxWinch").getDouble(0.0);
  }
}
