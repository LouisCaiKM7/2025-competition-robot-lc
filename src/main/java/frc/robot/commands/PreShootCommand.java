package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.utils.DestinationSupplier;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.endeffector.EndEffectorSubsystem;
import frc.robot.subsystems.indicator.IndicatorSubsystem;
import frc.robot.subsystems.indicator.IndicatorIO.Patterns;
import frc.robot.subsystems.intake.IntakeSubsystem;

import static frc.robot.RobotConstants.ElevatorConstants.IDLE_EXTENSION_METERS;

public class PreShootCommand extends Command {
    private final EndEffectorSubsystem endEffectorSubsystem;
    private final IntakeSubsystem intakeSubsystem;
    private final ElevatorSubsystem elevatorSubsystem;
    private final IndicatorSubsystem indicatorSubsystem;
    private double targetPosition = 0.0;


    public PreShootCommand(
        EndEffectorSubsystem endEffectorSubsystem, 
        IntakeSubsystem intakeSubsystem, 
        ElevatorSubsystem elevatorSubsystem,
        IndicatorSubsystem indicatorSubsystem) {
        this.endEffectorSubsystem = endEffectorSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.indicatorSubsystem = indicatorSubsystem;

    }

    @Override
        public void initialize() {
            targetPosition = DestinationSupplier.getInstance().getElevatorSetpoint(true);
            indicatorSubsystem.setPattern(Patterns.HOLDING_CORAL);
    }

    @Override
        public void execute() {
            intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.HOME);
            elevatorSubsystem.setElevatorPosition(targetPosition);
            endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.PRE_SHOOT);
        }

    @Override
    public void end(boolean interrupted) {
        elevatorSubsystem.setElevatorPosition(IDLE_EXTENSION_METERS.get());
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
