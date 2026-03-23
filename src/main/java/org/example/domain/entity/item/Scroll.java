package org.example.domain.entity.item;

import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.character.Player;

public class Scroll extends Item {
    private ScrollType scrollType;

    public enum ScrollType {
       strength, dexterity, max_hp
    }

    public Scroll(Coordinate position, ScrollType scrollType) {
        super( "scroll", position);
        this.scrollType = scrollType;
    }

    @Override
    public void ItemFunction(Player player) {
        switch (scrollType) {
            case  strength:
                player.increaseStrength(5);
                break;
            case dexterity:
                player.increaseDexterity(5);
                break;
            case max_hp:
                int increase = 15;
                player.increaseMaxHP(increase);
                player.increaseHP(increase);
                break;
        }
    }

    public ScrollType getScrollType() { return scrollType; }

    public int getValue() {
             switch (scrollType) {
                 case strength:
                 case dexterity: return 5;
                 case max_hp: return 15;
        }
        return 0;
    }
}