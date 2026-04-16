package Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Entities.Enemy;

public class Room {
    private int currentFloor;
    private int floorPosition;
    private boolean visited;
    private final ArrayList<Room> nextRooms;
    private final ArrayList<Enemy> enemies = new ArrayList<>();

    public Room(int currentFloor, int floorPosition) {
        this.currentFloor = currentFloor;
        this.floorPosition = floorPosition;
        this.visited = false;
        this.nextRooms = new ArrayList<>(Arrays.asList(null, null, null));
    }


    public int getCurrentFloor() {
        return currentFloor;
    }

    
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }


    public int getFloorPosition() {
        return floorPosition;
    }

    
    public void setFloorPosition(int floorPosition) {
        this.floorPosition = floorPosition;
    }
    
    
    public List<Room> getNextRooms() {
        return nextRooms;
    }


    public boolean hasLeftChild() {
        return (this.nextRooms.get(0) != null);
    }


    public boolean hasCenterChild() {
        return (this.nextRooms.get(1) != null);
    }


    public boolean hasRightChild() {
        return (this.nextRooms.get(2) != null);
    }
    
    
    public boolean isRoomEqual(Room room) {
        return this.currentFloor == room.getCurrentFloor() && this.floorPosition == room.getFloorPosition();
    }
}
