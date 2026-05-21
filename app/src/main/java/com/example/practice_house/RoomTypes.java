package com.example.practice_house;

/**
 * Все типы комнат приложения — единый источник для экрана добавления и главного списка.
 */
public final class RoomTypes {

    public static final class Type {
        public final int labelRes;
        public final int mainListIconRes;
        public final int pickerIconRes;

        public Type(int labelRes, int mainListIconRes, int pickerIconRes) {
            this.labelRes = labelRes;
            this.mainListIconRes = mainListIconRes;
            this.pickerIconRes = pickerIconRes;
        }
    }

    public static final Type[] ALL = {
            new Type(
                    R.string.add_room_type_living,
                    R.drawable.ic_main_room_living,
                    R.drawable.ic_add_room_type_living_selected
            ),
            new Type(
                    R.string.add_room_type_kitchen,
                    R.drawable.ic_main_room_kitchen,
                    R.drawable.ic_add_room_type_kitchen_selected
            ),
            new Type(
                    R.string.add_room_type_bathroom,
                    R.drawable.ic_main_room_bathroom,
                    R.drawable.ic_add_room_type_bathroom_selected
            ),
            new Type(
                    R.string.add_room_type_office,
                    R.drawable.ic_add_room_type_office_selected,
                    R.drawable.ic_add_room_type_office_selected
            ),
            new Type(
                    R.string.add_room_type_bedroom,
                    R.drawable.ic_add_room_type_bedroom_selected,
                    R.drawable.ic_add_room_type_bedroom_selected
            ),
            new Type(
                    R.string.add_room_type_hall,
                    R.drawable.ic_add_room_type_hall_selected,
                    R.drawable.ic_add_room_type_hall_selected
            ),
    };

    private RoomTypes() {
    }
}
