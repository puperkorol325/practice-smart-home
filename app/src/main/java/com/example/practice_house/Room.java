package com.example.practice_house;

public class Room {

    private final String name;
    private final int typeIndex;

    public Room(String name, int typeIndex) {
        this.name = name;
        this.typeIndex = typeIndex;
    }

    public String getName() {
        return name;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public RoomTypes.Type getType() {
        return RoomTypes.ALL[typeIndex];
    }
}
