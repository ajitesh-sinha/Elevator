package com.ajitesh.learning.elevator.eventhandler

import com.ajitesh.learning.elevator.Elevator
import com.ajitesh.learning.elevator.controller.ElevatorControl
import com.ajitesh.learning.elevator.enum.Direction
import com.ajitesh.learning.elevator.enum.Floor

class FloorPanelEventHandler (private val elevator: Elevator): EventHandler {

    override fun sendElevator() {
        while (ElevatorControl.callingFloors.isNotEmpty()) {
            val callingFloor = ElevatorControl.callingFloors[0]
            startElevator(callingFloor)
            ElevatorControl.callingFloors.removeAt(0)
        }
        while (ElevatorControl.callingFloorsInQueue.isNotEmpty()) {
            val callingFloor = ElevatorControl.callingFloorsInQueue[0]
            startElevator(callingFloor)
            ElevatorControl.callingFloorsInQueue.removeAt(0)
        }
        elevator.direction = Direction.STILL
        println("Elevator is halted now.")
    }

    private fun startElevator(callingFloor: Floor) {
        when {
            elevator.position > callingFloor -> {
                println("Elevator coming down from floor ${elevator.position} to floor $callingFloor")
                elevator.direction = Direction.DOWN
                updateElevatorPosition(callingFloor)
            }
            elevator.position < callingFloor -> {
                println("Elevator coming up from floor ${elevator.position} to floor $callingFloor")
                elevator.direction = Direction.UP
                updateElevatorPosition(callingFloor)
            }
            else -> {
                println("Elevator is on the same floor !")
            }
        }
    }

    private fun updateElevatorPosition(callingFloor: Floor){
        val updatedCallingFloor = trackPositionOfMovingElevator(callingFloor)
        elevator.position = updatedCallingFloor
        println("Elevator is now at destination floor: ${elevator.position}")
    }

    private fun trackPositionOfMovingElevator(callingFloor: Floor): Floor {
        var counter = 0
        var floor = callingFloor
        when (elevator.direction) {
            Direction.DOWN -> {
                counter = elevator.position.floorNumber
                println("\t### At floor number $counter ###")
                while (counter > floor.floorNumber) {
                    Thread.sleep(1500)
                    println("\t### At floor number ${--counter} ###")
                    elevator.position.floorNumber = counter

                    val floorNumbers = if(ElevatorControl.callingFloors.isEmpty()) ElevatorControl.callingFloorsInQueue else ElevatorControl.callingFloors
                    if(floorNumbers[0] != floor ) {
                        floor = floorNumbers[0]
                    }
                }
            }
            Direction.UP -> {
                counter = elevator.position.floorNumber
                println("\t\t### At floor number $counter ###")
                while (counter < floor.floorNumber) {
                    Thread.sleep(1500)
                    println("\t\t### At floor number ${++counter} ###")
                    elevator.position.floorNumber = counter

                    val floorNumbers = if(ElevatorControl.callingFloors.isEmpty()) ElevatorControl.callingFloorsInQueue else ElevatorControl.callingFloors
                    if(floorNumbers[0] != floor ) {
                        floor = floorNumbers[0]
                    }
                }
            }
            else -> println("Nothing to do !")
        }
        return floor
    }
}