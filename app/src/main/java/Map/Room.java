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
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private RoomType type;

    public Room(int currentFloor, int floorPosition, RoomType type) {
        this.currentFloor = currentFloor;
        this.floorPosition = floorPosition;
        this.type = type;
        this.visited = false;
        this.nextRooms = new ArrayList<>(Arrays.asList(null, null, null));
        
        if (this.type != RoomType.CAMPFIRE) {
            this.enemies.add(new Enemy("Thug Spider", 2, 0, new int[] {1, 4}));
            this.enemies.add(new Enemy("Tuff Spider", 2, 0, new int[] {2, 5}));
        }
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
    public ArrayList<Enemy> getEnemies() { 
        return enemies; 
    }
    public void setEnemies(ArrayList<Enemy> enemies) { 
        this.enemies = enemies; 
    }
    public boolean isVisited() { 
        return visited; 
    }
    public void setVisited(boolean visited) { 
        this.visited = visited; 
    }
    public RoomType getType() {
         return type;
        }
}
