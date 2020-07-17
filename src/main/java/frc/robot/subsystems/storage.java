/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

public class storage extends SubsystemBase {
  /**
   * Creates a new storage.
   */
  private WPI_VictorSPX m_storage = null;
  private double maxStorage = 1;
  private double direction;

  public storage() {
    direction = 0;
    m_storage = new WPI_VictorSPX(Constants.storage);
  }

  public void storageDrive(double speed){
    DriverStation.reportError("in storage", true);
    direction = speed;
    m_storage.set(-speed*maxStorage);
  }

  public double getDirection(){
    return direction;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    maxStorage = NetworkTableInstance.getDefault().getTable("Preferences").getEntry("maxStorage").getDouble(0.0);

  }
}
