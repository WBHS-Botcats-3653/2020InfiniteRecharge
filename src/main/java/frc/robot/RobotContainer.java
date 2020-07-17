/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  
  public int climbMode = 0;

  private SendableChooser<Command> autoCommand = new SendableChooser();
  private UsbCamera cam0 = null;

  private final climb m_climb = new climb();
  private final drive m_drive = new drive();
  private final outtake m_out = new outtake();
  private final conveyer m_con = new conveyer();
  private final intake m_in = new intake();
  private final limelight m_lime = new limelight();
  private final storage m_store = new storage();
  private final climbMode m_climbMode = new climbMode();
  private final controlPanel m_control = new controlPanel();
  private final driveSensors m_drivesensors = new driveSensors();
  private final digitalinputs m_digital = new digitalinputs();
  private final XboxController m_controller = new XboxController(0);
  private  final ultrasonic m_ultra = new ultrasonic();
   /* The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //autoCommand.addOption("Move past line", new autoDrive(m_drive,m_drivesensors,-8));
    autoCommand.setDefaultOption("Shoot 3, move past line", 
    new ParallelCommandGroup(
      new SequentialCommandGroup(
        new WaitCommand(Constants.shootTiming),
        new autoAim(m_lime,m_drive,()->0),
        new autoShoot(m_con, m_digital,m_store,m_lime),
        new autoArcade(m_drive,m_drivesensors,-4,0.5,40,0.5)
      ),
      new outtakeEngage(m_out,m_lime,1)/*,
      new SequentialCommandGroup(
        new WaitCommand(Constants.threeBallTime),
        new autoArcade(m_drive,m_drivesensors,-4,0.5,-90,0.55)
      )*/
    ));
    SmartDashboard.putData("Auto Command",autoCommand);
    
    
    cam0 = CameraServer.getInstance().startAutomaticCapture(0);
		cam0.setResolution(72, 45);
		cam0.setFPS(20);

    // Configure the button bindings
    m_drive.setDefaultCommand(
        new differentialDrive(m_drive, () -> m_controller.getY(GenericHID.Hand.kLeft)*((1-m_climbMode.climbMode)*0.25+0.75), () -> m_controller.getX(GenericHID.Hand.kRight)*((1-m_climbMode.climbMode)*0.25+0.75)));
        //new testControllerInputs(m_controller,m_drive));
        configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void configureButtonBindings() {
    double trigEpsilon = 0.3;
    Trigger buttonA = new Trigger(m_controller::getAButton);
    Trigger buttonB = new Trigger(m_controller::getBButton);
    Trigger buttonX = new Trigger(m_controller::getXButton);
    Trigger buttonY = new Trigger(m_controller::getYButton);
    JoystickButton leftBump = new JoystickButton(m_controller,5);
    JoystickButton rightBump = new JoystickButton(m_controller,6);
    Trigger rightTrig = new Trigger(
      () -> {
             return (m_controller.getTriggerAxis(GenericHID.Hand.kRight)>trigEpsilon);
            }
      );
    Trigger leftTrig = new Trigger(
      () -> {
              return (m_controller.getTriggerAxis(GenericHID.Hand.kLeft)>trigEpsilon);
            }
    );
    Trigger backButton = new Trigger(m_controller::getBackButton);
    Trigger startButton = new Trigger(m_controller::getStartButton);
    Trigger bottomDPad = new Trigger(
      () -> {
              return (m_controller.getPOV(0)>135 && m_controller.getPOV(0)<225);
            }
    );
    Trigger leftDPad = new Trigger(
      () -> {
              return (m_controller.getPOV(0)>225 && m_controller.getPOV(0)<315);
            }
    );
    Trigger rightDPad = new Trigger(
      () -> {
              return (m_controller.getPOV(0)>45 && m_controller.getPOV(0)<135);
            }
    );
    Trigger topDPad = new Trigger(
      () -> {
              return (m_controller.getPOV(0)>=315 || (m_controller.getPOV(0)<=45 && m_controller.getPOV(0)>0));
            }
    );
    Trigger climbMode = new Trigger(
      () -> {
              //m_controller.setRumble(RumbleType.kLeftRumble,1);
              if(m_climbMode.climbMode == 1){
                return true;
              } else {
                return false;
              }
            }
    );
    
    backButton.whenActive(
      new SequentialCommandGroup(
        new toggleClimbMode(m_climbMode),
        new rumble(m_controller,true),
        new WaitCommand(0.5),
        new rumble(m_controller,false)
        ));
    //buttonA.whileActiveContinuous(new intakeEngage(m_in,1));
    
    //(climbMode.negate()).and(topDPad).toggleWhenActive(new controlLift(m_control,Constants.servoTheta1));
    (climbMode.negate()).and(bottomDPad).toggleWhenActive(new controlLift(m_control,Constants.servoTheta1));
    (climbMode.negate()).and(leftDPad).whileActiveOnce(new controlSpin(m_control,-1));
    (climbMode.negate()).and(rightDPad).whileActiveOnce(new controlSpin(m_control,1));
    (climbMode.negate()).and(buttonX).whileActiveOnce(new intakeEngage(m_in,1));
    (climbMode.negate()).and(buttonB).whileActiveOnce(new deliveryEngage(m_con,1));
    (climbMode.negate()).and(buttonA).whileActiveOnce(new storageEngage(m_store,1));
    (climbMode.negate()).and(rightTrig).whileActiveOnce(new outtakeEngage(m_out,m_lime,1));
    (climbMode.negate()).and(buttonY.and(buttonX)).whileActiveOnce(new intakeEngage(m_in,-1));
    (climbMode.negate()).and(buttonY.and(buttonB)).whileActiveOnce(new deliveryEngage(m_con,-1));
    (climbMode.negate()).and(buttonY.and(buttonA)).whileActiveOnce(new storageEngage(m_store,-1));
    (climbMode.negate()).and(buttonY.and(rightTrig)).whileActiveOnce(new outtakeEngage(m_out,m_lime,-1));
    (climbMode.negate()).and(leftTrig).whileActiveOnce(new autoAim(m_lime,m_drive,() -> m_controller.getY(GenericHID.Hand.kLeft)));
    //(climbMode.negate()).and(rightBump).whenActive(new changePipeline(m_lime));
    (climbMode.negate()).and(leftBump).whenActive(new changeMode(m_lime));

    climbMode.and(buttonX.or(buttonB)).whileActiveOnce(new winchEngage(m_climb,1,() -> m_controller.getXButton(),() -> m_controller.getBButton()));
    climbMode.and(rightTrig.or(leftTrig)).whileActiveOnce(new climbEngage(m_climb,-1,
    () -> { return (m_controller.getTriggerAxis(GenericHID.Hand.kLeft)>trigEpsilon); },
    () -> { return (m_controller.getTriggerAxis(GenericHID.Hand.kRight)>trigEpsilon); }));
    climbMode.and(rightBump.or(leftBump)).whileActiveOnce(new climbEngage(m_climb,1,() -> m_controller.getBumper(GenericHID.Hand.kLeft),() -> m_controller.getBumper(GenericHID.Hand.kRight)));
    climbMode.and(buttonY.and(buttonX.or(buttonB))).whileActiveOnce(new winchEngage(m_climb,-1,() -> m_controller.getXButton(),() -> m_controller.getBButton()));
    /*
    rightTrig.whileActiveOnce(new ParallelCommandGroup(new intakeEngage(m_in,1), new storageEngage(m_store)));
    leftTrig.whileActiveOnce(new ParallelCommandGroup(new deliveryEngage(m_con,1), new outtakeEngage(m_out,1)));
    leftTrig.and(rightBump).whileActiveOnce(new ParallelCommandGroup(new deliveryEngage(m_con,-1), new outtakeEngage(m_out,-1)));
    (buttonA.or(buttonB)).and(rightBump.negate()).whileActiveOnce(new climbEngage(m_climb,1,() -> m_controller.getAButton(),() -> m_controller.getBButton()));
    buttonY.or(buttonX).whileActiveOnce(new winchEngage(m_climb,1,() -> m_controller.getXButton(),() -> m_controller.getYButton()));
    (buttonA.or(buttonB)).and(rightBump).whileActiveOnce(new climbEngage(m_climb,-1,() -> m_controller.getAButton(),() -> m_controller.getBButton()));
    */

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand(){
    return autoCommand.getSelected();
  }

}
