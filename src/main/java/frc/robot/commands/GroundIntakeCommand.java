//package frc.robot.commands;
//
//import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.subsystems.elevator.ElevatorSubsystem;
//import frc.robot.subsystems.intake.IntakeSubsystem;
//import frc.robot.subsystems.endeffector.EndEffectorSubsystem;
//import static frc.robot.RobotConstants.ElevatorConstants.*;
//
//// Represents a command to deploy the intake and prepare for ground intake operation
//public class GroundIntakeCommand extends Command{
//
//    private final IntakeSubsystem intakeSubsystem;
//    private final EndEffectorSubsystem endEffectorSubsystem;
//    private final ElevatorSubsystem elevatorSubsystem;
//
//    public GroundIntakeCommand(IntakeSubsystem intakeSubsystem, EndEffectorSubsystem endEffectorSubsystem, ElevatorSubsystem elevatorSubsystem) {
//        this.intakeSubsystem = intakeSubsystem;
//        this.endEffectorSubsystem = endEffectorSubsystem;
//        this.elevatorSubsystem = elevatorSubsystem;
//    }
//    // Called repeatedly when this Command is scheduled to run
//    @Override
//    public void execute() {
//        intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.DEPLOY_INTAKE);
//        endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.GROUND_INTAKE);
//        elevatorSubsystem.setElevatorPosition(0.03);
//    }
//
//    // Called once after isFinished returns true or when another command interrupts this one
//    @Override
//    public void end(boolean interrupted) {
//        intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.FUNNEL_AVOID);
//        elevatorSubsystem.setElevatorPosition(IDLE_EXTENSION_METERS.get());
//    }
//
//    // Returns true when this Command no longer needs to run execute()
//    @Override
//    public boolean isFinished() {
//        return endEffectorSubsystem.hasCoral();
//    }
//}