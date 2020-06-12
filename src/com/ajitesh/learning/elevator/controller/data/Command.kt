package com.ajitesh.learning.elevator.controller.data

import com.ajitesh.learning.elevator.enum.Button
import com.ajitesh.learning.elevator.enum.Floor
import com.ajitesh.learning.elevator.eventhandler.EventHandler

data class Command (val destinationFloor: Floor, val eventHandler: EventHandler, val button: Button) {
}

sealed class ElevatorCommand

data class GoUp(val currentFloor: Floor) : ElevatorCommand()
data class GoDown(val currentFloor: Floor) : ElevatorCommand()
