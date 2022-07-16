package dungeonmania.util;

import java.util.List;
<<<<<<< HEAD
import java.util.stream.Collectors;

import dungeonmania.entities.Item;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
=======

import dungeonmania.entities.Item;
>>>>>>> 6ccb4a5227bd5b4cacedc0a31e75bfda8a6574d8

public class Round {
    private double deltaPlayerHealth;
    private double deltaEnemyHealth;
    private List<Item> weaponryUsed;

    public Round(double deltaPlayerHealth, double deltaEnemyHealth, List<Item> weaponryUsed) {
        this.deltaPlayerHealth = deltaPlayerHealth;
        this.deltaEnemyHealth = deltaEnemyHealth;
        this.weaponryUsed = weaponryUsed;
    }

    public double getDeltaCharacterHealth(){
        return deltaPlayerHealth;
    }
    
    public double getDeltaEnemyHealth(){
        return deltaEnemyHealth;
    }

    public List<Item> getWeaponryUsed() { return weaponryUsed; }
<<<<<<< HEAD

    public List<ItemResponse> getItemResponses(List<Item> items) {
        return items.stream().map(Item::getItemResponse).collect(Collectors.toList());
    }

    public RoundResponse getRoundResponse() {
        return new RoundResponse(deltaPlayerHealth, deltaEnemyHealth, getItemResponses(weaponryUsed));
    }
=======
>>>>>>> 6ccb4a5227bd5b4cacedc0a31e75bfda8a6574d8
}
