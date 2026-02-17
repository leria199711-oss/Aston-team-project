public class Main {
    public static void main(String[] args) {
        Barrel barrel = new Barrel.Builder()
                .setVolume(100.0)
                .setMaterial("")
                .setStoredMaterial("Килька")
                .build();
        System.out.println(barrel);

    }

}
