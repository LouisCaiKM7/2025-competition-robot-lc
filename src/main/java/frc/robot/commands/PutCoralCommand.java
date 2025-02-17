//package frc.robot.commands;
//
//import static frc.robot.RobotContainer.driverController;
//
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Commands;
//import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
//import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
//import frc.robot.subsystems.elevator.ElevatorSubsystem;
//import frc.robot.subsystems.endeffector.EndEffectorSubsystem;
//import frc.robot.subsystems.intake.IntakeSubsystem;
//
//public class PutCoralCommand extends ParallelCommandGroup{
//
//    public PutCoralCommand(EndEffectorSubsystem endeffectorSubsystem, ElevatorSubsystem elevatorSubsystem, IntakeSubsystem intakeSubsystem,double elevatorSetPoint) {
//        addCommands(
//            Commands.parallel(
//                new PreShootCommand(endeffectorSubsystem, intakeSubsystem, elevatorSubsystem, elevatorSetPoint),
//                Commands.sequence(
//                    new WaitUntilCommand(()->(driverController.a().getAsBoolean() && endeffectorSubsystem.isShootReady())),
//                    new ShootCommand(endeffectorSubsystem)
//                )
//            )
//        );
//    }
//}
