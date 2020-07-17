/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class drive extends SubsystemBase {
  /**
   * Creates a new drive.
   */  
  public double maxDrive = 1;

  private WPI_VictorSPX m_leftDriveMaster = null;
  private WPI_VictorSPX m_leftDriveSlave = null;
  private WPI_VictorSPX m_rightDriveMaster = null;
  private WPI_VictorSPX m_rightDriveSlave = null;

  private SpeedControllerGroup m_leftDrive = null;
  private SpeedControllerGroup m_rightDrive = null;

  private DifferentialDrive m_drive = null;

  public drive() {
    m_leftDriveMaster = new WPI_VictorSPX(Constants.leftDriveM);
    m_rightDriveMaster = new WPI_VictorSPX(Constants.rightDriveM);
    m_leftDriveSlave = new WPI_VictorSPX(Constants.leftDriveS);
    m_rightDriveSlave = new WPI_VictorSPX(Constants.rightDriveS);
    m_leftDriveMaster.setNeutralMode(NeutralMode.Brake);
    m_leftDriveSlave.setNeutralMode(NeutralMode.Brake);
    m_rightDriveMaster.setNeutralMode(NeutralMode.Brake);
    m_rightDriveSlave.setNeutralMode(NeutralMode.Brake);

    m_leftDrive = new SpeedControllerGroup(m_leftDriveMaster, m_leftDriveSlave);
    m_rightDrive = new SpeedControllerGroup(m_rightDriveMaster, m_rightDriveSlave);
    m_leftDrive.setInverted(true);

    m_drive = new DifferentialDrive(m_leftDrive,m_rightDrive);
  }

  public void differentialDrive(double angle, double speed){
    SmartDashboard.putNumber("rotation", angle);
    SmartDashboard.putNumber("speed", speed);
    //DriverStation.reportError("in drive", true);
    m_drive.arcadeDrive(speed,angle);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
