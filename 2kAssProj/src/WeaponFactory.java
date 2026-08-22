public class WeaponFactory {

    public static Entity MakeWeapon(int wepId)
    {
        Entity weapon = new Entity();
        weapon.components.add(new Physical(weapon));

        switch (wepId) {
            case 1:
                weapon.components.add(new Weapon(weapon, 5, "knife"));
                break;
            case 2:
                weapon.components.add(new Weapon(weapon, 10, "sword"));
                break;
            case 3:
                weapon.components.add(new Weapon(weapon, 15, "axe"));
                break;
            default:
                throw new AssertionError();
        }
        return weapon;
    }

    public static Entity MakeArmor(int armorID)
    {
        Entity armor = new Entity();
        armor.components.add(new Physical(armor));

        switch (armorID) {
            case 1:
                armor.components.add(new Armor(armor, 5, "hat"));
                break;
            case 2:
                armor.components.add(new Armor(armor, 10, "sombrero"));
                break;
            case 3:
                armor.components.add(new Armor(armor, 15, "helmet"));
                break;
            default:
                throw new AssertionError();
        }
        return armor;
    }

}
