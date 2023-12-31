// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoDrive;
import frc.robot.commands.Drive;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.SetConveyorSpeed;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;

import java.util.HashSet;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
//  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
//
//  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  Joystick leftJoystick = new Joystick(0);
//  Joystick rightJoystick = new Joystick(1);
  Drivetrain drivetrain = new Drivetrain();
  Conveyor conveyor = new Conveyor(new VictorSP(2));
  Drive drive = new Drive(drivetrain, leftJoystick);

  JoystickButton joystickButton = new JoystickButton(leftJoystick, 3);
  JoystickButton joystickButton2 = new JoystickButton(leftJoystick, 2);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    drivetrain.setDefaultCommand(drive);
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    joystickButton
            .whileTrue(new SetConveyorSpeed(conveyor, 1D))
            .whileFalse(() -> {
              if (joystickButton2.getAsBoolean()) {
                new SetConveyorSpeed(conveyor, -1D).execute();
              } else {
                new SetConveyorSpeed(conveyor, 0D).execute();
              }
                HashSet<Subsystem> ret = new HashSet<>();
                ret.add(conveyor);
                return ret;
            });
    joystickButton2
            .whileTrue(new SetConveyorSpeed(conveyor, -1D))
            .whileFalse(() -> {
              if (joystickButton.getAsBoolean()) {
                new SetConveyorSpeed(conveyor, 1D).execute();
              } else {
                new SetConveyorSpeed(conveyor, 0D).execute();
              }
              HashSet<Subsystem> ret = new HashSet<>();
              ret.add(conveyor);
              return ret;
            });
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new AutoDrive(drivetrain, 0.5, 0.5, 2);
  }
}
