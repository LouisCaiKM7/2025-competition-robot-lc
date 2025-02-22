package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.utils.DestinationSupplier;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.endeffector.EndEffectorSubsystem;
import frc.robot.subsystems.indicator.IndicatorSubsystem;
import frc.robot.subsystems.indicator.IndicatorIO.Patterns;
import frc.robot.subsystems.intake.IntakeSubsystem;

public class PokeCommand extends Command {
    private final EndEffectorSubsystem endEffectorSubsystem;
    private final IntakeSubsystem intakeSubsystem;
    private final ElevatorSubsystem elevatorSubsystem;
    private final IndicatorSubsystem indicatorSubsystem;
    private double lastPosition = 0.0;

    public PokeCommand(
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
        lastPosition = DestinationSupplier.getInstance().getElevatorSetpoint(true);
        indicatorSubsystem.setPattern(Patterns.POKING);
    }

    @Override
    public void execute() {
        intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.HOME);
        elevatorSubsystem.setElevatorPosition(DestinationSupplier.getInstance().getElevatorSetpoint(false));
        endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.POKE);
    }

    @Override
    public void end(boolean interrupted) {
        elevatorSubsystem.setElevatorPosition(lastPosition);
        endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.IDLE);
        indicatorSubsystem.setPattern(Patterns.NORMAL);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
