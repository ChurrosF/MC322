package Map;

import java.util.Random;

public final class Map {
    private final int height;
    private final int maxWidth;
    private final int maxStartRooms;
    private final Room[][] floors;
    private final Room[] startRooms;
    private Room bossRoom;


    public Map(int height, int maxWidth, int maxStartRooms) {
        this.height = height;
        this.maxWidth = maxWidth;
        this.maxStartRooms = maxStartRooms;
        this.floors = new Room[height][maxWidth];
        this.startRooms = new Room[maxStartRooms];
        generateRandomStartFloor();
        generateMap(height);
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


    public void generateMap(int height) {
        for (Room room: this.startRooms) {
            generateRandomPath(room);
        }
    }


    public void generateRandomStartFloor() {
        Random random = new Random();
        for (int i = 0; i < maxStartRooms; i++) {
            int randomStartRoomIndex = random.nextInt(maxWidth);
            Room newStartRoom = new Room(1, randomStartRoomIndex);
            this.startRooms[i] = newStartRoom;
            this.floors[1][randomStartRoomIndex] = newStartRoom;
        }
    }


    public void generateRandomPath(Room curRoom) {
        Random random = new Random();
        Room newRoom;

        if (curRoom.getCurrentFloor() == this.height) {
            return;
        }
        
        int[] possibleSpots = new int[3];
        for (int i = 0; i < 3; i++) {
            Room possibleRoom = new Room(curRoom.getCurrentFloor() + 1, curRoom.getFloorPosition() - 1 + i);
            possibleSpots[i] = 0;
            if (!isPathCrossing(curRoom, possibleRoom)) {
                possibleSpots[i] = i;
            }
        }

        int spot = possibleSpots[random.nextInt(possibleSpots.length)];
        curRoom.insert(curRoom, spot);

        newRoom = (Room) curRoom.getChildAt(spot);
        int roomIndex = curRoom.getFloorPosition() - 1 + spot;
        this.floors[curRoom.getCurrentFloor() + 1][roomIndex] = newRoom; 
        generateRandomPath(newRoom);
    }


    private boolean isPathCrossingRL(Room room1, Room room2) {
        if (!room1.getLeftChild().isRoomEqual(room2)) {
            return false;            
        }

        Room bottomLeftRoom = this.floors[room1.getCurrentFloor()][room1.getFloorPosition() - 1];
        Room upperRightRoom = this.floors[room2.getCurrentFloor()][room2.getFloorPosition() + 1];
        return bottomLeftRoom.getRightChild().isRoomEqual(upperRightRoom);
    }


    private boolean isPathCrossingLR(Room room1, Room room2) {
        if (!room1.getRightChild().isRoomEqual(room2)) {
            return false;            
        }
        
        Room bottomRightRoom = this.floors[room1.getCurrentFloor()][room1.getFloorPosition() + 1];
        Room upperLeftRoom = this.floors[room2.getCurrentFloor()][room2.getFloorPosition() - 1];

        if ((bottomRightRoom == null) || (upperLeftRoom == null)) {
            return true;
        }

        return bottomRightRoom.getLeftChild().isRoomEqual(upperLeftRoom);
    }


    private boolean isPathCrossing(Room room1, Room room2) {
        if (room1.getFloorPosition() == room1.getFloorPosition() - 1) {
            return isPathCrossingRL(room1, room2);
        }
        else {
            return isPathCrossingLR(room1, room2);
        }
    }


    public Room getBossRoom() {
        return bossRoom;
    }


    public void printMap() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.maxWidth; j++) {
                if (this.floors[i][j] != null) {
                    System.out.println("a");
                }
                else {
                    System.out.println(" ");
                }
            }
        }
    }
}
