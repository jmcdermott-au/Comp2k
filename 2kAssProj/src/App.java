public class App {
    public static void main(String[] args) throws Exception {
        Entity ent1 = new Entity();
        Entity ent2 = new Entity();

        Entity sword = new Entity();
        
        sword.components.add(new Physical(sword));
        sword.components.add(new Weapon(sword));

        Component ent1Limb = new Limb(ent1);
        ent1.components.add(ent1Limb);
        ent1.components.add(new Physical(ent1));
        ent2.components.add(new Physical(ent2));
       
        ent1.components.add(new Inventory(ent1));
        //ent1.components.add(new Limb(ent1));

        Event e1 = new Event();
        e1.ID = "addItem";
        e1.Parameters.put("item", sword);
        ent1.SendEvent(e1);
        
        Event e2 = new Event();
        e2.ID = "equipItemInLimb";
        e2.Parameters.put("item", sword);
        e2.Parameters.put("target", ent1Limb); //i need to figure out how an entity is going to know about its limbs
        ent1.SendEvent(e2);

        Event e3 = new Event();
        e3.ID = "attackWithLimb";
        e3.Parameters.put("target", ent2);
        ent1.SendEvent(e3);

        Event e4 = new Event();
        e4.ID = "DealDamage";
        e4.Parameters.put("target", ent2);
        ent1.SendEvent(e4);
        

    }
}
