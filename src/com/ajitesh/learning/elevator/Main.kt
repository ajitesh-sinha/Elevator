package com.ajitesh.learning.elevator

import com.ajitesh.learning.elevator.controller.ElevatorControl
import com.ajitesh.learning.elevator.controller.data.Command
import com.ajitesh.learning.elevator.enum.Button
import com.ajitesh.learning.elevator.enum.Direction
import com.ajitesh.learning.elevator.enum.Floor
import com.ajitesh.learning.elevator.eventhandler.FloorPanelEventHandler
import kotlin.concurrent.thread
// upward case: starting at 0, then call from 9,6,4,11,3
//TODO pass eventHandler to elevatorControl
fun main() {
    val elevator = Elevator(Floor.TWELVE, Direction.STILL)
    val elevatorControl = ElevatorControl(elevator)

    thread {
        val floor = Floor.NINE
        Thread.currentThread().name="Thread-1"
        val command = Command(floor, FloorPanelEventHandler(elevator), Button.DOWN)
        println(">>> Call from floor $floor, Button: ${Button.DOWN} <<<")
        elevatorControl.sendCommand(command)
    }
    thread {
        val floor = Floor.SIX
        Thread.currentThread().name="Thread-2"
        val command = Command(floor, FloorPanelEventHandler(elevator), Button.DOWN)
        Thread.sleep(2000L)
        println(">>> Call from floor $floor, Button: ${Button.DOWN} <<<")
        elevatorControl.sendCommand(command)
    }
    thread {
        val floor = Floor.FOUR
        Thread.currentThread().name="Thread-3"
        val command = Command(floor, FloorPanelEventHandler(elevator), Button.UP)
        Thread.sleep(4000L)
        println(">>> Call from floor $floor, Button: ${Button.UP} <<<")
        elevatorControl.sendCommand(command)
    }
    thread {
        val floor = Floor.ELEVEN
        Thread.currentThread().name="Thread-4"
        val command = Command(floor, FloorPanelEventHandler(elevator), Button.DOWN)
        Thread.sleep(6000L)
        println(">>> Call from floor $floor, Button: ${Button.DOWN} <<<")
        elevatorControl.sendCommand(command)
    }
    thread {
        val floor = Floor.THREE
        Thread.currentThread().name="Thread-5"
        val command = Command(floor, FloorPanelEventHandler(elevator), Button.UP)
        Thread.sleep(8000L)
        println(">>> Call from floor $floor, Button: ${Button.UP} <<<")
        elevatorControl.sendCommand(command)
    }
}