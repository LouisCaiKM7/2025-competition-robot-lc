package frc.robot.commands;

import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

public class ElevatordownCommand extends Command{
    private final ElevatorSubsystem elevatorSubsystem;
    public ElevatordownCommand(ElevatorSubsystem elevatorSubsystem){
        this.elevatorSubsystem = elevatorSubsystem;
        addRequirements(elevatorSubsystem);
    }
    @Override
    public void initialize(){}

    @Override
    public void execute(){
        elevatorSubsystem.getIo().setElevatorDirectVoltage(Volts.of(1));
    }
    
    @Override
    public void end(boolean interrupted){
        elevatorSubsystem.getIo().setElevatorDirectVoltage(Volts.zero());
    }
}
