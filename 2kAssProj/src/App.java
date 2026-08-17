public class App {
    public static void main(String[] args) throws Exception {
        Entity ent1 = new Entity();
        Entity ent2 = new Entity();

        ent1.components.add(new TestComponent());
        ent2.components.add(new TestComponent());

        Event testEv = new Event();
        testEv.ID = "TestEvent";
        testEv.Parameters.put("health", 15);
        ent2.SendEvent(testEv);
        
        
    }
}
