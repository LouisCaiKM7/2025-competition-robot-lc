//package frc.robot.commands;
//
//import static frc.robot.RobotConstants.ElevatorConstants.IDLE_EXTENSION_METERS;
//import static frc.robot.RobotConstants.ElevatorConstants.L3_EXTENSION_METERS;
//
//import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.subsystems.elevator.ElevatorSubsystem;
//import frc.robot.subsystems.endeffector.EndEffectorSubsystem;
//import frc.robot.subsystems.intake.IntakeSubsystem;
//
//public class PreShootCommand extends Command{
//    private final EndEffectorSubsystem endEffectorSubsystem;
//    private final IntakeSubsystem intakeSubsystem;
//    private final ElevatorSubsystem elevatorSubsystem;
//    private double elevatorSetPoint;
//    public PreShootCommand(EndEffectorSubsystem endEffectorSubsystem, IntakeSubsystem intakeSubsystem, ElevatorSubsystem elevatorSubsystem, double elevatorSetPoint) {
//        this.endEffectorSubsystem = endEffectorSubsystem;
//        this.intakeSubsystem = intakeSubsystem;
//        this.elevatorSubsystem = elevatorSubsystem;
//        this.elevatorSetPoint = elevatorSetPoint;
//    }
//    @Override
//        public void execute() {
//            intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.FUNNEL_AVOID);
//            elevatorSubsystem.setElevatorPosition(elevatorSetPoint);
//            endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.PRE_SHOOT);
//        }
//
//        @Override
//        public void end(boolean interrupted) {
//            elevatorSubsystem.setElevatorPosition(IDLE_EXTENSION_METERS.get());
//        }
//
//        @Override
//        public boolean isFinished() {
//            return endEffectorSubsystem.isShootFinished();
//        }
//}
