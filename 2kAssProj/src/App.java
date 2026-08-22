public class App {
    public static void main(String[] args) throws Exception {
        Entity ent1 = new Entity();
        Entity ent2 = new Entity();

        ent1.components.add(new Physical(ent1));
        ent2.components.add(new Physical(ent2));
        ent1.components.add(new Weapon(ent1));

        ent2.components.add(new Armor(ent2));
        Event e1 = new Event();
        e1.ID = "DealDamage";
        e1.Parameters.put("target", ent2);
        ent1.SendEvent(e1);        
       
        

    }
}
