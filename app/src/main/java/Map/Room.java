package Map;

import javax.swing.tree.DefaultMutableTreeNode;

public class Room extends DefaultMutableTreeNode {
    private int currentFloor;
    private int floorPosition;

    public Room(int currentFloor, int floorPosition) {
        this.currentFloor = currentFloor;
        this.floorPosition = floorPosition;
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


    public Room getLeftChild() {
       Room room = (Room) this.getChildAt(0);
       return room;
    }


    public Room getCenterChild() {
        Room room = (Room) this.getChildAt(1);
        return room;
    }


    public Room getRightChild() {
        Room room = (Room) this.getChildAt(2);
        return room;
    }


    public boolean isRoomEqual(Room room) {
        return this.currentFloor == room.getCurrentFloor() && this.floorPosition == room.getFloorPosition();
    }
}
