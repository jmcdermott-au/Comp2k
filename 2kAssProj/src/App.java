public class App {
    public static void main(String[] args) throws Exception {
        Entity ent1 = new Entity();
        Entity ent2 = new Entity();

        ent1.components.add(new TestComponent(ent1));
        ent2.components.add(new TestComponent(ent2));
        ent1.components.add(new TestChangerComponent(ent1));

        
        ent1.components.get(0).TargetedEventSend(ent2);
        

    }
}
