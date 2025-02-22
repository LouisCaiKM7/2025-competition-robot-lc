package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.endeffector.EndEffectorSubsystem;
import frc.robot.subsystems.indicator.IndicatorSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;

public class PutCoralCommand extends ParallelCommandGroup {
    public PutCoralCommand(CommandXboxController driverController, 
                           EndEffectorSubsystem endeffectorSubsystem,
                           ElevatorSubsystem elevatorSubsystem, 
                           IntakeSubsystem intakeSubsystem,
                           IndicatorSubsystem indicatorSubsystem) {
        addCommands(
                Commands.deadline(
                        new PreShootCommand(endeffectorSubsystem, intakeSubsystem, elevatorSubsystem, indicatorSubsystem),
                        Commands.sequence(
                                new WaitUntilCommand(() -> (driverController.rightTrigger().getAsBoolean() && endeffectorSubsystem.isShootReady())),
                                new ShootCommand(endeffectorSubsystem,indicatorSubsystem)
                        )
                )
        );
    }
}
