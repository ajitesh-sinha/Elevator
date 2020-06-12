package com.ajitesh.learning.elevator.controller

import com.ajitesh.learning.elevator.Elevator
import com.ajitesh.learning.elevator.controller.data.Command
import com.ajitesh.learning.elevator.enum.Button
import com.ajitesh.learning.elevator.enum.Direction
import com.ajitesh.learning.elevator.enum.Floor

class ElevatorControl (private val elevator: Elevator) {

    companion object {
        val callingFloors = mutableListOf<Floor>()
        val callingFloorsInQueue = mutableListOf<Floor>()
    }
    fun sendCommand(command: Command) {
        val destinationFloor = command.destinationFloor
        val eventHandler = command.eventHandler
        val button = command.button

        if (elevator.direction == Direction.STILL) {
            callingFloors.add(destinationFloor)
            println("Control: Elevator is starting from floor number ${elevator.position} now..")
            eventHandler.sendElevator()
        } else {
            println("Control: Elevator is going ${elevator.direction}")

            // If elevator is moving downwards,
            // and is above the calling floor,
            // and Down caller button is used, then add interim pick-up stop
            if (elevator.direction == Direction.DOWN && elevator.position > destinationFloor && button == Button.DOWN) {
                callingFloors.add(destinationFloor)
                callingFloors.sortWith(reverseOrder())
                println("Control: Elevator will halt at floor $destinationFloor.")
                callingFloors.forEach { print("$it ") }
                println("")
            }

            // If elevator is moving downwards and is below the calling floor or UP call button is used,
            // add it to queue, so that it can be resumed once downward journey is complete
            if (elevator.direction == Direction.DOWN && (elevator.position < destinationFloor || button == Button.UP) ){
                callingFloorsInQueue.add(destinationFloor)
                callingFloorsInQueue.sort()
                if (button == Button.UP) {
                    println("Control: Elevator is going downwards, UP button call has been queued.")
                } else {
                    println("Control: Elevator is going downwards and is below floor number $destinationFloor, it has been added to the queue.")
                }
                callingFloorsInQueue.forEach { print("$it ") }
                println("")
            }

            // If elevator is moving upwards,
            // and is below the calling floor,
            // and UP caller button is used, then add interim pick-up stop
            if (elevator.direction == Direction.UP && elevator.position < destinationFloor && button == Button.UP) {
                callingFloors.add(destinationFloor)
                callingFloors.sort()
                println("Control: Elevator will halt at floor $destinationFloor.")
                callingFloors.forEach { print("$it ") }
                println("")
            }

            // If elevator is moving upwards and is above the calling floor, wait for lift to stop
            if (elevator.direction == Direction.UP && (elevator.position > destinationFloor || button == Button.DOWN) ) {
                callingFloorsInQueue.add(destinationFloor)
                callingFloorsInQueue.sortWith(reverseOrder())
                if (button == Button.DOWN) {
                    println("Control: Elevator is going upwards, DOWN button call has been queued.")
                } else {
                    println("Control: Elevator is going upwards and is above floor number $destinationFloor, it has been added to the queue.")
                }
                callingFloorsInQueue.forEach { print("$it ") }
                println("")
            }
        }
    }
}