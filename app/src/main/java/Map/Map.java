package Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Map {
    private final int height;
    private final int maxWidth;
    private final int maxStartRooms;
    private final Room[][] floors;
    private final ArrayList<Room> startRooms;
    private Room bossRoom;


    public Map(int height, int maxWidth, int maxStartRooms) {
        this.height = height;
        this.maxWidth = maxWidth;
        this.maxStartRooms = maxStartRooms;
        this.floors = new Room[height][maxWidth]; 
        this.startRooms = new ArrayList<>();
        generateMap();
    }


    public int getHeight() {
        return height;
    }

    
    public int getMaxWidth() {
        return maxWidth;
    }


    public Room[][] getFloors() {
        return floors;
    }


    public void generateMap() {
        Random random = new Random();

        for (int i = 0; i < maxStartRooms; i++) {
            int randomStartPosition = random.nextInt(maxWidth);
            Room newStartRoom = new Room(0, randomStartPosition);
            this.startRooms.add(newStartRoom); 
            this.floors[0][randomStartPosition] = newStartRoom;
        }

        Room curRoom;

        for (Room startRoom : this.startRooms) {

            curRoom = startRoom;
            
            for (int i = 1; i < this.height; i++) {
                ArrayList<Integer> possibleDirections = new ArrayList<>(List.of(-1, 0, 1));

                int curRoomPosition = curRoom.getFloorPosition();

                if (curRoomPosition - 1 < 0 || checkLeftCross(curRoom)) {possibleDirections.remove(0);}
                else if (curRoomPosition + 1 >= maxWidth || checkRightCross(curRoom)) {possibleDirections.remove(2);}

                Integer step = possibleDirections.get(random.nextInt(possibleDirections.size()));
                
                if (this.floors[i][curRoomPosition + step] == null) {
                    Room nextRoom = new Room(i, curRoomPosition + step);
                    this.floors[i][curRoomPosition + step] = nextRoom;
                    curRoom.getNextRooms().set(step + 1, nextRoom);
                }
                else {
                    curRoom.getNextRooms().set(step + 1, this.floors[i][curRoomPosition + step]);
                }

                curRoom = this.floors[i][curRoomPosition + step];
            }
        }
    }

    
    private boolean checkLeftCross(Room room) {
        int curRoomPosition = room.getFloorPosition();
        int curRoomHeight = room.getCurrentFloor();

        if (curRoomPosition - 1 < 0) {
            return false;
        }

        if (floors[curRoomHeight][curRoomPosition - 1] == null) {
            return false;
        }

        return floors[curRoomHeight][curRoomPosition - 1].hasRightChild();
    }


    private boolean checkRightCross(Room room) {
        int curRoomPosition = room.getFloorPosition();
        int curRoomHeight = room.getCurrentFloor();

        if (curRoomPosition + 1 >= maxWidth) {
            return false;
        }

        if (floors[curRoomHeight][curRoomPosition + 1] == null) {
            return false;
        }

        return floors[curRoomHeight][curRoomPosition + 1].hasLeftChild();
    }


    public Room getBossRoom() {
        return bossRoom;
    }


    public void printMap() {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < maxWidth; j++) {
                System.out.print(this.floors[i][j] != null ? "[R]" : "   ");
            }
            System.out.println();
        }
    }
}

