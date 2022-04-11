package battleship;

import java.util.ArrayList;
import java.util.List;


public class Ships {

    char[][] array = new char[10][10];
    char[][] fogArray = new char[10][10];
    List<String> list = new ArrayList<>();

    int carrierCount = 5;
    int battlesCount = 4;
    int submarineCount = 3;
    int cruiserCount = 3;
    int destroyerCount = 2;

    List<String> carrierShip = new ArrayList<>();
    List<String> battlesShip = new ArrayList<>();
    List<String> submarineShip = new ArrayList<>();
    List<String> cruiserShip = new ArrayList<>();
    List<String> destroyerShip = new ArrayList<>();

    public Ships() {
        list.add("carrierCount");
        list.add("battlesCount");
        list.add("submarineCount");
        list.add("cruiserCount");
        list.add("destroyerCount");

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = '~';
                fogArray[i][j] = '~';
            }
        }
    }

    public void put(String coordination, String nameShip) {
        switch (nameShip) {
            case "Carrier":
                carrierShip.add(coordination);
                break;
            case "BattlesShip":
                battlesShip.add(coordination);
                break;
            case "Submarine":
                submarineShip.add(coordination);
                break;
            case "Cruiser":
                cruiserShip.add(coordination);

                break;
            case "Destroyer":
                destroyerShip.add(coordination);
                break;
        }
    }

    public void shotShip(String coordination) {
        if (carrierShip.contains(coordination)) {
            carrierCount--;
        } else if (battlesShip.contains(coordination)) {
            battlesCount--;
        } else if (submarineShip.contains(coordination)) {
            submarineCount--;
        } else if (cruiserShip.contains(coordination)) {
            cruiserCount--;

        } else if (destroyerShip.contains(coordination)) {
            destroyerCount--;
        }
    }
}